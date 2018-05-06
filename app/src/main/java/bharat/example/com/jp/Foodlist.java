package bharat.example.com.jp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import bharat.example.com.jp.Common.Common;
import bharat.example.com.jp.Interface.ItemClickListener;
import bharat.example.com.jp.ViewHolder.FoodViewHolder;
import bharat.example.com.jp.models.Food;

public class Foodlist extends AppCompatActivity {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

        FloatingActionButton fab;

        FirebaseDatabase database;
        DatabaseReference foodList;
        FirebaseStorage storage;
        StorageReference storageReference;

        EditText name,description,price,discount;
        Button select, upload,submit,cancel;

        Food newFood;

        String categoryID="";

        FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

        Uri uri;

        private final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);
        database=FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images/category/food");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fab = (FloatingActionButton)findViewById(R.id.add_fd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFoodDialog();

            }
        });
      if(getIntent() != null)
          categoryID = getIntent().getStringExtra("CategoryId");

        if(!categoryID.isEmpty())
            loadListFood(categoryID);
    }

    private void showAddFoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Foodlist.this);
        builder.setTitle("Add new Food Item");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_fd = inflater.inflate(R.layout.add_new_food_item, null);

        name = add_new_fd.findViewById(R.id.fname);
        description = add_new_fd.findViewById(R.id.fdecription);
        price = add_new_fd.findViewById(R.id.fprice);
        discount = add_new_fd.findViewById(R.id.fdiscount);
        select = add_new_fd.findViewById(R.id.btnSelect);
        upload = add_new_fd.findViewById(R.id.btnUpload);




        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        builder.setView(add_new_fd);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (newFood != null) {
                    foodList.push().setValue(newFood);
                }
                else {
                    Toast.makeText(Foodlist.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void loadListFood(final String categoryID) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryID)
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.food_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void uploadImage() {
        if (uri != null) {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("category" + imageName);

            imageFolder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mDialog.dismiss();
                    Toast.makeText(Foodlist.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //Toast.makeText(Foodlist.this, "Please Fill all the fields!", Toast.LENGTH_SHORT).show();

                            newFood = new Food();
                            if (!isEmpty(name.getText().toString()) && !isEmpty(price.getText().toString()) && !isEmpty(discount.getText().toString())
                                    && !isEmpty(description.getText().toString())) {
                                newFood.setName(name.getText().toString());
                                newFood.setDescription(description.getText().toString());
                                newFood.setPrice(price.getText().toString());
                                newFood.setMenuId(categoryID);
                                newFood.setDiscount(discount.getText().toString());
                                newFood.setImage(uri.toString());
                                Toast.makeText(Foodlist.this, "New Food Added successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Foodlist.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(Foodlist.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploading..." );
                }
            });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        //intent.setType("images/*");
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        // startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            uri = data.getData();
            select.setText("Image Selected");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE)){
            showUpdateFoodDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        else
        {
            deleteFood(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteFood(String key) {
        foodList.child(key).removeValue();
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }

    private void showUpdateFoodDialog(final String key, final Food item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Foodlist.this);
        builder.setTitle("Add new Food Item");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_fd = inflater.inflate(R.layout.add_new_food_item, null);

        name = add_new_fd.findViewById(R.id.fname);
        description = add_new_fd.findViewById(R.id.fdecription);
        price = add_new_fd.findViewById(R.id.fprice);
        discount = add_new_fd.findViewById(R.id.fdiscount);
        select = add_new_fd.findViewById(R.id.btnSelect);
        upload = add_new_fd.findViewById(R.id.btnUpload);


        name.setText(item.getName());
        description.setText(item.getDescription());
        price.setText(item.getPrice());
        discount.setText(item.getDiscount());




        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
            }
        });


        builder.setView(add_new_fd);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!isEmpty(name.getText().toString()) && !isEmpty(price.getText().toString()) && !isEmpty(discount.getText().toString())
                        && !isEmpty(description.getText().toString())) {

                    item.setName(name.getText().toString());
                    item.setPrice(price.getText().toString());
                    item.setDescription(description.getText().toString());
                    item.setDiscount(discount.getText().toString());

                    foodList.child(key).setValue(item);

                    Toast.makeText(Foodlist.this, "Successfully saved the changes", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Foodlist.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
            }
        });
        builder.show();

    }

    private void changeImage(final Food item) {
        if (uri != null) {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Image Uploading...");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("category" + imageName);
            imageFolder.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(Foodlist.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value for newCategory if image upload and we can get download link
                                    item.setImage(uri.toString());

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(Foodlist.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploading..." );
                        }
                    });
        }
    }
    private boolean isEmpty(String string) {
        return string.equals("");
    }
}

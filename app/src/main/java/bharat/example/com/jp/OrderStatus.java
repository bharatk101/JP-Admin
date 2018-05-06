package bharat.example.com.jp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import bharat.example.com.jp.Common.Common;
import bharat.example.com.jp.Interface.ItemClickListener;
import bharat.example.com.jp.Remote.APIService;
import bharat.example.com.jp.ViewHolder.OrderViewHolder;
import bharat.example.com.jp.models.MyResponse;
import bharat.example.com.jp.models.Notification;
import bharat.example.com.jp.models.Request;
import bharat.example.com.jp.models.Sender;
import bharat.example.com.jp.models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatus extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase db;
    DatabaseReference requests;

    MaterialSpinner spinner;

    APIService mService;
    String status = "0";
    MaterialSpinner stat;
    String spin = "1";

    private static final String TAG = "OrderStatus";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");

        mService = Common.getFCMService();

        recyclerView = (RecyclerView)findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        stat = (MaterialSpinner)findViewById(R.id.options);
        Common.spin = stat;

        stat.setItems("Placed","Confirmed","Out for Delivery","Delivered","Delivery Atempted","Delivery Failed",Common.convertCodeToString("6"),Common.convertCodeToString("7"),Common.convertCodeToString("8"));

        stat.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if (0 == position){
                    status = "0";
                    Log.d(TAG, "onItemSelected: status position ="+position);
                    Log.d(TAG, "onItemSelected: view = "+view);
                    Log.d(TAG, "onItemSelected: id " + id);
                    Log.d(TAG, "onItemSelected: item = "+ item);
                    loadOrders();
                }
                else if (1 == position){
                    status = "1";
                    Log.d(TAG, "onItemSelected: status position ="+position);
                    Log.d(TAG, "onItemSelected: view = "+view);
                    Log.d(TAG, "onItemSelected: id " + id);
                    Log.d(TAG, "onItemSelected: item = "+ item);
                    loadOrders();
                }
                else if (position == 2){
                    status = "2";
                    Log.d(TAG, "onItemSelected: status position ="+position);
                    Log.d(TAG, "onItemSelected: view = "+view);
                    Log.d(TAG, "onItemSelected: id " + id);
                    Log.d(TAG, "onItemSelected: item = "+ item);
                    loadOrders();
                }
                else if (position == 3){
                    status = "3";
                    Log.d(TAG, "onItemSelected: status position ="+position);
                    Log.d(TAG, "onItemSelected: view = "+view);
                    Log.d(TAG, "onItemSelected: id " + id);
                    Log.d(TAG, "onItemSelected: item = "+ item);
                    loadOrders();
                }
                else if (position == 4){
                    status ="4";
                    loadOrders();
                }
                else if (position == 5){
                    status = "5";
                    loadOrders();
                }
                else if (position == 6) {
                    status = "6";
                    loadOrders();
                }
                else if (position == 7){
                    status = "7";
                    loadOrders();

                }else {
                    status = "8";
                    loadOrders();
                }


            }


        });

        loadOrders();
    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("status").equalTo(status)

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, int position) {

                viewHolder.txtOrderId.setText("Order ID : #"+adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Status : "+Common.convertCodeToString(model.getStatus()));
                viewHolder.txtOrderAddress.setText("Address : "+model.getAddress());
                viewHolder.txtOrderPhone.setText("Phone : "+model.getPhone());
                viewHolder.txtOrderdate.setText("Date : "+model.getDatetime());
                viewHolder.orderName.setText("Name :"+model.getName());


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent details = new Intent(OrderStatus.this,OrderDetails.class);
                        details.putExtra("OrderId",adapter.getRef(position).getKey());
                        Common.currentRequest=model;
                        startActivity(details);

                    }
                });



            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle().equals("Update")) {
            showUpdatedialog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));

        }
        else if (item.getTitle().equals(Common.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());

        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {

        requests.child(key).removeValue();

    }

    private void showUpdatedialog(String key, final Request item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("Update Order");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_layout,null);

        spinner = (MaterialSpinner) view.findViewById(R.id.statusSpinner);

        if (stat.getSelectedIndex() == 0){
            spinner.setItems("Confirmed","Cancelled due to shop being closed","Cancelled due to out of delivery area","Cancelled due to no delivery currently available");
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    if (spinner.getText() == "Confirmed"){
                        spin = "1";
                    }else if (spinner.getSelectedIndex() == 1){
                        spin = "6";
                    }
                    else if (spinner.getSelectedIndex() == 2){
                        spin = "7";
                    }else
                        spin = "8";
                }
            });
        }
        else if (stat.getSelectedIndex() == 1)
        {   spin = "2";
            spinner.setItems("Out for Delivery");
            Log.d(TAG, "showUpdatedialog: "+stat.getSelectedIndex());
        }
        else{
            spinner.setVisibility(View.INVISIBLE);
            //spinner.setItems("Cancelled");

            Log.d(TAG, "showUpdatedialog: "+stat.getSelectedIndex());
        }
        /*if (stat.getSelectedIndex() == 0){
            spinner.setItems("Confirmed","Cancelled due to shop being closed","Cancelled due to out of delivery area","Cancelled due to shop being closed");
            if(spinner.getSelectedIndex() == 0){
                spin = "1";
            }
            else if (spinner.getSelectedIndex() == 1){
                spin ="6";
            }
            else if (spinner.getSelectedIndex() == 2){
                spin = "7";
            }
            else
                spin = "8";

                Log.d(TAG, "showUpdatedialog: +"+spinner.getSelectedIndex());
        }
        else if (stat.getSelectedIndex() == 1)
        {   spin = "2";
            spinner.setItems("Out for Delivery");
            Log.d(TAG, "showUpdatedialog: "+stat.getSelectedIndex());
        }
        else{
            spinner.setVisibility(View.INVISIBLE);
            //spinner.setItems("Cancelled");

            Log.d(TAG, "showUpdatedialog: "+stat.getSelectedIndex());
        }
        //spinner.setItems("Placed","Confirmed","Out for Delivery","Delivered","Cancelled");

*/




        alertDialog.setView(view);

        final String localKey = key;
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spin));


                requests.child(localKey).setValue(item);

                sendorderStatusToUser(localKey,item);

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();


    }

    private void sendorderStatusToUser(final String key, final Request item) {
        DatabaseReference tokens = db.getReference("Tokens");


        tokens.orderByKey().equalTo(item.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot:dataSnapshot.getChildren())
                        {
                            Token token = postSnapShot.getValue(Token.class);

                            Notification notification = new Notification("Joe's Pizzeria","Your order "+key+" was updated");
                            Sender content = new Sender(token.getToken(),notification);

                            mService.sendNotification(content)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            if (response.body().success == 1){
                                                Toast.makeText(OrderStatus.this, "Your order has been Updated", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(OrderStatus.this, "Order Updated but failed to Notify", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {
                                            Log.e("onFailure: ",t.getMessage() );

                                        }
                                    });


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}

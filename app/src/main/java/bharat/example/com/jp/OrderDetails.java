package bharat.example.com.jp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import bharat.example.com.jp.Common.Common;
import bharat.example.com.jp.Remote.APIService;
import bharat.example.com.jp.ViewHolder.OrderDetailAdapter;
import bharat.example.com.jp.models.MyResponse;
import bharat.example.com.jp.models.Notification;
import bharat.example.com.jp.models.Order;
import bharat.example.com.jp.models.Sender;
import bharat.example.com.jp.models.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetails extends AppCompatActivity {

    TextView orderId, orderDate, orderPrice,orderAddress, orderLandmark, orderPhone, orderAltPhone;
    String order_id_vale = "";
    RecyclerView foods;
    RecyclerView.LayoutManager layoutManager;
    OrderDetailAdapter adapter = new OrderDetailAdapter(Common.currentRequest.getFoods());
    String localKey = "";
    FirebaseDatabase db;
    DatabaseReference requests;
    Order order;
    List<Order> myOrders;
    APIService mService;
    private static final String TAG = "OrderDetails";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        db = FirebaseDatabase.getInstance();
        requests = db.getReference("Requests");
        mService = Common.getFCMService();

        orderId = (TextView)findViewById(R.id.order_id);
        orderDate = (TextView)findViewById(R.id.order_date);
        orderPrice = (TextView)findViewById(R.id.order_total);
        orderAddress = (TextView)findViewById(R.id.order_address);
        orderPhone = (TextView)findViewById(R.id.order_phone);
        orderAltPhone = (TextView)findViewById(R.id.order_alt_phone);
        orderLandmark = (TextView)findViewById(R.id.order_landmark);

        foods = (RecyclerView)findViewById(R.id.lstFood);
        foods.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        foods.setLayoutManager(layoutManager);

        if (getIntent() != null){
            order_id_vale = getIntent().getStringExtra("OrderId");
        }


        orderId.setText("Order ID : #"+order_id_vale);
        orderPhone.setText("Phone : "+Common.currentRequest.getPhone());
        orderPrice.setText("Total : "+Common.currentRequest.getTotal());
        orderAddress.setText("Address : "+Common.currentRequest.getAddress());
        orderAltPhone.setText(" Alternate Phone : "+Common.currentRequest.getAlternateMobile());
        orderDate.setText("TimeStamp : "+Common.currentRequest.getDatetime());
        orderLandmark.setText("Landmark : "+Common.currentRequest.getLandMark());



        localKey= order_id_vale;
        adapter.notifyDataSetChanged();
        foods.setAdapter(adapter);


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (Common.spin.getSelectedIndex() == 0) {
            if (item.getTitle().equals("Item Not Available!")) {
                sendorderStatusToUser(adapter, item);
                changeStatus();
            }
        }
        return super.onContextItemSelected(item);
    }

    private void changeStatus() {
        int t = Integer.parseInt(Common.key);
        String val = String.valueOf(t).trim();
        String id = order_id_vale.substring(order_id_vale.indexOf("d")+1);

        requests.child(id).child("foods").child(val).child("pstatus").setValue("Unavailable");
    }

    private void deleteItem(String localKey) {
        int t = 1 + Integer.parseInt(Common.key);
        String val = String.valueOf(t).trim();
        Log.d(TAG, "deleteItem: " + val);
        String id = order_id_vale.substring(order_id_vale.indexOf("d")+1);
        Log.d(TAG, "deleteItem: id "+id);
       //boolean del = requests.child(localKey).child("foods").getKey().equals(Common.key);
        // requests.child(id).child("foods").child(val).removeValue();
        // requests.child(order_id_vale).removeValue();


    }


    private void sendorderStatusToUser(final OrderDetailAdapter localKey, final MenuItem item) {
        DatabaseReference tokens = db.getReference("Tokens");

        tokens.orderByKey().equalTo(Common.currentRequest.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                            Token token = postSnapShot.getValue(Token.class);

                            Notification notification = new Notification("Joe's Pizzeria", "Your item "+Common.currentOrder+" isn't available and can't be delivered");
                            Sender content = new Sender(token.getToken(), notification);

                            mService.sendNotification(content)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            if (response.body().success == 1) {
                                                Toast.makeText(OrderDetails.this, "Your order has been Updated", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(OrderDetails.this, "Order Updated but failed to Notify", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {
                                            Log.e("onFailure: ", t.getMessage());

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


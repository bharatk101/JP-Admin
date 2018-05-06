package bharat.example.com.jp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bharat.example.com.jp.Common.Common;
import bharat.example.com.jp.Interface.ItemClickListener;
import bharat.example.com.jp.R;
import bharat.example.com.jp.models.Order;

/**
 * Created by bharat on 3/6/18.
 */

public class OrderDetailAdapter  extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener, View.OnCreateContextMenuListener{

    private ItemClickListener itemClickListener;
    List<Order> myOrders;

    public int position;
    private static final String TAG = "OrderDetailAdapter";
    Order order;

    public OrderDetailAdapter(List<Order> myOrders) {
        this.myOrders = myOrders;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_details_layout,parent,false);

        itemView.setOnCreateContextMenuListener(this);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setTag(position);

         order = myOrders.get(position);

        holder.name.setText(String .format("Name : %s",order.getProductName()));
        //Common.currentOrder = order.getProductName();
        holder.quantity.setText(String .format("Quantity : %s",order.getQuantity()));
        holder.size.setText(String .format("Size : %s",order.getSize()));
        holder.crust.setText(String .format("Crust : %s",order.getCrust()));
        holder.price.setText(String .format("Price : %s",order.getPrice()));
        holder.discount.setText(String .format("Discount : %s",order.getDiscount()));
        holder.status.setText(String .format("Status : %s",order.getPstatus()));

    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (Common.spin.getSelectedIndex() == 0) {
            getName((Integer) v.getTag(), v);
            menu.setHeaderTitle("Select an Action");
            menu.add(0, 0, position, "Item Not Available!");
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        position = (int)v.getTag();
        itemClickListener.onClick(v,position,false);
        getName(position, v);
    }
    public void getName(int position, View v){
        order = myOrders.get(position);
        //Common.key = (String) v.getTag();
        Common.key = String.valueOf(position);
        Log.d(TAG, "getName: "+Common.key);
        Common.cOrder = myOrders.get(position);
        String name =order.getProductName();
        Common.currentOrder = name;
    }


}


class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView name, quantity, size, crust, discount, price,status;

    public MyViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.product_name);
        quantity = (TextView)itemView.findViewById(R.id.product_quantity);
        size = (TextView)itemView.findViewById(R.id.product_size);
        price = (TextView)itemView.findViewById(R.id.product_price);
        discount = (TextView)itemView.findViewById(R.id.product_discount);
        crust = (TextView)itemView.findViewById(R.id.product_crust);
        status = (TextView)itemView.findViewById(R.id.product_status);




    }
}
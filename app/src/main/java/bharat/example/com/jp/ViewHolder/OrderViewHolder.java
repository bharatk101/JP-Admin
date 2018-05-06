package bharat.example.com.jp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import bharat.example.com.jp.Common.Common;
import bharat.example.com.jp.Interface.ItemClickListener;
import bharat.example.com.jp.R;

/**
 * Created by bharat on 2/28/18.
 */

public class OrderViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener,
       // View.OnLongClickListener,
        View.OnCreateContextMenuListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone,txtOrderAddress,txtOrderdate,orderName;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);
        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_address);
        txtOrderdate = (TextView)itemView.findViewById(R.id.order_date);
        orderName = (TextView)itemView.findViewById(R.id.order_name);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
        //itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
            }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (Common.spin.getSelectedIndex() == 2 || Common.spin.getSelectedIndex() == 3
                || Common.spin.getSelectedIndex() == 4 || Common.spin.getSelectedIndex() == 5
                || Common.spin.getSelectedIndex() == 6 || Common.spin.getSelectedIndex() == 7 || Common.spin.getSelectedIndex() == 8){
            //doNothing
        }
        else {
            menu.setHeaderTitle("Select an Action");

            menu.add(0, 0, getAdapterPosition(), "Update");
            //menu.add(0, 1, getAdapterPosition(), "Delete");
        }
    }

   /* @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }*/
}

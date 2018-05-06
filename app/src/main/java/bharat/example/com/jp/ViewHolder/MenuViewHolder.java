package bharat.example.com.jp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import bharat.example.com.jp.Common.Common;
import bharat.example.com.jp.Interface.ItemClickListener;
import bharat.example.com.jp.R;

/**
 * Created by bharat on 2/14/18.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnCreateContextMenuListener{


    public TextView menuname;
    public ImageView menuimage;
    public Button update,delete;

    private ItemClickListener itemClickListener;


    public MenuViewHolder(View itemView) {
        super(itemView);
        menuname = (TextView)itemView.findViewById(R.id.menu_name);
        menuimage = (ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select the action");

            contextMenu.add(0, 0, getAdapterPosition(), Common.UPDATE);
            contextMenu.add(0, 1, getAdapterPosition(), Common.DELETE);
            }


}
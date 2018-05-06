package bharat.example.com.jp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import bharat.example.com.jp.R;

/**
 * Created by bharat on 3/11/18.
 */

public class FeedBackViewHolder extends RecyclerView.ViewHolder {

   public TextView name,mobile,fname,rdate,ratenum,comment;
   public   RatingBar ratingBar;

    public FeedBackViewHolder(View itemView) {
        super(itemView);

        name = (TextView)itemView.findViewById(R.id.uname);
        mobile = (TextView)itemView.findViewById(R.id.mob);
        fname = (TextView)itemView.findViewById(R.id.fname);
        rdate = (TextView)itemView.findViewById(R.id.rDate);
        ratenum = (TextView)itemView.findViewById(R.id.numRate);
        comment = (TextView)itemView.findViewById(R.id.comment);
        ratingBar = (RatingBar) itemView.findViewById(R.id.rateBar);
    }
}

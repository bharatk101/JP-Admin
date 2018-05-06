package bharat.example.com.jp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import bharat.example.com.jp.ViewHolder.FeedBackViewHolder;
import bharat.example.com.jp.models.Ratings;

public class Feedback extends AppCompatActivity {

    private static final String TAG = "Feedback";

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView.LayoutManager layoutManager;

    String fid = "";
    TextView message;

    //FirebaseRecyclerAdapter<Ratings,FeedbackViewHolder> adapter;
    FirebaseRecyclerAdapter<Ratings, FeedBackViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Rating");

        recyclerView = (RecyclerView)findViewById(R.id.feedBacks);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadFeedBacks();
    }

    private void loadFeedBacks() {
        adapter = new FirebaseRecyclerAdapter<Ratings, FeedBackViewHolder>(
                Ratings.class,
                R.layout.feedback_layout,
                FeedBackViewHolder.class,
                reference

        ) {
            @Override
            protected void populateViewHolder(FeedBackViewHolder viewHolder, Ratings model, int position) {
                viewHolder.name.setText(model.getUserName());
                viewHolder.mobile.setText(model.getUserPhone());
                viewHolder.comment.setText(model.getComment());
                viewHolder.rdate.setText(model.getDateTime());
                viewHolder.ratenum.setText(model.getRateValue());
                viewHolder.fname.setText(model.getFoodName());
                viewHolder.ratingBar.setRating(Float.parseFloat(model.getRateValue()));




            }
        };
        recyclerView.setAdapter(adapter);
    }
}

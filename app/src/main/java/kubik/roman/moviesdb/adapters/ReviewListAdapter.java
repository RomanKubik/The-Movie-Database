package kubik.roman.moviesdb.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.models.movie_details.Review;

/**
 * Created by roman on 5/11/2016.
 */
public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private List<Review> mReviewList = new ArrayList<>();

    public ReviewListAdapter(List<Review> reviewList) {
        this.mReviewList = reviewList;
    }

    @Override
    public ReviewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewListAdapter.ViewHolder holder, int position) {
        Review review = mReviewList.get(position);

        holder.mTvAuthor.setPaintFlags(holder.mTvAuthor.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.mTvAuthor.setText(review.getAuthor());
        holder.mTvReview.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvAuthor;
        public TextView mTvReview;


        public ViewHolder(View itemView) {
            super(itemView);

            mTvAuthor = (TextView) itemView.findViewById(R.id.tv_review_author);
            mTvReview = (TextView) itemView.findViewById(R.id.tv_review_content);
        }
    }
}

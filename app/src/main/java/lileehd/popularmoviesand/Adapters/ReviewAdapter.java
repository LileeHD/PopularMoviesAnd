package lileehd.popularmoviesand.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lileehd.popularmoviesand.Models.Review;
import lileehd.popularmoviesand.R;
import lileehd.popularmoviesand.Utils.OnItemClickListener;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private Context mContext;
    private ArrayList<Review> mReviewList;
    private OnItemClickListener mListener;
    private TextView readMore;

    public ReviewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setmReviewList(ArrayList<Review> reviews) {
        mReviewList = reviews;
    }

    public Review getReviewFrom(int position){
        return this.mReviewList.get(position);
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reviewView = LayoutInflater.from(mContext).inflate(R.layout.review_item, parent, false);
        return new ReviewAdapter.ReviewViewHolder(reviewView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = mReviewList.get(position);
        String reviewUrl = currentReview.getmUrl();
        holder.mAuthor.setText(currentReview.getmAuthor());
        holder.mContent.setText((currentReview.getmContent()));
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView mAuthor;
        public TextView mContent;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.author);
            mContent = itemView.findViewById(R.id.content);
            readMore = itemView.findViewById(R.id.readMore);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onReviewClick(position);
                        }
                    }

                }
            });
        }
    }
}

package lileehd.popularmoviesand.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lileehd.popularmoviesand.Models.Movie;
import lileehd.popularmoviesand.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PosterViewHolder> {
    private Context mContext;
    private ArrayList<Movie> mMovieList;
    private OnItemClickListener mListener;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setmMovieList(ArrayList<Movie> movies) {
        this.mMovieList= movies;
    }

    public Movie getMovieFrom(int position) {
        return this.mMovieList.get(position);
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.poster_item, viewGroup, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder posterViewHolder, int i) {
        Movie currentMovie = mMovieList.get(i);
        String posterUrl = currentMovie.getPosterPath();
        Picasso.with(mContext)
                .load(posterUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(posterViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.movieThumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
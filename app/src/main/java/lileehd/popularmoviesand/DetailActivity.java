package lileehd.popularmoviesand;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lileehd.popularmoviesand.Adapters.ReviewAdapter;
import lileehd.popularmoviesand.Adapters.VideoAdapter;
import lileehd.popularmoviesand.Data.AppDatabase;
import lileehd.popularmoviesand.Data.AppExecutors;
import lileehd.popularmoviesand.Models.Movie;
import lileehd.popularmoviesand.Models.Review;
import lileehd.popularmoviesand.Models.Video;
import lileehd.popularmoviesand.Utils.AddFavViewModel;
import lileehd.popularmoviesand.Utils.AddMovieViewModelFactory;
import lileehd.popularmoviesand.Utils.JsonTask;
import lileehd.popularmoviesand.Utils.OnItemClickListener;
import lileehd.popularmoviesand.Utils.RequestHandler;
import lileehd.popularmoviesand.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity implements OnItemClickListener {
    //      TODO: the fav btn
//
//    TODO: view model

    ActivityDetailBinding detailBinding;
    private RequestQueue mRequestQueue;
    private ArrayList<Video> mVideos = new ArrayList<>();
    private ArrayList<Review> mReviews = new ArrayList<>();
    private Movie mMovie;
    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter = new ReviewAdapter(DetailActivity.this);
    private ImageView posterThumbnail;
    private TextView title;
    private TextView releaseDate;
    private TextView rating;
    private TextView overview;
    private RecyclerView trailer;
    private RecyclerView review;
    private Button mFavBtn;
    private AppDatabase db;
    public boolean movieIsFav;
    private static final int DEFAULT_MOVIE_ID = -1;
    private int mMovieId = DEFAULT_MOVIE_ID;
    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        if(savedInstanceState !=null&& savedInstanceState.containsKey(INSTANCE_TASK_ID)){
            mMovieId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_MOVIE_ID);
        }
//Views
        posterThumbnail = detailBinding.moviePosterDetail;
        title = detailBinding.movieTitle;
        releaseDate = detailBinding.movieReleaseDate;
        rating = detailBinding.movieRating;
        overview = detailBinding.movieOverview;
        trailer = detailBinding.trailerRecyclerView;
        review = detailBinding.reviewRecyclerView;
        mFavBtn = detailBinding.favBtn;

        db = AppDatabase.getInstance(getApplication());
//        Data from MDB api
        Intent intent = getIntent();
        mMovie = (Movie) intent.getParcelableExtra(getString(R.string.movie_parsing_key));

        setDetailBinding(mMovie);
        mRequestQueue = Volley.newRequestQueue(this);
        new RequestHandler();
        JsonObjectRequest videoRequest =
                requestVideos(getString(R.string.movie_db_base_url)
                        + mMovie.getId() + getString(R.string.api_label_and_key));
        mRequestQueue.add(videoRequest);
        favMovieViewModel(mMovieId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mMovieId);
        super.onSaveInstanceState(outState);
    }

    private void setDetailBinding(Movie movie) {
// movie detail
        Picasso.with(this)
                .load(Movie.IMG_BASE_URL + movie.getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fit()
                .centerInside()
                .into(posterThumbnail);
        title.setText(movie.getTitle());
        releaseDate.setText(movie.getReleaseDate());
        rating.setText(String.format(getString(R.string.from_10), String.valueOf(movie.getRating())));
        overview.setText(movie.getOverview());

// videos
        trailer.setHasFixedSize(true);
        if (DetailActivity.this.getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT) {
            trailer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            trailer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        }

//  reviews
        review.setHasFixedSize(true);
        if (DetailActivity.this.getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT) {
            review.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            review.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        }
    }

    public JsonObjectRequest requestVideos(String videoUrl) {
        JsonTask task = new JsonTask() {
            @Override
            public void handle(JSONObject json) throws JSONException {
                mVideoAdapter = new VideoAdapter(DetailActivity.this);
                JSONArray results = json.getJSONArray("results");
                mVideos.clear();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject videoInfo = results.getJSONObject(i);
                    Video video = new Video(videoInfo);
                    mVideos.add(i, video);
                    Log.v("VIDEO INFO", video.getKey());
                    requestReviews(getString(R.string.movie_db_base_url) + mMovie.getId() + getString(R.string.api_label_review__key));
                }
                trailer.setAdapter(mVideoAdapter);
                mVideoAdapter.setmVideoList(mVideos);
                mVideoAdapter.setOnItemClickListener(DetailActivity.this);
            }
        };
        return RequestHandler.getInstance().create(task, videoUrl);
    }

    private void requestReviews(String reviewUrl) {
        JsonTask taskR = new JsonTask() {
            @Override
            public void handle(JSONObject json) throws JSONException {
                mReviewAdapter = new ReviewAdapter(DetailActivity.this);
                JSONArray results = json.getJSONArray("results");
                mReviews.clear();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject reviewInfo = results.getJSONObject(i);
                    Review review = new Review(reviewInfo);
                    mReviews.add(i, review);
                }
                review.setAdapter(mReviewAdapter);
                mReviewAdapter.setmReviewList(mReviews);
                mReviewAdapter.setOnItemClickListener(DetailActivity.this);
            }
        };
        JsonObjectRequest request = RequestHandler.getInstance().create(taskR, reviewUrl);
        mRequestQueue.add(request);
    }

    private void favBtn() {
        String btnLabel = movieIsFav ? "Remove from fav" : "Add to fav";
        mFavBtn.setText(btnLabel);

    }

    @Override
    public void onVideoClick(int position) {
        Video videoClicked = mVideoAdapter.getVideoFrom(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoClicked.getKey()));
        startActivity(intent);
    }

    @Override
    public void onReviewClick(int position) {
        Review reviewClicked = mReviewAdapter.getReviewFrom(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse((reviewClicked.getmUrl())));
        startActivity(intent);
    }

    @Override
    public void onClickFavBtn() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (DetailActivity.this.movieIsFav) {
                    db.favmovieDao().delete(mMovie);
                    Log.v("favBtn", "favBtn clicked to delete");
                } else {
                    db.favmovieDao().insert(mMovie);
                    Log.v("favBtn", "favBtn clicked to add");
                }
                DetailActivity.this.movieIsFav = !DetailActivity.this.movieIsFav;
                Log.v("favBtn", "favBtn is clicked");
            }
        });
        String message = movieIsFav ? "deleted" : "added";
        Toast.makeText(DetailActivity.this, message, Toast.LENGTH_LONG).show();
        this.favBtn();
    }

    public void favMovieViewModel(int position) {
        if(mMovieId == DEFAULT_MOVIE_ID){
            AddMovieViewModelFactory factory = new AddMovieViewModelFactory(db, mMovieId);
            final AddFavViewModel viewModel = ViewModelProviders.of(this, factory).get(AddFavViewModel.class);
            viewModel.getMovie().observe(this, new Observer<Movie>() {
                @Override
                public void onChanged(Movie movie) {
                    viewModel.getMovie().removeObserver(this);
                    mFavBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickFavBtn();
                        }
                    });
                }
            });
        }
}
}


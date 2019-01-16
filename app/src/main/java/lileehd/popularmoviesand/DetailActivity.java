package lileehd.popularmoviesand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import lileehd.popularmoviesand.Adapters.VideoAdapter;
import lileehd.popularmoviesand.Models.Movie;
import lileehd.popularmoviesand.Models.Video;
import lileehd.popularmoviesand.Utils.HasVolleyQueue;
import lileehd.popularmoviesand.Utils.JsonTask;
import lileehd.popularmoviesand.Utils.RequestHandler;
import lileehd.popularmoviesand.databinding.ActivityDetailBinding;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements HasVolleyQueue {
    //      TODO: the fav btn
//    TODO : the trailers and reviews views
//    TODO: view model

    ActivityDetailBinding detailBinding;
    private RequestQueue mRequestQueue;
    private VideoAdapter mVideoAdapter = new VideoAdapter(DetailActivity.this);
    private ArrayList<Video>mVideos = new ArrayList<>();
    boolean mFavBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        new RequestHandler(this);
        mRequestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(getString(R.string.movie_parsing_key));
        requestVideos("https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=e9a4d258ab2f1609f16bd29d0eef3719");
        setDetailBinding(movie);
//        favBtnSwitch();
    }

    private void setDetailBinding(Movie movie) {
        Picasso.with(this)
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .fit()
                .centerInside()
                .into(detailBinding.moviePosterDetail);
        detailBinding.movieTitle.setText(movie.getTitle());
        detailBinding.movieReleaseDate.setText(movie.getReleaseDate());
        detailBinding.movieRating.setText(String.format(getString(R.string.from_10), String.valueOf(movie.getRating())));
        detailBinding.movieOverview.setText(movie.getOverview());

// videos
        detailBinding.trailerRecyclerView.setHasFixedSize(true);
        if(DetailActivity.this.getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT){
            detailBinding.trailerRecyclerView
                    .setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }else{
            detailBinding.trailerRecyclerView
                    .setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        }
//        mVideoAdapter.setmVideoList(this.mVideos);
//        detailBinding.trailerRecyclerView.setAdapter(mVideoAdapter);

    }
    public void requestVideos(String url) {
        JsonTask task = new JsonTask() {
            @Override
            public void handle(JSONObject json) throws JSONException {
                JSONArray results = json.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject videoInfo = results.getJSONObject(i);
                    Video video = new Video(videoInfo);
                    mVideos.add(video);
                    Log.v("VIDEO INFO", video.getName());
                }
                mVideoAdapter.setmVideoList(mVideos);
                detailBinding.trailerRecyclerView.setAdapter(mVideoAdapter);

            }
        };
        RequestHandler.getInstance().create(task, url);
    }

    @Override
    public void addToQueue(JsonObjectRequest request) {
        mRequestQueue.add(request);
    }

//    private void favBtnSwitch(){
//        if(mFavBtn){
//            detailBinding.favBtn.setText("Delete from favz");
//        }else
//            detailBinding.favBtn.setText(R.string.button_fav);
//    }

}

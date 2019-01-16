package lileehd.popularmoviesand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import lileehd.popularmoviesand.Adapters.MovieAdapter;
import lileehd.popularmoviesand.Models.Movie;
import lileehd.popularmoviesand.Utils.HasVolleyQueue;
import lileehd.popularmoviesand.Utils.JsonTask;
import lileehd.popularmoviesand.Utils.MovieDbUrlBuilder;
import lileehd.popularmoviesand.Utils.RequestHandler;
import lileehd.popularmoviesand.databinding.ActivityMainBinding;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HasVolleyQueue, MovieAdapter.OnItemClickListener {

    private RequestQueue mRequestQueue;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private MovieAdapter mMovieAdapter;
    ActivityMainBinding mainBinding;
    protected MovieDbUrlBuilder movieDbURLBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        new RequestHandler(this);
        mRequestQueue = Volley.newRequestQueue(this);
        movieDbURLBuilder = new MovieDbUrlBuilder();
        requestMovies(movieDbURLBuilder.sortBy("popular").getUrl());
        setMainBinding();
    }
    private void setMainBinding() {
        mainBinding.recyclerView.setHasFixedSize(true);
        if (MainActivity.this.getResources().getConfiguration()
                .orientation == Configuration.ORIENTATION_PORTRAIT) {
            mainBinding.recyclerView
                    .setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } else {
            mainBinding.recyclerView
                    .setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }
    }

    @Override
    public void addToQueue(JsonObjectRequest request) {
        mRequestQueue.add(request);
    }

//    public void addMovie(Movie movie) {
//        this.mMovies.add(movie);
//    }
//
//    public ArrayList<Movie> getMovies() {
//        return this.mMovies;
//    }

    public void requestMovies(String url) {
        JsonTask task = new JsonTask() {
            @Override
            public void handle(JSONObject json) throws JSONException {
                mMovieAdapter = new MovieAdapter(MainActivity.this);
                JSONArray results = json.getJSONArray("results");
                mMovies.clear();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieInfo = results.getJSONObject(i);
                    Movie movie = new Movie(movieInfo);
                    mMovies.add(movie);
                    Log.v("MOVIE INFO", movie.getTitle());
                }
                mMovieAdapter.setmMovieList(mMovies);
                mMovieAdapter.setOnItemClickListener(MainActivity.this);
                mainBinding.recyclerView.setAdapter(mMovieAdapter);
            }
        };
        RequestHandler.getInstance().create(task, url);
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Movie movieClicked = mMovieAdapter.getMovieFrom(position);
        intent.putExtra(getString(R.string.movie_parsing_key), movieClicked);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean sortBy(String sort) {
        String url = movieDbURLBuilder.sortBy(sort).getUrl();
        Log.v("SORTBY", sort);
        Log.v("SORTBY", url);
        requestMovies(url);
        Toast.makeText(getApplicationContext(), sort, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                setTitle(R.string.most_popular);
                return this.sortBy(getString(R.string.popular_key));
            case R.id.top_rated:
                setTitle(R.string.top_rated);
                return this.sortBy(getString(R.string.top_rated_key));
//                TODO: Favorites
            case R.id.favorites:
                setTitle(R.string.favorites_db);
                return this.sortBy(getString(R.string.favorites_db));
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}

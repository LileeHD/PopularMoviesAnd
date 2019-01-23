package lileehd.popularmoviesand;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lileehd.popularmoviesand.Adapters.MovieAdapter;
import lileehd.popularmoviesand.Data.AppDatabase;
import lileehd.popularmoviesand.Models.Movie;
import lileehd.popularmoviesand.Utils.JsonTask;
import lileehd.popularmoviesand.Utils.MovieDbUrlBuilder;
import lileehd.popularmoviesand.Utils.MovieViewModel;
import lileehd.popularmoviesand.Utils.RequestHandler;
import lileehd.popularmoviesand.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {

    private RequestQueue mRequestQueue;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private List<Movie> mFavMovies;
    private MovieAdapter mMovieAdapter;
//    private MovieAdapter mFavAdapter;
    ActivityMainBinding mainBinding;
    protected MovieDbUrlBuilder movieDbURLBuilder;
    private RecyclerView mRecyclerView;

    private Parcelable recyclerViewState;
    private Movie mMovie;
    private AppDatabase mDb;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMovieAdapter = new MovieAdapter(MainActivity.this);
        mDb = AppDatabase.getInstance(getApplicationContext());
        new RequestHandler();
        setMainBinding();
        mRequestQueue = Volley.newRequestQueue(this);
        movieDbURLBuilder = new MovieDbUrlBuilder();
        requestMovies(movieDbURLBuilder.sortBy("popular").getUrl());
        setupViewModel();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("movie", mMovie);
        Log.v("INSTANCE_STATE", "save");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("INSTANCE_STATE", "restore");
        mMovie = savedInstanceState.getParcelable("movie");
//        mRecyclerView = savedInstanceState.getParcelable("view");

    }

    private void setMainBinding() {
        mRecyclerView = mainBinding.recyclerView;
        mRecyclerView.setHasFixedSize(true);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }
        mRecyclerView.setAdapter(mMovieAdapter);
        mMovieAdapter.notifyDataSetChanged();

    }

    public void requestMovies(String url) {
        JsonTask task = new JsonTask() {
            @Override
            public void handle(JSONObject json) throws JSONException {
                JSONArray results = json.getJSONArray("results");
                mMovies.clear();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieInfo = results.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.jsonHydrate(movieInfo);
//                    .setExtras()
                    mMovies.add(movie);
                    Log.v("MOVIE INFO", String.valueOf(movie.getId()));
                }
                mRecyclerView.setAdapter(mMovieAdapter);
                mMovieAdapter.setMovieList(mMovies);
                mMovieAdapter.setOnItemClickListener(MainActivity.this);
            }
        };
        JsonObjectRequest request = RequestHandler.getInstance().create(task, url);
        mRequestQueue.add(request);
        Log.v("REQUEST MOVIE", "is requested");
    }

    //    Favorites
    private void setupViewModel() {
        MovieViewModel viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getAllFavs().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> favMovies) {
                mMovieAdapter.setMovieList((ArrayList<Movie>) favMovies);
                mRecyclerView.setAdapter(mMovieAdapter);
                Log.v("ONCHANGE", "fav is changed");
            }
        });
        Log.v("VIEWMODEL", "view model");
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Movie movieClicked = mMovieAdapter.getMovieFrom(position);
        intent.putExtra(getString(R.string.movie_parsing_key), movieClicked);
        startActivity(intent);
        Log.v("onItemClick", "intent");
    }

    //    Menu
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                setTitle(R.string.most_popular);
                Log.v("ISCLICKED", "popular");
                return this.sortBy(getString(R.string.popular_key));
            case R.id.top_rated:
                setTitle(R.string.top_rated);
                Log.v("ISCLICKED", "top rated");
                return this.sortBy(getString(R.string.top_rated_key));
            case R.id.favorites:
                setTitle(R.string.favorites_db);
                setupViewModel();
                Log.v("ISCLICKED", "fav");
                return true;
            default:
                Log.v("ISCLICKED", "any choice");
                return super.onOptionsItemSelected(item);
        }
    }

}

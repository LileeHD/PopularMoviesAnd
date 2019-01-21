//package lileehd.popularmoviesand;
//
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import lileehd.popularmoviesand.Adapters.MovieAdapter;
//import lileehd.popularmoviesand.Data.AppDatabase;
//import lileehd.popularmoviesand.Data.AppExecutors;
//import lileehd.popularmoviesand.Models.Movie;
//import lileehd.popularmoviesand.databinding.ActivityFavBinding;
//
//public class FavActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {
//
//    private RecyclerView favRecyclerView;
//    ActivityFavBinding binding;
//    private MovieAdapter adapter;
//    private List<Movie> mFavMovies = new ArrayList<>();
//    private AppDatabase mDb;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_fav);
//
//        adapter = new MovieAdapter(this);
//        favRecyclerView = binding.recyclerViewFav;
//        setViews();
//        favRecyclerView.setAdapter(adapter);
//        favRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//
//        setFavList();
//    }
//    private void setViews(){
//        favRecyclerView.setHasFixedSize(true);
//        if (FavActivity.this.getResources().getConfiguration()
//                .orientation == Configuration.ORIENTATION_PORTRAIT) {
//            favRecyclerView
//                    .setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//        } else {
//            favRecyclerView
//                    .setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
//        }
//        adapter.setOnItemClickListener(FavActivity.this);
//    }
//
//    private void setFavList(){
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                mDb = AppDatabase.getInstance(getApplicationContext());
//                mFavMovies = mDb.favmovieDao().getFavMovieList();
//                FavActivity.this.adapter.setMovieList((ArrayList<Movie>) mFavMovies);
//                for(Movie movie : mFavMovies) {
//                    Log.v("FAV", movie.getPosterPath());
//                }
//            }
//        });
//    }
//    @Override
//    public void onItemClick(int position) {
//        Intent intent = new Intent(FavActivity.this, DetailActivity.class);
//        Movie movieClicked = adapter.getMovieFrom(position);
//        intent.putExtra(getString(R.string.movie_parsing_key), movieClicked);
//        startActivity(intent);
//    }
//}

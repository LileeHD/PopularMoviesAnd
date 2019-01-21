package lileehd.popularmoviesand.Utils;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import lileehd.popularmoviesand.Data.AppDatabase;
import lileehd.popularmoviesand.Models.Movie;

public class MovieViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> mFavMovies;
    public static final String RIVER = MovieViewModel.class.getSimpleName();

    public MovieViewModel(@NonNull Application application) {
        super(application);
        AppDatabase mDb = AppDatabase.getInstance(this.getApplication());
        Log.v(RIVER, "Actively retrieving movies from database");
        mFavMovies = mDb.favmovieDao().getFavMovieList();
    }
    public LiveData<List<Movie>> getAllFavs(){
        return mFavMovies;
    }

}

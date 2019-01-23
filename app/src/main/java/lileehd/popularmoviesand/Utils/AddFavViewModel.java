package lileehd.popularmoviesand.Utils;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import lileehd.popularmoviesand.Data.AppDatabase;
import lileehd.popularmoviesand.Models.Movie;

public class AddFavViewModel extends ViewModel {
    private LiveData<Movie>movie;
    private static final String TAG = AddFavViewModel.class.getSimpleName();

    public AddFavViewModel(AppDatabase database, int movieId) {
        movie= database.favmovieDao().loadByMovieId(movieId);
        Log.d(TAG, "Add the movie to the DataBase");
    }

    public LiveData<Movie>getMovie(){
        return movie;
    }
}

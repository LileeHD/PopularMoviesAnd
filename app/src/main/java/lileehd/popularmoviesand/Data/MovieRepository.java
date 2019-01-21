//package lileehd.popularmoviesand.Data;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//import androidx.lifecycle.LiveData;
//import lileehd.popularmoviesand.Models.Movie;
//
//public class MovieRepository {
//    private FavMovieDao favMovieDao;
//    private LiveData<List<Movie>> favMovies;
//
//    public MovieRepository(Application application) {
//        AppDatabase database = AppDatabase.getInstance(application);
//        favMovieDao = database.favmovieDao();
//        favMovies = favMovieDao.getFavMovieList();
//    }
//
//    public void insert(Movie movie){
//        new InsertMovieAsyncTask(favMovieDao).execute(movie);
//    }
//
//    public LiveData<List<Movie>>getFavMovies(){
//        return favMovies;
//    }
//
//    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void>{
//        private FavMovieDao favMovieDao;
//
//        private InsertMovieAsyncTask(FavMovieDao favMovieDao) {
//            this.favMovieDao = favMovieDao;
//        }
//
//        @Override
//        protected Void doInBackground(Movie... movies) {
//            favMovieDao.insert(movies[0]);
//            return null;
//        }
//    }
//}

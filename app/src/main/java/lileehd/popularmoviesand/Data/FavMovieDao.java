package lileehd.popularmoviesand.Data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import lileehd.popularmoviesand.Models.Movie;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface FavMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Query("SELECT COUNT(*) FROM movies WHERE id = :id")
    int movieCount(int id);

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie>loadByMovieId(int id);

    @Query("SELECT * FROM movies" )
    LiveData<List<Movie>> LoadFavMovies();

    @Delete
    void delete(Movie movie);

}

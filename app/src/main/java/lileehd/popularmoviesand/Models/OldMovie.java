//package lileehd.popularmoviesand.Models;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//
//@Entity(tableName = "movies")
//public class Movie implements Serializable {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//    private String title;
//    private String posterPath;
//    private String releaseDate;
//    private int rating;
//    private String overview;
//
//    public static String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";
//
//    public void jsonHydrate(JSONObject info) throws JSONException {
//        this.id = info.getInt("id");
//        this.posterPath = info.getString("poster_path");
//        this.title = info.getString("title");
//        this.releaseDate = info.getString("release_date");
//        this.rating = info.getInt("vote_average");
//        this.overview = info.getString("overview");
//    }
//
//    public void setId(int mId) {
//        this.id = mId;
//    }
//
//    public void setTitle(String mTitle) {
//        this.title = mTitle;
//    }
//
//    public void setPosterPath(String mPosterPath) {
//        this.posterPath = mPosterPath;
//    }
//
//    public void setReleaseDate(String mReleaseDate) {
//        this.releaseDate = mReleaseDate;
//    }
//
//    public void setRating(int mRating) {
//        this.rating = mRating;
//    }
//
//    public void setOverview(String mOverview) {
//        this.overview = mOverview;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getPosterPath() {
//        return posterPath;
//    }
//
//    public String getReleaseDate() {
//        return releaseDate;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public String getOverview() {
//        return overview;
//    }
//
//
//}

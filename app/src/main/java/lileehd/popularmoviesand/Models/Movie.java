package lileehd.popularmoviesand.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import lileehd.popularmoviesand.Adapters.VideoAdapter;
import lileehd.popularmoviesand.Utils.JsonTask;
import lileehd.popularmoviesand.Utils.RequestHandler;
import lileehd.popularmoviesand.databinding.ActivityDetailBinding;

//@Entity(tableName = "movieTable")
public class Movie implements Serializable {
    //    @PrimaryKey(autoGenerate = true)
    private int mId;
    private String mTitle;
    private String mPosterPath;
    private String mReleaseDate;
    private int mRating;
    private ArrayList<Video> mVideos = new ArrayList<>();
//    VideoAdapter mVideoAdapter;
//    ActivityDetailBinding activityDetailBinding;
    //    TODO proper datatype for the rating
    private String mOverview;
    private String IMG_BASE_URL = "https://image.tmdb.org/t/p/w500";

    public Movie(JSONObject info) throws JSONException {
        this.mId = info.getInt("id");
        this.mPosterPath = info.getString("poster_path");
        this.mTitle = info.getString("title");
        this.mReleaseDate = info.getString("release_date");
        this.mRating = info.getInt("vote_average");
        this.mOverview = info.getString("overview");
//        this.mVideos = requestVideos("https://api.themoviedb.org/3/movie/" + this.mId + "/videos?api_key=e9a4d258ab2f1609f16bd29d0eef3719");
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterPath() {
        return IMG_BASE_URL + mPosterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public int getRating() {
        return mRating;
    }

    public String getOverview() {
        return mOverview;
    }

    public ArrayList<Video> getmVideos() {
        return mVideos;
    }

//    public ArrayList<Video> requestVideos(String url) {
//        JsonTask task = new JsonTask() {
//            @Override
//            public void handle(JSONObject json) throws JSONException {
//                JSONArray results = json.getJSONArray("results");
//                for (int i = 0; i < results.length(); i++) {
//                    JSONObject videoInfo = results.getJSONObject(i);
//                    Video video = new Video(videoInfo);
//                    mVideos.add(video);
//                    Log.v("VIDEO INFO", video.getName());
//                }
//            }
//        };
//        RequestHandler.getInstance().create(task, url);
//        return mVideos;
//    }


    //    public void requestReviews(String url) {
//        JsonTask task = new JsonTask() {
//            @Override
//            public void handle(JSONObject json) throws JSONException {
//                JSONArray results = json.getJSONArray("results");
//                for (int i = 0; i < results.length(); i++) {
//                    JSONObject reviewInfo = results.getJSONObject(i);
//                    Review review = new Review(reviewInfo);
//                    mVideos.add(video);
//                    Log.v("REVIEW INFO", review.getName());
//                }
//            }
//        };
//        RequestHandler.getInstance().create(task, url);
//    }

}

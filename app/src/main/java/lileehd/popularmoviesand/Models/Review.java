package lileehd.popularmoviesand.Models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Review implements Serializable {

    private String mAuthor;
    private String mContent;
    private String mId;
    private String mUrl;

    public Review(JSONObject reviewInfo) throws JSONException {

        this.mAuthor = reviewInfo.getString("author");
        this.mContent = reviewInfo.getString("content");
        this.mId = reviewInfo.getString("id");
        this.mUrl = reviewInfo.getString("url");

        Log.v("REVIEW INFO", String.valueOf(reviewInfo));
    }

    public String getmId() {
        return mId;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmUrl() {
        return mUrl;
    }
}

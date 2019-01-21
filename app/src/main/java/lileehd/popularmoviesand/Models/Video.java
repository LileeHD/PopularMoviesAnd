package lileehd.popularmoviesand.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Video implements Serializable {
    private String mId;
    private String mKey;
    private String mName;
    private String VIDEO_BASE_URL = "https://www.youtube.com/watch?v=";

    public Video(JSONObject videoInfo) throws JSONException {
        this.mId = videoInfo.getString("id");
        this.mKey = videoInfo.getString("key");
        this.mName = videoInfo.getString("name");
    }

    public String getId() {
        return mId;
    }

    public String getKey() {
        return VIDEO_BASE_URL + mKey;
    }

    public String getName() {
        return mName;
    }


}


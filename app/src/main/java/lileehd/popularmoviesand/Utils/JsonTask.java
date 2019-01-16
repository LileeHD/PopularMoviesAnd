package lileehd.popularmoviesand.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonTask {
    void handle(JSONObject json) throws JSONException;
}

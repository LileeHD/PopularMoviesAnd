package lileehd.popularmoviesand.Utils;

import com.android.volley.toolbox.JsonObjectRequest;

public interface HasVolleyQueue {
    void addToQueue(JsonObjectRequest request);
}

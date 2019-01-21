package lileehd.popularmoviesand.Utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler {

    public JsonTask task;

    private static RequestHandler mInstance;

    public RequestHandler() {
        mInstance = this;
    }

    public static synchronized RequestHandler getInstance() {
        return mInstance;
    }

    public JsonObjectRequest create(JsonTask task, String url) {
        this.task = task;

        return new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    RequestHandler.this.task.handle(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

    }
}

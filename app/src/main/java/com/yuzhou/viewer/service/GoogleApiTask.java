package com.yuzhou.viewer.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.common.eventbus.EventBus;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.yuzhou.viewer.R;
import com.yuzhou.viewer.model.GoogleImage;
import com.yuzhou.viewer.model.GoogleImageFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhou on 2015/08/02.
 */
public class GoogleApiTask extends AsyncTask<ApiParam, Integer, List<GoogleImage>>
{
    private final SyncHttpClient client = new SyncHttpClient();
    private final List<GoogleImage> items = new ArrayList<>();
    private final EventBus eventBus;
    private final Context context;
    private int errorResource;

    public GoogleApiTask(EventBus eventBus, Context context)
    {
        this.eventBus = eventBus;
        this.context = context;
    }

    private List<GoogleImage> interExecute(ApiParam request)
    {
        Log.d("VIEWER", request.toString());
        client.get(request.getUrl(), request.getParams(), new JsonHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response)
            {
                Log.i("VIEWER", "status code=" + statusCode + ", response=" + response + ", error=" + throwable.getMessage());
                errorResource = R.string.error_unavailable_network;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                if (response == null) {
                    Log.i("VIEWER", "Response no context");
                    errorResource = R.string.error_server_side;
                    return;
                }

                try {
                    int httpCode = response.getInt("responseStatus");
                    if (httpCode == 400) {
                        errorResource = R.string.error_data_not_found;
                        Log.d("VIEWER", "response=" + response.getString("responseDetails"));
                        return;
                    }
                    if (httpCode != 200) {
                        errorResource = R.string.error_server_side;
                        Log.d("VIEWER", "response=" + response.getString("responseDetails"));
                        return;
                    }

                    List<GoogleImage> images = GoogleImageFactory.create(response);
                    if (images.isEmpty()) {
                        Log.i("VIEWER", "Can not parse JSON");
                        Log.d("VIEWER", "response=" + response.toString());
                        errorResource = R.string.error_server_side;
                        return;
                    }
                    items.addAll(images);
                } catch (JSONException e) {
                    Log.i("VIEWER", "Can not parse JSON");
                    e.printStackTrace();
                }
            }
        });
        return items;
    }

    @Override
    protected List<GoogleImage> doInBackground(ApiParam... requests)
    {
        ApiParam request = requests[0];
        return interExecute(request);
    }

    @Override
    protected void onPreExecute()
    {
        items.clear();
    }

    @Override
    protected void onPostExecute(List<GoogleImage> googleImages)
    {
        if (errorResource > 0) {
            Toast.makeText(context, errorResource, Toast.LENGTH_LONG).show();
        }
        eventBus.post(items);
    }

}
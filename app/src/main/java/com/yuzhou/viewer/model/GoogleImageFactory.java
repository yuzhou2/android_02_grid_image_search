package com.yuzhou.viewer.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhou on 2015/08/03.
 */
public class GoogleImageFactory
{
    private GoogleImageFactory()
    {
    }

    public static final List<GoogleImage> create(JSONObject response)
    {
        List<GoogleImage> result = new ArrayList<>();
        try {
            JSONArray jsnArray = response.getJSONObject("responseData").getJSONArray("results");
            for (int i = 0, n = jsnArray.length(); i < n; i++) {
                JSONObject jsn = jsnArray.getJSONObject(i);
                GoogleImage image = new GoogleImage();
                image.setTitleNoFormatting(jsn.getString("titleNoFormatting"));
                image.setTbUrl(jsn.getString("tbUrl"));
                image.setTbWidth(jsn.getInt("tbWidth"));
                image.setTbHeight(jsn.getInt("tbHeight"));
                result.add(image);
            }
        } catch (JSONException e) {
            Log.i("VIEWER", "can not parse JSON data. error=" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
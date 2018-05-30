package com.vynaloze.smartmirror.model.weather.web;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vynaloze.smartmirror.util.ApplicationContextProvider;

import org.json.JSONObject;

public class ForecastRequester {
    private static final String TAG = "ForecastRequester";
    private static final String URL = "https://api.darksky.net/forecast/285d2f7ba2b4c6a886a3356737900fb9/51.750000,19.466670";
    private static final String URL2 = "https://api.darksky.net/forecast/285d2f7ba2b4c6a886a3356737900fb9/25.2994,91.5808";      //rainy india...
    private RequestQueue queue;

    public ForecastRequester() {
        queue = Volley.newRequestQueue(ApplicationContextProvider.getContext());
    }

    public void request(VolleyCallback callback) {
//        String URL = new Random().nextInt()%2==0 ? ForecastRequester.URL : URL2;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }
}

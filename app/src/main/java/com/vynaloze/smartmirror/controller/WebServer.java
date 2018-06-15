package com.vynaloze.smartmirror.controller;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.vynaloze.smartmirror.util.ApplicationContextProvider;

import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

import static android.content.Context.WIFI_SERVICE;

public class WebServer extends NanoHTTPD {
    private static final int PORT = 8180;
    private Activity mainActivity;

    public WebServer(Context context) {
        super(PORT);
        this.mainActivity = (Activity) context;
    }

    public String getIpAddress() {
        WifiManager wm = (WifiManager) ApplicationContextProvider.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip + ":" + PORT;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, List<String>> params = session.getParameters();

        if (uri.equals("/weatherLocation")) {

            mainActivity.runOnUiThread(() -> mainActivity.recreate());
        }

        return newFixedLengthResponse(Response.Status.OK, "text/plain", null);
    }
}

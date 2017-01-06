package com.example.monicamarcus.processingjsondata;

import android.app.IntentService;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherBroadcastService extends IntentService {
    public static final String myURLpart1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String myURLpart2 = ",usa&appid=15f7a3f5636821a266802cf05999a2a5";
    private String jsonResult;

    public WeatherBroadcastService() {
        super("WeatherBroadcastService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String location = intent.getStringExtra("location");
        jsonResult = getJsonData(myURLpart1, location, myURLpart2);
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        intent1.putExtra("jsonData", jsonResult);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }

    private String getJsonData(String urlString1, String s, String urlString2) {
        String result = "";
        if (s != null && s.length() > 0) {
            try {
                s = URLEncoder.encode(s, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(urlString1 + s + urlString2);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                int oneChar = 0;
                while ((oneChar = reader.read()) != -1) {
                    result += (char) oneChar;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            result = null;
        }
        return result;
    }
}
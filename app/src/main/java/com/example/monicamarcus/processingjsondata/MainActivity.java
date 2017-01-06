package com.example.monicamarcus.processingjsondata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    //public static final String myURL = "http://api.openweathermap.org/data/2.5/weather?q=San%20Francisco,usa&appid=15f7a3f5636821a266802cf05999a2a5";
    private TextView textView;
    private EditText editText;
    private Button button;
    private Button buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        buttonClear = (Button) findViewById(R.id.buttonClear);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent1 = getIntent();
        if (intent1 != null) {
            String jsonData = intent1.getStringExtra("jsonData");
            if (jsonData != null) {
                extractInfo(jsonData,textView);
            } else {
                Log.d("MainActivity onResume", "null jsonData");
            }
        }

    }

    public void getWeatherBroadcast(View view) {
        Intent intent = new Intent(this, WeatherBroadcastService.class);
        intent.putExtra("location", editText.getText().toString());
        startService(intent);
    }


    public void clearAll(View view) {
        editText.setText("");
        textView.setText(getResources().getString(R.string.title));
    }

    private void extractInfo(String s, TextView textView) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String name = jsonObject.getString("name");
            textView.setText(textView.getText() + " for " + name + ":");
            String weatherInfo = jsonObject.getString("weather");

            JSONArray jsonArray = new JSONArray(weatherInfo);
            String main, description;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPart = jsonArray.getJSONObject(i);
                main = jsonPart.getString("main");
                description = jsonPart.getString("description");
                textView.setText(textView.getText() + "\n" + main + "\n\t" + description);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

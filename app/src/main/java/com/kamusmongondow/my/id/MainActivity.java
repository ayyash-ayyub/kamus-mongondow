package com.kamusmongondow.my.id;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView bannerImage = findViewById(R.id.bannerImage);
        EditText inputText = findViewById(R.id.inputText);
        Button translateButton = findViewById(R.id.translateButton);
        TextView resultText = findViewById(R.id.resultText);

        String jsonString = loadJSONFromAsset();
        JSONObject dictionary = null;
        try {
            dictionary = new JSONObject(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JSONObject finalDictionary = dictionary;
        translateButton.setOnClickListener(v -> {
            String word = inputText.getText().toString().trim().toLowerCase();
            String translation = finalDictionary.optString(word, "Tidak ditemukan");
            resultText.setText(translation);
        });
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("dictionary.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return json;
    }
}

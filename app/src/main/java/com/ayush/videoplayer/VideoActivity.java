package com.ayush.videoplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoActivity extends AppCompatActivity {
    HashMap<String, ArrayList<String>> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        String folder = intent.getStringExtra("folder");
        String number = intent.getStringExtra("number");
        number = number.substring(0, number.indexOf(' '));
        hashMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("map");
        Log.e("this", "" + hashMap.size());
    }
}

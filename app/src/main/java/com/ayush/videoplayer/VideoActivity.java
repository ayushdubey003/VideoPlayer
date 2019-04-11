package com.ayush.videoplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        String folder = getIntent().getStringExtra("folder");
        String number = getIntent().getStringExtra("number");
        number=number.substring(0,number.indexOf(' '));
        Toast.makeText(VideoActivity.this, folder + " " + number, Toast.LENGTH_LONG).show();
    }
}

package com.ayush.videoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        VideoView videoView = (VideoView) findViewById(R.id.videoview);
        String filepath = getIntent().getStringExtra("address");
        videoView.setVideoPath(filepath);
        //videoView.setMediaController(new MediaController(this));
        videoView.start();
    }
}

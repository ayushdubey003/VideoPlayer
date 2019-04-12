package com.ayush.videoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        final VideoView videoView = (VideoView) findViewById(R.id.videoview);
        String filepath = getIntent().getStringExtra("address");
        videoView.setVideoPath(filepath);
        videoView.setMediaController(new MediaController(this));
        videoView.start();
        /*PlayerView playerView = (PlayerView) findViewById(R.id.videoview);
        playerView.getPlayer();
        playerView.*/
    }
}

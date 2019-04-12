package com.ayush.videoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoViewActivity extends AppCompatActivity {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    private boolean playWhenReady = true;
    private long playbackPosition;
    private int currentWindow;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        path = getIntent().getStringExtra("address");
        path = "file://" + path;
        playerView = findViewById(R.id.playVideo);
        playbackPosition = 0;
        currentWindow = 0;
    }

    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        Uri uri = Uri.parse(path);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
        if (Util.SDK_INT <= 23 || player == null)
            initializePlayer();
    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23)
            initializePlayer();
    }

    private MediaSource buildMediaSource(Uri uri) {
        String playerInfo = Util.getUserAgent(this, "ExoPlayerInfo");
        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(this, playerInfo)).
                createMediaSource(uri);
    }
}

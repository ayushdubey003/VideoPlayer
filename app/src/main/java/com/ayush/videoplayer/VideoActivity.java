package com.ayush.videoplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VideoActivity extends AppCompatActivity {
    HashMap<String, ArrayList<String>> hashMap;
    private VideoAdapter mVideoAdapter;
    private ArrayList<Video> videoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        String folder = intent.getStringExtra("folder");
        String number = intent.getStringExtra("number");
        number = number.substring(0, number.indexOf(' '));
        hashMap = (HashMap<String, ArrayList<String>>) intent.getSerializableExtra("map");
        ArrayList<String> arrayList = new ArrayList<>();
        String s = " ";
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            s = pair.getKey().toString();
            arrayList = (ArrayList<String>) pair.getValue();
            int size = arrayList.size();
            if (s.contains(folder) && size == Integer.parseInt(number)) {
                break;
            }
        }
        mVideoAdapter = new VideoAdapter(VideoActivity.this, 0, videoArrayList);
        ListView listView = (ListView) findViewById(R.id.videolist);
        for (int i = 0; i < arrayList.size(); i++)
            mVideoAdapter.add(new Video(R.drawable.ic_folder_black_24dp, arrayList.get(i)));
        listView.setAdapter(mVideoAdapter);
    }
}
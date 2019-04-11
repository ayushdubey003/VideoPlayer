package com.ayush.videoplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ArrayList<File> arrayList = new ArrayList<>();
    ArrayList<Folder> list = new ArrayList<>();

    private FolderAdapter mArrayAdapter;
    public HashMap<String, ArrayList<String>> hashMap;
    private final int PERMISSIONS = 44;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tohide);
                    TextView textView = (TextView) findViewById(R.id.textt);
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "font.ttf");
                    textView.setTypeface(typeface);
                    final ListView listView = (ListView) findViewById(R.id.list);
                    linearLayout.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            linearLayout.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                        }
                    }, 4000);
                    File root = Environment.getExternalStorageDirectory();
                    String rootPath = root.getPath();
                    File[] file = root.listFiles();
                    dfs(file);
                    mArrayAdapter = new FolderAdapter(MainActivity.this, 0, list);
                    hashMap = new HashMap<>();
                    HashSet<String> hashSet = new HashSet<>();
                    for (int i = 0; i < arrayList.size(); i++) {
                        String s = arrayList.get(i).toString();
                        int ind = s.lastIndexOf('/');
                        String videoName = s.substring(ind + 1);
                        String s1 = s.substring(0, ind);
                        int ind1 = s1.lastIndexOf('/');
                        String folderName = s1.substring(0, ind1);
                        hashSet.add(s.substring(0, ind));
                        if (hashMap.get(s.substring(0, ind)) == null) {
                            ArrayList<String> a = new ArrayList<>();
                            a.add(videoName);
                            hashMap.put(s.substring(0, ind), a);
                        } else
                            hashMap.get(s.substring(0, ind)).add(videoName);
                    }
                    Iterator iterator = hashSet.iterator();
                    mArrayAdapter.getMap(hashMap);
                    while (iterator.hasNext()) {
                        String s = iterator.next().toString();
                        ArrayList<String> a = hashMap.get(s);
                        s = s.substring(s.lastIndexOf('/') + 1);
                        if (s.equals("0"))
                            s = "Internal Memory";
                        mArrayAdapter.add(new Folder(R.drawable.ic_folder_black_24dp, s, a.size()));
                    }
                    listView.setAdapter(mArrayAdapter);
                } else
                    Toast.makeText(this, "Sorry", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.list);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tohide);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            TextView textView = (TextView) findViewById(R.id.textt);
            Typeface typeface = Typeface.createFromAsset(getAssets(), "font.ttf");
            textView.setTypeface(typeface);
            linearLayout.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    linearLayout.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            }, 4000);
            File root = Environment.getExternalStorageDirectory();
            String rootPath = root.getPath();
            File[] file = root.listFiles();
            dfs(file);
            mArrayAdapter = new FolderAdapter(MainActivity.this, 0, list);
            hashMap = new HashMap<>();
            HashSet<String> hashSet = new HashSet<>();
            for (int i = 0; i < arrayList.size(); i++) {
                String s = arrayList.get(i).toString();
                int ind = s.lastIndexOf('/');
                String videoName = s.substring(ind + 1);
                String s1 = s.substring(0, ind);
                int ind1 = s1.lastIndexOf('/');
                String folderName = s1.substring(0, ind1);
                hashSet.add(s.substring(0, ind));
                if (hashMap.get(s.substring(0, ind)) == null) {
                    ArrayList<String> a = new ArrayList<>();
                    a.add(videoName);
                    hashMap.put(s.substring(0, ind), a);
                } else
                    hashMap.get(s.substring(0, ind)).add(videoName);
            }
            Iterator iterator = hashSet.iterator();
            mArrayAdapter.getMap(hashMap);
            while (iterator.hasNext()) {
                String s = iterator.next().toString();
                ArrayList<String> a = hashMap.get(s);
                s = s.substring(s.lastIndexOf('/') + 1);
                if (s.equals("0"))
                    s = "Internal Memory";
                mArrayAdapter.add(new Folder(R.drawable.ic_folder_black_24dp, s, a.size()));
            }
            listView.setAdapter(mArrayAdapter);
        }
    }

    private void dfs(File file[]) {
        for (int i = 0; i < file.length; i++) {
            if (file[i].toString().contains("/storage/emulated/0/Android/data")) {
                return;
            } else if (file[i].toString().charAt(0) == '.')
                return;
            else if (file[i].toString().endsWith(".mp4") ||
                    file[i].toString().endsWith(".mkv") ||
                    file[i].toString().endsWith(".flv")) {
                arrayList.add(file[i]);
            } else if (file[i].isDirectory()) {
                File[] files = file[i].listFiles();
                dfs(files);
            }
        }
    }
}

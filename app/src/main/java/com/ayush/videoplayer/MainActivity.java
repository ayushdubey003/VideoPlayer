package com.ayush.videoplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<File> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "Please give the required permissions", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 44);
        } else {
            File root = Environment.getExternalStorageDirectory();
            String rootPath = root.getPath();
            File[] file = root.listFiles();
            dfs(file);
        }
    }

    private void dfs(File file[]) {
        for (int i = 0; i < file.length; i++) {
            if (file[i].toString().charAt(0) == '.')
                return;
            else if (file[i].toString().endsWith(".mp4") || file[i].toString().endsWith(".mkv")) {
                arrayList.add(file[i]);
                Log.e("this", "" + file[i]);
            }
            if (file[i].isDirectory()) {
                File[] files = file[i].listFiles();
                dfs(files);
            }
        }
    }
}

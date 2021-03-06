package com.ayush.videoplayer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
    LinearLayout linearLayout;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stream: {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Network Stream");
                alertDialog.setIcon(R.drawable.ic_view_stream_black_24dp);
                alertDialog.setMessage("Link Here");
                final EditText editText = new EditText(MainActivity.this);
                int dim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dim, dim);
                editText.setLayoutParams(lp);
                editText.setSingleLine();
                alertDialog.setView(editText);
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String link = editText.getText().toString();
                        Intent intent = new Intent(MainActivity.this, InternetVideoViewActivity.class);
                        intent.putExtra("link", link);
                        startActivity(intent);
                    }
                });
                alertDialog.show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TextView textView = (TextView) findViewById(R.id.textt);
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "font.ttf");
                    textView.setTypeface(typeface);
                    linearLayout.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    File root = Environment.getExternalStorageDirectory();
                    File[] file = root.listFiles();
                    new FileAsyncTask().execute(file);
                    return;
                }
            }
            default:{
                Toast.makeText(MainActivity.this,"App cannot function without the required permissions",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        final ListView listView = (ListView) findViewById(R.id.list);
        linearLayout = (LinearLayout) findViewById(R.id.tohide);
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
            File root = Environment.getExternalStorageDirectory();
            File[] file = root.listFiles();
            new FileAsyncTask().execute(file);
        }
    }

    private class FileAsyncTask extends AsyncTask<File, Integer, String> {

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

        @Override
        protected String doInBackground(File... params) {
            dfs(params);
            return " ";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            final ListView listView = (ListView) findViewById(R.id.list);
            linearLayout.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.VISIBLE);
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
}

package com.ayush.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends ArrayAdapter<Folder> {
    public FolderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Folder> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, null, false);
        final TextView textView = (TextView) convertView.findViewById(R.id.foldername);
        final TextView textView1 = (TextView) convertView.findViewById(R.id.numberofvideos);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.folderimg);
        textView.setText(getItem(position).getmFolderName());
        imageView.setImageResource(R.drawable.ic_folder_black_24dp);
        textView1.setText(Integer.toString(getItem(position).getmSize()) + " video(s)");
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font.ttf");
        textView1.setTypeface(typeface);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textView.getText().toString();
                String number = textView1.getText().toString();
                if (s.equalsIgnoreCase("internal memory"))
                    s = "0";
                Intent intent = new Intent(getContext(), VideoActivity.class);
                intent.putExtra("folder", s);
                intent.putExtra("number", number);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}

package com.ayush.videoplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends ArrayAdapter<Folder> {
    public FolderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Folder> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, null, false);
        TextView textView = (TextView) convertView.findViewById(R.id.foldername);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.folderimg);
        textView.setText(getItem(position).getmFolderName());
        imageView.setImageResource(R.drawable.ic_folder_black_24dp);
        return convertView;
    }
}

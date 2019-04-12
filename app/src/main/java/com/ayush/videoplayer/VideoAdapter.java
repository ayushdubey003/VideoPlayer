package com.ayush.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {
    public VideoAdapter(@NonNull Context context, int resource, @NonNull List<Video> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.videoitems, null, false);
        TextView textView = (TextView) convertView.findViewById(R.id.videoname);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.videoimg);
        textView.setText(getItem(position).getmVideoName().toString());
        final String filepath = getItem(position).getmAddress();
        Glide.with(getContext())
                .load(Uri.fromFile(new File(filepath)))
                .apply(new RequestOptions().override(275, 200))
                .centerCrop()
                .into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VideoViewActivity.class);
                intent.putExtra("address", filepath);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}

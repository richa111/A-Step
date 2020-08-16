package com.example.ajay.a_step;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CostemList extends ArrayAdapter<String> {

    private String[] urls;
    private Bitmap[] bitmaps;
    private Activity context;




    public CostemList(Activity context, String[] urls, Bitmap[] bitmaps) {
        super(context, R.layout.activity_costem_list, urls);
        this.context = context;
        this.urls= urls;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_costem_list, null, true);
        // TextView textViewURL = (TextView) listViewItem.findViewById(R.id.textViewURL);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageDownloaded);

        // textViewURL.setText(urls[position]);
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],600,500,false));
        return  listViewItem;
    }
}




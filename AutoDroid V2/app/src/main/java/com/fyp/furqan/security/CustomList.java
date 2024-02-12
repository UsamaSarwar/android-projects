package com.fyp.furqan.security;

/**
 * Created by Furqan on 3/16/2016.
 */
import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends  ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> web;
    private final ArrayList<Bitmap> imageId;

    public CustomList(Activity context,
                      ArrayList<String> web, ArrayList<Bitmap> imageId) {
        super(context, R.layout.activity_locations__list, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.locations_list_interface, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web.get(position));

        imageView.setImageBitmap(imageId.get(position));

        return rowView;
    }
}
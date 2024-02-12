package com.example.aamir.tablayout.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.aamir.tablayout.Modals.Icon;
import com.example.aamir.tablayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AYAAN on 5/14/2017.
 */

public class IconGridAdapter extends BaseAdapter {

    Context context;
    List<Icon> iconList;

    public IconGridAdapter(Context context , List<Icon> list){
        this.context = context;
        this.iconList = list;
    }
    @Override
    public int getCount() {
        return iconList.size();
    }

    @Override
    public Icon getItem(int position) {
        return iconList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_row,null);

        ImageView iconImage = (ImageView) v.findViewById(R.id.grid_icon_image_view);

        Icon drawObject = getItem(position);
        final int id = context.getResources().getIdentifier(drawObject.getName(),"drawable",context.getPackageName());
        iconImage.setImageResource(id);



        return v;
    }
}

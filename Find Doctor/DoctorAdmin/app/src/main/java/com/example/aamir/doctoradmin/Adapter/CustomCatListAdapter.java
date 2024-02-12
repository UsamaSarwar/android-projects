package com.example.aamir.doctoradmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aamir.doctoradmin.Modals.ModalCatList;
import com.example.aamir.doctoradmin.Modals.ModalCityList;
import com.example.aamir.doctoradmin.R;

import java.util.List;

/**
 * Created by Aamir on 6/11/2017.
 */

public class CustomCatListAdapter  extends BaseAdapter{

    Context context;
    List<ModalCatList> list;

    public CustomCatListAdapter(Context context, List<ModalCatList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ModalCatList getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v ;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        v = layoutInflater.inflate(R.layout.city_item_row,null);

        TextView profName = (TextView) v.findViewById(R.id.textView_adapter_city_name);

        ModalCatList object = getItem(position);

         profName.setText(object.getProf_name());

        return v;
    }
}

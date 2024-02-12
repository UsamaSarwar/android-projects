package com.example.aamir.doctor.CityListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.MainActivity;
import com.example.aamir.doctor.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 5/28/2017.
 */

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.Viewholder> {

    Context context;
    List<ModalCityList> cityList;
    View view;
    private int lastPosition = -1;

    public CityListAdapter(Context context, List<ModalCityList> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item_row,parent,false);

        view = v;

        return new Viewholder(v,context,cityList);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {


        Typeface typeFaceRegular = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.otf");

        ModalCityList modalCityList = cityList.get(position);
        holder.cityName.setText(modalCityList.getCity_name());
        holder.cityName.setTypeface(typeFaceRegular);


        for (int i = 0; i < getItemCount(); i++) {

            animate(view, i);

        }

            if(position == 0){
                holder.viewCity.setVisibility(View.VISIBLE);
            }

    }

    private void animate(final View view, final int position){

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        view.startAnimation(animation);
        lastPosition = position;

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textView_city_list)
        TextView cityName;
        @BindView(R.id.view_city)
                View viewCity;
        Context ctx;
        List<ModalCityList> cityList;
        public Viewholder(View itemView,Context context,List<ModalCityList> list) {
            super(itemView);
            this.ctx = context;
            this.cityList = list;
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            SharedPreferences sp = context.getSharedPreferences("City",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("city_id",cityList.get(getAdapterPosition()).getCity_id());
            editor.putString("city_name",cityList.get(getAdapterPosition()).getCity_name());
            editor.apply();
            ((Activity)context).finish();
        }
    }
}

package com.example.aamir.doctor.GridMainList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.DialogPreference;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Connection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.ChooseLocation;
import com.example.aamir.doctor.Activities.DoctorsList;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.Util;

/**
 * Created by Aamir on 5/27/2017.
 */

public class DrCategoryAdapter extends RecyclerView.Adapter<DrCategoryAdapter.ViewHolder>{

    List<ModalDrCat> drCatList;
    Context context;
    private int lastPosition = -1;
    View view;

    public DrCategoryAdapter(List<ModalDrCat> drCatList, Context context) {
        this.drCatList = drCatList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dr_cat_item_row,
                parent,false);
        view = v;
        return new ViewHolder(v,this.context,drCatList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Typeface typefaceRegular = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.otf");

        ModalDrCat modalDrCat = drCatList.get(position);
        final int id = context.getResources().getIdentifier(modalDrCat.getCatImage(),"drawable",context.getPackageName());
        holder.drCatImage.setImageResource(id);
        holder.drCatName.setText(modalDrCat.getCatName());
        holder.drCatName.setTypeface(typefaceRegular);


    }


    @Override
    public int getItemCount() {
        return drCatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.dr_cat_image)
        ImageView drCatImage;
        @BindView(R.id.dr_cat_name)
        TextView drCatName;
        List<ModalDrCat> drCatList;
        Context ctx;

        public ViewHolder(View itemView,Context context,List<ModalDrCat> list) {
            super(itemView);
            this.drCatList = list;
            this.ctx = context;
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View v) {

            SharedPreferences sp = ctx.getSharedPreferences("City",ctx.MODE_PRIVATE);

            if(sp.getInt("city_id",0)!=-1){
                ModalDrCat modelDrCat = this.drCatList.get(getAdapterPosition());

                Intent i = new Intent(context,DoctorsList.class);
                i.putExtra("send","yes");
                i.putExtra("cat_id",modelDrCat.getCatId());
                i.putExtra("city_id",sp.getInt("city_id",0));
                i.putExtra("cat_name",modelDrCat.getCatName());
                ctx.startActivity(i);
            }else{

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Choose Location");
                builder.setMessage("Select your current city.");
                builder.setPositiveButton("Location",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ctx.startActivity(new Intent(ctx, ChooseLocation.class));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //((Activity)ctx).finish();
                    }
                });
                builder.show();
            }



        }
    }





}

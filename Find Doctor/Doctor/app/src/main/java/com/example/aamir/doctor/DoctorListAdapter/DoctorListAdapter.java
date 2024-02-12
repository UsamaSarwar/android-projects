package com.example.aamir.doctor.DoctorListAdapter;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.DoctorDetail;
import com.example.aamir.doctor.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aamir on 5/27/2017.
 */

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder>  {

    Context context;
    List<ModalDrList> drList;
    View view1;
    private int lastPosition = -1;

    public DoctorListAdapter(Context context, List<ModalDrList> drList) {
        this.context = context;
        this.drList = drList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dr_list_item_row,
                parent,false);
        view1 = v;
        return new ViewHolder(v,this.context,drList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Typeface typefaceRegular = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typefaceBold = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Bold.otf");

        ModalDrList modalDrList = drList.get(position);

        if(modalDrList.getDoc_photo().equals("http://tabeeb.com.pk/admin/images/")){
            holder.drImage.setImageResource(R.drawable.doctor);
        }else {
            Picasso.with(context).load(modalDrList.getDoc_photo()).resize(50,50).into(holder.drImage);
        }
        //holder.drImage.setImageResource(R.drawable.doctor);
        holder.drCatName.setText(modalDrList.getCategory());
        holder.drName.setText(modalDrList.getDoc_name());
        holder.drExperience.setText(modalDrList.getExperience());
        holder.drQualification.setText(modalDrList.getDoc_edu());

        holder.drCatName.setTypeface(typefaceRegular);
        holder.drName.setTypeface(typefaceRegular);
        holder.drExperience.setTypeface(typefaceRegular);
        holder.drQualification.setTypeface(typefaceRegular);


    }



    @Override
    public int getItemCount() {
        return drList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.drListImageView)
        ImageView drImage;
        @BindView(R.id.drListCatName)
        TextView drCatName;
        @BindView(R.id.drListDrName)
        TextView drName;
        @BindView(R.id.drListDrExperience)
        TextView drExperience;
        @BindView(R.id.drListDrQualification)
        TextView drQualification;
        Context ctx;
        List<ModalDrList> drLists;
        public ViewHolder(View itemView,Context context,List<ModalDrList> list) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
            this.ctx = context;
            this.drLists = list;
        }

        @Override
        public void onClick(View v) {


           /* Intent intent = new Intent(ctx,DoctorDetail.class);

            ModalDrList modalDrList = drLists.get(getAdapterPosition());*/

            //intent.putExtra();

            ModalDrList modalDrList = drLists.get(getAdapterPosition());
            Intent intent = new Intent(ctx,DoctorDetail.class);
            intent.putExtra("city_id",modalDrList.getCity_id());
            intent.putExtra("prof_id",modalDrList.getProf_id());
            intent.putExtra("dr_id",modalDrList.getDoc_id());
            intent.putExtra("dr_name",modalDrList.getDoc_name());
            intent.putExtra("dr_edu",modalDrList.getDoc_edu());
            intent.putExtra("dr_cat",modalDrList.getCategory());
            intent.putExtra("dr_image",modalDrList.getDoc_photo());
            intent.putExtra("dr_hospital",modalDrList.getHospital());
            intent.putExtra("dr_timing",modalDrList.getDoc_timing());
            intent.putExtra("dr_address",modalDrList.getDoc_address());
            intent.putExtra("dr_service",modalDrList.getServices());
            intent.putExtra("dr_exp",modalDrList.getExperience());
            intent.putExtra("dr_fee",modalDrList.getDoc_fee());
            intent.putExtra("dr_phone",modalDrList.getDoc_phone());
            ctx.startActivity(intent);
        }

    }
}

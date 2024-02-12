package com.example.aamir.doctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.HospitalDetail;
import com.example.aamir.doctor.Activities.HospitalList;
import com.example.aamir.doctor.Modal.HospitalModal;
import com.example.aamir.doctor.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 8/31/2017.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    Context context;
    List<HospitalModal> hospitalModals;

    public HospitalAdapter(Context context, List<HospitalModal> hospitalModals) {
        this.context = context;
        this.hospitalModals = hospitalModals;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Typeface typeface1 = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeface2 = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Bold.otf");

        final HospitalModal hospitalList = hospitalModals.get(position);

        holder.name.setText(hospitalList.getHospital_name());
        holder.name.setTypeface(typeface2);
        holder.address.setText(hospitalList.getHospital_address());
        holder.address.setTypeface(typeface1);

        if(hospitalList.getHospital_photo().equals("")){
        }else {
            Picasso.with(context).load(hospitalList.getHospital_photo()).placeholder(R.drawable.hospital).resize(70,70).into(holder.image);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, HospitalDetail.class);
                intent.putExtra("hospital_name",hospitalList.getHospital_name());
                intent.putExtra("hospital_photo",hospitalList.getHospital_photo());
                intent.putExtra("hospital_services",hospitalList.getHospital_services());
                intent.putExtra("hospital_address",hospitalList.getHospital_address());
                intent.putExtra("hospital_phone",hospitalList.getHospital_phone());

                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return hospitalModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imageView_hospital)
        ImageView image;
        @BindView(R.id.textView_hospital_name)
        TextView name;
        @BindView(R.id.textView_hospital_address)
        TextView address;
        @BindView(R.id.cardView_hospital)
        CardView cardView;
        public ViewHolder(View itemView) {
           super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }



}

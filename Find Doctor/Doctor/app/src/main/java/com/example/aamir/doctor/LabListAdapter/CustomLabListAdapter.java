package com.example.aamir.doctor.LabListAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.DoctorDetail;
import com.example.aamir.doctor.CallDialogue.CallDialogue;
import com.example.aamir.doctor.CallDialogue.CallDialogue2;
import com.example.aamir.doctor.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 6/24/2017.
 */

public class CustomLabListAdapter extends RecyclerView.Adapter<CustomLabListAdapter.ViewHolder> {

    Context contex;
    List<ModalLab> modalLabList;

    public CustomLabListAdapter(Context contex, List<ModalLab> modalLabList) {
        this.contex = contex;
        this.modalLabList = modalLabList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lab_item_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Typeface typeFaceRegular = Typeface.createFromAsset(contex.getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(contex.getAssets(),"fonts/Quicksand-Bold.otf");

        final ModalLab modalLab = modalLabList.get(position);

        holder.labName.setText(modalLab.getLab_name());
        holder.labName.setTypeface(typeFaceBold);

        holder.labAddress.setText(modalLab.getLab_address());
        holder.labAddress.setTypeface(typeFaceRegular);

        holder.callLab.setTypeface(typeFaceRegular);
        holder.callLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.app.FragmentManager fragmentManager = ((Activity) contex).getFragmentManager();
                final CallDialogue2 callDialogue = new CallDialogue2(modalLab.lab_name,modalLab.getLab_phone());
                callDialogue.show(fragmentManager, "my Fragment");

            }
        });
    }

    @Override
    public int getItemCount() {
        return modalLabList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textView_lab_name)
        TextView labName;
        @BindView(R.id.textView_lab_address)
        TextView labAddress;
        @BindView(R.id.button_lab_call)
        Button callLab;
        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}

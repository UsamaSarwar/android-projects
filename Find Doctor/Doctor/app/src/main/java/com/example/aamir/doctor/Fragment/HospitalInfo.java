package com.example.aamir.doctor.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aamir.doctor.Activities.DoctorDetail;
import com.example.aamir.doctor.CallDialogue.CallDialogue2;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 8/31/2017.
 */

public class HospitalInfo extends Fragment {



    @BindView(R.id.button_call_hospital)
    Button callButton;
    @BindView(R.id.dr_services_heading)
    TextView servicesHeading;
    @BindView(R.id.textView_dr_detail_services)
    TextView services;
    @BindView(R.id.textView_more_services)
    TextView moreServices;
    @BindView(R.id.dr_address_heading)
    TextView addressHeding;
    @BindView(R.id.textView_dr_detail_address)
    TextView address;
    @BindView(R.id.linear_layout_services)
    LinearLayout layoutServices;

    String [] service = new String[50];
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hospital_detail,container,false);

        ButterKnife.bind(this,view);

        setTypeFace();

        setTextToViews();

        initListner();

        return view;
    }

    private void initListner() {


        if(service.length>3)
        {
            moreServices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    final ListPopupWindow listPopupWindow = new ListPopupWindow(getActivity());
                    listPopupWindow.setAdapter(new ArrayAdapter(
                            getActivity(),
                            android.R.layout.simple_list_item_1, service));
                    listPopupWindow.setAnchorView(moreServices);
                    listPopupWindow.setWidth(400);
                    listPopupWindow.setHeight(650);

                    listPopupWindow.setModal(true);
                    listPopupWindow.show();

                    listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listPopupWindow.dismiss();
                        }
                    });


                }
            });


        }else {
            moreServices.setVisibility(View.GONE);
        }

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                final CallDialogue2 callDialogue = new CallDialogue2(Utility.name1,Utility.number);
                callDialogue.show(fragmentManager, "my Fragment");
            }
        });
    }

    private void setTextToViews() {

        address.setText(Utility.address);

       if(Utility.services.equals("")){
           layoutServices.setVisibility(View.GONE);
       }else {
           service = Utility.services.split(",");
           if(service.length == 1){
               services.setText("+ "+service[0]);
           }else if(service.length == 2) {
               services.setText("+ "+service[0]+"\n"+"+ "+service[1]);
           }else if(service.length > 2){
               services.setText("+ "+service[0]+"\n"+"+ "+service[1]+"\n"+"+ "+service[2]);
           }
       }


    }

    private void setTypeFace() {

        Typeface type1 = Typeface.createFromAsset(getActivity().getAssets(),"Quicksand-Regular.otf");
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"Quicksand-Bold.otf");
        servicesHeading.setTypeface(type);
        services.setTypeface(type1);
        moreServices.setTypeface(type);
        addressHeding.setTypeface(type);
        address.setTypeface(type1);
        callButton.setTypeface(type1);
    }
}

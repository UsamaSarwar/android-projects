package com.example.aamir.doctor.CallDialogue;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aamir.doctor.Activities.DoctorDetail;
import com.example.aamir.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 5/30/2017.
 */

public class CallDialogue2 extends DialogFragment {

    @BindView(R.id.imageView_dialogue_call)
    ImageView callImage;
    @BindView(R.id.textView_dialogue_call_text)
    TextView callText;
    @BindView(R.id.textView_dialogue_call_number)
    TextView callNumber;
    @BindView(R.id.button_dialogue_call)
    Button callButton;

    String name , number;

    public CallDialogue2(){
        super();
    }


    @SuppressLint("ValidFragment")
    public CallDialogue2(String name,String Number) {
        this.name = name;
        this.number = Number;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.call_dialogue,container,false);

        ButterKnife.bind(this,v);



        callText.setText(name);
        callNumber.setText(number);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phNo = number;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",
                        phNo, null));
                startActivity(intent);
                getDialog().dismiss();
            }
        });
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);



            Typeface typeFaceRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quicksand-Regular.otf");
            Typeface typeFaceBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quicksand-Bold.otf");

            callText.setTypeface(typeFaceBold);
            callNumber.setTypeface(typeFaceRegular);
            callButton.setTypeface(typeFaceRegular);



        return v;
    }


    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }
}

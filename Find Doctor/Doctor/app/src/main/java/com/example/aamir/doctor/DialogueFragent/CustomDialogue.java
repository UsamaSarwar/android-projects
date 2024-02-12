package com.example.aamir.doctor.DialogueFragent;

import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aamir.doctor.Activities.DoctorsList;
import com.example.aamir.doctor.R;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 5/28/2017.
 */

public class CustomDialogue extends DialogFragment {

    @BindView(R.id.button_dialogue_button)
    Button okDialogue;
    @BindView(R.id.imageView_dialigue_close)
    ImageView iconAnimated;
    @BindView(R.id.textView_dialogue_no_internet_1)
    TextView text1;
    @BindView(R.id.textView_dialogue_no_internet_2)
    TextView text2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialogue_no_internet,container,false);
        getDialog().setCanceledOnTouchOutside(false);
        ButterKnife.bind(this,v);


        okDialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                //((DoctorsList)getActivity()).retry.setVisibility(View.VISIBLE);
            }
        });
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        Typeface typefaceRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quicksand-Regular.otf");
        text1.setTypeface(typefaceRegular);
        text2.setTypeface(typefaceRegular);
        okDialogue.setTypeface(typefaceRegular);





        return v;
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.NoInternetDialogAnimation;
    }
}

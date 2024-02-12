package com.example.aamir.doctor.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.aamir.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsDescription extends AppCompatActivity {

    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.description)
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_description);

        ButterKnife.bind(this);

        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typefaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");

        heading.setTypeface(typefaceBold);
        description.setTypeface(typefaceRegular);





    }
}

package com.example.aamir.doctor.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.CallDialogue.CallDialogue;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorDetail extends AppCompatActivity {


    @BindView(R.id.back_dr_detail)
    ImageView drDetailBack;
    @BindView(R.id.imageView_dr_detail_image)
    ImageView drDetailImage;
    @BindView(R.id.textView_dr_detail_name)
    TextView drName;
    @BindView(R.id.textView_dr_detail_education)
    TextView drEducation;
    @BindView(R.id.textView_dr_detail_category)
    TextView drCategory;
    @BindView(R.id.button_dr_detail_call)
    Button drCall;
    @BindView(R.id.button_dr_detail_review)
    Button drReview;
    @BindView(R.id.button_dr_detail_feed_back)
    Button drFeedBack;
    @BindView(R.id.textView_dr_detail_hospital)
    TextView drHospital;
    @BindView(R.id.textView_dr_detail_timing)
    TextView drTiming;
    @BindView(R.id.textView_dr_detail_address)
    TextView drAddress;
    @BindView(R.id.textView_dr_detail_services)
    TextView drServices;
    @BindView(R.id.textView_dr_detail_fee)
    TextView drFee;
    @BindView(R.id.textView_more_services)
    TextView moreServices;
    @BindView(R.id.linear_layout_dr_detail_services_click)
    LinearLayout linearLayoutclickService;
    @BindView(R.id.dr_Time_Heading)
            TextView timeHeading;
    @BindView(R.id.dr_address_heading)
            TextView addressHeading;
    @BindView(R.id.dr_services_heading)
            TextView servicesHeading;
    @BindView(R.id.appName)
            TextView appName;
    @BindView(R.id.linear_layout_services)
            LinearLayout linearLayoutServices;

    int cityId, profId, drId;
    String drEduText, drCatText, drImageText,
            drHospitalText, drTimingText, drAddressText, drServicesText, drExpText, drFeeText ;
    public String drNameText, drPhoneText;

    private static String [] services = new String[50];

    Typeface typefaceRegular,typefaceBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        ButterKnife.bind(this);

        typefaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        typefaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");

        getBundle();

        setBundleToFields();

        initListner();

        setTypeFaces();

    }

    private void setTypeFaces() {

        drCall.setTypeface(typefaceRegular);
        drFeedBack.setTypeface(typefaceRegular);
        drReview.setTypeface(typefaceRegular);
        drName.setTypeface(typefaceBold);
        drEducation.setTypeface(typefaceRegular);
        drCategory.setTypeface(typefaceRegular);
        drHospital.setTypeface(typefaceRegular);
        drFee.setTypeface(typefaceRegular);
        drTiming.setTypeface(typefaceRegular);
        drAddress.setTypeface(typefaceRegular);
        drServices.setTypeface(typefaceRegular);
        moreServices.setTypeface(typefaceBold);
        timeHeading.setTypeface(typefaceBold);
        addressHeading.setTypeface(typefaceBold);
        servicesHeading.setTypeface(typefaceBold);
        appName.setTypeface(typefaceBold);
    }

    private void initListner() {

        final View view = findViewById(android.R.id.content);

        drCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(drPhoneText.equals("") || drPhoneText.equals("no")){
                   Snackbar.make(view,"Sorry no contact number available to this doctor",Snackbar.LENGTH_SHORT).show();

               }else {

                   //call dialogue
                   final android.app.FragmentManager fragmentManager = getFragmentManager();
                   final CallDialogue callDialogue = new CallDialogue();
                   callDialogue.show(fragmentManager, "my Fragment");
               }
            }
        });

        drDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        drReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                  SharedPreferences sp1 = getSharedPreferences("User",MODE_PRIVATE);
                    if(sp1.getString("name","").equals("e") || sp1.getString("user_name","").equals("e")
                || sp1.getString("password","").equals("e")){


                        AlertDialog.Builder builder = new AlertDialog.Builder(DoctorDetail.this);
                        builder.setTitle("Login")
                                .setMessage("You have to need login first")
                                .setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(DoctorDetail.this,LoginSignup.class));
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();



                    }else{

                        if(Utility.isOnline(DoctorDetail.this)){
                            Intent intent = new Intent(DoctorDetail.this,FeedBack.class);
                            intent.putExtra("dr_name",drNameText);
                            intent.putExtra("dr_id",drId);
                            startActivity(intent);
                        }else {
                            final android.app.FragmentManager fragmentManager = getFragmentManager();
                            final CustomDialogue customDialogue = new CustomDialogue();
                            customDialogue.show(fragmentManager, "my Fragment");
                        }
                    }




            }
        });

        drFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isOnline(DoctorDetail.this)){
                    Intent intent = new Intent(DoctorDetail.this,PatientFeedback.class);
                    intent.putExtra("dr_id",drId);
                    intent.putExtra("dr_name",drNameText);
                    intent.putExtra("dr_exp",drExpText);
                    intent.putExtra("dr_edu",drEduText);
                    startActivity(intent);
                }else {
                    final android.app.FragmentManager fragmentManager = getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                }



            }
        });

        // check whether services lenght greter three and then shop popup
        if(services.length>3)
        {
            moreServices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    final ListPopupWindow listPopupWindow = new ListPopupWindow(DoctorDetail.this);
                    listPopupWindow.setAdapter(new ArrayAdapter(
                            DoctorDetail.this,
                            android.R.layout.simple_list_item_1, services));
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

    }

    private void setBundleToFields() {

        drName.setText(drNameText);
        drEducation.setText(drEduText);
        drCategory.setText(drCatText);
        drHospital.setText("Hospital : "+drHospitalText);
        drTiming.setText(drTimingText);
        drAddress.setText(drAddressText);
        if(drServicesText.equals("")){
                linearLayoutServices.setVisibility(View.GONE);
        }else {
            if(services.length == 1){
                drServices.setText("+"+services[0]);
            }else if(services.length == 2){
                drServices.setText("+"+services[0]+"\n"+"+"+services[1]);
            }else {
                drServices.setText("+"+services[0]+"\n"+"+"+services[1]+"\n"+"+"+services[2]);
            }

        }
        drFee.setText("Fee : "+drFeeText);

        if(drImageText.equals("http://tabeeb.com.pk/admin/images/")){
            drDetailImage.setImageResource(R.drawable.doctor);
        }else {
            Picasso.with(DoctorDetail.this).load(drImageText).into(drDetailImage);
        }

    }

    private void getBundle() {

        Bundle b = getIntent().getExtras();
        cityId = b.getInt("city_id");
        profId = b.getInt("prof_id");
        drId = b.getInt("dr_id");
        drNameText = b.getString("dr_name");
        drEduText = b.getString("dr_edu");
        drCatText = b.getString("dr_cat");
        drImageText = b.getString("dr_image");
        drHospitalText = b.getString("dr_hospital");
        drTimingText = b.getString("dr_timing");
        drAddressText = b.getString("dr_address");
        drServicesText = b.getString("dr_service");
        drExpText = b.getString("dr_exp");
        drFeeText = b.getString("dr_fee");
        drPhoneText = b.getString("dr_phone");

        services = drServicesText.split(",");

    }
}

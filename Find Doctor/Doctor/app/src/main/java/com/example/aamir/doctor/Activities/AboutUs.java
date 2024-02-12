package com.example.aamir.doctor.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUs extends AppCompatActivity {

    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.Team)
    TextView team;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.rate)
    TextView rate;
    @BindView(R.id.description)
    TextView desc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ButterKnife.bind(this);

        setTypeFace();
    }

    private void setTypeFace() {


        Typeface typeFaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");

        version.setTypeface(typeFaceRegular);
        contact.setTypeface(typeFaceRegular);
        team.setTypeface(typeFaceRegular);
        rate.setTypeface(typeFaceRegular);
        desc.setTypeface(typeFaceRegular);

    }


    public void launchContact(View view){
        startActivity(new Intent(AboutUs.this,ContactUs.class));
    }
    public void launchEmail(View view){

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.emailaddress)});
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)));

    }
    public void launchRate(View view){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.app_url)));
        startActivity(intent);

    }
    public void launchAbout(View view){
        startActivity(new Intent(AboutUs.this,AboutTeam.class));
    }
    public void launchDescription(View view){

        startActivity(new Intent(AboutUs.this,AboutUsDescription.class));
    }
    public void launchFacebook(View view){

       /* startActivity(openFacebook(AboutUs.this));*/
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);

    }


    public static String FACEBOOK_URL = "https://www.facebook.com/tabeeb.com.pk";
    public static String FACEBOOK_PAGE_ID = "tabeeb.com.pk";

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.aamir.rashid.180", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}

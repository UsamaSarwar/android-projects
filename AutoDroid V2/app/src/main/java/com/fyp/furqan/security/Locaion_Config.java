package com.fyp.furqan.security;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.google.android.gms.common.api.GoogleApiClient;


public class Locaion_Config extends Activity  {
    private RadioGroup radioInternetGroup;
    private RadioButton radioInternetButton;
    private RadioGroup radioprofileGroup;
    private RadioButton radioprofileButton;
    private RemindersDbAdapter mDbAdapter;
    private EditText title;
    private Button btnDisplay;
    private CheckBox scrnLk,walck;
    public static Double stLat;
    public static Double stLon;
    public static int chk=1;
    public static boolean Updates=false;
    Intent te;
    int IdGet=0;
    private GoogleApiClient client;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private TextView lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locaion__config);
        chk=1;
//startService(new Intent(getApplicationContext(), MyService.class));
try {
    // scrnLk = (CheckBox)findViewById(R.id.LockScreenCheck);
    walck = (CheckBox) findViewById(R.id.setWallpapercheck);

    radioInternetGroup = (RadioGroup) findViewById(R.id.radioGroup);
    radioprofileGroup = (RadioGroup) findViewById(R.id.radioGroup2);
    title = (EditText) findViewById(R.id.titleLocation);
    btnDisplay = (Button) findViewById(R.id.button2);
    mDbAdapter = new RemindersDbAdapter(this);
    lat = (TextView) findViewById(R.id.textLat);
    lon = (TextView) findViewById(R.id.textLon);

    lat.setText("Latitude: " + stLat.toString());
    lon.setText("Longitude: " + stLon.toString());


    findViewById(R.id.imageButton)
            .setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {

                    // in onCreate or any event where your want the user to
                    // select a file

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);
                }
            });


    te = getIntent();
    if (Updates) {
        mDbAdapter.open();
        IdGet = te.getExtras().getInt("idRow");

        Cursor c = mDbAdapter.fetchReminder(IdGet);

        startManagingCursor(c);
        //NEW
        title.setText(c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_TITLE)));
        //        Toast.makeText(Locaion_Config.this,c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_INTERNET_MODE))+ "", Toast.LENGTH_SHORT).show();

        if (c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_INTERNET_MODE)).equals("Wifi")) {
            radioInternetGroup.check(R.id.wifiradbtn);
        } else if (c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_INTERNET_MODE)).equals("3G")) {
            radioInternetGroup.check(R.id.gradbtn);
        } else {
            radioInternetGroup.check(R.id.offrdbtn);
        }

        if (c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_Profile_TIME)).equals("Silent")) {
            radioprofileGroup.check(R.id.silentradbtn);
        } else if (c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_INTERNET_MODE)).equals("Vibrate")) {
            radioprofileGroup.check(R.id.vibradbtn);
        } else {
            radioprofileGroup.check(R.id.norradbtn);
        }

    }


    mDbAdapter.open();
//Toast.makeText(getApplicationContext(),stLat+"  "+stLon,Toast.LENGTH_LONG).show();
    btnDisplay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int selectedInternetId = radioInternetGroup.getCheckedRadioButtonId();
            radioInternetButton = (RadioButton) findViewById(selectedInternetId);
            int selectedProfileId = radioprofileGroup.getCheckedRadioButtonId();
            radioprofileButton = (RadioButton) findViewById(selectedProfileId);
            int chckScren = 0;
            int checkwall;

            if (walck.isChecked()) {
                checkwall = 1;
            } else {
                checkwall = 0;

            }
            if (Updates == true) {
                if (IdGet == 1) {
                    // Log.d("DEFAULTRUN","Riini");

                    mDbAdapter.updateReminder(IdGet, title.getText().toString(), radioInternetButton.getText().toString(), radioprofileButton.getText().toString(), chckScren + "", 0.0, 0.0, checkwall + "", selectedImagePath);
                } else {
                    mDbAdapter.updateReminder(IdGet, title.getText().toString(), radioInternetButton.getText().toString(), radioprofileButton.getText().toString(), chckScren + "", stLat, stLon, checkwall + "", selectedImagePath);

                }
                //int k = mDbAdapter.fetchAllReminders().getCount();
                // Toast.makeText(Locaion_Config.this, "" + checkwall + "  " + selectedImagePath, Toast.LENGTH_SHORT).show();

                Updates = false;

                //        Toast.makeText(Locaion_Config.this, "" + stLat + "  " + stLon, Toast.LENGTH_SHORT).show();
            } else {

                mDbAdapter.createReminder(title.getText().toString(), radioInternetButton.getText().toString(), radioprofileButton.getText().toString(), chckScren + "", stLat, stLon, checkwall + "", selectedImagePath);
                int k = mDbAdapter.fetchAllReminders().getCount();
                //  Toast.makeText(Locaion_Config.this, "" + stLat + "  " + stLon, Toast.LENGTH_SHORT).show();
            }
            mDbAdapter.close();
            finish();


        }
    });

}
catch (Exception e)
{

}//end Exception
}



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
               // Toast.makeText(Locaion_Config.this,selectedImagePath+ "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

}

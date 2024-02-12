package com.example.aamir.tablayout.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aamir.tablayout.AlarmService.AlarmManager;
import com.example.aamir.tablayout.Classes.Utility;
import com.example.aamir.tablayout.Database.DatabaseHandler;
import com.example.aamir.tablayout.R;
import com.google.gson.JsonElement;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import ai.api.ui.AIButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;

public class ApiActivity extends AppCompatActivity implements AIListener, TextToSpeech.OnInitListener {


    AIButton aiButton;
    private TextToSpeech t1;
    Animation animation = null;
    private AIService aiService;
    private TextView resultTextView;
    ImageButton listenButton;



    @BindView(R.id.toolBar)
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        if(toolbar!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }





        if(!isConnected(this)){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error!")
                    .setMessage("You must have an internet connection for voice conversation!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    });
            builder.show();

        }else{


            resultTextView = (TextView) findViewById(R.id.resultTextView);

            t1 = new TextToSpeech(this, this);
            checkRequiredPermissions();
            Initializtion();
            initButton();

        }

        initAIService();
        initAnimator();




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            t1.stop();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        t1.stop();
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;
    }


    private void initAnimator() {
        animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
    }

    private void initButton() {
        listenButton = (ImageButton) findViewById(R.id.listen);
        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenButton.startAnimation(animation);
                setTexttoNull();
                aiService.startListening();
            }
        });
    }

    void setTexttoNull() {
        resultTextView.setText(null);
    }

    private void initAIService() {
        final AIConfiguration config = new AIConfiguration("0ce3834527f14adfa803809f7517d4ae",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);

    }

    private void startCommunication() {

        String msgToSpeak = "Welcome, It's Remind Me, I can set Reminder for you";
        t1.speak(msgToSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 101);
            }
        }
    }
    private void Initializtion() {
        aiButton = (AIButton) findViewById(R.id.micButton);
        final AIConfiguration config = new AIConfiguration("0ce3834527f14adfa803809f7517d4ae",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiButton.initialize(config);
        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiButton.performClick();

            }
        });
        aiButton.setResultsListener(new AIButton.AIButtonListener() {
            @Override
            public void onResult(final AIResponse result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ApiAi", "onResult");
                        // TODO process response here
                    }
                });
            }

            @Override
            public void onError(final AIError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ApiAi", "onError");
                        // TODO process error here
                    }
                });
            }

            @Override
            public void onCancelled() {

            }
        });

    }

    @Override
    public void onResult(final AIResponse response) {
        final DatabaseHandler dbhandler = new DatabaseHandler(ApiActivity.this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Result result = response.getResult();

                // Get parameters
                String[] whos = null;
                if (result.getFulfillment().getSpeech().contains("set")) {
                    String date = "", time = "", subject = "";
                    String msgToSpeak = result.getFulfillment().getSpeech();
                    t1.speak(msgToSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    String parameterString = "";
                    if (result.getParameters() != null && !result.getParameters().isEmpty()) {
                        for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                            parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
                            if (entry.getKey().equals("Subject")) {
                                subject = entry.getValue().getAsString().toString();

                            } else if (entry.getKey().equals("date")) {
                                date = entry.getValue().getAsString().toString();

                            } else if (entry.getKey().equals("time")) {
                                time = entry.getValue().getAsString().toString();
                            }
                        }
                    }
                    int id = Utility.getAlarmId(ApiActivity.this);
                    String hour, minute, day,month,year;
                    String time1[] = time.split(":");
                    String date1[] = date.split("-");
                    hour = time1[0];
                    minute = time1[1];
                    day = date1[2];
                    month = date1[1];
                    int m = Integer.parseInt(month);
                    m = m-1;
                    year = date1[0];
                    java.util.Calendar calender = java.util.Calendar.getInstance();
                    calender.set (java.util.Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
                    calender.set (java.util.Calendar.MINUTE, Integer.parseInt(minute));
                    calender.set(java.util.Calendar.SECOND,0);
                    calender.set(calender.DAY_OF_MONTH,Integer.parseInt(day));
                    calender.set(calender.MONTH,m);
                    calender.set(calender.YEAR,Integer.parseInt(year));
                    AlarmManager alarm = new AlarmManager();
                    alarm.setAlarm(id,ApiActivity.this,calender,1);


                    if(dbhandler.addColor(id, Color.parseColor("#80C6FF")) &&
                            dbhandler.addIcon(id,R.drawable.ic_notifications_white_24dp)){
                        dbhandler.addReminder(id,subject,Integer.parseInt(minute),Integer.parseInt(hour),
                                Integer.parseInt(day),m,Integer.parseInt(year),"");
                    }
                    dbhandler.close();
                    resultTextView.setText("Subject : "+subject+"\n"
                            +"Time : "+Utility.timeFormat(hour+":"+minute)+"\n"+
                                    "Date : "+Utility.dateFormat(day+"/"+month+"/"+year));
                   /* resultTextView.setText("hour : "+hour+"minute :"+minute+"" +
                            "\n"+"day :"+day+"/"+"month :"+month+"/"+"year :"+year+"\n"+subject + "\n" + date + "\n" + time);*/

                    Thread thread = new Thread() {

                        @Override
                        public void run() {

                            try {
                                sleep(5000);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();

                } else if (result.getFulfillment().getSpeech().contains("scheduled")) {
                    String  date = "", time = "", subject = "";
                    String msgToSpeak = result.getFulfillment().getSpeech();
                    t1.speak(msgToSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    String parameterString = "";
                    if (result.getParameters() != null && !result.getParameters().isEmpty()) {
                        for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                            parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
                            if (entry.getKey().equals("Subject")) {
                                subject = entry.getValue().getAsString().toString();

                            } else if (entry.getKey().equals("date")) {
                                date = entry.getValue().getAsString().toString();

                            }  else if (entry.getKey().equals("time")) {
                                time = entry.getValue().getAsString().toString();
                            }
                        }
                    }
                    int id = Utility.getAlarmId(ApiActivity.this);
                    String hour, minute, day,month,year;
                    String time1[] = time.split(":");
                    String date1[] = date.split("-");
                    hour = time1[0];
                    minute = time1[1];
                    day = date1[2];
                    month = date1[1];
                    year = date1[0];
                    int m = Integer.parseInt(month);
                    m = m-1;
                    java.util.Calendar calender = java.util.Calendar.getInstance();
                    calender.set (java.util.Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
                    calender.set (java.util.Calendar.MINUTE, Integer.parseInt(minute));
                    calender.set(java.util.Calendar.SECOND,0);
                    calender.set(calender.DAY_OF_MONTH,Integer.parseInt(day));
                    calender.set(calender.MONTH,m);
                    calender.set(calender.YEAR,Integer.parseInt(year));
                    AlarmManager alarm = new AlarmManager();
                    alarm.setAlarm(id,ApiActivity.this,calender,1);



                    if(dbhandler.addColor(id, Color.parseColor("#80C6FF")) &&
                            dbhandler.addIcon(id,R.drawable.ic_notifications_white_24dp)){
                        dbhandler.addReminder(id,subject,Integer.parseInt(minute),Integer.parseInt(hour),
                                Integer.parseInt(day),m,Integer.parseInt(year),"");
                    }

                    dbhandler.close();
                    resultTextView.setText("Subject : "+subject+"\n"
                            +"Time : "+Utility.timeFormat(hour+":"+minute)+"\n"+
                            "Date : "+Utility.dateFormat(day+"/"+month+"/"+year));
                    Thread thread = new Thread() {

                        @Override
                        public void run() {

                            try {
                                sleep(5000);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();

                } else {
                    String msgToSpeak = result.getFulfillment().getSpeech();
                    t1.speak(msgToSpeak, TextToSpeech.QUEUE_FLUSH, null);

                }

                result = null;                // Show results in TextView.

            }
        });
    }


    @Override
    public void onError(final AIError error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                resultTextView.setText(error.toString());
            }
        });
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {
        listenButton.clearAnimation();

    }

    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
            t1.setLanguage(Locale.US);
            startCommunication();

        }
    }

}

package com.example.aamir.tablayout.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aamir.tablayout.AlarmService.AlarmManager;
import com.example.aamir.tablayout.Classes.Animation;
import com.example.aamir.tablayout.Classes.Utility;
import com.example.aamir.tablayout.Database.DatabaseHandler;
import com.example.aamir.tablayout.Dialogue.IconDialogue;
import com.example.aamir.tablayout.R;

import net.margaritov.preference.colorpicker.ColorPickerDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddReminder extends AppCompatActivity {

    @BindView(R.id.linear_layout)
    LinearLayout linearLaout;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.timePicker_add_reminder_timePicker)
    TimePicker timePicker;

    /*@BindView(R.id.linearLayout_add_reminder_Subject)
    LinearLayout linearLayoutAddSubject;*/
    @BindView(R.id.editText_add_reminder_subject)
    EditText addReminderSubject;
    @BindView(R.id.editText_add_reminder_message)
    EditText addReminderMessage;
    @BindView(R.id.linearLayout_add_reminder_date)
    LinearLayout linearLayoutAddDate;
    @BindView(R.id.textView_add_reminder_date)
    TextView addReminderDate;
    @BindView(R.id.linearLayout_add_reminder_participants)
    LinearLayout linearLayoutaddParticipants;
    @BindView(R.id.textView_add_reminder_participants)
    TextView textViewAddREminderColor;
    @BindView(R.id.imageView_add_reminder_participants)
    ImageView imageViewAddREminderColor;
    @BindView(R.id.linearLayout_add_reminder_icon)
    LinearLayout linearLayoutAddReminderIcon;
    public @BindView(R.id.imageView_add_reminder_icon)
    ImageView imageViewAddReminderIcon;
    public @BindView(R.id.textView_add_reminder_icon)
    TextView textviewAddReminderIcon;

    ColorPickerDialog colorPicker;

    final  Calendar cal = Calendar.getInstance();



    int color = Color.parseColor("#80C6FF");
    int Dialog_Date_Id = 1;
    int year_x,month_x,day_x,u_day_x,u_month_x,u_year_x,u_hour,u_minute;

    int uColor,uId;
    int uIcon;
    String update = null,uSubject = null,uMessage = null;
    public static int selectedIcon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        ButterKnife.bind(this);

        //disable auto focus on Edittext
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //set toolbar
        setSupportActionBar(toolBar);
        //show back arraow on toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        final FragmentManager fragmentManager = getFragmentManager();
        final IconDialogue iconDialogue = new IconDialogue();

       linearLayoutAddReminderIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                iconDialogue.show(fragmentManager,"ICON");
           }
       });


        pickCurrentDateToPicker();

        getBundle();

        initListner();




    }

    private void getBundle() {

        Bundle b = getIntent().getExtras();

        if(b!=null){

            uId = b.getInt("id");
            update = b.getString("update");
            uSubject = b.getString("subject");
            uMessage = b.getString("message");
            String uTime = b.getString("time");
            String uDate = b.getString("date");
            String []Time = uTime.split(":");
            String []Date = uDate.split("/");

            u_day_x = Integer.parseInt(Date[0]);
            u_month_x = Integer.parseInt(Date[1]);
            u_year_x = Integer.parseInt(Date[2]);

            u_hour = Integer.parseInt(Time[0]);
            u_minute = Integer.parseInt(Time[1]);



             uIcon = b.getInt("icon");
             uColor = b.getInt("color");

            if(update !=null)
                if(update.equals("true")){

                    addReminderSubject.setText(uSubject);
                    addReminderMessage.setText(uMessage);

                    addReminderDate.setText(Utility.dateFormat(u_day_x+"/"+u_month_x+"/"+u_year_x));

                    timePicker.setHour(u_hour);
                    timePicker.setMinute(u_minute);

                    cal.set(Calendar.DAY_OF_MONTH,u_day_x);
                    cal.set(Calendar.MONTH,u_month_x);
                    cal.set(Calendar.YEAR,u_year_x);

                    imageViewAddReminderIcon.setImageResource(uIcon);
                    imageViewAddREminderColor.setColorFilter(uColor, PorterDuff.Mode.MULTIPLY);

                    textViewAddREminderColor.setText("Edit Color");
                    textviewAddReminderIcon.setText("Change Icon");


                    if(selectedIcon == 0){
                        selectedIcon = uIcon;
                    }
                    if(color == Color.parseColor("#80C6FF")){
                        color = uColor;
                    }


                }
        }


    }

    private void initListner() {

        linearLayoutAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_Date_Id);
            }
        });

        linearLayoutaddParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AddReminder.this,ContactsActivity.class));

                colorPicker = new ColorPickerDialog(AddReminder.this,color);
                colorPicker.setAlphaSliderVisible(true);
                colorPicker.setHexValueEnabled(true);
                colorPicker.setTitle("Choose Color");
                colorPicker.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int col) {
                        color = col;
                        textViewAddREminderColor.setText("Custom Color");
                        imageViewAddREminderColor.setColorFilter(color,android.graphics.PorterDuff.Mode.MULTIPLY);

                    }
                });

                colorPicker.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
           onBackPressed(); // close this activity and return to preview activity (if there is any)
        }
        if(item.getItemId() == R.id.save){

            if(update!= null && update.equals("true")){

                u_day_x = day_x;
                u_month_x = month_x;
                u_year_x = year_x;

                updateReminder(uId,addReminderSubject.getText().toString(),addReminderMessage.getText().toString(),
                        timePicker.getCurrentHour(),timePicker.getCurrentMinute(),
                        u_day_x,u_month_x,u_year_x,selectedIcon,color);





            }else {
                if(addReminderSubject.getText().toString().trim().isEmpty()){

                    Snackbar.make(linearLaout,"Subject cannot be empty!",Snackbar.LENGTH_SHORT).show();
                    Animation.shakeView(addReminderSubject,this);
                }else{
                    String subject = addReminderSubject.getText().toString();
                    String message = addReminderMessage.getText().toString();

                    if(selectedIcon == 0){
                        selectedIcon = R.drawable.ic_notifications_white_24dp;
                    }else {

                    }

                    addReminder(Utility.getAlarmId(AddReminder.this),subject,timePicker.getCurrentMinute(),timePicker.getCurrentHour(),
                            day_x,month_x,year_x,message,selectedIcon);
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateReminder(int uId, String subject, String msg, Integer currentHour, Integer currentMinute,
                                int u_day_x, int u_month_x, int u_year_x, int selectedIcon, int color) {

        if(new DatabaseHandler(this).updateReminder(uId,subject,msg,currentHour,currentMinute,
                u_day_x,u_month_x,u_year_x,selectedIcon,color)){

            java.util.Calendar calender = java.util.Calendar.getInstance();
            calender.set (java.util.Calendar.HOUR_OF_DAY, currentHour);
            calender.set (java.util.Calendar.MINUTE, currentMinute);
            calender.set(java.util.Calendar.SECOND,0);
            calender.set(calender.DAY_OF_MONTH,u_day_x);
            calender.set(calender.MONTH,u_month_x);
            calender.set(calender.YEAR,u_year_x);
            AlarmManager alarm = new AlarmManager();
            alarm.setAlarm(uId,AddReminder.this,calender,1);


            Snackbar.make(linearLaout,"Successfully Update Reminder",Snackbar.LENGTH_SHORT).show();
            setDefaults();
            finish();

        }else {
            Toast.makeText(this, "Error while updating Reminder", Toast.LENGTH_SHORT).show();
        }

    }


    private void addReminder(int alarmId, String subject, Integer currentMinute,
                             Integer currentHour, int day_x, int month_x,
                             int year_x, String message,int icon) {
        DatabaseHandler database = new DatabaseHandler(this);

        if(database.addReminder(alarmId,subject,currentMinute,currentHour,day_x,month_x,year_x,message)){
            if(database.addIcon(alarmId,icon)){
                if(database.addColor(alarmId,color)){
                    java.util.Calendar calender = java.util.Calendar.getInstance();
                    calender.set (java.util.Calendar.HOUR_OF_DAY,currentHour);
                    calender.set (java.util.Calendar.MINUTE, currentMinute);
                    calender.set(java.util.Calendar.SECOND,0);
                    calender.set(calender.DAY_OF_MONTH,day_x);
                    calender.set(calender.MONTH,month_x);
                    calender.set(calender.YEAR,year_x);
                    AlarmManager alarm = new AlarmManager();
                    alarm.setAlarm(alarmId,AddReminder.this,calender,1);
                    finish();

                    //set default valuew
                    setDefaults();

                }else {
                    Toast.makeText(this, "Error Adding Color", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Error Adding Icon", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Error Adding", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDefaults() {

        textviewAddReminderIcon.setText("Default Icon");
        imageViewAddReminderIcon.setImageResource(R.drawable.ic_notifications_white_24dp);
        textViewAddREminderColor.setText("Default Color");
        imageViewAddREminderColor.setImageResource(R.drawable.ic_color_lens_white_24dp);
        color = Color.parseColor("#80C6FF");
        selectedIcon = 0;

    }


    public void showDatePickerDialog() {
        showDialog(Dialog_Date_Id);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if (id == Dialog_Date_Id) {
            return new DatePickerDialog(AddReminder.this, kDatePickerListner, year_x, month_x, day_x);
        }
        return null;
    }

    protected DatePickerDialog.OnDateSetListener kDatePickerListner =
            new DatePickerDialog.OnDateSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                    day_x = dayOfMonth;
                    month_x = month;
                    year_x = year;
                    Calendar cal = Calendar.getInstance();
                    if(day_x<cal.get(Calendar.DAY_OF_MONTH) || month_x<cal.get(Calendar.MONTH)
                            || year_x<cal.get(Calendar.YEAR)){
                        addReminderDate.setText("You can't pick previous date");
                        addReminderDate.setTextColor(getResources().getColor(R.color.errorColor, getResources().newTheme()));
                    }else {
                        addReminderDate.setTextColor(getResources().getColor(R.color.green, getResources().newTheme()));
                        String date = dayOfMonth+"/"+month+"/"+year;
                        addReminderDate.setText(Utility.dateFormat(date));
                    }
                }
            };


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pickCurrentDateToPicker() {

        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
    }


}

package com.example.aamir.tablayout.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.tablayout.Activities.AddReminder;
import com.example.aamir.tablayout.AlarmService.AlarmManager;
import com.example.aamir.tablayout.Classes.Utility;
import com.example.aamir.tablayout.Database.DatabaseHandler;
import com.example.aamir.tablayout.Modals.Reminder;
import com.example.aamir.tablayout.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 8/2/2017.
 */

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ViewHolder> {

    List<Reminder> reminderList;
    Context context;

    public ReminderListAdapter(List<Reminder> reminderList, Context context) {
        this.reminderList = reminderList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder_list1,parent,false);
        return new ViewHolder(view,reminderList,context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Reminder reminder = reminderList.get(position);

        final int color = new DatabaseHandler(context).getColor(reminder.getID());
        final int icon = new DatabaseHandler(context).getIcon(reminder.getID());

        holder.Subject.setText(reminder.getSubject());
        holder.Time.setText(Utility.timeFormat(reminder.getTime()));




        if (position > 0 && reminderList.get(position).getDate().equals(reminderList.get(position - 1).getDate()) ) {

            holder.headerSeperator.setVisibility(View.GONE);
        } else {

            holder.headerSeperator.setText(Utility.dateFormat(reminder.getDate()));
            holder.headerSeperator.setVisibility(View.VISIBLE);
            holder.headerSeperator.setTextColor(color);
        }

        GradientDrawable bgShape = (GradientDrawable) holder.circle.getDrawable();
        bgShape.setColor(color);
        holder.circleIcon.setImageResource(icon);

        holder.aSwitch.setOnCheckedChangeListener(null);
        if(reminder.getFlag() == 1){
            holder.aSwitch.setChecked(true);
        }else {
            holder.aSwitch.setChecked(false);
        }
        holder.longclickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sending_intent = new Intent(context, AddReminder.class);
                sending_intent.putExtra("update","true");
                sending_intent.putExtra("id",reminder.getID());
                sending_intent.putExtra("subject",reminder.getSubject());
                sending_intent.putExtra("message",reminder.getMessage());
                sending_intent.putExtra("date",reminder.getDate());
                sending_intent.putExtra("time",reminder.getTime());
                sending_intent.putExtra("icon",icon);
                sending_intent.putExtra("color",color);

                context.startActivity(sending_intent);

            }
        });

        holder.longclickable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?")
                        .setMessage("Delete this Reminder?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseHandler databaseHandler = new DatabaseHandler(context);
                                if(databaseHandler.deleteReminder(reminderList.get(position).getID())){

                                    //disable reminder

                                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                                    AlarmManager alarmManager1 = new AlarmManager();
                                    alarmManager1.setAlarm(reminderList.get(position).getID(),context,calendar,0);

                                   /* reminderList.remove(position);
                                    notifyDataSetChanged();*/

                                    Intent updateIntent = new Intent("BROADCAST_REFRESH");
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);



                                    Snackbar.make(holder.itemView,"Successfully removed Reminder",Snackbar.LENGTH_SHORT).show();
                                }else {
                                    Snackbar.make(holder.itemView,"Error occur...",Snackbar.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return false;
            }
        });
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                java.util.Calendar calendar = java.util.Calendar.getInstance();
                AlarmManager alarmManager1 = new AlarmManager();
                if(isChecked){

                    String []time = reminderList.get(position).getTime().split(":");
                    String []date = reminderList.get(position).getDate().split("/");
                    int hour = Integer.parseInt(time[0]);
                    int minute = Integer.parseInt(time[1]);
                    int day = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]);
                    int year = Integer.parseInt(date[2]);

                    if(new DatabaseHandler(context).UpdateFlag(reminderList.get(position).getID(),1)){
                        calendar.set(java.util.Calendar.HOUR_OF_DAY,hour);
                        calendar.set(java.util.Calendar.MINUTE,minute);
                        calendar.set(java.util.Calendar.SECOND,0);
                        calendar.set(java.util.Calendar.DAY_OF_MONTH,day);
                        calendar.set(java.util.Calendar.MONTH,month);
                        calendar.set(java.util.Calendar.YEAR,year);
                        alarmManager1.setAlarm(reminderList.get(position).getID(),context,calendar,1);

                        reminder.setFlag(1);

                        Snackbar.make(holder.itemView,"Reminder Activated",Snackbar.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Error Occur...", Toast.LENGTH_SHORT).show();
                    }

                }else {

                   if( new DatabaseHandler(context).UpdateFlag(reminderList.get(position).getID(),0)){
                       alarmManager1.setAlarm(reminderList.get(position).getID(),context,calendar,0);
                       Snackbar.make(holder.itemView,"Reminder is Deactivated",Snackbar.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(context, "Error Occur...", Toast.LENGTH_SHORT).show();
                    }

                    reminder.setFlag(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private List<Reminder> reminderList;
        private Context ctx;
        @BindView(R.id.reminder_item_subject)
        TextView Subject;
        @BindView(R.id.reminder_item_time)
        TextView Time;
        @BindView(R.id.notification_card)
        CardView longclickable;
        @BindView(R.id.switch_reminder_active)
        SwitchCompat aSwitch;
        @BindView(R.id.notification_circle)
        ImageView circle;
        @BindView(R.id.notification_icon)
        ImageView circleIcon;
        @BindView(R.id.header_separator)
        TextView headerSeperator;
        public ViewHolder(View itemView,List<Reminder> reminderList,Context context) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.ctx = context;
            this.reminderList = reminderList;
        }


    }



}

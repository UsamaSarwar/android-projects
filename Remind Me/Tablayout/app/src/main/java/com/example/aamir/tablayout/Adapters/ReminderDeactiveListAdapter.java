package com.example.aamir.tablayout.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.tablayout.AlarmService.AlarmManager;
import com.example.aamir.tablayout.Classes.Utility;
import com.example.aamir.tablayout.Database.DatabaseHandler;
import com.example.aamir.tablayout.Modals.Reminder;
import com.example.aamir.tablayout.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 8/8/2017.
 */

public class ReminderDeactiveListAdapter extends RecyclerView.Adapter<ReminderDeactiveListAdapter.ViewHolder> {

    Context context;
    List<Reminder> reminderList;

    public ReminderDeactiveListAdapter(Context context, List<Reminder> reminders) {
        this.context = context;
        this.reminderList = reminders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder_list_deactive,parent,false);


        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final Reminder reminder = reminderList.get(position);

        int color = new DatabaseHandler(context).getColor(reminder.getID());
        final int icon = new DatabaseHandler(context).getIcon(reminder.getID());

        holder.reminderSubject.setText(reminder.getSubject());
        holder.reminderContent.setText(reminder.getMessage());
        holder.reminderTime.setText(Utility.timeFormat(reminder.getTime()));


        if (position > 0 && reminderList.get(position).getDate().equals(reminderList.get(position - 1).getDate()) ) {

            holder.headerSepertor.setVisibility(View.GONE);
        } else {

            holder.headerSepertor.setText(Utility.dateFormat(reminder.getDate()));
            holder.headerSepertor.setVisibility(View.VISIBLE);
            holder.headerSepertor.setTextColor(color);
        }

        GradientDrawable bgShape = (GradientDrawable) holder.reminderColor.getDrawable();
        bgShape.setColor(color);
        holder.reminderIcon.setImageResource(icon);



        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
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


                                   /* reminderList.remove(position);
                                    notifyDataSetChanged();*/


                                    Intent updateIntent = new Intent("BROADCAST_REFRESH");
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);


                                    Snackbar.make(holder.itemView,"Successfully removed History",Snackbar.LENGTH_SHORT).show();
                                }else {
                                    Snackbar.make(holder.itemView,"Error occured",Snackbar.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.notification_card_de_active)
        CardView cardView;
        @BindView(R.id.header_separator_de_active)
        TextView headerSepertor;
        @BindView(R.id.notification_circle_de_active)
        ImageView reminderColor;
        @BindView(R.id.notification_icon_de_active)
        ImageView reminderIcon;
        @BindView(R.id.reminder_item_subject_de_active)
        TextView reminderSubject;
        @BindView(R.id.reminder_item_content_de_active)
        TextView reminderContent;
        @BindView(R.id.reminder_item_time_de_active)
        TextView reminderTime;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

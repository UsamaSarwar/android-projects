package com.example.aamir.tablayout.Fragments;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.tablayout.Activities.AddReminder;
import com.example.aamir.tablayout.Activities.MainActivity;
import com.example.aamir.tablayout.Adapters.ReminderDeactiveListAdapter;
import com.example.aamir.tablayout.Adapters.ReminderListAdapter;
import com.example.aamir.tablayout.Database.DatabaseHandler;
import com.example.aamir.tablayout.Modals.Reminder;
import com.example.aamir.tablayout.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeActiveReminderList extends Fragment {

    @BindView(R.id.recycler_view_reminder_list_de_active)
    RecyclerView recyclerView;
    ReminderDeactiveListAdapter adapter;
    @BindView(R.id.image_view_de_active)
    ImageView imageViewDeactive;
    @BindView(R.id.textView_de_active)
    TextView textViewDeactive;
    List<Reminder> reminderList = new ArrayList<>();


    public DeActiveReminderList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_de_active_reminder_list, container, false);
        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        reminderList = getListData();
        adapter = new ReminderDeactiveListAdapter(getActivity(),reminderList);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount() == 0){
            imageViewDeactive.setVisibility(View.VISIBLE);
            textViewDeactive.setVisibility(View.VISIBLE);
        }


        return view;
    }



    private List<Reminder> getListData(){
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        List<Reminder> list = databaseHandler.getAllReminders(0);
        databaseHandler.close();
        return list;
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            update();
        }
    };

    @Override
    public void onResume() {

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("BROADCAST_REFRESH"));
        update();
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }


    public void update(){

        reminderList.clear();
        reminderList.addAll(getListData());
        adapter.notifyDataSetChanged();

        if(adapter.getItemCount() == 0){
            textViewDeactive.setVisibility(View.VISIBLE);
            imageViewDeactive.setVisibility(View.VISIBLE);
        }else {
            imageViewDeactive.setVisibility(View.GONE);
            textViewDeactive.setVisibility(View.GONE);
        }

    }

}

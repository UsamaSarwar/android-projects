package com.example.aamir.tablayout.Fragments;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
public class ActiveReminderList extends Fragment {


    @BindView(R.id.recycler_view_reminder_list)
    RecyclerView recyclerViewReminderList;
    @BindView(R.id.image_view_active)
    ImageView imageViewActive;
    @BindView(R.id.textView_active)
    TextView textViewActive;

    List<Reminder>reminderList = new ArrayList<>();
    ReminderListAdapter reminderListAdapter;


    static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public ActiveReminderList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_active_reminder_list, container, false);
        ButterKnife.bind(this,view);

        //check permissions
        checkAndRequestPermissions();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewReminderList.setLayoutManager(layoutManager);

        reminderList = getListData();
        reminderListAdapter = new ReminderListAdapter(reminderList,getActivity());
        recyclerViewReminderList.setAdapter(reminderListAdapter);


        if(reminderListAdapter.getItemCount() == 0){
            imageViewActive.setVisibility(View.VISIBLE);
            textViewActive.setVisibility(View.VISIBLE);
        }


        initFab(view);





        return view;
    }



    private void initFab(View view) {
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);


        recyclerViewReminderList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(dy > 0){
                    fab.hide();
                } else{
                    fab.show();
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddReminder.class);
                startActivity(intent);
            }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           update();
        }
    };


    private boolean checkAndRequestPermissions() {

        int recordAudio = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (recordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

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
        reminderListAdapter.notifyDataSetChanged();

        if(reminderListAdapter.getItemCount() == 0){
            imageViewActive.setVisibility(View.VISIBLE);
            textViewActive.setVisibility(View.VISIBLE);
        }else {
            imageViewActive.setVisibility(View.GONE);
            textViewActive.setVisibility(View.GONE);
        }

    }

    private List<Reminder> getListData(){
        DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
        List<Reminder> list = databaseHandler.getAllReminders(1);
        databaseHandler.close();
        return list;
    }


}

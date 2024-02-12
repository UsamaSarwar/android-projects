package com.example.aamir.tablayout.Dialogue;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.aamir.tablayout.Activities.AddReminder;
import com.example.aamir.tablayout.Adapters.IconGridAdapter;
import com.example.aamir.tablayout.Modals.Icon;
import com.example.aamir.tablayout.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 8/4/2017.
 */

public class IconDialogue extends DialogFragment {

    List<Icon> iconList = new ArrayList<>();
    final int gridColumn = 5;

    @BindView(R.id.gridView_icon_gridView)
    GridView gridView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.icon_grid_layout,null);

        ButterKnife.bind(this,view);


        String [] icons = this.getResources().getStringArray(R.array.icons_string_array);
        for(int i=0; i<icons.length; i++){
            iconList.add(new Icon(i,icons[i]));
        }

        IconGridAdapter iconGridAdapter = new IconGridAdapter(getActivity(),iconList);

        gridView.setAdapter(iconGridAdapter);

       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               id = getActivity().getResources().getIdentifier(iconList.get(position).getName(),
                       "drawable",getActivity().getPackageName());
               ((AddReminder)getActivity()).imageViewAddReminderIcon.setImageResource((int) id);
               ((AddReminder)getActivity()).textviewAddReminderIcon.setText("Custom Icon");
               ((AddReminder)getActivity()).selectedIcon = ((int) id);
               getDialog().cancel();
           }
       });









        return view;
    }
}

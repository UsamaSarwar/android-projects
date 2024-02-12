package com.example.aamir.tablayout.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aamir.tablayout.Activities.AddReminder;
import com.example.aamir.tablayout.Dialogue.IconDialogue;
import com.example.aamir.tablayout.Modals.Icon;
import com.example.aamir.tablayout.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 8/4/2017.
 */

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {

    Context context;
    List<Icon> iconList;

    public IconAdapter(Context context, List<Icon> iconList) {
        this.context = context;
        this.iconList = iconList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row,parent,false);


        return new ViewHolder(view,iconList,context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Icon drawableIcon = iconList.get(position);
        final int id = context.getResources().getIdentifier(drawableIcon.getName(),"drawable",context.getPackageName());
        holder.gridIconImage.setImageResource(id);

    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        List<Icon> iconList;
        Context ctx;

        @BindView(R.id.grid_icon_image_view)
        ImageView gridIconImage;

        public ViewHolder(View itemView, List<Icon> iconList, Context ctx) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);


            this.iconList = iconList;
            this.ctx = ctx;
        }


        @Override
        public void onClick(View v) {

            Icon icon = iconList.get(getAdapterPosition());
            Toast.makeText(ctx,icon.getName()+"",Toast.LENGTH_SHORT).show();

            int id = ctx.getResources().getIdentifier(icon.getName(),
                    "drawable",ctx.getPackageName());
            ((AddReminder)ctx).imageViewAddReminderIcon.setImageResource(id);
            ((AddReminder)ctx).textviewAddReminderIcon.setText("Custom Icon");


        }
    }
}

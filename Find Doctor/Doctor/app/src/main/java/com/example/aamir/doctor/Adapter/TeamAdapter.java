package com.example.aamir.doctor.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aamir.doctor.Modal.TeamModal;
import com.example.aamir.doctor.R;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 8/18/2017.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    Context context;
    List<TeamModal> teamModalList;

    public TeamAdapter(Context context, List<TeamModal> teamModalList) {
        this.context = context;
        this.teamModalList = teamModalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_about_team,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Typeface typeFaceRegular = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Bold.otf");


        TeamModal teamModal = teamModalList.get(position);

        String []nameArray = teamModal.getPerson_name().split(",");

        holder.name.setHtml(nameArray[0]);
        holder.name.setTypeface(typeFaceBold);
        if(!(nameArray.length<2)){
            holder.position1.setText(nameArray[1]);
            holder.position1.setTypeface(typeFaceBold);
            holder.position1.setVisibility(View.VISIBLE);
        }


        holder.position.setText(teamModal.getPerson_prof());
        holder.position.setTypeface(typeFaceRegular);

        holder.headerSeparator.setText(teamModal.getPerson_category());
        holder.headerSeparator.setTypeface(typeFaceBold);

        if(teamModal.getPerson_image().equals("")){
            holder.image.setImageResource(R.drawable.login_back);
        }else {
            Picasso.with(context).load(teamModal.getPerson_image())
                    .placeholder(R.drawable.boss)
                    .resize(200,200)
                    .into(holder.image);
        }

        if(position>0){
            if(teamModalList.get(position).getPerson_category().equals(teamModalList.get(position-1).getPerson_category())){
                holder.headerSeparator.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return teamModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_team_name)
        HtmlTextView name;
        @BindView(R.id.item_team_position1)
        TextView position1;
        @BindView(R.id.item_team_position)
        TextView position;
        @BindView(R.id.item_team_image)
        ImageView image;
        @BindView(R.id.item_team_header_separator)
        TextView headerSeparator;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}

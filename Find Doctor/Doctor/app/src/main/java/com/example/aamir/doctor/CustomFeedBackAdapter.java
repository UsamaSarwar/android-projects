package com.example.aamir.doctor;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.aamir.doctor.Classes.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 5/31/2017.
 */

public class CustomFeedBackAdapter extends RecyclerView.Adapter<CustomFeedBackAdapter.ViewHolder> {

    Context context;
    List<ModalFeedBack> feedBackList;

    View view ;
    private int lastPosition = -1;

    public CustomFeedBackAdapter(Context context, List<ModalFeedBack> feedBackList) {
        this.context = context;
        this.feedBackList = feedBackList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_back_item_row,parent,false);

        view = v;
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        ModalFeedBack modalFeedBack = feedBackList.get(position);
        holder.cardText.setText(modalFeedBack.getFeedBack());
        holder.userText.setText(modalFeedBack.getUser());
        String[] date = modalFeedBack.getTime().split(" ");
        holder.dateText.setText(Utility.dateFormat(date[0]));

        setTypeFace(holder);




    }

    private void setTypeFace(ViewHolder holder) {
        Typeface typeFaceRegular = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Bold.otf");

        holder.cardText.setTypeface(typeFaceBold);
        holder.userText.setTypeface(typeFaceRegular);
        holder.dateText.setTypeface(typeFaceRegular);
    }



    @Override
    public int getItemCount() {
        return feedBackList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textView_card_feed_back_content)
        TextView cardText;
        @BindView(R.id.textView_card_feed_back_user)
        TextView userText;
        @BindView(R.id.textView_card_feed_back_date)
        TextView dateText;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

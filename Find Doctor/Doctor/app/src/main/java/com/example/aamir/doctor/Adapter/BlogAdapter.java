package com.example.aamir.doctor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.BlogDetail;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.Modal.BlogModal;
import com.example.aamir.doctor.R;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aamir on 8/21/2017.
 */

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {

    Context context;
    List<BlogModal> blogModalList;

    public BlogAdapter(Context context, List<BlogModal> blogModalList) {
        this.context = context;
        this.blogModalList = blogModalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Typeface typeface1 = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.otf");

        final BlogModal blogList = blogModalList.get(position);


        final String []date = blogList.getDatetime().split(" ");
        holder.date.setText(Utility.dateFormat(date[0]));
        holder.date.setTypeface(typeface1);

        holder.heading.setHtml(blogList.getHeading());
        holder.heading.setTypeface(typeface1);

        holder.shortDesc.setHtml(blogList.getShort_desc());
        holder.shortDesc.setTypeface(typeface1);

        holder.detail.setTypeface(typeface1);

        if(blogList.getPhoto().equals("")){
            holder.image.setImageResource(R.drawable.login_back);
        }else {
            Picasso.with(context).load(blogList.getPhoto()).resize(350,200).into(holder.image);
        }


        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BlogDetail.class);
                intent.putExtra("blog_id",blogList.getBlog_id());
                intent.putExtra("photo",blogList.getPhoto());
                intent.putExtra("short_desc",blogList.getShort_desc());
                intent.putExtra("blog_heading",blogList.getHeading());
                intent.putExtra("date",Utility.dateFormat(date[0]));

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return blogModalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textView_blog_date)
        TextView date;
        @BindView(R.id.textView_blog_heading)
        HtmlTextView heading;
        @BindView(R.id.imageView_blog_image)
        ImageView image;
        @BindView(R.id.textView_blog_short_desc)
        HtmlTextView shortDesc;
        @BindView(R.id.button_blog_detail)
        Button detail;
        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}

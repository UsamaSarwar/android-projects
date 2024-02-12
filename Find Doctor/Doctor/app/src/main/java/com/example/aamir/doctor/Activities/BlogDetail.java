package com.example.aamir.doctor.Activities;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetail extends AppCompatActivity {

    @BindView(R.id.heading)
    TextView toolBarHeading;
    @BindView(R.id.back)
    ImageView Back;
    @BindView(R.id.imageView_blog_image)
    ImageView image;
    @BindView(R.id.textView_blog_date)
    TextView date;
    @BindView(R.id.textView_blog_short_desc)
    HtmlTextView short_desc;
    @BindView(R.id.textView_blog_long_desc)
    HtmlTextView long_desc;
    @BindView(R.id.textView_blog_heading)
    HtmlTextView heading;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private static int blogId;
    private static String dateText;
    private static String photo;
    private static String shortDesc;
    private static String blogHeading;

    Typeface typeFaceRegular;
    Typeface typeFaceBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        ButterKnife.bind(this);

        getBundle();

        initListner();

        typeFaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");

        toolBarHeading.setTypeface(typeFaceBold);


        if(Utility.isOnline(BlogDetail.this)){
            setValues();
            loadData();
        }else {
            final android.app.FragmentManager fragmentManager = getFragmentManager();
            final CustomDialogue customDialogue = new CustomDialogue();
            customDialogue.show(fragmentManager, "my Fragment");
        }





    }

    private void initListner() {

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void loadData() {

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<String> call = service.getBlogLongDesc(blogId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String longDesc = response.body();
                long_desc.setHtml(longDesc);
                long_desc.setTypeface(typeFaceRegular);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
            }
        });



    }

    private void setValues() {



        heading.setHtml(blogHeading);
        short_desc.setHtml(shortDesc);
        date.setText(dateText);

        heading.setTypeface(typeFaceRegular);
        short_desc.setTypeface(typeFaceRegular);
        date.setTypeface(typeFaceRegular);

        Picasso.with(BlogDetail.this).load(photo).resize(350,200).into(image);



    }

    private void getBundle() {

        Bundle b = getIntent().getExtras();
        if(b!=null){

            blogId = b.getInt("blog_id");
            dateText = b.getString("date");
            photo = b.getString("photo");
            blogHeading = b.getString("blog_heading");
            shortDesc = b.getString("short_desc");


        }



    }


}

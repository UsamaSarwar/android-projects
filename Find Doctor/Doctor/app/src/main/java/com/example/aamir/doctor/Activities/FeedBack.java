package com.example.aamir.doctor.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedBack extends AppCompatActivity {

    @BindView(R.id.back_feedback)
    ImageView backFeedback;
    @BindView(R.id.textView_dr_name_feedback)
    TextView drName;
    @BindView(R.id.editText_feedback_comments)
    EditText comments;
    @BindView(R.id.button_feedback_send)
    Button senFeedback;
    @BindView(R.id.appName)
            TextView toolBarHeading;
    @BindView(R.id.feedback_text_1)
            TextView text1;
    @BindView(R.id.feedback_text_2)
            TextView text2;

    int drId;
    String drNAme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        ButterKnife.bind(this);


        getBundle();



        initListner();

        setTypeFace();


    }

    private void setTypeFace() {

        Typeface typeFaceReguar = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");

        drName.setTypeface(typeFaceBold);
        text1.setTypeface(typeFaceReguar);
        text2.setTypeface(typeFaceReguar);
        toolBarHeading.setTypeface(typeFaceBold);
        comments.setTypeface(typeFaceReguar);
        senFeedback.setTypeface(typeFaceReguar);

    }

    private void initListner() {

        senFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(FeedBack.this)){

                    if(Utility.isEmpty(comments,comments.getText().toString(),FeedBack.this)){
                        sendFeedBack();
                    }



                }else {
                    final android.app.FragmentManager fragmentManager = getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                }

            }
        });

        backFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendFeedBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(FeedBack.this);
        builder.setView(R.layout.loading_dialogue);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        SharedPreferences sp = getSharedPreferences("User",MODE_PRIVATE);
        try {

            APIService service = ApiClient.getClient().create(APIService.class);

           Call<String> call = service.sendFeed(drId,sp.getString("name",""),comments.getText().toString());

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String respons = response.body();
                    dialog.dismiss();
                    comments.setText("");
                    Toast.makeText(FeedBack.this, respons, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(FeedBack.this, "Weak internet connection!", Toast.LENGTH_SHORT).show();
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void getBundle() {


        Bundle b = getIntent().getExtras();
        drId = b.getInt("dr_id");
        drNAme = b.getString("dr_name");
        drName.setText(drNAme);

    }




}

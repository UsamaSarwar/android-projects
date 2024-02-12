package com.example.aamir.doctor.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.ModalFeedBack;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs extends AppCompatActivity {

    @BindView(R.id.editText_contact_us_name)
    EditText name;
    @BindView(R.id.editText_contact_us_phone)
    EditText phone;
    @BindView(R.id.editText_contact_us_subject)
    EditText subject;
    @BindView(R.id.editText_contact_us_message)
    EditText message;
    @BindView(R.id.button_contact_us_submit)
    Button submit;

    @BindView(R.id.name)
    TextView nameHead;
    @BindView(R.id.phone)
    TextView phoneHead;
    @BindView(R.id.subject)
    TextView subjectHead;
    @BindView(R.id.message)
    TextView messageHead;
    @BindView(R.id.heading)
    TextView toolBarHeading;
    @BindView(R.id.description)
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ButterKnife.bind(this);


        //prevent to auto open keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //pass textwatcher to edit texts



        setTypeFace();





        initListner();
    }

    private void setTypeFace() {

        Typeface typeFaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");

        name.setTypeface(typeFaceRegular);
        nameHead.setTypeface(typeFaceBold);
        phone.setTypeface(typeFaceRegular);
        phoneHead.setTypeface(typeFaceBold);
        subject.setTypeface(typeFaceRegular);
        subjectHead.setTypeface(typeFaceBold);
        message.setTypeface(typeFaceRegular);
        messageHead.setTypeface(typeFaceBold);
        toolBarHeading.setTypeface(typeFaceBold);
        submit.setTypeface(typeFaceRegular);
        desc.setTypeface(typeFaceRegular);

    }

    private void initListner() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isOnline(ContactUs.this)){

                    if(Utility.isInvalidName(name,name.getText().toString(),ContactUs.this) &&
                            Utility.isValidMobile(phone,phone.getText().toString(),ContactUs.this) &&
                            Utility.isEmpty(subject,subject.getText().toString(),ContactUs.this) &&
                            Utility.isEmpty(message,message.getText().toString(),ContactUs.this)) {

                        sendData();

                    }



                }else {
                    final android.app.FragmentManager fragmentManager = getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                }
            }
        });
    }

    private void sendData() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ContactUs.this);
        builder.setView(R.layout.loading_dialogue);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


       try {
           APIService service = ApiClient.getClient().create(APIService.class);

           Call<String> call = service.sendContact(name.getText().toString(),phone.getText().toString(),
                   subject.getText().toString(),message.getText().toString());

           call.enqueue(new Callback<String>() {
               @Override
               public void onResponse(Call<String> call, Response<String> response) {

                   String respons = response.body();
                   Toast.makeText(ContactUs.this, "Thanks for contact us, we will shortly contact you...", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                   setFieldsToNull();

               }

               @Override
               public void onFailure(Call<String> call, Throwable t) {

                   Toast.makeText(ContactUs.this, "Weak internet connection!", Toast.LENGTH_SHORT).show();
                   dialog.dismiss();
               }
           });
       }catch (Exception e){
           e.printStackTrace();
       }

    }

    private void setFieldsToNull() {
        name.setText("");
        phone.setText("");
        subject.setText("");
        message.setText("");
    }



}

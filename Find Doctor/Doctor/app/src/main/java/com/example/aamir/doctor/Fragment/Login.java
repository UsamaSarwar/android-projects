package com.example.aamir.doctor.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.ChooseLocation;
import com.example.aamir.doctor.Activities.LoginSignup;
import com.example.aamir.doctor.Activities.MainActivity;
import com.example.aamir.doctor.Classes.Animation;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aamir on 8/16/2017.
 */

public class Login extends Fragment {

    @BindView(R.id.editText_login_user_name)
    EditText name;
    @BindView(R.id.editText_login_password)
    EditText password;
    @BindView(R.id.button_login_submit)
    Button submit;
    View view;

    @BindView(R.id.user_name)
    TextView nameHead;
    @BindView(R.id.password)
    TextView passwordHead;

    @BindView(R.id.linear_layout_user_name_login)
    LinearLayout linerUserName;
    @BindView(R.id.linear_layout_password_login)
    LinearLayout linearPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.log_in,container,false);

        ButterKnife.bind(this,view);


        //checkFieldsForEmptyValues();

        initListner();

        setTypeFace();

        return view;
    }

    private void setTypeFace() {

        Typeface typeFaceRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quicksand-Bold.otf");

        name.setTypeface(typeFaceRegular);
        nameHead.setTypeface(typeFaceBold);

        password.setTypeface(typeFaceRegular);
        passwordHead.setTypeface(typeFaceBold);

        submit.setTypeface(typeFaceRegular);


    }

    private void initListner() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isOnline(getActivity())){


                   if(Utility.isValidMail(name,name.getText().toString(),getActivity()) &&
                           Utility.isValidPassword(password,password.getText().toString(),getActivity())){

                       sendData(name.getText().toString(),password.getText().toString());
                   }



                }else {
                    final android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                }

            }
        });
    }


    private void sendData(String email,String pass) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.loading_dialogue);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        try {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<String> call = service.userLogin(email,pass);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String respons = response.body();
                    String []resp = respons.split(",");
                    if(resp[0].equals("Login Success")){
                        dialog.dismiss();

                        setPrefrences(resp[1]);
                        setAllFieldsToNull();
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        Toast.makeText(getActivity(), "Login success", Toast.LENGTH_SHORT).show();
                    }else if(respons.equals("Login Failed")){

                        Animation.shakeView(linerUserName,getActivity());
                        Animation.shakeViewReverse(linearPassword,getActivity());
                        name.setError("User name or password is wrong");
                        password.setError("User name or password is wrong");
                        dialog.dismiss();
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Snackbar.make(view,"Weak internet connection or an un expected error occurred...Try again!",Snackbar.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void setPrefrences(String name1) {

        SharedPreferences sp = getActivity().getSharedPreferences("User",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",name1);
        editor.putString("email","");
        editor.putString("user_name",name.getText().toString());
        editor.apply();
    }


    private void setAllFieldsToNull() {
        name.setText("");
        password.setText("");
    }





    private void checkFieldsForEmptyValues() {

        String Nam = name.getText().toString();
        String paswrd = password.getText().toString();

        if(Nam.equals("")  || paswrd.equals("")){
            submit.setEnabled(false);
        }else {
            submit.setEnabled(true);
        }
    }




}

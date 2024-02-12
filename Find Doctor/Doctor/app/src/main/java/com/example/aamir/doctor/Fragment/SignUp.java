package com.example.aamir.doctor.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.Activities.*;
import com.example.aamir.doctor.Classes.Animation;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aamir on 8/16/2017.
 */

public class SignUp extends Fragment {
    @BindView(R.id.editText_create_account_name)
    EditText name;
    @BindView(R.id.editText_create_account_user_name)
    EditText userName;
    @BindView(R.id.editText_create_account_confirm_password)
    EditText confirmPassword;
    @BindView(R.id.editText_create_account_password)
    EditText password;
    @BindView(R.id.button_create_account_submit)
    Button submit;
    @BindView(R.id.password_error)
    ImageView passwordError;
    @BindView(R.id.confirm_password_error)
    ImageView confirmPasswordError;

    @BindView(R.id.name)
            TextView nameHead;
    @BindView(R.id.user_name)
            TextView userNameHead;
    @BindView(R.id.password)
            TextView passwordHead;
    @BindView(R.id.confirm_password)
            TextView confirmPasswrodHead;

    @BindView(R.id.linear_layout_password_signup)
    LinearLayout linerPassword;
    @BindView(R.id.linear_layout_confirm_password_signup)
    LinearLayout confirmLinerPassword;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.sign_up,container,false);

        ButterKnife.bind(this,view);






        initListner();

        setTypeFace();

        return view;
    }

    private void setTypeFace() {

        Typeface typeFaceRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Quicksand-Bold.otf");

        name.setTypeface(typeFaceRegular);
        nameHead.setTypeface(typeFaceBold);

        userName.setTypeface(typeFaceRegular);
        userNameHead.setTypeface(typeFaceBold);

        password.setTypeface(typeFaceRegular);
        passwordHead.setTypeface(typeFaceBold);

        confirmPassword.setTypeface(typeFaceRegular);
        confirmPasswrodHead.setTypeface(typeFaceBold);

        submit.setTypeface(typeFaceRegular);
    }


    private void initListner() {


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isOnline(getActivity())){


                       if(Utility.isInvalidName(name,name.getText().toString(),getActivity()) &&
                               Utility.isValidMail(userName,userName.getText().toString(),getActivity()) &&
                               Utility.isValidPassword(password,password.getText().toString(),getActivity()) &&
                               Utility.isValidPassword(confirmPassword,confirmPassword.getText().toString(),getActivity())){


                           if(password.getText().toString().equals(confirmPassword.getText().toString())){

                               sendData();
                           }else {
                               Animation.shakeView(password,getActivity());
                               Animation.shakeViewReverse(confirmPassword,getActivity());
                               confirmPassword.setError("password not matched the confirm password");
                               password.setError("password not matched the confirm password");
                           }


                       }


                }else {
                    final android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                }
            }
        });


    }

    private void sendData() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.loading_dialogue);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        APIService service = ApiClient.getClient().create(APIService.class);
        Call<String> call = service.regUser(name.getText().toString(),userName.getText().toString(),
                password.getText().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String respons = response.body();
                Toast.makeText(getActivity(), respons, Toast.LENGTH_SHORT).show();
                dialog.dismiss();


                if(respons.equals("User already exists")){

                    Animation.shakeView(userName,getActivity());
                    userName.setError("This user already exists, try another one");

                }else if(respons.equals("Registration unsuccess")){

                }else if(respons.equals("Registration Success")){
                    setPrefrences();
                    setFieldsToNull();
                    getActivity().finish();

                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Toast.makeText(getActivity(), "Your account has been created", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(view,"Weak internet connection or an un expected error occurred...Try again!",Snackbar.LENGTH_SHORT).show();
            }
        });
    }



    private void setPrefrences() {
        SharedPreferences sp = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",name.getText().toString());
        editor.putString("user_name",userName.getText().toString());
        editor.putString("email","");
        editor.apply();
    }


    private void setFieldsToNull() {

        name.setText("");
        userName.setText("");
        password.setText("");
        confirmPassword.setText("");
    }


}

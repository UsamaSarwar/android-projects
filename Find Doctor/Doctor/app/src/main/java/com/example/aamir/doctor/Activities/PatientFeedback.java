package com.example.aamir.doctor.Activities;

import android.graphics.Typeface;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctor.CustomFeedBackAdapter;
import com.example.aamir.doctor.DoctorListAdapter.ModalDrList;
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

public class PatientFeedback extends AppCompatActivity {

    @BindView(R.id.back_patient_feed_back)
    ImageView backPatientFeedBack;
    @BindView(R.id.textView_patient_feedback_dr_name)
    TextView drName;
    @BindView(R.id.textView_patient_feedback_dr_education)
    TextView drEdu;
    @BindView(R.id.textView_patient_feedback_dr_experience)
    TextView drExperience;
    @BindView(R.id.recyclerView_patient_feed_back)
    RecyclerView recyclerViewPatient;
    @BindView(R.id.imageView_patient_feedback_inactive)
            ImageView inactiveImage;
    @BindView(R.id.appName)
            TextView toolBarHeading;

    int drId;
    String drNameText,drExperienceText,drEducationText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_feedback);

        ButterKnife.bind(this);


        getBundle();
        
        initListner();

        getFeedbackData();

        setTypeFace();


    }

    private void setTypeFace() {

        Typeface typeFaceRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf");
        Typeface typeFaceBold = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");

        drName.setTypeface(typeFaceBold);
        drEdu.setTypeface(typeFaceRegular);
        drExperience.setTypeface(typeFaceRegular);
        toolBarHeading.setTypeface(typeFaceBold);
        toolBarHeading.setText("REVIEWS");

    }

    private void getFeedbackData() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PatientFeedback.this);
        builder.setView(R.layout.loading_dialogue);
        final AlertDialog dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        try{
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<List<ModalFeedBack>> call = service.getFeedback(drId);

            call.enqueue(new Callback<List<ModalFeedBack>>() {
                @Override
                public void onResponse(Call<List<ModalFeedBack>> call, Response<List<ModalFeedBack>> response) {
                    List<ModalFeedBack> feedBacklist = response.body();

                    if(feedBacklist.size() == 0){

                        dialog.dismiss();
                        inactiveImage.setVisibility(View.VISIBLE);
                    }else {
                        dialog.dismiss();
                        recyclerViewPatient.setHasFixedSize(true);
                        recyclerViewPatient.setLayoutManager(new LinearLayoutManager(PatientFeedback.this));
                        CustomFeedBackAdapter adapter = new CustomFeedBackAdapter(PatientFeedback.this,feedBacklist);
                        recyclerViewPatient.setAdapter(adapter);
                    }

                }

                @Override
                public void onFailure(Call<List<ModalFeedBack>> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void initListner() {

        backPatientFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getBundle() {

        Bundle b = getIntent().getExtras();
        drId = b.getInt("dr_id");
        drNameText = b.getString("dr_name");
        drExperienceText = b.getString("dr_exp");
        drEducationText = b.getString("dr_edu");
        drName.setText(drNameText);
        drExperience.setText(drExperienceText);
        drEdu.setText(drEducationText);
    }
}

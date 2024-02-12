package com.example.aamir.doctor.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aamir.doctor.Adapter.BlogAdapter;
import com.example.aamir.doctor.Classes.Utility;
import com.example.aamir.doctor.DialogueFragent.CustomDialogue;
import com.example.aamir.doctor.Modal.BlogModal;
import com.example.aamir.doctor.R;
import com.example.aamir.doctor.Retrofit.APIService;
import com.example.aamir.doctor.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aamir on 8/21/2017.
 */

public class AllBlog extends Fragment {
    @BindView(R.id.recyclerView_latest_blog)
    RecyclerView recyclerView;
    @BindView(R.id.button_retry_blog)
    Button retry;
    @BindView(R.id.no_record)
    ImageView Record;
    BlogAdapter adapter;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.latest_blog , container ,false);

        ButterKnife.bind(this,view);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        if(Utility.isOnline(getActivity())){
            loadData();
        }else{

            final android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
            final CustomDialogue customDialogue = new CustomDialogue();
            customDialogue.show(fragmentManager, "my Fragment");
            retry.setVisibility(View.VISIBLE);
        }

        initlistner();

        return view;

    }

    private void initlistner() {

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.isOnline(getActivity())){
                    loadData();
                    retry.setVisibility(View.GONE);
                }else{

                    final android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                    final CustomDialogue customDialogue = new CustomDialogue();
                    customDialogue.show(fragmentManager, "my Fragment");
                    retry.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    public List<BlogModal> loadData(){

       progressBar.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);


        try{


            APIService service = ApiClient.getClient().create(APIService.class);
            Call<List<BlogModal>> call = service.getBlog(0);
            call.enqueue(new Callback<List<BlogModal>>() {
                @Override
                public void onResponse(Call<List<BlogModal>> call, Response<List<BlogModal>> response) {

                    List<BlogModal> blogModalList = response.body();


                    progressBar.setVisibility(View.GONE);

                    if(blogModalList.size() == 0){
                        Record.setVisibility(View.VISIBLE);
                    }else {
                        adapter = new BlogAdapter(getActivity(),blogModalList);
                        recyclerView.setAdapter(adapter);
                    }






                }

                @Override
                public void onFailure(Call<List<BlogModal>> call, Throwable t) {

                   progressBar.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }



        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}

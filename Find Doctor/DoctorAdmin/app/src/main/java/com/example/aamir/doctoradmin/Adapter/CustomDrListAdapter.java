package com.example.aamir.doctoradmin.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aamir.doctoradmin.Modals.ModalDrList;
import com.example.aamir.doctoradmin.R;
import com.example.aamir.doctoradmin.Retrofit.APIService;
import com.example.aamir.doctoradmin.Retrofit.ApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aamir on 6/18/2017.
 */

public class CustomDrListAdapter extends RecyclerView.Adapter<CustomDrListAdapter.ViewHolder> {

    Context context;
    List<ModalDrList> list;

    public CustomDrListAdapter(Context context, List<ModalDrList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dr_list_item_row,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ModalDrList modalDrList = list.get(position);
        holder.drName.setText(modalDrList.getDoc_name());
        holder.drAddress.setText(modalDrList.getDoc_address());
        holder.drHospital.setText(modalDrList.getHospital());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                delDoctor(modalDrList.getDoc_id());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textView_delete_dr_name)
        TextView drName;
        @BindView(R.id.textView_delete_dr_hospital)
        TextView drHospital;
        @BindView(R.id.textView_delete_dr_address)
        TextView drAddress;
        @BindView(R.id.button_delete_dr)
        Button delete;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public String delDoctor(int drId){

        final ProgressDialog dialog = ProgressDialog.show(context,"","Deleting...Please wait!",true);

        final String[] respons = new String[1];
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<String> call = service.deleteDoctor(drId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                respons[0] = response.body();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
               respons[0] = "Weak internet connection!";
            }
        });
        return respons[0];
    }
}

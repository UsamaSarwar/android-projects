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

import com.example.aamir.doctoradmin.Modals.ModalLabList;
import com.example.aamir.doctoradmin.R;
import com.example.aamir.doctoradmin.Retrofit.APIService;
import com.example.aamir.doctoradmin.Retrofit.ApiClient;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Aamir on 6/25/2017.
 */

public class CustomLabListAdapter extends RecyclerView.Adapter<CustomLabListAdapter.ViewHoldr> {

    Context context;
    List<ModalLabList> modalLabList;

    public CustomLabListAdapter(Context context, List<ModalLabList> modalLabList) {
        this.context = context;
        this.modalLabList = modalLabList;
    }

    @Override
    public ViewHoldr onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lab_item_row,parent,false);

        return new ViewHoldr(v);
    }

    @Override
    public void onBindViewHolder(ViewHoldr holder, int position) {
        final ModalLabList modLis = modalLabList.get(position);
        holder.labName.setText(modLis.getLab_name());
        holder.labAddress.setText(modLis.getLab_address());
        holder.delLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delLab(modLis.getLab_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modalLabList.size();
    }

    public class ViewHoldr extends RecyclerView.ViewHolder{
        @BindView(R.id.textView_delete_lab_name)
        TextView labName;
        @BindView(R.id.textView_delete_lab_address)
        TextView labAddress;
        @BindView(R.id.button_delete_lab)
        Button delLab;
        public ViewHoldr(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void delLab(int ladId){

        final ProgressDialog dialog = ProgressDialog.show(context,"","Deleting...Please wait!",true);

        final String[] respons = new String[1];
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<String> call = service.deleteLab(ladId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                respons[0] = response.body();
                Toast.makeText(context, respons[0]+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "Weak internet connection!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

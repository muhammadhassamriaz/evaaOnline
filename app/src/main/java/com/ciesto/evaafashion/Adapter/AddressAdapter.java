package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Activity.DisplayAddressActivity;
import com.ciesto.evaafashion.Model.AddressModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Myvieholder> {
    Context context;
    ArrayList<AddressModel.Address> addresslist;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    String Action;

    public AddressAdapter(Activity activity, ArrayList<AddressModel.Address> addresslist) {
        this.context = activity;
        this.addresslist = addresslist;
        progressDialog = new ProgressDialog(context);
        loginPreferences = new LoginPreferences(context);

    }

    @NonNull
    @Override
    public Myvieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myvieholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myvieholder holder, int position) {

        final AddressModel.Address bean = addresslist.get(position);
        holder.tv_address.setText( bean.getBuildingName()+","+bean.getShippingAddress());

        if (bean.getIsPrimary().equalsIgnoreCase("1")) {
            holder.switch_primary.setChecked(true);
            holder.switch_primary.setClickable(false);
        } else {
            holder.switch_primary.setChecked(false);
        }

        holder.switch_primary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (Constant.isNetworkAvailable(context)) {
                    SetPrimaryaddress(bean.getAddressId());
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                }

            }
        });

        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DisplayAddressActivity) context).showAddressDialog(bean.getAddressId(), bean);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addresslist.size();
    }

    public class Myvieholder extends RecyclerView.ViewHolder {
        ImageView imgedit;
        TextView tv_address;
        SwitchCompat switch_primary;

        public Myvieholder(@NonNull View itemView) {
            super(itemView);
            imgedit = itemView.findViewById(R.id.img_edit);
            tv_address = itemView.findViewById(R.id.tv_address);
            switch_primary = itemView.findViewById(R.id.switch_primary);
        }
    }

    private void SetPrimaryaddress(String addressId) {
        progressDialog.setTitle(context.getResources().getString(R.string.please_wait));
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
        Call<JsonObject> call = apiService.SetPrimaryAddress(Constant.ACCESSKEY, "6", loginPreferences.getUserID(), addressId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    // Log.e("Address Response==",response.body().toString());
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {

                        ((DisplayAddressActivity) context).GetAddress();

                    } else {
                        Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }
}

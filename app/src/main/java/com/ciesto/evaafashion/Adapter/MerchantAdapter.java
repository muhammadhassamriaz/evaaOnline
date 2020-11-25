package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Activity.MerchantActivity;
import com.ciesto.evaafashion.Model.MerchantModel;
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

public class MerchantAdapter extends RecyclerView.Adapter<MerchantAdapter.Myviewholder> {
    Context context;
    ArrayList<MerchantModel.Merchant> merchantlist;
    String Qty;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    int flag=0;


    public MerchantAdapter(Activity activity, ArrayList<MerchantModel.Merchant> merchantlist, String Qty) {
        this.context = activity;
        this.merchantlist = merchantlist;
        this.Qty = Qty;
        progressDialog = new ProgressDialog(context);
        loginPreferences = new LoginPreferences(context);
    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, int position) {
        final MerchantModel.Merchant bean = merchantlist.get(position);
        holder.tv_price.setText(context.getResources().getString(R.string.rupee)+ " " + bean.getPrice());
        holder.tv_storename.setText(bean.getStoreName());
        holder.tv_merchantnm.setText(bean.getMerchantName());
        holder.tv_location.setText(bean.getAddress());

        holder.tv_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag==0) {
                    if (holder.tv_addtocart.getText().toString().equals(context.getResources().getString(R.string.add_to_cart))) {
                        if (Constant.isNetworkAvailable(context))
                        {
                            AddToCart(bean.getMerchantId(), bean.getProductId(), bean.getStockId(), bean.getPriceId(), bean.getMPriceId(), holder.tv_addtocart,holder.iv_check,bean);
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.cart_validation), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return merchantlist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView tv_price, tv_storename, tv_addtocart, tv_merchantnm, tv_location;
        ImageView iv_check;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_storename = itemView.findViewById(R.id.tv_storename);
            tv_addtocart = itemView.findViewById(R.id.tv_addtocart);
            tv_merchantnm = itemView.findViewById(R.id.tv_merchantnm);
            tv_location = itemView.findViewById(R.id.tv_location);
            iv_check = itemView.findViewById(R.id.iv_check);
        }
    }

    private void AddToCart(String merchantid, String productid, String stockid, String priceid, String mpriceid, final TextView tvcart, final ImageView iv_check, final MerchantModel.Merchant bean) {
        progressDialog.setTitle(context.getResources().getString(R.string.please_wait));
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.show();

        Log.e("Merchan ID==",merchantid);
        Log.e("product ID==",productid);
        Log.e("stock ID==",stockid);
        Log.e("price ID==",priceid);
        Log.e("Mprice ID==",mpriceid);

        ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
        Call<JsonObject> call = apiService.AddToCart(Constant.ACCESSKEY, loginPreferences.getUserID(), merchantid, productid, stockid, Qty, priceid, mpriceid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        tvcart.setText(context.getResources().getString(R.string.added));
                        bean.setSelected(true);
                        iv_check.setVisibility(View.VISIBLE);
                        flag=1;
                        ((MerchantActivity)context).GetCartlist();
                    } else {
                        Toast.makeText(context, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
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

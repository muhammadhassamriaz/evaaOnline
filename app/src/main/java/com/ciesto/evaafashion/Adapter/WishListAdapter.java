package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Activity.ProductDetailsActivity;
import com.ciesto.evaafashion.Activity.WishListActivity;
import com.ciesto.evaafashion.Model.WishlistModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
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

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.Myviewholder>
{
    Context context;
    ArrayList<WishlistModel.Product> wishlist;
    ProgressDialog progressDialog;

    public WishListAdapter(Activity activity, ArrayList<WishlistModel.Product> wishlist)
    {
        this.context=activity;
        this.wishlist=wishlist;
        progressDialog=new ProgressDialog(activity);
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, final int position) {
        final WishlistModel.Product bean=wishlist.get(position);
        holder.tvCollectionName.setText(bean.getProductName());
        holder.tvPrice.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getSellPrice());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.drawable.demo)
                .error(R.drawable.demo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(Constant.Product_Image + bean.getProductImage())
                .apply(options)
                .thumbnail(Glide.with(context).load(R.drawable.loader))
                .into(holder.ivCollection);

        holder.ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getResources().getString(R.string.delete_msg))
                        .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (Constant.isNetworkAvailable(context)) {
                                   RemoveToWishlist(bean.getProductId(),position);
                                } else {
                                    Toast.makeText(context, context.getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                                }

                            }
                        }).setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
       holder.tvaddtocart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               context.startActivity(new Intent(context, ProductDetailsActivity.class)
                       .putExtra("ProductId", bean.getProductId())
                       .putExtra("MerchantId", String.valueOf(bean.getMerchantId())));
               Functions.animNext(context);
           }
       });

    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder
    {
        ImageView ivCollection,ivclose;
        TextView tvCollectionName,tvPrice,tvaddtocart;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            ivCollection=itemView.findViewById(R.id.ivCollection);
            tvCollectionName=itemView.findViewById(R.id.tvCollectionName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvaddtocart=itemView.findViewById(R.id.tvaddtocart);
            ivclose=itemView.findViewById(R.id.ivclose);
        }
    }

    private void RemoveToWishlist(String Productid, final int pos) {
        progressDialog.setTitle(context.getResources().getString(R.string.please_wait));
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
        Call<JsonObject> call = apiService.AddToWishlist(Constant.ACCESSKEY, new LoginPreferences(context).getUserID(), Productid, "0", "0");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        wishlist.remove(pos);
                        notifyDataSetChanged();
                        if(!new ExtraPreferences(context).getWhishlistcount().equals("")) {
                            int count = Integer.parseInt(new ExtraPreferences(context).getWhishlistcount()) - 1;
                            new ExtraPreferences(context).setWhishlistcount(String.valueOf(count));
                            ((WishListActivity)context).setBottom();
                        }


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

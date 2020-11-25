package com.ciesto.evaafashion.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Activity.CartActivity;
import com.ciesto.evaafashion.Model.CartModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Myviewholder> {
    Context context;
    List<CartModel.CartDatum> cartlist;
    ProgressDialog progressDialog;
    int count=1;
    ArrayList<String> qtylist;
    MerchantCartAdapter merchantCartAdapter;

    public CartAdapter(Context context, List<CartModel.CartDatum> cartlist,MerchantCartAdapter merchantCartAdapter)
    {

        this.context = context;
        this.cartlist = cartlist;
        this.merchantCartAdapter=merchantCartAdapter;
        progressDialog = new ProgressDialog(context);
        qtylist=new ArrayList();
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final CartModel.CartDatum bean = cartlist.get(position);

        CartModel.CartDatum bean1 = new CartModel.CartDatum();
        bean1.setQty(cartlist.get(position).getQty());
        bean1.setCartId(cartlist.get(position).getCartId());
        Constant.Cartlist.add(bean1);

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
                .into(holder.ivProductImage);

        holder.tvProductName.setText(bean.getProductName());
        holder.tvPrice.setText(context.getResources().getString(R.string.rupee)+ " " + bean.getProductPrice());
        holder.tvPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tvDiscountPrice.setText(context.getResources().getString(R.string.rupee)+ " " + bean.getDiscountedPrice());
        holder.tvDiscountPercent.setText("( " + bean.getDiscountPercent() + " % off )");
        holder.tvattribute.setText(bean.getUnits());


        if(qtylist.size()>0)
            qtylist.clear();

        for(int i=1;i<=10;i++)
        {
            qtylist.add(String.valueOf(i));
        }

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context, R.layout.item_sppiner, qtylist);
        holder.sp_qty.setAdapter(spinnerArrayAdapter);
        holder.sp_qty.setSelection(Integer.parseInt(bean.getQty())-1);

        holder.sp_qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < Constant.Cartlist.size() ; i++)
                {
                    if(Constant.Cartlist.get(i).getCartId().equals(bean.getCartId()))
                    {
                        Constant.Cartlist.get(i).setQty(qtylist.get(position));
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.ivremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getResources().getString(R.string.delete_msg))
                        .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (Constant.isNetworkAvailable(context)) {
                                    RemoveCart(bean.getCartId());
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

        if(position+1==cartlist.size())
            holder.viewline.setVisibility(View.GONE);
        else
            holder.viewline.setVisibility(View.VISIBLE);


    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView ivProductImage,ivremove;
        TextView tvProductName, tvRemoveItem, tvAddToWishlist, tvDiscountPrice, tvPrice, tvDiscountPercent,tvattribute;
        Spinner sp_qty;
        View viewline;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            ivremove = itemView.findViewById(R.id.ivremove);
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiscountPercent = itemView.findViewById(R.id.tvDiscountPercent);
            tvattribute = itemView.findViewById(R.id.tvattribute);
            sp_qty = itemView.findViewById(R.id.sp_qty);
            viewline = itemView.findViewById(R.id.viewline);

        }
    }

    private void RemoveCart(String cartid) {
        progressDialog.setTitle(context.getResources().getString(R.string.please_wait));
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
        Call<JsonObject> call = apiService.RemoveCart(Constant.ACCESSKEY, cartid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(context, object.getString("Message"), Toast.LENGTH_SHORT).show();

                        String count=new ExtraPreferences(context).getCartcount();
                        int f_count=Integer.parseInt(count)-1;
                        new ExtraPreferences(context).setCartcount(String.valueOf(f_count));
                        ((CartActivity) context).setBottom();

                        ((CartActivity) context).GetCartlist();



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

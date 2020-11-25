package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Model.CartModel;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;
import java.util.List;

public class MerchantCartAdapter extends RecyclerView.Adapter<MerchantCartAdapter.MyViewholder> {
    Context context;
    ArrayList<CartModel.Product> merchantcartlist;
    CartAdapter adapter;
    List<CartModel.CartDatum> cartlist;

    public MerchantCartAdapter(Activity activity, ArrayList<CartModel.Product> merchantcartlist) {
        this.context = activity;
        this.merchantcartlist = merchantcartlist;

    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchantcart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        CartModel.Product bean = merchantcartlist.get(position);
        holder.tvmerchantnm.setText(context.getResources().getString(R.string.merchant_name) + " " + bean.getMerchantName());
        holder.tvCartsubTotal.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getSubTotal());
        holder.tvdeliverycharge.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getDeliveryCharge());
        holder.tvtotal.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getTotal());

        if (bean.getCartData().size() > 0 && bean.getCartData() != null) {
            cartlist = new ArrayList<>();
            cartlist = bean.getCartData();

            holder.rvCartList.setLayoutManager(new LinearLayoutManager(context));
            adapter = new CartAdapter(context, cartlist,MerchantCartAdapter.this);
            holder.rvCartList.setAdapter(adapter);
        }

    }

    @Override
    public int getItemCount() {
        return merchantcartlist.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        TextView tvmerchantnm,tvCartsubTotal,tvdeliverycharge,tvtotal;
        RecyclerView rvCartList;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            tvmerchantnm = itemView.findViewById(R.id.tv_merchantnm);
            tvCartsubTotal = itemView.findViewById(R.id.tvCartsubTotal);
            tvdeliverycharge = itemView.findViewById(R.id.tvdeliverycharge);
            tvtotal = itemView.findViewById(R.id.tvtotal);
            rvCartList = itemView.findViewById(R.id.rvCartList);
        }
    }


}

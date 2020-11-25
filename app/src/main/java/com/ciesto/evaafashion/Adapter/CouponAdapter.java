package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Activity.CouponActivity;
import com.ciesto.evaafashion.Model.CouponModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.Myviewholder>
{
    Context context;
    ArrayList<CouponModel.Coupon> couponlist;

    public CouponAdapter(Activity activity, ArrayList<CouponModel.Coupon> couponlist)
    {
        this.context=activity;
        this.couponlist=couponlist;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupon,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final CouponModel.Coupon bean = couponlist.get(position);

        holder.tv_coupon.setText(bean.getCouponCode());
        holder.tv_des.setText(bean.getCouponDescription());
        holder.tv_discount.setText("Discount : " + bean.getCouponAmount());
        holder.tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Constant.Couponcode=bean.getCouponCode();
                Constant.MerchatnId=bean.getMerchantId();
                ((CouponActivity)context).onBackPressed();

            }
        });

    }

    @Override
    public int getItemCount() {
        return couponlist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder
    {
        TextView tv_apply, tv_des, tv_coupon, tv_discount;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tv_des = itemView.findViewById(R.id.tv_des);
            tv_apply = itemView.findViewById(R.id.tv_apply);
            tv_coupon = itemView.findViewById(R.id.tv_coupon);
            tv_discount = itemView.findViewById(R.id.tv_discount);
        }
    }
}

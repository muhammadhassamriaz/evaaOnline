package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Model.OrderDetails;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;
import java.util.List;

public class OrderMerchantAdapter extends RecyclerView.Adapter<OrderMerchantAdapter.Myviewholder> {

    Context context;
    List<OrderDetails.Order> orderList;

    public OrderMerchantAdapter(Activity activity, List<OrderDetails.Order> orderList) {
        this.context = activity;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordermerchant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position)
    {
        OrderDetails.Order bean=orderList.get(position);
        holder.tvMerchantname.setText(context.getResources().getString(R.string.merchant_name)+" "+bean.getMerchantName());
        holder.tvMobile.setText(context.getResources().getString(R.string.mobile_no_1)+" "+bean.getMobileNo());
        holder.tvOrderno.setText(context.getResources().getString(R.string.order)+" #"+bean.getOrderNo());
        holder.tvEmail.setText(context.getResources().getString(R.string.email_id)+" "+bean.getEmailId());
        holder.tvTotalAmount.setText(context.getResources().getString(R.string.total_amount)+" "+context.getResources().getString(R.string.rupee)+ " "+bean.getTotal());
        holder.tvTotalItem.setText(context.getResources().getString(R.string.total_item)+bean.getTotalItem());
        holder.tvSubtotal.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getSubTotal());
        holder.tvDeliverycharge.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getDeliveryCharge());
        holder.tvTotalPay.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getTotal());
        holder.tvCouponamout.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getCouponAmount());
        if(bean.getDeliveryCharge().equalsIgnoreCase("0"))
        {
            holder.lnrDiscount.setVisibility(View.GONE);
        }
        else
        {
            holder.lnrDiscount.setVisibility(View.VISIBLE);
        }
        if(bean.getCouponAmount().equalsIgnoreCase("0"))
        {
            holder.lnrCoupon.setVisibility(View.GONE);
        }
        else
        {
            holder.lnrCoupon.setVisibility(View.VISIBLE);
        }

        if(bean.getOrderData()!=null && bean.getOrderData().size()>0)
        {
            List<OrderDetails.OrderDatum> itemlist=new ArrayList<>();
            itemlist=bean.getOrderData();
            holder.rvproduct.setLayoutManager(new LinearLayoutManager(context));
            holder.rvproduct.setAdapter(new PendingProductAdapter(context,itemlist));
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder
    {

        private TextView tvMerchantname;
        private TextView tvMobile;
        private TextView tvEmail;
        private TextView tvAddress;
        private TextView tvOrderno;
        private TextView tvTotalAmount;
        private TextView tvTotalItem;
        private TextView tvSubtotal;
        private TextView tvCouponamout;
        private TextView tvDeliverycharge;
        private TextView tvTotalPay;
        RecyclerView rvproduct;
        LinearLayout lnrDiscount,lnrCoupon;

        public Myviewholder(@NonNull View rootView)
        {
            super(rootView);

            tvMerchantname = (TextView) rootView.findViewById(R.id.tv_merchantname);
            tvMobile = (TextView) rootView.findViewById(R.id.tv_mobile);
            tvEmail = (TextView) rootView.findViewById(R.id.tv_email);
            tvAddress = (TextView) rootView.findViewById(R.id.tv_address);
            tvOrderno = (TextView) rootView.findViewById(R.id.tv_orderno);
            tvTotalAmount = (TextView) rootView.findViewById(R.id.tv_Total_amount);
            tvTotalItem = (TextView) rootView.findViewById(R.id.tv_Total_Item);
            tvSubtotal = (TextView) rootView.findViewById(R.id.tv_subtotal);
            tvCouponamout = (TextView) rootView.findViewById(R.id.tv_couponamout);
            tvDeliverycharge = (TextView) rootView.findViewById(R.id.tv_deliverycharge);
            tvTotalPay = (TextView) rootView.findViewById(R.id.tv_totalPay);
            rvproduct = (RecyclerView) rootView.findViewById(R.id.rv_product);
            lnrDiscount = (LinearLayout) rootView.findViewById(R.id.lnr_discount);
            lnrCoupon = (LinearLayout) rootView.findViewById(R.id.lnr_coupon);

        }
    }
}

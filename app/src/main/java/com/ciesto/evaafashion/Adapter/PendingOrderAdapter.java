package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Activity.CompleteOrderDetailsActivity;
import com.ciesto.evaafashion.Activity.OrderDetailsActivity;
import com.ciesto.evaafashion.Model.PandingOrderModel;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.MyViewholder> {

    Context context;
    ArrayList<PandingOrderModel.Order> orderlist;


    public PendingOrderAdapter(Activity activity, ArrayList<PandingOrderModel.Order> orderlist) {
        this.context = activity;
        this.orderlist = orderlist;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_padingorder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        final PandingOrderModel.Order bean = orderlist.get(position);
        holder.txtOrderNo.setText("Order #" + bean.getOrderNo());
        holder.txtAmount.setText(context.getResources().getString(R.string.rupee)+ " " + bean.getTotalAmount());
        holder.txtStatus.setText(bean.getOrderStatusText());

        holder.txtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getOrderStatus().equals("0")) {
                    context.startActivity(new Intent(context, OrderDetailsActivity.class)
                            .putExtra("OrderId", bean.getOrderId()));
                    Functions.animNext(context);
                } else {
                    context.startActivity(new Intent(context, CompleteOrderDetailsActivity.class)
                            .putExtra("OrderStatus",bean.getOrderStatusText())
                            .putExtra("OrderId", bean.getOrderId()));
                    Functions.animNext(context);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }


    public class MyViewholder extends RecyclerView.ViewHolder {
        private TextView txtOrderNo;
        private TextView txtDetail;
        private TextView txtStatus;
        private TextView txtAmount;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            txtOrderNo = (TextView) itemView.findViewById(R.id.txt_order_no);
            txtDetail = (TextView) itemView.findViewById(R.id.txt_detail);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_status);
            txtAmount = (TextView) itemView.findViewById(R.id.txt_amount);
        }
    }
}

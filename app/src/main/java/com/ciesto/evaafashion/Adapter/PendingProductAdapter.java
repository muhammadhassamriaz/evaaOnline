package com.ciesto.evaafashion.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Model.OrderDetails;
import com.ciesto.evaafashion.R;

import java.util.List;

public class PendingProductAdapter extends RecyclerView.Adapter<PendingProductAdapter.Myviewholder> {

    Context context;
    List<OrderDetails.OrderDatum> itemlist;

    public PendingProductAdapter(Context context, List<OrderDetails.OrderDatum> itemlist) {
        this.context = context;
        this.itemlist = itemlist;

    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        OrderDetails.OrderDatum bean=itemlist.get(position);
        holder.tvPrice.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getTotalAmount());
        holder.tvQty.setText(bean.getQuantity()+" x "+bean.getProductPrice());
        holder.tvProductnm.setText(bean.getProductName());

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }



    public class Myviewholder extends RecyclerView.ViewHolder {

        private TextView tvProductnm;
        private TextView tvQty;
        private TextView tvPrice;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvProductnm = (TextView) itemView.findViewById(R.id.tv_productnm);
            tvQty = (TextView) itemView.findViewById(R.id.tv_qty);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}

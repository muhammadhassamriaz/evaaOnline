package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Activity.ProductDetailsActivity;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.List;

public class RecentlyViewAapter extends RecyclerView.Adapter<RecentlyViewAapter.Myvieholder>
{

    Context context;
    List<HomeScreenModel.RecentlyViewed> recentlyViewedList;

    public RecentlyViewAapter(Activity activity, List<HomeScreenModel.RecentlyViewed> recentlyViewedList)
    {
        this.context=activity;
        this.recentlyViewedList=recentlyViewedList;
    }

    @NonNull
    @Override
    public Myvieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myvieholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recentlyview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myvieholder holder, int position) {

        final HomeScreenModel.RecentlyViewed bean=recentlyViewedList.get(position);
        holder.tvProductName.setText(bean.getProductName());
        holder.tvPrice.setText(context.getResources().getString(R.string.rupee)+ " "+bean.getProductPrice());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.demo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(Constant.Product_Image + bean.getProductImage())
                .apply(options)
                .thumbnail(Glide.with(context).load(R.drawable.loader))
                .into(holder.ivProductImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProductDetailsActivity.class)
                        .putExtra("ProductId", bean.getProductId())
                        .putExtra("MerchantId", bean.getMerchantId()));
                Functions.animNext(context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentlyViewedList.size();
    }

    public class Myvieholder extends RecyclerView.ViewHolder
    {
        ImageView ivProductImage;
        TextView tvProductName,tvPrice;

        public Myvieholder(@NonNull View itemView) {
            super(itemView);
            ivProductImage=itemView.findViewById(R.id.ivProductImage);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
        }
    }
}

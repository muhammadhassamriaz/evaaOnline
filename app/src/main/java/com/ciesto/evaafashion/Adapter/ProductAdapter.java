package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.ciesto.evaafashion.Model.ProductModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Myviewholder> {
    Context context;
    ArrayList<ProductModel.Product> productlist;

    public ProductAdapter(Activity activity, ArrayList<ProductModel.Product> productlist) {
        this.context = activity;
        this.productlist = productlist;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final ProductModel.Product bean = productlist.get(position);
        holder.tvCollectionName.setText(bean.getProductName());


        holder.tvPrice.setText(context.getResources().getString(R.string.rupee)+ " " + bean.getPrice().get(0).getSellPrice());

        if(Integer.parseInt( bean.getPrice().get(0).getDiscountPercent())>0)
        {
            holder.tvDiscountPrice.setVisibility(View.VISIBLE);
            holder.tvDiscountPercent.setVisibility(View.VISIBLE);

            holder.tvDiscountPrice.setText(context.getResources().getString(R.string.rupee)+ " " + bean.getPrice().get(0).getDiscountedPrice());
            holder.tvDiscountPrice.setPaintFlags(holder.tvDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvDiscountPercent.setText("( " + bean.getPrice().get(0).getDiscountPercent() + " % off )");
        }
        else
        {
            holder.tvDiscountPrice.setVisibility(View.GONE);
            holder.tvDiscountPercent.setVisibility(View.GONE);
        }


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





        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        return productlist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView ivCollection;
        TextView tvCollectionName, tvProductName, tvDiscountPrice, tvDiscountPercent, tvPrice;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            ivCollection = itemView.findViewById(R.id.ivCollection);
            tvCollectionName = itemView.findViewById(R.id.tvCollectionName);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice);
            tvDiscountPercent = itemView.findViewById(R.id.tvDiscountPercent);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }

}

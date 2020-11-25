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
import com.ciesto.evaafashion.Model.MerchantDetailsModel;
import com.ciesto.evaafashion.Model.MerchantFilterModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.List;

public class AtoZProductFilterAdapter extends RecyclerView.Adapter<AtoZProductFilterAdapter.Myviewhoider>
{

    Context context;
    List<MerchantFilterModel.Product> productslist;
    String MerchantId;



    public AtoZProductFilterAdapter(Activity activity, List<MerchantFilterModel.Product> products, String merchantId)
    {
        this.context=activity;
        this.productslist=products;
        this.MerchantId=merchantId;
    }

    @NonNull
    @Override
    public Myviewhoider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewhoider(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atozproduct,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewhoider holder, int position)
    {
        final MerchantFilterModel.Product bean=productslist.get(position);
        holder.tvPrice.setText(context.getResources().getString(R.string.rupee) + " "+ bean.getPrice().get(0).getSellPrice());
        holder.tvCollectionName.setText(bean.getProductName());

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
                        .putExtra("MerchantId",MerchantId));
                Functions.animNext(context);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productslist.size();
    }

    public class Myviewhoider extends RecyclerView.ViewHolder 
    {
        ImageView ivCollection;
        TextView tvCollectionName, tvProductName, tvDiscountPrice, tvDiscountPercent, tvPrice;
        
        public Myviewhoider(@NonNull View itemView) {
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

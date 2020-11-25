package com.ciesto.evaafashion.Adapter;

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
import com.ciesto.evaafashion.Activity.ProductActivity;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.List;

public class ShoByProductSubAdapter extends RecyclerView.Adapter<ShoByProductSubAdapter.Myviewholder>
{
    Context context;
    List<HomeScreenModel.Subcategory> subcategory;

    public ShoByProductSubAdapter(Context context, List<HomeScreenModel.Subcategory> subcategory)
    {
        this.context=context;
        this.subcategory=subcategory;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopbyproductsubcategory,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position)
    {
        final HomeScreenModel.Subcategory bean=subcategory.get(position);
        holder.tvsCategoryName.setText(bean.getSubCategory());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.drawable.demo)
                .error(R.drawable.demo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(Constant.Category_Image + bean.getImage())
                .apply(options)
                .thumbnail(Glide.with(context).load(R.drawable.loader))
                .into(holder.ivShopCategory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProductActivity.class)
                        .putExtra("Sucategory_id",bean.getSubCategoryId())
                        .putExtra("Subcategorynm",bean.getSubCategory())
                        .putExtra("Action","0"));
                Functions.animNext(context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subcategory.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder
    {
        TextView tvsCategoryName;
        ImageView ivShopCategory;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvsCategoryName=itemView.findViewById(R.id.tvsCategoryName);
            ivShopCategory=itemView.findViewById(R.id.ivShopCategory);
        }
    }
}

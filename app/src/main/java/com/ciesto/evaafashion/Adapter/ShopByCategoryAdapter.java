package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Activity.SubCategoryActivity2;
import com.ciesto.evaafashion.Filter.ShopByCategoryFilter;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.List;

public class ShopByCategoryAdapter extends RecyclerView.Adapter<ShopByCategoryAdapter.Myviewholder> implements Filterable {
    Context context;
    public List<HomeScreenModel.ShopByCategory> shopbycategoryList;
    ShopByCategoryFilter filter;

    public ShopByCategoryAdapter(Activity activity, List<HomeScreenModel.ShopByCategory> shopbycategoryList) {
        this.context = activity;
        this.shopbycategoryList = shopbycategoryList;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopbycategory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final HomeScreenModel.ShopByCategory bean = shopbycategoryList.get(position);
        holder.tvCategoryName.setText(bean.getCategory());
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
                context.startActivity(new Intent(context, SubCategoryActivity2.class)
                        .putExtra("Sucategory_id", bean.getSubCategoryId())
                        .putExtra("Subcategorynm", bean.getSubCategory()));
                Functions.animNext(context);

            }
        });

    }

    @Override
    public int getItemCount() {
        if(shopbycategoryList.size()>0)
        return shopbycategoryList.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ShopByCategoryFilter(ShopByCategoryAdapter.this, shopbycategoryList);
        }
        return filter;
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView tvCategoryName, tvsCategoryName;
        ImageView ivShopCategory;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvsCategoryName = itemView.findViewById(R.id.tvsCategoryName);
            ivShopCategory = itemView.findViewById(R.id.ivShopCategory);

        }
    }
}

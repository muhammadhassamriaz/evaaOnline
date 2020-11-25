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
import com.ciesto.evaafashion.Activity.SubCategoryActivity2;
import com.ciesto.evaafashion.Model.CategoryModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.Myviewholder>
{
    Context context;
    List<CategoryModel.SubCategory> subCategoryList;


    public SubCategoryAdapter(Activity activity, List<CategoryModel.SubCategory> subCategoryList)
    {
        this.context=activity;
        this.subCategoryList=subCategoryList;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position)
    {
        final CategoryModel.SubCategory bean=subCategoryList.get(position);
        holder.tvName.setText(bean.getSubCategory());

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
                .into(holder.ivCollection);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SubCategoryActivity2.class)
                        .putExtra("Sucategory_id",bean.getSubCategoryId())
                .putExtra("Subcategorynm",bean.getSubCategory()));
                Functions.animNext(context);

            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder
    {
        TextView tvName;
        ImageView ivCollection;

        public Myviewholder(@NonNull View itemView)
        {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            ivCollection=itemView.findViewById(R.id.ivCollection);
        }
    }
}

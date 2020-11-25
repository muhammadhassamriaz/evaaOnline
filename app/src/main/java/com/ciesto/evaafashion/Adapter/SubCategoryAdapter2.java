package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Activity.ProductActivity;
import com.ciesto.evaafashion.Model.SubCategoryModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class SubCategoryAdapter2 extends RecyclerView.Adapter<SubCategoryAdapter2.Myviewholder> {
    Context context;
    List<SubCategoryModel.Category> subCategoryList;
    String action;


    public SubCategoryAdapter2(Activity activity, List<SubCategoryModel.Category> subCategoryList, String action) {
        this.context = activity;
        this.subCategoryList = subCategoryList;
        this.action=action;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final SubCategoryModel.Category bean = subCategoryList.get(position);
        holder.tvName.setText(bean.getCategory());
        holder.tvName1.setText(bean.getCategory());

        if(action.equalsIgnoreCase("home"))
        {
            holder.lnrmain1.setVisibility(View.VISIBLE);
            holder.lnr_main.setVisibility(View.GONE);
            holder.view_line.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.lnrmain1.setVisibility(View.GONE);
            holder.view_line.setVisibility(View.GONE);
            holder.lnr_main.setVisibility(View.VISIBLE);
        }

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

        Glide.with(context)
                .load(Constant.Category_Image + bean.getImage())
                .apply(options)
                .thumbnail(Glide.with(context).load(R.drawable.loader))
                .into(holder.img_category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProductActivity.class)
                        .putExtra("Sucategory_id", bean.getCategoryId())
                        .putExtra("Subcategorynm", bean.getCategory())
                        .putExtra("Action", "0"));
                Functions.animNext(context);

            }
        });



    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView tvName,tvName1;
        ImageView ivCollection;
        LinearLayout lnrmain1,lnr_main;
        CircularImageView img_category;
        View view_line;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvName1 = itemView.findViewById(R.id.tvName1);
            ivCollection = itemView.findViewById(R.id.ivCollection);
            lnrmain1 = itemView.findViewById(R.id.lnrmain1);
            lnr_main = itemView.findViewById(R.id.lnr_main);
            img_category = itemView.findViewById(R.id.img_category);
            view_line = itemView.findViewById(R.id.view_line);
        }
    }
}

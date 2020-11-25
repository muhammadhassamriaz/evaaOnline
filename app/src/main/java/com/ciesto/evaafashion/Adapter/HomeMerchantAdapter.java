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
import com.ciesto.evaafashion.Activity.AtoZDetailsActivity;
import com.ciesto.evaafashion.Activity.LoginActivity;
import com.ciesto.evaafashion.Activity.SubCategoryActivity1;
import com.ciesto.evaafashion.Model.CategoryModel;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;

import java.util.List;

public class HomeMerchantAdapter extends RecyclerView.Adapter<HomeMerchantAdapter.Myviewholder> {
    Context context;
    List<HomeScreenModel.MerchantList> merchantList;

    public HomeMerchantAdapter(Activity activity, List<HomeScreenModel.MerchantList> merchantList)
    {
        this.context=activity;
        this.merchantList=merchantList;
    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homemerchant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, final int position) {

        final HomeScreenModel.MerchantList bean = merchantList.get(position);

            holder.tvName.setText(bean.getMerchantName().toUpperCase());

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    //.placeholder(R.drawable.demo)
                    .error(R.drawable.demo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(context)
                    .load(Constant.Profile_Images + bean.getProfileImage())
                    .apply(options)
                    .thumbnail(Glide.with(context).load(R.drawable.loader))
                    .into(holder.ivSubCategory);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new LoginPreferences(context).isLoggIn()) {
                    context.startActivity(new Intent(context, AtoZDetailsActivity.class)
                            .putExtra("Merchantid",bean.getMerchantId()));
                    Functions.animNext(context);
                }
                else
                {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return merchantList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView  tvName;
        ImageView ivSubCategory;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            ivSubCategory = itemView.findViewById(R.id.ivSubCategory);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}

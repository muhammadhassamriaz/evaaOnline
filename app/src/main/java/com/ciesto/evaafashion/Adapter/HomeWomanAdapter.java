package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Activity.ProductActivity;
import com.ciesto.evaafashion.Activity.SubCategoryActivity1;
import com.ciesto.evaafashion.Activity.SubCategoryActivity2;
import com.ciesto.evaafashion.Filter.HomeFilter;
import com.ciesto.evaafashion.Filter.HomeWomanFilter;
import com.ciesto.evaafashion.Filter.ShopByCategoryFilter;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;
import java.util.List;

public class HomeWomanAdapter extends RecyclerView.Adapter<HomeWomanAdapter.Myviewholder>  implements Filterable
{
    Context context;
    public List<HomeScreenModel.SubCategory1> WomanSubcategoryList;
    String image,finalimages;
    HomeWomanFilter filter;

    public HomeWomanAdapter(Activity activity, List<HomeScreenModel.SubCategory1> WomanSubcategoryList, String image)
    {
        this.context=activity;
        this.WomanSubcategoryList=WomanSubcategoryList;
        this.image=image;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homewoman,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, final int position)
    {
        final HomeScreenModel.SubCategory1 bean=WomanSubcategoryList.get(position);
       holder.tvName.setText(bean.getSubCategory().toUpperCase());

       /*if(image.equalsIgnoreCase(""))
           finalimages=bean.getImage();*/

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
                .into(holder.img_main);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SubCategoryActivity2.class)
                        .putExtra("Sucategory_id", bean.getSubCategoryId())
                        .putExtra("SubcategoryImages",bean.getImage())
                        .putExtra("Subcategorynm", bean.getSubCategory()));
                Functions.animNext(context);

            }
        });

    }

    @Override
    public int getItemCount() {
        if(WomanSubcategoryList.size()>0)
        return WomanSubcategoryList.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new HomeWomanFilter(HomeWomanAdapter.this, WomanSubcategoryList);
        }
        return filter;
    }


    public class Myviewholder extends RecyclerView.ViewHolder
    {
        TextView tvName;

        ImageView img_main;
        RecyclerView rvSucategory;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            img_main=itemView.findViewById(R.id.img_main);

        }
    }
}

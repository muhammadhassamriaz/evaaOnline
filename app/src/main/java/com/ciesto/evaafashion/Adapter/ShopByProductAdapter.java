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

import com.ciesto.evaafashion.Activity.SubCategoryActivity1;
import com.ciesto.evaafashion.Filter.HomeFilter;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;
import java.util.List;

public class ShopByProductAdapter extends RecyclerView.Adapter<ShopByProductAdapter.Myviewholder> implements Filterable
{
    Context context;
    public  List<HomeScreenModel.ShopByProduct> shopByProductsList;
    HomeFilter filter;

    public ShopByProductAdapter(Activity activity, List<HomeScreenModel.ShopByProduct> shopByProductsList)
    {
        this.context=activity;
        this.shopByProductsList=shopByProductsList;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopbyproduct,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, final int position)
    {
        final HomeScreenModel.ShopByProduct bean=shopByProductsList.get(position);
        holder.tvName.setText(bean.getCategory());

        holder.tvSeeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SubCategoryActivity1.class);
                i.putExtra("selected",position);
                i.putParcelableArrayListExtra("CategoryList", (ArrayList<? extends Parcelable>)  Constant.categorylist );
                context.startActivity(i);
                Functions.animNext(context);

            }
        });

        if(new ExtraPreferences(context).getLanguage().equalsIgnoreCase("English"))
        {
            holder.imgArrow.setRotation(0);
        }
        else
        {
            holder.imgArrow.setRotation(180);
        }

        if(shopByProductsList.size()==position+1)
        {
            holder.viewline.setVisibility(View.GONE);
        }
        else {
            holder.viewline.setVisibility(View.VISIBLE);
        }

        if(bean.getSubcategory().size()>0 && bean.getSubcategory()!=null)
        {
            holder.rvSucategory.setLayoutManager(new GridLayoutManager(context,3));
            holder.rvSucategory.setAdapter(new ShoByProductSubAdapter(context,bean.getSubcategory()));
        }

    }

    @Override
    public int getItemCount() {
        if(shopByProductsList.size()>0)
        return shopByProductsList.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new HomeFilter(ShopByProductAdapter.this, shopByProductsList);
        }
        return filter;
    }

    public class Myviewholder extends RecyclerView.ViewHolder
    {
        TextView tvName,tvSeeall;
        View viewline;
        ImageView imgArrow;
        RecyclerView rvSucategory;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            viewline=itemView.findViewById(R.id.view_line);
            imgArrow=itemView.findViewById(R.id.img_arrow);
            tvSeeall=itemView.findViewById(R.id.tvseeall);
            rvSucategory=itemView.findViewById(R.id.rv_sucategory);
        }
    }
}

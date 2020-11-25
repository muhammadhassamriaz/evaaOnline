package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Activity.AtoZDetailsActivity;
import com.ciesto.evaafashion.Activity.LoginActivity;
import com.ciesto.evaafashion.Activity.WishListActivity;
import com.ciesto.evaafashion.Filter.MerchantFilter;
import com.ciesto.evaafashion.Filter.SubMerchantFilter;
import com.ciesto.evaafashion.Model.AtoZModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;

import java.util.List;

import kotlin.Function;

public class AtoZSubCategoryAdapter extends RecyclerView.Adapter<AtoZSubCategoryAdapter.Myviewhoider> implements Filterable
{

    Context context;
    public List<AtoZModel.Value> valuelist;
    SubMerchantFilter filter;

    public AtoZSubCategoryAdapter(Context activity, List<AtoZModel.Value> value) {
        this.context=activity;
        this.valuelist=value;
    }

    @NonNull
    @Override
    public Myviewhoider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewhoider(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atozsubcategory,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewhoider holder, int position) {
        final AtoZModel.Value bean=valuelist.get(position);
        holder.tv_name.setText(bean.getMerchantName());

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
        return valuelist.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SubMerchantFilter(AtoZSubCategoryAdapter.this, Constant.MerchantFilter);
        }
        return filter;
    }

    public class Myviewhoider extends RecyclerView.ViewHolder 
    {
        TextView tv_name;
        
        public Myviewhoider(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}

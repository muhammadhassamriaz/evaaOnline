package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Filter.HomeFilter;
import com.ciesto.evaafashion.Filter.MerchantFilter;
import com.ciesto.evaafashion.Model.AtoZModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.R;

import java.util.List;

public class AtoZCategoryAdapter extends RecyclerView.Adapter<AtoZCategoryAdapter.Myviewhoider> implements Filterable
{
    Context context;
   public List<AtoZModel.Merchant> merchantlist;
    MerchantFilter merchantFilter;
    AtoZSubCategoryAdapter adapter;

    public AtoZCategoryAdapter(Activity activity, List<AtoZModel.Merchant> merchant) {
        this.context=activity;
        this.merchantlist=merchant;
    }

    @NonNull
    @Override
    public Myviewhoider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewhoider(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_atozcategory,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewhoider holder, int position) {
        AtoZModel.Merchant bean=merchantlist.get(position);

        holder.tv_name.setText(bean.getName());

        if(bean.getValue().size()>0) {
            holder.rv_subcategory.setLayoutManager(new LinearLayoutManager(context));
            adapter=new AtoZSubCategoryAdapter(context, bean.getValue());
            holder.rv_subcategory.setAdapter(adapter);
            Constant.MerchantFilter=bean.getValue();
        }

    }

    @Override
    public int getItemCount() {
        return merchantlist.size();
    }

    @Override
    public Filter getFilter() {
        if (merchantFilter == null) {
            merchantFilter = new MerchantFilter(AtoZCategoryAdapter.this, merchantlist);
        }
        return merchantFilter;
    }

    public void GetFilter(CharSequence c)
    {
        adapter.getFilter().filter(c);
    }

    public class Myviewhoider extends RecyclerView.ViewHolder 
    {
        RecyclerView rv_subcategory;
        TextView tv_name;
        
        public Myviewhoider(@NonNull View itemView) {
            super(itemView);
            rv_subcategory=itemView.findViewById(R.id.rv_subcategory);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}

package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Model.ProductDetailsModel;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;
import java.util.List;

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.MyviewHolder> {
    Context context;
    List<ProductDetailsModel.AttributeDatum> attributeDatumList;


    public AttributeAdapter(Activity activity, List<ProductDetailsModel.AttributeDatum> attributeDatumList) {
        this.context = activity;
        this.attributeDatumList = attributeDatumList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attribute, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.tvattributenm.setText("Select " + attributeDatumList.get(position).getAttributeName());

        List<ProductDetailsModel.Unit> unitList = new ArrayList<>();

        if (attributeDatumList.get(position).getUnits() != null && attributeDatumList.get(position).getUnits().size() > 0) {
            holder.rvitem.setVisibility(View.VISIBLE);
            holder.tvnodata.setVisibility(View.GONE);
            holder.tvattributenm.setVisibility(View.VISIBLE);

            unitList = attributeDatumList.get(position).getUnits();
            holder.rvitem.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            holder.rvitem.setAdapter(new UnitAdapter(context, unitList, attributeDatumList.get(position).getAttributeName()));
        } else {
            holder.rvitem.setVisibility(View.GONE);
            holder.tvnodata.setVisibility(View.GONE);
            holder.tvattributenm.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return attributeDatumList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvattributenm, tvnodata;
        RecyclerView rvitem;
        LinearLayout liner_Container;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            tvattributenm = itemView.findViewById(R.id.tvattributenm);
            rvitem = itemView.findViewById(R.id.rvitem);
            tvnodata = itemView.findViewById(R.id.tv_nodata);
        }
    }

    private void SetAttributeData(List<ProductDetailsModel.Unit> unitList) {


    }
}

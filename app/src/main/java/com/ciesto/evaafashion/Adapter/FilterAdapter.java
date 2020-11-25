package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Filter.CustomFilter1;
import com.ciesto.evaafashion.Model.FilterModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.R;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.Myviewholder> implements Filterable {
    Context context;
    int Row_index = -1;
    public List<FilterModel.Datum> datalist;
    CustomFilter1 filter1;

    public FilterAdapter(Activity activity, List<FilterModel.Datum> datalist) {
        this.context = activity;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final FilterModel.Datum bean = datalist.get(position);
        holder.tvname.setText(bean.getName());

        if (bean.getType().equalsIgnoreCase("Unit")) {
            if (Constant.UnitIdList.size() > 0 && Constant.UnitIdList != null) {
                for (int i = 0; i < Constant.UnitIdList.size(); i++) {
                    if (Constant.UnitIdList.get(i).equalsIgnoreCase(bean.getId())) {
                        bean.setSelected(true);
                        holder.imgcheck.setImageResource(R.drawable.ic_check_fill);
                    }
                }
            }
        } else {
            if (Constant.PriceIdList.size() > 0 && Constant.PriceIdList != null) {
                for (int i = 0; i < Constant.PriceIdList.size(); i++) {
                    if (Constant.PriceIdList.get(i).equalsIgnoreCase(bean.getId())) {
                        bean.setSelected(true);
                        holder.imgcheck.setImageResource(R.drawable.ic_check_fill);
                    }
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isSelected() == true) {
                    bean.setSelected(false);
                    holder.imgcheck.setImageResource(R.drawable.ic_checkbox_empty);
                    SetSelectedId(false, bean.getId(), bean.getType());
                } else {
                    bean.setSelected(true);
                    holder.imgcheck.setImageResource(R.drawable.ic_check_fill);
                    SetSelectedId(true, bean.getId(), bean.getType());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if(datalist.size()>0 && datalist!=null)
        return datalist.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        if (filter1 == null) {
            filter1 = new CustomFilter1(FilterAdapter.this, datalist);
        }
        return filter1;
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView imgcheck;
        TextView tvname;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            imgcheck = itemView.findViewById(R.id.img_check);
            tvname = itemView.findViewById(R.id.tv_name);
        }
    }

    public void SetSelectedId(boolean isselected, String id, String type) {
        if (type.equalsIgnoreCase("Unit")) {

            if (isselected == true) {
                Constant.UnitIdList.add(id);
            } else {
                for (int i = 0; i < Constant.UnitIdList.size(); i++) {
                    if (id.equalsIgnoreCase(Constant.UnitIdList.get(i))) {
                        Constant.UnitIdList.remove(i);
                    }
                }
            }
        } else {
            if (isselected == true) {
                Constant.PriceIdList.add(id);
            } else {
                for (int i = 0; i < Constant.PriceIdList.size(); i++) {
                    if (id.equalsIgnoreCase(Constant.PriceIdList.get(i))) {
                        Constant.PriceIdList.remove(i);
                    }
                }
            }
        }
    }
}

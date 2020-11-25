package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Activity.FilterActivity;
import com.ciesto.evaafashion.Model.FilterModel;
import com.ciesto.evaafashion.R;

import java.util.List;

public class FilterCategoryAdapter extends RecyclerView.Adapter<FilterCategoryAdapter.MyViewholder>
{
    Context context;
    int row_index=0;
     List<FilterModel.Filter> filtercategorylist;


    public FilterCategoryAdapter(Activity activity, List<FilterModel.Filter> filtercategorylist)
    {
        this.context=activity;
        this.filtercategorylist=filtercategorylist;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filtercategoty,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, final int position)
    {
        final FilterModel.Filter bean=filtercategorylist.get(position);
        holder.tvname.setText(bean.getName());;

        if(row_index==position)
            holder.tvname.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        else
            holder.tvname.setBackgroundColor(context.getResources().getColor(R.color.White));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                notifyDataSetChanged();
                ((FilterActivity)context).SetData(position,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filtercategorylist.size();
    }



    public class MyViewholder extends RecyclerView.ViewHolder
    {
        TextView tvname;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            tvname=itemView.findViewById(R.id.tv_name);
        }
    }
}

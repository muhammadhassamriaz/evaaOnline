package com.ciesto.evaafashion.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Model.ProductDetailsModel;
import com.ciesto.evaafashion.R;

import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.Myviewholder> {
    Context context;
    List<ProductDetailsModel.Unit> unitList;
    String Attributenm;
    int Indexposition = 0;

    public UnitAdapter(Context context, List<ProductDetailsModel.Unit> unitList, String Attributenm) {
        this.context = context;
        this.unitList = unitList;
        this.Attributenm = Attributenm;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attributeitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, final int position)
    {
        ProductDetailsModel.Unit bean=unitList.get(position);

        if (Indexposition == position)
        {

            if (bean.getColorCode().startsWith("#"))//color
            {
                bean.setSelected(true);
                holder.lnrcolor.setVisibility(View.VISIBLE);
                holder.tvsize.setVisibility(View.GONE);
                //Change Strock color
                GradientDrawable myGrad = (GradientDrawable)holder.tvcolor.getBackground();
                myGrad.setStroke(convertDpToPx(2), Color.BLACK);

            } else {
                bean.setSelected(true);
                holder.lnrcolor.setVisibility(View.GONE);
                holder.tvsize.setVisibility(View.VISIBLE);
                //Change Solid color
                Drawable background = holder.tvsize.getBackground();
                ((GradientDrawable) background).setColor(context.getResources().getColor(R.color.colorBlack));
                holder.tvsize.setTextColor(context.getResources().getColor(android.R.color.white));
            }
        }
        else {

            if (bean.getColorCode().startsWith("#"))//color
            {
                bean.setSelected(false);
                holder.lnrcolor.setVisibility(View.VISIBLE);
                holder.tvsize.setVisibility(View.GONE);
                //Change Strock color
                GradientDrawable myGrad = (GradientDrawable)holder.tvcolor.getBackground();
                myGrad.setStroke(convertDpToPx(2), context.getResources().getColor(R.color.colorGrey));

            } else {
                bean.setSelected(false);
                holder.lnrcolor.setVisibility(View.GONE);
                holder.tvsize.setVisibility(View.VISIBLE);
                //Change Solid color
                Drawable background = holder.tvsize.getBackground();
                ((GradientDrawable) background).setColor(context.getResources().getColor(R.color.attribute));
                holder.tvsize.setTextColor(context.getResources().getColor(R.color.colordark));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Indexposition = position;
                notifyDataSetChanged();
            }
        });


        if (bean.getColorCode().startsWith("#")) {

            holder.lnrcolor.setVisibility(View.VISIBLE);
            holder.tvsize.setVisibility(View.GONE);

            Drawable background = holder.tvcolor.getBackground();
            ((GradientDrawable) background).setColor(Color.parseColor(bean.getColorCode()));
        } else {
            holder.tvsize.setVisibility(View.VISIBLE);
            holder.tvsize.setBackground(context.getResources().getDrawable(R.drawable.simple_round));
            holder.lnrcolor.setVisibility(View.GONE);
            holder.tvsize.setText(bean.getUnitName());
        }
    }
    private int convertDpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView tvsize, tvcolor;
        LinearLayout lnrcolor;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvsize = itemView.findViewById(R.id.tvsize);
            tvcolor = itemView.findViewById(R.id.tvcolor);
            lnrcolor = itemView.findViewById(R.id.lnr_color);
        }
    }
}

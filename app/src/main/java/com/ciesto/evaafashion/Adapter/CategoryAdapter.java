package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.ciesto.evaafashion.Activity.SubCategoryActivity1;
import com.ciesto.evaafashion.Model.CategoryModel;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Myviewholder> {
    Context context;
    int row_index = 0;
    List<CategoryModel.Category> categorylist;
    int pos;


    public CategoryAdapter(Activity activity, List<CategoryModel.Category> categorylist ,int pos) {
        this.context = activity;
        this.categorylist = categorylist;
        this.pos=pos;
        if (context instanceof SubCategoryActivity1) {
            row_index = pos;
        }
    }




    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context instanceof SubCategoryActivity1)
            return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category1, parent, false));
        else
            return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, final int position) {

        CategoryModel.Category bean = categorylist.get(position);

        if (context instanceof SubCategoryActivity1)
        {
            holder.tv_title.setText(bean.getCategory());
            if (row_index == position)
            {
                holder.view_line.setVisibility(View.VISIBLE);
                holder.tv_title.setTextColor(context.getResources().getColor(R.color.blue));
            } else {
                holder.view_line.setVisibility(View.INVISIBLE);
                holder.tv_title.setTextColor(context.getResources().getColor(R.color.colordark));
            }

            if(categorylist.size()==position+1)
                holder.view_line1.setVisibility(View.GONE);
            else
                holder.view_line1.setVisibility(View.VISIBLE);

        }
        else
            {

          /*  holder.tvName.setText(bean.getCategory());

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
                    .into(holder.ivSubCategory);*/

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (context instanceof SubCategoryActivity1)
                {
                    row_index = position;
                    notifyDataSetChanged();
                     ((SubCategoryActivity1) context).SetProduct(position);
                } else {
                   /* Intent i = new Intent(context, SubCategoryActivity1.class);
                    i.putExtra("selected",position);
                    i.putParcelableArrayListExtra("CategoryList", (ArrayList<? extends Parcelable>) categorylist);
                    context.startActivity(i);
                    Functions.animNext(context);*/
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView tv_title, tvName;
        View view_line,view_line1;
        ImageView ivSubCategory;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_name);
            view_line = itemView.findViewById(R.id.view_line);
            view_line1 = itemView.findViewById(R.id.view_line1);
            ivSubCategory = itemView.findViewById(R.id.ivSubCategory);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}

package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.ciesto.evaafashion.Activity.ProductDetailsActivity;
import com.ciesto.evaafashion.Model.ProductDetailsModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

import java.util.List;

public class SimilrProductAdapter extends RecyclerView.Adapter<SimilrProductAdapter.Myviewholder>
{
    Context context;
    List<ProductDetailsModel.SimilarProdut> similarProdutList;

    public SimilrProductAdapter(Activity activity, List<ProductDetailsModel.SimilarProdut> similarProdutList)
    {
        this.context=activity;
        this.similarProdutList=similarProdutList;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similarproduct,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position)
    {
        final ProductDetailsModel.SimilarProdut bean=similarProdutList.get(position);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.placeholder(R.drawable.demo)
                .error(R.drawable.demo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(Constant.Product_Image + bean.getProductImage())
                .apply(options)
                .thumbnail(Glide.with(context).load(R.drawable.loader))
                .into(holder.ivCollection);

        holder.tvCollectionName.setText(bean.getProductName());
        holder.tvCollectiondes.setText(bean.getShortDescription());


        if(bean.getPriceData()!=null && bean.getPriceData().size()>0) {
            holder.tvPrice.setText(context.getResources().getString(R.string.rupee)+ " " + bean.getPriceData().get(0).getSellPrice());
        }
        else
        {
            holder.tvPrice.setText(context.getResources().getString(R.string.rupee) + " 0");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Merchant ID==",bean.getMerchantId());
                context.startActivity(new Intent(context, ProductDetailsActivity.class)
                        .putExtra("ProductId", bean.getProductId())
                        .putExtra("MerchantId", bean.getMerchantId()));
                Functions.animNext(context);

            }
        });


    }



    @Override
    public int getItemCount() {
        return similarProdutList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder
    {
        ImageView ivCollection;
        TextView tvCollectionName,tvCollectiondes,tvPrice;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            ivCollection=itemView.findViewById(R.id.ivCollection);
            tvCollectionName=itemView.findViewById(R.id.tvCollectionName);
            tvCollectiondes=itemView.findViewById(R.id.tvCollectiondes);
            tvPrice=itemView.findViewById(R.id.tvPrice);
        }
    }


  /*  public void getMinValue(List<ProductDetailsModel.PriceDatum> numbers) {
        int Price = Integer.parseInt(numbers.get(0).getSellPrice());

        String DiscountPrice = "", DiscountPer = "0";
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.size() != 1) {

                if (Integer.parseInt(numbers.get(i).getSellPrice()) < Price) {
                    Price = Integer.parseInt(numbers.get(i).getSellPrice());

                    DiscountPer = numbers.get(i).getDiscountPercent();
                    DiscountPrice = numbers.get(i).getDiscountedPrice();

                }
            } else {
                Price = Integer.parseInt(numbers.get(i).getSellPrice());
                DiscountPer = numbers.get(i).getDiscountPercent();
                DiscountPrice = numbers.get(i).getDiscountedPrice();

            }
        }

           *//*  Log.e("DiscountPrice==",DiscountPrice);
            Log.e("DiscountPer==",DiscountPer);
        *//*

        tvPrice.setText(getResources().getString(R.string.rupee) + Price);

        if (Integer.parseInt(DiscountPer) > 0) {
            tvDiscountPrice.setVisibility(View.VISIBLE);
            tvDiscountPercent.setVisibility(View.VISIBLE);

            tvDiscountPrice.setPaintFlags(tvDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvDiscountPrice.setText(getResources().getString(R.string.rupee) + DiscountPrice);
            tvDiscountPercent.setText("( " + DiscountPer + " % off )");
        } else {
            tvDiscountPrice.setVisibility(View.GONE);
            tvDiscountPercent.setVisibility(View.GONE);
        }

    }*/
}

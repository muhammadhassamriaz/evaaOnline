package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Activity.AtoZDetailsActivity;
import com.ciesto.evaafashion.Activity.LoginActivity;
import com.ciesto.evaafashion.Activity.WardrobDetailsActivity;
import com.ciesto.evaafashion.Model.WardrobModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;

import java.util.List;

public class WardrobeAdapter extends RecyclerView.Adapter<WardrobeAdapter.Myviewhoider>
{
    Context context;
    List<WardrobModel.Merchant> merchant;

    public WardrobeAdapter(Activity activity, List<WardrobModel.Merchant> merchant) {
        this.context=activity;
        this.merchant=merchant;
    }

    @NonNull
    @Override
    public Myviewhoider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewhoider(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wardrobe,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewhoider holder, int position)
    {
        final WardrobModel.Merchant bean=merchant.get(position);
        holder.tv_name.setText(bean.getMerchantName());
        holder.tv_bio.setText(Html.fromHtml(bean.getBio()));

        if(!bean.getCoverImage().equalsIgnoreCase("")) {

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    //.placeholder(R.drawable.demo)
                    .error(R.drawable.demo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(context)
                    .load(Constant.Profile_Images + bean.getCoverImage())
                    .apply(options)
                    .thumbnail(Glide.with(context).load(R.drawable.loader))
                    .into(holder.img_feature);
        }

        if(!bean.getProfileImage().equalsIgnoreCase("")) {

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    //.placeholder(R.drawable.demo)
                    .error(R.drawable.demo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(context)
                    .load(Constant.Profile_Images + bean.getProfileImage())
                    .apply(options)
                    .thumbnail(Glide.with(context).load(R.drawable.loader))
                    .into(holder.img_user);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new LoginPreferences(context).isLoggIn()) {
                    context.startActivity(new Intent(context, WardrobDetailsActivity.class)
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
        return merchant.size();
    }

    public class Myviewhoider extends RecyclerView.ViewHolder 
    {
        TextView tv_bio,tv_name;
        ImageView img_feature,img_user;
        public Myviewhoider(@NonNull View itemView) {
            super(itemView);
            tv_bio=itemView.findViewById(R.id.tv_bio);
            tv_name=itemView.findViewById(R.id.tv_name);
            img_feature=itemView.findViewById(R.id.img_feature);
            img_user=itemView.findViewById(R.id.img_user);
        }
    }
}

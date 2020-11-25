package com.ciesto.evaafashion.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Model.ProductDetailsModel;
import com.ciesto.evaafashion.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Myviewholder> {

    Context context;
    List<ProductDetailsModel.Review> reviews;

    public ReviewAdapter(Activity activity, List<ProductDetailsModel.Review> reviews)
    {
        this.context=activity;
        this.reviews=reviews;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position)
    {
        ProductDetailsModel.Review bean=reviews.get(position);
        holder.tvCustomernm.setText(bean.getName());
        holder.tvDate.setText(bean.getCreated());
        holder.tvReviews.setText(bean.getReview());
        holder.ratingBar.setRating(Float.parseFloat(bean.getRating()));

     /*   if(position+1==reviews.size())
            holder.viewLine.setVisibility(View.GONE);
        else holder.viewLine.setVisibility(View.VISIBLE);*/
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
    

    public class Myviewholder extends RecyclerView.ViewHolder 
    {
        protected TextView tvCustomernm;
        protected RatingBar ratingBar;
        protected TextView tvDate;
        protected TextView tvReviews;
        protected View viewLine;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvCustomernm = (TextView) itemView.findViewById(R.id.tv_customernm);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvReviews = (TextView) itemView.findViewById(R.id.tv_reviews);
            viewLine = (View) itemView.findViewById(R.id.view_line);
        }
    }
}

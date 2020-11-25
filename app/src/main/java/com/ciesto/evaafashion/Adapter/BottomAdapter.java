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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Activity.AtoZActivity;
import com.ciesto.evaafashion.Activity.CartActivity;
import com.ciesto.evaafashion.Activity.LoginActivity;
import com.ciesto.evaafashion.Activity.ProfileActivity;
import com.ciesto.evaafashion.Activity.SubCategoryActivity1;
import com.ciesto.evaafashion.Activity.WardrobeActivity;
import com.ciesto.evaafashion.Activity.WishListActivity;
import com.ciesto.evaafashion.Activity.MainActivity;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;

import java.util.ArrayList;

public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.Myviewholder> {
    Context context;
    private ArrayList<String> dataList;
    private ArrayList<Integer> iconList;
    private int selPos;
    LoginPreferences loginPreferences;
    ExtraPreferences extraPreferences;

    public BottomAdapter(Activity activity, int selPos) {
        this.context = activity;
        this.selPos = selPos;
        loginPreferences = new LoginPreferences(context);
        extraPreferences = new ExtraPreferences(context);
        String language = extraPreferences.getLanguage();


        dataList = new ArrayList<>();
        iconList = new ArrayList<>();

        if (language.toLowerCase().equals("English".toLowerCase())) {
            dataList.add("Home");
            iconList.add(selPos == 0 ? R.drawable.ic_home_fill : R.drawable.ic_home_line);
            dataList.add("Boutique");
            iconList.add(selPos == 1 ?
                    R.drawable.ic_collection_fill
                    : R.drawable.ic_collection_line);

            dataList.add("Wardrobe");
            iconList.add(selPos == 2 ?
                    R.drawable.ic_wardrobe_fill
                    : R.drawable.ic_wardrobe_line);
            dataList.add("Bag");
            iconList.add(selPos == 3 ?
                    R.drawable.ic_bag_fill
                    : R.drawable.ic_bag_line);

            dataList.add("Account");
            iconList.add(selPos == 4 ?
                    R.drawable.ic_user_fill
                    : R.drawable.ic_user_line);
        } else {
            dataList.add("الصفحة الرئيسية");
            iconList.add(selPos == 0 ? R.drawable.ic_home_fill : R.drawable.ic_home_line);
            dataList.add("متجر");
            iconList.add(selPos == 1 ?
                    R.drawable.ic_collection_fill
                    : R.drawable.ic_collection_line);

            dataList.add("خزانة الثياب");
            iconList.add(selPos == 2 ?
                    R.drawable.ic_wardrobe_fill
                    : R.drawable.ic_wardrobe_line);
            dataList.add("حقيبة");
            iconList.add(selPos == 3 ?
                    R.drawable.ic_bag_fill
                    : R.drawable.ic_bag_line);

            dataList.add("الحساب");
            iconList.add(selPos == 4 ?
                    R.drawable.ic_user_fill
                    : R.drawable.ic_user_line);

        }


    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        holder.img.setImageResource(iconList.get(position));
        holder.tv_title.setText(dataList.get(position));

//        if (extraPreferences.getCartcount().equals("")) {
//            holder.notification_badge.setText("0");
//        } else {
//            holder.notification_badge.setText(extraPreferences.getCartcount());
//        }

        if (extraPreferences.getCartcount().equals("")) {
            holder.notification_wishlist.setText("0");
        } else {
            holder.notification_wishlist.setText(extraPreferences.getCartcount());
        }

//        if (position == 2) {
//            holder.notification_badge.setVisibility(View.VISIBLE);
//        } else {
//            holder.notification_badge.setVisibility(View.GONE);
//        }
        if (position == 3) {
            holder.notification_wishlist.setVisibility(View.VISIBLE);
        } else {
            holder.notification_wishlist.setVisibility(View.GONE);
        }

        //Log.e("Sel Pos==", String.valueOf(selPos));

        holder.tv_title.setTextColor(context.getResources()
                .getColor(selPos == position ? R.color.blue : R.color.gray));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selPos != position) {
                    switch (position) {
                        case 0: {

                            ((AppCompatActivity) context).finish();
                            Intent i = new Intent(context, MainActivity.class);
                            context.startActivity(i);
                            Functions.animNext(context);

                            break;
                        }
                        case 1: {
                          /*  Intent i = new Intent(context, SubCategoryActivity1.class);
                            i.putExtra("selected",0);
                            i.putParcelableArrayListExtra("CategoryList", (ArrayList<? extends Parcelable>) Constant.categorylist);
                            context.startActivity(i);*/
                            context.startActivity(new Intent(context, AtoZActivity.class));
                            Functions.animNext(context);

                            break;
                        }
                        case 2: {

                            context.startActivity(new Intent(context, WardrobeActivity.class));
                            Functions.animNext(context);
                           /* if (loginPreferences.isLoggIn()) {
                                if (!extraPreferences.getCartcount().equals("0") && !extraPreferences.getCartcount().equals("")) {
                                    Intent i = new Intent(context, CartActivity.class);
                                    context.startActivity(i);
                                    Functions.animNext(context);
                                } else {
                                    Toast.makeText(context, "" + context.getResources().getString(R.string.your_cart_is_empty), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }*/
                            break;
                        }
                        case 3: {
                            if (loginPreferences.isLoggIn()) {
                                Intent i = new Intent(context, CartActivity.class);
                                context.startActivity(i);
                                Functions.animNext(context);
                            } else {
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                            break;
                        }
                        case 4: {
                            if (loginPreferences.isLoggIn()) {
                                Intent i = new Intent(context, ProfileActivity.class);
                                context.startActivity(i);
                                Functions.animNext(context);
                            } else {
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                            break;
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_title, notification_badge, notification_wishlist;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv_title = itemView.findViewById(R.id.tv_title);
            notification_badge = itemView.findViewById(R.id.notification_badge);
            notification_wishlist = itemView.findViewById(R.id.notification_wishlist);

        }
    }
}

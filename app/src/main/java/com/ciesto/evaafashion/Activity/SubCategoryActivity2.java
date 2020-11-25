package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.SubCategoryAdapter2;
import com.ciesto.evaafashion.Model.SubCategoryModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity2 extends BaseActivity {

    protected ImageView imgMain;
    protected TextView tvName;
    protected FrameLayout frmWoman;
    Activity activity = SubCategoryActivity2.this;
    protected TextView tvCategoryName;
    protected TextView tvItems;
    protected RecyclerView rvSucategory;
    protected TextView tvNodata;
    ProgressDialog progressDialog;
    String CategoryID, Action = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_subcategory2);
        super.onCreateDrawer();
        initView();
        initcomponent();
    }

    private void initView() {
        tvCategoryName = (TextView) findViewById(R.id.tvCategoryName);
        tvItems = (TextView) findViewById(R.id.tvItems);
        rvSucategory = (RecyclerView) findViewById(R.id.rv_sucategory);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        lnrLogo = (LinearLayout) findViewById(R.id.lnr_logo);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        imgMain = (ImageView) findViewById(R.id.img_main);
        tvName = (TextView) findViewById(R.id.tv_name);
        frmWoman = (FrameLayout) findViewById(R.id.frm_woman);
    }

    private void initcomponent() {
        progressDialog = new ProgressDialog(activity);
        CategoryID = getIntent().getStringExtra("Sucategory_id");
        tvCategoryName.setText(getIntent().getStringExtra("Subcategorynm"));
        Log.e("CategoryId==", CategoryID);

        if (getIntent().hasExtra("SubcategoryImages")) {
            tvCategoryName.setVisibility(View.GONE);
            tvItems.setVisibility(View.GONE);
            frmWoman.setVisibility(View.VISIBLE);
            tvName.setText(getIntent().getStringExtra("Subcategorynm"));
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    //.placeholder(R.drawable.demo)
                    .error(R.drawable.demo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(activity)
                    .load(Constant.Category_Image + getIntent().getStringExtra("SubcategoryImages"))
                    .apply(options)
                    .thumbnail(Glide.with(activity).load(R.drawable.loader))
                    .into(imgMain);
            Action = "home";

        } else {
            tvCategoryName.setVisibility(View.VISIBLE);
            tvItems.setVisibility(View.VISIBLE);
            frmWoman.setVisibility(View.GONE);
            Action = "subcategory2";
        }

        if (Constant.isNetworkAvailable(activity))
            GetSubCategory();
        else
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
    }


    private void GetSubCategory() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<SubCategoryModel> call = apiService.GetSubCategory(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), CategoryID);
        call.enqueue(new Callback<SubCategoryModel>() {
            @Override
            public void onResponse(Call<SubCategoryModel> call, Response<SubCategoryModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {
                    tvNodata.setVisibility(View.GONE);
                    rvSucategory.setVisibility(View.VISIBLE);
                    List<SubCategoryModel.Category> categorylist = new ArrayList<>();
                    categorylist = response.body().getCategory();
                    tvItems.setText(categorylist.size() + " items");
                    if (Action.equalsIgnoreCase("home"))
                        rvSucategory.setLayoutManager(new LinearLayoutManager(activity));
                    else
                        rvSucategory.setLayoutManager(new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false));
                    rvSucategory.setAdapter(new SubCategoryAdapter2(activity, categorylist, Action));

                } else {
                    tvNodata.setVisibility(View.VISIBLE);
                    rvSucategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SubCategoryModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBottom();
    }

    private void setBottom() {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);

        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);

        rvBottom.setAdapter(new BottomAdapter(activity, 1));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(activity);
    }
}

package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciesto.evaafashion.Adapter.AtoZProductAdapter;
import com.ciesto.evaafashion.Adapter.AtoZProductFilterAdapter;
import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Model.MerchantDetailsModel;
import com.ciesto.evaafashion.Model.MerchantFilterModel;
import com.ciesto.evaafashion.Model.ProductModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtoZDetailsActivity extends AppCompatActivity {

    protected RecyclerView rvProduct;
    protected RecyclerView rvBottom;
    protected ImageView imgUser;
    protected TextView tvUsername;
    protected TextView tvDes;
    protected TextView tvNodata;
    protected LinearLayout lnrMain;
    protected LinearLayout lnrShortclose;
    protected ImageView ivClose;
    protected RadioButton rbPhighlow;
    protected RadioButton rbPlowhigh;
    protected RadioButton rbNatoz;
    protected RadioButton rbNztoa;
    protected RadioGroup rdGroup;
    protected LinearLayout lnrSort1;
    protected LinearLayout lnrShorlayout;
    protected LinearLayout lnrShort;
    protected TextView tvFilter;
    Activity activity = AtoZDetailsActivity.this;
    String MerchantId;
    String ShortFilter = "", FilterType = "", FilterValue = "", Featured = "";
    ProgressDialog progressDialog;
    String SubCategoryId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_ato_z_details);


        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        rvProduct = (RecyclerView) findViewById(R.id.rv_product);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
        imgUser = (ImageView) findViewById(R.id.img_user);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvDes = (TextView) findViewById(R.id.tv_des);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
        lnrMain = (LinearLayout) findViewById(R.id.lnr_main);
        lnrShortclose = (LinearLayout) findViewById(R.id.lnr_shortclose);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        rbPhighlow = (RadioButton) findViewById(R.id.rb_Phighlow);
        rbPlowhigh = (RadioButton) findViewById(R.id.rb_Plowhigh);
        rbNatoz = (RadioButton) findViewById(R.id.rb_Natoz);
        rbNztoa = (RadioButton) findViewById(R.id.rb_Nztoa);
        rdGroup = (RadioGroup) findViewById(R.id.rd_group);
        lnrSort1 = (LinearLayout) findViewById(R.id.lnr_sort1);
        lnrShorlayout = (LinearLayout) findViewById(R.id.lnr_shorlayout);
        lnrShort = (LinearLayout) findViewById(R.id.lnr_short);
        tvFilter = (TextView) findViewById(R.id.tv_filter);
    }

    private void initComponent() {
        progressDialog = new ProgressDialog(activity);
        MerchantId = getIntent().getStringExtra("Merchantid");

        if (Constant.isNetworkAvailable(activity)) {
            lnrMain.setVisibility(View.GONE);
            GetMerchantDetails();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }

    }

    private void SetListener() {
        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(activity, FilterActivity.class).putExtra("SubcategoryID",SubCategoryId), 123);
                Functions.animNext(activity);
            }
        });

        lnrShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lnrShorlayout.getVisibility() != View.VISIBLE) {
                    lnrShorlayout.setVisibility(View.VISIBLE);
                   /* Animation anim = new ScaleAnimation(
                            1f, 1f, // Start and end values for the X axis scaling
                            0f, 1f, // Start and end values for the Y axis scaling
                            Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                            Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
                    anim.setFillAfter(true); // Needed to keep the result of the animation
                    anim.setDuration(400);*/
                    ScaleAnimation anim = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
                    anim.setDuration(300);
                    lnrShorlayout.startAnimation(anim);
                }

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseLayout();
            }
        });
        lnrShortclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseLayout();
            }
        });

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rdGroup.getCheckedRadioButtonId()) {
                    case R.id.rb_Phighlow:
                        CloseLayout();
                        ShortFilter = "h_to_l";
                        if (Constant.isNetworkAvailable(activity)) {
                            GetFilter();
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.rb_Plowhigh:
                        CloseLayout();
                        ShortFilter = "l_to_h";
                        if (Constant.isNetworkAvailable(activity)) {
                            GetFilter();
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.rb_Natoz:
                        CloseLayout();
                        ShortFilter = "a_to_z";
                        if (Constant.isNetworkAvailable(activity)) {
                            GetFilter();
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.rb_Nztoa:
                        CloseLayout();
                        ShortFilter = "z_to_a";
                        if (Constant.isNetworkAvailable(activity)) {
                            GetFilter();
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                        }
                        break;

                }
            }
        });

    }

    public void GetMerchantDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<MerchantDetailsModel> call = apiService.GetMerchantDetails(Constant.ACCESSKEY, MerchantId, new ExtraPreferences(activity).getLanguage(), new LoginPreferences(activity).getUserID());
        call.enqueue(new Callback<MerchantDetailsModel>() {
            @Override
            public void onResponse(Call<MerchantDetailsModel> call, Response<MerchantDetailsModel> response) {
                progressDialog.dismiss();
                lnrMain.setVisibility(View.VISIBLE);
                if (response.body().getStatus().equals("1")) {

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            //.placeholder(R.drawable.demo)
                            .error(R.drawable.demo)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH);

                    Glide.with(activity)
                            .load(Constant.Profile_Images + response.body().getMerchant().getProfileImage())
                            .apply(options)
                            .thumbnail(Glide.with(activity).load(R.drawable.loader))
                            .into(imgUser);


                    tvUsername.setText(response.body().getMerchant().getMerchantName());
                    tvDes.setText(Html.fromHtml(response.body().getMerchant().getBio()));
                    if (response.body().getMerchant().getProducts().size() > 0) {
                        rvProduct.setVisibility(View.VISIBLE);
                        tvNodata.setVisibility(View.GONE);

                        rvProduct.setLayoutManager(new GridLayoutManager(activity, 2));
                        rvProduct.setAdapter(new AtoZProductAdapter(activity, response.body().getMerchant().getProducts(), response.body().getMerchant().getMerchantId()));
                    } else {
                        rvProduct.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);

                    }


                } else {
                    lnrMain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MerchantDetailsModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    private void CloseLayout() {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        anim.setDuration(300);
        lnrShorlayout.startAnimation(anim);
        lnrShorlayout.setVisibility(View.GONE);
    }

    //================================================ApplyFilter===========================
    private void GetFilter() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.e("UserId==", new LoginPreferences(activity).getUserID());
        Log.e("Filter==", FilterType);
        Log.e("FilterValue==", FilterValue);
        Log.e("ShortFilter==", ShortFilter);
        Log.e("SubCategoryId==", SubCategoryId);

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<MerchantFilterModel> call = apiService.GetMerchantFilter(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), new LoginPreferences(activity).getUserID(), FilterType, FilterValue, ShortFilter,"Merchant",MerchantId, SubCategoryId);
        call.enqueue(new Callback<MerchantFilterModel>() {
            @Override
            public void onResponse(Call<MerchantFilterModel> call, Response<MerchantFilterModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {
                    rvProduct.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);

                    if (response.body().getProducts().size() > 0) {
                        rvProduct.setVisibility(View.VISIBLE);
                        tvNodata.setVisibility(View.GONE);

                        rvProduct.setLayoutManager(new GridLayoutManager(activity, 2));
                        rvProduct.setAdapter(new AtoZProductFilterAdapter(activity, response.body().getProducts(), MerchantId));
                    } else {
                        rvProduct.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);
                    }


                } else {
                    rvProduct.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);

                    // Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MerchantFilterModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    //==================================FilterApply==========================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 123) {
            String UnitId = "", Priceid = "", Subcategoryid = "";
            FilterType = "";
            if (Constant.UnitIdList.size() > 0) {
                FilterType = FilterType + "Unit" + ",";
                for (int i = 0; i < Constant.UnitIdList.size(); i++) {
                    if (i == 0)
                        UnitId = UnitId + Constant.UnitIdList.get(i);
                    else
                        UnitId = UnitId + "," + Constant.UnitIdList.get(i);
                }
            }
            if (Constant.PriceIdList.size() > 0) {
                FilterType = FilterType + "Price" + ",";
                for (int i = 0; i < Constant.PriceIdList.size(); i++) {
                    if (i == 0)
                        Priceid = Priceid + Constant.PriceIdList.get(i);
                    else
                        Priceid = Priceid + "," + Constant.PriceIdList.get(i);
                }
            }
            Log.e("UnitId==", UnitId);
            Log.e("PriceId==", Priceid);
            Log.e("SubCategoryId==", Subcategoryid);

            JSONArray personarray = new JSONArray();
            try {
                JSONObject person1 = null;
                person1 = new JSONObject();
                person1.put("Unit_Id", UnitId);
                person1.put("Price_Value", Priceid);
                personarray.put(person1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FilterValue = personarray.toString();
            // Log.e("FilterValue=", FilterValue);
            GetFilter();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(activity);
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


}
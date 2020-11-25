package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.ProductAdapter;
import com.ciesto.evaafashion.Model.ProductModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends BaseActivity {

    protected RadioButton rvPhighlow;
    protected RadioButton rvPlowhigh;
    protected RadioButton rvNatoz;
    protected RadioButton rvNztoa;
    protected RecyclerView rvBottom;
    protected NavigationView navView;
    protected DrawerLayout drawerLayout;
    protected RadioButton rbPhighlow;
    protected RadioButton rbPlowhigh;
    protected RadioButton rbNatoz;
    protected RadioButton rbNztoa;
    protected LinearLayout lnrFiltermenu;
    protected LinearLayout lnrShortclose;
    Activity activity = ProductActivity.this;
    protected TextView tvCategoryName;
    protected TextView tvItems;
    protected RecyclerView rvProduct;
    protected TextView tvNodata;
    protected LinearLayout lnrMain;
    protected RadioGroup rdGroup;
    protected LinearLayout lnrShorlayout;
    protected LinearLayout lnrShort, lnrsort1;
    protected TextView tvCategory;
    protected LinearLayout lnrFilter;
    String SubCategoryId;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    ImageView iv_close;
    ArrayList<ProductModel.Product> productlist;
    String ShortFilter = "", FilterType = "", FilterValue = "",Featured="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_product_activity);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        tvCategoryName = (TextView) findViewById(R.id.tvCategoryName);
        tvItems = (TextView) findViewById(R.id.tvItems);
        rvProduct = (RecyclerView) findViewById(R.id.rv_product);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
        lnrMain = (LinearLayout) findViewById(R.id.lnr_main);
        rdGroup = (RadioGroup) findViewById(R.id.rd_group);
        lnrShorlayout = (LinearLayout) findViewById(R.id.lnr_shorlayout);
        lnrShort = (LinearLayout) findViewById(R.id.lnr_short);
        lnrsort1 = (LinearLayout) findViewById(R.id.lnr_sort1);
        lnrFilter = (LinearLayout) findViewById(R.id.lnr_filter);
        lnrLogo = (LinearLayout) findViewById(R.id.lnr_logo);

        rbPhighlow = (RadioButton) findViewById(R.id.rb_Phighlow);
        rbPlowhigh = (RadioButton) findViewById(R.id.rb_Plowhigh);
        rbNatoz = (RadioButton) findViewById(R.id.rb_Natoz);
        rbNztoa = (RadioButton) findViewById(R.id.rb_Nztoa);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
        lnrFiltermenu = (LinearLayout) findViewById(R.id.lnr_filtermenu);
        navView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lnrShortclose = (LinearLayout) findViewById(R.id.lnr_shortclose);
        iv_close = (ImageView) findViewById(R.id.iv_close);
    }

    private void initComponent() {

        progressDialog = new ProgressDialog(activity);
        loginPreferences = new LoginPreferences(activity);
        productlist = new ArrayList<>();

        Featured=getIntent().getStringExtra("Action");
        if(getIntent().hasExtra("Sucategory_id")) {
            SubCategoryId = getIntent().getStringExtra("Sucategory_id");
            tvCategoryName.setText(getIntent().getStringExtra("Subcategorynm"));
            tvItems.setVisibility(View.VISIBLE);
        }
        else
        {
            SubCategoryId="0";
            tvCategoryName.setText(getResources().getString(R.string.featured_products));
            tvItems.setVisibility(View.GONE);
        }
        Log.e("SubcategoryId==", SubCategoryId);
        Log.e("Featured==", Featured);


        if (Constant.isNetworkAvailable(activity)) {
            GetProduct();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void SetListener() {

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

        iv_close.setOnClickListener(new View.OnClickListener() {
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

        lnrFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(activity, FilterActivity.class).putExtra("SubcategoryID",SubCategoryId), 123);
                Functions.animNext(activity);
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

    //===============================================GetProduct===============================

    private void GetProduct() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<ProductModel> call = apiService.GetProduct(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), SubCategoryId,Featured);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {
                    rvProduct.setVisibility(View.VISIBLE);
                    lnrFiltermenu.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);

                    if (productlist.size() > 0)
                        productlist.clear();

                    for (int i = 0; i < response.body().getProducts().size(); i++) {
                        productlist.add(response.body().getProducts().get(i));
                    }
                    tvItems.setText(productlist.size() + " items");
                    Constant.ProductCount= String.valueOf(productlist.size());
                    rvProduct.setLayoutManager(new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false));
                    rvProduct.setAdapter(new ProductAdapter(activity, productlist));

                } else {
                    rvProduct.setVisibility(View.GONE);
                    lnrFiltermenu.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                    rvProduct.setLayoutManager(new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false));
                    rvProduct.setAdapter(new ProductAdapter(activity, productlist));
                    // Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
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
        Call<ProductModel> call = apiService.GetFilter(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), new LoginPreferences(activity).getUserID(), FilterType, FilterValue, ShortFilter,"Product" ,SubCategoryId);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {
                    rvProduct.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);

                    if (productlist.size() > 0)
                        productlist.clear();


                    for (int i = 0; i < response.body().getProducts().size(); i++) {
                        productlist.add(response.body().getProducts().get(i));
                    }
                    tvItems.setText(productlist.size() + " items");
                    rvProduct.setLayoutManager(new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false));
                    rvProduct.setAdapter(new ProductAdapter(activity, productlist));

                } else {
                    rvProduct.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                    rvProduct.setLayoutManager(new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false));
                    rvProduct.setAdapter(new ProductAdapter(activity, productlist));
                    // Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
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

    private void CloseLayout() {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        anim.setDuration(300);
        lnrShorlayout.startAnimation(anim);
        lnrShorlayout.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        if (lnrShorlayout.getVisibility() == View.VISIBLE) {
            CloseLayout();
        } else {
            super.onBackPressed();
            if (Constant.UnitIdList.size() > 0)
                Constant.UnitIdList.clear();
            if (Constant.PriceIdList.size() > 0)
                Constant.PriceIdList.clear();
            Functions.animBack(activity);
        }
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

        rvBottom.setAdapter(new BottomAdapter(activity, 100));
    }
}


package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.CategoryAdapter;
import com.ciesto.evaafashion.Adapter.SubCategoryAdapter;
import com.ciesto.evaafashion.Model.CategoryModel;
import com.ciesto.evaafashion.Model.HomeScreenModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity1 extends BaseActivity
{

    protected LinearLayout lnrMain;
    protected LinearLayout lnrShorlayout;
    protected LinearLayout lnrShort;
    protected TextView tvCategory;
    protected LinearLayout lnrFilter;
    protected NavigationView navView;
    protected DrawerLayout drawerLayout;
    protected RadioGroup rdGroup;
    protected ImageView imgSlide;
    protected RecyclerView rvBottom;
    Activity activity = SubCategoryActivity1.this;
    protected RecyclerView rvCatecory;
    protected RecyclerView rvProduct;
    protected TextView tvNodata;
    ArrayList<CategoryModel.Category> maintlist;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    int pos=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_subcategory1);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        rvCatecory = (RecyclerView) findViewById(R.id.rv_catecory);
        rvProduct = (RecyclerView) findViewById(R.id.rv_product);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
        lnrMain = (LinearLayout) findViewById(R.id.lnr_main);
        lnrShorlayout = (LinearLayout) findViewById(R.id.lnr_shorlayout);
        lnrShort = (LinearLayout) findViewById(R.id.lnr_short);
        tvCategory = (TextView) findViewById(R.id.tv_category);
        lnrFilter = (LinearLayout) findViewById(R.id.lnr_filter);
        navView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        rdGroup = (RadioGroup) findViewById(R.id.rd_group);
        imgSlide = (ImageView) findViewById(R.id.img_slide);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
    }

    private void initComponent()
    {
        loginPreferences = new LoginPreferences(activity);
        progressDialog = new ProgressDialog(activity);
        maintlist = new ArrayList<>();

        //pos=getIntent().getIntExtra("selected",-1);
        //List<HomeScreenModel.Category> categorylist = (List<HomeScreenModel.Category>) getIntent().getSerializableExtra("CategoryList");

    }

    private void SetListener()
    {

        if (Constant.isNetworkAvailable(activity)) {
            GetCategory();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }

        lnrShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnrShorlayout.setVisibility(View.VISIBLE);
            }
        });
        rdGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnrShorlayout.setVisibility(View.GONE);
            }
        });
    }

    private void GetCategory()
    {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<CategoryModel> call = apiService.GetCategory(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage());
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {

                    rvCatecory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                    rvCatecory.setAdapter(new CategoryAdapter(activity, response.body().getCategory(),0));

                    for (int i = 0; i < response.body().getCategory().size(); i++) {
                        maintlist.add(response.body().getCategory().get(i));
                    }
                    Log.e("Pos==", String.valueOf(pos));
                    SetProduct(pos);

                } else {

                    // Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    public void SetProduct(int pos)
    {
        List<CategoryModel.SubCategory> subCategoryList=new ArrayList<>();

        subCategoryList=maintlist.get(pos).getSubCategory();

        if(maintlist.get(pos).getSlider()!=null && maintlist.get(pos).getSlider().size()>0) {

            Picasso.get()
                    .load(Constant.Slider_Image + maintlist.get(pos).getSlider().get(0).getSliderImage())
                    .error(R.drawable.demo)
                    .into(imgSlide);
        }
        if(subCategoryList.size()>0)
        {
            rvProduct.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
        }
        else
        {
            rvProduct.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }

        rvProduct.setLayoutManager(new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false));
        rvProduct.setAdapter(new SubCategoryAdapter(activity,subCategoryList));
    }

    @Override
    public void onBackPressed() {
        if (lnrShorlayout.getVisibility() == View.VISIBLE) {
            lnrShorlayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
            Functions.animBack(activity);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBottom();
    }

    private void setBottom()
    {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);

        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);

        rvBottom.setAdapter(new BottomAdapter(activity, 1));
    }
}

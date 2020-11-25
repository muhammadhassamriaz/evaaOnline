package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.FilterAdapter;
import com.ciesto.evaafashion.Adapter.FilterCategoryAdapter;
import com.ciesto.evaafashion.Model.FilterModel;
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

public class FilterActivity extends BaseActivity {


    protected ImageView imgClose;
    protected TextView tvItemCount;
    protected TextView tvreset;
    protected TextView tvapply;
    protected Spinner spCateogry;
    protected EditText etSearch;
    Activity activity = FilterActivity.this;
    protected RecyclerView rvFiltercategory;
    protected RecyclerView rvFilter;
    ProgressDialog progressDialog;
    List<FilterModel.Filter> filtercategorylist;
    int flag = 0,datapos;
    FilterAdapter filterAdapter;
    String SubcategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_filter);
        super.onCreateDrawer();

        initView();
        initcomponent();
        SetListener();

    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        rvFiltercategory = (RecyclerView) findViewById(R.id.rv_filtercategory);
        rvFilter = (RecyclerView) findViewById(R.id.rv_filter);
        imgClose = (ImageView) findViewById(R.id.img_close);
        tvItemCount = (TextView) findViewById(R.id.tv_itemCount);
        tvreset = (TextView) findViewById(R.id.tvreset);
        tvapply = (TextView) findViewById(R.id.tvapply);
        lnrLogo = (LinearLayout) findViewById(R.id.lnr_logo);
        etSearch = (EditText) findViewById(R.id.et_search);
    }

    private void initcomponent() {
        progressDialog = new ProgressDialog(activity);
        filtercategorylist = new ArrayList<>();
        SubcategoryID=getIntent().getStringExtra("SubcategoryID");
        Log.e("SubcategoryID==",SubcategoryID);

        tvItemCount.setText(Constant.ProductCount+"  "+getResources().getString(R.string.product_available));

        if (Constant.isNetworkAvailable(activity)) {
            GetFilterData();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }

    }

    private void SetListener() {

        tvapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                setResult(123, i);
                onBackPressed();
            }
        });

        tvreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.UnitIdList.size() > 0)
                    Constant.UnitIdList.clear();
                if (Constant.PriceIdList.size() > 0)
                    Constant.PriceIdList.clear();
                if (Constant.isNetworkAvailable(activity)) {
                    GetFilterData();
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                }

            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                /*if(datapos==0) {
                    filterSubcategoryAdapter.getFilter().filter(s);
                }
                else
                {*/
                    filterAdapter.getFilter().filter(s);
               // }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //===============================================GetFilterData===============================

    private void GetFilterData() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<FilterModel> call = apiService.GetFilterData(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage());
        call.enqueue(new Callback<FilterModel>() {
            @Override
            public void onResponse(Call<FilterModel> call, Response<FilterModel> response) {
                progressDialog.dismiss();
                Log.e("Filterdata==", response.body().toString());
                if (response.body().getStatus().equals("1")) {

                    if (filtercategorylist.size() > 0)
                        filtercategorylist.clear();

                    filtercategorylist = response.body().getFilters();
                    rvFiltercategory.setLayoutManager(new LinearLayoutManager(activity));
                    rvFiltercategory.setAdapter(new FilterCategoryAdapter(activity, filtercategorylist));

                    SetData(0, 0);

                } else {
                    Toast.makeText(activity, "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    public void SetData(int pos, int sp_pos) {
        List<FilterModel.Datum> datalist = new ArrayList<>();
        if (datalist.size() > 0)
            datalist.clear();
        datalist = filtercategorylist.get(pos).getData();
        Log.e("Pos==", String.valueOf(pos));
        Log.e("Sppiner Pos==", String.valueOf(sp_pos));
        datapos=pos;
       /* if (pos == 0) {
            spCateogry.setVisibility(View.VISIBLE);
            if (flag == 0) {
                spCateogry.setAdapter(new CategorySppinerAdapter(activity, android.R.layout.simple_spinner_item, datalist));
                flag = 1;
            }
            List<FilterModel.SubCategory> subCategoryList = new ArrayList<>();
            subCategoryList = datalist.get(sp_pos).getSubCategory();
            rvFilter.setLayoutManager(new LinearLayoutManager(activity));
            filterSubcategoryAdapter=new FilterSubcategoryAdapter(activity, subCategoryList);
            rvFilter.setAdapter(filterSubcategoryAdapter);
        } else {*/
            rvFilter.setLayoutManager(new LinearLayoutManager(activity));
            filterAdapter = new FilterAdapter(activity, datalist);
            rvFilter.setAdapter(filterAdapter);
       // }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Functions.animBack(activity);
    }
}

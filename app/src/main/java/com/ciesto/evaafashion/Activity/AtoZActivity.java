package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.AtoZCategoryAdapter;
import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Model.AtoZModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtoZActivity extends BaseActivity {
    protected RecyclerView rvCatecory;
    protected RecyclerView rvBottom;
    protected TextView tvNodata;
    protected EditText edtSearch;
    Activity activity = AtoZActivity.this;
    ProgressDialog progressDialog;
    AtoZCategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_ato_z);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();

    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        lnrLogo = (LinearLayout) findViewById(R.id.lnr_logo);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        rvCatecory = (RecyclerView) findViewById(R.id.rv_catecory);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
        edtSearch = (EditText) findViewById(R.id.edt_search);
    }

    private void initComponent() {

        if (Constant.MerchantFilter.size() > 0)
            Constant.MerchantFilter.clear();

        if (Constant.isNetworkAvailable(activity)) {
            GetMerchantList();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void SetListener() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // adapter.getFilter().filter(charSequence);
                //adapter.GetFilter(charSequence);

                if (charSequence.length() > 2) {
                    if (Constant.isNetworkAvailable(activity)) {
                        Searchlist();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                } else {

                    if (edtSearch.getText().toString().equalsIgnoreCase("")) {
                        if (Constant.isNetworkAvailable(activity)) {
                            GetMerchantList();
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void GetMerchantList() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<AtoZModel> call = apiService.GetMerchantList(Constant.ACCESSKEY, "Company");
        call.enqueue(new Callback<AtoZModel>() {
            @Override
            public void onResponse(Call<AtoZModel> call, Response<AtoZModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("1")) {

                    if (response.body().getMerchant().size() > 0) {
                        rvCatecory.setVisibility(View.VISIBLE);
                        tvNodata.setVisibility(View.GONE);

                        rvCatecory.setLayoutManager(new LinearLayoutManager(activity));
                        adapter = new AtoZCategoryAdapter(activity, response.body().getMerchant());
                        rvCatecory.setAdapter(adapter);
                    } else {
                        rvCatecory.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);

                    }


                } else {
                    rvCatecory.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AtoZModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }


    public void Searchlist() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<AtoZModel> call = apiService.GetMerchantSearch(Constant.ACCESSKEY, "Company", edtSearch.getText().toString());
        call.enqueue(new Callback<AtoZModel>() {
            @Override
            public void onResponse(Call<AtoZModel> call, Response<AtoZModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("1")) {

                    if (response.body().getMerchant().size() > 0) {
                        rvCatecory.setVisibility(View.VISIBLE);
                        tvNodata.setVisibility(View.GONE);

                        rvCatecory.setLayoutManager(new LinearLayoutManager(activity));
                        adapter = new AtoZCategoryAdapter(activity, response.body().getMerchant());
                        rvCatecory.setAdapter(adapter);
                    } else {
                        rvCatecory.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);
                    }

                } else {
                    rvCatecory.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AtoZModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
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
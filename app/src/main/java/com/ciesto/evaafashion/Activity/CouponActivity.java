package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.CouponAdapter;
import com.ciesto.evaafashion.Model.CouponModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponActivity extends BaseActivity {
    Activity activity = CouponActivity.this;
    protected RecyclerView rvCouponlist;
    protected TextView tvNodata;
    ProgressDialog progressDialog;
    String Merchantid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_coupon);
        super.onCreateDrawer();
        initView();
        initComponet();
        SetListener();
    }

    private void initView() {
        rvCouponlist = (RecyclerView) findViewById(R.id.rv_couponlist);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);

    }

    private void initComponet() {
        progressDialog = new ProgressDialog(activity);
        Merchantid=getIntent().getStringExtra("MerchantId");
    }

    private void SetListener() {
        if (Constant.isNetworkAvailable(activity)) {
            GetCoupon();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    public void GetCoupon() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.e("Merchantid=",Merchantid);

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<CouponModel> call = apiService.GetCoupon(Constant.ACCESSKEY,Merchantid);
        call.enqueue(new Callback<CouponModel>() {
            @Override
            public void onResponse(Call<CouponModel> call, Response<CouponModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("1")) {

                    rvCouponlist.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);

                    ArrayList<CouponModel.Coupon> couponlist = new ArrayList<>();
                    for (int i = 0; i < response.body().getCoupons().size(); i++) {
                        couponlist.add(response.body().getCoupons().get(i));
                    }
                    rvCouponlist.setLayoutManager(new LinearLayoutManager(activity));
                    rvCouponlist.setAdapter(new CouponAdapter(activity, couponlist));

                } else {
                    rvCouponlist.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CouponModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    @Override
    public void onBackPressed()
    {
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

        rvBottom.setAdapter(new BottomAdapter(activity, 100));
    }


}

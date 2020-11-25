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
import com.ciesto.evaafashion.Adapter.PendingOrderAdapter;
import com.ciesto.evaafashion.Model.PandingOrderModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingOrderActivity extends BaseActivity {

    Activity activity=PendingOrderActivity.this;
    protected RecyclerView rvOrder;
    protected TextView tvNodata;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_pending_order);
        super.onCreateDrawer();
        initView();
        initComponent();
    }

    private void initView() {
        rvOrder = (RecyclerView) findViewById(R.id.rv_order);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
    }

    private void initComponent()
    {
        progressDialog=new ProgressDialog(activity);
        loginPreferences=new LoginPreferences(activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBottom();
        if (Constant.isNetworkAvailable(activity)) {
            GetOrder();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    public void GetOrder()
    {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<PandingOrderModel> call = apiService.GetPandingOrder(Constant.ACCESSKEY,loginPreferences.getUserID(),"0");
        call.enqueue(new Callback<PandingOrderModel>() {
            @Override
            public void onResponse(Call<PandingOrderModel> call, Response<PandingOrderModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("1")) {

                    rvOrder.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);

                    ArrayList<PandingOrderModel.Order> orderlist = new ArrayList<>();
                    for (int i = 0; i < response.body().getOrders().size(); i++) {
                        orderlist.add(response.body().getOrders().get(i));
                    }
                    rvOrder.setLayoutManager(new LinearLayoutManager(activity));
                    rvOrder.setAdapter(new PendingOrderAdapter(activity, orderlist));

                } else {
                    rvOrder.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PandingOrderModel> call, Throwable t) {
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



    private void setBottom() {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);

        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);

        rvBottom.setAdapter(new BottomAdapter(activity, 100));
    }
}

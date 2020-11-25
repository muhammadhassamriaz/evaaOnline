package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.OrderMerchantAdapter;
import com.ciesto.evaafashion.Model.OrderDetails;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteOrderDetailsActivity extends BaseActivity {

    Activity activity=CompleteOrderDetailsActivity.this;
    protected ImageView tvMenu;
    protected TextView tvOrderStatus;
    protected RecyclerView rvmerchantorderlist;
    protected RecyclerView rvBottom;
    protected LinearLayout linearLayout;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    String OrderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_complete_order_details);
        super.onCreateDrawer();
        initView();
        initComponent();
    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        tvOrderStatus = (TextView) findViewById(R.id.tv_orderStatus);
        rvmerchantorderlist = (RecyclerView) findViewById(R.id.rvmerchantorderlist);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    private void initComponent() {
        progressDialog = new ProgressDialog(activity);
        loginPreferences = new LoginPreferences(activity);
        OrderID = getIntent().getStringExtra("OrderId");
        tvOrderStatus.setText(getResources().getString(R.string.status)+" : "+getIntent().getStringExtra("OrderStatus"));
        Log.e("OrderID==", OrderID);

        if (Constant.isNetworkAvailable(activity)) {
            OrderDetails();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void OrderDetails() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<OrderDetails> call = apiService.GetOrderDetails(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), loginPreferences.getUserID(), OrderID);
        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                progressDialog.dismiss();


                if (response.body().getStatus().equalsIgnoreCase("1")) {
                    List<OrderDetails.Order> orderList = new ArrayList<>();
                    for (int i = 0; i < response.body().getOrders().size(); i++) {
                        orderList.add(response.body().getOrders().get(i));
                    }
                    rvmerchantorderlist.setLayoutManager(new LinearLayoutManager(activity));
                    rvmerchantorderlist.setAdapter(new OrderMerchantAdapter(activity, orderList));

                } else {
                    //  Toast.makeText(activity, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
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

        rvBottom.setAdapter(new BottomAdapter(activity, 100));
    }

}

package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
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
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends BaseActivity {

    protected CardView cardHomepage;
    protected RecyclerView rvmerchantorderlist;
    protected CardView cardCancleorder;

    Activity activity = OrderDetailsActivity.this;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    String OrderID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_order_details);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();

    }

    private void initView() {
        cardHomepage = (CardView) findViewById(R.id.card_homepage);
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        rvmerchantorderlist = (RecyclerView) findViewById(R.id.rvmerchantorderlist);
        cardCancleorder = (CardView) findViewById(R.id.card_cancleorder);
    }

    private void initComponent() {
        progressDialog = new ProgressDialog(activity);
        loginPreferences = new LoginPreferences(activity);
        OrderID = getIntent().getStringExtra("OrderId");
        Log.e("OrderID==", OrderID);
    }

    private void SetListener() {
        if (Constant.isNetworkAvailable(activity)) {
            OrderDetails();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }

        cardHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, MainActivity.class));
                finish();
                Functions.animNext(activity);
            }
        });

        cardCancleorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(getResources().getString(R.string.are_you_sure_you_want_to_cancel_this_order))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (Constant.isNetworkAvailable(activity)) {
                                    RemoveOrder();
                                } else {
                                    Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


    }

    //=============================================OrderDetails==============================
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

    //===========================================RemoveOrders==============================
    private void RemoveOrder() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.CancleOrder(Constant.ACCESSKEY, OrderID);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1"))
                    {
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    } else {
                        Toast.makeText(activity, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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

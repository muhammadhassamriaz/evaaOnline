package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.MerchantAdapter;
import com.ciesto.evaafashion.Model.CartModel;
import com.ciesto.evaafashion.Model.MerchantModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantActivity extends BaseActivity {

    protected ImageView ivproductimg;
    protected TextView tvproductnm;
    protected RatingBar ratingBar;
    protected DrawerLayout drawerLayout;
    protected TextView tvcontinue;
    Activity activity = MerchantActivity.this;
    protected RecyclerView rvMerchantlist;
    protected TextView tvNoData;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    String ProductId, PriceID, Qty;
    MerchantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_merchant);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        rvMerchantlist = (RecyclerView) findViewById(R.id.rv_merchantlist);
        tvNoData = (TextView) findViewById(R.id.tvNoData);
        ivproductimg = (ImageView) findViewById(R.id.ivproductimg);
        tvproductnm = (TextView) findViewById(R.id.tvproductnm);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        lnrLogo = (LinearLayout) findViewById(R.id.lnr_logo);
        tvcontinue = (TextView) findViewById(R.id.tvcontinue);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvcontinue.setVisibility(View.VISIBLE);
    }

    private void initComponent() {
        loginPreferences = new LoginPreferences(activity);
        progressDialog = new ProgressDialog(activity);
        ProductId = getIntent().getStringExtra("ProductID");
        PriceID = getIntent().getStringExtra("PriceID");
        Qty = getIntent().getStringExtra("Qty");
        tvproductnm.setText(getIntent().getStringExtra("Productnm"));
        ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("Productrating")));
        Picasso.get()
                .load(Constant.Product_Image + getIntent().getStringExtra("Productimg"))
                .error(R.drawable.demo)
                .into(ivproductimg);

        if (Constant.isNetworkAvailable(activity)) {
            GetMerchantlist();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void SetListener() {
        tvcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!new ExtraPreferences(activity).getCartcount().equals("0") && !new ExtraPreferences(activity).getCartcount().equals("")) {
                    startActivity(new Intent(activity,CartActivity.class));
                    Functions.animNext(activity);
                    finish();
                }
                else
                {
                    Toast.makeText(activity, ""+getResources().getString(R.string.pelase_select_supplier), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void GetMerchantlist() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.e("ProductId=", ProductId);
        Log.e("PriceID=", PriceID);

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<MerchantModel> call = apiService.GetMerchantByProduct(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), ProductId, PriceID);
        call.enqueue(new Callback<MerchantModel>() {
            @Override
            public void onResponse(Call<MerchantModel> call, Response<MerchantModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {

                    rvMerchantlist.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);

                    ArrayList<MerchantModel.Merchant> merchantlist = new ArrayList<>();
                    for (int i = 0; i < response.body().getMerchant().size(); i++) {
                        merchantlist.add(response.body().getMerchant().get(i));
                    }
                    rvMerchantlist.setLayoutManager(new LinearLayoutManager(activity));
                    adapter=new MerchantAdapter(activity, merchantlist, Qty);
                    rvMerchantlist.setAdapter(adapter);

                } else {
                    rvMerchantlist.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);

                    // Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MerchantModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    //=============================GetCartCount=================================

    public void GetCartlist() {
        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<CartModel> call = apiService.GetShoppingCart(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), loginPreferences.getUserID());
        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {
                    int count = response.body().getProducts().size();
                    new ExtraPreferences(activity).setCartcount(String.valueOf(count));
                    setBottom();

                } else {

                }
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
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

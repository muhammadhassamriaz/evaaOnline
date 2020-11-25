package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.WishListAdapter;
import com.ciesto.evaafashion.Model.WishlistModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListActivity extends BaseActivity {
    Activity activity = WishListActivity.this;
    protected RecyclerView rvWishlist;
    protected TextView tvNoData;
    protected RecyclerView rvBottom;
    LoginPreferences loginPreferences;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_wish_list);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        rvWishlist = (RecyclerView) findViewById(R.id.rv_wishlist);
        tvNoData = (TextView) findViewById(R.id.tvNoData);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
    }

    private void initComponent() {
        loginPreferences = new LoginPreferences(activity);
        progressDialog = new ProgressDialog(activity);
    }

    private void SetListener() {
        if (Constant.isNetworkAvailable(activity)) {
            GetWishlist();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void GetWishlist() {

        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<WishlistModel> call = apiService.GetWishlist(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), loginPreferences.getUserID());
        call.enqueue(new Callback<WishlistModel>() {
            @Override
            public void onResponse(Call<WishlistModel> call, Response<WishlistModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {

                    rvWishlist.setVisibility(View.VISIBLE);
                    tvNoData.setVisibility(View.GONE);

                    ArrayList<WishlistModel.Product> wishlist = new ArrayList<>();
                    for (int i = 0; i < response.body().getProducts().size(); i++) {
                        wishlist.add(response.body().getProducts().get(i));
                    }
                    rvWishlist.setLayoutManager(new GridLayoutManager(activity, 2));
                    rvWishlist.setAdapter(new WishListAdapter(activity, wishlist));

                } else {
                    rvWishlist.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<WishlistModel> call, Throwable t) {
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

    public void setBottom() {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);

        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);

//        rvBottom.setAdapter(new BottomAdapter(activity, 7));
    }
}

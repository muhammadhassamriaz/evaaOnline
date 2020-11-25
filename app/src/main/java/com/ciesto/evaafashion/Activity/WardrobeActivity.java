package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.WardrobeAdapter;
import com.ciesto.evaafashion.Model.WardrobModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WardrobeActivity extends BaseActivity {

    protected RecyclerView rvWardrobecategory;
    protected RecyclerView rvBottom;
    protected TextView tvNodata;
    protected EditText edtSearch;
    protected TextView tvShowmore;
    protected TextView tvdes;
    Activity activity = WardrobeActivity.this;
    ProgressDialog progressDialog;
    String string1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_wardrobe);
        super.onCreateDrawer();

        initView();
        initcomponet();
        SetListener();
    }

    private void initcomponet() {
        if (Constant.isNetworkAvailable(activity)) {
            GetMerchantList();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void SetListener() {




            if (getResources().getString(R.string.wardrobe_content).length() < 150) {
                string1 = getResources().getString(R.string.wardrobe_content);
                tvdes.setVisibility(View.VISIBLE);
                tvShowmore.setVisibility(View.GONE);
            } else {
                string1 = getResources().getString(R.string.wardrobe_content).substring(0, 150) + "...";
                tvdes.setVisibility(View.VISIBLE);
                tvShowmore.setVisibility(View.VISIBLE);
            }

            tvdes.setText(Html.fromHtml(string1));


        tvShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tvdes.getText().toString().length()==153) {
                    tvShowmore.setText("Show Less");
                    tvdes.setText(getResources().getString(R.string.wardrobe_content));
                }
                else
                {
                    tvShowmore.setText("Show More");
                    tvdes.setText(Html.fromHtml(string1));
                }
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
        Call<WardrobModel> call = apiService.GetMerchantListWithDesigner(Constant.ACCESSKEY, "Fashion Designer");
        call.enqueue(new Callback<WardrobModel>() {
            @Override
            public void onResponse(Call<WardrobModel> call, Response<WardrobModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("1")) {

                    if (response.body().getMerchant().size() > 0) {
                        rvWardrobecategory.setVisibility(View.VISIBLE);
                        tvNodata.setVisibility(View.GONE);

                        rvWardrobecategory.setLayoutManager(new GridLayoutManager(activity, 2));
                        rvWardrobecategory.setAdapter(new WardrobeAdapter(activity, response.body().getMerchant()));
                    } else {
                        rvWardrobecategory.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);

                    }


                } else {
                    rvWardrobecategory.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<WardrobModel> call, Throwable t) {
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
        Call<WardrobModel> call = apiService.GetMerchantFilter(Constant.ACCESSKEY, "Fashion Designer", edtSearch.getText().toString());
        call.enqueue(new Callback<WardrobModel>() {
            @Override
            public void onResponse(Call<WardrobModel> call, Response<WardrobModel> response) {
                progressDialog.dismiss();
                if (response.body().getStatus().equals("1")) {

                    if (response.body().getMerchant().size() > 0) {
                        rvWardrobecategory.setVisibility(View.VISIBLE);
                        tvNodata.setVisibility(View.GONE);

                        rvWardrobecategory.setLayoutManager(new GridLayoutManager(activity, 2));
                        rvWardrobecategory.setAdapter(new WardrobeAdapter(activity, response.body().getMerchant()));
                    } else {
                        rvWardrobecategory.setVisibility(View.GONE);
                        tvNodata.setVisibility(View.VISIBLE);

                    }
                } else {
                    rvWardrobecategory.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<WardrobModel> call, Throwable t) {
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

        rvBottom.setAdapter(new BottomAdapter(activity, 2));
    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        lnrLogo = (LinearLayout) findViewById(R.id.lnr_logo);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        rvWardrobecategory = (RecyclerView) findViewById(R.id.rv_wardrobecategory);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
        edtSearch = (EditText) findViewById(R.id.edt_search);
        tvShowmore = (TextView) findViewById(R.id.tv_showmore);
        tvdes = (TextView) findViewById(R.id.tvdes);
    }
}
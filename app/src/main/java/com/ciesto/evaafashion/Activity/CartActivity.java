package com.ciesto.evaafashion.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Adapter.MerchantCartAdapter;
import com.ciesto.evaafashion.Model.CartModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.GPSTracker;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends BaseActivity {

    protected LinearLayout lnrMain;
    protected TextView tvNodata;
    protected TextView tvCartsubTotal;
    protected TextView tvDeliveryCharge;
    protected TextView tvIsCouponApply;
    protected LinearLayout lnrCoupon;
    protected TextView tvGrandTotal;
    protected LinearLayout llPlaceOrder;
    protected CardView cartApplycoupon;
    protected CardView cartUpdate;
    protected RecyclerView rvBottom;
    protected NavigationView navView;
    protected DrawerLayout drawerLayout;
    protected LinearLayout llAddShippingAddress;
    protected TextView tvAddress;
    protected Button btnPlaceOrder;
    protected ImageView ivedit;
    Activity activity = CartActivity.this;
    protected RecyclerView rvCartList;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    ArrayList<CartModel.Product> cartlist;
    String Cartdata = "";
    String FinalTotal = "";
    String CouponId = "0";
    String Total = "";
    String SubTotal = "";
    String Deliveycharge = "";
    String Couponamount = "";
    EditText edt_area_name, edt_pincode, edt_landmark, edt_house_name, edt_country, edt_state, edt_city;
    CardView cart_address;
    ProgressBar my_progressBar;
    TextView tv_chooseAddress, tv_save, tv_cancel;
    SwitchCompat switch_status, switch_primary;
    CardView card_user_my_location;
    String zipcode, houseno, areaname, landmark, country, city, state, active = "0", primary = "0", UserId, action, addressid = "0", MerchantId = "0";
    String latitude = "", longitude = "";
    SwitchCompat radio_status, radio_primary;
    ImageView img_close;
    MerchantCartAdapter adapter;
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_cart);
        super.onCreateDrawer();
        initView();
        initcomponent();
        SetListner();
    }

    private void initView() {
        rvCartList = (RecyclerView) findViewById(R.id.rvCartList);
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        lnrMain = (LinearLayout) findViewById(R.id.lnr_main);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);

        tvCartsubTotal = (TextView) findViewById(R.id.tvCartsubTotal);
        tvDeliveryCharge = (TextView) findViewById(R.id.tvDeliveryCharge);
        tvIsCouponApply = (TextView) findViewById(R.id.tvIsCouponApply);
        lnrCoupon = (LinearLayout) findViewById(R.id.lnr_coupon);
        tvGrandTotal = (TextView) findViewById(R.id.tvGrandTotal);
        cartApplycoupon = (CardView) findViewById(R.id.cart_applycoupon);
        cartUpdate = (CardView) findViewById(R.id.cart_update);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
        navView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        llAddShippingAddress = (LinearLayout) findViewById(R.id.llAddShippingAddress);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
        ivedit = (ImageView) findViewById(R.id.ivedit);

    }

    private void initcomponent() {
        progressDialog = new ProgressDialog(activity);
        loginPreferences = new LoginPreferences(activity);
        lnrCoupon.setVisibility(View.GONE);
        cartlist = new ArrayList<>();
        if (Constant.Cartlist.size() > 0)
            Constant.Cartlist.clear();

    }

    private void SetListner() {
        if (Constant.isNetworkAvailable(activity))
        { GetCartlist();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }

        cartApplycoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, CouponActivity.class)
                        .putExtra("MerchantId", MerchantId));
                Functions.animNext(activity);
            }
        });

        llAddShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressDialog();
            }
        });
        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddressDialog();
            }
        });


        cartUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray personarray = new JSONArray();
                Cartdata = "";
                for (int i = 0; i < Constant.Cartlist.size(); i++) {
                    try {
                        JSONObject person1 = null;
                        person1 = new JSONObject();
                        person1.put("Qty", Constant.Cartlist.get(i).getQty());
                        person1.put("Cart_Id", Constant.Cartlist.get(i).getCartId());
                        personarray.put(person1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Cartdata = personarray.toString();
                Log.e("cart_data=", Cartdata);

                if (Constant.isNetworkAvailable(activity)) {
                    Updatecart();
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("userID==", loginPreferences.getUserID());
                Log.e("Address==", tvAddress.getText().toString());
                Log.e("Total==", Total);
                Log.e("SubTotal==", SubTotal);
                Log.e("CouponId==", CouponId);
                Log.e("Couponamount==", Couponamount);
                Log.e("Deliveycharge==", Deliveycharge);
                if(!tvAddress.getText().toString().trim().equalsIgnoreCase("")) {
                    if (Constant.isNetworkAvailable(activity)) {
                        OrderPlace();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(activity, getResources().getString(R.string.select_address_validation), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //=======================AddressDialog====================

    public void showAddressDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_address);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        edt_house_name = dialog.findViewById(R.id.edt_house_name);
        edt_area_name = dialog.findViewById(R.id.edt_area_name);
        edt_pincode = dialog.findViewById(R.id.edt_pincode);
        edt_landmark = dialog.findViewById(R.id.edt_landmark);
        edt_country = dialog.findViewById(R.id.edt_country);
        edt_state = dialog.findViewById(R.id.edt_state);
        edt_city = dialog.findViewById(R.id.edt_city);


        card_user_my_location = dialog.findViewById(R.id.card_user_my_location);
        my_progressBar = dialog.findViewById(R.id.my_progressBar);
        tv_chooseAddress = dialog.findViewById(R.id.tv_chooseAddress);
        switch_status = dialog.findViewById(R.id.radio_status);
        switch_primary = dialog.findViewById(R.id.radio_primary);
        tv_save = dialog.findViewById(R.id.tv_save);
        tv_cancel = dialog.findViewById(R.id.tv_cancel);
        img_close = dialog.findViewById(R.id.img_close);

        radio_status = dialog.findViewById(R.id.radio_status);
        radio_primary = dialog.findViewById(R.id.radio_primary);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_chooseAddress.setVisibility(View.VISIBLE);
        tv_chooseAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(activity, DisplayAddressActivity.class));
                Functions.animNext(activity);
            }
        });


        if (!addressid.equalsIgnoreCase("0")) {
            edt_pincode.setText(zipcode);
            edt_house_name.setText(houseno);
            edt_area_name.setText(areaname);
            edt_landmark.setText(landmark);
            edt_country.setText(country);
            edt_city.setText(city);
            edt_state.setText(state);

            tv_save.setText(getResources().getString(R.string.update));

            if (active.equalsIgnoreCase("1")) {
                radio_status.setChecked(true);
            } else {
                radio_status.setChecked(false);
            }
            if (primary.equalsIgnoreCase("1")) {
                radio_primary.setChecked(true);
            } else {
                radio_primary.setChecked(false);
            }

        }


        radio_primary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    primary = "1";
                } else {
                    primary = "0";
                }
            }
        });
        radio_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    active = "1";
                } else {
                    active = "0";
                }
            }
        });


        card_user_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EasyPermissions.hasPermissions(activity, permissions)) {
                    EasyPermissions.requestPermissions(activity, "Please allow app", 1, permissions);
                } else {
                    GPSTracker gpsTracker = new GPSTracker(activity, my_progressBar, edt_area_name, edt_pincode, edt_country, edt_state, edt_city);
                    gpsTracker.GetLocation();
                }

            }
        });


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zipcode = edt_pincode.getText().toString();
                houseno = edt_house_name.getText().toString();
                areaname = edt_area_name.getText().toString();
                landmark = edt_landmark.getText().toString();
                country = edt_country.getText().toString();
                city = edt_city.getText().toString();
                state = edt_state.getText().toString();

                // Log.e("Status=", active);
                // Log.e("Primary=", primary);


                if (TextUtils.isEmpty(zipcode)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_zipcode), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(houseno)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_houseno), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(areaname)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_streetname), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(country)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_countryname), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(state)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_statename), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(city)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_cityname), Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder coder = new Geocoder(activity);
                    try {
                        ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(areaname, 50);
                        for (Address add : adresses) {

                            longitude = String.valueOf(add.getLongitude());
                            latitude = String.valueOf(add.getLatitude());

                            Log.e("latitude", latitude + " ==" + longitude);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (Constant.isNetworkAvailable(activity)) {
                        Addaddress(dialog);
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }


                }

            }
        });

        dialog.show();
    }

    //=======================GetShoppingList====================

    public void GetCartlist() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<CartModel> call = apiService.GetShoppingCart(Constant.ACCESSKEY, new ExtraPreferences(activity).getLanguage(), loginPreferences.getUserID());
        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {

                    lnrMain.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);

                    if (cartlist.size() > 0)
                        cartlist.clear();

                    for (int i = 0; i < response.body().getProducts().size(); i++) {
                        cartlist.add(response.body().getProducts().get(i));
                    }

                    rvCartList.setLayoutManager(new LinearLayoutManager(activity));
                    adapter = new MerchantCartAdapter(activity, cartlist);
                    rvCartList.setAdapter(adapter);

                    Total = response.body().getGrandTotal();
                    Deliveycharge = response.body().getDeliveryCharge();
                    SubTotal = response.body().getCartTotal();
                    MerchantId = response.body().getMerchantIds();

                    tvCartsubTotal.setText(getResources().getString(R.string.rupee)+ " " + response.body().getCartTotal());
                    tvDeliveryCharge.setText(getResources().getString(R.string.rupee)+ " " + response.body().getDeliveryCharge());
                    FinalTotal = response.body().getGrandTotal();
                    tvGrandTotal.setText(getResources().getString(R.string.rupee) + " "+ FinalTotal);

                } else {
                    lnrMain.setVisibility(View.GONE);
                    tvNodata.setVisibility(View.VISIBLE);
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

    //=======================UpdateCart====================

    private void Updatecart() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.UpdateCart(Constant.ACCESSKEY, Cartdata);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        lnrCoupon.setVisibility(View.GONE);
                        Constant.Couponcode = "";
                        CouponId = "0";
                        GetCartlist();
                        Toast.makeText(activity, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();

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

    //=======================CheckCoupon====================

    private void CheckCoupon() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        String MerchantTotal = "";

        for (int i = 0; i < cartlist.size(); i++) {

            if (Constant.MerchatnId.equals(cartlist.get(i).getMerchantId())) {
                MerchantTotal = cartlist.get(i).getTotal();
            }
        }

        Log.e("CouponCode==", Constant.Couponcode);
        Log.e("MerchantTotal==", MerchantTotal);
        Log.e("Grand_Total==", FinalTotal);

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.Checkcoupon(Constant.ACCESSKEY, Constant.Couponcode, loginPreferences.getUserID(),FinalTotal, MerchantTotal);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    Log.e("Check Coupon=", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        lnrCoupon.setVisibility(View.VISIBLE);
                        tvGrandTotal.setText(getResources().getString(R.string.rupee)+ " " + object.getString("Total_Amount"));
                        tvIsCouponApply.setText(getResources().getString(R.string.rupee)+ " " + object.getString("Coupon_Amount"));
                        CouponId = object.getString("Coupon_Id");
                        Total = object.getString("Total_Amount");
                        Couponamount = object.getString("Coupon_Amount");

                    } else {
                        //Toast.makeText(getActivity(), object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
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

    //=======================GetAddress====================

    private void GetProfile() {

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.GetProfile(Constant.ACCESSKEY, "1", loginPreferences.getUserID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    Log.e("Profile response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        // Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        JSONObject object1 = object.getJSONObject("Customer");
                        tvAddress.setText(object1.getString("Building_Name")+","+object1.getString("Shipping_Address"));

                        addressid = object1.getString("Address_Id");
                        zipcode = object1.getString("Pincode");
                        houseno = object1.getString("Building_Name");
                        areaname = object1.getString("Area_Name");
                        landmark = object1.getString("Landmark");
                        country = object1.getString("Country");
                        state = object1.getString("State");
                        city = object1.getString("City");
                        active = object1.getString("IsActive");
                        primary = object1.getString("IsPrimary");
                    } else {
                        //  Toast.makeText(activity, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Log error here since request failed
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    //=======================ChangeAddress====================

    private void Addaddress(final Dialog dialog) {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.AddNewAddress(Constant.ACCESSKEY, loginPreferences.getUserID(), "5", houseno, areaname, landmark, country, state, city, zipcode, active, primary, areaname, latitude, longitude, addressid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    // Log.e("Address Response==",response.body().toString());
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {

                        if (Constant.isNetworkAvailable(activity)) {

                            dialog.dismiss();
                            Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                            GetProfile();
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
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

    //============================PlaceOrder================

    private void OrderPlace() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.PlaceOrder(Constant.ACCESSKEY, loginPreferences.getUserID(), tvAddress.getText().toString(), Total, SubTotal, CouponId, Couponamount, Deliveycharge);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(activity, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();

                        new ExtraPreferences(activity).setCartcount(String.valueOf(0));
                        setBottom();

                        startActivity(new Intent(activity, PendingOrderActivity.class));
                        Functions.animNext(activity);


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
        GetProfile();
        if (!Constant.Couponcode.equals("")) {
            Log.e("Suceess", "");
            if (Constant.isNetworkAvailable(activity)) {
                CheckCoupon();
            } else {
                Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
            }
        }

    }

    public void setBottom() {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);
        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);
        rvBottom.setAdapter(new BottomAdapter(activity, 3));
    }

}

package com.ciesto.evaafashion.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.AddressAdapter;
import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Model.AddressModel;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.GPSTracker;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayAddressActivity extends BaseActivity {

    protected CardView cartAddress;
    Activity activity = DisplayAddressActivity.this;
    protected RecyclerView rvAddress;
    protected TextView tvNodata;
    EditText edt_area_name, edt_pincode, edt_landmark, edt_house_name,edt_country,edt_state,edt_city;
    CardView cart_address;
    ProgressBar my_progressBar;
    TextView tv_chooseAddress, tv_save, tv_cancel;
    SwitchCompat switch_status, switch_primary;
    CardView card_user_my_location;
    String zipcode,houseno,areaname,landmark,country,city,state,active="0",primary="0",UserId,action,addressid="0";
    String latitude="",longitude="";
    SwitchCompat radio_status,radio_primary;
    ImageView img_close; private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    String Action="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_display_address);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        rvAddress = (RecyclerView) findViewById(R.id.rv_address);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
        cartAddress = (CardView) findViewById(R.id.cart_address);
    }

    private void initComponent() {
        progressDialog=new ProgressDialog(activity);
        loginPreferences=new LoginPreferences(activity);
    }

    private void SetListener() {

        if (Constant.isNetworkAvailable(activity)) {
            GetAddress();
        } else {
            Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
        }

        cartAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressModel.Address bean = null;
                showAddressDialog("0",bean);


            }
        });

    }

    public void showAddressDialog(String AddressId, AddressModel.Address bean)
    {

        addressid=AddressId;

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

        radio_status=dialog.findViewById(R.id.radio_status);
        radio_primary=dialog.findViewById(R.id.radio_primary);

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

        tv_chooseAddress.setVisibility(View.GONE);


        if(!addressid.equalsIgnoreCase("0"))
        {
            edt_pincode.setText(bean.getPincode());
            edt_house_name.setText(bean.getBuildingName());
            edt_area_name.setText(bean.getAreaName());
            edt_landmark.setText(bean.getLandmark());
            edt_country.setText(bean.getCountry());
            edt_city.setText(bean.getCity());
            edt_state.setText(bean.getState());
            active=bean.getIsActive();
            primary=bean.getIsPrimary();
            if(active.equalsIgnoreCase("1"))
            {
                radio_status.setChecked(true);
            }
            else
            {
                radio_status.setChecked(false);
            }
            if(primary.equalsIgnoreCase("1"))
            {
                radio_primary.setChecked(true);
            }
            else
            {
                radio_primary.setChecked(false);
            }

        }



        radio_primary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    primary="1";
                }
                else
                {
                    primary="0";
                }
            }
        });
        radio_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    active="1";
                }
                else
                {
                    active="0";
                }
            }
        });


        card_user_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!EasyPermissions.hasPermissions(activity, permissions)) {
                    EasyPermissions.requestPermissions(activity, "Please allow app", 1, permissions);
                } else {

                    GPSTracker gpsTracker = new GPSTracker(activity, my_progressBar, edt_area_name, edt_pincode,edt_country,edt_state,edt_city);
                    gpsTracker.GetLocation();

                }

            }
        });


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                zipcode=edt_pincode.getText().toString();
                houseno=edt_house_name.getText().toString();
                areaname=edt_area_name.getText().toString();
                landmark=edt_landmark.getText().toString();
                country=edt_country.getText().toString();
                city=edt_city.getText().toString();
                state=edt_state.getText().toString();

                Log.e("Status=",active);
                Log.e("Primary=",primary);


                if(TextUtils.isEmpty(zipcode))
                {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_zipcode), Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(houseno))
                {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_houseno), Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(areaname))
                {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_streetname), Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(country))
                {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_countryname), Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(state))
                {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_statename), Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(city))
                {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_cityname), Toast.LENGTH_SHORT).show();
                }
                else
                {
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

    public void GetAddress() {

        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<AddressModel> call = apiService.ViewAddress(Constant.ACCESSKEY,"4",loginPreferences.getUserID());
        call.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                progressDialog.dismiss();
                //Log.e("Store==", response.body().toString());
                if (response.body().getStatus().equals("1")) {

                    tvNodata.setVisibility(View.GONE);
                    rvAddress.setVisibility(View.VISIBLE);

                    ArrayList<AddressModel.Address> addresslist=new ArrayList<>();

                    for (int i = 0; i < response.body().getAddress().size(); i++) {
                        addresslist.add(response.body().getAddress().get(i));
                    }
                    rvAddress.setLayoutManager(new LinearLayoutManager(activity));
                    rvAddress.setAdapter(new AddressAdapter(activity,addresslist));

                } else {
                    tvNodata.setVisibility(View.VISIBLE);
                    rvAddress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();
                Log.e("NETWORK ERROR --> ", t.toString());
            }
        });
    }

    private void Addaddress(final Dialog dialog)
    {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.AddNewAddress(Constant.ACCESSKEY,loginPreferences.getUserID(),"5",houseno,areaname,landmark,country,state,city,zipcode,active,primary,areaname,latitude,longitude,addressid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                   // Log.e("Address Response==",response.body().toString());
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1"))
                    {
                        dialog.dismiss();
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        if (Constant.isNetworkAvailable(activity)) {
                            GetAddress();
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
        RecyclerView rvBottom=findViewById(R.id.rv_bottom);

        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);

        rvBottom.setAdapter(new BottomAdapter(activity,100));
    }
}

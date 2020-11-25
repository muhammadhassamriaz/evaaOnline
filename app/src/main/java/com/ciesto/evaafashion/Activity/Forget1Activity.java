package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.SendOtp;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forget1Activity extends AppCompatActivity {

    protected EditText etCountrycode;
    Activity activity = Forget1Activity.this;
    protected ImageView ivBack;
    protected EditText etmobileno;
    protected Button btnsendotp;
    String s_mobile;
    ProgressDialog progressDialog;
    String UserId,s_countrycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_forget1);

        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        etmobileno = (EditText) findViewById(R.id.etmobileno);
        btnsendotp = (Button) findViewById(R.id.btnsendotp);
        etCountrycode = (EditText) findViewById(R.id.et_countrycode);
    }

    private void initComponent() {
        progressDialog = new ProgressDialog(activity);
    }

    private void SetListener() {
        btnsendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_mobile = etmobileno.getText().toString();
                s_countrycode=etCountrycode.getText().toString();
                if (TextUtils.isEmpty(s_mobile)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_mobile_number), Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(s_countrycode)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_country_code), Toast.LENGTH_SHORT).show();
                }  else {
                    if (Constant.isNetworkAvailable(activity)) {
                        MobileVerify();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void MobileVerify() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.Forget1(Constant.ACCESSKEY, "+"+s_countrycode+s_mobile, "Customer");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    // Log.e("Login response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {

                        UserId = object.getString("Customer_Id");

                        String otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
                        SendOtp sendOtp=new SendOtp(activity);
                        sendOtp.SendOtp("+"+s_countrycode+s_mobile,otp,s_countrycode,"forget",UserId);
                        progressDialog.dismiss();
                        /*startActivity(new Intent(activity, OTPActivity.class)
                                .putExtra("mobile", s_mobile)
                                .putExtra("c_code", s_countrycode)
                                .putExtra("Action", "forget")
                                .putExtra("UserID", UserId));*/

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

}

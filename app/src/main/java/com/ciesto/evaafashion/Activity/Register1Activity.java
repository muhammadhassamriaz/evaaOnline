package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
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

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class Register1Activity extends AppCompatActivity {

    protected EditText etCountrycode;
    Activity activity = Register1Activity.this;
    protected ImageView ivBack;
    protected EditText etmobileno;
    protected Button btnsendotp;
    ProgressDialog progressDialog;
    String s_mobile,s_countrycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register1);

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

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnsendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_mobile = etmobileno.getText().toString();
                s_countrycode=etCountrycode.getText().toString();


                  if (TextUtils.isEmpty(s_mobile)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_mobileno), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(s_countrycode)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_country_code), Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.isNetworkAvailable(activity)) {
                        SignUpMobile();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                }

             // new SendOTP().execute();


            }
        });
    }

    private void SignUpMobile() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.SignupMobile(Constant.ACCESSKEY, "1", s_mobile);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    Log.e("Login response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        //Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();

                       /* startActivity(new Intent(activity, OTPActivity.class)
                                .putExtra("mobile", s_mobile)
                                .putExtra("c_code", s_countrycode)
                                .putExtra("Action", "signup")
                                .putExtra("UserId", "0"));*/
                        String otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
                       // Log.e("OTP==",otp);

                        SendOtp sendOtp=new SendOtp(activity);
                        sendOtp.SendOtp("+"+s_countrycode+s_mobile,otp,s_countrycode,"signup","0");


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

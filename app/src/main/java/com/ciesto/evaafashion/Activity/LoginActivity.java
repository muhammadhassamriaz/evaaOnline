package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    protected EditText etEmailMobile;
    protected EditText etPassword;
    protected TextView btnLogin;
    protected TextView btnSignUp;
    protected TextView btnForgotPassword;
    protected LinearLayout lnrSkip;
    protected ImageView ivRightarrow;
    protected ImageView imgViewpass;
    protected LinearLayout lnrpass;
    Activity activity = LoginActivity.this;
    String name, pass;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);

        initView();
        initComponent();
        SetListner();
    }

    private void initView() {
        etEmailMobile = (EditText) findViewById(R.id.etEmailMobile);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnSignUp = (TextView) findViewById(R.id.btnSignUp);
        btnForgotPassword = (TextView) findViewById(R.id.btnForgotPassword);
        lnrSkip = (LinearLayout) findViewById(R.id.lnr_skip);
        ivRightarrow = (ImageView) findViewById(R.id.iv_rightarrow);
        imgViewpass = (ImageView) findViewById(R.id.img_viewpass);
        lnrpass = (LinearLayout) findViewById(R.id.lnrpass);
    }

    private void initComponent() {
        progressDialog = new ProgressDialog(activity);
        loginPreferences = new LoginPreferences(activity);
        if(new ExtraPreferences(activity).getLanguage().equalsIgnoreCase("Arabic"))
            ivRightarrow.setRotation(180);
        else
            ivRightarrow.setRotation(0);


    }

    private void SetListner() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, Register1Activity.class));
            }
        });
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, Forget1Activity.class));
            }
        });
        lnrpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    // show password
                    imgViewpass.setImageResource(R.drawable.ic_eye);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag = 1;
                } else {
                    // hide password
                    imgViewpass.setImageResource(R.drawable.ic_eye1);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag = 0;
                }
            }
        });
        lnrSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, MainActivity.class));
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "+974"+etEmailMobile.getText().toString();
                pass = etPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(activity, "" + getResources().getString(R.string.reg_enter_mobile_number), Toast.LENGTH_SHORT).show();
                } else if (etEmailMobile.getText().length() != 8) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_valid_mobileno), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(activity, "" + getResources().getString(R.string.reg_enter_password), Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.isNetworkAvailable(activity)) {
                        Login();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
    }


    private void Login() {

        String device_key = new ExtraPreferences(activity).getDevicekey();
        Log.e("Device Key=", device_key);

        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.Login(Constant.ACCESSKEY, name, pass, device_key);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    Log.e("Login response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        loginPreferences.setIsLogin(true);
                        loginPreferences.SetSession(object.getString("Customer_Id"),
                                object.getString("Customer_Name"),
                                object.getString("Email_Id"),
                                object.getString("Mobile_No"),
                                object.getString("Image"));
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();

                        finish();
                        startActivity(new Intent(activity, MainActivity.class));
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

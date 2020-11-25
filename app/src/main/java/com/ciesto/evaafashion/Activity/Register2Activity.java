package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2Activity extends AppCompatActivity {

    Activity activity = Register2Activity.this;
    protected ImageView ivBack;
    protected TextInputEditText etFullName;
    protected TextInputLayout textInputLayout4;
    protected TextInputEditText etEmail;
    protected TextInputLayout textInputLayout;
    protected TextInputEditText etMobile;
    protected TextInputLayout textInputLayout3;
    protected TextInputEditText etPassword;
    protected TextInputLayout textInputLayout2;
    protected TextView tvMale;
    protected TextView tvFemale;
    protected CheckBox cbReferralCode;
    protected Button btnCreateAccount;
    ProgressDialog progressDialog;
    String name, email, pass, gender = "", Mobile;
    private ColorStateList oldColors;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    LoginPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register2);
        initView();
        initComponent();
        SetListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        etFullName = (TextInputEditText) findViewById(R.id.etFullName);
        textInputLayout4 = (TextInputLayout) findViewById(R.id.textInputLayout4);
        etEmail = (TextInputEditText) findViewById(R.id.etEmail);
        textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        etMobile = (TextInputEditText) findViewById(R.id.etMobile);
        textInputLayout3 = (TextInputLayout) findViewById(R.id.textInputLayout3);
        etPassword = (TextInputEditText) findViewById(R.id.etPassword);
        textInputLayout2 = (TextInputLayout) findViewById(R.id.textInputLayout2);
        tvMale = (TextView) findViewById(R.id.tvMale);
        tvFemale = (TextView) findViewById(R.id.tvFemale);
        cbReferralCode = (CheckBox) findViewById(R.id.cbReferralCode);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
    }

    private void initComponent() {
        progressDialog = new ProgressDialog(activity);
        loginPreferences=new LoginPreferences(activity);
        oldColors = tvMale.getTextColors();
        Mobile = getIntent().getStringExtra("Mobile");
    }

    private void SetListener() {
        tvMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFemale.setTextColor(oldColors);
                tvMale.setTextColor(getResources().getColor(R.color.colorPink));
                gender = "male";
            }
        });
        tvFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMale.setTextColor(oldColors);
                tvFemale.setTextColor(getResources().getColor(R.color.colorPink));
                gender = "female";
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                pass = etPassword.getText().toString();
                name = etFullName.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_full_name), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_email), Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_valid_email), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_password), Toast.LENGTH_SHORT).show();
                }
                else if (gender.equals("")) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_select_gender), Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Constant.isNetworkAvailable(activity)) {
                        SignUp();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void SignUp()
    {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        String device_key = new ExtraPreferences(activity).getDevicekey();
        Log.e("Device Key=", device_key);


        final ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.Signup(Constant.ACCESSKEY, "2", name, Mobile, email, pass, gender,device_key);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    Log.e("Register response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1"))
                    {

                        loginPreferences.setIsLogin(true);
                        loginPreferences.SetSession(object.getString("Customer_Id"),
                                object.getString("Customer_Name"),
                                object.getString("Email_Id"),
                                object.getString("Mobile_No"),
                                object.getString("Image"));
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(activity,MainActivity.class));
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
}

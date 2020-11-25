package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forget2Activity extends AppCompatActivity {

    Activity activity=Forget2Activity.this;
    protected ImageView ivBack;
    protected EditText edtPassword;
    protected TextView tvSave;
    ProgressDialog progressDialog;
    String s_pass,UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_forget2);
        initView();
        initcomponent();
        SetListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        edtPassword = (EditText) findViewById(R.id.edt_Password);
        tvSave = (TextView) findViewById(R.id.tv_save);
    }
    private void initcomponent()
    {
        progressDialog=new ProgressDialog(activity);
        UserId=getIntent().getStringExtra("UserID");
        Log.e("User Id==",UserId);
    }
    private void SetListener()
    {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_pass=edtPassword.getText().toString();
                if (TextUtils.isEmpty(s_pass)) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_password), Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.isNetworkAvailable(activity)) {
                        UpdateForget();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void UpdateForget() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();


        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.Forget2(Constant.ACCESSKEY, UserId, "Customer", s_pass);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    Log.e("Forget response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(activity,LoginActivity.class));

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

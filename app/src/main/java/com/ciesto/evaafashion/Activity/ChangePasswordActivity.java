package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity {


    Activity activity = ChangePasswordActivity.this;
    protected EditText etOldpass;
    protected EditText etNewpass;
    protected EditText etConfirmpass;
    protected CardView cardSave;
    protected RecyclerView rvBottom;
    ProgressDialog progressDialog;
    LoginPreferences loginPreferences;
    String oldpass, newpass, confirmpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_change_password);
        super.onCreateDrawer();
        initView();
        initComponent();
        SetListener();
    }


    private void initView() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        etOldpass = (EditText) findViewById(R.id.et_oldpass);
        etNewpass = (EditText) findViewById(R.id.et_newpass);
        etConfirmpass = (EditText) findViewById(R.id.et_confirmpass);
        cardSave = (CardView) findViewById(R.id.card_save);
        rvBottom = (RecyclerView) findViewById(R.id.rv_bottom);
    }

    private void initComponent() {
        progressDialog = new ProgressDialog(activity);
        loginPreferences = new LoginPreferences(activity);
    }

    private void SetListener() {
        cardSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpass = etOldpass.getText().toString();
                newpass = etNewpass.getText().toString();
                confirmpass = etConfirmpass.getText().toString();

                if (TextUtils.isEmpty(oldpass)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_old_pass), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(newpass)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_new_pass), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(confirmpass)) {
                    Toast.makeText(activity, getResources().getString(R.string.please_enter_confirm_pass), Toast.LENGTH_SHORT).show();
                } else if (!newpass.equals(confirmpass)) {
                    Toast.makeText(activity, getResources().getString(R.string.password_not_match), Toast.LENGTH_SHORT).show();
                } else {
                    if (Constant.isNetworkAvailable(activity)) {
                        PasswordChange();
                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
    }


    private void PasswordChange() {
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient(activity).create(ApiInterface.class);
        Call<JsonObject> call = apiService.ChangePassword(Constant.ACCESSKEY, loginPreferences.getUserID(), newpass, oldpass, "3");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    // Log.e("Register response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        onBackPressed();
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
    }

    private void setBottom() {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);

        rvBottom.setHasFixedSize(true);
        rvBottom.setLayoutManager(new GridLayoutManager(activity, 5));
        rvBottom.setNestedScrollingEnabled(false);

        rvBottom.setAdapter(new BottomAdapter(activity, 4));
    }

}

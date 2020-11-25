package com.ciesto.evaafashion.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.Reset.ApiClient;
import com.ciesto.evaafashion.Reset.ApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaseActivity extends AppCompatActivity {

    protected ImageView tvMenu;
    protected ImageView ivNotification;
    protected ImageView ivCart;
    protected ImageView iWishList;
    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavigationView navigationView;
    BottomNavigationView navigation;
    LinearLayout llShippingAddress, llHome, llMyAccount, llSetting, llWishlist, llMyOrder, llMyOrderhistory, lllanguagechange, llLogin,llfeatureproduct;
    LoginPreferences loginPreferences;
    TextView tvUserName, tvUserEmail, tvLogout, tvpadingcount, tvorderhistorycount;
    ImageView ivProfile;
    LinearLayout lnrLogo;
    ImageView iv_home,iv_login,iv_account,iv_order,iv_history,iv_address,iv_wislist,iv_changepass,iv_setting,iv_featureproduct;

    protected void onCreateDrawer() {
        tvMenu = (ImageView) findViewById(R.id.tv_menu);
        ivNotification = (ImageView) findViewById(R.id.ivNotification);
        ivCart = (ImageView) findViewById(R.id.ivCart);
        drawer = findViewById(R.id.drawer_layout);
        iWishList = (ImageView) findViewById(R.id.wishListBtn);
        navigationView = findViewById(R.id.nav_view);

        llShippingAddress = navigationView.getHeaderView(0).findViewById(R.id.llShippingAddress);
        llHome = navigationView.getHeaderView(0).findViewById(R.id.llHome);
        llMyAccount = navigationView.getHeaderView(0).findViewById(R.id.llMyAccount);
        llSetting = navigationView.getHeaderView(0).findViewById(R.id.llSetting);
        llWishlist = navigationView.getHeaderView(0).findViewById(R.id.llWishlist);
        tvLogout = navigationView.getHeaderView(0).findViewById(R.id.tvLogout);
        llMyOrder = navigationView.getHeaderView(0).findViewById(R.id.llMyOrder);
        llMyOrderhistory = navigationView.getHeaderView(0).findViewById(R.id.llMyOrderhistory);
        lllanguagechange = navigationView.getHeaderView(0).findViewById(R.id.lllanguagechange);
        llLogin = navigationView.getHeaderView(0).findViewById(R.id.llLogin);
        llfeatureproduct = navigationView.getHeaderView(0).findViewById(R.id.llfeatureproduct);

        tvUserName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        tvUserEmail = navigationView.getHeaderView(0).findViewById(R.id.tvUserEmail);
        ivProfile = navigationView.getHeaderView(0).findViewById(R.id.ivProfile);
        tvpadingcount = navigationView.getHeaderView(0).findViewById(R.id.tvpadingcount);
        tvorderhistorycount = navigationView.getHeaderView(0).findViewById(R.id.tvorderhistorycount);
        lnrLogo = findViewById(R.id.lnr_logo);

        iv_home = navigationView.getHeaderView(0).findViewById(R.id.iv_home);
        iv_login = navigationView.getHeaderView(0).findViewById(R.id.iv_login);
        iv_account = navigationView.getHeaderView(0).findViewById(R.id.iv_account);
        iv_order = navigationView.getHeaderView(0).findViewById(R.id.iv_order);
        iv_history = navigationView.getHeaderView(0).findViewById(R.id.iv_history);
        iv_address = navigationView.getHeaderView(0).findViewById(R.id.iv_address);
        iv_wislist = navigationView.getHeaderView(0).findViewById(R.id.iv_wislist);
        iv_changepass = navigationView.getHeaderView(0).findViewById(R.id.iv_changepass);
        iv_setting = navigationView.getHeaderView(0).findViewById(R.id.iv_setting);
        iv_featureproduct = navigationView.getHeaderView(0).findViewById(R.id.iv_featureproduct);

        loginPreferences = new LoginPreferences(BaseActivity.this);

        if(new ExtraPreferences(BaseActivity.this).getLanguage().equalsIgnoreCase("English"))
        {
            iv_home.setRotation(0);
            iv_login.setRotation(0);
            iv_account.setRotation(0);
            iv_order.setRotation(0);
            iv_history.setRotation(0);
            iv_address.setRotation(0);
            iv_wislist.setRotation(0);
            iv_changepass.setRotation(0);
            iv_setting.setRotation(0);
            iv_featureproduct.setRotation(0);
        }
        else
        {
            iv_home.setRotation(180);
            iv_login.setRotation(180);
            iv_account.setRotation(180);
            iv_order.setRotation(180);
            iv_history.setRotation(180);
            iv_address.setRotation(180);
            iv_wislist.setRotation(180);
            iv_changepass.setRotation(180);
            iv_setting.setRotation(180);
            iv_featureproduct.setRotation(180);
        }
        if (loginPreferences.isLoggIn())
        {
            tvLogout.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.VISIBLE);
            tvUserEmail.setVisibility(View.VISIBLE);
            ivProfile.setVisibility(View.VISIBLE);

            llShippingAddress.setVisibility(View.VISIBLE);
            llMyAccount.setVisibility(View.VISIBLE);
            llWishlist.setVisibility(View.VISIBLE);
            tvLogout.setVisibility(View.VISIBLE);
            llMyOrder.setVisibility(View.VISIBLE);
            llMyOrderhistory.setVisibility(View.VISIBLE);
            llLogin.setVisibility(View.GONE);
            llfeatureproduct.setVisibility(View.VISIBLE);

            try {
                GetProfile();
                GetCount();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            tvLogout.setVisibility(View.GONE);
            tvUserName.setVisibility(View.GONE);
            tvUserEmail.setVisibility(View.GONE);
            ivProfile.setVisibility(View.GONE);

            llShippingAddress.setVisibility(View.GONE);
            llMyAccount.setVisibility(View.GONE);
            llWishlist.setVisibility(View.GONE);
            tvLogout.setVisibility(View.GONE);
            llMyOrder.setVisibility(View.GONE);
            llMyOrderhistory.setVisibility(View.GONE);
            llfeatureproduct.setVisibility(View.GONE);
            llLogin.setVisibility(View.VISIBLE);


        }
        SetListner();

    }


    private void SetListner() {

        lnrLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                Functions.animNext(BaseActivity.this);
                finishAffinity();

            }
        });

        iWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, WishListActivity.class));
                Functions.animNext(BaseActivity.this);
            }
        });

        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                } else {
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });


        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                finish();
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                Functions.animNext(BaseActivity.this);

            }
        });

        llMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if (loginPreferences.isLoggIn()) {
                    startActivity(new Intent(BaseActivity.this, ProfileActivity.class));
                    Functions.animNext(BaseActivity.this);
                } else {
                    startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                }

            }
        });

        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(BaseActivity.this, SettingsActivity.class));
                Functions.animNext(BaseActivity.this);
            }
        });

        llShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);

                startActivity(new Intent(BaseActivity.this, DisplayAddressActivity.class));
                Functions.animNext(BaseActivity.this);


            }
        });
        llWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);

                startActivity(new Intent(BaseActivity.this, WishListActivity.class));
                Functions.animNext(BaseActivity.this);

            }
        });

        llfeatureproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(BaseActivity.this, ProductActivity.class)
                        .putExtra("Action", "1"));
                Functions.animNext(BaseActivity.this);
            }
        });

        llMyOrderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(BaseActivity.this, OrderHistroyActivity.class));
                Functions.animNext(BaseActivity.this);
            }
        });

        llMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(BaseActivity.this, PendingOrderActivity.class));
                Functions.animNext(BaseActivity.this);

            }
        });
        lllanguagechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                SelectLanguageDialog();
            }
        });
        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(BaseActivity.this, LoginActivity.class));
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                showLogoutDialog();
            }
        });

    }

    public void showLogoutDialog() {

        final Dialog builder = new Dialog(BaseActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(BaseActivity.this).inflate(R.layout.logout_dialog, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        tvDialogMessage.setText(getResources().getString(R.string.logout_msg));

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                if (Constant.isNetworkAvailable(BaseActivity.this)) {
                    Logout();
                } else {
                    Toast.makeText(BaseActivity.this, getResources().getString(R.string.internet_connection), Toast.LENGTH_LONG).show();
                }
            }
        });


        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.setCancelable(false);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        builder.setContentView(view1);
        builder.show();

    }

    private void GetProfile() {
        ApiInterface apiService = ApiClient.getClient(BaseActivity.this).create(ApiInterface.class);
        Call<JsonObject> call = apiService.GetProfile(Constant.ACCESSKEY, "1", loginPreferences.getUserID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    // Log.e("Profile response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        // Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        JSONObject object1 = object.getJSONObject("Customer");
                        tvUserName.setText(object1.getString("First_Name"));
                        tvUserEmail.setText(object1.getString("Email_Id"));
                        Picasso.get()
                                .load(Constant.Profile_Images + object1.getString("Profile_Image"))
                                .error(R.drawable.ic_user)
                                .into(ivProfile);

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

    private void Logout() {
        final ProgressDialog progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setTitle(getResources().getString(R.string.please_wait));
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
        Call<JsonObject> call = apiService.Logout(Constant.ACCESSKEY, loginPreferences.getUserID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    Log.e("Login response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {

                        Toast.makeText(BaseActivity.this, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
                        finishAffinity();
                        loginPreferences.logoutUser();
                        loginPreferences.setIsLogin(false);


                    } else {
                        Toast.makeText(BaseActivity.this, object.getString("Message").toString(), Toast.LENGTH_SHORT).show();
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

    private void GetCount() {
        ApiInterface apiService = ApiClient.getClient(BaseActivity.this).create(ApiInterface.class);
        Call<JsonObject> call = apiService.GetCount(Constant.ACCESSKEY, "Customer", loginPreferences.getUserID());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    //  Log.e("Count response==", response.body().toString());
                    if (object.getString("Status").equalsIgnoreCase("1")) {
                        // Toast.makeText(activity, object.getString("Message"), Toast.LENGTH_SHORT).show();
                        JSONObject object1 = object.getJSONObject("Setting");
                        new ExtraPreferences(BaseActivity.this).setCartcount(object1.getString("Cart"));
                        new ExtraPreferences(BaseActivity.this).setWhishlistcount(object1.getString("Wishlist"));
                        tvpadingcount.setText(object1.getString("Pending_Order"));
                        tvorderhistorycount.setText(object1.getString("Order_History"));

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

    // ==============LanguageDialog================
    public void SelectLanguageDialog() {
        final Dialog builder = new Dialog(BaseActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(BaseActivity.this).inflate(R.layout.languagedialog, null);
        LinearLayout lnrEnglish = (LinearLayout) view1.findViewById(R.id.lnr_english);
        LinearLayout lnrArabic = (LinearLayout) view1.findViewById(R.id.lnr_arabic);

        lnrEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                new ExtraPreferences(BaseActivity.this).setLanguage("English");
                setLocale("en");

            }
        });
        lnrArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                new ExtraPreferences(BaseActivity.this).setLanguage("Arabic");
                setLocale("ar");

            }
        });
        builder.setContentView(view1);
        builder.show();
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(lang)); // API 17+ only.
        } else {
            conf.locale = new Locale(lang);
        }

        res.updateConfiguration(conf, dm);


        Intent intent = new Intent(BaseActivity.this,MainActivity.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


    }

}

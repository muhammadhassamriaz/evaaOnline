package com.ciesto.evaafashion.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciesto.evaafashion.Adapter.BottomAdapter;
import com.ciesto.evaafashion.Fragment.HomeFragment;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;

public class MainActivity extends BaseActivity {

    protected TextView tvMenu;
    protected ImageView ivNotification;
    protected ImageView ivCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        if (getIntent().hasExtra("Type"))
        {
            String Type = getIntent().getStringExtra("Type");
            if (new LoginPreferences(MainActivity.this).isLoggIn())
            {
                if (Type.equalsIgnoreCase("order_outfordelivery")|| Type.equalsIgnoreCase("order_place_by_customer")|| Type.equalsIgnoreCase("order_confirmed"))
                {
                    startActivity(new Intent(MainActivity.this, PendingOrderActivity.class));
                    Functions.animNext(MainActivity.this);
                } else if (Type.equalsIgnoreCase("order_delivered") || Type.equalsIgnoreCase("merchant_order_cancel")
                        ) {
                    startActivity(new Intent(MainActivity.this, OrderHistroyActivity.class));
                    Functions.animNext(MainActivity.this);
                }
            } else {
                ReplaceFragment(new HomeFragment());
            }
        } else {
            ReplaceFragment(new HomeFragment());
        }

    }

    private void ReplaceFragment(Fragment fragment1) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment1, "Main");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void showExitDialog() {

        final Dialog builder = new Dialog(MainActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.logout_dialog, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        tvDialogMessage.setText(getResources().getString(R.string.App_exit).toUpperCase());

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                //System.exit(0);
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                //System.exit(0);

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

    @Override
    public void onBackPressed()
    {
        Fragment f = MainActivity.this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if(f instanceof HomeFragment) {
            showExitDialog();
        }
        else
        {
            ReplaceFragment(new HomeFragment());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBottom();
    }

    private void setBottom() {
        RecyclerView rvBottom = findViewById(R.id.rv_bottom);
        rvBottom.setHasFixedSize(true);
//        rvBottom.setLayoutManager(new GridLayoutManager(MainActivity.this, 5));
        rvBottom.setLayoutManager(new GridLayoutManager(MainActivity.this, 5));
        rvBottom.setNestedScrollingEnabled(false);
        rvBottom.setAdapter(new BottomAdapter(MainActivity.this, 0));
    }

}

package com.ciesto.evaafashion.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.ciesto.evaafashion.R;
import com.msg91.sendotpandroid.library.SendOtpVerification;
import com.msg91.sendotpandroid.library.Verification;
import com.msg91.sendotpandroid.library.VerificationListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OTPActivity extends AppCompatActivity implements VerificationListener {
    protected TextView txtRetry;
    Activity activity = OTPActivity.this;
    protected ImageView ivBack;
    protected PinView firstPinView;
    protected Button btnsendotp;
    ProgressDialog progressDialog;
    String Mobile, Type, UserId,Countrycode,otp;
    private Verification mVerification;
    private OkHttpClient mClient = new OkHttpClient();
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_otp);

        initView();
        SetListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        firstPinView = (PinView) findViewById(R.id.firstPinView);
        btnsendotp = (Button) findViewById(R.id.btnsendotp);
        txtRetry = (TextView) findViewById(R.id.txt_retry);

        Mobile=getIntent().getStringExtra("mobile");
        Type=getIntent().getStringExtra("Action");
        UserId=getIntent().getStringExtra("UserId");
        Countrycode=getIntent().getStringExtra("c_code");
        otp=getIntent().getStringExtra("OTP");
    }

    private void SetListener() {

        startTimer();
        //createVerification(false);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ResendCode();
                showProgress();
                 otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
                Log.e("OTP==",otp);
                SendOtp(Mobile,otp);
                startTimer();
            }
        });

        btnsendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstPinView.getText().toString().isEmpty()) {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_valid_otp), Toast.LENGTH_SHORT).show();
                }
                else if( (!otp.equalsIgnoreCase(firstPinView.getText().toString())))
                {
                    Toast.makeText(activity, getResources().getString(R.string.reg_enter_valid_otp), Toast.LENGTH_SHORT).show();
                }
                else {
                    //showProgress();
                   // mVerification.verify(firstPinView.getText().toString());
                    if(Type.equalsIgnoreCase("signup")) {
                        startActivity(new Intent(activity, Register2Activity.class).putExtra("Mobile", Mobile));
                    }
                    else
                    {
                        startActivity(new Intent(activity, Forget2Activity.class).putExtra("UserID", UserId));
                    }
                }
            }
        });
    }

    void createVerification(boolean autoverification) {
        Log.e("Mobile==",Mobile);
        mVerification = SendOtpVerification.createSmsVerification
                (SendOtpVerification
                        .config("+"+Countrycode + Mobile)
                        .context(activity)
                        .autoVerification(autoverification)
                        .httpsConnection(false)
                        .senderId("NEOMIF")
                        .otplength("4")
                        .build(), this);
        mVerification.initiate();
    }

    private void ResendCode() {
        startTimer();
        mVerification.resend("voice");

    }

    private void startTimer() {
        txtRetry.setClickable(false);
        txtRetry.setTextColor(getResources().getColor(R.color.sky));
        new CountDownTimer(30000, 1000)
        {
            int secondsLeft = 0;

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    txtRetry.setText(" ( " + secondsLeft + " )");
                }
            }

            public void onFinish() {
                otp="";
                txtRetry.setClickable(true);
                txtRetry.setText("Resend OTP");
                txtRetry.setTextColor(getResources().getColor(R.color.colorGreen));
            }
        }.start();
    }

    private void SendOtp(final String mobileno, final String otp)
    {

        Log.e("Final mobileno==",mobileno);
        Log.e("Final otp==",otp);

        final String finaldata="";
        String base64EncodedCredentials = "Basic " + Base64.encodeToString(("AC801e3f4f7c9cacd6fd4af250a3425992:ff83135d253dc7bc1d918dbe6a789c91").getBytes(), Base64.NO_WRAP);
        String url="https://api.twilio.com/2010-04-01/Accounts/AC801e3f4f7c9cacd6fd4af250a3425992/SMS/Messages.json";

      /*  String base64EncodedCredentials = "Basic " + Base64.encodeToString(("AC72c156907eebffc6ce83d65974985972:fd00a807d70497833b5a6491910dabb6").getBytes(), Base64.NO_WRAP);
        String url="https://api.twilio.com/2010-04-01/Accounts/AC72c156907eebffc6ce83d65974985972/SMS/Messages.json";*/
        try {
            post(url, base64EncodedCredentials,mobileno,otp,
                    new Callback()
                    {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            Log.e("onFailure=", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, final Response response) {
                            Log.e("Sucess==", response.toString());
                            Log.e("Sucess==", String.valueOf(response.isSuccessful()));
                            hideProgress();
                            //startTimer();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error=", e.toString());
        }

    }


    Call post(String url, String base64EncodedCredentials,String mobileno,String otp, Callback callback) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("To", mobileno)
                .add("From", "+16183644595")
                /*  .add("To", "+917016332603")
                  .add("From", "+13205262607")*/
                .add("Body", otp)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("Authorization", base64EncodedCredentials)
                .build();

        Call response = mClient.newCall(request);

        // Call response = mClient.newCall(request);
        response.enqueue(callback);
        return response;
    }

    public void showProgress() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading..");
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onInitiated(String response) {
        Log.e("OTP", "Initialized!" + response);
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        Log.e("OTP", "Verification initialization failed: " + paramException.getMessage());
    }

    @Override
    public void onVerified(String response) {
        //Log.e("OTP", "Verified!\n" + response);
        hideProgress();
        Log.e("Verified==", Type);

        if(Type.equalsIgnoreCase("signup")) {
            startActivity(new Intent(activity, Register2Activity.class).putExtra("Mobile", Mobile));
        }
        else
        {
            startActivity(new Intent(activity, Forget2Activity.class).putExtra("UserID", UserId));
        }
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        Log.e("OTP", " verification failed: " + paramException.getMessage());
        hideProgress();
        Toast.makeText(activity, getResources().getString(R.string.reg_enter_valid_otp), Toast.LENGTH_SHORT).show();
    }
}

package com.ciesto.evaafashion.Other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.ciesto.evaafashion.Activity.OTPActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendOtp {
    private OkHttpClient mClient = new OkHttpClient();

    Context context;

    public SendOtp(Activity activity) {
        this.context = activity;
    }

    public String SendOtp(final String mobileno, final String otp, final String countrycode, final String action, final String userid)
    {

        Log.e("Final mobileno==",mobileno);
        Log.e("Final otp==",otp);
        Log.e("Final UserId==",userid);

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

                           // String finaldata= String.valueOf(response.isSuccessful());
                            context.startActivity(new Intent(context, OTPActivity.class)
                                    .putExtra("mobile", mobileno)
                                    .putExtra("c_code", countrycode)
                                    .putExtra("OTP", otp)
                                    .putExtra("Action", action)
                                    .putExtra("UserId", userid));
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error=", e.toString());
        }
        return finaldata;
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
}

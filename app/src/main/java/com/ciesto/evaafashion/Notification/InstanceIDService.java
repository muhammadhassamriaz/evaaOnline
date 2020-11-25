package com.ciesto.evaafashion.Notification;

import android.util.Log;

import com.ciesto.evaafashion.Other.Constant;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class InstanceIDService extends FirebaseInstanceIdService
{
 
    private static final String TAG = "MyFireBaseIIDService";
 
    @Override
    public void onTokenRefresh()
    {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Constant.FIREBASE_TOKEN = refreshedToken;

        Log.e(TAG, refreshedToken);
    }
}
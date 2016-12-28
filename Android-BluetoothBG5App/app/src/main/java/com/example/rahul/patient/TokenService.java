package com.example.rahul.patient;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/*---------------------------------------------------------------------
        |  Class TokenService
        |
        |  Purpose:  This java class generates unique token for FireBase DB.
        |               It helps FireBase to identify where to send the
        |               notifications and messages.
        |
        *-------------------------------------------------------------------*/

/**
 * Created by rahul on 12-Nov-16.
 */

public class TokenService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.v("Token ----->" , ":" + refreshToken);
        sendRegistrationToServer(refreshToken);
    }

    private void sendRegistrationToServer(String token)
    {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("Token", token).build();

        Request request = new Request.Builder().url("http://singhrah.dev.fast.sheridanc.on.ca/fcm/fcm.php")
                .post(body)
                .build(); // url of php script running on Database

        Log.v("Token Service ->request", ":" + request);
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package com.example.ranjana.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ranjana on 16-11-2017.
 */

public class FCM_SEND
{
    //private static FCM_SEND f

    public static void sendRegistrationToServer(Context context) {
        try
        {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            SharedPreferences sh = context.getSharedPreferences("auth", MODE_PRIVATE);
            OkHttpClient client = new OkHttpClient();
            String token = sh.getString("token",null);
            if(token == null)
                return;
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{   \r\n  \"platform\": 1 ,\r\n  \"deviceId\": \""+refreshedToken+"\"\r\n                                                                        }");
            Request request = new Request.Builder()
                    .url("https://smartpill.herokuapp.com/api/device")
                    .post(body)
                    .addHeader("authorization", "Bearer "+token)
                    .build();

            Log.v("FCM", "{   \r\n  platform: 1\r\n  deviceId: \""+refreshedToken+"\" \r\n}");
            Response response = client.newCall(request).execute();
            Log.v("response_token",response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

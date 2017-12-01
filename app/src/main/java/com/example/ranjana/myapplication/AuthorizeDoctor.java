package com.example.ranjana.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizeDoctor extends AppCompatActivity {
    private GetDoctorListTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_doctor);
        showDoctorList();
    }
    public void showDoctorList(){
        mAuthTask = new GetDoctorListTask();
        mAuthTask.execute((Void) null);

    }
    public class GetDoctorListTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try
            {
                SharedPreferences sh = getSharedPreferences("auth", MODE_PRIVATE);
                OkHttpClient client = new OkHttpClient();
                String token = sh.getString("token",null);
                if(token == null)
                    return false;

                Request request = new Request.Builder()
                        .url("https://smartpill.herokuapp.com/api/doc")
                        .get()
                        .addHeader("content-type", "application/json")
                        .addHeader("authorization", "Bearer "+token)
                        .build();
                Log.v("Doctor Request",request.toString());
                Response response = client.newCall(request).execute();

            }

            catch (Exception e)
            {
                return false;
            }
            return true;
        }
    }
}

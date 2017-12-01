package com.example.ranjana.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Doctor extends AppCompatActivity {
    private EditText mNameView;
    private Authorize mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        mNameView = (EditText) findViewById(R.id.DoctorName);
        Button AuthorizeDoctor = (Button) findViewById(R.id.SendDoctor);
        AuthorizeDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAuthorization();
            }
        });
    }

    public void attemptAuthorization() {
        String name = mNameView.getText().toString();
        mAuthTask = new Authorize(name);
        mAuthTask.execute((Void) null);
    }

    public class Authorize extends AsyncTask<Void, Void, Boolean> {

        private final String mName;

        public Authorize(String name) {
            mName = name;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient();
                SharedPreferences sh = getSharedPreferences("auth", MODE_PRIVATE);
                SharedPreferences s2=getSharedPreferences("pill_bottle", MODE_PRIVATE);
                long id=s2.getLong("id",-1);
                String token = sh.getString("token",null);
                Log.v("pillid ",String.valueOf(id));
                Log.v("tokenpill",token);
                Log.v("doctorname",mName);
                Log.v("request","{ \r\n \"pillBottleId\" :"+id+",\r\n  \"doctorUsername\": \"" +mName+"\"\r\n}");

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\r\n  \"pillBottleId\":"+id+",\r\n  \"doctorUsername\": \"" +mName+"\"\r\n}");
                Request request = new Request.Builder()
                        .url("https://smartpill.herokuapp.com/api/pillbottle/doc")
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("authorization", "Bearer "+token)

                        .build();
                Log.v("DoctorRequest", request.body().toString());
                Response response = client.newCall(request).execute();
                Log.v("DoctorResponse", response.toString());
            } catch (Exception e) {
                return false;

            }
            return true;
        }
    }
}

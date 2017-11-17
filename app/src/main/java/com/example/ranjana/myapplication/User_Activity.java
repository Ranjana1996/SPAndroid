package com.example.ranjana.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ranjana.myapplication.Models.PillBottleIdWrapper;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class User_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_);
        Button button = (Button) findViewById(R.id.RegisterBottle1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegDevice().execute();
            }
        });
    }
    private SharedPreferences GetSharedPreferences()
    {
        return getSharedPreferences("pill_bottle", MODE_PRIVATE);
    }

    public void StoreToken(long id)
    {
        SharedPreferences sh = GetSharedPreferences();
        SharedPreferences.Editor editor = sh.edit();
        editor.putLong("id", id);
        editor.apply();
    }

    public long getToken()
    {
        SharedPreferences sh = GetSharedPreferences();
        return sh.getLong("id", -1);
    }
    public class RegDevice extends AsyncTask<Void,Void,Boolean>{
        PillBottleIdWrapper idWrapper;
        @Override
        protected Boolean doInBackground(Void... voids) {
           try {
               SharedPreferences sh = getSharedPreferences("auth", MODE_PRIVATE);
               String token = sh.getString("token", null);

               OkHttpClient client = new OkHttpClient();
              // MediaType mediaType = MediaType.parse("application/json");
              // RequestBody body=RequestBody.create(mediaType,"");
               //RequestBody reqbody = RequestBody.create(null, new byte[0]);
               MediaType mediaType = MediaType.parse("application/json");
               RequestBody body = RequestBody.create(mediaType,"{ }");


               Request request = new Request.Builder()
                       .url("https://smartpill.herokuapp.com/api/pillbottle")
                       .post(body)
                       .addHeader("authorization", "Bearer " + token)
                       .build();
               Log.v("request",request.toString());
               Response response = client.newCall(request).execute();
               String res = response.body().string();
               Log.v("responseRegisterBottle",response.toString());
               Log.v("responseBottleBody",res);
               if(response.isSuccessful())
               {
                   Gson gson = new Gson();

                   idWrapper = gson.fromJson(res, PillBottleIdWrapper.class);
                   User_Activity.this.StoreToken(idWrapper.id);

                    return true;
               }

               return false;
           }
           catch(Exception e1) {
               Log.e("REG_DEVICE", "ERROR", e1);
               return false;
           }
        }

     /*   private SharedPreferences GetSharedPreferences()
        {
            return getSharedPreferences("pill_bottle", MODE_PRIVATE);
        }

        public void StoreToken(long id)
        {
            SharedPreferences sh = GetSharedPreferences();
            SharedPreferences.Editor editor = sh.edit();
            editor.putLong("id", id);
            editor.apply();
        }

        public long getToken()
        {
            SharedPreferences sh = GetSharedPreferences();
            return sh.getLong("id", -1);
        }
        */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(aBoolean)
            {
                Toast.makeText(User_Activity.this,"Bottle Registration Successful", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(User_Activity.this,"Retry Registration", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

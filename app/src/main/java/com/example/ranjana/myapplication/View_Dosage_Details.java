package com.example.ranjana.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.json.JSONTokener;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class View_Dosage_Details extends AppCompatActivity {
    private ViewDetails mAuthTask = null;
    private String pill="xyz";
    private long course;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__dosage__details);
        show_details();



    }
    public void show_details(){
        mAuthTask=new ViewDetails();
        mAuthTask.execute((Void) null);


    }
    public class ViewDetails extends AsyncTask<Void,Void,Boolean> {


        @Override
        protected Boolean doInBackground(Void... voids) {
           try{ OkHttpClient client = new OkHttpClient();
               SharedPreferences sh = getSharedPreferences("auth", MODE_PRIVATE);
                SharedPreferences s2=getSharedPreferences("pill_bottle", MODE_PRIVATE);
                long id=s2.getLong("id",-1);
                id=361;
               String token = sh.getString("token",null);
            Request request = new Request.Builder()
                    .url("https://smartpill.herokuapp.com/api/pillbottle/"+id)
                    .get()
                    .addHeader("content-type", "application/json")
                    .addHeader("authorization", "Bearer "+token)

                    .build();
               Log.v("DosageRequest", request.toString());
            Response response = client.newCall(request).execute();
               String res = response.body().string();

               Log.v("DosageResponse", res);
               if(response.isSuccessful())
               {
                   Intent i = new Intent(View_Dosage_Details.this, WifiService.class);
                   i.putExtra(WifiService.WifiData, res);
                   startService(i);
                   JSONObject json= new JSONObject(res);

                   pill = json.getString("pill");
                   Log.v("pill",pill);


                   JSONObject json2= new JSONObject(res);
                    course = json2.getLong("course");
                   Log.v("course",String.valueOf(course));
                    //Course.setText(String.valueOf(course));
                   description = json.getString("description");
                   Log.v("description",description);
                   //Description.setText(description);


                 /* jobject = jobject.getAsJsonObject("dosage");
                     jarray = jobject.getAsJsonArray("dosage");
                   jobject = jarray.get(0).getAsJsonObject();
                   String result = jobject.get("time").toString();
                   Log.e("dosage",result);*/


                   //String pill_name = gson.;
                   //User_Activity.this.StoreToken(idWrapper.id);

                   return true;
               }
           }
            catch(Exception e)
            {

            }

            return true;
        }
        @Override
        protected void onPostExecute ( final Boolean success){
            mAuthTask=null;
            TextView PillName = (TextView) findViewById(R.id.PillName);
            TextView Course=(TextView) findViewById(R.id.course);
            TextView  Description=(TextView) findViewById(R.id.Description);
           // Log.v("pillname2",pill);
            PillName.setText(pill);
            Course.setText(String.valueOf(course));
            Description.setText(description);


        }
    }
}

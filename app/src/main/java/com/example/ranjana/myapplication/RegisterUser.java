package com.example.ranjana.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterUser extends AppCompatActivity {
    private EditText mPasswordView;
    private EditText mNameView;
    private EditText mUsernameView;
    private UserRegistrationTask mAuthTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mPasswordView = (EditText) findViewById(R.id.PasswordR);
        mNameView=(EditText) findViewById(R.id.NameR);
        mUsernameView=(EditText) findViewById(R.id.UserNameR);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
         Button RegisterUser=(Button) findViewById(R.id.RegisterUser);
        RegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    public void attemptRegister(){
        String usename = mUsernameView.getText().toString();
        String name=mNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        mAuthTask = new UserRegistrationTask(usename,name, password);
        mAuthTask.execute((Void) null);

    }
    public class UserRegistrationTask extends AsyncTask<Void,Void,Boolean> {
        private final String mUsername;
        private final String mPassword;
        private final String mName;
        UserRegistrationTask(String username,String name, String password) {
            mUsername = username;
            mPassword = password;
            mName=name;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
           try{ OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n  \"name\": \""+mName+"\",\r\n  \"username\": \""+mUsername+"\",\r\n  \"password\": \""+mPassword+"\",\r\n  \"typeId\": 1\r\n}");
            Request request = new Request.Builder()
                    .url("https://smartpill.herokuapp.com/api/signup")
                    .post(body)
                    .addHeader("content-type", "application/json")

                    .build();
               Log.v("Signuprequest",request.toString());

            Response response = client.newCall(request).execute();
               Log.v("Signuprequest",response.toString());
           }
            catch(Exception e)
            {
                return false;
            }
            return true;
        }
    }


}

package com.fyp_app;

import android.content.Intent;

import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fyp_app.MyFirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;

import static com.google.android.gms.internal.zzs.TAG;

public class LoginActivity extends AppCompatActivity {

    private static final String Activity_TAG = "LoginActivity";
    public String login_username;
    public String login_password;
    public static final String EXTRA_MESSAGE = "com.fyp.LoginActivity";
    public AlertDialog info_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token_loginpage: " + token);

    }



    /** Function for Login_Button */
    public void loginButton(View view) throws IOException {

        final Intent intent_HomePageActivity = new Intent(this, HomePageActivity.class);
        EditText username = (EditText) findViewById(R.id.Login_Username);
        EditText password = (EditText) findViewById(R.id.Login_Password);
        login_username = username.getText().toString();
        login_password = password.getText().toString();

        final TextView test = (TextView) findViewById(R.id.test);
        final Handler login_dialog = new Handler();



        //Thread for network
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                URL url = null;
                HttpURLConnection connection = null;
                try{
                    url = new URL("http://"+getString(R.string.Server_IP)+":"+getString(R.string.Server_Port)+"/login/"+login_username+"&"+login_password);
                    connection = (HttpURLConnection) url.openConnection();


                    InputStream stream = connection.getInputStream();
                    final BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                    final StringBuilder response = new StringBuilder();

                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }

                    final JSONObject response_json = new JSONObject(response.toString());


/**
                    //Thread for UI operation
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                               // test.setText(in.readLine());
                            try {
                                test.setText(response_json.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
*/


                    login_dialog.post(new Runnable() {
                        @Override
                        public void run() {
                            info_dialog = new AlertDialog.Builder(LoginActivity.this).create();
                            info_dialog.setTitle("Info");
                            try {
                                info_dialog.setMessage(response_json.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            info_dialog.show();




                        }
                    });




                    //intent to HomePageActivity
                    if(response_json.getBoolean("check_login")) {

                        Thread.sleep(500);
                        if(info_dialog.isShowing()){
                            info_dialog.dismiss();
                        }
                        intent_HomePageActivity.putExtra(EXTRA_MESSAGE, response.toString());
                        startActivity(intent_HomePageActivity);
                    }



                } catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        thread.start();


    }

    //Function for register
    public void registerText(View view) throws IOException {
        final Intent intent_RegisterPageActivity = new Intent(this, RegisterPageActivity.class);
        startActivity(intent_RegisterPageActivity);

    }



}

package com.fyp_app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.android.gms.internal.zzs.TAG;

public class RegisterPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token_registerpage: " + token);
    }

    public void registerButton(View view) throws IOException{

        final Intent intent_LoginActivity = new Intent(this, LoginActivity.class);

        EditText text_usernameInput = (EditText) findViewById(R.id.Register_usernameInput);
        EditText text_passwordInput = (EditText) findViewById(R.id.Register_passwordInput);
        EditText text_passwordInput_check = (EditText) findViewById(R.id.Register_passwordInput_check);
        EditText text_emailAddrInput = (EditText) findViewById(R.id.Register_emailAddrInput);

        final String usernameInput = text_usernameInput.getText().toString();
        final String passwordInput = text_passwordInput.getText().toString();
        String passwordInput_check = text_passwordInput_check.getText().toString();
        final String emailAddrInput = text_emailAddrInput.getText().toString();

        if(!check_password(passwordInput, passwordInput_check)) {
            AlertDialog info_dialog = new AlertDialog.Builder(RegisterPageActivity.this).create();
            info_dialog.setTitle("Info");
            info_dialog.setMessage("Password Check Fails");
            info_dialog.show();
        }else if(!check_emailAddr(emailAddrInput)){
            AlertDialog info_dialog = new AlertDialog.Builder(RegisterPageActivity.this).create();
            info_dialog.setTitle("Info");
            info_dialog.setMessage("Invalid Email Address!");
            info_dialog.show();
        }else{


            final Handler register_dialog = new Handler();

            //Thread for network
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    URL url = null;
                    HttpURLConnection connection = null;
                    try{
                        url = new URL("http://"+getString(R.string.Server_IP)+":"+getString(R.string.Server_Port)+"/registration/"+usernameInput+"&"+passwordInput+"&"+emailAddrInput);
                        connection = (HttpURLConnection) url.openConnection();
                        InputStream stream = connection.getInputStream();
                        final BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                        final StringBuilder response = new StringBuilder();

                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }

                        final JSONObject response_json = new JSONObject(response.toString());


                        register_dialog.post(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog info_dialog = new AlertDialog.Builder(RegisterPageActivity.this).create();
                                info_dialog.setTitle("Info");
                                try {
                                    info_dialog.setMessage(response_json.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                info_dialog.show();




                            }
                        });


                        //intent to LoginActivity
                        if(response_json.getBoolean("check_registration")){
                            Thread.sleep(500);
                            startActivity(intent_LoginActivity);

                        }



/**
                        //intent to HomePageActivity
                        if(response_json.getBoolean("check_login") == true) {

                            Thread.sleep(500);
                            intent_HomePageActivity.putExtra(EXTRA_MESSAGE, response.toString());
                            startActivity(intent_HomePageActivity);
                        }
*/


                    } catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            thread.start();





        }

    }

    public boolean check_password(String password1, String password2){
        if(password1.equals(password2)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean check_emailAddr(String emailAddr){
        if(emailAddr.contains("@")){
            return true;
        }
        else{
            return false;
        }
    }



}

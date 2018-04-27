package com.fyp_app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class PaymentByAccountActivity extends AppCompatActivity {


    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.PaymentByAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_by_account);

        Intent intent = getIntent();
        try {
            JSONObject intent_message = new JSONObject(intent.getStringExtra(PaymentPageActivity.EXTRA_MESSAGE));
            this.user_name = intent_message.getString("user_name");
            this.user_id = intent_message.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void PayByAccountButton(View view) throws IOException{

        final Intent intent_PaymentPageActivity = new Intent(this, PaymentPageActivity.class);
        final AlertDialog info_dialog = new AlertDialog.Builder(PaymentByAccountActivity.this).create();

        EditText text_accountInput = (EditText) findViewById(R.id.TransferAccount);
        EditText text_account_checkInput = (EditText) findViewById(R.id.TransferAccount_check);
        EditText text_transferValue = (EditText) findViewById(R.id.TransferValue);


        final String accountInput = text_accountInput.getText().toString();
        final String account_checkInput = text_account_checkInput.getText().toString();
        final int transferValue = Integer.parseInt(text_transferValue.getText().toString());

        if(!accountInput.equals(account_checkInput)){
            info_dialog.setTitle("Info");
            info_dialog.setMessage("Account Check Fails");
            info_dialog.show();
        }else if(transferValue <=0){
            info_dialog.setTitle("Info");
            info_dialog.setMessage("Invalid Transfer Value");
            info_dialog.show();
        }else{



            final Handler transfer_dialog = new Handler();

            //Thread for network
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    URL url = null;
                    HttpURLConnection connection = null;
                    String FCMtoken = FirebaseInstanceId.getInstance().getToken();
                    try{
                        url = new URL("http://"+getString(R.string.Server_IP)+":"+getString(R.string.Server_Port)+"/transfer/"+user_name+"&"+accountInput+"&"+transferValue);
                        connection = (HttpURLConnection) url.openConnection();
                        InputStream stream = connection.getInputStream();
                        final BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                        final StringBuilder response = new StringBuilder();

                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }

                        final JSONObject response_json = new JSONObject(response.toString());


                        transfer_dialog.post(new Runnable() {
                            @Override
                            public void run() {
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
                        if(response_json.getBoolean("check_transfer")){
                            Thread.sleep(500);
                            if(info_dialog.isShowing()){
                                info_dialog.dismiss();
                            }
                            startActivity(intent_PaymentPageActivity);

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


}

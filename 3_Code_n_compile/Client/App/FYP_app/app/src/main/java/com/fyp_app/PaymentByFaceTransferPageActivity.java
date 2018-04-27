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

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaymentByFaceTransferPageActivity extends AppCompatActivity {


    String toUserName = null;
    String fromUserName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_by_face_transfer_page);


        Intent intent = getIntent();


        fromUserName = intent.getStringExtra("fromName");
        toUserName = intent.getStringExtra("toName");
        TextView message = (TextView) findViewById(R.id.faceTransfer_message);
        String msg = "You will transfer to user "+toUserName;
        message.setText(msg);
//        message.setTextScaleX(3f);
        message.setTextSize(message.getTextSize()*0.5f);



    }



    public void PayByFaceTransferButton(View view) throws IOException {


        final AlertDialog info_dialog = new AlertDialog.Builder(PaymentByFaceTransferPageActivity.this).create();

        EditText text_valueInput = (EditText) findViewById(R.id.faceTransfer_value);
        final int transferValue = Integer.parseInt(text_valueInput.getText().toString());

        if(transferValue <=0){
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
                        url = new URL("http://"+getString(R.string.Server_IP)+":"+getString(R.string.Server_Port)+"/transfer/"+fromUserName+"&"+toUserName+"&"+transferValue);
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
                                Thread.sleep(500);
                                finish();
                            }




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

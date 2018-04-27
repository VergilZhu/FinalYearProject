package com.fyp_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fyp_app.util.QRCodeUtil;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaymentByMyQRCodeActivity extends AppCompatActivity {
    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.PaymentByMyQRCodeActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_by_my_qrcode);

        Intent intent = getIntent();
        try {
            JSONObject intent_message = new JSONObject(intent.getStringExtra(PaymentPageActivity.EXTRA_MESSAGE));
            this.user_name = intent_message.getString("user_name");
            this.user_id = intent_message.getString("user_id");

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }



    public void PayByQRCodeButton(View view) throws IOException {

        final Intent intent_PaymentPageActivity = new Intent(this, PaymentPageActivity.class);
        final AlertDialog info_dialog = new AlertDialog.Builder(PaymentByMyQRCodeActivity.this).create();


        EditText text_transferValue = (EditText) findViewById(R.id.TransferValue_qr);

        final int transferValue = Integer.parseInt(text_transferValue.getText().toString());

        if(transferValue <=0){
            info_dialog.setTitle("Info");
            info_dialog.setMessage("Invalid Transfer Value");
            info_dialog.show();
        }else{



            final Handler qrCode_dialog = new Handler();
            final Intent intent_PaymentByMyQRCodeDisplayActivity = new Intent(this, PaymentByMyQRCodeDisplayActivity.class);

            //Thread for network
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    URL url = null;
                    HttpURLConnection connection = null;
                    String FCMtoken = FirebaseInstanceId.getInstance().getToken();
                    try{
                        url = new URL("http://"+getString(R.string.Server_IP)+":"+getString(R.string.Server_Port)+"/qrcode/"+user_name+"&2&"+Integer.toString(transferValue)+"&"+FCMtoken);
                        connection = (HttpURLConnection) url.openConnection();


                        InputStream stream = connection.getInputStream();
                        final BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                        final StringBuilder response = new StringBuilder();

                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }

                        final JSONObject response_json = new JSONObject(response.toString());




                        if(!response_json.getBoolean("check_qrCodeCreated")) {
                            qrCode_dialog.post(new Runnable() {
                                @Override
                                public void run() {
                                    // info_dialog = new AlertDialog.Builder(PaymentByMyQRCodeActivity.this).create();
                                    info_dialog.setTitle("Info");
                                    info_dialog.setMessage("Fail to generate QR Code");
                                    info_dialog.show();


                                }
                            });
                        }else{

                            String message = response.toString();

                            //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
                            intent_PaymentByMyQRCodeDisplayActivity.putExtra(EXTRA_MESSAGE, message);
                            startActivity(intent_PaymentByMyQRCodeDisplayActivity);


                        }



//
//                    //intent to HomePageActivity
//                    if(response_json.getBoolean("check_login")) {
//
//                        Thread.sleep(500);
//                        if(info_dialog.isShowing()){
//                            info_dialog.dismiss();
//                        }
//                        intent_HomePageActivity.putExtra(EXTRA_MESSAGE, response.toString());
//                        startActivity(intent_HomePageActivity);
//                    }



                    } catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            thread.start();






        }




    }


}




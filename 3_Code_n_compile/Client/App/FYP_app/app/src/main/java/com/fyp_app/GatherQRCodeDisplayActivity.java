package com.fyp_app;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp_app.util.QRCodeUtil;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GatherQRCodeDisplayActivity extends AppCompatActivity {


    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.GatherQRCodeDisplayActivity";
    public AlertDialog info_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gather_qrcode_display);

        Intent intent = getIntent();
        try {
            JSONObject intent_message = new JSONObject(intent.getStringExtra(GatheringPageActivity.EXTRA_MESSAGE));
            this.user_name = intent_message.getString("user_name");
            this.user_id = intent_message.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Handler qrCode_dialog = new Handler();


        //Thread for network
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                URL url = null;
                HttpURLConnection connection = null;
                String FCMtoken = FirebaseInstanceId.getInstance().getToken();
                try{
                    url = new URL("http://"+getString(R.string.Server_IP)+":"+getString(R.string.Server_Port)+"/qrcode/"+user_name+"&1&-1"+"&"+FCMtoken);
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
                                info_dialog = new AlertDialog.Builder(GatherQRCodeDisplayActivity.this).create();
                                info_dialog.setTitle("Info");
                                info_dialog.setMessage("Fail to generate QR Code");
                                info_dialog.show();


                            }
                        });
                    }else{



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String qrCode_content_string = response_json.toString();
                                ImageView mImageView = (ImageView) findViewById(R.id.gather_qrcode);
                                Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(qrCode_content_string, 480, 480);
//                        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap("test", 480, 480);
                                mImageView.setImageBitmap(mBitmap);
                            }
                        });




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


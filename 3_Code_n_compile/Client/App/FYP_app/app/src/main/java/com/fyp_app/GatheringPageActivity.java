package com.fyp_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GatheringPageActivity extends AppCompatActivity {

    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.GatheringPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering_page);

        Intent intent = getIntent();
        try {
            JSONObject intent_message = new JSONObject(intent.getStringExtra(HomePageActivity.EXTRA_MESSAGE));
            this.user_name = intent_message.getString("user_name");
            this.user_id = intent_message.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void GatherByScanButton(View view) throws IOException {

        String message = new String("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        final Intent intent_GatheringByScan = new Intent(this, GatherByScanActivity.class);
        intent_GatheringByScan.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent_GatheringByScan);


    }

    public void GatherByMyQRCodeButton(View view) throws IOException {

        String message = new String("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        final Intent intent_GatherQRCodeDisplay = new Intent(this, GatherQRCodeDisplayActivity.class);
        intent_GatherQRCodeDisplay.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent_GatherQRCodeDisplay);


    }

    public void GatherByFaceButton(View view) throws IOException {

        String message = new String("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        final Intent intent_GatherByFace = new Intent(this, GatherByFaceActivity.class);
        intent_GatherByFace.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent_GatherByFace);


    }
}

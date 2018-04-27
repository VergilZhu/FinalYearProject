package com.fyp_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class HomePageActivity extends AppCompatActivity {

    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.HomePageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        try {
            JSONObject intent_message = new JSONObject(intent.getStringExtra(LoginActivity.EXTRA_MESSAGE));
            this.user_name = intent_message.getString("user_name");
            this.user_id = intent_message.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void ProfileButton(View view) throws IOException {

    }

    public void PaymentButton(View view) throws IOException {


        String message = new String("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        final Intent intent_PaymentPageActivity = new Intent(this, PaymentPageActivity.class);
        intent_PaymentPageActivity.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent_PaymentPageActivity);




    }

    public void GatheringButton(View view) throws IOException {



        String message = new String("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        final Intent intent_GatheringPageActivity = new Intent(this, GatheringPageActivity.class);
        intent_GatheringPageActivity.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent_GatheringPageActivity);

    }

    public void SettingButton(View view) throws IOException {

    }


}

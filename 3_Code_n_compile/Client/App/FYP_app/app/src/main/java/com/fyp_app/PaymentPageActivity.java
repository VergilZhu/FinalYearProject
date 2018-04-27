package com.fyp_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class PaymentPageActivity extends AppCompatActivity {

    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.PaymentPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        Intent intent = getIntent();
        try {
            JSONObject intent_message = null;
            if(intent.getStringExtra(HomePageActivity.EXTRA_MESSAGE)!= null){
            intent_message = new JSONObject(intent.getStringExtra(HomePageActivity.EXTRA_MESSAGE));}
            if(intent.getStringExtra(PaymentByAccountActivity.EXTRA_MESSAGE)!=null){
            intent_message = new JSONObject(intent.getStringExtra(HomePageActivity.EXTRA_MESSAGE));
            }
            if(intent.getStringExtra(PaymentByScanActivity.EXTRA_MESSAGE)!=null){
            intent_message = new JSONObject(intent.getStringExtra(PaymentByScanActivity.EXTRA_MESSAGE));
            }
            this.user_name = intent_message.getString("user_name");
            this.user_id = intent_message.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void PaymentByScanButton(View view) throws IOException{

        String message = new String("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        final Intent intent_PaymentByScanActivity = new Intent(this, PaymentByScanActivity.class);
        intent_PaymentByScanActivity.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent_PaymentByScanActivity);

    }

    public void PaymentByAccountButton(View view) throws IOException{

        String message = new String("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        final Intent intent_PaymentByAccountActivity = new Intent(this, PaymentByAccountActivity.class);
        intent_PaymentByAccountActivity.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent_PaymentByAccountActivity);

    }

    public void PaymentByMyQRCodeButton(View view) throws IOException{

        String message = new String("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        final Intent intent_PaymentByMyQRCodeActivity = new Intent(this, PaymentByMyQRCodeActivity.class);
        intent_PaymentByMyQRCodeActivity.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent_PaymentByMyQRCodeActivity);

    }


}

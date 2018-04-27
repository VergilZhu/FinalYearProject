package com.fyp_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.fyp_app.util.QRCodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentByMyQRCodeDisplayActivity extends AppCompatActivity {
    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.PaymentByMyQRCodeDisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_by_my_qrcode_display);

        Intent intent = getIntent();
        try {
            JSONObject intent_message = new JSONObject(intent.getStringExtra(PaymentByMyQRCodeActivity.EXTRA_MESSAGE));
            this.user_name = intent_message.getString("user_name");

            String qrcontent_string = intent_message.toString();

            ImageView mImageView = (ImageView) findViewById(R.id.payment_qrcode);
            Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(qrcontent_string  , 480, 480);
            mImageView.setImageBitmap(mBitmap);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}




package com.fyp_app;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;

public class PaymentByScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.PaymentByScanActivity";
    public Intent intent_PaymentPageActivity;

    private String input_value = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "onCreate");

        Intent intent = getIntent();
        try {
            JSONObject intent_message = new JSONObject(intent.getStringExtra(PaymentPageActivity.EXTRA_MESSAGE));
            this.user_name = intent_message.getString("user_name");
            this.user_id = intent_message.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String message = new String("{'user_name':"+user_name+", 'user_id':"+user_id+"}");
        //JSONObject message = new JSONObject("{'user_name':"+this.user_name+", 'user_id':"+this.user_id+"}");
        intent_PaymentPageActivity = new Intent(this, PaymentPageActivity.class);
        intent_PaymentPageActivity.putExtra(EXTRA_MESSAGE, message);

        mScannerView = new ZXingScannerView(this);

        setContentView(mScannerView);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();

            } else {
                requestPermission();
            }
        }

    }

    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(PaymentByScanActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.e("onResume", "onResume");
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        Log.e("currentapiVersion", Integer.toString(currentapiVersion));
        Log.e("requiredapiVersion", Integer.toString(android.os.Build.VERSION_CODES.M));

        if (currentapiVersion+1 >= android.os.Build.VERSION_CODES.M) {
            //Log.e("apiVersionCheck1", "apiVersionCheck1");
            if (checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
                Log.e("startCamera", "startCamera");
            } else {
                //Log.e("requestPermission", "requestPermission");
                requestPermission();
            }
        }
        //Log.e("apiVersionCheck2", "apiVersionCheck2");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        final String result = rawResult.getText();
        final JSONObject result_json;
        try{
            result_json = new JSONObject(result);

            final String toName = result_json.getString("user_name");
            final String qrValidNum = result_json.getString("qrValidNum");

            Log.d("QRCodeScanner", rawResult.getText());
            Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pay to "+toName);
            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int input_value = Integer.parseInt(input.getText().toString());

                    requestForTransfer(user_name, toName, input_value, qrValidNum);

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });






            //builder.setMessage(rawResult.getText());

            AlertDialog alert1 = builder.create();
            alert1.show();
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }




    public void requestForTransfer(final String fromName, final String toName, final int transferValue, final String qrValidNum){

        final AlertDialog info_dialog = new AlertDialog.Builder(PaymentByScanActivity.this).create();


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
                        url = new URL("http://"+getString(R.string.Server_IP)+":"+getString(R.string.Server_Port)+"/transferbyqrcode/"+fromName+"&"+toName+"&"+Integer.toString(transferValue)+"&1&"+qrValidNum+"&"+FCMtoken);
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




                    } catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            thread.start();






        }




    }

}

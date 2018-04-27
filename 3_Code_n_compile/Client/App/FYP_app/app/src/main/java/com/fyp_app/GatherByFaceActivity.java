package com.fyp_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fyp_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static android.provider.Telephony.Mms.Part.CHARSET;

public class GatherByFaceActivity extends AppCompatActivity {
    private String user_name;
    private String user_id;
    public static final String EXTRA_MESSAGE = "com.fyp.GatherByFaceActivity";

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private CameraManager mCameraManager;
    private CameraDevice mCameraDeivce;
    private CameraCaptureSession mCameraSession;
    private CaptureRequest.Builder mRequestBuilder;
    private ImageReader mImageReader;
    private SavePhotoListener mSaveListener;
    private int width = 92;
    private int height = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        try {
            JSONObject intent_message = new JSONObject(intent.getStringExtra(GatheringPageActivity.EXTRA_MESSAGE));
            this.user_name = intent_message.getString("user_name");
            this.user_id = intent_message.getString("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_gather_by_face);
        mCameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_preview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceViewCallBack());
        mSaveListener = new SavePhotoListener();



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCameraDeivce != null){
            mCameraDeivce.close();
        }
    }

    public void onCapturePhoto(View view) {
        try {

            final CaptureRequest.Builder mBuilder = mCameraDeivce.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            mBuilder.addTarget(mImageReader.getSurface());
            mBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            mBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            mBuilder.set(CaptureRequest.CONTROL_AWB_MODE,CaptureRequest.CONTROL_AWB_MODE_AUTO);
//            mImageReader.setOnImageAvailableListener(mSaveListener,null);
            mCameraSession.stopRepeating();
            mCameraSession.capture((mBuilder.build()),new CameraResultCallBack(),null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean CheckPermission(){
        return true;
    }
    private class SurfaceViewCallBack implements SurfaceHolder.Callback{
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCameraManager.openCamera("0",new CameraCallBack(),null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    private class CameraCallBack extends CameraDevice.StateCallback{
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            Log.e("zyq","onOpened");
            mCameraDeivce = camera;
            try {
                mImageReader = ImageReader.newInstance(mSurfaceView.getWidth(),mSurfaceView.getHeight(), ImageFormat.JPEG,7);
                //mImageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG,7);
                mImageReader.setOnImageAvailableListener(mSaveListener,null);
                mRequestBuilder = mCameraDeivce.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                mRequestBuilder.addTarget(mSurfaceHolder.getSurface());
                mCameraDeivce.createCaptureSession(Arrays.asList(mSurfaceHolder.getSurface(),mImageReader.getSurface()),new CameraCaptureConfig(),null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {

        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            if(camera != null){
                camera.close();
            }
        }
    }

    private class CameraCaptureConfig extends CameraCaptureSession.StateCallback{
        @Override
        public void onConfigured(@NonNull CameraCaptureSession session) {
            mCameraSession = session;
            mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            mRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            mRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE,CaptureRequest.CONTROL_AWB_MODE_AUTO);
            try {
                mCameraSession.setRepeatingRequest(mRequestBuilder.build(),null,null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {

        }
    }

    private class CameraResultCallBack extends CameraCaptureSession.CaptureCallback{
        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            super.onCaptureProgressed(session, request, partialResult);
            Log.e("zyq","onCaptureProgressed");
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            mCameraSession = session;
            Log.e("zyq","onCaptureCompleted");
            try {
                mCameraSession.setRepeatingRequest(mRequestBuilder.build(),null,null);
            } catch (CameraAccessException e) {
                Log.e("zyq","onCaptureCompleted e="+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private class SavePhotoListener implements ImageReader.OnImageAvailableListener{
        @Override
        public void onImageAvailable(ImageReader reader) {
            Log.e("zyq","save photo");
            Image image = reader.acquireLatestImage();
            if(image != null){
                Log.e("zyq","image = "+image.getHeight()+" , "+image.getWidth());
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);











                Log.e("bitmap:", bitmapImage.toString());


                int []pixels = new int[bitmapImage.getWidth() * bitmapImage.getHeight()]; //通过位图的大小创建像素点数组

                bitmapImage.getPixels(pixels, 0, bitmapImage.getWidth(), 0, 0, bitmapImage.getWidth(), bitmapImage.getHeight());
                int alpha = 0xFF << 24;
                for(int i = 0; i < bitmapImage.getHeight(); i++)  {
                    for(int j = 0; j < bitmapImage.getWidth(); j++) {
                        int grey = pixels[bitmapImage.getWidth() * i + j];

                        int red = ((grey  & 0x00FF0000 ) >> 16);
                        int green = ((grey & 0x0000FF00) >> 8);
                        int blue = (grey & 0x000000FF);

                        grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);
                        grey = alpha | (grey << 16) | (grey << 8) | grey;
                        pixels[bitmapImage.getWidth() * i + j] = grey;
                    }
                }

                Matrix matrix_rotate = new Matrix();
                matrix_rotate.setRotate(90);
                Bitmap bmpGray = Bitmap.createBitmap(bitmapImage.getWidth(), bitmapImage.getHeight(), Bitmap.Config.RGB_565);
                bmpGray.setPixels(pixels, 0, bitmapImage.getWidth(), 0, 0, bitmapImage.getWidth(), bitmapImage.getHeight());

                bmpGray = Bitmap.createBitmap(bmpGray, 0, 0, bitmapImage.getWidth(), bitmapImage.getHeight(), matrix_rotate, true);












                //final String result = null;





//                Matrix scaleMatrix = new Matrix();
//                scaleMatrix.postScale((float)width, (float)height);
//
                Bitmap bmpGray_scaled = Bitmap.createScaledBitmap(bmpGray, width, height, true);

                File appDir = new File(Environment.getExternalStorageDirectory(), "FYP");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(appDir, fileName);

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmpGray_scaled.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }






                ByteArrayOutputStream baos = null;
                try {
                    if (bmpGray != null) {
                        baos = new ByteArrayOutputStream();
                        bmpGray_scaled.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        //bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        baos.flush();
                        baos.close();

                        byte[] bitmapBytes = baos.toByteArray();
                        final String result = android.util.Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
                        //Log.e("zzzz", result);
                        // send http request for identification
                        // result is the image
                        final TextView test = (TextView) findViewById(R.id.test);
                        final Handler info_dialog = new Handler();


                        final Intent intent_GatheringPageActivity = new Intent(GatherByFaceActivity.this, GatheringPageActivity.class);
                        //Thread for network
                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {

                                URL url = null;
                                HttpURLConnection connection = null;
                                try{
                                    url = new URL("http://"+getString(R.string.Server_IP)+":"+getString(R.string.Server_Port)+"/identification/upload/");
                                    connection = (HttpURLConnection) url.openConnection();

                                    connection.setRequestMethod("POST");
                                    connection.setReadTimeout(50000);
                                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                    connection.setRequestProperty("Connection", "keep-alive");
                                    //connection.setRequestProperty("Content-Length", String.valueOf(result.length()));
                                    //connection.setRequestProperty("Charset", CHARSET);
                                    connection.setRequestProperty("Cache-Control", "no-cache");
                                    //connection.setDoOutput(true);
                                    String output = "image="+result;


                                    connection.getOutputStream().write(output.getBytes());

                                    InputStream stream = connection.getInputStream();
                                    final BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                                    final StringBuilder response = new StringBuilder();

                                    String line;
                                    while ((line = in.readLine()) != null) {
                                        response.append(line);
                                    }

                                    final JSONObject response_json = new JSONObject(response.toString());


                                    //intent to HomePageActivity
                                    if(response_json.getBoolean("check_identification")) {

                                        intent_GatheringPageActivity.putExtra(EXTRA_MESSAGE, response.toString());
                                        startActivity(intent_GatheringPageActivity);
                                    }



                                } catch (Exception e){
                                    e.printStackTrace();
                                }


                            }
                        });

                        thread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (baos != null) {
                            baos.flush();
                            baos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }




            }
        }
    }

}

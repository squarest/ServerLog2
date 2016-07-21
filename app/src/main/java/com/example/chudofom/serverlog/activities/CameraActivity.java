package com.example.chudofom.serverlog.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chudofom.serverlog.CameraPreview;
import com.example.chudofom.serverlog.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
    private android.hardware.Camera mCamera;
    private CameraPreview mCameraPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.preview);
        preview.addView(mCameraPreview);
        createToolbar();
        ImageView captureButton = (ImageView) findViewById(R.id.capture_button);
        captureButton.setOnClickListener(view ->
        {
            mCamera.takePicture(null, null, mPicture);
            Intent intent = new Intent(CameraActivity.this,PhotoInfoActivity.class);
            startActivity(intent);
        });
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.camera_toolbar);
        toolbar.setTitle("Сделать снимок");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private android.hardware.Camera getCameraInstance() {
        android.hardware.Camera camera = null;
        try {
            camera = android.hardware.Camera.open();
            camera.setDisplayOrientation(90);
        } catch (Exception e) {
            Toast.makeText(CameraActivity.this, "Камера недоступна", Toast.LENGTH_SHORT).show();
        }
        return camera;
    }

    android.hardware.Camera.PictureCallback mPicture = (data, camera) -> {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }
    };

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }
}
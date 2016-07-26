package com.example.chudofom.serverlog.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.activities.main.CommentActivity;
import com.example.chudofom.serverlog.databinding.ActivityPhotoInfoBinding;

import java.text.SimpleDateFormat;

public class PhotoInfoActivity extends AppCompatActivity {
    ActivityPhotoInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_info);
        createToolbar();
        String photoPath = getIntent().getExtras().getString("photoPath");
        if(photoPath!= null) {
            Bitmap photo = rotatePhoto(photoPath);
            binding.photoView.setImageBitmap(photo);
        }
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM  HH:MM");
        binding.timeView.setText(sdf.format(date));
        binding.addComment.setOnClickListener(view ->
        {
            Intent intent = new Intent(PhotoInfoActivity.this, CommentActivity.class);
            intent.putExtra("comment", binding.addComment.getText().toString());
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data == null) return;
        binding.addComment.setText(data.getExtras().getString("comment"));
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.camera_toolbar);
        toolbar.setTitle("Отчет");
        toolbar.inflateMenu(R.menu.report_menu);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setOnMenuItemClickListener(item ->
        {
            Toast.makeText(PhotoInfoActivity.this, "Отправлено", Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    private Bitmap rotatePhoto(String photoPath) {
        Bitmap photo = BitmapFactory.decodeFile(photoPath);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
    }

}

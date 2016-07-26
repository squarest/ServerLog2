package com.example.chudofom.serverlog.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class MyBindingAdapter {
    @BindingAdapter("age")
    public static void setAge(TextView textView, String ageInMills) {

        if (ageInMills != null) {
            textView.setText(AgeFormatter.millsToAge(Long.parseLong(ageInMills)));
        }

    }

    @BindingAdapter({"app:imagePath", "app:placeHolder"})
    public static void setImagePath(ImageView imageView, String imagePath, Drawable placeHolder) {
        Picasso.with(imageView.getContext())
                .load(new File(imagePath))
                .placeholder(placeHolder)
                .into(imageView);
    }
}

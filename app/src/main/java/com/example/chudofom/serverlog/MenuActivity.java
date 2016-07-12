package com.example.chudofom.serverlog;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.chudofom.serverlog.databinding.ActivityMenuBinding;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import rx.Subscription;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);


        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("Меню");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitleTextColor(Color.WHITE);
        binding.setSwitchCheck(false);
        Subscription subscribe = RxCompoundButton.checkedChanges(binding.gpsSwitch)
                .subscribe(b -> {
                    if (b) {
                        binding.setGpsStatus("Маяк работает");
                    } else {
                        binding.setGpsStatus("Включить маяк");
                    }
                });
        binding.line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, PersonalAreaActivity.class);
                startActivity(intent);
            }
        });


    }
}
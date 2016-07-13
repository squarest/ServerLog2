package com.example.chudofom.serverlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class PersonalAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);
        Toolbar toolbar = (Toolbar)findViewById(R.id.account_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.inflateMenu(R.menu.personal_area_menu);
        toolbar.setOnMenuItemClickListener(view ->
        {
            Intent intent = new Intent(PersonalAreaActivity.this,EditActivity.class);
            startActivity(intent);
            return true;
        });
    }
}

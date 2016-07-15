package com.example.chudofom.serverlog.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.databinding.ActivityPersonalAreaBinding;
import com.example.chudofom.serverlog.util.UserRepository;

public class AccountActivity extends AppCompatActivity {

    ActivityPersonalAreaBinding binding;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_area);
        binding.setUser(userRepository.getUser());
        createToolbar();
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.account_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(view ->
        {
            Intent intent = new Intent(AccountActivity.this, MenuActivity.class);
            startActivity(intent);

        });
        toolbar.inflateMenu(R.menu.personal_area_menu);
        toolbar.setOnMenuItemClickListener(view ->
        {
            Intent intent = new Intent(AccountActivity.this, EditAccountActivity.class);
            if (userRepository.getUser() != null) intent.putExtra("userIsFound", true);
            else intent.putExtra("userIsFound", false);
            startActivity(intent);
            return true;
        });
    }
}

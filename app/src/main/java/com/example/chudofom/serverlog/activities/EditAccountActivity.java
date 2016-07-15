package com.example.chudofom.serverlog.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.databinding.ActivityEditBinding;
import com.example.chudofom.serverlog.model.User;
import com.example.chudofom.serverlog.util.UserRepository;

public class EditAccountActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    UserRepository userRepository;
    boolean userIsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        userRepository = new UserRepository(this);
        userIsFound = getIntent().getExtras().getBoolean("userIsFound");
        if (userIsFound) {
            binding.setUser(userRepository.getUser());
        }
        else
        {
            User user = new User();
            binding.setUser(user);
        }
        createToolbar();
    }

    private void submit() {
        User user = new User();
        user.firstName = binding.firstName.getText().toString();
        user.lastName = binding.lastName.getText().toString();
        user.patronymic =binding.patronymic.getText().toString();
        user.age = binding.age.getText().toString();
        user.phone =binding.phone.getText().toString();
        user.email =binding.email.getText().toString();
        user.city = binding.city.getText().toString();
        if (userIsFound)
        {
            userRepository.editUser(user);
            Toast.makeText(EditAccountActivity.this, "Изменено", Toast.LENGTH_SHORT).show();
        }
        else
        {
            userRepository.addUser(user);
            Toast.makeText(EditAccountActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
        }
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_toolbar);
        toolbar.setTitle("Редактировать профиль");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.edit_menu);
        toolbar.setOnMenuItemClickListener(view ->
        {
            submit();
            Intent intent = new Intent(EditAccountActivity.this, AccountActivity.class);
            startActivity(intent);
            return true;
        });
    }
}

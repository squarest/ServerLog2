package com.example.chudofom.serverlog.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.databinding.ActivityPersonalAreaBinding;
import com.example.chudofom.serverlog.model.User;
import com.example.chudofom.serverlog.util.AgeFormatter;
import com.example.chudofom.serverlog.util.DB.UserRepository;

public class AccountActivity extends AppCompatActivity {

    ActivityPersonalAreaBinding binding;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_area);
        User user = userRepository.getUser();
        if (user.age != null) {
            user.age = AgeFormatter.milisToAge(Long.parseLong(user.age));
        }

        if (user.imagePath != null && user.imagePath.length() != 0) {
            binding.profileImage.setImageBitmap(BitmapFactory.decodeFile(user.imagePath));
        } else binding.profileImage.setImageResource(R.drawable.shape);
        binding.setUser(user);
        createToolbar();
    }

//    public void getFromServer(){
//        ProgressDialog dialog = ProgressDialog.show(AccountActivity.this, "",
//                "Loading. Please wait...", false);
//        ConnectToServer.getInstance().getUser()
//                .delay(5, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(()->dialog.dismiss())
//                .subscribe(user ->
//                {
//                    if (user.age != null) {
//                        user.age = AgeFormatter.milisToAge(Long.parseLong(user.age));
//                    }
//                    binding.setUser(user);
//                });
//
//    }

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

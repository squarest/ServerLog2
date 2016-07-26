package com.example.chudofom.serverlog.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.chudofom.serverlog.DB.UserRepository;
import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.databinding.ActivityPersonalAreaBinding;
import com.example.chudofom.serverlog.model.User;

public class AccountActivity extends AppCompatActivity {

    ActivityPersonalAreaBinding binding;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_area);
        User user = userRepository.getUser();
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
//                        user.age = AgeFormatter.millsToAge(Long.parseLong(user.age));
//                    }
//                    binding.setUser(user);
//                },
//                        throwable ->
//                        {
//                            Log.d("TAG", throwable.getMessage());
//                            Toast.makeText(EditAccountActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                        });
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
            if (userRepository.getUser() != null) intent.putExtra(EditAccountActivity.USER_IS_FOUND, true);
            else intent.putExtra(EditAccountActivity.USER_IS_FOUND, false);
            startActivity(intent);
            return true;
        });
    }
}

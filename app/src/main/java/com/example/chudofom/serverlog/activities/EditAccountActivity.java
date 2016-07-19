package com.example.chudofom.serverlog.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.databinding.ActivityEditBinding;
import com.example.chudofom.serverlog.model.User;
import com.example.chudofom.serverlog.util.AgeFormatter;
import com.example.chudofom.serverlog.util.ImagePicker;
import com.example.chudofom.serverlog.util.UserRepository;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Calendar;

import rx.Observable;

public class EditAccountActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    UserRepository userRepository;
    boolean userIsFound;
    long ageInMillis;
    String picturePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        createToolbar();
        createDatePickerDialog();
        validation();
        userRepository = new UserRepository(this);
        userIsFound = getIntent().getExtras().getBoolean("userIsFound");
        User user;
        if (userIsFound) user = userRepository.getUser();
        else user = new User();
        if (user.age != null) {
            user.age = AgeFormatter.milisToAge(Long.parseLong(user.age));
        }
        if (user.imagePath != null && user.imagePath.length() != 0) {
            binding.shape.setImageBitmap(BitmapFactory.decodeFile(user.imagePath));
        } else binding.shape.setImageResource(R.drawable.shape);
        binding.setUser(user);
        binding.shape.setOnClickListener(view -> {
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
            startActivityForResult(chooseImageIntent, 1);

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            picturePath = ImagePicker.getImageFromResult(this, resultCode, data);
            binding.shape.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }

    private void validation() {
        Observable<Boolean> firstNameObs = RxTextView.textChanges(binding.firstName)
                .map(charSequence -> charSequence.toString())
                .map(s -> s.matches("[a-z,A-Z,А-Я,а-я]+"));
        firstNameObs.subscribe(b -> changeColor(b, binding.firstName));

        Observable<Boolean> lastNameObs = RxTextView.textChanges(binding.lastName)
                .map(charSequence -> charSequence.toString())
                .map(s -> s.matches("[a-z,A-Z,А-Я,а-я]+"));
        lastNameObs.subscribe(b -> changeColor(b, binding.lastName));

        Observable<Boolean> patronymicObs = RxTextView.textChanges(binding.patronymic)
                .map(charSequence -> charSequence.toString())
                .map(s -> s.matches("[a-z,A-Z,А-Я,а-я]*"));
        patronymicObs.subscribe(b -> changeColor(b, binding.patronymic));

        Observable<Boolean> emailObs = RxTextView.textChanges(binding.email)
                .map(charSequence -> charSequence.toString())
                .map(s -> s.length() > 0)
                .map(b -> b ? binding.email.getText().toString().matches(".+@.+") : true);
        emailObs.subscribe(b -> changeColor(b, binding.email));

        Observable<Boolean> phoneObs = RxTextView.textChanges(binding.phone)
                .map(charSequence -> charSequence.length() > 0);
        phoneObs.subscribe(b -> changeColor(b, binding.phone));

        Observable<Boolean> cityObs = RxTextView.textChanges(binding.city)
                .map(charSequence -> charSequence.toString())
                .map(s -> s.matches("[a-z,A-Z,А-Я,а-я]*"));
        cityObs.subscribe(b -> changeColor(b, binding.city));
        ActionMenuItemView doneButton = (ActionMenuItemView) findViewById(R.id.done_button);
        doneButton.setOnClickListener(view ->
        {
            submit();
            Intent intent = new Intent(EditAccountActivity.this, AccountActivity.class);
            startActivity(intent);
        });
        Observable.combineLatest(firstNameObs, lastNameObs, patronymicObs, emailObs, phoneObs, cityObs,
                (a, b, c, d, e, f) -> a && b && c && d && e && f)
                .subscribe(b -> doneButton.setEnabled(b));
    }

    private void changeColor(Boolean b, EditText e) {
        if (!b) {
            e.setTextColor(Color.RED);
            e.setHintTextColor(Color.RED);
        } else {
            e.setTextColor(Color.BLACK);
            e.setHintTextColor(Color.GRAY);
        }
    }

    private void createDatePickerDialog() {
        final Calendar currentCal = Calendar.getInstance();
        final Calendar chosenCal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (datePicker, i, i1, i2) ->
                {
                    chosenCal.set(Calendar.DAY_OF_MONTH, i2);
                    chosenCal.set(Calendar.MONTH, i1);
                    chosenCal.set(Calendar.YEAR, i);
                    ageInMillis = currentCal.getTimeInMillis() - chosenCal.getTimeInMillis();
                    binding.age.setText(AgeFormatter.milisToAge(ageInMillis));
                },
                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                currentCal.get(Calendar.DAY_OF_MONTH));

        dialog.getDatePicker().setMaxDate(currentCal.getTimeInMillis());
        binding.age.setOnClickListener(view -> dialog.show());
    }

    private void submit() {
        User user = new User();
        if (userIsFound) {
            user = userRepository.getUser();
        }
        if (picturePath.length() != 0) user.imagePath = picturePath;
        user.firstName = binding.firstName.getText().toString();
        user.lastName = binding.lastName.getText().toString();
        user.patronymic = binding.patronymic.getText().toString();
        if (ageInMillis != 0) user.age = String.valueOf(ageInMillis);
        user.phone = binding.phone.getText().toString();
        user.email = binding.email.getText().toString();
        user.city = binding.city.getText().toString();
        if (userIsFound) {
            userRepository.editUser(user);
            Toast.makeText(EditAccountActivity.this, "Изменено", Toast.LENGTH_SHORT).show();
        } else {
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
    }
}

package com.example.chudofom.serverlog.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chudofom.serverlog.DB.UserRepository;
import com.example.chudofom.serverlog.R;
import com.example.chudofom.serverlog.camera.ImagePicker;
import com.example.chudofom.serverlog.databinding.ActivityEditBinding;
import com.example.chudofom.serverlog.model.User;
import com.example.chudofom.serverlog.util.AgeFormatter;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Subscription;

public class EditAccountActivity extends BaseActivity {
    public static final String USER_IS_FOUND = "userIsFound";
    private ActivityEditBinding binding;
    private UserRepository userRepository;
    private boolean userIsFound;
    private long ageInMillis;
    private String picturePath;
    private ActionMenuItemView doneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        createDatePickerDialog();
        validation();
        userRepository = new UserRepository(this);
        userIsFound = getIntent().getExtras().getBoolean(USER_IS_FOUND);
        User user = userIsFound ? userRepository.getUser() : new User();
        binding.setUser(user);
        binding.editPhoto.setOnClickListener(view -> {
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
            startActivityForResult(chooseImageIntent, 1);
        });
    }
//    public void getFromServer() {
//        ProgressDialog dialog = ProgressDialog.show(EditAccountActivity.this, "",
//                "Loading. Please wait...", false);
//        ConnectToServer.getInstance().getUser()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(() -> dialog.dismiss())
//                .subscribe(user ->
//                        {
//                            if (user.age != null) {
//                                user.age = AgeFormatter.millsToAge(Long.parseLong(user.age));
//                            }
//                            binding.setUser(user);
//                        },
//                        throwable ->
//                        {
//                            Log.d("TAG", throwable.getMessage());
//                            Toast.makeText(EditAccountActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                        });
//    }
//    public void setToServer(User user)
//    {
//        ProgressDialog dialog = ProgressDialog.show(EditAccountActivity.this, "",
//                "Loading. Please wait...", false);
//
//        ConnectToServer.getInstance().setUser(user)
//                .map(obs -> obs.firstName)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate(()->dialog.dismiss())
//                .subscribe(name -> Toast.makeText(EditAccountActivity.this, name, Toast.LENGTH_SHORT).show(),
//                        throwable ->
//                        {
//                            Log.d("TAG", throwable.getMessage());
//                            Toast.makeText(EditAccountActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                        });
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            picturePath = ImagePicker.getImageFromResult(this, resultCode, data);
            binding.editPhoto.post(() ->
                    binding.editPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath)));
        }


    }

    private void validation() {
        List<Observable<Boolean>> obsList = new ArrayList<Observable<Boolean>>();
        Observable<Boolean> firstNameObs = validateName(binding.firstName);
        obsList.add(firstNameObs);

        Observable<Boolean> lastNameObs = validateName(binding.lastName);
        obsList.add(lastNameObs);

        Observable<Boolean> patronymicObs = RxTextView.textChanges(binding.patronymic)
                .map(CharSequence::toString)
                .map(s -> s.matches("[a-zA-ZА-Яа-я]*"))
                .doOnNext(b -> changeColor(b, binding.patronymic));
        obsList.add(patronymicObs);

        Observable<Boolean> emailObs = RxTextView.textChanges(binding.email)
                .map(CharSequence::toString)
                .map(s -> s.length() > 0)
                .map(b -> b ? binding.email.getText().toString().matches(".+@.+") : !b)
                .doOnNext(b -> changeColor(b, binding.email));
        obsList.add(emailObs);

        Observable<Boolean> phoneObs = RxTextView.textChanges(binding.phone)
                .map(charSequence -> charSequence.length() > 0)
                .doOnNext(b -> changeColor(b, binding.phone));
        obsList.add(phoneObs);

        Observable<Boolean> cityObs = RxTextView.textChanges(binding.city)
                .map(CharSequence::toString)
                .map(s -> s.matches("[a-zA-ZА-Яа-я0-9 ,]*"))
                .doOnNext(b -> changeColor(b, binding.city));
        obsList.add(cityObs);

        Subscription subscription = Observable.combineLatest(obsList, a ->
        {

            for (Object anA : a) {
                if (!(boolean) anA) return false;
            }
            return true;
        })
                .subscribe(b -> doneButton.setEnabled(b));
        addSubscription(subscription);
    }

    private Observable<Boolean> validateName(EditText editText) {
        return RxTextView.textChanges(editText)
                .map(CharSequence::toString)
                .map(s -> s.matches("[a-zA-ZА-Яа-я]+"))
                .doOnNext(b -> changeColor(b, editText));
    }

    private boolean changeColor(boolean b, EditText e) {
        if (!b) {
            e.setTextColor(Color.RED);
            e.setHintTextColor(Color.RED);
        } else {
            e.setTextColor(Color.BLACK);
            e.setHintTextColor(Color.GRAY);
        }
        return b;
    }

    private void createDatePickerDialog() {
        final Calendar currentCal = Calendar.getInstance();
        final Calendar chosenCal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DialogTheme,
                (datePicker, i, i1, i2) ->
                {
                    chosenCal.set(Calendar.DAY_OF_MONTH, i2);
                    chosenCal.set(Calendar.MONTH, i1);
                    chosenCal.set(Calendar.YEAR, i);
                    ageInMillis = currentCal.getTimeInMillis() - chosenCal.getTimeInMillis();
                    binding.age.setText(AgeFormatter.millsToAge(ageInMillis));
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
        if (picturePath != null) user.imagePath = picturePath;
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

    protected void inflateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_toolbar);
        toolbar.setTitle("Редактировать профиль");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.edit_menu);
        doneButton = (ActionMenuItemView) findViewById(R.id.done_button);
        doneButton.setOnClickListener(view ->
        {
            submit();
            Intent intent = new Intent(EditAccountActivity.this, AccountActivity.class);
            startActivity(intent);
        });
    }
}

package com.example.chudofom.serverlog.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chudofom.serverlog.model.User;

/**
 * Created by Chudofom on 15.07.16.
 */
public class UserRepository {
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;

    public UserRepository(Context context) {
        myDBHelper = new MyDBHelper(context);
        sqLiteDatabase = myDBHelper.getWritableDatabase();
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(myDBHelper.COLUMN_FIRST_NAME, user.firstName);
        values.put(myDBHelper.COLUMN_LAST_NAME, user.lastName);
        values.put(myDBHelper.COLUMN_PATRONYMIC, user.patronymic);
        values.put(myDBHelper.COLUMN_AGE, user.age);
        values.put(myDBHelper.COLUMN_PHONE, user.phone);
        values.put(myDBHelper.COLUMN_EMAIL, user.email);
        values.put(myDBHelper.COLUMN_CITY, user.city);

        long newRowId;
        newRowId = sqLiteDatabase.insert(
                myDBHelper.TABLE_NAME,
                null,
                values);

    }

    public User getUser() {
        Cursor cursor = sqLiteDatabase.query("user", new String[]{myDBHelper.COLUMN_FIRST_NAME,
                myDBHelper.COLUMN_LAST_NAME, myDBHelper.COLUMN_PATRONYMIC,
                myDBHelper.COLUMN_AGE, myDBHelper.COLUMN_PHONE, myDBHelper.COLUMN_EMAIL,
                myDBHelper.COLUMN_CITY}, null, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.getCount() != 0) {
            User user = new User();
            user.firstName = cursor.getString(cursor.getColumnIndex(myDBHelper.COLUMN_FIRST_NAME));
            user.lastName = cursor.getString(cursor.getColumnIndex(myDBHelper.COLUMN_LAST_NAME));
            user.patronymic = cursor.getString(cursor.getColumnIndex(myDBHelper.COLUMN_PATRONYMIC));
            user.age = cursor.getString(cursor.getColumnIndex(myDBHelper.COLUMN_AGE));
            user.phone = cursor.getString(cursor.getColumnIndex(myDBHelper.COLUMN_PHONE));
            user.email = cursor.getString(cursor.getColumnIndex(myDBHelper.COLUMN_EMAIL));
            user.city = cursor.getString(cursor.getColumnIndex(myDBHelper.COLUMN_CITY));
            cursor.close();
            return user;
        } else return null;
    }

    public void editUser(User user) {
        ContentValues values = new ContentValues();
        values.put(myDBHelper.COLUMN_FIRST_NAME, user.firstName);
        values.put(myDBHelper.COLUMN_LAST_NAME, user.lastName);
        values.put(myDBHelper.COLUMN_PATRONYMIC, user.patronymic);
        values.put(myDBHelper.COLUMN_AGE, user.age);
        values.put(myDBHelper.COLUMN_PHONE, user.phone);
        values.put(myDBHelper.COLUMN_EMAIL, user.email);
        values.put(myDBHelper.COLUMN_CITY, user.city);

        int count = sqLiteDatabase.update(
                myDBHelper.TABLE_NAME,
                values,
                null,
                null);
    }
}

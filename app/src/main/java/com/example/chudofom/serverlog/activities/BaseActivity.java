package com.example.chudofom.serverlog.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {

    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateToolbar();
    }

    protected abstract void inflateToolbar();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();

    }

    protected void addSubscription(Subscription subscription)
    {
        subscriptions.add(subscription);
    }
    protected void unsubscribe() {
        if (subscriptions.hasSubscriptions()) subscriptions.unsubscribe();
    }
}

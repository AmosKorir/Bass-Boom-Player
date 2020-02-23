package com.app.apic.mvp.androidtemplate.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.app.apic.data.utils.RxUtil;
import com.app.apic.mvp.androidtemplate.MyApplication;
import com.app.apic.mvp.androidtemplate.di.activity.ActivityComponent;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Korir on 3/12/19.
 * amoskrr@gmail.com
 */
public class BaseActivity extends AppCompatActivity {
    protected CompositeDisposable compositeDisposable;

    @Override public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        injector().baseInject(this);
    }

    public ActivityComponent injector(){
        return ((MyApplication) getApplicationContext()).getActivityInjector(this);
    }

    public void handleError(Throwable throwable) {
        Toast.makeText(getBaseContext(), throwable.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override protected void onStart() {
        super.onStart();
        ButterKnife.bind(this);
        compositeDisposable = RxUtil.initDisposables(compositeDisposable);
    }

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    protected void dispose() {
        RxUtil.dispose(compositeDisposable);
    }
}
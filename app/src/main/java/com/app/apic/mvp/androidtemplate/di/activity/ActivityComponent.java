package com.app.apic.mvp.androidtemplate.di.activity;

import com.app.apic.mvp.androidtemplate.di.adapter.AdapterComponent;
import com.app.apic.mvp.androidtemplate.di.fragment.FragmentComponent;
import com.app.apic.mvp.androidtemplate.ui.activities.BaseActivity;
import com.app.apic.mvp.androidtemplate.ui.activities.MainActivity;
import dagger.Subcomponent;

/**
 * Created by Korir on 3/12/19.
 * amoskrr@gmail.com
 */
@ActivityScope @Subcomponent(modules = { ActivityModule.class })
public interface ActivityComponent {
  FragmentComponent.Builder fragmentBuilder();

  AdapterComponent.Builder adapterBuilder();

  void baseInject(BaseActivity baseActivity);

  void inject(MainActivity mainActivity);

  @Subcomponent.Builder interface Builder {
    Builder activityModule(ActivityModule activityModule);

    ActivityComponent build();
  }
}

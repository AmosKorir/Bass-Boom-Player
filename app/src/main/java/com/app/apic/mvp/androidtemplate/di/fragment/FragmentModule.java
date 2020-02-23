package com.app.apic.mvp.androidtemplate.di.fragment;

import com.app.apic.mvp.androidtemplate.ui.fragments.BaseDialogFragment;
import com.app.apic.mvp.androidtemplate.ui.fragments.BaseFragment;
import dagger.Module;

/**
 * Created by Korir on 3/12/19.
 * amoskrr@gmail.com
 */
@Module public class FragmentModule {
  private BaseFragment baseFragment;

  public FragmentModule(BaseFragment baseFragment) {
    this.baseFragment = baseFragment;
  }

  public FragmentModule(BaseDialogFragment baseDialogFragment) {
  }
}

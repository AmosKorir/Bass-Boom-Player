package com.app.apic.mvp.androidtemplate.di.fragment;

import com.app.apic.mvp.androidtemplate.ui.fragments.BaseDialogFragment;
import com.app.apic.mvp.androidtemplate.ui.fragments.BaseFragment;
import com.app.apic.mvp.androidtemplate.ui.fragments.EquilizerFragment;
import com.app.apic.mvp.androidtemplate.ui.fragments.MusicListFragment;
import dagger.Subcomponent;

/**
 * Created by Korir on 3/12/19.
 * amoskrr@gmail.com
 */
@Subcomponent(modules = FragmentModule.class) public interface FragmentComponent {

    void baseInject(BaseFragment baseFragment);

  void inject(MusicListFragment musicListFragment);

  void inject(EquilizerFragment equilizerFragment);

  void baseInject(BaseDialogFragment baseDialogFragment);

  @Subcomponent.Builder interface Builder{
        Builder fragmentModule(FragmentModule fragmentModule);
        FragmentComponent build();
    }
}

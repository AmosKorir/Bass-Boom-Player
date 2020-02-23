package com.app.apic.mvp.androidtemplate.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.ButterKnife;
import com.app.apic.data.utils.RxUtil;
import com.app.apic.domain.constants.DIConstants;
import com.app.apic.mvp.androidtemplate.di.activity.ActivityComponent;
import com.app.apic.mvp.androidtemplate.di.fragment.FragmentComponent;
import com.app.apic.mvp.androidtemplate.di.fragment.FragmentModule;
import com.app.apic.mvp.androidtemplate.ui.activities.BaseActivity;
import io.reactivex.disposables.CompositeDisposable;
import java.net.UnknownHostException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Korir on 10/16/19.
 * amoskrr@gmail.com
 */
public class BaseDialogFragment extends DialogFragment {
  @Inject @Named(DIConstants.ACTIVITY) Context context;
  private static final int NO_LAYOUT = -1;
  private CompositeDisposable compositeDisposable;
  private ProgressDialog progressDialog;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injector().baseInject(this);
  }

  public int getLayoutId() {
    return NO_LAYOUT;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable
      ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (getLayoutId() != NO_LAYOUT) {
      View view = inflater.inflate(getLayoutId(), container, false);
      ButterKnife.bind(this, view);
      return view;
    } else {
      return super.onCreateView(inflater, container, savedInstanceState);
    }
  }

  public void handleError(Throwable throwable) {
    if (isAdded()) {
      if (!(throwable instanceof UnknownHostException)) {
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
      }
    }
  }

  public void customToast(String message) {
    Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
    //View view = toast.getView();
    //view.setBackgroundResource(R.drawable.edit_background);
    //TextView text = (TextView) view.findViewById(android.R.id.message);
    //text.setPadding(50,0,50,0);
    //text.setTextColor(getResources().getColor(R.color.colorPrimary));
    toast.show();
  }

  protected FragmentComponent injector() {
    return activityInjector().fragmentBuilder().fragmentModule(new FragmentModule(this)).build();
  }

  protected ActivityComponent activityInjector() {
    return ((BaseActivity) getActivity()).injector();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    dispose();
  }

  public boolean onBackPressed() {
    return false;
  }

  public void user_location(double lat, double lon) {

  }

  public void showProgress(String message) {
    progressDialog = new ProgressDialog(context);
    progressDialog.setMessage(message + "....");
    progressDialog.show();
  }

  public void dismissProgressDialog() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  protected void dispose() {
    RxUtil.dispose(compositeDisposable);
  }

}

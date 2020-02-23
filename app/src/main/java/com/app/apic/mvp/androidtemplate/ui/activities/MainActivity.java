package com.app.apic.mvp.androidtemplate.ui.activities;

import android.content.Intent;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;
import com.app.apic.mvp.androidtemplate.R;
import com.app.apic.mvp.androidtemplate.ui.fragments.EquilizerFragment;
import com.app.apic.mvp.androidtemplate.ui.fragments.MusicListFragment;
import com.bullhead.equalizer.DialogEqualizerFragment;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MusicListFragment.MusicFragmentInterface {
  @Inject FragmentManager fragmentManager;
  @BindView(R.id.fragment_container) LinearLayout linearLayout;
  @BindView(R.id.bottosheet) LinearLayout blinearLayout;
  @BindView(R.id.floatingActionButton) FloatingActionButton equalizerFab;
  private BottomSheetBehavior bottomSheetBehavior;
  @BindView(R.id.control) PlayerControlView playerControlView;
  @BindView(R.id.control2) PlayerControlView playerControlView2;
  private MusicListFragment musicListFragment;
  private ExoPlayer exoPlayer;
  //private Equalizer equalizer;
  private Toolbar mTopToolbar;
  private EquilizerFragment equilizerFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_player);
    injector().inject(this);
    mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
    mTopToolbar.setTitle(R.string.app_name);
    mTopToolbar.setLogo(R.mipmap.ic_logo);
  }

  @Override protected void onStart() {
    super.onStart();
    showBottomSheet();
    musicListFragment = new MusicListFragment();
    equilizerFragment = new EquilizerFragment();
    musicListFragment.setMusicFragmentInterface(this);
    loadMusic();
  }

  private void loadMusic() {
    FragmentTransaction fragmentTransaction = initFragments();
    fragmentTransaction.replace(R.id.fragment_container, musicListFragment);
    fragmentTransaction.commit();
  }

  private void EquilizerView() {
    DialogEqualizerFragment fragment = DialogEqualizerFragment.newBuilder()
        .setAudioSessionId(exoPlayer.getAudioComponent().getAudioSessionId())
        .themeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        .textColor(ContextCompat.getColor(this, R.color.colorAccent))
        .accentAlpha(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        .darkColor(ContextCompat.getColor(this, R.color.colorAccent))
        .setAccentColor(ContextCompat.getColor(this, R.color.colorAccent))
        .build();
    fragment.show(getSupportFragmentManager(), "eq");
  }

  private FragmentTransaction initFragments() {
    FragmentTransaction fragmentTransaction;
    fragmentManager = getSupportFragmentManager();
    fragmentTransaction = fragmentManager.beginTransaction();
    return fragmentTransaction;
  }

  private void showBottomSheet() {
    bottomSheetBehavior = BottomSheetBehavior.from(blinearLayout);
    bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View view, int i) {
        switch (i) {
          case BottomSheetBehavior.STATE_HIDDEN:
            break;
          case BottomSheetBehavior.STATE_EXPANDED: {
            playerControlView.setVisibility(View.INVISIBLE);
          }
          break;
          case BottomSheetBehavior.STATE_COLLAPSED: {
            playerControlView.setVisibility(View.VISIBLE);
          }
          break;
          case BottomSheetBehavior.STATE_DRAGGING:
            playerControlView.setVisibility(View.INVISIBLE);
            break;
          case BottomSheetBehavior.STATE_SETTLING:
            break;
        }
      }

      @Override public void onSlide(@NonNull View view, float v) {

      }
    });
  }

  //sending broadcast
  private void sendControl(String control) {
    Intent intent = new Intent();
    intent.setAction("com.player.broadcast.MY_NOTIFICATION");
    intent.putExtra("data", control);
    sendBroadcast(intent);
  }

  @OnClick(R.id.floatingActionButton) public void fabClicked() {
    //EquilizerView();
    startEquilizer();
  }

  private void startEquilizer() {
    equilizerFragment.setAudioSession(exoPlayer.getAudioComponent().getAudioSessionId());
    equilizerFragment.show(fragmentManager, "equilizer");
  }

  @Override public void setPlayer(ExoPlayer simpleExoPlayer) {
    exoPlayer = simpleExoPlayer;
    playerControlView.setPlayer(simpleExoPlayer);
    playerControlView2.setPlayer(simpleExoPlayer);
    exoPlayer.getCurrentPosition();
    int sessionId = exoPlayer.getAudioComponent().getAudioSessionId();
    if (sessionId != 0) {
      //equalizer = new Equalizer(1000, sessionId);
      //equalizer.setEnabled(true);

    }
  }
}

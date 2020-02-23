package com.app.apic.mvp.androidtemplate.ui.fragments;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.apic.domain.models.Songs;
import com.app.apic.mvp.androidtemplate.R;
import com.app.apic.mvp.androidtemplate.ui.activities.MusicService;
import com.app.apic.mvp.androidtemplate.ui.adapters.MusicAdapter;
import com.app.apic.presentation.MusicPresenter;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicListFragment extends BaseFragment implements MusicPresenter.MyView,
    MusicAdapter.MusicAdapterInterface {
  private View view;
  @BindView(R.id.musicSong) RecyclerView recyclerView;
  @Inject MusicPresenter musicPresenter;
  MusicService musicService;
  Messenger messenger = null;
  private SimpleExoPlayer simpleExoPlayer;
  private boolean mBound = false;
  private MusicFragmentInterface musicFragmentInterface;
  ArrayList<Songs> songs;

  public MusicListFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    if (view == null) {
      view = inflater.inflate(R.layout.fragment_music_list, container, false);
      ButterKnife.bind(this, view);
    }
    return view;
  }

  @SuppressLint("NewApi") @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injector().inject(this);
  }

  public void setMusicFragmentInterface(
      MusicFragmentInterface musicFragmentInterface) {
    this.musicFragmentInterface = musicFragmentInterface;
  }

  public SimpleExoPlayer getSimpleExoPlayer() {
    return simpleExoPlayer;
  }

  @Override public void onStart() {
    super.onStart();
    context.startService(new Intent(context, MusicService.class));
    musicPresenter.setView(this);
    musicPresenter.getMusic();
    Intent intent = new Intent(context, MusicService.class);
    context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }

  private ServiceConnection mConnection = new ServiceConnection() {

    @Override
    public void onServiceConnected(ComponentName className,
        IBinder service) {
      // We've bound to LocalService, cast the IBinder and get LocalService instance
      musicService = ((MusicService.LocalBinder) service).getMusicService();
      mBound = true;
      try {
        musicFragmentInterface.setPlayer(musicService.getExoplayer());
      } catch (Exception e) {

      }
    }

    @Override
    public void onServiceDisconnected(ComponentName arg0) {
      mBound = false;
      messenger = null;
    }
  };

  @Override public void music(ArrayList<Songs> songs) {
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    MusicAdapter musicAdapter = new MusicAdapter(context, songs);
    musicAdapter.setMusicAdapterInterface(this);

    recyclerView.setAdapter(musicAdapter);
    this.songs = songs;
    if (mBound) {
      musicService.setMusic(songs);
    }
  }

  @Override public void onStop() {
    super.onStop();
    if (mBound) {
      context.unbindService(mConnection);
      mBound = false;
    }
  }

  @Override public void playerPosition(int position) {
    //musicService.setCurrentSong(position);
    musicService.setMusic(songs);
    musicService.getExoplayer().seekTo(position, C.TIME_UNSET);
  }

  public interface MusicFragmentInterface {
    void setPlayer(ExoPlayer simpleExoPlayer);
  }
}

package com.app.apic.mvp.androidtemplate.ui.activities;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.app.apic.domain.constants.Constants;
import com.app.apic.domain.models.Songs;
import com.app.apic.mvp.androidtemplate.MusicControlReceiver;
import com.app.apic.mvp.androidtemplate.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import java.util.ArrayList;

public class MusicService extends Service {
  private static final String ACTION = "com.player.broadcast.MY_NOTIFICATION";
  ExoPlayer exoPlayer;

  private ArrayList<Songs> songs;
  private MusicControlReceiver musicControlReceiver;
  private PlayerNotificationManager playerNotificationManager;
  private MediaSessionCompat mediaSession;

  private MediaSessionConnector mediaSessionConnector;

  public MusicService() {
  }

  // This is the object that receives interactions from clients.
  private final IBinder mBinder = new LocalBinder();

  public class LocalBinder extends Binder {

    public MusicService getMusicService() {
      return MusicService.this;
    }
  }

  public void initMediaSession() {
    mediaSession = new MediaSessionCompat(getApplicationContext(), "mysession");
    mediaSessionConnector = new MediaSessionConnector(mediaSession);
  }

  private void playSong() {

    ConcatenatingMediaSource concatenatedSource =
        new ConcatenatingMediaSource();
    addSources(songs, concatenatedSource);
    exoPlayer.setPlayWhenReady(true);
    exoPlayer.prepare(concatenatedSource);
  }

  private void addSources(ArrayList<Songs> songs, ConcatenatingMediaSource concatenatedSource) {
    for (int postion = 0; postion < songs.size(); postion++) {
      Uri uri = Uri.fromFile(songs.get(postion).getFile());
      MediaSource mediaSource = new ProgressiveMediaSource.Factory(new FileDataSourceFactory())
          .createMediaSource(uri);
      concatenatedSource.addMediaSource(mediaSource);
    }
  }

  @Override public void onCreate() {
    super.onCreate();
    playerNotificationManager =
        new PlayerNotificationManager(getApplicationContext(), Constants.MUSIC_CHANNEL, 200,
            new DescriptionAdapter());
    playerNotificationManager.setPriority(NotificationCompat.PRIORITY_LOW);
    initMediaSession();
    initializePlayer();
    initBroadReceiver();
  }

  private void initializePlayer() {
    if (exoPlayer == null) {
      exoPlayer = ExoPlayerFactory.newSimpleInstance(
          getApplicationContext(),
          new DefaultTrackSelector(),
          new DefaultLoadControl()
      );
      mediaSessionConnector.setPlayer(exoPlayer, null);
      exoPlayer.setPlayWhenReady(true);
      playerNotificationManager.setPlayer(exoPlayer);
      playerNotificationManager.setSmallIcon(R.mipmap.ic_logo);
      playerNotificationManager.setColor(getResources().getColor(R.color.colorPrimaryDark));
      playerNotificationManager.setColorized(true);
      playerNotificationManager.setMediaSessionToken(mediaSession.getSessionToken());
      playerNotificationManager.setPriority(NotificationCompat.PRIORITY_LOW);
    }
  }

  public ExoPlayer getExoplayer() {
    return exoPlayer;
  }

  private void initBroadReceiver() {
    final IntentFilter theFilter = new IntentFilter();
    theFilter.addAction(ACTION);
    musicControlReceiver = new MusicControlReceiver() {
      @Override public void onReceive(Context context, Intent intent) {

        String control = intent.getExtras().get("data").toString();
      }
    };
    this.registerReceiver(this.musicControlReceiver, theFilter);
  }

  @Override
  public IBinder onBind(Intent intent) {
    return mBinder;
  }

  @Override
  public void onStart(Intent intent, int startid) {

  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  public void setMusic(ArrayList<Songs> songs) {
    this.songs = songs;
    playSong();
  }

  public String getCurrestSong() {
    int index = exoPlayer.getCurrentWindowIndex();
    return songs.get(index).getTitle();
  }

  @Override
  public void onDestroy() {
    Toast.makeText(this, "Service Stopped and Music Stopped", Toast.LENGTH_LONG).show();
    playerNotificationManager.invalidate();
  }
}

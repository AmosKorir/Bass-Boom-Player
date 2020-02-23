package com.app.apic.mvp.androidtemplate.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;
import com.app.apic.domain.constants.Constants;
import java.util.List;

/**
 * Created by Korir on 9/30/19.
 * amoskrr@gmail.com
 */
public class PlayerService extends MediaBrowserServiceCompat {

  private static final String MY_MEDIA_ROOT_ID = "media_root_id";
  private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";

  private MediaSessionCompat mediaSession;
  private PlaybackStateCompat.Builder stateBuilder;

  @Override public void onCreate() {
    super.onCreate();

    mediaSession = new MediaSessionCompat(this, Constants.MUSIC_CHANNEL);

    mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

    // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
    stateBuilder = new PlaybackStateCompat.Builder()
        .setActions(
            PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PLAY_PAUSE);
    mediaSession.setPlaybackState(stateBuilder.build());

    // MySessionCallback() has methods that handle callbacks from a media controller
    mediaSession.setCallback(new MySessionCallback());

    // Set the session's token so that client activities can communicate with it.
    setSessionToken(mediaSession.getSessionToken());

  }




















  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    return super.onStartCommand(intent, flags, startId);
  }

  @Nullable @Override public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid,
      @Nullable Bundle rootHints) {
    return null;
  }

  @Override public void onLoadChildren(@NonNull String parentId,
      @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

  }
}

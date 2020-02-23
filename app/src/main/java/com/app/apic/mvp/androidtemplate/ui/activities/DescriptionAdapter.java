package com.app.apic.mvp.androidtemplate.ui.activities;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;

/**
 * Created by Korir on 10/8/19.
 * amoskrr@gmail.com
 */
class DescriptionAdapter implements PlayerNotificationManager.MediaDescriptionAdapter {
  @Override public String getCurrentContentTitle(Player player) {
    return null;
  }

  @Nullable @Override public PendingIntent createCurrentContentIntent(Player player) {
    return null;
  }

  @Nullable @Override public String getCurrentContentText(Player player) {
    return null;
  }

  @Nullable @Override public Bitmap getCurrentLargeIcon(Player player,
      PlayerNotificationManager.BitmapCallback callback) {
    return null;
  }
}

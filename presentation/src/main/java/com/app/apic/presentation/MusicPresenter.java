package com.app.apic.presentation;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.app.apic.data.utils.RxUtil;
import com.app.apic.domain.constants.DIConstants;
import com.app.apic.domain.models.Songs;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Korir on 9/15/19.
 * amoskrr@gmail.com
 */
public class MusicPresenter implements BasePresenter {
  private CompositeDisposable compositeDisposable;
  @Inject @Named(DIConstants.ACTIVITY) Context context;
  private MyView view;

  @Inject public MusicPresenter() {
  }

  public void setView(MyView view) {
    this.view = view;
  }

  @Override public void dispose() {
    compositeDisposable.add(compositeDisposable);
  }

  public void getMusic() {
    compositeDisposable = RxUtil.initDisposables(compositeDisposable);
    Disposable disposable = Single.just(musicSource())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(view::music, view::handleError);
    compositeDisposable.add(disposable);
  }

  private ArrayList<Songs> musicSource() {
    ArrayList<Songs> songs = new ArrayList<>();
    ContentResolver contentResolver = context.getContentResolver();
    Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

    if (songCursor != null && songCursor.moveToFirst()) {
      int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
      int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
      int fileSource = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
      int artistIn = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

      do {
        long currentId = songCursor.getLong(songId);
        String currentTitle = songCursor.getString(songTitle);
        String artist = songCursor.getString(artistIn);
        songs.add(Songs.newBuilder()
            .withArtist(artist)
            .withTitle(currentTitle)
            .withFile(new File(songCursor.getString(fileSource)))
            .build());
      } while (songCursor.moveToNext());
    }
    return songs;
  }

  public interface MyView extends View {
    void music(ArrayList<Songs> songs);
  }
}

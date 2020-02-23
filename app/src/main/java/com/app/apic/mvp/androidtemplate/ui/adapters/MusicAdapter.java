package com.app.apic.mvp.androidtemplate.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.apic.domain.models.Songs;
import com.app.apic.mvp.androidtemplate.R;
import java.util.ArrayList;

/**
 * Created by Korir on 9/15/19.
 * amoskrr@gmail.com
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
  private Context context;
  private ArrayList<Songs> songs = new ArrayList<>();
  private MusicAdapterInterface musicAdapterInterface;

  public MusicAdapter(Context context, ArrayList<Songs> songs) {
    this.context = context;
    this.songs = songs;
  }

  @NonNull @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
    return new MyViewHolder(view);
  }

  public MusicAdapterInterface getMusicAdapterInterface() {
    return musicAdapterInterface;
  }

  public void setMusicAdapterInterface(
      MusicAdapterInterface musicAdapterInterface) {
    this.musicAdapterInterface = musicAdapterInterface;
  }

  @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.bindView(songs.get(position));
    holder.itemView.setOnClickListener(v -> musicAdapterInterface.playerPosition(position));
  }

  @Override public int getItemCount() {
    return songs.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.songArtist) TextView artistTitle;
    @BindView(R.id.songTitle) TextView songTitle;

    public MyViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    private void bindView(Songs songs) {
      artistTitle.setText(songs.getArtist());
      songTitle.setText(songs.getTitle());
    }
  }

  //interface
  public interface MusicAdapterInterface {
    void playerPosition(int position);
  }
}

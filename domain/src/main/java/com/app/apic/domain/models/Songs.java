package com.app.apic.domain.models;

import java.io.File;

/**
 * Created by Korir on 9/15/19.
 * amoskrr@gmail.com
 */
public class Songs {
  private String title;
  private String artist;
  private File file;

  public Songs() {
  }

  private Songs(Builder builder) {
    setTitle(builder.title);
    setArtist(builder.artist);
    setFile(builder.file);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public static final class Builder {
    private String title;
    private String artist;
    private File file;

    private Builder() {
    }

    public Builder withTitle(String val) {
      title = val;
      return this;
    }

    public Builder withArtist(String val) {
      artist = val;
      return this;
    }

    public Builder withFile(File val) {
      file = val;
      return this;
    }

    public Songs build() {
      return new Songs(this);
    }
  }
}

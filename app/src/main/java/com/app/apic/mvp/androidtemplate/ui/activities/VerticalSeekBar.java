package com.app.apic.mvp.androidtemplate.ui.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatSeekBar;

/**
 * Created by Korir on 10/17/19.
 * amoskrr@gmail.com
 */
public class VerticalSeekBar extends AppCompatSeekBar {
  ProgressListerner progressListerner;
  int progress;

  public VerticalSeekBar(Context context) {
    super(context);
  }

  public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public VerticalSeekBar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(h, w, oldh, oldw);
  }

  public void setProgressListerner(
      ProgressListerner progressListerner) {
    this.progressListerner = progressListerner;
  }

  @Override
  protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(heightMeasureSpec, widthMeasureSpec);
    setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
  }

  protected void onDraw(Canvas c) {
    c.rotate(-90);
    c.translate(-getHeight(), 0);

    super.onDraw(c);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (!isEnabled()) {
      return false;
    }

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
      case MotionEvent.ACTION_UP:
        setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
        progress = getMax() - (int) (getMax() * event.getY() / getHeight());
        if (progressListerner != null) {
          progressListerner.progress(progress);
        }
        onSizeChanged(getWidth(), getHeight(), 0, 0);
        break;

      case MotionEvent.ACTION_CANCEL:
        break;
    }
    return true;
  }

  public interface ProgressListerner {
    void progress(int progress);
  }
}

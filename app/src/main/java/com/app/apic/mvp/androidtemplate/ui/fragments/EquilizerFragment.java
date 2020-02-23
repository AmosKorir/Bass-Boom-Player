package com.app.apic.mvp.androidtemplate.ui.fragments;

import android.graphics.Color;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.app.apic.mvp.androidtemplate.R;
import com.app.apic.mvp.androidtemplate.ui.activities.AnalogController;
import com.app.apic.mvp.androidtemplate.ui.activities.VerticalSeekBar;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class EquilizerFragment extends BaseDialogFragment {

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private View view;
  private BassBoost bassBoost;
  private PresetReverb presetReverb;
  private Equalizer equalizer;
  private int audioSessionId;
  private int MaxBassBoost = 0;
  private int preverbPriority = 0;

  @BindView(R.id.bands) LinearLayout holder;
  @BindView(R.id.bassController) AnalogController bassAnalog;
  @BindView(R.id.r3DController) AnalogController analogController;

  public EquilizerFragment() {
    // Required empty public constructor
  }

  public static EquilizerFragment newInstance(int audioSessionId) {
    EquilizerFragment fragment = new EquilizerFragment();
    Bundle args = new Bundle();
    args.putInt("audioSessionId", audioSessionId);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      audioSessionId = getArguments().getInt("audioSessionId");
    }
    injector().inject(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    view = super.onCreateView(inflater, container, savedInstanceState);
    if (view == null) {
      view = inflater.inflate(R.layout.fragment_equilizer, container, false);
      ButterKnife.bind(this, view);
    }
    return view;
  }

  @Override public void onStart() {
    super.onStart();
    equalizer = new Equalizer(100000, audioSessionId);
    setEquilizer();
    setAnalogControllers();
    setBassBoost();
    setPresetReverb();
  }

  private void setBassBoost() {
    bassBoost = new BassBoost(MaxBassBoost, audioSessionId);
    bassBoost.setEnabled(true);
    BassBoost.Settings bassBoostSettingTemp = bassBoost.getProperties();
    BassBoost.Settings bassBoostSetting = new BassBoost.Settings(bassBoostSettingTemp.toString());
    bassBoostSetting.strength = (1000 / 19);
    bassBoost.setProperties(bassBoostSetting);
  }

  private void setPresetReverb() {
    presetReverb = new PresetReverb(preverbPriority, audioSessionId);
    presetReverb.setPreset(PresetReverb.PRESET_NONE);
    presetReverb.setEnabled(true);
  }

  private void setEquilizer() {
    if (equalizer == null) {
      equalizer = new Equalizer(424875557, audioSessionId);
    }
    equalizer.setEnabled(true);
    short bandsNumber = equalizer.getNumberOfBands();
    short upperLevel = equalizer.getBandLevelRange()[1];
    short lowerLevel = equalizer.getBandLevelRange()[0];
    for (short i = 0; i < bandsNumber; i++) {
      final short index = i;
      LinearLayout linearLayout = new LinearLayout(context);
      linearLayout.setOrientation(LinearLayout.VERTICAL);
      // Gets the layout params that will allow you to resize the layout

      VerticalSeekBar seekBar = new VerticalSeekBar(context);

      seekBar.setProgress(20);
      seekBar.setMax(upperLevel - lowerLevel);
      seekBar.setProgress(equalizer.getBandLevel(i));

      short finalI = i;
      seekBar.setProgressListerner(progress -> {
        equalizer.setBandLevel(finalI, (short) (progress + lowerLevel));
        Toast.makeText(context, progress + "", Toast.LENGTH_SHORT).show();
      });

      TextView label = new TextView(context);
      label.setText(equalizer.getCenterFreq(i) / 100 + "Hz");
      label.setTextColor(Color.WHITE);

      linearLayout.addView(seekBar);
      linearLayout.addView(label);

      ViewGroup.LayoutParams layoutParams = seekBar.getLayoutParams();
      layoutParams.width = MATCH_PARENT;
      seekBar.setLayoutParams(layoutParams);

      holder.addView(linearLayout);

      ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
      params.width = WRAP_CONTENT;
      params.height = MATCH_PARENT;

      LinearLayout.LayoutParams layoutParams1 =
          (LinearLayout.LayoutParams) seekBar.getLayoutParams();
      layoutParams1.weight = 1.0f;

      LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) label.getLayoutParams();
      textParams.setMargins(5, 2, 5, 2);
      label.setBackgroundColor(context.getResources().getColor(R.color.backgroundColorTint));
      label.setLayoutParams(textParams);
      linearLayout.setBackgroundColor(context.getResources().getColor(R.color.backgroundColorTint));
      seekBar.setBackgroundColor(context.getResources().getColor(R.color.backgroundColorTint));
      holder.setBackgroundColor(context.getResources().getColor(R.color.backgroundColorTint));

      linearLayout.setLayoutParams(params);
      seekBar.setLayoutParams(params);
      seekBar.setLayoutParams(layoutParams1);
    }
  }

  private void setAnalogControllers() {
    bassAnalog.setLabel("BASS");
    analogController.setLabel("3D");
    bassAnalog.circlePaint2.setColor(context.getResources().getColor(R.color.colorAccent));
    bassAnalog.linePaint.setColor(context.getResources().getColor(R.color.colorAccent));
    bassAnalog.setLineColor(Color.RED);
    bassAnalog.invalidate();

    analogController.circlePaint2.setColor(context.getResources().getColor(R.color.colorAccent));
    analogController.linePaint.setColor(context.getResources().getColor(R.color.colorAccent));
    analogController.invalidate();

    analogController.setProgressColor(context.getResources().getColor(R.color.colorAccent));

    analogController.setOnProgressChangedListener(progress -> {
      bassBoost.setStrength((short) (((float) 1000 / 19) * (progress)));
    });
    bassAnalog.setOnProgressChangedListener(progress -> {
      try {
        presetReverb.setPreset((short) progress);
      } catch (Exception e) {

      }
    });
  }

  public void setAudioSession(int audioSessionId) {
    this.audioSessionId = audioSessionId;
  }
}

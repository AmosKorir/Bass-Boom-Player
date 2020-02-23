package com.app.apic.mvp.androidtemplate.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.app.apic.mvp.androidtemplate.R;

public class Splash extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    TextView textView = findViewById(R.id.bottomText);
    textView.setText(Html.fromHtml("<b>"
        + "<small><font color=\"green\">"
        + "  3D"
        + "</font></small>"

        + "<small><font color=\"#df077d\">"
        + "  Music"
        + "</font></small>"

        + "<small><font color=\"#dfbb07\">"
        + "  All day .."
        + "</small></font>"));
    Handler handler = new Handler();
    handler.postDelayed(() -> {
      startActivity(new Intent(Splash.this, MainActivity.class));
      finish();
    }, 3000);
  }

}

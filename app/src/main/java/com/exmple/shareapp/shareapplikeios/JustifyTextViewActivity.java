package com.exmple.shareapp.shareapplikeios;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.TextView;

import static android.R.attr.width;

public class JustifyTextViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justify_text_view);

        TextView textView = (TextView) findViewById(R.id.textView);


    }
}

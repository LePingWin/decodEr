package com.fougas.decoder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EndCallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_call);

        TextView aEndTvDuration = (TextView) findViewById(R.id.aEndTvDuration);


        aEndTvDuration.setText("39:25");
    }
}

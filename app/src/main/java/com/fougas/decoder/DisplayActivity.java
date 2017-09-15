package com.fougas.decoder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Button aDispBtnClose = (Button) findViewById(R.id.aDispBtnClose);
        Button aDispBtnParameters = (Button) findViewById(R.id.aDispBtnParameters);

        aDispBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnClose();
            }
        });

        aDispBtnParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnParameters();
            }
        });
    }

    /**
     * On click on the button close
     */
    private void onClickBtnClose() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * On click on the button parameters
     */
    private void onClickBtnParameters() {
        Intent intent = new Intent(this, ParameterActivity.class);
        startActivity(intent);
    }
}

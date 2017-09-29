package com.fougas.decoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Button aDispBtnClose = (Button) findViewById(R.id.aDispBtnClose);
        Button aDispBtnListen = (Button) findViewById(R.id.aDispBtnListen);
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
        aDispBtnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnListen();
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

    /**
     * On click on the button listen
     */
    private void onClickBtnListen() {
        // TODO Call listen from google
    }

    /**
     * Allow to fill the textview with a string
     * @param text The string that will be displayed
     */
    private void fillTextView(String text){
        TextView textView = (TextView) findViewById(R.id.aDispTvText);
        textView.setText(text);
    }
}

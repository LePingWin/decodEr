package com.fougas.decoder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

public class DisplayActivity extends Activity {

    private final int FILE_SELECT_CODE = 1;

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
        /*Intent intent = new Intent(this, ParameterActivity.class);
        startActivity(intent);*/

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*");
        startActivityForResult(intent, FILE_SELECT_CODE);
    }

    /**
     * On click on the button listen
     */
    private void onClickBtnListen() {
        // TODO Call listen from google
    }

    /**
     * Allow to fill the textview with a string
     *
     * @param text The string that will be displayed
     */
    private void fillTextView(String text) {
        TextView textView = (TextView) findViewById(R.id.aDispTvText);
        textView.setText(text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        // Check which request we're responding to
        if (requestCode == FILE_SELECT_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // User pick the file
                if (data != null) {
                    uri = data.getData();
                    fillTextView(readTextFile(uri));
                }

            } else {
                Log.i(TAG, data.toString());
            }
        }
    }

    private String readTextFile(Uri uri) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        String line = "";
        try {
            reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}

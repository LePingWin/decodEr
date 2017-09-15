package com.fougas.decoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import com.fougas.decoder.Model.GoogleCloudTranslate;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jean on 12/09/2017.
 */

public class TranslationActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private EditText aTransEtSourceText;
    private EditText aTransEtResponseText;
    private Button aTransBtnLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);
        aTransEtSourceText = (EditText) findViewById(R.id.aTransEtSourceText);
        aTransEtResponseText = (EditText) findViewById(R.id.aTransEtResponseText);
        aTransBtnLaunch = (Button) findViewById(R.id.aTransBtnLaunch);


        aTransBtnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString(getString(R.string.sharedPreferencesPath), aTransEtSourceText.getText().toString());
                //editor.apply();

                //Toast.makeText(v.getContext(), getString(R.string.aParaTvSaveNewPathSaved), Toast.LENGTH_SHORT).show();
                try {
                    aTransEtResponseText.setText(GoogleCloudTranslate.translation());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




}

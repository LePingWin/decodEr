package com.fougas.decoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Main Activity
 */
public class MainActivity extends Activity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);

        initSharedPreferences();
        initSpinner();

        Button aMainBtnHelp = (Button) findViewById(R.id.aMainBtnHelp);
        Button aMainBtnLaunchTranslation = (Button) findViewById(R.id.aMainBtnLaunchTranslation);
        Button aMainBtnParameters = (Button) findViewById(R.id.aMainBtnParameters);

        aMainBtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnHelp();
            }
        });
        aMainBtnLaunchTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnLaunchTranslation();
            }
        });
        aMainBtnParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnParameters();
            }
        });
    }

    /**
     * Init the shared preferences
     */
    private void initSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getString(getString(R.string.sharedPreferencesPath), "").isEmpty()) {
            editor.putString(getString(R.string.sharedPreferencesPath), "/decodErSaves"); // TODO pour choper la racine Environment.getExternalStorageDirectory().getPath() puis ce qu'il y a dans la variable path
            editor.apply();
        }
        if (sharedPreferences.getString(getString(R.string.sharedPreferencesTranslationLanguage), "").isEmpty()) {
            editor.putString(getString(R.string.sharedPreferencesTranslationLanguage), getResources().getStringArray(R.array.spLanguage)[0]);
            editor.apply();
        }
        if (sharedPreferences.getString(getString(R.string.sharedPreferencesInterlocutorLanguage), "").isEmpty()) {
            editor.putString(getString(R.string.sharedPreferencesInterlocutorLanguage), getResources().getStringArray(R.array.spLanguage)[0]);
            editor.apply();
        }
    }

    /**
     * On click on the button help
     */
    private void onClickBtnHelp() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    /**
     * On click on the button to launch translation
     */
    private void onClickBtnLaunchTranslation() {
        Intent intent = new Intent(this, DisplayActivity.class);
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
     * Init the spinner
     */
    private void initSpinner() {
        int spinnerPosition;
        Spinner aMainSpLanguage = (Spinner) findViewById(R.id.aMainSpLanguage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spLanguage, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aMainSpLanguage.setAdapter(adapter);

        spinnerPosition = adapter.getPosition(sharedPreferences.getString(getString(R.string.sharedPreferencesInterlocutorLanguage), ""));
        aMainSpLanguage.setSelection(spinnerPosition);

        aMainSpLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.sharedPreferencesInterlocutorLanguage), parentView.getSelectedItem().toString());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
}

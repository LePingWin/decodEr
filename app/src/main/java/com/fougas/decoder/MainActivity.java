package com.fougas.decoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Main Activity
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSavePath();
        fillSpinner();

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
     * Init the save path in the shared preferences
     */
    private void initSavePath() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);
        if (sharedPreferences.getString(getString(R.string.sharedPreferencesPath), "").isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.sharedPreferencesPath), "test");
            editor.apply();
        }
    }

    /**
     * On click on the button help
     */
    private void onClickBtnHelp() {
        Intent intent = new Intent(this, EndCallActivity.class);
        startActivity(intent);
    }

    /**
     * On click on the button to launch translation
     */
    private void onClickBtnLaunchTranslation() {

    }

    /**
     * On click on the button parameters
     */
    private void onClickBtnParameters() {
        Intent intent = new Intent(this, ParameterActivity.class);
        startActivity(intent);
    }

    /**
     * Fill the spinner with an Array String
     */
    private void fillSpinner() {
        Spinner aMainSpLanguage = (Spinner) findViewById(R.id.aMainSpLanguage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spLanguage, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aMainSpLanguage.setAdapter(adapter);
    }
}

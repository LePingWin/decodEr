package com.fougas.decoder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ParameterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);

        fillSpinner();
        initEvSavePath();
    }

    /**
     * Fill the spinner with an Array String
     */
    private void fillSpinner() {
        Spinner aParaSpLanguage = (Spinner) findViewById(R.id.aParaSpLanguage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spLanguage, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aParaSpLanguage.setAdapter(adapter);
    }

    /**
     * Init the EditText with the current save path
     */
    private void initEvSavePath() {
        EditText editText = (EditText) findViewById(R.id.aParaEvSavePath);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);
        editText.setText(sharedPreferences.getString(getString(R.string.sharedPreferencesPath), ""));
    }
}

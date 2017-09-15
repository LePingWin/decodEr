package com.fougas.decoder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class ParameterActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private EditText aParaEtSavePath;
    private Button aParaBtnSavePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);

        sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);
        aParaEtSavePath = (EditText) findViewById(R.id.aParaEtSavePath);
        aParaBtnSavePath = (Button) findViewById(R.id.aParaBtnSavePath);

        initSpinner();
        initEtSavePath();

        aParaEtSavePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide keyboard
                InputMethodManager inputManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                aParaBtnSavePath.setVisibility(View.VISIBLE);
            }
        });
        aParaBtnSavePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.sharedPreferencesPath), aParaEtSavePath.getText().toString());
                editor.apply();
                aParaBtnSavePath.setVisibility(View.INVISIBLE);
                Toast.makeText(v.getContext(), getString(R.string.aParaTvSaveNewPathSaved), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Init the spinner
     */
    private void initSpinner() {
        int spinnerPosition;
        Spinner aParaSpLanguage = (Spinner) findViewById(R.id.aParaSpLanguage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spLanguage, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aParaSpLanguage.setAdapter(adapter);
        spinnerPosition = adapter.getPosition(sharedPreferences.getString(getString(R.string.sharedPreferencesTranslationLanguage), ""));
        aParaSpLanguage.setSelection(spinnerPosition);

        aParaSpLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.sharedPreferencesTranslationLanguage), parentView.getSelectedItem().toString());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    /**
     * Init the EditText with the current save path
     */
    private void initEtSavePath() {
        aParaEtSavePath.setText(sharedPreferences.getString(getString(R.string.sharedPreferencesPath), ""));
    }
}

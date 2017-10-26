package com.fougas.decoder.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.fougas.decoder.R;
import com.fougas.decoder.Service.FileUtil;

public class ParameterActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private TextView aParaTvSavePathChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);
        aParaTvSavePathChosen = (TextView) findViewById(R.id.aParaTvSavePathChosen);
        Button aParaBtnChoosePath = (Button) findViewById(R.id.aParaBtnChoosePath);

        initSpinner();
        initTvChosePath();

        aParaBtnChoosePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                startActivityForResult(intent, 42);
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
    private void initTvChosePath() {
        aParaTvSavePathChosen.setText(sharedPreferences.getString(getString(R.string.sharedPreferencesPath), ""));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (resultCode == RESULT_OK) {
            Uri treeUri = resultData.getData();

            String path = FileUtil.getFullPathFromTreeUri(treeUri,this);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(getString(R.string.sharedPreferencesPath), path);
            editor.apply();
            initTvChosePath();
            Toast.makeText(getApplicationContext(),"New Path Chosen : "+path,Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }
}

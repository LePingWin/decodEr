package com.fougas.decoder.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.fougas.decoder.R;
import com.fougas.decoder.Service.Interface.IOnTaskCompleted;
import com.fougas.decoder.Service.TranslateService;

import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Jean on 15/09/2017.
 * Activity to show how does translation work and how to call TranslateService
 */
public class ExampleTranslationActivity extends Activity implements IOnTaskCompleted {

    private SharedPreferences sharedPreferences;
    private EditText aTransEtSourceText;
    private TextView aTransTvResponseAPIText;
    private Button aTransBtnLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_translation);

        sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);
        aTransEtSourceText = (EditText) findViewById(R.id.aTransEtSourceText);
        aTransTvResponseAPIText = (TextView) findViewById(R.id.aTransTvResponseAPIText);
        aTransBtnLaunch = (Button) findViewById(R.id.aTransBtnLaunch);

        final IOnTaskCompleted listener = this;

        aTransEtSourceText.setText("Hello");//TODO TEST
        aTransBtnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TranslateService taskGoogleAPITranslate =
                            new TranslateService(
                                    listener
                                    , aTransEtSourceText.getText().toString()
                                    , sharedPreferences.getString(getString(R.string.sharedPreferencesTranslationLanguage), ""));
                    taskGoogleAPITranslate.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Value return when task is completed
     * @param value Value return when task is completed
     */
    @Override
    public void onTaskCompleted(Object value) {
        aTransTvResponseAPIText.setText(value.toString());
    }
}

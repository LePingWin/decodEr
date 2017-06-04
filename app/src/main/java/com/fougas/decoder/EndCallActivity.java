package com.fougas.decoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class EndCallActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private Switch aEndSwSaveCall;
    private Switch aEndSwSaveTranscription;
    private Switch aEndSwSaveTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_call);

        sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);

        Button aEndBtnCancel = (Button) findViewById(R.id.aEndBtnCancel);
        Button aEndBtnValidate = (Button) findViewById(R.id.aEndBtnValidate);
        TextView aEndTvDuration = (TextView) findViewById(R.id.aEndTvDuration);
        aEndSwSaveCall = (Switch) findViewById(R.id.aEndSwSaveCall);
        aEndSwSaveTranscription = (Switch) findViewById(R.id.aEndSwSaveTranscription);
        aEndSwSaveTranslation = (Switch) findViewById(R.id.aEndSwSaveTranslation);


        aEndBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnCancel();
            }
        });
        aEndBtnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnValidate();
            }
        });
        aEndTvDuration.setText("39:25");

        aEndSwSaveCall.setChecked(sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveCall), false));
        aEndSwSaveTranscription.setChecked(sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveTranscription), false));
        aEndSwSaveTranslation.setChecked(sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveTranslation), false));
    }

    /**
     * On click on the button Cancel
     */
    private void onClickBtnCancel() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * On click on the button validate
     */
    private void onClickBtnValidate() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveCall), false) != aEndSwSaveCall.isChecked())
            editor.putBoolean(getString(R.string.sharedPreferencesSaveCall), aEndSwSaveCall.isChecked());
        if (sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveTranscription), false) != aEndSwSaveTranscription.isChecked())
            editor.putBoolean(getString(R.string.sharedPreferencesSaveTranscription), aEndSwSaveTranscription.isChecked());
        if (sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveTranslation), false) != aEndSwSaveTranslation.isChecked())
            editor.putBoolean(getString(R.string.sharedPreferencesSaveTranslation), aEndSwSaveTranslation.isChecked());
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

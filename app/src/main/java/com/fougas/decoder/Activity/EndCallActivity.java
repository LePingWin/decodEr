package com.fougas.decoder.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.fougas.decoder.R;
import com.fougas.decoder.Service.ServiceTxtFile;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;

public class EndCallActivity extends Activity {

    private SharedPreferences sharedPreferences;
    private Switch aEndSwSaveTranscription;
    private Switch aEndSwSaveTranslation;

    private String mTranscripted;
    private String mTranslated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTranscripted = getIntent().getStringExtra("TEXT_TRANSCRIPTED");
        mTranslated = getIntent().getStringExtra("TEXT_TRANSLATED");
        String callTime = getIntent().getStringExtra("DURATION");

        setContentView(R.layout.activity_end_call);

        sharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);

        Button aEndBtnCancel = (Button) findViewById(R.id.aEndBtnCancel);
        Button aEndBtnValidate = (Button) findViewById(R.id.aEndBtnValidate);
        TextView aEndTvDuration = (TextView) findViewById(R.id.aEndTvDuration);
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
        aEndTvDuration.setText(callTime);

        aEndSwSaveTranscription.setChecked(sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveTranscription), false));
        aEndSwSaveTranslation.setChecked(sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveTranslation), false));
    }

    /**
     * On click on the button Cancel
     */
    private void onClickBtnCancel() {
        finish();
    }

    /**
     * On click on the button validate
     */
    private void onClickBtnValidate() {

        String pathToSave = (sharedPreferences.getString(getString(R.string.sharedPreferencesPath), ""));

        DateTimeFormatter timeFormat = DateTimeFormat.forPattern("yyyy_MM_dd_hh_mm_ss");
        String dateToSave = timeFormat.print(DateTime.now());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveTranscription), false) != aEndSwSaveTranscription.isChecked()) {
            editor.putBoolean(getString(R.string.sharedPreferencesSaveTranscription), aEndSwSaveTranscription.isChecked());
        }
        if (sharedPreferences.getBoolean(getString(R.string.sharedPreferencesSaveTranslation), false) != aEndSwSaveTranslation.isChecked()) {
            editor.putBoolean(getString(R.string.sharedPreferencesSaveTranslation), aEndSwSaveTranslation.isChecked());
        }
        editor.apply();

        SaveEndCall(pathToSave, dateToSave);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void SaveEndCall(String pathToSave, String dateToSave) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != 1) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != 1) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if(aEndSwSaveTranslation.isChecked() || aEndSwSaveTranscription.isChecked()){
            File f = new File(pathToSave);
            if(!f.exists()) {
                Toast.makeText(getApplicationContext(), "You must select a valid path to save data", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ParameterActivity.class);
                startActivityForResult(intent,42);
            }
        }

        if(aEndSwSaveTranscription.isChecked()){
            String pathTranscript = ServiceTxtFile.getFilePath(pathToSave,dateToSave + "_Transcripted.txt");
            ServiceTxtFile.writeAndCreate(getApplicationContext(),pathTranscript,mTranscripted);

        }
        if(aEndSwSaveTranslation.isChecked()){
            String pathTranslate = ServiceTxtFile.getFilePath(pathToSave,dateToSave + "_Translated.txt");
            ServiceTxtFile.writeAndCreate(getApplicationContext(),pathTranslate,mTranslated);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

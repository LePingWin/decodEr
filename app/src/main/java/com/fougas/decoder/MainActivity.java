package com.fougas.decoder;

import android.app.Activity;
import android.content.Intent;
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

        Button aMainBtnHelp = (Button) findViewById(R.id.aMainBtnHelp);
        Button aMainBtnLaunchTranslation = (Button) findViewById(R.id.aMainBtnLaunchTranslation);
        Button aMainBtnParameters = (Button) findViewById(R.id.aMainBtnParameters);
        Spinner aMainSpLanguage = (Spinner) findViewById(R.id.aMainSpLanguage);

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
        fillSpinner(aMainSpLanguage);

    }

    /**
     * On click on the button help
     */
    private void onClickBtnHelp(){
        Intent intent = new Intent(this, EndCallActivity.class);
        startActivity(intent);
    }

    /**
     * On click on the button to launch translation
     */
    private void onClickBtnLaunchTranslation(){

    }

    /**
     * On click on the button parameters
     */
    private void onClickBtnParameters(){

    }

    /**
     * Fill the spinner with an Array String
     * @param spinner The spinner that need to be fill
     */
    private void fillSpinner(Spinner spinner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.aMainSpLanguage, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}

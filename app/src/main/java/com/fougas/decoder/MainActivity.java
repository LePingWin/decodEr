package com.fougas.decoder;

import android.app.Activity;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
=======
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.fougas.decoder.Model.APIWhatsMateTranslator;
import com.fougas.decoder.Model.APIYandexTranslatorTranslator;
import com.fougas.decoder.Model.Language;
import com.fougas.decoder.Model.ReadTxtFile;
>>>>>>> 408a95406f4a14477053113794f2332b35095fda

/**
 * Main Activity
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        // Fill the spinner
        Spinner spinner = (Spinner) findViewById(R.id.aMainSpLanguage);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.aMainSpLanguage, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
=======
        m_ed_txtToTranslate = (EditText) this.findViewById(R.id.et_txtToTranslate);
        //Run translation
        Resources res = getResources();
        String fileName = this.getFilesDir() + "/" + "fileTranslate.txt";
        launchTranslationWhatsMate(res);
        launchTranslationYandex(res);





    }

    protected void launchTranslationWhatsMate(final Resources res){
        m_tv_translationWhatsMate = (TextView) this.findViewById(R.id.tv_translationWhatsMate);
        m_btn_launchTranslationWhatsMate = (Button) this.findViewById(R.id.btn_launchTranslationWhatsMate);

        // Call the translation APIWhatsMate
        m_btn_launchTranslationWhatsMate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try
                {
                    m_tv_translationWhatsMate.setText( APIWhatsMateTranslator.translate(Language.ENGLISH,Language.FRENCH, ReadTxtFile.Read(res)));//m_ed_txtToTranslate.getText().toString()));
                }
                catch (Exception e)
                {

                    e.printStackTrace();
                    m_tv_translationWhatsMate.setText("Error with API WhatsMate of translation");
                }
            }
        });
    }

    protected void launchTranslationYandex(final Resources res){
        m_tv_translationYandex = (TextView) this.findViewById(R.id.tv_translationYandex);
        m_btn_launchTranslationYandex = (Button) this.findViewById(R.id.btn_launchTranslationYandex);

        // Call the translation APIWhatsMate
        m_btn_launchTranslationYandex.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try
                {
                    APIYandexTranslatorTranslator.setKey("trnsl.1.1.20170422T153755Z.d4d22f7b0e646ae8.eff9111fe0b3162ecb363e0a1a962604da10d7b8");
                    m_tv_translationYandex.setText((
                            APIYandexTranslatorTranslator.execute(
                                APIYandexTranslatorTranslator.execute( /*m_ed_txtToTranslate.getText().toString()*/ReadTxtFile.Read(res), Language.ENGLISH,Language.RUSSIAN)
                                , Language.RUSSIAN
                                , Language.FRENCH)));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    m_tv_translationYandex.setText("Error with API Yandex of translation" + e.toString());
                }
            }
        });
>>>>>>> 408a95406f4a14477053113794f2332b35095fda
    }

}

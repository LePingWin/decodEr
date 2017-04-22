package com.fougas.decoder;

import android.content.res.Resources;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.fougas.decoder.Model.APIWhatsMate_Translator;
import com.fougas.decoder.Model.APIYandexTranslator_Translator;
import com.fougas.decoder.Model.Language;
import com.fougas.decoder.Model.ReadTxtFile;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Members of this class
     */
    private EditText m_ed_txtToTranslate = null;
    // Yandex
    private TextView m_tv_translationYandex = null;
    private Button m_btn_launchTranslationYandex = null;
    // WhatsMate
    private TextView m_tv_translationWhatsMate = null;
    private Button m_btn_launchTranslationWhatsMate = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // Autorisation to connect to internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    m_tv_translationWhatsMate.setText( APIWhatsMate_Translator.translate(Language.ENGLISH,Language.FRENCH, ReadTxtFile.Read(res)));//m_ed_txtToTranslate.getText().toString()));
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
                    APIYandexTranslator_Translator.setKey("trnsl.1.1.20170422T153755Z.d4d22f7b0e646ae8.eff9111fe0b3162ecb363e0a1a962604da10d7b8");
                    m_tv_translationYandex.setText((
                            APIYandexTranslator_Translator.execute(
                                APIYandexTranslator_Translator.execute( /*m_ed_txtToTranslate.getText().toString()*/ReadTxtFile.Read(res), Language.ENGLISH,Language.RUSSIAN)
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
    }

}

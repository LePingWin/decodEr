package com.fougas.decoder;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.fougas.decoder.Model.APIWhatsMate_TranslatorConnected;

import java.util.ArrayList;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Members of this class
     */
    private TextView m_tv_translation = null;
    private Button m_btn_launchTranslation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // Autorisation to connect to internet
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Run translation
        launchTranslation();
    }

    protected void launchTranslation(){
        m_tv_translation = (TextView) this.findViewById(R.id.tv_translation);
        m_btn_launchTranslation = (Button) this.findViewById(R.id.btn_launchTranslation);

        // Call the translation APIWhatsMate
        m_btn_launchTranslation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try
                {
                    m_tv_translation.setText( APIWhatsMate_TranslatorConnected.translate("en","fr","I'm coming"));
                }
                catch (Exception e)
                {

                    e.printStackTrace();
                    m_tv_translation.setText("Error with API WhatsMate of translation");
                }
            }
        });
    }

}

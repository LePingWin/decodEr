package com.fougas.decoder.Service;

import android.os.AsyncTask;

import com.fougas.decoder.Service.Interface.IOnTaskCompleted;

// Imports the Google Cloud client library
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 * Created by Jean on 15/09/2017.
 * Service to translate a request, asyncTask
 */
public class TranslateService extends AsyncTask<Object, Object, Object> {
    private IOnTaskCompleted listener;
    private String mrequest;
    private static final String API_KEY = "API_KEY";//TODO set API_KEY and secure it

    /**
     * Constructor
     * @param listener of calling activity
     * @param request to translate
     */
    public TranslateService(IOnTaskCompleted listener, String request){
        this.listener=listener;
        this.mrequest=request;
    }

    /**
     * required method(s)
     */
    @Override
    protected Object doInBackground(Object... params) {
        String response;
        TranslateOptions options = TranslateOptions.newBuilder()
                .setApiKey(API_KEY)
                .build();
        Translate translate = options.getService();
        try{
            final Translation translation =
                translate.translate(mrequest,
                    Translate.TranslateOption.targetLanguage("fr"));//TODO set dynamically targetLanguage

            response = translation.getTranslatedText();
        }
        catch ( Exception e){
            response = "ERROR 400 API KEY not valid";//TODO change exception message
        }
        return response;
    }

    /**
     * When task is completed
     * @param o here response translated by Google API
     */
    protected void onPostExecute(Object o){
        listener.onTaskCompleted(o.toString());
    }
}

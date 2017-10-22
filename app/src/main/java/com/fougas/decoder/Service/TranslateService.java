package com.fougas.decoder.Service;

import android.os.AsyncTask;
import com.fougas.decoder.Service.Interface.IOnTaskCompleted;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

// Imports the Google Cloud client library

/**
 * Created by Jean on 15/09/2017.
 * Service to translate a request, asyncTask
 */
public class TranslateService extends AsyncTask<Object, Object, Object> {
    private IOnTaskCompleted listener;
    private String mrequest;
    private String mtargetLangage;
    private static final String API_KEY = "API_KEY";//TODO set API_KEY and secure it

    /**
     * Constructor
     * @param listener of calling activity
     * @param request to translate
     * @param targetLangage is the langage of translation
     */
    public TranslateService(IOnTaskCompleted listener, String request, String targetLangage){
        this.listener=listener;
        this.mrequest=request;
        this.mtargetLangage = targetLangage;
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
                    Translate.TranslateOption.targetLanguage(mtargetLangage));

            response = translation.getTranslatedText();
        }
        catch ( Exception e){
            response = "ERROR";//TODO change exception message
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

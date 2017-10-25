package com.fougas.decoder.Service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

// Imports the Google Cloud client library

/**
 * Created by Jean on 15/09/2017.
 * Service to translate a request, asyncTask
 */
public class TranslateService extends Service{
    private final ArrayList<Listener> mListeners = new ArrayList<>();
    private Binder mTranslateBinder = new TranslateBinder();
    private static final String API_KEY = "";//TODO set API_KEY and secure it

    public interface Listener {

        /**
         * Called when a new piece of text was recognized by the Translate API.
         *
         * @param text    The text.
         */
        void onTextTranslated(String text);

    }

    public static TranslateService from(IBinder binder) {
        return ((TranslateBinder) binder).getService();
    }

    public void addListener(@NonNull Listener listener) {
        mListeners.add(listener);
    }

    public void removeListener(@NonNull Listener listener) {
        mListeners.remove(listener);
    }

    public void Translate(String request,String targetLanguage) {
        String response = null;
        try {
            response = new TranslateTask().execute(request,targetLanguage).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for (Listener item : mListeners){
            item.onTextTranslated(response);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mTranslateBinder;
    }

    private class TranslateBinder extends Binder{
        TranslateService getService() {return TranslateService.this;}
    }

    private class TranslateTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String response;
            TranslateOptions options = TranslateOptions.newBuilder()
                    .setApiKey(API_KEY)
                    .build();
            Translate translate = options.getService();
            try{
                final Translation translation =
                        translate.translate(strings[0],
                                Translate.TranslateOption.targetLanguage(strings[1]));

                response = translation.getTranslatedText();
            }
            catch ( Exception e){
                response = e.getMessage();
            }
            return response;
        }
    }

}

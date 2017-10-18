package com.fougas.decoder.Service;

import android.widget.TextView;
import com.fougas.decoder.Service.Interface.IOnTaskCompleted;

public class TranslationTaskCompleted implements IOnTaskCompleted {

    private TextView txtViewToUpdate;

    public TranslationTaskCompleted(TextView t,String text){
        txtViewToUpdate = t;
        final IOnTaskCompleted listener = this;
        try {
            TranslateService taskGoogleAPITranslate =
                    new TranslateService(listener, text);
            taskGoogleAPITranslate.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Value return when task is completed
     * @param value Value return when task is completed
     */
    @Override
    public void onTaskCompleted(Object value) {
        txtViewToUpdate.setText(value.toString());
    }
}
package com.fougas.decoder.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.fougas.decoder.R;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Button aHelpBtnCancel = (Button) findViewById(R.id.aHelpBtnCancel);
        aHelpBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnCancel();
            }
        });
    }

    /**
     * On click on the button Cancel
     */
    private void onClickBtnCancel() {
        finish();
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }
}

package com.fougas.decoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndCallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_call);

        Button aEndBtnCancel = (Button) findViewById(R.id.aEndBtnCancel);
        Button aEndBtnValidate = (Button) findViewById(R.id.aEndBtnValidate);
        TextView aEndTvDuration = (TextView) findViewById(R.id.aEndTvDuration);

        aEndBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnCancel();
            }
        });
        aEndTvDuration.setText("39:25");
    }

    /**
     * On click on the button Cancel
     */
    private void onClickBtnCancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

package com.fougas.decoder.Activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.fougas.decoder.R;
import com.fougas.decoder.Service.SpeechService;
import com.fougas.decoder.Service.VoiceRecorder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DisplayActivity extends FragmentActivity implements MessageDialogFragment.Listener {
    private static final String FRAGMENT_MESSAGE_DIALOG = "message_dialog";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;
    private final int FILE_SELECT_CODE = 1;

    private SpeechService mSpeechService;

    private VoiceRecorder mVoiceRecorder;
    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {

        @Override
        public void onVoiceStart() {
            if (mSpeechService != null) {
                mSpeechService.startRecognizing(mVoiceRecorder.getSampleRate());
            }
        }

        @Override
        public void onVoice(byte[] data, int size) {
            if (mSpeechService != null) {
                mSpeechService.recognize(data, size);
            }
        }

        @Override
        public void onVoiceEnd() {
            if (mSpeechService != null) {
                mSpeechService.finishRecognizing();
            }
        }
    };
    // View references
    private TextView mText;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            mSpeechService = SpeechService.from(binder);
            mSpeechService.addListener(mSpeechServiceListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mSpeechService = null;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Button aDispBtnClose = (Button) findViewById(R.id.aDispBtnClose);
        Button aDispBtnListen = (Button) findViewById(R.id.aDispBtnListen);
        Button aDispBtnParameters = (Button) findViewById(R.id.aDispBtnParameters);

        aDispBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnClose();
            }
        });
        aDispBtnParameters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnParameters();
            }
        });
        aDispBtnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnListen();
            }
        });
        //TODO : implements button LOAD audio file
        //mSpeechService.recognizeInputStream(getResources().openRawResource(R.raw.audio));

        mText = (TextView) findViewById(R.id.text);

    }

    /**
     * On click on the button close
     */
    private void onClickBtnClose() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * On click on the button parameters
     */
    private void onClickBtnParameters() {
        Intent intent = new Intent(this, ParameterActivity.class);
        startActivity(intent);
    }

    /**
     * On click on the button listen
     */
    private void onClickBtnListen() {
        // Start listening to voices
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            startVoiceRecorder();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            showPermissionMessageDialog();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        // Check which request we're responding to
        if (requestCode == FILE_SELECT_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // User pick the file
                if (data != null) {
                    uri = data.getData();
                    fillTextView(readTextFile(uri));
                }

            } else {
                Log.i("DEBUG", data.toString());
            }
        }
    }

    private String readTextFile(Uri uri) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }


    /**
     * Allow to fill the textview with a string
     * @param text The string that will be displayed
     */
    private void fillTextView(String text){
        TextView textView = (TextView) findViewById(R.id.aDispTvText);
        textView.setText(text);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Prepare Cloud Speech API
        bindService(new Intent(this, SpeechService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        // Stop listening to voice
        stopVoiceRecorder();

        // Stop Cloud Speech API
        mSpeechService.removeListener(mSpeechServiceListener);
        unbindService(mServiceConnection);
        mSpeechService = null;

        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (permissions.length == 1 && grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startVoiceRecorder();
            } else {
                showPermissionMessageDialog();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.action_file:
                mSpeechService.recognizeInputStream(getResources().openRawResource(R.raw.audio));
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder = new VoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }

    private void stopVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
            mVoiceRecorder = null;
        }
    }

    private void showPermissionMessageDialog() {
        MessageDialogFragment
               .newInstance(getString(R.string.permission_message))
              .show(getSupportFragmentManager(), FRAGMENT_MESSAGE_DIALOG);
    }

    @Override
    public void onMessageDialogDismissed() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                REQUEST_RECORD_AUDIO_PERMISSION);
    }

    private final SpeechService.Listener mSpeechServiceListener =
            new SpeechService.Listener() {
                @Override
                public void onSpeechRecognized(final String text, final boolean isFinal) {
                    if (isFinal) {
                        mVoiceRecorder.dismiss();
                    }
                    if (mText != null && !TextUtils.isEmpty(text)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinal) {
                                    mText.setText(null);
                                } else {
                                    mText.setText(text);
                                    Log.d("TRAD",text);

                                }

                            }
                        });
                    }
                }
            };

}

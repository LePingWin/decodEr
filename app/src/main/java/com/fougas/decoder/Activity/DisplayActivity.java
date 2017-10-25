package com.fougas.decoder.Activity;

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.fougas.decoder.Model.Langage;
import com.fougas.decoder.R;
import com.fougas.decoder.Service.SpeechService;
import com.fougas.decoder.Service.TranslateService;
import com.fougas.decoder.Service.VoiceRecorder;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DisplayActivity extends FragmentActivity implements MessageDialogFragment.Listener  {
    private static final String FRAGMENT_MESSAGE_DIALOG = "message_dialog";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;
    private final int FILE_SELECT_CODE = 1;

    private SharedPreferences msharedPreferences ;

    private DateTime mStartTime = DateTime.now();

    private SpeechService mSpeechService;
    private TranslateService mTranslateService;

    private VoiceRecorder mVoiceRecorder;

    private String mTranscriptionLanguageCode;
    private String mTranslationLanguageCode;

    private final StringBuilder mTranslatedText = new StringBuilder();
    private final StringBuilder mTranscriptedText = new StringBuilder();

    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {

        @Override
        public void onVoiceStart() {
            if (mSpeechService != null) {
                mSpeechService.startRecognizing(mVoiceRecorder.getSampleRate(),mTranscriptionLanguageCode);
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

    private final ServiceConnection mTranslateServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mTranslateService = TranslateService.from(service);
            mTranslateService.addListener(mTranslateServiceListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mTranslateService = null;
        }
    };

    private final ServiceConnection mSpeechServiceConnection = new ServiceConnection() {

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
        Button aDispBtnParameters = (Button) findViewById(R.id.aDispBtnParameters);

        msharedPreferences = getSharedPreferences(getString(R.string.appName), Context.MODE_PRIVATE);
        mTranscriptionLanguageCode = Langage.getELanguage((msharedPreferences.getString(getString(R.string.sharedPreferencesInterlocutorLanguage),"")));
        mTranslationLanguageCode = Langage.getELanguage((msharedPreferences.getString(getString(R.string.sharedPreferencesTranslationLanguage),"")));


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

        mText = (TextView) findViewById(R.id.aDispTvText);

        Listen();
    }

    /**
     * On click on the button close
     */
    private void onClickBtnClose() {
        Intent intent = new Intent(this, EndCallActivity.class);
        intent.putExtra("TEXT_TRANSLATED", mTranslatedText.toString());
        intent.putExtra("TEXT_TRANSCRIPTED", mTranscriptedText.toString());
        Duration finalTime = new Duration(mStartTime,DateTime.now());
        String time = String.format("%02d:%02d:%02d",finalTime.getStandardHours(),finalTime.getStandardMinutes(),finalTime.getStandardSeconds());
        intent.putExtra("DURATION",time);
        startActivity(intent);
    }

    /**
     * On click on the button parameters
     */
    private void onClickBtnParameters() {
        mSpeechService.recognizeInputStream(getResources().openRawResource(R.raw.audio),mTranscriptionLanguageCode);
    }

    private void Listen() {
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

    private String readTextFile(Uri uri) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri), StandardCharsets.UTF_8));
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


    @Override
    protected void onStart() {
        super.onStart();
        // Prepare Cloud Speech API
        bindService(new Intent(this, SpeechService.class), mSpeechServiceConnection, BIND_AUTO_CREATE);
        bindService(new Intent(this, TranslateService.class), mTranslateServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        // Stop listening to voice
        stopVoiceRecorder();

        // Stop Cloud Speech API
        mSpeechService.removeListener(mSpeechServiceListener);
        unbindService(mSpeechServiceConnection);
        mSpeechService = null;

        // Stop Cloud Translate API
        mTranslateService.removeListener(mTranslateServiceListener);
        unbindService(mTranslateServiceConnection);
        mTranslateService = null;

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
                    if (isFinal && mVoiceRecorder != null) {
                        mVoiceRecorder.dismiss();
                    }
                    if (mText != null && !TextUtils.isEmpty(text)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinal) {
                                    try {
                                        mTranscriptedText.append(text);
                                        mTranscriptedText.append("\n");
                                        mTranslateService.Translate(text,mTranslationLanguageCode);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });
                    }
                }
            };

    private final TranslateService.Listener mTranslateServiceListener = new TranslateService.Listener() {
        @Override
        public void onTextTranslated(final String text) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        mTranslatedText.append(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)).toString();
                    } else {
                        mTranslatedText.append(Html.fromHtml(text).toString());
                    }
                    mTranslatedText.append("\n");
                    mText.setText(null);
                    mText.setText(mTranslatedText);
                }
            });
        }
    };

}

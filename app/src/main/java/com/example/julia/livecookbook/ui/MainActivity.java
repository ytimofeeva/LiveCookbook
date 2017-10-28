package com.example.julia.livecookbook.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julia.livecookbook.R;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {


    private int RECOGNIZER_REQUEST_CODE = 110;
    private int RECORD_AUDIO_PERMISSION_CODE = 102;
    private Button startButton;
    private Button playButton;
    private ImageView imageViewStartRecord;
    private TextView recognitionResults;

    private MainPresenter recognitionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.start);
        playButton = (Button) findViewById(R.id.play);
        imageViewStartRecord = (ImageView) findViewById(R.id.iv_start_record);
        recognitionResults = (TextView) findViewById(R.id.tv_result);
        playButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        recognitionPresenter = new MainPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recognitionPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        recognitionPresenter.detachView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.start:

        if (ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO) != PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
            } else {

            }
        } else {
            recognitionPresenter.startRecognition();
        }
        break;
            case R.id.play:
                recognitionPresenter.startVocalization();
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.length == 1 &&  grantResults[0] == PERMISSION_GRANTED) {
                recognitionPresenter.startRecognition();
            }
        }
    }

    @Override
    public void onStartRecordAudio() {
        final TypedValue value = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        imageViewStartRecord.setBackgroundColor(value.data);

    }

    @Override
    public void onPartialResult(String message) {
        recognitionResults.setText(message);
    }

    @Override
    public void onStopRecognition() {
        final TypedValue value = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        imageViewStartRecord.setBackgroundColor(value.data);
    }
}

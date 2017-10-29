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
import android.widget.Toast;

import com.example.julia.livecookbook.R;

import java.util.List;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {


    private int RECOGNIZER_REQUEST_CODE = 110;
    private int RECORD_AUDIO_PERMISSION_CODE = 102;
    private Button startButton;
    private Button playButton;
    private Button stepButton;
    private Button saveButton;

    private ImageView imageViewStartRecord;
    private TextView recognitionResults;
    private TextView previousStepsTextView;

    private MainPresenter recognitionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.start);
        playButton = (Button) findViewById(R.id.play);
        stepButton = (Button) findViewById(R.id.step);
        saveButton = (Button) findViewById(R.id.save);
        imageViewStartRecord = (ImageView) findViewById(R.id.iv_start_record);
        recognitionResults = (TextView) findViewById(R.id.tv_result);
        previousStepsTextView = (TextView) findViewById(R.id.tv_prev_steps);
        playButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        stepButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
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
            case R.id.step:
                recognitionPresenter.nextStep();
                break;
            case R.id.save:
                recognitionPresenter.saveReceipe();
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

    @Override
    public void showPreviousSteps(List<String> data) {
        String result = null;
        for (String s: data) {
            result += s  + "\n\n";
        }
        previousStepsTextView.setText(result);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

package com.example.julia.livecookbook.data.recognition;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;
import timber.log.Timber;

/**
 * Created by julia on 27.10.17.
 */

public class RecognitionRepositoryImpl implements RecognitionRepository, RecognizerListener, VocalizerListener {

    private Recognizer recognizer;
    private Vocalizer vocalizer;
    private PublishSubject<String> partialRecognozedString;

    @Override
    public Observable<String> observePartialRecognition() {
        return partialRecognozedString;
    }

    @Override
    public void startRecognitionRequest() {
        recognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, this);
        partialRecognozedString = PublishSubject.create();
        try {
            recognizer.start();
        } catch (SecurityException e) {

        }

    }

    @Override
    public void startVocalizationRequest(String text) {
        vocalizer = Vocalizer.createVocalizer(Vocalizer.Language.RUSSIAN, text, true, Vocalizer.Voice.ALYSS);
        vocalizer.setListener(this);
        vocalizer.start();
    }

    @Override
    public void onRecordingBegin(Recognizer recognizer) {
        Timber.d("onRecordingBegin");

    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {
        Timber.d("onSpeechDetected");
    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {
        Timber.d("onSpeechEnds");
    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
        Timber.d("onRecordingDone");

    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {
        Timber.d("onSoundDataRecorded");
    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {
        Timber.d("onPowerUpdated");
    }

    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {

        partialRecognozedString.onNext(recognition.getBestResultText());

        Timber.d("onPartialResults " + recognition.getBestResultText());
    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
        Timber.d("onRecognitionDone");

        partialRecognozedString.onComplete();

    }

    @Override
    public void onError(Recognizer recognizer, Error error) {
        Timber.d("onError " + error.getString());

        partialRecognozedString.onError(new Throwable(error.getString()));

    }

    @Override
    public void onSynthesisBegin(Vocalizer vocalizer) {
        Timber.d("onSynthesisBegin ");
    }

    @Override
    public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {
        Timber.d("onSynthesisDone");
    }

    @Override
    public void onPlayingBegin(Vocalizer vocalizer) {
        Timber.d("oonPlayingBegin");
    }

    @Override
    public void onPlayingDone(Vocalizer vocalizer) {
        Timber.d("oonPlayingDone");
    }

    @Override
    public void onVocalizerError(Vocalizer vocalizer, Error error) {
        Timber.d("onError " + error.getString());
    }
}

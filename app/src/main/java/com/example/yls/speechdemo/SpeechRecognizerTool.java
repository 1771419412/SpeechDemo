package com.example.yls.speechdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import com.baidu.speech.VoiceRecognitionService;

/**
 * Created by 雪无痕 on 2017/3/30.
 */

public class SpeechRecognizerTool implements RecognitionListener{
    private Context mContext;
    private ResultsCallback mResultsCallback;
    public interface ResultsCallback {
        void onResults(String result);
    }

    public SpeechRecognizerTool(Context context) {
        mContext = context;
    }
    private SpeechRecognizer mSpeechSynthesizer1;
    public synchronized void createTool() {
        if (null == mSpeechSynthesizer1) {

            // 创建识别器
            mSpeechSynthesizer1 = SpeechRecognizer.createSpeechRecognizer(mContext,
                    new ComponentName(mContext, VoiceRecognitionService.class));

            // 注册监听器
            mSpeechSynthesizer1.setRecognitionListener(this);
        }
    }

    public void startASR(ResultsCallback callback) {
        mResultsCallback = callback;

        Intent intent = new Intent();
        bindParams(intent);
        mSpeechSynthesizer1.startListening(intent);
    }



    public void stopASR() {
        mSpeechSynthesizer1.stopListening();
    }
    public synchronized void destroyTool() {
        mSpeechSynthesizer1.stopListening();
        mSpeechSynthesizer1.destroy();
        mSpeechSynthesizer1 = null;
    }

    private void bindParams(Intent intent) {
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Toast.makeText(mContext, "请开始说话", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        // 最终结果处理
        if(mResultsCallback!=null){
            String text=  results.get(SpeechRecognizer.RESULTS_RECOGNITION).toString().replace("]","").replace("[","");
            mResultsCallback.onResults(text);

        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}

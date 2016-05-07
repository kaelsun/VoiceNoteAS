package com.zyguo.voicenote;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.iflytek.cloud.SpeechUtility;
import com.zyguo.voicenote.tools.Messenger;
import com.zyguo.voicenote.tools.VoiceRecogEng;
import com.zyguo.voicenote.view.VoiceNoteRecordAnimationFragment;

public class MainActivity extends FragmentActivity implements Handler.Callback, VoiceRecogEng.IVoiceRecogCallbk{

    // constants start
    public static final int MAIN_HANDLER_START_RECORD = 10;
    public static final int MAIN_HANDLER_STOP_RECORD = 11;
    public static final int MAIN_HANDLER_CANCEL_RECORD = 12;
    // constants end

    private Handler mHandler;

    VoiceRecogEng mVoiceRecogEng = VoiceRecogEng.getInstance();

    VoiceNoteRecordAnimationFragment recordDialog = new VoiceNoteRecordAnimationFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public Handler getMainActivityHandler() {
        return mHandler;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVoiceRecogEng.unInitialize();
        Messenger.getInstance().release();
    }

    private void initController() {
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.ifly_app_id));
        mHandler = new Handler(this);
        mVoiceRecogEng.initialize(this, this);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MAIN_HANDLER_START_RECORD:
                mVoiceRecogEng.voiceRecognizeStart(this);
                recordDialog.show(getSupportFragmentManager(), recordDialog.getClass().getName());
                break;
            case MAIN_HANDLER_STOP_RECORD:
                mVoiceRecogEng.voiceRecognizeStop();
                recordDialog.dismiss();
                break;
            case MAIN_HANDLER_CANCEL_RECORD:
                mVoiceRecogEng.voiceRecognizeCancel();
                recordDialog.dismiss();
                break;
        }
        return true;
    }

    @Override
    public void onEvent(int type, Object obj1, Object obj2) {
        switch (type) {
            case VoiceRecogEng.IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_INIT_ERROR:
                Toast.makeText(this, "初始化失败", Toast.LENGTH_SHORT).show();
                break;
            case VoiceRecogEng.IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_START_LISTEN_ERROR:
                Toast.makeText(this, "识别未成功开始", Toast.LENGTH_SHORT).show();
                break;
            case VoiceRecogEng.IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_RECOGLIZE_ERROR:
                Toast.makeText(this, "" + obj1, Toast.LENGTH_SHORT).show();
                break;
            case VoiceRecogEng.IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_RECOGLIZE_RESULT:
                Toast.makeText(this, "" + obj1+obj2+"", Toast.LENGTH_SHORT).show();
                break;
            case VoiceRecogEng.IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_MEMORY_NOT_AVAILIABLE:
                Toast.makeText(this, "内存不足，请检查手机内存", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

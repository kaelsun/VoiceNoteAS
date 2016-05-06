package com.zyguo.voicenote.tools;

/******************************************************************************************
 * @file VoiceRecogEng.java
 * @brief 语音识别和录音引擎
 * @CodeHistory: [2015-7-30] terry initial version
 * [2015-8-10] pcm 转 wav
 * @CodeReview:
 *********************************************************************************************/

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.zyguo.voicenote.global.ErrCode;

import java.io.File;

public class VoiceRecogEng {

    /***
     * @brief This interface is callback to up layer for voiceRecoglize
     *
     */
    public static interface IVoiceRecogCallbk {
        /***
         * 初始化失败
         */
        int VOICE_RECOGNLIZE_CALLBACK_TYPE_INIT_ERROR = 0;
        /***
         * 未成功开启监听
         */
        int VOICE_RECOGNLIZE_CALLBACK_TYPE_START_LISTEN_ERROR = 1;
        /****
         * 识别失败或未监听到说话（有附件，字符串类型(obj1)）
         */
        int VOICE_RECOGNLIZE_CALLBACK_TYPE_RECOGLIZE_ERROR = 2;
        /****
         * 识别结果（有附件，字符串类型((obj1,识别结果，obj2,录音文件路径))）
         */
        int VOICE_RECOGNLIZE_CALLBACK_TYPE_RECOGLIZE_RESULT = 3;

        /***
         * 内存不可用
         */
        int VOICE_RECOGNLIZE_CALLBACK_TYPE_MEMORY_NOT_AVAILIABLE = 4;

        void onEvent(int type, Object obj1, Object obj2);
    }

    private final static String LOGTAG = "[VoiceRecogEng]";

    private static VoiceRecogEng voiceRecogEng = new VoiceRecogEng();

    private SpeechRecognizer mSpeechRecognizer; // 语音识别类
    private InitListener mInitListener; // 初始化监听
    private RecognizerListener mRecognizerListener;

    private IVoiceRecogCallbk mIVoiceRecogCallbk;
    private Handler handler;
    private String mRecoderFilePath;

    private VoiceRecogEng() {
        Log.i(LOGTAG, "==>VoiceRecogEng::constructor<>::VoiceRecogEng init");
        handler = new Handler(Looper.getMainLooper());
        mInitListener = new InitListener() {

            @Override
            public void onInit(int code) {
                Log.i(LOGTAG, "Thread:" + Thread.currentThread().getName() + "==>VoiceRecogEng::SpeechRecognizer::InitListener::onInit()::result:" + code);
                if (code != ErrorCode.SUCCESS) {
                    notifyUpLayer(IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_INIT_ERROR, null, null);
                }
            }
        };
        mRecognizerListener = new RecognizerListener() {

            @Override
            public void onVolumeChanged(int i) {

            }

            @Override
            public void onBeginOfSpeech() {
                Log.i(LOGTAG, "VoiceRecogEng::RecognizerListener::onBeginOfSpeech()");
            }

            @Override
            public void onError(SpeechError error) {
                Log.i(LOGTAG, "Thread:" + Thread.currentThread().getName() + "==>VoiceRecogEng::SpeechRecognizer::RecognizerListener::onError()::" + error);
                synchronized (VoiceRecogEng.this) {
                    notifyUpLayer(IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_RECOGLIZE_ERROR, error.getErrorDescription(), null);
                }
            }

            @Override
            public void onEndOfSpeech() {
                Log.i(LOGTAG, "VoiceRecogEng::RecognizerListener::onEndOfSpeech()");
            }

            private StringBuilder sb = new StringBuilder();

            @Override
            public void onResult(RecognizerResult results, boolean isLast) {
                String text = JsonParser.parseIatResult(results.getResultString());
                sb.append(text);
                if (isLast) {
                    String resultText = sb.toString();
                    String reg = "^.+[？，。！!,.?]$";
                    if (resultText.matches(reg)) {
                        resultText = resultText.substring(0, resultText.length() - 1).trim();
                    }
                    Log.i(LOGTAG, "Thread:" + Thread.currentThread().getName() + "==>VoiceRecogEng::SpeechRecognizer::RecognizerListener::onResult()::" + resultText);
                    synchronized (VoiceRecogEng.this) {
                        notifyUpLayer(IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_RECOGLIZE_RESULT, resultText, mRecoderFilePath);
                    }
                    sb.delete(0, sb.length());
                }
            }

            @Override
            public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

            }
        };
    }

    public static VoiceRecogEng getInstance() {
        return voiceRecogEng;
    }

    /*****
     *
     * @brief initialize order VoiceRecogEng
     * @param voiceRecogCallbk
     *            recognize results callback
     */
    public synchronized int initialize(Context context, IVoiceRecogCallbk voiceRecogCallbk) {

        if (mSpeechRecognizer != null) {
            mSpeechRecognizer.destroy();
        }
        mIVoiceRecogCallbk = voiceRecogCallbk;
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(context, mInitListener);
        Log.i(LOGTAG, "==>VoiceRecogEng::initialize()");
        return ErrCode.XERR_NONE;
    }

    /*****
     *
     * @brief unInitialize order VoiceRecogEng,release resource
     *
     */
    public synchronized int unInitialize() {
        try {
            if (mSpeechRecognizer != null) {
                mSpeechRecognizer.destroy();
            }
        } catch (Exception e) {
        }
        mIVoiceRecogCallbk = null;
        mSpeechRecognizer = null;
        Log.i(LOGTAG, "==>VoiceRecogEng::unInitialize()::release resource");
        return ErrCode.XERR_NONE;
    }

    /***
     *
     * @brief start voice recognize and audio recode
     */
    public synchronized int voiceRecognizeStart(Context context) {
        Log.i(LOGTAG, "==>VoiceRecogEng::voiceRecognizeStart()::Enter");
        if (mSpeechRecognizer == null) {
            Log.e(LOGTAG, "==>VoiceRecogEng::voiceRecognizeStart()::engine not init or init fail!!!");
            return ErrCode.XERR_BAD_STATE;
        }

        if (mSpeechRecognizer.isListening()) {
            Log.w(LOGTAG, "==>VoiceRecogEng::voiceRecognizeStart()::engine is listening ");
            return ErrCode.XERR_BAD_STATE;
        }
        int res = createPcmFile(context);
        if (res != ErrCode.XERR_NONE) {
            Log.e(LOGTAG, "==>VoiceRecogEng::voiceRecognizeStart()::create file error!!!");
            return ErrCode.XERR_BAD_STATE;
        }
        setParam();
        int ret = mSpeechRecognizer.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            Log.e(LOGTAG, "==>VoiceRecogEng::voiceRecognizeStart()::listening fail:" + ret);
            notifyUpLayer(IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_START_LISTEN_ERROR, null, null);
            return ErrCode.XERR_BAD_STATE;
        }
        return ErrCode.XERR_NONE;
    }

    public synchronized boolean recognizeIsListening() {
        if (mSpeechRecognizer == null)
            return false;
        return mSpeechRecognizer.isListening();
    }

    /***
     * @brief 创建保存pcm数据的文件夹，以及判断是不是可能保存文件到sd中
     * @param context
     * @return
     */
    private int createPcmFile(Context context) {
        boolean isExternalAvailable = false;
        boolean isInternalAvailable = false;
        long size = 10l * 1024 * 1024;
        if (FileSizeUtil.externalMemoryAvailable()) {
            long externalAvailaSize = FileSizeUtil.getAvailableExternalMemorySize();
            Log.i(LOGTAG, "==>VoiceRecogEng::createPcmFile()::sdcard available size:" + externalAvailaSize / 1024 / 1024 + "MB");
            if (externalAvailaSize >= size) {
                isExternalAvailable = true;
            } else {
                long internalAvailaSize = FileSizeUtil.getAvailableInternalMemorySize();
                Log.i(LOGTAG, "==>VoiceRecogEng::createPcmFile()::internal available size:" + internalAvailaSize / 1024 / 1024 + "MB");
                if (internalAvailaSize >= size) {
                    isInternalAvailable = true;
                }
            }
        } else {
            long internalAvailaSize = FileSizeUtil.getAvailableInternalMemorySize();
            Log.i(LOGTAG, "==>VoiceRecogEng::createPcmFile()::internal available size:" + internalAvailaSize / 1024 / 1024 + "MB");
            if (internalAvailaSize >= size) {
                isInternalAvailable = true;
            }
        }
        try {
            String fileName = "/voice_" + System.currentTimeMillis();
            if (isExternalAvailable) {
                try {
                    mRecoderFilePath = context.getExternalCacheDir().getAbsolutePath() + fileName;
                } catch (Exception e) {
                    mRecoderFilePath = context.getCacheDir().getAbsoluteFile() + fileName;
                }
            } else if (isInternalAvailable) {
                mRecoderFilePath = context.getCacheDir().getAbsoluteFile() + fileName;
            } else {
                Log.e(LOGTAG, "==>VoiceRecogEng::createPcmFile()::Memory nAvailaSize not enough!!");
                notifyUpLayer(IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_MEMORY_NOT_AVAILIABLE, null, null);
                return ErrCode.XERR_BAD_STATE;
            }

            File file = new File(mRecoderFilePath);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ErrCode.XERR_BAD_STATE;
        }
        Log.i(LOGTAG, "==>VoiceRecogEng::createPcmFile()::recoder path:" + mRecoderFilePath);
        return ErrCode.XERR_NONE;
    }

    /***
     *
     * @brief stop voice recognize and save recode file ,results notify by
     *        callback (IVoiceRecogCallbk) function
     *
     */
    public synchronized int voiceRecognizeStop() {
        Log.i(LOGTAG, "==>VoiceRecogEng::voiceRecognizeStop()::Enter");
        if (mSpeechRecognizer == null) {
            Log.e(LOGTAG, "==>VoiceRecogEng::voiceRecognizeStop()::engine not init or init fail!!!");
            return ErrCode.XERR_NONE;
        }
        if (!mSpeechRecognizer.isListening()) {
            Log.w(LOGTAG, "==>VoiceRecogEng::voiceRecognizeStop()::is not listening");
            return ErrCode.XERR_NONE;
        }
        mSpeechRecognizer.stopListening();
        return ErrCode.XERR_NONE;
    }

    /***
     *
     * @brief cancel recognize
     *
     */
    public synchronized int voiceRecognizeCancel() {
        Log.i(LOGTAG, "==>VoiceRecogEng::voiceRecognizeCancel()::Enter");
        if (mSpeechRecognizer == null) {
            Log.e(LOGTAG, "==>VoiceRecogEng::voiceRecognizeCancel()::engine not init or init fail!!!");
            return ErrCode.XERR_NONE;
        }
        mSpeechRecognizer.cancel();
        return ErrCode.XERR_NONE;
    }

    private void notifyUpLayer(final int type, final Object obj1, final Object obj2) {
        handler.post(new Runnable() {
            public void run() {
                synchronized (VoiceRecogEng.this) {
                    if (mIVoiceRecogCallbk != null) {
                        mIVoiceRecogCallbk.onEvent(type, obj1, obj2);
                        if (type == IVoiceRecogCallbk.VOICE_RECOGNLIZE_CALLBACK_TYPE_INIT_ERROR) {
                            unInitialize();
                        }
                    }
                }
            }
        });

    }

    private void setParam() {
        // 清空参数
        mSpeechRecognizer.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mSpeechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语言
        mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        // }
        // 设置语音前端点
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_BOS, "10000");
        // 设置语音后端点
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_EOS, "10000");
        // 设置标点符号
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_PTT, "0");
        mSpeechRecognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, mRecoderFilePath);
        //mSpeechRecognizer.setParameter(SpeechConstant.ASR_DWA, "0");
    }

}

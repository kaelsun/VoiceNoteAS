package com.zyguo.voicenote.view;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyguo.voicenote.R;
import com.zyguo.voicenote.base.BaseFragment;
import com.zyguo.voicenote.tools.Messenger;

public class VoiceNoteBodyFragment extends BaseFragment{

    public static final int BODY_HANDLER_ONRESULT = 20;

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_body, container, false);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Messenger.getInstance().registerHandler(this.getClass().getName(), mHandler);
    }
    
    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void onPause() {
        super.onPause();
    }
    
    @Override
    public void onStop() {
        super.onStop();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Messenger.getInstance().unRegisterHandler(this.getClass().getName());
    }

    @Override
    public boolean handleMessage(Message message) {
        return true;
    }
}

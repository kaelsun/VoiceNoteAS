package com.zyguo.voicenote.view;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Chronometer;

import com.zyguo.voicenote.R;

public class VoiceNoteRecordAnimationFragment extends DialogFragment{

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_talk_record, container, false);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Chronometer chronometer = (Chronometer) getView().findViewById(R.id.talk_animation_chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
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
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

}

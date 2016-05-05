package com.zyguo.voicenote.view;

import com.zyguo.voicenote.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VoiceNoteRecordAnimationFragment extends Fragment{

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_talk_record, container, false);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

}

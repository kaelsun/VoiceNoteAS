package com.zyguo.voicenote.view;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zyguo.voicenote.MainActivity;
import com.zyguo.voicenote.R;
import com.zyguo.voicenote.base.BaseFragment;

public class VoiceNoteBottomFragment extends BaseFragment implements View.OnTouchListener{

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_bottom, container, false);
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

    @Override
    protected void initController() {
        findViewById(R.id.fragment_bottom_speech).setOnTouchListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_bottom_speech:
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Message msg = new Message();
        if(view.getId() == R.id.fragment_bottom_speech) {
            switch(motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    msg.what = MainActivity.MAIN_HANDLER_START_RECORD;
                    if(getActivityHandler() != null)
                        getActivityHandler().sendMessage(msg);
                    break;
                case MotionEvent.ACTION_UP:
                    if (isInside(motionEvent.getX(), motionEvent.getY())) {
                        msg.what = MainActivity.MAIN_HANDLER_STOP_RECORD;
                        if(getActivityHandler() != null)
                            getActivityHandler().sendMessage(msg);
                    }
                    break;
                case MotionEvent.ACTION_OUTSIDE:
                case MotionEvent.ACTION_CANCEL:
                    msg.what = MainActivity.MAIN_HANDLER_CANCEL_RECORD;
                    if(getActivityHandler() != null)
                        getActivityHandler().sendMessage(msg);
                    break;
            }
        }
        return true;
    }

    /**
     * @brief To see if a motion event is inside the record button.
     * @param x The x of the motion event.
     * @param y The y of the motion event.
     * @return If the motion event is inside the record button.
     */
    private boolean isInside(float x, float y) {
        View talkbutton = findViewById(R.id.fragment_bottom_speech);
        if(0 < x && x < talkbutton.getWidth() &&
                0 < y && y < talkbutton.getHeight())
            return true;
        else
            return false;
    }
}

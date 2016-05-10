package com.zyguo.voicenote.view;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zyguo.voicenote.R;
import com.zyguo.voicenote.base.BaseFragment;
import com.zyguo.voicenote.tools.Messenger;

public class VoiceNoteBodyFragment extends BaseFragment implements View.OnTouchListener, GestureDetector.OnGestureListener{

    public static final int BODY_HANDLER_ONRESULT = 20;

    private GestureDetectorCompat mDetector;

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_body, container, false);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Messenger.getInstance().registerHandler(this.getClass().getName(), mHandler);
        mDetector = new GestureDetectorCompat(getContext(), this);
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

    @Override
    protected void initController() {
        DrawerLayout drawerLayout = (DrawerLayout) getView().findViewById(R.id.fragment_body_drawer);
        drawerLayout.openDrawer(Gravity.LEFT);
        drawerLayout.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Toast.makeText(getContext(), "onFling", Toast.LENGTH_SHORT).show();
        return false;
    }
}

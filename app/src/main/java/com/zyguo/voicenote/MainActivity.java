package com.zyguo.voicenote;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements Handler.Callback{

    private Handler mHandler;

    public static final int MAIN_HANDLER_SHOW_RECORD_DIALOG = 10;

    public static final int MAIN_HANDLER_HIDE_RECORD_DIALOG = 11;

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
    }

    private void initController() {
        mHandler = new Handler(this);
    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }
}

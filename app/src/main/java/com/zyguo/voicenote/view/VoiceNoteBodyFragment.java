package com.zyguo.voicenote.view;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zyguo.voicenote.R;
import com.zyguo.voicenote.base.BaseFragment;
import com.zyguo.voicenote.tools.Messenger;

public class VoiceNoteBodyFragment extends BaseFragment{

    public static final int BODY_HANDLER_ONRESULT = 20;

    private LayoutInflater mInflater;

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_body, container, false);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Messenger.getInstance().registerHandler(this.getClass().getName(), mHandler);
        mInflater = LayoutInflater.from(getContext());
    }
    
    @Override
    public void onStart() {
        super.onStart();
        initView();
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
        //drawerLayout.openDrawer(Gravity.LEFT);
    }

    private void initView() {
        LinearLayout body = (LinearLayout) getView().findViewById(R.id.fragment_body_main);
        //View view = mInflater.inflate(R.layout.item, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = (int) getContext().getResources().getDimension(R.dimen.item_height);
        //body.addView(view, params);
        ItemViewDecorator decorator = new ItemViewDecorator(getContext());
        if(decorator.getView() != null)
            body.addView(decorator.getView(), params);
    }
}

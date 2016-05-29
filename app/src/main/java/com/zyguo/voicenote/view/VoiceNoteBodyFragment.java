package com.zyguo.voicenote.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zyguo.voicenote.R;
import com.zyguo.voicenote.base.BaseFragment;
import com.zyguo.voicenote.database.VoiceDatabaseManager;
import com.zyguo.voicenote.model.ItemEntity;
import com.zyguo.voicenote.model.ItemModel;
import com.zyguo.voicenote.tools.Messenger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoiceNoteBodyFragment extends BaseFragment{

    public static final int BODY_HANDLER_ONRESULT = 20;

    private LayoutInflater mInflater;

    private LinearLayout mBody;

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
        mBody = (LinearLayout) getView().findViewById(R.id.fragment_body_main);
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
        clearView();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Messenger.getInstance().unRegisterHandler(this.getClass().getName());
    }

    @Override
    public boolean handleMessage(Message message) {
        if(message.what == BODY_HANDLER_ONRESULT) {
            String content = message.getData().getString("content");
            String path = message.getData().getString("path");
            onResult(content, path);
        }
        return true;
    }

    @Override
    protected void initController() {
        DrawerLayout drawerLayout = (DrawerLayout) getView().findViewById(R.id.fragment_body_drawer);
        //drawerLayout.openDrawer(Gravity.LEFT);
    }

    private void initView() {
        List<ItemEntity> itemList = VoiceDatabaseManager.getInstance().queryAll();
        List<ItemModel> modelList = ItemModel.createModelList(itemList);
        Collections.sort(modelList);
        loadLegacy(modelList);
    }

    private int getCurrentNoteCount() {
        LinearLayout body = (LinearLayout) getView().findViewById(R.id.fragment_body_main);
        if(body != null)
            return body.getChildCount();
        return 0;
    }

    private void onResult(String content, String path) {
        // Create database entity.
        ItemModel item = ItemModel.createItem(content, path, true, getImei());
        // Store the entity to database.
        if(VoiceDatabaseManager.getInstance().isInitialized())
            VoiceDatabaseManager.getInstance().insert(item);
        // Create the imp view.
        ItemViewDecorator decorator = new ItemViewDecorator(getContext(), item);
        if(decorator.getView() != null)
            mBody.addView(decorator.getView(), getParams());
    }

    private String getImei() {
        return ((TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    private void loadLegacy(List<ItemModel> itemList) {
        for(ItemEntity entity : itemList) {
            ItemModel model = ItemModel.createItem(entity);
            ItemViewDecorator decorator = new ItemViewDecorator(getContext(), model);
            if(decorator.getView() != null)
                mBody.addView(decorator.getView(), getParams());
        }
    }

    private LinearLayout.LayoutParams getParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = (int) getContext().getResources().getDimension(R.dimen.item_height);
        return params;
    }

    public void refresh() {

    }

    private void clearView() {
        if(mBody != null)
        mBody.removeAllViews();
    }
}

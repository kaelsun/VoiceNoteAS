package com.zyguo.voicenote.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public static final int BODY_HANDLER_REFRESH = 21;

    private LayoutInflater mInflater;

    private LinearLayout mBody;

    private ItemViewDecorator mDefaultItem = null;

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
        } else if(message.what == BODY_HANDLER_REFRESH) {
            refresh();
        }
        return true;
    }

    @Override
    protected void initController() {
        //DrawerLayout drawerLayout = (DrawerLayout) getView().findViewById(R.id.fragment_body_drawer);
        //drawerLayout.openDrawer(Gravity.LEFT);
        //findViewById(R.id.fragment_body).setOnClickListener(this);
    }

    private void initView() {
        List<ItemEntity> itemList = VoiceDatabaseManager.getInstance().queryAll();
        List<ItemModel> modelList = ItemModel.createModelList(itemList);
        Collections.sort(modelList);
        loadLegacy(modelList);
        if(modelList.isEmpty()) {
            showDefault();
        }
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
        if(mDefaultItem != null) {
            mBody.removeView(mDefaultItem.getView());
        }
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

    private void showDefault() {
        mDefaultItem = new ItemViewDecorator(getContext());
        if(mDefaultItem.getView() != null)
            mBody.addView(mDefaultItem.getView(), getParams());
        final TextView main = (TextView) mDefaultItem.getView().findViewById(R.id.item_text_main);
        main.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    main.setText("");
                }
            }
        });
        main.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                final String str = textView.getText().toString().trim();
                if (actionId== EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
                    if(str == null || str.equals("")) {
                        if (actionId==EditorInfo.IME_ACTION_SEND)
                            Toast.makeText(getContext(), "请输入文字", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // Create database entity.
                        ItemModel item = ItemModel.createItem(str, "", false, getImei());
                        // Store the entity to database.
                        if(VoiceDatabaseManager.getInstance().isInitialized())
                            VoiceDatabaseManager.getInstance().insert(item);
                        // Create the imp view.
                        mBody.removeView(mDefaultItem.getView());
                        mDefaultItem = null;
                        ItemViewDecorator decorator = new ItemViewDecorator(getContext(), item);
                        if(decorator.getView() != null)
                            mBody.addView(decorator.getView(), getParams());
                        InputMethodManager m = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        //v.clearFocus();
                    }
                    return false;
                }
                return false;
            }
        });
    }

    private LinearLayout.LayoutParams getParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = (int) getContext().getResources().getDimension(R.dimen.item_height);
        return params;
    }

    public void refresh() {
        clearView();
        initView();
    }

    private void clearView() {
        if(mBody != null)
        mBody.removeAllViews();
    }

    @Override
    public void onClick(View view) {
        if(mDefaultItem == null)
            showDefault();
        Toast.makeText(getContext(), "AAA", Toast.LENGTH_SHORT).show();
    }
}

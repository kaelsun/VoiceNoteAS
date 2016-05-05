package com.zyguo.voicenote.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zyguo.voicenote.R;
import com.zyguo.voicenote.base.BaseFragment;

public class VoiceNoteTitleFragment extends BaseFragment {

    private static final String TAG_SWITCH_ON = "switch_on";

    private static final String TAG_SWITCH_OFF = "switch_off";

    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_title, container, false);
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
        findViewById(R.id.fragment_title_order).setOnClickListener(this);
        findViewById(R.id.fragment_title_order).setTag(TAG_SWITCH_OFF);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_title_order:
                reOrder();
                break;

        }
    }

    private void reOrder() {
        ImageView orderButton = (ImageView) findViewById(R.id.fragment_title_order);
        if(orderButton.getTag().equals(TAG_SWITCH_OFF)) {
            orderButton.setImageResource(R.drawable.icon_switch_on);
            orderButton.setTag(TAG_SWITCH_ON);
        } else {
            orderButton.setImageResource(R.drawable.icon_switch_off);
            orderButton.setTag(TAG_SWITCH_OFF);
        }
    }
}

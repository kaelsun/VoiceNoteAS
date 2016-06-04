package com.zyguo.voicenote.view;

/**
 * Created by zyguo on 2016/6/4.
 */

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zyguo.voicenote.R;

public class VoiceNoteTimePickerFragment extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(R.style.Theme_picker, R.style.Theme_picker);
    }

    @Override
    public void onStart() {
        super.onStart();
        initController();
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

    private void initController() {
        final TimePicker timePicker = (TimePicker) getView().findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        //resetTextColor(timePicker);

        DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datepicker);
        //resetTextColor(datePicker);
        //datePicker.seton
    }

    private void resetTextColor(ViewGroup viewGroup) {
        for(int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if(child instanceof TextView)
                ((TextView)child).setTextColor(getActivity().getResources().getColorStateList(R.color.text_grey));
            if(child instanceof ViewGroup)
                resetTextColor((ViewGroup) child);
        }
    }
}


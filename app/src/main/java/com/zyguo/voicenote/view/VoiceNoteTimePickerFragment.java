package com.zyguo.voicenote.view;

/**
 * Created by zyguo on 2016/6/4.
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.zyguo.voicenote.MainActivity;
import com.zyguo.voicenote.R;

import java.util.Calendar;

public class VoiceNoteTimePickerFragment extends DialogFragment implements View.OnClickListener{

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

        DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datepicker);

        getView().findViewById(R.id.fragment_time_picker_confirm).setOnClickListener(this);
        getView().findViewById(R.id.fragment_time_picker_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fragment_time_picker_confirm:
                setAlarm();
                dismiss();
                break;
            case R.id.fragment_time_picker_cancel:
                dismiss();
                break;
        };
    }

    private void setAlarm() {
        TimePicker timePicker = (TimePicker) getView().findViewById(R.id.timepicker);
        DatePicker datePicker = (DatePicker) getView().findViewById(R.id.datepicker);

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        long milli = calendar.getTimeInMillis();
        //Toast.makeText(getActivity(), year+"年"+month+"月"+day+"日"+hour+"时"+minite+"分"+milli, Toast.LENGTH_SHORT).show();

        Handler mainHandler = ((MainActivity)getActivity()).getMainActivityHandler();
        Message msg = new Message();
        msg.what = MainActivity.MAIN_HANDLER_TIME_PICKED;
        msg.obj = milli;
        mainHandler.sendMessage(msg);
    }
}


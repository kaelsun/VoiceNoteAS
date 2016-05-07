package com.zyguo.voicenote.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.zyguo.voicenote.MainActivity;

/**
 * Created by zyguo on 2016/5/5.
 * Show suggested method names for fragments.<br/>
 * Suggested to be the father of all fragments, but not forced.
 */
public abstract class BaseFragment extends Fragment implements OnClickListener, Handler.Callback{

    /**
     * Handler of the Fragment. Children should register to the Messenger if it want to use the handler.
     */
    protected Handler mHandler;

    /**
     * Anything about the controller layer could be here.
     */
    protected void initController() {}

    /**
     * Same as getActivity(), but more related to the common getContext().<br/>
     * The Context of the Fragments must be Activity anyway.
     * @return Activity that the Fragment attach to.
     */
    public Context getContext() {
        return getActivity();
    }

    /**
     * A more convenient way to find views.
     * @param id The resource id of the view.
     * @return The view found, or null if not found.
     */
    public View findViewById(int id) {
        return getActivity().findViewById(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(this);
        //initController();
    }

    @Override
    public void onStart() {
        super.onStart();
        initController();
    }

    @Override
    public void onClick(View view) {

    }

    public Handler getActivityHandler() {
        try {
            MainActivity activity = (MainActivity) getActivity();
            return activity.getMainActivityHandler();
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        return false;
    }

    public Handler getHandler() {
        return mHandler;
    }
}

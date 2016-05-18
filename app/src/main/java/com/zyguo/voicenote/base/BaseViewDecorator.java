package com.zyguo.voicenote.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by zyguo on 2016/5/17.
 * Base of all View Containers.
 * It's kind of an implementation of the decorator pattern.<br/>
 * Kind of the controller layer of the view.
 */
public abstract class BaseViewDecorator implements OnClickListener{

    protected View mView;

    protected int layoutId;

    /**
     * Key method of all the View Containers. Clients should not call the view directly, but by calling methods of the containers.
     * @return the main view.
     */
    public View getView() {
        return mView;
    }

    /**
     * Get the child view of the main view.
     * @param id Id of the child view.
     * @return The child view.
     */
    public View findViewById(int id) {
        return mView.findViewById(id);
    }

    public BaseViewDecorator(Context context, int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(layoutId, null);

        initController();
    }

    /**
     * It's suggested to init the controller part of the view here.
     */
    protected void initController() {}

    @Override
    public void onClick(View view) {

    }
}

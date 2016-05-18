package com.zyguo.voicenote.view;

import android.content.Context;

import com.zyguo.voicenote.R;
import com.zyguo.voicenote.base.BaseViewDecorator;

/**
 * Created by zyguo on 2016/5/16.
 */
public class ItemViewDecorator extends BaseViewDecorator{

    protected int layoutId = R.layout.item;

    public ItemViewDecorator(Context context) {
        super(context, R.layout.item);
    }

    protected void initController() {

    }
}

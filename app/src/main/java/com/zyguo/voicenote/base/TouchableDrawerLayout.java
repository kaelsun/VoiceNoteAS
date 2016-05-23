package com.zyguo.voicenote.base;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;

/**
 * Created by zyguo on 2016/5/11.
 * A child of DrawerLayout that could be open and closed by Touch event.
 */
public class TouchableDrawerLayout extends DrawerLayout implements GestureDetector.OnGestureListener{

    private GestureDetectorCompat mDetector;

    public TouchableDrawerLayout(Context context) {
        super(context);
        mDetector = new GestureDetectorCompat(context, this);
    }

    public TouchableDrawerLayout(Context context, AttributeSet attrs) {
       super(context, attrs);
        mDetector = new GestureDetectorCompat(context, this);
    }

    public TouchableDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDetector = new GestureDetectorCompat(context, this);
    }

    @Override
    public boolean onTouchEvent (MotionEvent ev) {
        this.mDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    /**
     * Override this method for using this drawerlayout in the LinearLayout.
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        openDrawer(Gravity.RIGHT);
        openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(isFlingRight(v, v1)) {
            if(!isDrawerOpen(Gravity.RIGHT))
                openDrawer(Gravity.LEFT);
            else
                closeDrawer(Gravity.RIGHT);
        }
        else if(isFlingLeft(v, v1)) {
            if(!isDrawerOpen(Gravity.LEFT))
                openDrawer(Gravity.RIGHT);
            else
                closeDrawer(Gravity.LEFT);
        }
        return false;
    }

    private boolean isFlingLeft(float x, float y) {
        if(x < 0 && x * x > y * y)
            return true;
        return false;
    }

    private boolean isFlingRight(float x, float y) {
        if(x > 0 && x * x > y * y)
            return true;
        return false;
    }
}

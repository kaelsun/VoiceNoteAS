package com.zyguo.voicenote.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyguo.voicenote.MainActivity;
import com.zyguo.voicenote.R;
import com.zyguo.voicenote.base.BaseViewDecorator;
import com.zyguo.voicenote.base.TouchableDrawerLayout;
import com.zyguo.voicenote.database.VoiceDatabaseManager;
import com.zyguo.voicenote.model.ItemModel;
import com.zyguo.voicenote.tools.Messenger;
import com.zyguo.voicenote.tools.TimeUtil;

import org.w3c.dom.Text;

/**
 * Created by zyguo on 2016/5/16.
 */
public class ItemViewDecorator extends BaseViewDecorator implements View.OnLongClickListener, TextView.OnEditorActionListener{

    protected int layoutId = R.layout.item;

    private ItemModel item;

    private boolean isDefault;

    public ItemViewDecorator(Context context, ItemModel item) {
        super(context, R.layout.item);
        this.item = item;
        isDefault = false;
        initController();
    }

    public ItemViewDecorator(Context context) {
        super(context, R.layout.item);
        isDefault = true;
        initController();
    }

    protected void initController() {
        findViewById(R.id.item_alarm).setOnClickListener(this);
        if(isDefault) {
            TextView create = (TextView) findViewById(R.id.item_text_create_time);
            create.setText(R.string.item_text_not_created);

            TextView remind = (TextView) findViewById(R.id.item_text_alarm_time);
            remind.setText(R.string.item_text_not_alarmed);
            remind.setTextColor(getContext().getResources().getColor(R.color.text_black));

            return;
        }
        EditText editText = (EditText) findViewById(R.id.item_text_main);
        editText.setText(item.getContent());
        editText.setTextColor(getContext().getResources().getColor(R.color.text_black));
        editText.setOnEditorActionListener(this);

        String createTime = TimeUtil.getItemCreateTime(item.getCreateTime());
        TextView create = (TextView) findViewById(R.id.item_text_create_time);
        create.setText(createTime);

        TextView remind = (TextView) findViewById(R.id.item_text_alarm_time);
        remind.setText(R.string.item_text_not_alarmed);
        remind.setTextColor(getContext().getResources().getColor(R.color.text_black));

        //findViewById(R.id.item_main).setOnLongClickListener(this);

        findViewById(R.id.item_left).setOnClickListener(this);
        findViewById(R.id.item_right).setOnClickListener(this);

        if(item.getIsStar()) {
            ImageView star = (ImageView) getView().findViewById(R.id.item_star);
            star.setImageResource(R.drawable.big_star_red);
            TextView content = (TextView) getView().findViewById(R.id.item_text_main);
            content.setTextColor(getContext().getResources().getColor(R.color.orange));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        TouchableDrawerLayout drawerLayout = (TouchableDrawerLayout) findViewById(R.id.fragment_body_drawer);
        drawerLayout.openDrawer(Gravity.LEFT);
        drawerLayout.openDrawer(Gravity.RIGHT);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_left:
                starThis();
                break;
            case R.id.item_right:
                deleteThis();
                break;
            case R.id.item_alarm:
                if(isDefault)
                    Toast.makeText(getContext(), R.string.item_default_tip, Toast.LENGTH_SHORT).show();
                else {
                    setAlarm();
                }
                break;
        }
    }

    private void starThis() {
        if(!item.getIsStar()) {
            item.setIsStar(true);
            //ImageView star = (ImageView) getView().findViewById(R.id.item_star);
            //star.setImageResource(R.drawable.big_star_red);
            //TextView content = (TextView) getView().findViewById(R.id.item_text_main);
            //content.setTextColor(getContext().getResources().getColor(R.color.orange));
        }
        else {
            item.setIsStar(false);
            //ImageView star = (ImageView) getView().findViewById(R.id.item_star);
            //star.setImageResource(R.drawable.big_star_grey);
            //TextView content = (TextView) getView().findViewById(R.id.item_text_main);
            //content.setTextColor(getContext().getResources().getColor(R.color.text_black));
        }
        VoiceDatabaseManager.getInstance().starItem(item);
        TouchableDrawerLayout drawerLayout = (TouchableDrawerLayout) findViewById(R.id.fragment_body_drawer);
        drawerLayout.closeDrawer(Gravity.LEFT);
        drawerLayout.closeDrawer(Gravity.RIGHT);

        pushRefresh();
    }

    private void deleteThis() {
        getView().setVisibility(View.GONE);
        VoiceDatabaseManager.getInstance().deleteItem(item);
    }

    private void pushRefresh() {
        Message msg = new Message();
        msg.what = VoiceNoteBodyFragment.BODY_HANDLER_REFRESH;
        Messenger.getInstance().getHandlerByName(VoiceNoteBodyFragment.class.getName()).sendMessage(msg);
    }

    private void setAlarm() {
        Handler mainHandler = ((MainActivity)getContext()).getMainActivityHandler();
        Message msg = new Message();
        msg.what = MainActivity.MAIN_HANDLER_PICK_TIME;
        msg.obj = this;
        mainHandler.sendMessage(msg);
    }

    public void setAlarm(long time) {
        //Toast.makeText(getContext(), time+"", Toast.LENGTH_SHORT).show();
        if(time == 0)
            return;
        item.setRemindTime(time);
        updateItem();
    }

    private void cancelAlarm() {
        item.setRemindTime(0L);
        updateItem();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        final String str = textView.getText().toString().trim();
        if (actionId== EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
            if(str == null || str.equals("")) {
                if (actionId==EditorInfo.IME_ACTION_SEND)
                    Toast.makeText(getContext(), "请输入文字", Toast.LENGTH_SHORT).show();
            }
            else {
                item.setContent(str);
                updateItem();
                InputMethodManager m = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                textView.clearFocus();
            }
            return false;
        }
        return false;
    }

    public void updateItem() {
        VoiceDatabaseManager.getInstance().updateItem(item);
    }
}

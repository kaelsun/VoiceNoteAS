package com.zyguo.voicenote.view;

import android.content.Context;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
public class ItemViewDecorator extends BaseViewDecorator implements View.OnLongClickListener{

    protected int layoutId = R.layout.item;

    private ItemModel item;

    public ItemViewDecorator(Context context, ItemModel item) {
        super(context, R.layout.item);
        this.item = item;
        initController();
    }

    protected void initController() {
        EditText editText = (EditText) findViewById(R.id.item_text_main);
        editText.setText(item.getContent());
        editText.setTextColor(getContext().getResources().getColor(R.color.text_black));

        String createTime = TimeUtil.getItemCreateTime(item.getCreateTime());
        TextView create = (TextView) findViewById(R.id.item_text_create_time);
        create.setText(createTime);

        TextView remind = (TextView) findViewById(R.id.item_text_alarm_time);
        remind.setText("未设置提醒");
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
}

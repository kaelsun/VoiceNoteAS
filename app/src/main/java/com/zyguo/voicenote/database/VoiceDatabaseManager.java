package com.zyguo.voicenote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zyguo.voicenote.model.ItemEntity;

import java.util.List;

/**
 * Created by zyguo on 2016/5/20.
 * Manager of the database module based on greenDao.
 */
public class VoiceDatabaseManager {

    private static VoiceDatabaseManager self;

    private SQLiteOpenHelper helper;

    private SQLiteDatabase db;

    private DaoMaster daoMaster;

    private DaoSession daoSession;

    private ItemEntityDao itemDao;

    private boolean initialized = false;

    protected VoiceDatabaseManager() {

    }

    public static VoiceDatabaseManager getInstance() {
        if(self == null)
            self = new VoiceDatabaseManager();
        return self;
    }

    public void init(Context context) {
        helper = new DaoMaster.DevOpenHelper(context, "item-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        itemDao = daoSession.getItemEntityDao();
        initialized = true;
    }

    public void unInit() {
        helper.close();
        db.close();
        daoMaster = null;
        daoSession.clear();
        daoSession = null;
        itemDao = null;
        initialized = false;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void insert(ItemEntity entity) {
        itemDao.insert(entity);
    }

    public List<ItemEntity> queryAll() {
        return itemDao.loadAll();
    }

    public void starItem(ItemEntity entity) {
        itemDao.update(entity);
    }

    public void updateItem(ItemEntity entity) {
        itemDao.update(entity);
    }

    public void deleteItem(ItemEntity entity) {
        itemDao.delete(entity);
    }
}

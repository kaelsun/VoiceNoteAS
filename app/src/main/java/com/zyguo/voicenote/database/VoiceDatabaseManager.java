package com.zyguo.voicenote.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zyguo on 2016/5/20.
 * Manager of the database module based on greenDao.
 */
public class VoiceDatabaseManager {

    private static VoiceDatabaseManager self;

    private SQLiteOpenHelper helper;

    private SQLiteDatabase db;

    //private DaoMaster daoMaster;

    protected VoiceDatabaseManager() {

    }

    public VoiceDatabaseManager getInstance() {
        if(self == null)
            self = new VoiceDatabaseManager();
        return self;
    }

    public void init() {
        //helper = DaoMaster.DevOpenHelper( this, "notes-db", null);
        db = helper.getWritableDatabase();
        //daoMaster = new DaoMaster(db);
        //daoSession = daoMaster.newSession();
        //noteDao = daoSession.getNoteDao();
    }
}

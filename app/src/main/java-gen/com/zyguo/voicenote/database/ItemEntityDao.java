package com.zyguo.voicenote.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.zyguo.voicenote.model.ItemEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ITEM_ENTITY".
*/
public class ItemEntityDao extends AbstractDao<ItemEntity, Long> {

    public static final String TABLENAME = "ITEM_ENTITY";

    /**
     * Properties of entity ItemEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property UserName = new Property(2, String.class, "userName", false, "USER_NAME");
        public final static Property UserPhone = new Property(3, String.class, "userPhone", false, "USER_PHONE");
        public final static Property Imei = new Property(4, String.class, "imei", false, "IMEI");
        public final static Property Content = new Property(5, String.class, "content", false, "CONTENT");
        public final static Property LocalPath = new Property(6, String.class, "localPath", false, "LOCAL_PATH");
        public final static Property Url = new Property(7, String.class, "url", false, "URL");
        public final static Property IsStar = new Property(8, Boolean.class, "isStar", false, "IS_STAR");
        public final static Property IsVoice = new Property(9, Boolean.class, "isVoice", false, "IS_VOICE");
        public final static Property CreateTime = new Property(10, Long.class, "createTime", false, "CREATE_TIME");
        public final static Property RemindTime = new Property(11, Long.class, "remindTime", false, "REMIND_TIME");
        public final static Property Type = new Property(12, String.class, "type", false, "TYPE");
        public final static Property Column1 = new Property(13, String.class, "column1", false, "COLUMN1");
        public final static Property Column2 = new Property(14, String.class, "column2", false, "COLUMN2");
        public final static Property Column3 = new Property(15, String.class, "column3", false, "COLUMN3");
        public final static Property Column4 = new Property(16, String.class, "column4", false, "COLUMN4");
        public final static Property Column5 = new Property(17, String.class, "column5", false, "COLUMN5");
    };


    public ItemEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ItemEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ITEM_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"USER_NAME\" TEXT," + // 2: userName
                "\"USER_PHONE\" TEXT," + // 3: userPhone
                "\"IMEI\" TEXT," + // 4: imei
                "\"CONTENT\" TEXT," + // 5: content
                "\"LOCAL_PATH\" TEXT," + // 6: localPath
                "\"URL\" TEXT," + // 7: url
                "\"IS_STAR\" INTEGER," + // 8: isStar
                "\"IS_VOICE\" INTEGER," + // 9: isVoice
                "\"CREATE_TIME\" INTEGER," + // 10: createTime
                "\"REMIND_TIME\" INTEGER," + // 11: remindTime
                "\"TYPE\" TEXT," + // 12: type
                "\"COLUMN1\" TEXT," + // 13: column1
                "\"COLUMN2\" TEXT," + // 14: column2
                "\"COLUMN3\" TEXT," + // 15: column3
                "\"COLUMN4\" TEXT," + // 16: column4
                "\"COLUMN5\" TEXT);"); // 17: column5
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ITEM_ENTITY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ItemEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String userPhone = entity.getUserPhone();
        if (userPhone != null) {
            stmt.bindString(4, userPhone);
        }
 
        String imei = entity.getImei();
        if (imei != null) {
            stmt.bindString(5, imei);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(6, content);
        }
 
        String localPath = entity.getLocalPath();
        if (localPath != null) {
            stmt.bindString(7, localPath);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(8, url);
        }
 
        Boolean isStar = entity.getIsStar();
        if (isStar != null) {
            stmt.bindLong(9, isStar ? 1L: 0L);
        }
 
        Boolean isVoice = entity.getIsVoice();
        if (isVoice != null) {
            stmt.bindLong(10, isVoice ? 1L: 0L);
        }
 
        Long createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(11, createTime);
        }
 
        Long remindTime = entity.getRemindTime();
        if (remindTime != null) {
            stmt.bindLong(12, remindTime);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(13, type);
        }
 
        String column1 = entity.getColumn1();
        if (column1 != null) {
            stmt.bindString(14, column1);
        }
 
        String column2 = entity.getColumn2();
        if (column2 != null) {
            stmt.bindString(15, column2);
        }
 
        String column3 = entity.getColumn3();
        if (column3 != null) {
            stmt.bindString(16, column3);
        }
 
        String column4 = entity.getColumn4();
        if (column4 != null) {
            stmt.bindString(17, column4);
        }
 
        String column5 = entity.getColumn5();
        if (column5 != null) {
            stmt.bindString(18, column5);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ItemEntity readEntity(Cursor cursor, int offset) {
        ItemEntity entity = new ItemEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userPhone
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // imei
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // content
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // localPath
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // url
            cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0, // isStar
            cursor.isNull(offset + 9) ? null : cursor.getShort(offset + 9) != 0, // isVoice
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // createTime
            cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11), // remindTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // type
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // column1
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // column2
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // column3
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // column4
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17) // column5
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ItemEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserPhone(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImei(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setContent(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLocalPath(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUrl(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIsStar(cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0);
        entity.setIsVoice(cursor.isNull(offset + 9) ? null : cursor.getShort(offset + 9) != 0);
        entity.setCreateTime(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setRemindTime(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
        entity.setType(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setColumn1(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setColumn2(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setColumn3(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setColumn4(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setColumn5(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ItemEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ItemEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}

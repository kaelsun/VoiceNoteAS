package com.zyguo;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;


public class VoiceDaoGenerator {
    public static void main(String[] args) throws Exception {

        //Schema schema = new Schema(1, "com.zyguo.voicenote.database");

        Schema schema = new Schema(1, "com.zyguo.voicenote.model");
        schema.setDefaultJavaPackageDao("com.zyguo.voicenote.database");

        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        // schema2.enableActiveEntitiesByDefault();
        // schema2.enableKeepSectionsByDefault();

        addItem(schema);

        new DaoGenerator().generateAll(schema, "d:/zyguo/git/VoiceNoteAS/app/src/main/java-gen");
    }

    /**
     * @param schema
     */
    private static void addItem(Schema schema) {
        Entity item = schema.addEntity("ItemEntity");
        //note.setTableName("NODE");

        item.addIdProperty();
        item.addStringProperty("userId");
        item.addStringProperty("userName");
        item.addStringProperty("userPhone");
        item.addStringProperty("imei");
        item.addStringProperty("content");
        item.addStringProperty("localPath");
        item.addStringProperty("url");
        item.addBooleanProperty("isStar");
        item.addBooleanProperty("isVoice");
        item.addLongProperty("createTime");
        item.addLongProperty("remindTime");
        item.addStringProperty("type");
        item.addStringProperty("column1");
        item.addStringProperty("column2");
        item.addStringProperty("column3");
        item.addStringProperty("column4");
        item.addStringProperty("column5");
    }
}

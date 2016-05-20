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

        addNote(schema);

        new DaoGenerator().generateAll(schema, "d:/zyguo/git/VoiceNoteAS/app/src/main/java-gen");
    }

    /**
     * @param schema
     */
    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("ItemEntity");
        //note.setTableName("NODE");

        note.addIdProperty();
        note.addIntProperty("userId");
        note.addStringProperty("content");
        note.addStringProperty("localPath");
        note.addStringProperty("url");
        note.addBooleanProperty("isStar");
        note.addBooleanProperty("isVoice");
        note.addLongProperty("createTime");
        note.addLongProperty("remindTime");
        note.addStringProperty("type");
        note.addStringProperty("column1");
        note.addStringProperty("column2");
        note.addStringProperty("column3");
        note.addStringProperty("column4");
        note.addStringProperty("column5");
    }
}

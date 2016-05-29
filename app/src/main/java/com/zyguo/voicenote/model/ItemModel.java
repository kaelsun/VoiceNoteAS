package com.zyguo.voicenote.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyguo on 2016/5/14.
 * This class extends the entity class, for sorting especially.
 */
public class ItemModel extends ItemEntity implements Serializable, Comparable<ItemModel>{

    /**
     * For sorting the item list.
     * @param itemModel
     * @return
     */
    @Override
    public int compareTo(ItemModel itemModel) {
        if(this.getIsStar() == itemModel.getIsStar()) {
            if (this.getCreateTime() < itemModel.getCreateTime())
                return -1;
            else
                return 1;
        } else if(this.getIsStar() == true)
            return -1;
        else
            return 1;
    }

    public static ItemModel createItem(String content, String localPath, boolean isVoice, String imei) {
        ItemModel item = new ItemModel();
        item.setContent(content);
        item.setLocalPath(localPath);
        item.setIsVoice(isVoice);
        item.setIsStar(false);
        item.setCreateTime(System.currentTimeMillis());
        item.setImei(imei);
        item.setColumn1("");
        item.setColumn2("");
        item.setColumn3("");
        item.setColumn4("");
        item.setColumn5("");
        item.setRemindTime(0L);
        item.setType("");
        item.setUrl("");
        item.setUserId("");
        item.setUserName("");
        item.setUserPhone("");
        return item;
    }

    public static ItemModel createItem(ItemEntity entity) {
        ItemModel model = new ItemModel();
        model.setContent(entity.getContent());
        model.setLocalPath(entity.getLocalPath());
        model.setCreateTime(entity.getCreateTime());
        model.setId(entity.getId());
        model.setImei(entity.getImei());
        model.setIsStar(entity.getIsStar());
        model.setIsVoice(entity.getIsVoice());
        model.setRemindTime(entity.getRemindTime());
        model.setType(entity.getType());
        model.setUrl(entity.getUrl());
        model.setUserId(entity.getUserId());
        model.setUserName(entity.getUserName());
        model.setUserPhone(entity.getUserPhone());
        model.setColumn1(entity.getColumn1());
        model.setColumn2(entity.getColumn2());
        model.setColumn3(entity.getColumn3());
        model.setColumn4(entity.getColumn4());
        model.setColumn5(entity.getColumn5());

        return model;
    }

    public static ArrayList<ItemModel> createModelList(List<ItemEntity> entityList) {
        ArrayList<ItemModel> modelList = new ArrayList<ItemModel>();
        for(ItemEntity entity : entityList) {
            ItemModel model = createItem(entity);
            modelList.add(model);
        }
        return modelList;
    }
//
//    /**
//     * Id of the message
//     */
//    private int id;
//
//    /**
//     * Id of the user
//     */
//    private int userId;
//
//    /**
//     * Content of the message
//     */
//    private String content;
//
//    /**
//     * The path of the voice file
//     */
//    private String localPath;
//
//    /**
//     * The url of the voice file if it is uploaded.
//     */
//    private String url;
//
//    /**
//     * Whether the message was starred or not.
//     */
//    private boolean isStar;
//
//    /**
//     * Whether the message was created by speech or text input.
//     */
//    private boolean isVoice;
//
//    /**
//     * Time stamp when the message was created.
//     */
//    private long createTime;
//
//    /**
//     * Time to remind the user about the message.
//     */
//    private long remindTime;
//
//    /**
//     * Type of the message.
//     */
//    private String type;
//
//    /**
//     * Below are not used yet, so they should all be "". May be used in the future.
//     */
//    private String column1;
//
//    private String column2;
//
//    private String column3;
//
//    private String column4;
//
//    private String column5;
}

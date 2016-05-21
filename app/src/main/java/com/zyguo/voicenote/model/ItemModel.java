package com.zyguo.voicenote.model;

import java.io.Serializable;

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
        item.setCreateTime(System.currentTimeMillis());
        item.setImei(imei);
        return item;
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

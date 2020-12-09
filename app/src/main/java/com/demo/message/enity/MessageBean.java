package com.demo.message.enity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MessageBean implements MultiItemEntity {
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    String message;

    public MessageBean(int itemType, String message) {
        this.itemType = itemType;
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

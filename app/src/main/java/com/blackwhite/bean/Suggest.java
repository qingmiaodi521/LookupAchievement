package com.blackwhite.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by BlackWhite on 15/9/11.
 */
public class Suggest extends BmobObject{

    String content;

    String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

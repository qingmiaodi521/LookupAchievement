package com.blackwhite.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by BlackWhite on 15/9/9.
 * change
 */
public class UsrAndPass extends BmobObject {

    private String name;
    private String pass;
    private String change;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

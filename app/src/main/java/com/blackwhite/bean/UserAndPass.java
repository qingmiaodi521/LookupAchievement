package com.blackwhite.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by BlackWhite on 15/9/11.
 */
public class UserAndPass extends BmobObject {
    String name;
    String pass;

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

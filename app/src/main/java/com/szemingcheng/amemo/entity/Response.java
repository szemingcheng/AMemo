package com.szemingcheng.amemo.entity;

import java.io.Serializable;

/**
 * Created by Jaygren on 2017/5/24.
 */

public class Response implements Serializable {
    private static final long serialVersionUID = -213221189192962074L;


    String resCode;
    String message;
    String userid;
    String password;
    String phone;
    String onscreen_name;

    public String getOnscreen_name() {
        return onscreen_name;
    }

    public void setOnscreen_name(String onscreen_name) {
        this.onscreen_name = onscreen_name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Response() {
    }

    public Response(String resCode, String message, String password, String userid, String phone, String onscreen_name) {
        this.resCode = resCode;
        this.message = message;
        this.password = password;
        this.userid = userid;
        this.phone = phone;
        this.onscreen_name=onscreen_name;
    }


}

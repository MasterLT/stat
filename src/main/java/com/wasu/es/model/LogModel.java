package com.wasu.es.model;

import java.util.Date;

/**
 * Created by MASTER_L on 2017/12/29.
 */
public class LogModel {
    private String rpcode;
    private String cpcode;
    private String timestamp;

    public String getCpcode() {
        return cpcode;
    }

    public void setCpcode(String cpcode) {
        this.cpcode = cpcode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRpcode() {
        return rpcode;
    }

    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }
}

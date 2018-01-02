package com.wasu.es.model;

import com.wasu.es.controller_pc.DataController;

import java.util.Date;

/**
 * Created by MASTER_L on 2017/12/29.
 */
public class LogModel {
    private String rpcode;
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRpcode() {
        return rpcode;
    }

    public void setRpcode(String rpcode) {
        this.rpcode = rpcode;
    }
}

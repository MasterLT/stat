package com.wasu.es.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "stat_region")
public class StatRegion {
    @Id
    private Integer id;

    private String region;

    private String rpname;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_update")
    private Date gmtUpdate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return rpname
     */
    public String getRpname() {
        return rpname;
    }

    /**
     * @param rpname
     */
    public void setRpname(String rpname) {
        this.rpname = rpname;
    }

    /**
     * @return gmt_create
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return gmt_update
     */
    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * @param gmtUpdate
     */
    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}
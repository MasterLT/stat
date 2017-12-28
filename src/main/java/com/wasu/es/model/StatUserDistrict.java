package com.wasu.es.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "stat_user_district")
public class StatUserDistrict {
    @Id
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmr_update")
    private Date gmrUpdate;

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
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return district_id
     */
    public Integer getDistrictId() {
        return districtId;
    }

    /**
     * @param districtId
     */
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
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
     * @return gmr_update
     */
    public Date getGmrUpdate() {
        return gmrUpdate;
    }

    /**
     * @param gmrUpdate
     */
    public void setGmrUpdate(Date gmrUpdate) {
        this.gmrUpdate = gmrUpdate;
    }
}
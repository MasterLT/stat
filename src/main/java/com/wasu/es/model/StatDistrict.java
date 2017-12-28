package com.wasu.es.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "stat_district")
public class StatDistrict {
    @Id
    private Integer id;

    /**
     * 区域名称
     */
    @Column(name = "district_name")
    private String districtName;

    /**
     * 区域值
     */
    @Column(name = "district_value")
    private String districtValue;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_update")
    private Date gmtUpdate;
    
    @Transient
    private Integer userId;

    @Column(name = "index_url")
    private String indexUrl;

    @Column(name = "css_key")
    private String cssKey;

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
     * 获取区域名称
     *
     * @return district_name - 区域名称
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * 设置区域名称
     *
     * @param districtName 区域名称
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * 获取区域值
     *
     * @return district_value - 区域值
     */
    public String getDistrictValue() {
        return districtValue;
    }

    /**
     * 设置区域值
     *
     * @param districtValue 区域值
     */
    public void setDistrictValue(String districtValue) {
        this.districtValue = districtValue;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getCssKey() {
        return cssKey;
    }

    public void setCssKey(String cssKey) {
        this.cssKey = cssKey;
    }
}
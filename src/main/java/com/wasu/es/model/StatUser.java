package com.wasu.es.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "stat_user")
public class StatUser implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private Integer id;

    private String account;

    private String password;

    private Integer type;

    private String name;

    private String nickname;

    private String mobile;

    private String email;

    private Integer status;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_update")
    private Date gmtUpdate;
    
    /**
     * 是否发送警告，0:不发送，1:发送
     */
    @Column(name = "is_warning")
    private Integer isWarning;

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
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
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
    
    public Integer getIsWarning() {
        return isWarning;
    }

    /**
     * 设置是否发送警告，0:不发送，1:发送
     *
     * @param isWarning 是否发送警告，0:不发送，1:发送
     */
    public void setIsWarning(Integer isWarning) {
        this.isWarning = isWarning;
    }
}
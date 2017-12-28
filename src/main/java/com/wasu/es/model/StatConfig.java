package com.wasu.es.model;
import javax.persistence.*;

@Table(name = "stat_config")
public class StatConfig {
    @Id
    private Integer id;

    /**
     * 键
     */
    @Column(name = "sys_key")
    private String sysKey;

    /**
     * 值
     */
    @Column(name = "sys_value")
    private String sysValue;

    /**
     * 状态，是否启动，1是0否
     */
    private Integer status;

    /**
     * 说明
     */
    private String content;

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
     * 获取键
     *
     * @return sys_key - 键
     */
    public String getSysKey() {
        return sysKey;
    }

    /**
     * 设置键
     *
     * @param sysKey 键
     */
    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    /**
     * 获取值
     *
     * @return sys_value - 值
     */
    public String getSysValue() {
        return sysValue;
    }

    /**
     * 设置值
     *
     * @param sysValue 值
     */
    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
    }

    /**
     * 获取状态，是否启动，1是0否
     *
     * @return status - 状态，是否启动，1是0否
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，是否启动，1是0否
     *
     * @param status 状态，是否启动，1是0否
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取说明
     *
     * @return content - 说明
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置说明
     *
     * @param content 说明
     */
    public void setContent(String content) {
        this.content = content;
    }
}
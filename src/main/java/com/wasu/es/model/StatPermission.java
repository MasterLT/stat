package com.wasu.es.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "stat_permission")
public class StatPermission implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private Integer id;

    private String name;

    private String url;

    @Column(name = "parent_id")
    private Integer parentId;

    private Integer level;

    private String code;
    
    /**
     * 用于后台刷新列表
     */
    private String tabid;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_update")
    private Date gmtUpdate;
    
    private String icon;
    
    @Transient
    private List<StatPermission> permissionList;

    @Transient
    private Integer roleId;
    
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
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return parent_id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 用于后台刷新列表
     * @return
     */
    public String getTabid() {
		return tabid;
	}

    /**
     * 用于后台刷新列表
     * @param tabid
     */
	public void setTabid(String tabid) {
		this.tabid = tabid;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<StatPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<StatPermission> permissionList) {
		this.permissionList = permissionList;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
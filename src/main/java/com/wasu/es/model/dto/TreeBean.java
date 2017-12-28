package com.wasu.es.model.dto;

import com.wasu.es.model.StatPermission;
import com.wasu.es.utils.GodUtils;

import java.util.ArrayList;
import java.util.List;

public class TreeBean {

	private Integer id;
	
	private String name;
	
	private boolean checked;
	
	private boolean checkedOld;
	
	private Integer level;
	
	private Integer pId;
	
	private List<TreeBean> children;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isCheckedOld() {
		return checkedOld;
	}

	public void setCheckedOld(boolean checkedOld) {
		this.checkedOld = checkedOld;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public List<TreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}

	public static void permissionTreeBean(StatPermission permission , TreeBean treeBean){
		List<TreeBean> tbList = new ArrayList<TreeBean>();
		List<StatPermission> spList =permission.getPermissionList();
		if (GodUtils.CheckNull(spList)) {
			return;
		}
		for (int i = 0; i < spList.size(); i++) {
			StatPermission sp = spList.get(i);
			TreeBean tb = new TreeBean();
			tb.setId(sp.getId());
			tb.setName(sp.getName());
			tb.setpId(sp.getParentId());
			if (sp.getRoleId() == null || sp.getRoleId() == 0) {
				tb.setChecked(false);
			}else{
				tb.setChecked(true);
			}
			if (!GodUtils.CheckNull(sp.getPermissionList())) {
				permissionTreeBean(sp, tb);
			}
			tbList.add(tb);
		}
		treeBean.setId(permission.getId());
		treeBean.setpId(permission.getParentId());
		treeBean.setName(permission.getName());
		if (permission.getRoleId() == null || permission.getRoleId() == 0) {
			treeBean.setChecked(false);
		}else {
			treeBean.setChecked(true);
		}
		treeBean.setChildren(tbList);
	}
	
}

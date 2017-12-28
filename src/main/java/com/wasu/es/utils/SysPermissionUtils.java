package com.wasu.es.utils;

import com.wasu.es.model.StatPermission;

import java.util.ArrayList;
import java.util.List;

public class SysPermissionUtils {

	/**
	 * List转Tree对象
	 * 
	 * @使用递归
	 * 
	 * @param knowPoint
	 * @param index
	 */
	public static void list2Tree(List<StatPermission> sysPermissionList, StatPermission sysPermission) {
		List<StatPermission> _sysPermission = new ArrayList<StatPermission>();
		int length = sysPermissionList.size();
		for (int i = 0; i < length; i++) {
			StatPermission sp = sysPermissionList.get(i);
			if (sp.getLevel() == null || sysPermission.getLevel() + 1 != sp.getLevel()) {
				continue;
			}
			if (sysPermission.getId().equals(sp.getParentId())) {
				_sysPermission.add(sp);
				list2Tree(sysPermissionList, sp);
			}
		}
		if (_sysPermission.size() > 0) {
			sysPermission.setPermissionList(_sysPermission);
		}
	}
	
	/**
	 * 获取树结构的菜单
	 * @param sysMenuList
	 * @return
	 */
	public static List<StatPermission> getTreeMenu(List<StatPermission> sysPermissionList){
		List<StatPermission> list = new ArrayList<StatPermission>();
		StatPermission sysPermission = new StatPermission();
		for (StatPermission sp : sysPermissionList) {
			sp.setGmtCreate(null);
			sp.setGmtUpdate(null);
			if (sp.getLevel() == 0) {
				sysPermission = sp;
			}
		}
		sysPermissionList.remove(sysPermission);
		if (sysPermission.getId() != null) {
			list2Tree(sysPermissionList, sysPermission);
			list = sysPermission.getPermissionList();
		}
		return list;
	}
	
	
	public static StatPermission getTreeMenuPermission(List<StatPermission> sysPermissionList){
		StatPermission sysPermission = new StatPermission();
		for (StatPermission sp : sysPermissionList) {
			sp.setGmtCreate(null);
			sp.setGmtUpdate(null);
			if (sp.getLevel() == 0) {
				sysPermission = sp;
			}
		}
		sysPermissionList.remove(sysPermission);
		if (sysPermission.getId() != null) {
			list2Tree(sysPermissionList, sysPermission);
		}
		return sysPermission;
	}
	
}

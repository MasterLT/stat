package com.wasu.es.mapper;

import com.wasu.es.common.MyMapper;
import com.wasu.es.model.StatPermission;

import java.util.List;

public interface StatPermissionMapper extends MyMapper<StatPermission> {

	List<StatPermission> findUserPermission(Integer userId);
	
	List<StatPermission> findRolePermission(Integer roleId);
}
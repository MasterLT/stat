package com.wasu.es.mapper;

import com.wasu.es.common.MyMapper;
import com.wasu.es.model.StatRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatRolePermissionMapper extends MyMapper<StatRolePermission> {

	StatRolePermission getRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

	void insertBatchRolePermission(List<StatRolePermission> addList);

	void deleteBatchRolePermission(List<StatRolePermission> deleteList);
}
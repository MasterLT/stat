package com.wasu.es.mapper;

import com.wasu.es.common.MyMapper;
import com.wasu.es.model.StatRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatRoleMapper extends MyMapper<StatRole> {

	/**
	 * 获取用户当前角色
	 * @param userId
	 * @return
	 */
	List<StatRole> findUserRoleList(@Param("userId") Integer userId);
}
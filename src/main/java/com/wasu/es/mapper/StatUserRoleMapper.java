package com.wasu.es.mapper;

import com.wasu.es.common.MyMapper;
import com.wasu.es.model.StatUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatUserRoleMapper extends MyMapper<StatUserRole> {

	/**
	 * 根据userid获取该用户的全部角色信息
	 * 
	 * @param userId
	 * @return
	 */
	List<StatUserRole> findUserRole(@Param("userId") Integer userId);

	/**
	 * 批量删除
	 * 
	 * @param deleteList
	 */
	void deleteBatchRole(List<StatUserRole> deleteList);

	/**
	 * 批量添加
	 * @param addList
	 */
	void insertBatchRole(List<StatUserRole> addList);
}
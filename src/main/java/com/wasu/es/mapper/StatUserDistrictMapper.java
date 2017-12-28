package com.wasu.es.mapper;

import com.wasu.es.common.MyMapper;
import com.wasu.es.model.StatUserDistrict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatUserDistrictMapper extends MyMapper<StatUserDistrict> {

	void deleteUserDistrict(@Param("userId") Integer userId);

	void insertBatchDistrict(@Param("list") List<StatUserDistrict> list);
}
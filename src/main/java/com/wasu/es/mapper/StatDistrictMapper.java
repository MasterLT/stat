package com.wasu.es.mapper;

import com.wasu.es.common.MyMapper;
import com.wasu.es.model.StatDistrict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatDistrictMapper extends MyMapper<StatDistrict> {

	List<StatDistrict> findUserDistrict(@Param("userId") Integer userId);

	List<StatDistrict> findUserDistrictList(@Param("userId") Integer userId);
}
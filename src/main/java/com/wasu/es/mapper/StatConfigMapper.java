package com.wasu.es.mapper;

import com.wasu.es.common.MyMapper;
import com.wasu.es.model.StatConfig;
import org.apache.ibatis.annotations.Param;

public interface StatConfigMapper extends MyMapper<StatConfig> {

	StatConfig getConfigByKey(@Param("sysKey") String sysKey);
}
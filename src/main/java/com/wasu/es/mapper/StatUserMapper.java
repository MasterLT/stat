package com.wasu.es.mapper;

import com.wasu.es.common.MyMapper;
import com.wasu.es.model.StatUser;

import java.util.List;

public interface StatUserMapper extends MyMapper<StatUser> {
	
	List<String> getEmailsForSengWarning();
	
}
package com.wasu.es.service.impl;

import com.wasu.es.mapper.StatDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by MASTER_L on 2017/12/5.
 */
@Service
public class RegionService {

    @Autowired
    StatDistrictMapper statDistrictMapper;

    public Object getRegionList() {
        return statDistrictMapper.selectAll();
    }
}

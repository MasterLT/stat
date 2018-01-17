package com.wasu.es.service;

import com.wasu.es.model.dto.PieChartDTO;
import org.elasticsearch.action.search.SearchResponse;
import java.util.List;

public interface IDataService {
    SearchResponse getLogsByExample(String index, String region, String rpname, String beginDate, String endDate);

    PieChartDTO getFromOrToDetail(String index, String keyword, String beginDate, String endDate,int queryType);

    List getRealName(String index, String keyword, String beginDate, String endDate);
}
package com.wasu.es.service;

import org.elasticsearch.action.search.SearchResponse;

public interface IDataService {
    SearchResponse getLogsByExample(String index, String region, String rpname, String beginDate, String endDate);

    SearchResponse getDetailFrom(String index, String keyword, String beginDate, String endDate);
}

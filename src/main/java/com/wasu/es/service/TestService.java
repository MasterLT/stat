package com.wasu.es.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

/**
 * Created by MASTER_L on 2017/12/4.
 */
@Log4j
@Service
public class TestService {
    public void esTest() {
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("125.210.126.99"), 9300));

            TermQueryBuilder queryBuilder = QueryBuilders.termQuery("multi", "test");

            SearchResponse response = client.prepareSearch("logstash-gansu-logging-*")
                    .setTypes("logs")
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(queryBuilder)                 // Query
                    .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                    .setFrom(0).setSize(60).setExplain(true)
                    .get();

            log.info(JSON.toJSON(response));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

}

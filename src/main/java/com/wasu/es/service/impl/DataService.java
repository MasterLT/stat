package com.wasu.es.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wasu.es.model.EsPosPvUv;
import com.wasu.es.service.IDataService;
import com.wasu.tool.es.EsClient;
import com.wasu.tool.es.EsDateUtil;
import com.wasu.tool.es.EsQuery;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("IDataService")
public class DataService implements IDataService {
	EsClient esClient;
	
	@PostConstruct
	public void init(){
		esClient = new EsClient();
	}
	
	@PreDestroy
	public void destory(){
		if (esClient != null) {
			log.info("esClient destory");
			esClient.destroy();
		}
	}

	public SearchResponse getLogsByExample(String index,String region, String rpname, String beginDate, String endDate) {
//		String index = "logstash-" + region + "-logging-*";
		String type = "new-logging";
		EsQuery esquery = new EsQuery();
		beginDate=beginDate.replaceAll("-", "");
		endDate=endDate.replaceAll("-", "");
		long startTime = EsDateUtil.parse(beginDate, EsDateUtil.FORMAT_3).getMillis();
		long endTime = EsDateUtil.parse(endDate, EsDateUtil.FORMAT_3).plusDays(1).getMillis();
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		builder.must(QueryBuilders.rangeQuery("@timestamp").from(startTime).to(endTime));
		builder.must(QueryBuilders.termQuery("rpname.raw", rpname));
		esquery.setQuery(builder);
		esquery.setPageSize(0);

		TermsAggregationBuilder aggs = AggregationBuilders.terms("datas").field("pagecode.raw").size(100);
		aggs.subAggregation(AggregationBuilders.cardinality("uv").field("stbid.raw"));
		aggs.subAggregation(AggregationBuilders.terms("name").field("cpname.raw")
				.subAggregation(AggregationBuilders.cardinality("uv").field("stbid.raw")));

		SearchResponse resp1 = esClient.searchByAggs(index, type, esquery, aggs);
		// System.out.println("result:" + resp1.toString());
		return resp1;
	}

	private List<EsPosPvUv> build(JSONArray obj, SearchResponse searchResponse) {
		List<EsPosPvUv> result = new ArrayList<EsPosPvUv>();

		StringTerms internalTerms = searchResponse.getAggregations().get("datas");
		for (Bucket item : internalTerms.getBuckets()) {
			InternalCardinality uv = item.getAggregations().get("uv");
			// System.out.print("key:" + item.getKeyAsString() + "uv:" +
			// uv.getValue() + "pv:" + item.getDocCount());
			StringTerms iTerms = item.getAggregations().get("name");

			for (int j = 0; j < obj.size(); j++) {
				JSONObject job = obj.getJSONObject(j); // 遍历 jsonarray

				if (item.getKeyAsString().equals(job.get("id"))) {
					for (Bucket i : iTerms.getBuckets()) {
						EsPosPvUv esDataMapDO = new EsPosPvUv();
						esDataMapDO.setId(item.getKeyAsString());
						esDataMapDO.setName(i.getKeyAsString());
						esDataMapDO.setLeft(job.getString("left"));
						esDataMapDO.setTop(job.getString("top"));
						esDataMapDO.setPv(item.getDocCount() + "");
						esDataMapDO.setUv(uv.getValue() + "");
						result.add(esDataMapDO);
						InternalCardinality iuv = i.getAggregations().get("uv");

						// System.out.print("name:" + i.getKeyAsString() + "uv:"
						// + iuv.getValue() + "pv:" + i.getDocCount());
					}
				}
			}
		}
		// System.out.println("result:" + result.toString());
		return result;

	}

	private static void test() {
		String index = "logstash-gansu-logging-2017.10.20";
		String type = "logs";
		EsQuery esquery = new EsQuery();
		long startTime = EsDateUtil.parse("20171020", EsDateUtil.FORMAT_3).getMillis();
		long endTime = EsDateUtil.parse("20171021", EsDateUtil.FORMAT_3).getMillis();
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		builder.must(QueryBuilders.rangeQuery("@timestamp").from(startTime).to(endTime));
		builder.must(QueryBuilders.termQuery("rpname.keyword", "甘肃首页3.1版本"));
		esquery.setQuery(builder);
		esquery.setPageSize(0);

		TermsAggregationBuilder aggs = AggregationBuilders.terms("datas").field("pagecode.keyword").size(100);
		aggs.subAggregation(AggregationBuilders.cardinality("uv").field("stbid.keyword"));
		aggs.subAggregation(AggregationBuilders.terms("name").field("cpname.keyword")
				.subAggregation(AggregationBuilders.cardinality("uv").field("stbid.keyword")));

		EsClient esClient = new EsClient();
		SearchResponse resp1 = esClient.searchByAggs(index, type, esquery, aggs);

		StringTerms internalTerms = resp1.getAggregations().get("datas");
		for (Bucket item : internalTerms.getBuckets()) {
			InternalCardinality uv = item.getAggregations().get("uv");
			System.out.print("key:" + item.getKeyAsString() + "uv:" + uv.getValue() + "pv:" + item.getDocCount());
			StringTerms iTerms = item.getAggregations().get("name");
			for (Bucket i : iTerms.getBuckets()) {
				InternalCardinality iuv = i.getAggregations().get("uv");
				System.out.print("name:" + i.getKeyAsString() + "uv:" + iuv.getValue() + "pv:" + i.getDocCount());
			}
			System.out.println("--------------");
		}
		System.out.println("result:" + resp1.toString());

	}

}

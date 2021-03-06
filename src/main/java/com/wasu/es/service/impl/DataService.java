package com.wasu.es.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wasu.es.enums.AnalysisEnum;
import com.wasu.es.mapper.StatDailyAnalysisMapper;
import com.wasu.es.model.EsPosPvUv;
import com.wasu.es.model.LogModel;
import com.wasu.es.model.StatDailyAnalysis;
import com.wasu.es.model.dto.DatatablesViewPage;
import com.wasu.es.model.dto.PieChartDTO;
import com.wasu.es.model.dto.ResourceDTO;
import com.wasu.es.service.IDataService;
import com.wasu.es.utils.DateUtils;
import com.wasu.es.utils.EsUtils;
import com.wasu.es.utils.HttpHelper;
import com.wasu.tool.es.EsClient;
import com.wasu.tool.es.EsDateUtil;
import com.wasu.tool.es.EsQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("IDataService")
public class DataService implements IDataService {
    EsClient esClient;
    @Autowired
    StatDailyAnalysisMapper statDailyAnalysisMapper;


    @PostConstruct
    public void init() {
        esClient = new EsClient();
    }

    @PreDestroy
    public void destory() {
        if (esClient != null) {
            log.info("esClient destory");
            esClient.destroy();
        }
    }

    /**
     * 查询页面各个按钮点击的pv，uv
     *
     * @param index
     * @param region
     * @param rpname
     * @param beginDate
     * @param endDate
     * @return
     */
    public SearchResponse getLogsByExample(String index, String region, String rpname, String beginDate, String endDate) {
//		String index = "logstash-" + region + "-logging-*";
        String type = "new-logging";
        EsQuery esquery = new EsQuery();
        beginDate = beginDate.replaceAll("-", "");
        endDate = endDate.replaceAll("-", "");
        long startTime = EsDateUtil.parse(beginDate, EsDateUtil.FORMAT_3).getMillis();
        long endTime = EsDateUtil.parse(endDate, EsDateUtil.FORMAT_3).plusDays(1).getMillis();
        List<String> list = DateUtils.getAllDayString(startTime, endTime);
        String[] indexs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            indexs[i] = index + list.get(i);
        }
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
//        builder.must(QueryBuilders.rangeQuery("@timestamp").from(startTime).to(endTime));
        builder.must(QueryBuilders.termQuery("rpname.raw", rpname));
        esquery.setQuery(builder);
        esquery.setPageSize(0);
        TermsAggregationBuilder aggs = AggregationBuilders.terms("datas").field("pagecode.raw").size(100);
        aggs.subAggregation(AggregationBuilders.cardinality("uv").field("stbid.raw"));
        aggs.subAggregation(AggregationBuilders.terms("name").field("cpname.raw")
                .subAggregation(AggregationBuilders.cardinality("uv").field("stbid.raw")));

        SearchResponse resp1 = esClient.searchByAggs(indexs, type, esquery, aggs);
        // System.out.println("result:" + resp1.toString());
        return resp1;
    }

    /**
     * 查询当前页面上下游页面的pv及uv
     *
     * @param index
     * @param keyword
     * @param beginDate
     * @param endDate
     * @param queryType 1:上游；2下游
     * @return
     */
    public PieChartDTO getFromOrToDetail(String index, String keyword, String beginDate, String endDate, int queryType) {
        PieChartDTO pieChartDTO = new PieChartDTO();
        String queryField = "cpcode.raw";
        String aggField = "rpcode.raw";
        pieChartDTO.setTitle(keyword + "上游统计");
        if (queryType == 2) {
            queryField = "rpcode.raw";
            aggField = "cpcode.raw";
            //如果是下游统计则剔除第三位置字段
            // 例如“少儿电影详情页^小马宝莉大电影4无尽森林传说^海报列表”
            //中“^海报列表”为上页面的位置信息，因此统计时需要截掉
            if (keyword.split("\\^").length == 3) {
                keyword = keyword.split("\\^")[0] + "^" + keyword.split("\\^")[1];
            }
            pieChartDTO.setTitle(keyword + "下游统计");
        }
        List<Map> list = Lists.newArrayList();
        List<String> tiles = Lists.newArrayList();
        double total = 0;
        if (!StringUtils.isEmpty(keyword) && !StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)) {
            String type = "new-logging";
            EsQuery esquery = new EsQuery();
            beginDate = beginDate.replaceAll("-", "");
            endDate = endDate.replaceAll("-", "");
            long startTime = EsDateUtil.parse(beginDate, EsDateUtil.FORMAT_3).getMillis();
            long endTime = EsDateUtil.parse(endDate, EsDateUtil.FORMAT_3).plusDays(1).getMillis();
            //筛选时间和模糊查询关键字
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(QueryBuilders.rangeQuery("@timestamp").from(startTime).to(endTime));
            builder.must(QueryBuilders.wildcardQuery(queryField, keyword + "*"));
//            builder.must(QueryBuilders.termQuery(queryField, keyword ));
            esquery.setQuery(builder);
            esquery.setPageSize(0);
            //先对cpcode分组得出匹配的各页面
//            TermsAggregationBuilder aggs = AggregationBuilders.terms("pages").field("cpcode.raw");
//            //再对rpcode分组得到该页面的各个上级页面
//            TermsAggregationBuilder subaggs = AggregationBuilders.terms("beforpages").field("rpcode.raw");
//            subaggs.subAggregation(AggregationBuilders.cardinality("uv").field("stbid.raw"));
//            aggs.subAggregation(subaggs);
            //对rpcode分组得到该页面的各个上级页面
            TermsAggregationBuilder aggs = AggregationBuilders.terms("page").field(aggField);
            aggs.subAggregation(AggregationBuilders.cardinality("uv").field("stbid.raw"));
            SearchResponse resp = esClient.searchByAggs(index, type, esquery, aggs);
            StringTerms terms = EsUtils.getAggFromResult(resp, "page");
            for (Bucket bucket : terms.getBuckets()) {
                String name = bucket.getKeyAsString();
                Integer pv = (int) bucket.getDocCount();
                Integer uv = 0;
                if (bucket.getAggregations() != null) {
                    InternalCardinality i = bucket.getAggregations().get("uv");
                    uv = (int) i.getValue();
                }
                Map map = Maps.newHashMap();
                map.put("value", pv);
                map.put("name", name);
                list.add(map);
                total += pv;
            }
            computeTitles(list, tiles, total);
            pieChartDTO.setVertical(tiles);
            pieChartDTO.setData(list);
        }
        return pieChartDTO;
    }

    /**
     * 查询当前页面下游页面的pv及uv
     *
     * @param index
     * @param keyword
     * @param beginDate
     * @param endDate
     * @return
     */
    public PieChartDTO getToDetail(String index, String keyword, String beginDate, String endDate) {
        PieChartDTO pieChartDTO = new PieChartDTO();
        pieChartDTO.setTitle(keyword + "下游统计");
        List<Map> list = Lists.newArrayList();
        List<String> tiles = Lists.newArrayList();
        String[] keys = keyword.split("\\^");
        double total = 0;
        if (keys.length == 2 && !StringUtils.isEmpty(keyword) && !StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)) {
            String type = "new-logging";
            EsQuery esquery = new EsQuery();
            beginDate = beginDate.replaceAll("-", "");
            endDate = endDate.replaceAll("-", "");
            long startTime = EsDateUtil.parse(beginDate, EsDateUtil.FORMAT_3).getMillis();
            long endTime = EsDateUtil.parse(endDate, EsDateUtil.FORMAT_3).plusDays(1).getMillis();
            //筛选时间和模糊查询关键字
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(QueryBuilders.rangeQuery("@timestamp").from(startTime).to(endTime));
            builder.must(QueryBuilders.termQuery("rpcode.raw", keyword));
            esquery.setQuery(builder);
            esquery.setPageSize(0);
            //对rpcode分组得到该页面的各个上级页面
            TermsAggregationBuilder aggs = AggregationBuilders.terms("type").field("cptype.raw");
            aggs.subAggregation(AggregationBuilders.terms("page").field("cpname.raw")
                    .subAggregation(AggregationBuilders.cardinality("uv").field("stbid.raw")));
            SearchResponse resp = esClient.searchByAggs(index, type, esquery, aggs);
            StringTerms terms = EsUtils.getAggFromResult(resp, "type");
            for (Bucket b : terms.getBuckets()) {
                String cptype = b.getKeyAsString();
                StringTerms subterms = b.getAggregations().get("page");
                for (Bucket bucket : subterms.getBuckets()) {
                    String name = cptype + "^" + bucket.getKeyAsString();
                    Integer pv = (int) bucket.getDocCount();
                    Integer uv = 0;
                    if (bucket.getAggregations() != null) {
                        InternalCardinality i = bucket.getAggregations().get("uv");
                        uv = (int) i.getValue();
                    }
                    Map map = Maps.newHashMap();
                    map.put("value", pv);
                    map.put("name", name);
                    list.add(map);
                    total += pv;
                }
            }
            computeTitles(list, tiles, total);
            pieChartDTO.setVertical(tiles);
            pieChartDTO.setData(list);
        }
        return pieChartDTO;
    }

    /**
     * 计算百分比
     *
     * @param list
     * @param tiles
     * @param total
     */
    public void computeTitles(List<Map> list, List<String> tiles, double total) {
        DecimalFormat df = new DecimalFormat("######0.00");
        for (Map map : list) {
            String name = (String) map.get("name");
            int pv = (int) map.get("value");
            double p = pv * 100 / total;
            String title = name + "(" + df.format(p) + "%)";
            map.put("name", title);
            tiles.add(title);
        }

    }

    /**
     * 模糊查询获得具体名称
     *
     * @param index
     * @param keyword
     * @return
     */
    @Override
    public List getRealName(String index, String keyword, String beginDate, String endDate) {
        List<Map> list = Lists.newArrayList();
        String type = "new-logging";
        EsQuery esquery = new EsQuery();
        beginDate = beginDate.replaceAll("-", "");
        endDate = endDate.replaceAll("-", "");
        long startTime = EsDateUtil.parse(beginDate, EsDateUtil.FORMAT_3).getMillis();
        long endTime = EsDateUtil.parse(endDate, EsDateUtil.FORMAT_3).plusDays(1).getMillis();
        //筛选时间和模糊查询关键字
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.rangeQuery("@timestamp").from(startTime).to(endTime));
        builder.must(QueryBuilders.wildcardQuery("rpname.raw", "*" + keyword + "*"));
        builder.must(QueryBuilders.wildcardQuery("rptype.raw", "*详?页"));
        esquery.setQuery(builder);
        esquery.setPageSize(0);
        //先对cpcode分组得出匹配的各页面
        TermsAggregationBuilder aggs = AggregationBuilders.terms("pages").field("rpcode.raw");
        SearchResponse resp = esClient.searchByAggs(index, type, esquery, aggs);
        StringTerms terms = EsUtils.getAggFromResult(resp, "pages");
        for (Bucket bucket : terms.getBuckets()) {
            String rpcode = bucket.getKeyAsString();
            Map map = new HashMap();
            map.put("value", rpcode);
            map.put("label", rpcode);
            list.add(map);
        }
        return list;
    }

    @Override
    public Object getAnalysis(String region, String day) {
        tk.mybatis.mapper.entity.Example example = new tk.mybatis.mapper.entity.Example(StatDailyAnalysis.class);
        example.createCriteria().andEqualTo("region", region).andEqualTo("day", day).andEqualTo("type", AnalysisEnum.INDEX.getType());
        List<StatDailyAnalysis> list = statDailyAnalysisMapper.selectByExample(example);
        if (list.size() > 0)
            return list.get(0).getValue();
        else return null;
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

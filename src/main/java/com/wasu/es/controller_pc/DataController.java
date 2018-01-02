package com.wasu.es.controller_pc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wasu.es.controller.ShiroDemoController;
import com.wasu.es.mapper.StatRegionMapper;
import com.wasu.es.model.EsPosPvUv;
import com.wasu.es.model.StatRegion;
import com.wasu.es.model.dto.EsNamePvUv;
import com.wasu.es.service.IDataService;
import com.wasu.es.utils.GodUtils;
import com.wasu.es.utils.HttpHelper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("data")
public class DataController {
    private static final transient Logger log = LoggerFactory.getLogger(ShiroDemoController.class);

    @Resource
    private IDataService iDataService;

    @Value("${mapDataUrl}")
    private String mapDataUrl;

    @Resource
    private StatRegionMapper statRegionMapper;

    @RequestMapping(value = "/getDetailFromData/{region}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDetailFromData(@PathVariable String region, HttpServletRequest request) throws Exception {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        //获取位置信息
        String url = mapDataUrl + region + "-" + endDate + ".json";
        JSONArray obj = HttpHelper.httpGetArray(url);

        String keyword = request.getParameter("keyword");

        //获取es信息
        SearchResponse searchResponse = iDataService.getDetailFrom("logstash-" + region + "-logging-*", keyword, beginDate, endDate);
        if (searchResponse == null) {
            return null;
        }

        //拼装信息
        List<EsPosPvUv> result = build(obj, searchResponse);

        return result;
    }

    @RequestMapping(value = "/getIndexData/{region}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getMapData(@PathVariable String region, HttpServletRequest request) throws Exception {
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        //获取位置信息
        String url = mapDataUrl + region + "-" + endDate + ".json";
        JSONArray obj = HttpHelper.httpGetArray(url);

        String rpname = getRpnameByRegion(region);

        //获取es信息
        SearchResponse searchResponse = iDataService.getLogsByExample("logstash-" + region + "-logging-*", region, rpname, beginDate, endDate);
        if (searchResponse == null) {
            return null;
        }

        //拼装信息
        List<EsPosPvUv> result = build(obj, searchResponse);

        return result;
    }

    private List<EsPosPvUv> build(JSONArray obj, SearchResponse searchResponse) {
        List<EsPosPvUv> result = new ArrayList<EsPosPvUv>();

        StringTerms internalTerms = searchResponse.getAggregations().get("datas");
        for (Bucket item : internalTerms.getBuckets()) {
            boolean isEqual = false;
            InternalCardinality uv = item.getAggregations().get("uv");
//			System.out.print("key:" + item.getKeyAsString() + "uv:" + uv.getValue() + "pv:" + item.getDocCount());
            StringTerms iTerms = item.getAggregations().get("name");

            EsPosPvUv esDataMapDO = new EsPosPvUv();
            esDataMapDO.setId(item.getKeyAsString());
            esDataMapDO.setPv(item.getDocCount() + "");
            esDataMapDO.setUv(uv.getValue() + "");

            for (int j = 0; j < obj.size(); j++) {
                JSONObject job = obj.getJSONObject(j); // 遍历 jsonarray
                if (item.getKeyAsString().equals(job.get("id"))) {
                    esDataMapDO.setLeft(job.getString("left"));
                    esDataMapDO.setTop(job.getString("top"));
                    isEqual = true;
                    break;
                }
            }

            if (isEqual) {
                List<EsNamePvUv> nameList = new ArrayList<EsNamePvUv>();
                for (Bucket i : iTerms.getBuckets()) {
                    EsNamePvUv name = new EsNamePvUv();
                    name.setName(i.getKeyAsString());
                    name.setPv(i.getDocCount() + "");
                    InternalCardinality nameUv = i.getAggregations().get("uv");
                    name.setUv(nameUv.getValue() + "");
                    nameList.add(name);
                }
                esDataMapDO.setList(nameList);

                result.add(esDataMapDO);
            }
        }
        return result;
    }

    /**
     * 根据region获取rpname
     *
     * @param region
     * @return
     */
    private String getRpnameByRegion(String region) {
        String rpname = new String();
        Example example = new Example(StatRegion.class);
        example.createCriteria().andCondition("region =", region);
        List<StatRegion> sRegionList = statRegionMapper.selectByExample(example);
        if (!GodUtils.CheckNull(sRegionList)) {
            StatRegion sRegion = sRegionList.get(0);
            rpname = sRegion.getRpname();
        }
        return rpname;
    }
}

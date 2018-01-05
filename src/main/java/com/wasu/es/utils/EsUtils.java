package com.wasu.es.utils;

import com.wasu.es.common.Constants;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EsUtils {

    public static Double getLostPercent(Integer peak, Map<String, Object> map) {
        Double lost = 0.0;
        if (peak == Constants.ES_DAY_TYPE_ALL) {
            lost = (Double) map.get("lost_percent");
        } else if (peak == Constants.ES_DAY_TYPE_DAY) {
            lost = (Double) map.get("day_lost_percent");
        } else {
            lost = (Double) map.get("night_lost_percent");
        }
        return lost;
    }

    public static Integer getTestFailNum(Integer peak, Map<String, Object> map) {
        Integer lostTestSum = 0;
        if (peak == Constants.ES_DAY_TYPE_ALL) {
            lostTestSum = (Integer) map.get("test_fail_num");
        } else if (peak == Constants.ES_DAY_TYPE_DAY) {
            lostTestSum = (Integer) map.get("day_test_fail_num");
        } else {
            lostTestSum = (Integer) map.get("night_test_fail_num");
        }
        return lostTestSum;
    }

    public static Integer getTestNum(Integer peak, Map<String, Object> map) {
        Integer testSum = 0;
        if (peak == Constants.ES_DAY_TYPE_ALL) {
            testSum = (Integer) map.get("test_num");
        } else if (peak == Constants.ES_DAY_TYPE_DAY) {
            testSum = (Integer) map.get("day_test_num");
        } else {
            testSum = (Integer) map.get("night_test_num");
        }
        return testSum;
    }

    public static Integer getFailUserNum(Integer peak, Map<String, Object> map) {
        Integer testSum = 0;
        if (peak == Constants.ES_DAY_TYPE_ALL) {
            testSum = (Integer) map.get("fail_user_num");
        } else if (peak == Constants.ES_DAY_TYPE_DAY) {
            testSum = (Integer) map.get("day_fail_user_num");
        } else {
            testSum = (Integer) map.get("night_fail_user_num");
        }
        return testSum;
    }

    public static StringTerms getAggFromResult(SearchResponse response, String args) {
        StringTerms internalTerms = response.getAggregations().get(args);
        return internalTerms;
    }

    public static <T> List<T> getListFromResult(SearchResponse response, Class<T> c) {
        List<T> list = new ArrayList<>();
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits.getHits()) {
            Object o = null;
            try {
                o = c.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Map<String, Object> map = hit.getSource();
            for (String k : map.keySet()) {
                setValue(o, k, map.get(k));
            }
            list.add((T) o);
        }
        return list;
    }

    private static void setValue(Object o, String key, Object value) {
        try {
            Field field = o.getClass().getDeclaredField(key.replaceAll("@", ""));
            field.setAccessible(true);
            field.set(o, castToClass(field.getType(), value));
        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static <T> T castToClass(Class<T> clazz, Object value) {
        return (T) value;
    }
}

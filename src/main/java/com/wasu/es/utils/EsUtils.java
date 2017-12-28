package com.wasu.es.utils;

import com.wasu.es.common.Constants;

import java.util.Map;

public class EsUtils {

	public static Double getLostPercent(Integer peak, Map<String, Object> map) {
		Double lost = 0.0;
		if (peak == Constants.ES_DAY_TYPE_ALL) {
			lost = (Double) map.get("lost_percent");
		}else if (peak == Constants.ES_DAY_TYPE_DAY) {
			lost = (Double) map.get("day_lost_percent");
		}else {
			lost = (Double) map.get("night_lost_percent");
		}
		return lost;
	}

	public static Integer getTestFailNum(Integer peak, Map<String, Object> map) {
		Integer lostTestSum = 0;
		if (peak == Constants.ES_DAY_TYPE_ALL) {
			lostTestSum = (Integer) map.get("test_fail_num");
		}else if (peak == Constants.ES_DAY_TYPE_DAY) {
			lostTestSum = (Integer) map.get("day_test_fail_num");
		}else {
			lostTestSum = (Integer) map.get("night_test_fail_num");
		}
		return lostTestSum;
	}

	public static Integer getTestNum(Integer peak, Map<String, Object> map) {
		Integer testSum = 0;
		if (peak == Constants.ES_DAY_TYPE_ALL) {
			testSum = (Integer) map.get("test_num");
		}else if (peak == Constants.ES_DAY_TYPE_DAY) {
			testSum = (Integer) map.get("day_test_num");
		}else {
			testSum = (Integer) map.get("night_test_num");
		}
		return testSum;
	}
	
	public static Integer getFailUserNum(Integer peak, Map<String, Object> map) {
		Integer testSum = 0;
		if (peak == Constants.ES_DAY_TYPE_ALL) {
			testSum = (Integer) map.get("fail_user_num");
		}else if (peak == Constants.ES_DAY_TYPE_DAY) {
			testSum = (Integer) map.get("day_fail_user_num");
		}else {
			testSum = (Integer) map.get("night_fail_user_num");
		}
		return testSum;
	}
}

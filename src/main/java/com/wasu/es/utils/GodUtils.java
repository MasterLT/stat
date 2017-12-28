package com.wasu.es.utils;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GodUtils {

	public static Logger log = Logger.getLogger(GodUtils.class);

	/**
	 * @param list
	 * @return
	 */
	public static boolean CheckNull(List<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean CheckNull(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean CheckNull(String str) {
		if (str == null) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static boolean CheckNull(Integer it) {
		if (it == null || it == 0) {
			return true;
		}
		return false;
	}

	public static boolean CheckNull(Object[] array) {
		if (array == null || array.length <= 0) {
			return true;
		}
		return false;
	}

	public static boolean CheckIsNumber(String number) {

		if (number == null || number == "") {

			return false;
		}

		for (int i = 0; i < number.length(); i++) {

			if (Character.isDigit(number.charAt(i))) {

				return true;
			}

		}
		return false;
	}

	/**
	 * 比较连个Integer是否相等
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static boolean sameInteger(Integer t1, Integer t2) {
		if (t1 == null && t2 == null) {
			// TODO 都为null的情况
			return true;
		}
		if (t1 == null || t2 == null) {
			return false;
		}
		if (t1.equals(t2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * json转string，转码
	 * 
	 * @param obj
	 * @return
	 */
	public static String JsonToString(Object obj) {
		String json = "";
		if (obj instanceof String) {
			json = Encoder(obj.toString(), "gb2312");
		} else if (obj instanceof JSONArray) {
			json = Encoder(obj.toString(), "gb2312");
		}
		return json;
	}

	public static String Encoder(String str, String Code) {
		String result = "";
		try {
			result = URLEncoder.encode(str, "gb2312");
		} catch (UnsupportedEncodingException e) {
			System.out.println("转码异常");
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> String2List(String str, Class<T> t) {
		List<T> list = new ArrayList<T>();
		if (CheckNull(str)) {
			return list;
		}
		String[] s = str.split(",");
		for (String string : s) {
			list.add((T) string);
		}
		return list;
	}

	/**
	 * String逗号隔开,组成列表
	 * 
	 * @param str
	 * @return
	 */
	public static List<Integer> String2ListInt(String str) {
		List<Integer> list = new ArrayList<Integer>();
		if (CheckNull(str)) {
			return list;
		}
		String[] s = str.split(",");
		for (String string : s) {
			Integer tmp = NumberUtils.toInt(string, -1);
			if (tmp == -1) {
				continue;
			}
			list.add(tmp);
		}
		return list;
	}

	/**
	 * String逗号隔开,组成列表
	 * 
	 * @param str
	 * @param stitch
	 *            前缀后缀
	 * @return
	 */
	public static List<Integer> String2ListInt(String str, String stitch) {
		List<Integer> list = new ArrayList<Integer>();
		if (str == null) {
			return list;
		}
		String[] s = str.split(",");
		for (String string : s) {
			string = string.replace(stitch, "");
			Integer tmp = NumberUtils.toInt(string, -1);
			if (tmp == -1) {
				continue;
			}
			list.add(tmp);
		}
		return list;
	}

	/**
	 * 数组转换成字符串,逗号隔开
	 * 
	 * @param intList
	 * @return
	 */
	public static String ListInt2String(List<Integer> intList) {
		StringBuffer result = new StringBuffer();
		if (GodUtils.CheckNull(intList)) {
			return "";
		}
		for (int i = 0; i < intList.size(); i++) {
			result.append(intList.get(i));
			if (i < intList.size() - 1) {
				result.append(",");
			}
		}
		return result.toString();
	}

	/**
	 * 数组转换成字符串,逗号隔开
	 * 
	 * @param intList
	 * @param stitch
	 *            前缀后缀
	 * @return
	 */
	public static String ListInt2String(List<Integer> intList, String stitch) {
		StringBuffer result = new StringBuffer();
		if (GodUtils.CheckNull(intList)) {
			return "";
		}
		for (int i = 0; i < intList.size(); i++) {
			result.append(stitch).append(intList.get(i)).append(stitch);
			if (i < intList.size() - 1) {
				result.append(",");
			}
		}
		return result.toString();
	}

	/**
	 * map's to say
	 * 
	 * @param map
	 * @return
	 */
	public static String Map2Say(Map<Object, Object> map) {
		StringBuffer result = new StringBuffer("{");
		if (GodUtils.CheckNull(map)) {
			return "";
		}
		Iterator<Object> it = map.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			result.append(key + "@" + map.get(key) + "|");
		}
		result.append("}");
		return result.toString();
	}

	public String getRootPath() {
		String rootPath = getClass().getResource("/").toString();
		System.out.println("======================");
		System.out.println("rootPath =" + rootPath);
		System.out.println("======================");

		rootPath = rootPath.split("WEB-INF")[0];
		rootPath = rootPath.substring(6);

		System.out.println("======================");
		System.out.println("rootPath =" + rootPath);
		System.out.println("======================");
		return rootPath;
	}

	/**
	 * 校验是否手机号
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobileNO(String mobile) {
		boolean result = false;
		/* 精确校验号码段 */
		StringBuffer regExp = new StringBuffer("^[1]([3][0-9]{1}");
		regExp.append("|45|47|50|51|52|53|55|56|57|58|59");
		regExp.append("|73|76|77|78");
		regExp.append("[8][0-9]{1})");
		regExp.append("[0-9]{8}$");
		Pattern p = Pattern.compile(regExp.toString());
		Matcher m = p.matcher(mobile);
		result = m.matches();
		if (!result) {
			/* 仅校验11位数字 */
			regExp = new StringBuffer("^[0-9]{11}$");
			p = Pattern.compile(regExp.toString());
			m = p.matcher(mobile);
			result = m.matches();
			if (result) {
				GodUtils.log.warn("号码段未知:" + mobile);
			}
		}
		return result;
	}

	/**
	 * 只校验位数和是否纯数字
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobileSimple(String mobile) {
		StringBuffer regExp = new StringBuffer("^[0-9]{11}$");
		Pattern p = Pattern.compile(regExp.toString());
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 判断字符串,是否在数组内
	 * 
	 * @param <T>
	 * 
	 * @param str
	 * @param arr
	 * @return
	 */
	public static <T> boolean objInArray(T obj, T[] arr) {
		if (GodUtils.CheckNull(arr)) {
			return false;
		}
		for (T el : arr) {
			if (obj == el || obj != null && obj.equals(el) || el != null && el.equals(obj)) {
				return true;
			}
		}
		return false;
	}
	
	// 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    /**
     * 完整的判断中文汉字和符号
     * @param strName
     * @return
     */
    public static boolean isChinese(String strName) {
    	if (!GodUtils.CheckNull(strName)) {
    		char[] ch = strName.toCharArray();
    		for (int i = 0; i < ch.length; i++) {
    			char c = ch[i];
    			if (isChinese(c)) {
    				return true;
    			}
    		}
		}
        return false;
    }

	public static void main(String[] args) {
		System.out.println("15158136199|" + GodUtils.isMobileNO("15158136199"));
		System.out.println("asdasdf|" + GodUtils.isMobileNO("asdfasdf"));
		System.out.println("15415145245|" + GodUtils.isMobileNO("15415145245"));
		String test = "1,2,3,4";
		List<Integer> intList = GodUtils.String2ListInt(test);
		test = GodUtils.ListInt2String(intList, "|");
		intList = GodUtils.String2ListInt(test, "|");
		System.out.println();
		
		System.out.println("test|"+ GodUtils.isChinese("test"));
		System.out.println("中文|"+ GodUtils.isChinese("中文"));
	}
}

package com.wasu.es.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 使用phantomjs实现网页转图片等功能<br>
 * 1、需配置phantomjs环境变量<br>
 * 2、执行的脚本js需放在用户根目录-》phantomjs下<br>
 * 
 * @see http://www.cnblogs.com/lekko/p/4796062.html
 * @see http://blog.csdn.net/mingzznet/article/details/51385374
 * @author wenguang
 * @date 2017年11月6日
 */
@Slf4j
public class PhantomTool {
	public static final String USER_HOME = System.getProperty("user.home");
	public static final String PHANTOM_HOME = "C:/Users/wenguang/Desktop/2016/tec/phantomjs/phantomjs-2.1.1-windows/bin/";
	private static final String COMMAND_BASE = "phantomjs";// 需配置phantomjs环境变量
	private static final String COMMAND_CAPTURE = USER_HOME + File.separator + "phantom" + File.separator
			+ "rasterize.js";
	private static final String COMMAND_POSITION = USER_HOME + File.separator + "phantom" + File.separator
			+ "position.js";

	/**
	 * 执行Phantom命令
	 * 
	 * @param command
	 *            命令
	 * @return 执行结果
	 */
	public static CmdTool.ExecInfo cmd(String command) {
		Preconditions.checkNotNull(command, "command can't be null");
		StringBuilder cmd = new StringBuilder(COMMAND_BASE).append(StringUtils.SPACE).append(command);
		return CmdTool.exec(cmd.toString());
	}

	/**
	 * 捕获网页并保存
	 * 
	 * @param url
	 *            网页地址
	 * @param filePath
	 *            图片保存路径
	 * @param size
	 *            图片的大小，如800px*600px（此时高度会裁切），或800px（此时 高度最少=宽度*9/16，高度不裁切）
	 * @return 是否成功
	 */
	public static boolean capture(String url, String filePath, String size) {
		Preconditions.checkNotNull(url, "url can't be null");
		Preconditions.checkNotNull(filePath, "filePath can't be null");

		StringBuilder cmd = new StringBuilder(COMMAND_BASE).append(StringUtils.SPACE).append(COMMAND_CAPTURE)
				.append(StringUtils.SPACE).append(url).append(StringUtils.SPACE).append(filePath);
		if (StringUtils.isNotEmpty(size))
			cmd.append(StringUtils.SPACE).append(size);
		CmdTool.ExecInfo execInfo = CmdTool.exec(cmd.toString());
		log.debug("capture resp:" + execInfo.getResp());
		if (!execInfo.isResult()) {
			log.warn(execInfo.getMsg());
		}
		return execInfo.result;
	}

	/**
	 * 获取网页元素的坐标位置信息
	 * 
	 * @param url
	 *            网页地址
	 * @param querySelector
	 *            样式筛选器,默认#nav1>div,#nav2,#nav3>div,#nav4>div,#nav5>div,#nav6>div
	 * @return 位置信息
	 */
	public static List<PositionInfo> position(String url, String querySelector) {
		Preconditions.checkNotNull(url, "url can't be null");
		List<PositionInfo> list = Lists.newArrayList();
		StringBuilder cmd = new StringBuilder(COMMAND_BASE).append(StringUtils.SPACE).append(COMMAND_POSITION)
				.append(StringUtils.SPACE).append(url);
		if (StringUtils.isNotEmpty(querySelector))
			cmd.append(StringUtils.SPACE).append(querySelector);
		CmdTool.ExecInfo execInfo = CmdTool.exec(cmd.toString());
		log.debug("position resp:" + execInfo.getResp());
		if (!execInfo.isResult()) {
			log.warn(execInfo.getMsg());
		}
		if (execInfo.isResult() && StringUtils.isNotEmpty(execInfo.getResp())) {
			String body = StringUtils.substringBetween(execInfo.getResp(), "{{", "}}");
			if (StringUtils.isNotEmpty(body)) {
				list = JSON.parseArray(body, PositionInfo.class);
			}
		}
		return list;
	}

	@Data
	public static class PositionInfo {
		String id;
		String left;
		String top;
	}

	public static void main(String[] args) {
		String url = "http://www.utc.gscatv.cn/a?f=gansuvsite&profile=GANSUNEW22SCENARIO";// 甘肃
		String key = "gansu";
		String baseFilePath = "/data/upload/stat/";
		if (args != null && args.length >= 2) {
			url = args[0];
			key = args[1];
		}

		// url = "http://nanchang-utc.wasu.cn/index.jsp";// 南昌
		// key = "nanchang";
		String picFilePath = baseFilePath + key + ".png";
		String jsonFilePath = baseFilePath + key + ".json";
		try {
			capture(url, picFilePath, null);
			List<PositionInfo> list = position(url, null);
			if (CollectionUtils.isNotEmpty(list)) {
				FileUtils.writeStringToFile(new File(jsonFilePath), JSON.toJSONString(list), Charset.defaultCharset(),
						false);
			}
		} catch (Exception e) {
			log.error("run Phantom error", e);
		}

	}
}

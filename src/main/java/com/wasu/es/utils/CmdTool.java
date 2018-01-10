/**
 * 
 */
package com.wasu.es.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import lombok.Data;

/**
 * 执行命令行工具类
 * 
 * @author wenguang
 * @date 2017年9月22日
 */
public class CmdTool {
	public final static String LINE = System.getProperty("line.separator");
	public final static long WAIT_SECONDS = 60*5;

	/**
	 * 执行命令行
	 */
	public static ExecInfo exec(String cmd) {
		ExecInfo execInfo = new ExecInfo(false);
		Process proc;
		try {
			Runtime runtime = Runtime.getRuntime();
			proc = runtime.exec(cmd);
			proc.waitFor(WAIT_SECONDS, TimeUnit.SECONDS);
			int exit = proc.exitValue();
			if (exit == 0) {
				execInfo.setResp(readInput(proc));
			} else {
				execInfo.setMsg("命令执行失败:exitValue=" + exit + ",ErrorStream="
						+ IOUtils.readLines(proc.getErrorStream(), Charset.defaultCharset()).toString());
			}
			execInfo.setResult(exit == 0);
		} catch (Exception e) {
			execInfo.setMsg("系统异常:" + e.getMessage());
		}
		return execInfo;
	}

	/**
	 * 执行命令行
	 */
	public static ExecInfo exec(String[] cmd) {
		ExecInfo execInfo = new ExecInfo(false);
		Process proc;
		try {
			Runtime runtime = Runtime.getRuntime();
			proc = runtime.exec(cmd);
			proc.waitFor(WAIT_SECONDS, TimeUnit.SECONDS);
			int exit = proc.exitValue();
			if (exit == 0) {
				execInfo.setResp(readInput(proc));
			} else {
				execInfo.setMsg("命令执行失败:exitValue=" + exit + ",ErrorStream="
						+ IOUtils.readLines(proc.getErrorStream(), Charset.defaultCharset()).toString());
			}
			execInfo.setResult(exit == 0);
		} catch (Exception e) {
			execInfo.setMsg("系统异常:" + e.getMessage());
		}
		return execInfo;
	}

	private static String readInput(Process proc) throws IOException {
		InputStreamReader reader = new InputStreamReader(proc.getInputStream(), Charset.defaultCharset());
		BufferedReader buffer = new BufferedReader(reader);
		String line;
		StringBuilder echo = new StringBuilder();
		while ((line = buffer.readLine()) != null) {
			echo.append(line).append(LINE);
		}
		return echo.toString();
	}

	@Data
	public static class ExecInfo {
		boolean result;
		String ip;
		String msg;
		String resp;

		public ExecInfo(boolean result) {
			this.result = result;
		}

		public static ExecInfo build() {
			return new ExecInfo(false);
		}

		public ExecInfo msg(String msg) {
			this.msg = msg;
			return this;
		}

		public ExecInfo ip(String ip) {
			this.ip = ip;
			return this;
		}
	}
}

package com.wasu.es.intercepoter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 记录日志拦截器
 * 
 * @author wenguang
 * @date 2015年6月5日
 */
public class AccessLogInterceptor extends HandlerInterceptorAdapter {
	Logger log = LoggerFactory.getLogger("access");
	private static final char SEPARATOR = '|';

	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		startTimeThreadLocal.set(System.currentTimeMillis());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long consumeTime = System.currentTimeMillis() - startTimeThreadLocal.get();
		startTimeThreadLocal.remove();

		if (log.isInfoEnabled()) {
			StringBuilder logStr = new StringBuilder();
			logStr.append(request.getRequestURI()).append(SEPARATOR).append(request.getParameter("spm"))
					.append(SEPARATOR).append(consumeTime);
			log.info(logStr.toString());
		}
	}
}

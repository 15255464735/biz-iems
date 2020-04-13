package com.biz.iems.mall.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtil {

	private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	/**
	 * 将request查询参数封装至Map
	 * 
	 * @param request
	 * @param printLog
	 *            是否打印日志
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getParameters(HttpServletRequest request, boolean printLog) {
		Enumeration<String> enume = request.getParameterNames();
		HashMap<String, String> map = new HashMap<String, String>();
		while (enume.hasMoreElements()) {
			String key = enume.nextElement();
			String value = request.getParameter(key);
			try {
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				continue;
			}
			map.put(key, value.trim());
			if (printLog) {
				logger.info(key + "==>" + value);
			}
		}
		return map;
	}

	/**
	 * 将request查询参数封装至Map
	 * @param request
	 * 是否打印日志
	 * @return
	 */
	public static HashMap<String, String> getParameters(HttpServletRequest request) {
		HashMap<String, String> map = getParameters(request, false);
		return map;
	}

	public static HttpServletRequest getCurrentRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}

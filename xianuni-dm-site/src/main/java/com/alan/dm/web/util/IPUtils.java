package com.alan.dm.web.util;


import com.alan.dm.common.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * IP工具类
 * Created by zhangbinalan on 15/6/25
 */
public class IPUtils {
	/**
	 * 获取用户请求IP
	 * @param request
	 * @return
	 */
	public static String getRequestIp(HttpServletRequest request){
		try {
			String xff = request.getHeader("X-Forwarded-For");
			//如果没有X-Forwarded-For,则取remote ip
			if(StringUtils.isEmpty(xff)){
				return request.getRemoteAddr();
			}
			//如果有，获取第一个非内网IP
			String ip = getFirstNonLocalIp(xff);
			if (ip == null) {
				//都是内网IP.获取第一个内网IP
				ip = getFirstLocalIp(xff);
			}
			return ip;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 请求是否来自内网
	 * @param request
	 * @return
	 */
	public static boolean isAllLocalIp(HttpServletRequest request){
		try {
			String xff = request.getHeader("X-Forwarded-For");
			if(StringUtils.isEmpty(xff)){
				String remoteIp=request.getRemoteAddr();
				return isLocalIp(remoteIp);
			}
			String ip = getLastNonLocalIp(xff);
			if (ip == null) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	/**
	 * 检查IP是否是内网IP
	 * @param ip
	 * @return
	 */
	public static boolean isLocalIp(String ip) {
		try {
			InetAddress ia = InetAddress.getByName(ip);
			boolean isLocal=ia.isSiteLocalAddress() || ia.isLinkLocalAddress() || ia.isLoopbackAddress();
			return isLocal;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取第一个非内网IP
	 * @param ipList
	 * @return
	 */
	public static String getFirstNonLocalIp(String ipList) {
		if (ipList == null)
			return null;
		String[] ips = ipList.split("[,\\s]+");
		for (String ip : ips) {
			if (!isLocalIp(ip)) {
				return ip;
			}
		}
		return null;
	}

	/**
	 * 获取最后一个非内网IP
	 * @param ipList
	 * @return
	 */
	public static String getLastNonLocalIp(String ipList) {
		if (ipList == null)
			return null;
		String[] ips = ipList.split("[,\\s]+");
		String lastIp = null;
		for (int i = ips.length - 1; i > -1; i--) {
			String ip = ips[i];
			if (!isLocalIp(ip)) {
				lastIp = ip;
				break;
			}
		}
		return lastIp;
	}
	/**
	 * 获取最后一个内网IP
	 * @param ipList
	 * @return
	 */
	public static String getLastLocalIp(String ipList) {
		if (ipList == null)
			return null;
		String[] ips = ipList.split("[,\\s]+");
		String lastIp = null;
		for (int i = ips.length - 1; i > -1; i--) {
			String ip = ips[i];
			if (!isLocalIp(ip))
				break;
			lastIp = ip;
		}
		return lastIp;
	}
	/**
	 * 获取第一个内网IP
	 * @param ipList
	 * @return
	 */
	public static String getFirstLocalIp(String ipList) {
		if (ipList == null)
			return null;
		String[] ips = ipList.split("[,\\s]+");
		for (String ip : ips) {
			if (isLocalIp(ip)) {
				return ip;
			}
		}
		return null;
	}
	/**
	 * 是否是有效的IPV4地址
	 * @param ip
	 * @return
	 */
	public static boolean isValidIp(String ip) {
		String i[] = ip.split("\\.");
		if (i.length != 4) {
			return false;
		}
		for (String t : i) {
			int v = Integer.parseInt(t);
			if (v < 0 || v > 255) {
				return false;
			}
		}
		return true;
	}
}
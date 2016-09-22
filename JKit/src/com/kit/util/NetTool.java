package com.kit.util;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author admin
 * 获得网站的host
 */
public class NetTool {
	InetAddress myIPaddress = null;
	InetAddress myServer = null;

	public static void main(String[] args) {
		NetTool mytool;
		mytool = new NetTool();
		String url;
		if (args.length > 0) {
			url = args[0];
		} else {
			url = "www.facebook.com";
		}
		System.out.println("Your host IP is: " + mytool.getMyIP());
		System.out.println("The Server IP is :" + mytool.getServerIP(url));

	}

	// 取得LOCALHOST的IP地址
	public InetAddress getMyIP() {
		try {
			myIPaddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
		}
		return (myIPaddress);
	}

	// 取得 www.baidu.com 的IP地址
	public InetAddress getServerIP(String url) {
		try {
			myServer = Inet4Address.getByName(url);
		} catch (UnknownHostException e) {
		}
		return (myServer);
	}
}
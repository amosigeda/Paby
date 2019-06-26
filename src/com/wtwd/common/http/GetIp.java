package com.wtwd.common.http;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.wtwd.common.lang.Constant;

public class GetIp {
	public static void main(String[] args) {
		System.out.println("1");
		try {
		String a=	getRealIp();
		System.out.println(a);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		 
	  /*  public void main(String[] args) throws SocketException {
	        System.out.println(GetIp.getRealIp());
	    }*/
	 
	    public static String getRealIp() throws SocketException {
	        String localip = null;// 本地IP，如果没有配置外网IP则返回它
	        String netip = null;// 外网IP
	 
	        Enumeration<NetworkInterface> netInterfaces = 
	            NetworkInterface.getNetworkInterfaces();
	        InetAddress ip = null;
	        boolean finded = false;// 是否找到外网IP
	        while (netInterfaces.hasMoreElements() && !finded) {
	            NetworkInterface ni = netInterfaces.nextElement();
	            Enumeration<InetAddress> address = ni.getInetAddresses();
	            while (address.hasMoreElements()) {
	                ip = address.nextElement();
	                if (!ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
	                    netip = ip.getHostAddress();
	                    finded = true;
	                    break;
	                } else if (ip.isSiteLocalAddress() 
	                        && !ip.isLoopbackAddress() 
	                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
	                    localip = ip.getHostAddress();
	                }
	            }
	        }
	     
	        if (netip != null && !"".equals(netip)) {
	        	
	        	if (Constant.SYS_SERVER_INETIP1.equals(netip) )
	        		return Constant.SYS_SERVER_DN1;
	        	else if (Constant.SYS_SERVER_INETIP2.equals(netip) )
	        		return Constant.SYS_SERVER_DN2;
	        	else
	        		return Constant.SYS_SERVER_DN1;
	        } else {
	        	if (Constant.SYS_SERVER_INETIP1.equals(localip) )
	        		return Constant.SYS_SERVER_DN1;
	        	else if (Constant.SYS_SERVER_INETIP2.equals(localip) )
	        		return Constant.SYS_SERVER_DN2;
	        	else
	        		return Constant.SYS_SERVER_DN1;
	        }
	    }
	

}
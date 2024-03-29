package com.wtwd.common.bean.wutil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wtwd.common.http.HttpRequest;

public class Distribution{
	//经度
	double longitude;
	//维度
	double dimensionality;
	
	
	public double getLongitude()
	{
	return longitude;
	}
	
	public void setLongitude(double longitude)
	{
	this.longitude = longitude;
	}
	
	public double getDimensionality()
	{
	return dimensionality;
	}
	
	public void setDimensionality(double dimensionality)
	{
	this.dimensionality = dimensionality;
	}
	
	/*
	* 计算两点之间距离, 此函数计算距离偏差比较大
	*
	* @param start
	*
	* @param end
	*
	* @return 米 
	*/
	public double getDistance(Distribution start, Distribution end)
	{
	 double lon1 = (Math.PI / 180) * start.longitude;
	 double lon2 = (Math.PI / 180) * end.longitude;
	 double lat1 = (Math.PI / 180) * start.dimensionality;
	 double lat2 = (Math.PI / 180) * end.dimensionality;
	 // 地球半径
	 double R = 6371;
	 // 两点间距离 km 如果想要米的话 结果*1000就可以了
	 double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
	 return d * 1000;
	 }
	
	
	/**
	       * 行驶距离测量
	       *@param key      用户key
	       *@param origins  起始点 例如116.493381,39.978437,270
	       *@param key      终点   例如116.493441,39.978887
	       * */
	public static String distanceGaode(String key , String origins , String destination){
		String url = "http://restapi.amap.com/v3/distance?output=json&key=";
		url += key;
		url += "&origins=" + origins;
		url += "&destination=" + destination;
	
		HttpRequest request = new HttpRequest();
		String content = null;
		//try {
			content = HttpRequest.urlReturnParamsAs(url); //建立HTTP请求
		//} catch (IOException e) {
//			e.printStackTrace();
		//}
		//String content =hr.getContent(); //获取请求数据
		String distance = null;
		if (null!=content) {
			JSONObject jsonData = JSONObject.fromObject(content); //数据解析
			JSONArray resultArr = jsonData.optJSONArray("results");
			if (resultArr != null) {
				int size = resultArr.size();
				for (int i = 0; i < size; i++) {
					JSONObject result = resultArr.getJSONObject(i);
					distance = result.optString("distance");  //行驶距离
					String distanceDebug = result.optString("distasdfadsf");  //行驶距离
					

					String duration = result.optString("duration"); //行驶时间
					System.out.println("distance="+distance+"duration="+duration);  //开发者验证数据
				}
			}
		}
		
		return distance;
	}	
	
}

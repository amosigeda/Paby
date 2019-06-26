package com.wtwd.common.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.util.Base64;


public class HttpRequest {
	
	Log logger = LogFactory.getLog(HttpRequest.class);
	
	/**
	 * ��ָ��URL����GET����������
	 * @param url���͵�URL
	 * @param param�������
	 * @return ��Ӧ���
	 */
	public static String sendGet(String url, String param){
		String urlNameString = url + "?" + param;
		
		LogFactory.getLog(HttpRequest.class).info("sendGet Req: " + urlNameString.trim());
		
		String returnParams = urlReturnParamsAs(urlNameString);
		
		LogFactory.getLog(HttpRequest.class).info("sendGet Resp: " + returnParams.trim());
		
		return returnParams;
	}

	
	public static String sendPost(String urlStr, String param, String referer, String encoding) {
		
		String line = "";
		//StringBuilder sb = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		HttpURLConnection httpConn = null;
		//OutputStreamWriter out = null;
		BufferedReader in = null;
		
		LogFactory.getLog(HttpRequest.class).info("sendPost Req param: " + param.trim());
		
		try {
			URL url = new URL(urlStr);
//			System.out.println("---url : " + urlStr + " --- post data :" + param);
			
			httpConn = (HttpURLConnection) url.openConnection();
			//POST 
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			
			if (referer != null) {
				httpConn.setRequestProperty("referer", referer);
			}
			
			httpConn.setRequestProperty("accept", "*/*");
			httpConn.setRequestProperty("connection", "Keep-Alive");
			httpConn.setRequestProperty("user-agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConn.setRequestProperty("Content-Type", "application/json");
			
			//httpConn.setConnectTimeout(5000);
			
			httpConn.connect();
			
			httpConn.getOutputStream().write(param.getBytes());
			httpConn.getOutputStream().flush();
			httpConn.getOutputStream().close();
//			out = new OutputStreamWriter(httpConn.getOutputStream(),encoding);
//			out.write(param);
//			out.flush();
			
			if (httpConn.getResponseCode() != 200) {
				System.out.println("error: " + httpConn.getResponseMessage());
				in = new BufferedReader(new InputStreamReader(httpConn.getErrorStream(),encoding));
			} else {
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),encoding));
			}
			
			while ((line = in.readLine()) != null) {
				sb.append(line).append('\n');
			}
			//httpConn.disconnect();
			
			LogFactory.getLog(HttpRequest.class).info("sendPost Resp: " + sb.toString().trim());
			
			return sb.toString();
					
		} catch (Exception e) {
			//httpConn.disconnect();
//			System.out.println(e.getMessage());
			LogFactory.getLog(HttpRequest.class).error("sendPost Exception: " + e.getMessage());
			LogFactory.getLog(HttpRequest.class).error("sendPost Resp: " + sb.toString().trim());
			return sb.toString();
		} finally {
			try {
				if (httpConn != null) {
					httpConn.disconnect();
				}
//				if (out != null) {
//					out.close();
//				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();	
			}
			//return sb.toString();
		}
		//Just debug
		//System.out.println("---getPost return : " + sb.toString());
		//return sb.toString();
	}
	
	/**
	 * ���Զ�λ
	 */
	public static String sendGetToGaoDe(String url, LinkedHashMap<String,String> map){	
		
//		String urlNameString = url;
//		String params = paramsTransToUrlAs(map);
//		String returnParams = urlReturnParams(urlNameString, params);

        String urlNameString = url + paramsTransToUrl(map);

        LogFactory.getLog(HttpRequest.class).info("sendGetToGaoDe Req: " + urlNameString.trim());

        String returnParams = urlReturnParamsAs(urlNameString);
		
		LogFactory.getLog(HttpRequest.class).info("sendGetToGaoDe Resp: " + returnParams.trim());
		
		return returnParams;
	}
	
	
	
	public static String paramsTransToUrl(LinkedHashMap<String,String> map){
        StringBuffer params = new StringBuffer("?");
		
		for(String key : map.keySet()){
			if(!params.toString().equals("?")){
				params.append("&");
			}
			params.append(key).append("=").append(map.get(key));
		}
		return params.toString();
	}
	
	public static String paramsTransToUrlAs(LinkedHashMap<String,String> map){
        StringBuffer params = new StringBuffer();
		
		for(String key : map.keySet()){
			params.append(key).append("=").append(map.get(key));
			params.append("&");
		}
		return params.toString();
	}	
//	@SuppressWarnings("finally")
	public static String urlReturnParamsAs(String urlNameString){
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		HttpURLConnection httpConnect = null;
		
		urlNameString = urlNameString.replaceAll(" ", "%20");
		
		try {
			URL realUrl = new URL(urlNameString);
			//����URL֮�������
			//URLConnection connection = realUrl.openConnection();
			//HttpURLConnection httpConnect = (HttpURLConnection)connection;
			httpConnect = (HttpURLConnection) realUrl.openConnection();		
			httpConnect.setRequestProperty("accept", "*/*");
			httpConnect.setRequestProperty("connection", "Keep-Alive");
			httpConnect.setRequestProperty("user-agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			//
			httpConnect.connect();
//			System.out.println("---resquest url string--- " + urlNameString);
			//
//			Map<String, List<String>> map = connection.getHeaderFields();
			//
			int code = httpConnect.getResponseCode();
			if(code == 200){
				in = new BufferedReader(new InputStreamReader(httpConnect.getInputStream()));
				String line;
				while((line = in.readLine()) != null){
					sb.append(line);
				}				
				in.close();
//				LogFactory.getLog(HttpRequest.class).info("---returnString---"+sb.toString());
//				LogFactory.getLog(HttpRequest.class).debug("---returnString---"+sb.toString());
//				System.out.println("---returnString---"+sb.toString());
			}else{
				LogFactory.getLog(HttpRequest.class).info("httpConnect.getResponseCode: " + code);
				sb.append("-1");
			}
			
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			sb.append("-1");
//			e.printStackTrace();
		}finally{
			//return sb.toString();
			try {
				if (httpConnect != null) {
					httpConnect.disconnect();
		}	
				
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();	
			}
		}	
		//Kevin.Liu added 20160904 for debug 
//		System.out.println("--- urlReturn Params : " + sb.toString());
		return sb.toString();
	}
	
	public static String urlReturnParams(String urlNameString, String params){
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		HttpURLConnection httpConnect = null;
		
		try {
			URL realUrl = new URL(urlNameString);
			httpConnect = (HttpURLConnection) realUrl.openConnection();
			httpConnect.setRequestMethod( "POST");	//smile add
			httpConnect.setDoOutput( true);	//smile add
			httpConnect.setRequestProperty("Accept-Charset", "UTF-8");
			httpConnect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConnect.setRequestProperty("Content-Length", String.valueOf(params.length()));
			OutputStream outputStream = null;
	        OutputStreamWriter outputStreamWriter = null;
	        InputStream inputStream = null;
	        InputStreamReader inputStreamReader = null;
	        BufferedReader reader = null;
	        StringBuffer resultBuffer = new StringBuffer();
	        String tempLine = null;
	        
	        try {
	            outputStream = httpConnect.getOutputStream();
	            outputStreamWriter = new OutputStreamWriter(outputStream);
	            
	            outputStreamWriter.write(params);
	            outputStreamWriter.flush();
	            //响应失败
	            if (httpConnect.getResponseCode() >= 300) {
	                return "-1";
	            }
	            //接收响应流
	           inputStream = httpConnect.getInputStream();
	            inputStreamReader = new InputStreamReader(inputStream);
	            reader = new BufferedReader(inputStreamReader);
	            
	            while ((tempLine = reader.readLine()) != null) {
	                resultBuffer.append(tempLine);
	            }
	            
	        } finally {
	            
	            if (outputStreamWriter != null) {
	                outputStreamWriter.close();
	            }
	            
	            if (outputStream != null) {
	                outputStream.close();
	            }
	            
	            if (reader != null) {
	                reader.close();
	            }
	            
	            if (inputStreamReader != null) {
	                inputStreamReader.close();
	            }
	            
	            if (inputStream != null) {
	                inputStream.close();
	            }
	            
	        }	
	        
	        return resultBuffer.toString();
		} catch(Exception e) {
			return "-1";
		}
	}

    public static String inputStreamToString(InputStream in,String encoding) throws Exception{ 
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        byte[] data = new byte[ 2048]; 
        int count = -1; 
        while((count = in.read(data,0, 2048)) != -1)
            outStream.write(data, 0, count);
        in.close();
        data = null; 
        return new String(outStream.toByteArray(),encoding); 
    } 	
	@SuppressWarnings("finally")
	public static String httpsUrlReturnParams(String urlNameString) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			LogFactory.getLog(HttpRequest.class).info("1");

			SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
			sslcontext.init(null, new TrustManager[] { myX509TrustManager },
					new java.security.SecureRandom());

			URL realUrl = new URL(urlNameString);
			HttpsURLConnection connection = (HttpsURLConnection) realUrl
					.openConnection();
			// HttpsURLConnection httpConnect = (HttpsURLConnection)connection;
			// connection.setRequestProperty("accept", "*/*");
			// //connection.setRequestProperty("connection", "Keep-Alive");
			// connection.setRequestProperty("user-agent",
			// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		
			   String authString = "AC08d153f6d0fb9a2135f0edd5614229f6" + ":" + "5fc4e38b694dfd9a530871996a4a038e";
//			    System.out.println("auth string: " + authString);
			    byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			    String authStringEnc = new String(authEncBytes);
			    connection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			connection.setSSLSocketFactory(sslcontext.getSocketFactory());
			// connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.connect();
			int code = connection.getResponseCode();
			LogFactory.getLog(HttpRequest.class).info("code=" + code);
			System.out.println("code="+code);
			if (code == 200) {
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
			} else {
				System.out.println("if-else这里");
				sb.append("-1");
			}

		} catch (Exception e) {
			System.out.println("异常这里");
			// TODO Auto-generated catch block
			sb.append("-1");
			e.printStackTrace();
		} finally {
			return sb.toString();
		}
	}
	private static TrustManager myX509TrustManager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] ax509certificate,
				String s) throws CertificateException {
			// TODO Auto-generated method stub

		}

		public void checkServerTrusted(X509Certificate[] ax509certificate,
				String s) throws CertificateException {
			// TODO Auto-generated method stub

		}

		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	};

	public static String sendGetToGeo(String url, LinkedHashMap<String,String> map){	
		
//		String urlNameString = url;
//		String params = paramsTransToUrlAs(map);
//		String returnParams = urlReturnParams(urlNameString, params);

		HttpRequest hr = new HttpRequest();
		
        String urlNameString = url + hr.paramsTransToUrlNotStatic(map);

        String returnParams = urlReturnParamsAs(urlNameString);

        hr = null;
		return returnParams;
	}
	

	public String paramsTransToUrlNotStatic(LinkedHashMap<String,String> map){
        StringBuffer params = new StringBuffer("?");
		
		for(String key : map.keySet()){
			if(!params.toString().equals("?")){
				params.append("&");
			}
			params.append(key).append("=").append(map.get(key));
		}
		return params.toString();
	}
	
	
	public static String shuMiUrlGetRequest(String urlNameString,String signature,String key,String timestamp){
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		HttpURLConnection httpConnect = null;
		
		try {
			URL realUrl = new URL(urlNameString);
			httpConnect = (HttpURLConnection) realUrl.openConnection();
			
			httpConnect.setRequestProperty("accept", "*/*");
			httpConnect.setRequestProperty("connection", "Keep-Alive");
			httpConnect.setRequestProperty("user-agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		
			httpConnect.setRequestProperty("timestamp",timestamp);
			httpConnect.setRequestProperty("key",key);
			httpConnect.setRequestProperty("signature",signature);
			httpConnect.connect();
			int code = httpConnect.getResponseCode();
		    System.out.println("code="+code);
			if(code == 200){
				in = new BufferedReader(new InputStreamReader(httpConnect.getInputStream()));
				String line;
				while((line = in.readLine()) != null){
					sb.append(line);
				}				
				in.close();
			}else{
				System.out.println("eles------");
				sb.append("-1");
			}
			
			
		}catch (Exception e) {
			sb.append("-1");
			e.printStackTrace();
		}finally{
			try {
				if (httpConnect != null) {
					httpConnect.disconnect();
		}	
				
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();	
			}
		}	
		return sb.toString();
	}
	
	
	public static String ShuMiUrlPostRequest(String urlNameString, String params,String signature,String key,String timestamp){
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		HttpURLConnection httpConnect = null;
		
		try {
			URL realUrl = new URL(urlNameString);
			httpConnect = (HttpURLConnection) realUrl.openConnection();
			httpConnect.setRequestMethod( "POST");	//smile add
			httpConnect.setDoOutput( true);	//smile add
			httpConnect.setRequestProperty("Accept-Charset", "UTF-8");
			httpConnect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConnect.setRequestProperty("Content-Length", String.valueOf(params.length()));
			
			httpConnect.setRequestProperty("timestamp",timestamp);
			httpConnect.setRequestProperty("key",key);
			httpConnect.setRequestProperty("signature",signature);
			
			OutputStream outputStream = null;
	        OutputStreamWriter outputStreamWriter = null;
	        InputStream inputStream = null;
	        InputStreamReader inputStreamReader = null;
	        BufferedReader reader = null;
	        StringBuffer resultBuffer = new StringBuffer();
	        String tempLine = null;
	        
	        try {
	            outputStream = httpConnect.getOutputStream();
	            outputStreamWriter = new OutputStreamWriter(outputStream);
	            
	            outputStreamWriter.write(params);
	            outputStreamWriter.flush();
	            //响应失败
	            if (httpConnect.getResponseCode() >= 300) {
	                return "-1";
	            }
	            //接收响应流
	         inputStream = httpConnect.getInputStream();
	            inputStreamReader = new InputStreamReader(inputStream);
	            reader = new BufferedReader(inputStreamReader);
	            
	            while ((tempLine = reader.readLine()) != null) {
	                resultBuffer.append(tempLine);
	            }
	            
	        } finally {
	            
	            if (outputStreamWriter != null) {
	                outputStreamWriter.close();
	            }
	            
	            if (outputStream != null) {
	                outputStream.close();
	            }
	            
	            if (reader != null) {
	                reader.close();
	            }
	            
	            if (inputStreamReader != null) {
	                inputStreamReader.close();
	            }
	            
	            if (inputStream != null) {
	                inputStream.close();
	            }
	            
	        }	
	        
	        return resultBuffer.toString();
		} catch(Exception e) {
			return "-1";
		}
	}
	
}


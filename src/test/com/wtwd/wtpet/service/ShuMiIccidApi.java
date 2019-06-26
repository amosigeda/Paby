package test.com.wtwd.wtpet.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.commons.codec.Charsets;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.wtwd.common.http.HttpRequest;

public class ShuMiIccidApi {

	private static final String URL = "https://api-staging.showmac.cn";
	private static final String KEY = "389524533368274944";
	private static final String SECRET = "Qqwou1iZnEPZOR0hnh7u";

	public static void main(String[] args) {
		String iccid = "0000031640200522067";

		// System.out.println("result="+getIccidAllMealList(iccid));
		// TODO Auto-generated catch block
		/*
		 * result={"code":0,"message":"","data":[{"id":"00000050001","name":"30MB套餐"
		 * ,
		 * "description":"30MB套餐","type":1,"price":500,"volume":30,"duration":1}
		 * ,
		 * {"id":"00000050003","name":"300MB套餐","description":"300MB套餐","type":0
		 * ,"price":1500,"volume":300,"duration":1},{"id":"46002020002","name":
		 * "1GB套餐"
		 * ,"description":"1GB套餐","type":0,"price":3000,"volume":1000,"duration"
		 * :1}]}
		 */
	//	System.out.println("result=" + IccidSubscription(iccid,"00000050001"));
		// System.out.println("result="+getIccidDataFlow(iccid)); //查询订阅
	System.out.println("result=" + updateIccidCardStatus(iccid, "1"));
		//System.out.println("result="+getIccidCardStatus(iccid));

	}

	// 获取iccid可订阅的所有套餐
	public static String getIccidAllMealList(String iccid) {
		// String result="";
		String timestamp = (System.currentTimeMillis() + "").substring(0, 10);
		System.out.println(timestamp);
		String requestUrl = URL + "/v1/openapi/rateplans/searchByIccid?iccid="
				+ iccid;
		System.out.println("连接=" + requestUrl);

		String joined = "GET/v1/openapi/rateplans/searchByIccidiccid" + iccid
				+ timestamp + KEY + SECRET;

		HashCode hashCode = Hashing.sha1().hashString(joined, Charsets.UTF_8);
		String signature = Base64.getEncoder().encodeToString(
				hashCode.asBytes());

		// signature=getBase64(ShaStringToString(signature));
		String result = HttpRequest.shuMiUrlGetRequest(requestUrl, signature,
				KEY, timestamp);
		return result;
	}

	// 订阅套餐
	public static String IccidSubscription(String iccid, String rateplanId) {
		/*
		 * 路路径：/sim/subscription/ Http请求⽅方法：POST
		 */

		String timestamp = (System.currentTimeMillis() + "").substring(0, 10);
		System.out.println(timestamp);
		String requestUrl = URL + "/v1/openapi/sim/subscription";
		System.out.println("请求的url=" + requestUrl);
		System.out.println("url=" + requestUrl);
		JSONObject json = new JSONObject();
		json.put("rateplanId", rateplanId);// 所订购套餐的id
		json.put("iccid", iccid);
		// json.put("effectiveDate", Integer.valueOf(timestamp));
		System.out.println("body=" + json.toString());
		String joined = "POST/v1/openapi/sim/subscription" + json.toString()
				+ timestamp + KEY + SECRET;
		System.out.println("joined=" + joined);
		joined = replaceBlank(joined);
		HashCode hashCode = Hashing.sha1().hashString(joined, Charsets.UTF_8);
		String signature = Base64.getEncoder().encodeToString(
				hashCode.asBytes());
		System.out.println(signature);
		System.out.println("去空格去换行=" + replaceBlank(json.toString()));
		// signature=getBase64(ShaStringToString(signature));
		// result = sendPost(requestUrl, json.toString(), signature,
		// KEY,timestamp);
		String result = sendPostUrl(requestUrl, json.toString(), "", "UTF-8",
				signature, KEY, timestamp);
		// TODO Auto-generated catch block
		// String urlNameString, String params,String signature,String
		// key,String timestamp
		return result;
	}

	// 获取iccid流量使用情况
	public static String getIccidDataFlow(String iccid) {
		// String result="";
		String timestamp = (System.currentTimeMillis() + "").substring(0, 10);
		System.out.println(timestamp);
		String requestUrl = URL + "/v1/openapi/sim/usage/" + iccid;
		System.out.println("连接=" + requestUrl);

		String joined = "GET/v1/openapi/sim/usage/" + iccid + timestamp + KEY
				+ SECRET;

		HashCode hashCode = Hashing.sha1().hashString(joined, Charsets.UTF_8);
		String signature = Base64.getEncoder().encodeToString(
				hashCode.asBytes());
		System.out.println(signature);

		// signature=getBase64(ShaStringToString(signature));
		String result = HttpRequest.shuMiUrlGetRequest(requestUrl, signature,
				KEY, timestamp);
		return result;
	}

	// iccid卡状态查询
	public static String getIccidCardStatus(String iccid) {
		// String result="";
		String timestamp = (System.currentTimeMillis() + "").substring(0, 10);
		System.out.println(timestamp);
		String requestUrl = URL + "/v1/openapi/sim/iccid/" + iccid + "/status";
		System.out.println("连接=" + requestUrl);

		String joined = "GET" + "/v1/openapi/sim/iccid/" + iccid + "/status"
				+ timestamp + KEY + SECRET;

		HashCode hashCode = Hashing.sha1().hashString(joined, Charsets.UTF_8);
		String signature = Base64.getEncoder().encodeToString(
				hashCode.asBytes());
		System.out.println(signature);

		// signature=getBase64(ShaStringToString(signature));
		String result = HttpRequest.shuMiUrlGetRequest(requestUrl, signature,
				KEY, timestamp);
		return result;
	}

	// iccid卡状态更改
	public static String updateIccidCardStatus(String iccid, String status) {
		// String result="";
		String timestamp = (System.currentTimeMillis() + "").substring(0, 10);
		System.out.println(timestamp);
		String requestUrl = URL + "/v1/openapi/sim/iccid/" + iccid + "/status/"+ status;
		System.out.println("连接=" + requestUrl);
		/*
		 * 修改卡的状态， 1代表停机，0代 表恢复
		 */

		String joined = "PUT" + "/v1/openapi/sim/iccid/" + iccid + "/status/"
				+ status + timestamp + KEY + SECRET;
		System.out.println("joined=" + joined);
		HashCode hashCode = Hashing.sha1().hashString(joined, Charsets.UTF_8);
		String signature = Base64.getEncoder().encodeToString(
				hashCode.asBytes());
		System.out.println(signature);
		//String result = doPut(requestUrl, "", signature, KEY, timestamp);
		String result="";
		try {
			result = testUpdate(requestUrl,timestamp, signature, KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String result=sendPostUrl(requestUrl,"","","UTF-8", signature,KEY,
		// timestamp);
		return result;
	}

	public static String ShaStringToString(String sb) {
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(sb.toString().getBytes());// 对接后的字符串进行sha1加密
			// 字节数组转换为 十六进制 数 的 字符串
			for (int i = 0; i < digest.length; i++) {
				String shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			// System.out.println(hexString.toString()); //签名密文字符串
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hexString.toString();
	}

	// 加密
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	// 解密
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String request(String address, String method,
			String contentType, String data) {
		String responseText = "";
		// if (Strings.isEmpty(method) ){
		// method = METHOD_POST;
		// }
		// if (Strings.isEmpty(contentType) ) {
		// contentType = "application/x-www-form-urlencoded";
		// }
		HttpURLConnection conn = null;
		try {
			URL url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10 * 1000);
			conn.setReadTimeout(30 * 1000);
			conn.setDoOutput(true);
			if (data != null) {
				conn.setDoInput(true);

			}
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", contentType);
			conn.setRequestProperty("User-Agent", "");
			// conn.setRequestProperty("Accept", "application/json");
			// conn.setRequestProperty("Accept-Encoding", "gzip");
			if (data != null) {
				conn.setRequestProperty("Content-Length",
						data.getBytes().length + "");
			}
			conn.setUseCaches(false);
			OutputStreamWriter wr = null;
			if (data != null) {
				wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(data);
				wr.flush();
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String currentLine = "";
			StringBuffer sb = new StringBuffer();
			while ((currentLine = rd.readLine()) != null) {
				sb.append(currentLine);
				sb.append("\n");
			}
			responseText = sb.toString();

			// logger.trace("接收数据�?);
			// logger.warn(responseText);
			if (data != null) {
				wr.close();
			}
			rd.close();

		} catch (Exception ex) {
			if (conn != null) {
				conn.disconnect();
			}
			ex.printStackTrace();
		}
		// finally
		// {
		// conn.disconnect();
		// }
		return responseText;
	}

	public static String httpsUrlReturnParams(String urlNameString,
			String timestamp, String signature, String key) {
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

			// String authString = "AC08d153f6d0fb9a2135f0edd5614229f6" + ":" +
			// "5fc4e38b694dfd9a530871996a4a038e";
			// System.out.println("auth string: " + authString);
			// byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			// String authStringEnc = new String(authEncBytes);
			// connection.setRequestProperty("Authorization", "Basic " +
			// authStringEnc);
			connection.setRequestProperty("timestamp", timestamp);
			connection.setRequestProperty("signature", signature);
			connection.setRequestProperty("key", key);

			connection.setSSLSocketFactory(sslcontext.getSocketFactory());
			// connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.connect();
			int code = connection.getResponseCode();
			LogFactory.getLog(HttpRequest.class).info("code=" + code);
			System.out.println("code=" + code);
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

	public static String doPut(String strUrl, String param, String timestamp,
			String key, String signature) {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		StringBuffer jsonString = new StringBuffer();
		try {
			final HttpPut put = new HttpPut(strUrl);

			put.setHeader("timestamp", timestamp);
			put.setHeader("key", key);
			put.setHeader("signature", signature);

			put.setEntity(new StringEntity(param, "UTF-8"));

			CloseableHttpResponse response1 = httpclient.execute(put);
			try {
				HttpEntity entity1 = response1.getEntity();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						entity1.getContent()));
				String line;
				while ((line = br.readLine()) != null) {
					jsonString.append(line);
				}

				EntityUtils.consume(entity1);
			} finally {
				response1.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString.toString();
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String sendPostUrl(String urlStr, String param,
			String referer, String encoding, String signature, String key,
			String timestamp) {

		String line = "";
		// StringBuilder sb = new StringBuilder();
		StringBuffer sb = new StringBuffer();
		HttpURLConnection httpConn = null;
		// OutputStreamWriter out = null;
		BufferedReader in = null;

		LogFactory.getLog(HttpRequest.class).info(
				"sendPost Req param: " + param.trim());

		try {
			URL url = new URL(urlStr);
			// System.out.println("---url : " + urlStr + " --- post data :" +
			// param);

			httpConn = (HttpURLConnection) url.openConnection();
			// POST
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
			httpConn.setRequestProperty("timestamp", timestamp);
			httpConn.setRequestProperty("key", key);
			httpConn.setRequestProperty("signature", signature);

			// httpConn.setConnectTimeout(5000);

			httpConn.connect();

			httpConn.getOutputStream().write(param.getBytes());
			httpConn.getOutputStream().flush();
			httpConn.getOutputStream().close();
			// out = new
			// OutputStreamWriter(httpConn.getOutputStream(),encoding);
			// out.write(param);
			// out.flush();

			if (httpConn.getResponseCode() != 200) {
				System.out.println("error: " + httpConn.getResponseMessage());
				in = new BufferedReader(new InputStreamReader(
						httpConn.getErrorStream(), encoding));
			} else {
				in = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream(), encoding));
			}

			while ((line = in.readLine()) != null) {
				sb.append(line).append('\n');
			}
			// httpConn.disconnect();

			LogFactory.getLog(HttpRequest.class).info(
					"sendPost Resp: " + sb.toString().trim());

			return sb.toString();

		} catch (Exception e) {
			// httpConn.disconnect();
			// System.out.println(e.getMessage());
			LogFactory.getLog(HttpRequest.class).error(
					"sendPost Exception: " + e.getMessage());
			LogFactory.getLog(HttpRequest.class).error(
					"sendPost Resp: " + sb.toString().trim());
			return sb.toString();
		} finally {
			try {
				if (httpConn != null) {
					httpConn.disconnect();
				}
				// if (out != null) {
				// out.close();
				// }
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			// return sb.toString();
		}
		// Just debug
		// System.out.println("---getPost return : " + sb.toString());
		// return sb.toString();
	}

	/*
	 * public static String httpPut(String requestUrl, String timestamp, String
	 * key, String signature) {}
	 */

/*	public static String doPut(String strUrl, String param) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String strReturn = "";
		PutMethod httpput = new PutMethod(strUrl);
		try {
			if (param != null) {
				RequestEntity entity = new StringRequestEntity(param,
						"application/json", "UTF-8");
				httpput.setRequestEntity(entity);
			}
			httpClient.executeMethod(httpput);
			byte[] bytes = httpput.getResponseBody();
			strReturn = new String(bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strReturn;
	}*/

	public static String testUpdate(String url,String timestamp, String signature, String key) throws Exception {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(url);
		put.setHeader("Content-type", "application/json");
		put.setHeader("timestamp", timestamp);
		put.setHeader("key", key);
		put.setHeader("signature", signature);
	
		StringEntity params = new StringEntity("");
		put.setEntity(params);

		HttpResponse response = client.execute(put);
		System.out.println("Response Code:"
				+ response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println("result:" + result);
		return result.toString();
	}

}

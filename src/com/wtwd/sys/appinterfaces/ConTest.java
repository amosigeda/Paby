package com.wtwd.sys.appinterfaces;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import net.sf.json.JSONObject;

import com.wtwd.common.lang.Tools;
import com.wtwd.sys.appinterfaces.innerw.WTSigninAction;
  
public class ConTest{  
	      
	  
	private static int thread_num = 15000 * 5 + 5;  
	  
	private static int client_num = 10;  
	  
	private  static  Integer num=0;  
	  
	public static int getNo(){  
	    num=num+1;  
//	    System.out.println(num);  
	    return num;  
	}  
	  
	  
	private static Map keywordMap = new HashMap();  
	  
	    static {  
	      
	//  try {  
	//    
	//  InputStreamReader isr = new InputStreamReader(new FileInputStream(  
	//    
	//          new File("clicks.txt")), "GBK");  
	//    
	//  BufferedReader buffer = new BufferedReader(isr);  
	//    
	//  String line = "";  
	//    
	//  while ((line = buffer.readLine()) != null) {  
	//    
	//      keywordMap.put(line.substring(0, line.lastIndexOf(":")), "");  
	//    
	//  }  
	//    
	//  } catch (Exception e) {  
	//    
	//      e.printStackTrace();  
	//    
	//  }  
	      
	}  

	    
	    
		/* reference : http://www.cnblogs.com/linjiqin/archive/2011/09/19/2181634.html */
	    
	    
	public static void main(String[] args) {  
	  
	        int size = keywordMap.size();  
	          
	        // TODO Auto-generated method stub  
	          
	        ExecutorService exec = Executors.newCachedThreadPool();  
	        
	        keywordMap.put("test01", "38338");  
	        keywordMap.put("test02", "38339");  
	        keywordMap.put("test03", "38340");  
	        keywordMap.put("test04", "38341");  
	        keywordMap.put("test05", "38342");  
	//      keywordMap.put("test06", "38343");  
	//      keywordMap.put("test07", "38344");  
	//      keywordMap.put("test08", "38345");  
	//      keywordMap.put("test09", "38346");  
	//      keywordMap.put("test10", "38347");  
	        // 50个线程可以同时访问  
	          
	        final Semaphore semp = new Semaphore(thread_num);  
	          
	          
            for (  int i = 0 ; i <= 200; i++) {
	        

    	        // 模拟2000个客户端访问  
    	        Set set =keywordMap.keySet();//   
            	
            	
    	        Iterator it=set.iterator();  
	            
	           int index=0;  
	             
		        while(it.hasNext()){  
		          
		              
		              
		            final String userName= (String) it.next();//        
		            final String pass=keywordMap.get(userName).toString();//  
		              
			        Runnable run = new Runnable() {  
			                int NO = ConTest.getNo();
			                    
			            public void run() {  
			              
			            try {  
			              
			                // 获取许可  
			                  
			                semp.acquire();  
			                  
			                System.out.println("Thread:" + NO);  
			                 
			                
			                synchronized(ConTest.num) {
				                String host = "http://localhost:8080/wtpet/doWTSignin.do";
				                //String params = "{\"user_password\":\"123456\",\"app_version\":\"V1.0\",\"device_token\":\"6489cc96cb2289b6\",\"verify_code\":\"308021\",\"time_zone\":\"Asia/Shanghai\",\"user_name\":\"" + userName + "\",\"type\":\"0\"}";
				                StringBuffer sb =new StringBuffer("{\"user_password\":\"123456\",\"user_name\":\"");
				                sb.append(userName);
				                sb.append("\"}");
				                
				                WhttpPostAs whp = new WhttpPostAs();
				                whp.httpPostInner(host, sb.toString());
				                //httpPostInner(host, sb.toString());
				                
			                }
			                System.out.println("第：" + NO + " 个");  
			              
			                semp.release();  
			                  
			                  
			            } catch (Exception e) {  
			              
			                e.printStackTrace();  
			              
			            }  
			              
			        }  
			          
			    };  
		          
		        exec.execute(run);  
		        return;
		          
		        }  
            }
	          
	        // 退出线程池  
	          
	        exec.shutdown();  
	  
	}  

	
	public static void conTest() {
		Tools tls = new Tools();		
		WTSigninAction ba = new WTSigninAction();
		
		try {
    		ba.insertVisit("1", null, "80", "ConTest begin 1");
		  
	        int size = keywordMap.size();  
	          
	        // TODO Auto-generated method stub  
	          
	        ExecutorService exec = Executors.newCachedThreadPool();  
	        
	        keywordMap.put("test01", "38338");  
	        keywordMap.put("test02", "38339");  
	        keywordMap.put("test03", "38340");  
	        keywordMap.put("test04", "38341");  
	        keywordMap.put("test05", "38342");  
	          
	        final Semaphore semp = new Semaphore(thread_num);           

    		ba.insertVisit("1", null, "80", "ConTest begin 2");
	        
	        
	        for (  int i = 0 ; i <= (15000 -1); i++) {

	    		//BaseAction.insertVisit("1", null, "80", "ConTest begin 3");
	        	

	        	if ( i == 5000  )
	        		ba.insertVisit("1", null, "80", "ConTest 200000 1");

	        	if ( i == 10000  )
	        		ba.insertVisit("1", null, "80", "ConTest 400000 1");

	        	if ( i == (15000 - 1)  )
	        		ba.insertVisit("1", null, "80", "ConTest 550000 1");
	        	
	        	
	        	
		        // 模拟2000个客户端访问  
		        Set set =keywordMap.keySet();//   
	        	
	        	
		        Iterator it=set.iterator();  
	            
	           int index=0;  
	             
		        while(it.hasNext()){  
		          
		              
		              
		            final String userName= (String) it.next();//        
		            final String pass=keywordMap.get(userName).toString();//  
		              
			        Runnable run = new Runnable() {  
			                int NO = ConTest.getNo();
			                    
			            public void run() {  
		        		WTSigninAction ba = new WTSigninAction();
			              
			            try {  
			              
			                // 获取许可  
			                  
			                semp.acquire();  
			                  
			                System.out.println("Thread:" + NO);  
			                 
			                
			                //synchronized(ConTest.num) {
				                String host = "http://localhost:8080/wtpet/doWTSignin.do";
				                //String params = "{\"user_password\":\"123456\",\"app_version\":\"V1.0\",\"device_token\":\"6489cc96cb2289b6\",\"verify_code\":\"308021\",\"time_zone\":\"Asia/Shanghai\",\"user_name\":\"" + userName + "\",\"type\":\"0\"}";
				                StringBuffer sb =new StringBuffer("{\"user_password\":\"123456\",\"user_name\":\"");
				                sb.append(userName);
				                sb.append("\"}");
				                
				                WhttpPostAs whp = new WhttpPostAs();
				                whp.httpPostInner(host, sb.toString());
				                //httpPostInner(host, sb.toString());
				                
			                //}
			                System.out.println("第：" + NO + " 个");  
			              
			                semp.release();  
			                  
			                  
			            } catch (Exception e) {  
			              
			                e.printStackTrace();  
			                ba.insertVisit("1", null, "80", "ConTest exception 1");
			              
			            }  
			              
			        }  
			          
			    };  
		          
		        exec.execute(run);  
		        //return;
		          
		        }  
	        }
	          
	        // 退出线程池  
	          
	        exec.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
            ba.insertVisit("1", null, "80", "ConTest exception 2");			
		}
  
}  
	
	
	
	public static synchronized void httpPostInner( String urlNameString,  String params ) {
		//String urlNameString = "http://192.168.17.225:8080/wtpet/doWTSignin.do";
		try {
			String encoding="UTF-8";    
			
//			System.out.println("httpPostInner");
	        
			//String params="{\"user_name\":\"luonianyuzhou@163.com\",\"type\":\"0\"}";
	        //String params = "{\"user_password\":\"123456\",\"app_version\":\"V1.0\",\"device_token\":\"6489cc96cb2289b6\",\"verify_code\":\"308021\",\"time_zone\":\"Asia/Shanghai\",\"user_name\":\"461261532@qq.com\",\"type\":\"0\"}";
	        byte[] data = params.getBytes(encoding);

			BufferedReader in = null;
			StringBuffer sb = new StringBuffer();
			
			
			URL url =new URL(urlNameString);        
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
			conn.setRequestMethod("POST");
	        conn.setDoOutput(true);        //application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据        
			conn.setRequestProperty("Content-Type", "application/x-javascript; charset="+ encoding);     
			conn.setRequestProperty("Content-Length", String.valueOf(data.length)); 
			conn.setConnectTimeout(120*1000);    
			OutputStream outStream = conn.getOutputStream();       
			outStream.write(data);    
			outStream.flush();    
			outStream.close();      
			//InputStream inStrm = conn.getInputStream(); 
			//System.out.println(conn.getResponseCode()); //响应代码 200表示成功
			int code = conn.getResponseCode();
			if(code == 200){
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while((line = in.readLine()) != null){
					sb.append(line);
				}				
				in.close();
				JSONObject object = JSONObject.fromObject(sb.toString());
				System.out.println("user_id:" + object.optString("user_id") + params);

                //Thread.sleep((long) (60 * 1000 * 200) );  
				
				
			}else{
				sb.append("-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---httpPostInner exception---");			
		}
		
	}
      
	  
}  

class WhttpPostAs {
	public WhttpPostAs() {
		
	}
	public /*staticsynchronized*/ void httpPostInner( String urlNameString,  String params ) {
		//String urlNameString = "http://192.168.17.225:8080/wtpet/doWTSignin.do";
		try {
			String encoding="UTF-8";    
			
//			System.out.println("httpPostInner");
	        
			//String params="{\"user_name\":\"luonianyuzhou@163.com\",\"type\":\"0\"}";
	        //String params = "{\"user_password\":\"123456\",\"app_version\":\"V1.0\",\"device_token\":\"6489cc96cb2289b6\",\"verify_code\":\"308021\",\"time_zone\":\"Asia/Shanghai\",\"user_name\":\"461261532@qq.com\",\"type\":\"0\"}";
	        byte[] data = params.getBytes(encoding);

			BufferedReader in = null;
			StringBuffer sb = new StringBuffer();
			
			
			URL url =new URL(urlNameString);        
			HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
			conn.setRequestMethod("POST");
	        conn.setDoOutput(true);        //application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据        
			conn.setRequestProperty("Content-Type", "application/x-javascript; charset="+ encoding);     
			conn.setRequestProperty("Content-Length", String.valueOf(data.length)); 
			conn.setConnectTimeout(120*1000);    
			OutputStream outStream = conn.getOutputStream();       
			outStream.write(data);    
			outStream.flush();    
			outStream.close();      
			//InputStream inStrm = conn.getInputStream(); 
			//System.out.println(conn.getResponseCode()); //响应代码 200表示成功
			int code = conn.getResponseCode();
			if(code == 200){
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while((line = in.readLine()) != null){
					sb.append(line);
				}				
				in.close();
				JSONObject object = JSONObject.fromObject(sb.toString());
				System.out.println("user_id:" + object.optString("user_id") + params);

                //Thread.sleep((long) (60 * 1000 * 200) );  
				
				
			}else{
				sb.append("-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---httpPostInner exception---");			
		}
		
	}
	
}

/*	                 
String para = "m=reqcalltaxi&id="+pass+"&key=35bc7f25daa881fa0974730276868f12&phone=13401038652&bx=116.311754&by=40.034496&"  
  
+ "bLocation=%E4%B8%8A%E5%9C%B0&bDes=%E5%8C%97%E4%BA%AC%E5%B8%82%E6%B5%B7%E6%B7%80%E5%8C%BA%E4%B8%8A%E5%9C%B03%E8%A1%97%E7%8E%AF%E5%B2%9B%E8%A5%BF%E5%8D%97%E8%A7%92(%E8%BF%91%E4%B8%8A%E5%9C%B0%E7%8E%AF%E5%B2%9B)&"  
  
+ "ex=116.315942&ey=40.05343&eLocation=%E8%A5%BF%E4%BA%8C%E6%97%97&eDes=333%E8%B7%AF%E5%86%85%E7%8E%AF,333%E8%B7%AF%E5%A4%96%E7%8E%AF,392%E8%B7%AF,%E8%BF%90%E9%80%9A114%E8%B7%AF,636%E8%B7%AF&"  
  
+ "time=15&userNum=1&isSalute=1&name=%E6%9C%B1%E5%85%88%E7%94%9F&level=3&credit=-3&bytime=2012-07-12%2016:34:17&type=0¬ifytime=2012-07-12%2016:34:17&s=123456&"  
  
+ "jsoncallback=jQuery16208664285382739452_1342059526704&_=1342059557372";  
  
System.out.println(host + para);  
  
URL url = new URL(host);// 此处填写供测试的url  
  
HttpURLConnection connection = (HttpURLConnection) url  
  
.openConnection();  
  
// connection.setRequestMethod("POST");  
  
// connection.setRequestProperty("Proxy-Connection",  
  
// "Keep-Alive");  
  
connection.setDoOutput(true);  
  
connection.setDoInput(true);  
  
PrintWriter out = new PrintWriter(connection  
  
.getOutputStream());  
  
out.print(para);  
  
out.flush();  
  
out.close();  
  
BufferedReader in = new BufferedReader(  
  
new InputStreamReader(connection  
  
.getInputStream()));  
  
String line = "";  
  
String result = "";  
  
while ((line = in.readLine()) != null) {  
  
    result += line;  
  
}  
  
// System.out.println(result);  
  
// Thread.sleep((long) (Math.random()) * 1000);  
  
// 释放  
*/



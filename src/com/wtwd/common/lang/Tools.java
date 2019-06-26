package com.wtwd.common.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;

import com.godoing.rose.lang.MD5;
import com.godoing.rose.log.LogFactory;


public class Tools {
	
	public final static String ZeroString = "0";
	public final static String OneString = "1";
	
	public static void main(String[] args) {
		String res = null;
		Tools tls = new Tools();
		res = tls.timeConvert("2017-12-15 15:02:44", "Europe/Prague", "UTC");
		System.out.println("res utc: " + res);
	}
	
	// file
		public static byte[] getContent(String filePath) throws IOException {
			File file = new File(filePath);

			long fileSize = file.length();

			if (fileSize > Integer.MAX_VALUE) {
				//System.out.println("file too big...");
				return null;
			}

			FileInputStream fi = new FileInputStream(file);

			byte[] buffer = new byte[(int) fileSize];

			int offset = 0;

			int numRead = 0;
			
			  while (offset < buffer.length && (numRead = fi.read(buffer, offset,
			  buffer.length - offset)) >= 0) {
			  
			  offset += numRead; }
			 
		//	byte[] b = new byte[fi.available()];
		//	offset = fi.read(b);

			if (offset != buffer.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());

			}
			fi.close();
			return buffer;
		}
		



		
		//yonghu 
		public String getSafeStringFromJson(final JSONObject obj, final String fld)
		{
			return obj.has(fld)?obj.getString(fld):"";			
		}
		//yonghu 
		public String getSafeStringFromJsonD0(final JSONObject obj, final String fld)
		{
			return obj.has(fld)?obj.getString(fld):"0";			
		}

		//yonghu 20150625 label
		public int getSafeIntFromString(final String obj)
		{
			try {
				if ( "null".equals(obj) )
					return 0;
				else
					return Integer.parseInt(obj);
			} catch(Exception e) {
				return 0;
			}
		}

		
		//yonghu 20150625 label
		public int getSafeIntFromJson(final JSONObject obj, final String fld)
		{
			return obj.has(fld)?obj.getInt(fld):-1;			
		}

		
		//用于文件名的拼接
		public String getStringFromDate_nmc(final Date data){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			return df.format(data); 
		}

		
		public String getStringFromDate(final Date data){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			return df.format(data); 
		}

		public String getUtcDateStrNow(){
	        Calendar cal = Calendar.getInstance();
	        TimeZone timeZone = cal.getTimeZone();
	        //System.out.println(timeZone.getID());
	        return timeConvert(getStringFromDate(new Date()), timeZone.getID(), "UTC"  );
		}		

		public Date getUtcDateStrNowDate(){
	        Calendar cal = Calendar.getInstance();
	        TimeZone timeZone = cal.getTimeZone();
	        //System.out.println(timeZone.getID());
	        String d1 = timeConvert(getStringFromDate(new Date()), timeZone.getID(), "UTC"  );
	        try {
	        	Date res = getDateFromString(d1);
	        	return res;
	        } catch(Exception e) {
	        	return new Date();
	        }
		}		
		
		
		public Date getDateFromString(final String data) throws ParseException{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			return df.parse(data); 
		}
		
		
		public String getUtilDateFromSqlDate(final Object obj)
		{
			if (isNullOrEmpty(obj)) {
				return "";
			} else {
			 Date dt = new Date(((java.sql.Date) obj).getTime());
			 DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			 return df.format(dt);
			}
		}

		public String getUtilDayFromSqlDate(final Object obj)
		{
			 Date dt = new Date(((java.sql.Date) obj).getTime());
			 DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
			 return df.format(dt);
		}
		
		public String getUtilDayFromSqlDatetime(final Object obj)
		{
			 Date dt = new Date(((java.sql.Timestamp) obj).getTime());
			 DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
			 return df.format(dt);
		}

		
	    public boolean isNullOrEmpty(Object obj) {  
	        if (obj == null)  
	            return true;  
	        
	        if ( "".equals(obj.toString().trim()) )
	            return true;  	        	

	        
	        if ( "null".equals(obj.toString()) )
	            return true;  	        	

	        if ( "\"null\"".equals(obj.toString()) )
	            return true;  	        	
	        
	        if ( "\"NULL\"".equals(obj.toString()) )
	            return true;  	        	

	        if ( "NULL".equals(obj.toString()) )
	            return true;  	        	

	        
	        
	        
	        if (obj instanceof CharSequence)  
	            return ((CharSequence) obj).length() == 0;  
	  
	        if (obj instanceof Collection)  
	            return ((Collection) obj).isEmpty();  
	  
	        if (obj instanceof Map)  
	            return ((Map) obj).isEmpty();  
	  
	        if (obj instanceof Object[]) {  
	            Object[] object = (Object[]) obj;  
	            if (object.length == 0) {  
	                return true;  
	            }  
	            boolean empty = true;  
	            for (int i = 0; i < object.length; i++) {  
	                if (!isNullOrEmpty(object[i])) {  
	                    empty = false;  
	                    break;  
	                }  
	            }  
	            return empty;  
	        }  
	        return false;  
	    }  

	    public long getLongFromDtStr(String d1) {		/* d1, d2 :例如 "2011-04-01 14:07:35" */
	    	long ret = 0l;
	        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间  
	        
	  
	        try {  
	            ret = d.parse(d1).getTime();
	        } catch (ParseException e) {  
	            e.printStackTrace(); 
	            return -1;
	        }
	        return ret;
	    }
	    
	    
	    public long getSecondsBetweenDays(String d1, String d2) {		/* d1, d2 :例如 "2011-04-01 14:07:35" */
	    	long ret = 0l;
	        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间  
	        
	  
	        try {  
	            ret = (d.parse(d2).getTime() - d.parse(d1)  
	                    .getTime()) / 1000;
	        } catch (ParseException e) {  
	            e.printStackTrace(); 
	            return -1;
	        }
	        return ret;
	    }

	    public long getSecondsBetweenDays(Date d1, Date d2) {		/* d1, d2 :例如 "2011-04-01 14:07:35" */
	    	long ret = 0l;
	  
	        ret = (d2.getTime() - d1.getTime()) / 1000;
	        return ret;
	    }
	    
		public String getOneZeroStringFromBool(String data)
		{
			if (  "true".equals(data) || "TRUE".equals(data)  ) {
				return "1";
			} else if (  "false".equals(data) || "FALSE".equals(data) ) {
				return "0";
			} else
				return data;
				
		}

		//从字符串的日期字符串加上指定的分钟数后的日期字符串
	    public String getDateStringAddMin(String d1, Integer min) {		/* d1, d2 :例如 "2011-04-01 14:07:35" */
	    	long ret = 0l;
	        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间  

	        try {  
	        
		        Date dt1 = d.parse(d1);
		        Calendar calendar=Calendar.getInstance();
		        calendar.setTime(dt1);
		        calendar.add(Calendar.MINUTE, min);
		        Date dta = calendar.getTime();
		        return getStringFromDate(dta);	        
	  
	        } catch (ParseException e) {  
	            e.printStackTrace();
	            return d1;
	        }
	    }

	
	    public String getDateStringAddSecond(String d1, Integer sec) {		/* d1, d2 :例如 "2011-04-01 14:07:35" */
	    	long ret = 0l;
	        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间  

	        try {  
	        
		        Date dt1 = d.parse(d1);
		        Calendar calendar=Calendar.getInstance();
		        calendar.setTime(dt1);
		        calendar.add(Calendar.SECOND, sec);
		        Date dta = calendar.getTime();
		        return getStringFromDate(dta);	        
	  
	        } catch (ParseException e) {  
	            e.printStackTrace();
	            return d1;
	        }
	    }
		
	    
	    public Boolean getBooleanFromInt(Integer para) {
	    	if ( para == 1 )
	    		return true;
	    	else
	    		return false;
	    }
	    
		public String getSqlStringFromIntList(Object[] idList)
		{
			if ( idList == null || idList.length <= 0 )
				return "";
			StringBuffer sb = new StringBuffer("(");
			int len = idList.length;
			for (  int i = 0; i < len-1; i++) {
				sb.append(idList[i]);
				sb.append(",");
			}
			sb.append(idList[len-1]);
			sb.append(")");
			return sb.toString();
				
		}

	    public int getDiffSteps(int step1, int step2) {
	    	if (step2 >= step1) {
	    		return (step2 - step1);
	    	} else {
	    		return (Constant.MAXSTEP - step1 + step2);
	    	}
	    }

		public String getHourMinFromDateString(final String data) {
			Log logger = LogFactory.getLog(Tools.class);

			try {
					
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				Date dt =  df.parse(data);
				SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");//设置日期格式
				return df1.format(dt);
			} catch( Exception e) {
				e.printStackTrace();
				logger.error(e);
				return data;
			}
			
		}
		
		
	    //abcd
		public String timeConvert(String sourceTime, String sourceId,
	            String targetId)
	    {
	        //校验入参是否合法
	        if (null == sourceId || "".equals(sourceId) || null == targetId
	                || "".equals(targetId) || null == sourceTime
	                || "".equals(sourceTime))
	        {
	            return "";
	        }
	        //校验 时间格式必须为：yyyy-MM-dd HH:mm:ss
	        String reg = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$";
	        if (!sourceTime.matches(reg))
	        {
	            return "";
	        }
	        
	        try
	        {
	            //时间格式
	            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            //根据入参原时区id，获取对应的timezone对象
	            TimeZone sourceTimeZone = TimeZone.getTimeZone(sourceId);
	            //设置SimpleDateFormat时区为原时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成原时区对应的date对象
	            df.setTimeZone(sourceTimeZone);
	            //将字符串sourceTime转化成原时区对应的date对象
	            Date sourceDate = df.parse(sourceTime);
	            
	            //开始转化时区：根据目标时区id设置目标TimeZone
	            TimeZone targetTimeZone = TimeZone.getTimeZone(targetId);
	            //设置SimpleDateFormat时区为目标时区（否则是本地默认时区），目的:用来将字符串sourceTime转化成目标时区对应的date对象
	            df.setTimeZone(targetTimeZone);
	            //得到目标时间字符串
	            String targetTime = df.format(sourceDate);
	            return targetTime;
	        }
	        catch (ParseException e)
	        {
	            e.printStackTrace();
	        }
	        return "";
	        
	    }

	    public boolean StrisNumeric(String str){  	
	    	try {
		    	String reg = "^-?[0-9]+";
		        if (!str.matches(reg)) 
		        	return false;
		        else
		        	return true;
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		return true;
	    	}
	    }
	    
	    public String getDateStringAddDay(String d1, Integer offset) {		/* d1, d2 :例如 "2011-04-01 14:07:35" */
	    	long ret = 0l;
	        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间  

	        try {  
	        
		        Date dt1 = d.parse(d1);
		        Calendar calendar=Calendar.getInstance();
		        calendar.setTime(dt1);
		        calendar.add(Calendar.DAY_OF_YEAR, offset);
		        Date dta = calendar.getTime();
		        return getStringFromDate(dta);	        
	  
	        } catch (ParseException e) {  
	            e.printStackTrace();
	            return d1;
	        }
	    }

		//从字符串的日期字符串加上指定的分钟数后的日期字符串
	    public String getDateStringAddMon(String d1, Integer mon) {		/* d1, d2 :例如 "2011-04-01 14:07:35" */
	    	long ret = 0l;
	        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间  

	        try {  
	        
		        Date dt1 = d.parse(d1);
		        Calendar calendar=Calendar.getInstance();
		        calendar.setTime(dt1);
		        calendar.add(Calendar.MONTH, mon);
		        Date dta = calendar.getTime();
		        return getStringFromDate(dta);	        
	  
	        } catch (ParseException e) {  
	            e.printStackTrace();
	            return d1;
	        }
	    }
	    //dist: 距离;   slong:时长；  snums: 步数； preLctType: 上次定位类型；  lctType : 本次定位类型
	    //preLctAcc: 上次定位精度；   lctAcc: 本次定位精度
	    
	    //返回值： 0： 表示有效定位(LCT_IS_VALID)
	    //         1： 表示重复定位(LCT_IS_REPEATED)
	    //         -1： 表示定位失败(LCT_IS_INVALID)
	    public int checkIfAbandonLctBtr(double dist, long slong, int snums, 
	    		String preLctType, 
	    		String lctType, 
	    		Float preLctAcc, Float lctAcc  
	    		) {


	    	if ("1".equals(lctType) && lctAcc < 20.9f )
	    		return Constant.LCT_IS_VALID;
	    	
	    	
	    	if ("4".equals(lctType) && lctAcc < 20.9f )
	    		return Constant.LCT_IS_VALID;
	    		    	
	    	//if ( dist < 5.0d )
	    	//	return Constant.LCT_IS_REPEATED;

	    	if  ( preLctAcc < lctAcc ) {
		    	if ( dist > 2000.0d ) {
		    		if ( slong < 40 ) 
		    			return Constant.LCT_IS_INVALID;
		    	}
		    	
		    	if ( dist > 1200.0d ) {
		    		if (lctAcc > 150)
		    			return Constant.LCT_IS_INVALID;	    				    		
		    	}
	    	}

	    	
	    	
	    	
	    	/*
	    	if ( preLctAcc!= null && preLctAcc <= 50.0f ) {
	    		if ( slong < 43 && dist > 700.0d ) {
	    			return false;	    		
	    		} else if ( slong < 83 && dist > 1530.0d ) {
		    		return false;
		    	} else if ( slong < 120 && dist > 3000.0d ) {
		    		return false;	    		
		    	} else if ( slong < 180 && dist > 4000.0d ) {
		    		return false;
		    	} else if ( slong < 300 && dist > 5000.0d ) {
		    		return false;	    		
		    	} 
	    	}*/

	    	if ( preLctAcc!= null && preLctAcc > 500.0f && lctAcc != null && lctAcc < 100.0f && slong > 20) {
	    		return Constant.LCT_IS_VALID;
	    	}
	    	
	    	
			if ( slong > 20 && snums > 21 ) {	    	
				if (preLctAcc != null && lctAcc != null && slong >=55 && preLctAcc >= lctAcc   )
		    		return Constant.LCT_IS_VALID;
			}
	    	
			if ( slong > 20 && snums > 21 && snums < 1000) {
				
				
				if ( lctAcc!= null && lctAcc < 100.0f  ) {
					if ( snums < 50 ) {
						if (dist < (double)slong && dist < (double)snums ) 
				    		return Constant.LCT_IS_VALID;
					} else if ( snums < 100) {
						if (dist < (double)2 * slong ) 
				    		return Constant.LCT_IS_VALID;
					} else if (snums < 150) {
						if (dist < (double)3 * slong ) 
				    		return Constant.LCT_IS_VALID;
					} else if (snums < 200) {
						if (dist < (double)4 * slong ) 
				    		return Constant.LCT_IS_VALID;
					} else
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 150.0f  ) {
					if ( slong >= 300 && preLctAcc!= null && preLctAcc >= 150.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 350.0f  ) {
					if ( slong >= 600 && preLctAcc!= null && preLctAcc >= 350.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 550.0f  ) {
					if ( slong >= 1200 && preLctAcc!= null && preLctAcc >= 550.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 850.0f  ) {
					if ( slong >= 1800  && preLctAcc!= null && preLctAcc >= 850.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 1250.0f  ) {
					if ( slong >= 3600  && preLctAcc!= null && preLctAcc >= 1250.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 3550.0f  ) {
					if ( slong >= 3600l * 24l)
			    		return Constant.LCT_IS_VALID;
				} else if (lctAcc != null && lctAcc < 5500.0f)  {
					if ( slong >= 3600l * 36l)
			    		return Constant.LCT_IS_VALID;
				}
 
			} else	if ( slong > 20 && snums >= 1000 && snums < 3000) {
				if ( slong >= 18000)
		    		return Constant.LCT_IS_VALID;
			} else	if ( slong > 20 && snums >= 3000) {
				if ( slong >= 9000)
		    		return Constant.LCT_IS_VALID;
			}
	    	
	    	if ( dist < (double)8 * slong ) {
				if ( slong >= 120 && snums > 500 &&  lctAcc < 600.0f )
		    		return Constant.LCT_IS_VALID;
		    	
				if ( slong >= 120 && snums >= 300 && dist < (double)2 * slong && lctAcc < 51.0f )
		    		return Constant.LCT_IS_VALID;
				
				if (   dist < (double)slong && dist < (double)snums ) {
		    		return Constant.LCT_IS_VALID;
				}
				
				if (preLctType != null && slong >= 300 && 
						!"2".equals(preLctType) )
		    		return Constant.LCT_IS_VALID;
				
				if ( preLctAcc!= null  && slong >= 300 && 
						preLctAcc > 170.0f )
		    		return Constant.LCT_IS_VALID;
	
				if ( preLctAcc!= null && lctAcc != null  && slong >= 300 && 
						lctAcc < 70.0f && preLctAcc > 80.0f )
		    		return Constant.LCT_IS_VALID;
				
				if ( preLctAcc!= null && lctAcc != null  && slong >= 300 && 
						lctAcc < 50.0f && preLctAcc > 60.0f )
		    		return Constant.LCT_IS_VALID;
				
				
				if (lctType != null && 
						preLctAcc != null && lctAcc != null && 
						("1".equals(lctType) || "4".equals(lctType)) && 
						( preLctAcc - lctAcc > 8.0f ) ) 
		    		return Constant.LCT_IS_VALID;
				
				
				if (slong <= 0)
		    		return Constant.LCT_IS_REPEATED;
				
				if ( snums * 100 / slong > 40 && snums * 100 / slong < 150 && slong > 20)
		    		return Constant.LCT_IS_VALID;

				if ( slong > 20 && snums > 50 && slong > 25 && lctAcc < 62.0f)
		    		return Constant.LCT_IS_VALID;
				
				
				if ( slong > 20 && snums >= 30 && slong > 25 && fabs(preLctAcc,lctAcc ) < 15.0f )
		    		return Constant.LCT_IS_VALID;
				
				if ( 	preLctAcc != null && 
						lctAcc != null && 
						preLctAcc > 170.0f && 
						lctAcc < 50.0f &&
						dist < (double)3 * slong  )
		    		return Constant.LCT_IS_VALID;
		    	
				if ( preLctAcc != null && 
						lctAcc != null &&
						preLctAcc > lctAcc &&
						slong >= 900 )
		    		return Constant.LCT_IS_VALID;
	    	}



	    	
			if ( slong >= 300 && lctAcc < 100.0f )
	    		return Constant.LCT_IS_VALID;
	    	
			if ( slong >= 3600 * 12 && lctAcc < 600.0f )
	    		return Constant.LCT_IS_VALID;
			if ( slong >= 3600 * 24 && lctAcc < 1200.0f )
	    		return Constant.LCT_IS_VALID;
			if ( slong >= 3600 * 36 && lctAcc < 1600.0f )
	    		return Constant.LCT_IS_VALID;
	    	
	    	
    		return Constant.LCT_IS_REPEATED;

	    }

	    
	    //dist: 距离;   slong:时长；  snums: 步数； preLctType: 上次定位类型；  lctType : 本次定位类型
	    //preLctAcc: 上次定位精度；   lctAcc: 本次定位精度
	    
	    //返回值： 0： 表示有效定位(LCT_IS_VALID)
	    //         1： 表示重复定位(LCT_IS_REPEATED)
	    //         -1： 表示定位失败(LCT_IS_INVALID)
	    public int checkIfAbandonLctSame(double dist, long slong, int snums, 
	    		String preLctType, 
	    		String lctType, 
	    		Float preLctAcc, Float lctAcc  
	    		) {


	    	if ("1".equals(lctType) && lctAcc < 20.9f )
	    		return Constant.LCT_IS_VALID;
	    	
	    	
	    	if ("4".equals(lctType) && lctAcc < 20.9f )
	    		return Constant.LCT_IS_VALID;
	    		    	
	    	if ( dist < 5.0d )
	    		return Constant.LCT_IS_REPEATED;

	    	if ( dist > 2000.0d ) {
	    		if ( slong < 40 ) 
	    			return Constant.LCT_IS_INVALID;
	    	}
	    	
	    	if ( dist > 1200.0d ) {
	    		if (lctAcc > 150)
	    			return Constant.LCT_IS_INVALID;	    				    		
	    	}

	    	
	    	
	    	
	    	/*
	    	if ( preLctAcc!= null && preLctAcc <= 50.0f ) {
	    		if ( slong < 43 && dist > 700.0d ) {
	    			return false;	    		
	    		} else if ( slong < 83 && dist > 1530.0d ) {
		    		return false;
		    	} else if ( slong < 120 && dist > 3000.0d ) {
		    		return false;	    		
		    	} else if ( slong < 180 && dist > 4000.0d ) {
		    		return false;
		    	} else if ( slong < 300 && dist > 5000.0d ) {
		    		return false;	    		
		    	} 
	    	}*/

	    	if ( preLctAcc!= null && preLctAcc > 500.0f && lctAcc != null && lctAcc < 100.0f && slong > 20) {
	    		return Constant.LCT_IS_VALID;
	    	}
	    	
	    	
			if ( slong > 20 && snums > 21 ) {	    	
				if (preLctAcc != null && lctAcc != null && slong >=55 && preLctAcc >= lctAcc   )
		    		return Constant.LCT_IS_VALID;
			}
	    	
			if ( slong > 20 && snums > 21 && snums < 1000) {
				
				
				if ( lctAcc!= null && lctAcc < 100.0f  ) {
					if ( snums < 50 ) {
						if (dist < (double)slong && dist < (double)snums ) 
				    		return Constant.LCT_IS_VALID;
					} else if ( snums < 100) {
						if (dist < (double)2 * slong ) 
				    		return Constant.LCT_IS_VALID;
					} else if (snums < 150) {
						if (dist < (double)3 * slong ) 
				    		return Constant.LCT_IS_VALID;
					} else if (snums < 200) {
						if (dist < (double)4 * slong ) 
				    		return Constant.LCT_IS_VALID;
					} else
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 150.0f  ) {
					if ( slong >= 300 && preLctAcc!= null && preLctAcc >= 150.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 350.0f  ) {
					if ( slong >= 600 && preLctAcc!= null && preLctAcc >= 350.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 550.0f  ) {
					if ( slong >= 1200 && preLctAcc!= null && preLctAcc >= 550.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 850.0f  ) {
					if ( slong >= 1800  && preLctAcc!= null && preLctAcc >= 850.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 1250.0f  ) {
					if ( slong >= 3600  && preLctAcc!= null && preLctAcc >= 1250.0f)
			    		return Constant.LCT_IS_VALID;
				} else if ( lctAcc!= null && lctAcc < 3550.0f  ) {
					if ( slong >= 3600l * 24l)
			    		return Constant.LCT_IS_VALID;
				} else if (lctAcc != null && lctAcc < 5500.0f)  {
					if ( slong >= 3600l * 36l)
			    		return Constant.LCT_IS_VALID;
				}
 
			} else	if ( slong > 20 && snums >= 1000 && snums < 3000) {
				if ( slong >= 18000)
		    		return Constant.LCT_IS_VALID;
			} else	if ( slong > 20 && snums >= 3000) {
				if ( slong >= 9000)
		    		return Constant.LCT_IS_VALID;
			}
	    	
	    	if ( dist < (double)8 * slong ) {
				if ( slong >= 120 && snums > 500 &&  lctAcc < 600.0f )
		    		return Constant.LCT_IS_VALID;
		    	
				if ( slong >= 120 && snums >= 300 && dist < (double)2 * slong && lctAcc < 51.0f )
		    		return Constant.LCT_IS_VALID;
				
				if (   dist < (double)slong && dist < (double)snums ) {
		    		return Constant.LCT_IS_VALID;
				}
				
				if (preLctType != null && slong >= 300 && 
						!"2".equals(preLctType) )
		    		return Constant.LCT_IS_VALID;
				
				if ( preLctAcc!= null  && slong >= 300 && 
						preLctAcc > 170.0f )
		    		return Constant.LCT_IS_VALID;
	
				if ( preLctAcc!= null && lctAcc != null  && slong >= 300 && 
						lctAcc < 70.0f && preLctAcc > 80.0f )
		    		return Constant.LCT_IS_VALID;
				
				if ( preLctAcc!= null && lctAcc != null  && slong >= 300 && 
						lctAcc < 50.0f && preLctAcc > 60.0f )
		    		return Constant.LCT_IS_VALID;
				
				
				if (lctType != null && 
						preLctAcc != null && lctAcc != null && 
						("1".equals(lctType) || "4".equals(lctType)) && 
						( preLctAcc - lctAcc > 8.0f ) ) 
		    		return Constant.LCT_IS_VALID;
				
				
				if (slong == 0)
		    		return Constant.LCT_IS_REPEATED;
				
				if ( snums * 100 / slong > 40 && snums * 100 / slong < 150 && slong > 20)
		    		return Constant.LCT_IS_VALID;

				if ( slong > 20 && snums > 50 && slong > 25 && lctAcc < 62.0f)
		    		return Constant.LCT_IS_VALID;
				
				
				if ( slong > 20 && snums >= 30 && slong > 25 && fabs(preLctAcc,lctAcc ) < 15.0f )
		    		return Constant.LCT_IS_VALID;
				
				if ( 	preLctAcc != null && 
						lctAcc != null && 
						preLctAcc > 170.0f && 
						lctAcc < 50.0f &&
						dist < (double)3 * slong  )
		    		return Constant.LCT_IS_VALID;
		    	
				if ( preLctAcc != null && 
						lctAcc != null &&
						preLctAcc > lctAcc &&
						slong >= 900 )
		    		return Constant.LCT_IS_VALID;
	    	}



	    	
			if ( slong >= 300 && lctAcc < 100.0f )
	    		return Constant.LCT_IS_VALID;
	    	
			if ( slong >= 3600 * 12 && lctAcc < 600.0f )
	    		return Constant.LCT_IS_VALID;
			if ( slong >= 3600 * 24 && lctAcc < 1200.0f )
	    		return Constant.LCT_IS_VALID;
			if ( slong >= 3600 * 36 && lctAcc < 1600.0f )
	    		return Constant.LCT_IS_VALID;
	    	
	    	
    		return Constant.LCT_IS_REPEATED;

	    }
	    
	    
	    //dist: 距离;   slong:时长；  snums: 步数； preLctType: 上次定位类型；  lctType : 本次定位类型
	    //preLctAcc: 上次定位精度；   lctAcc: 本次定位精度
	    public boolean checkIfAbandonLct(double dist, long slong, int snums, 
	    		String preLctType, 
	    		String lctType, 
	    		Float preLctAcc, Float lctAcc  
	    		) {


	    	if ("1".equals(lctType) && lctAcc < 20.9f )
	    		return true;
	    	
	    	
	    	if ("4".equals(lctType) && lctAcc < 20.9f )
	    		return true;
	    		    	
	    	if ( dist < 5.0d )
	    		return false;

	    	/*
	    	if ( preLctAcc!= null && preLctAcc <= 50.0f ) {
	    		if ( slong < 43 && dist > 700.0d ) {
	    			return false;	    		
	    		} else if ( slong < 83 && dist > 1530.0d ) {
		    		return false;
		    	} else if ( slong < 120 && dist > 3000.0d ) {
		    		return false;	    		
		    	} else if ( slong < 180 && dist > 4000.0d ) {
		    		return false;
		    	} else if ( slong < 300 && dist > 5000.0d ) {
		    		return false;	    		
		    	} 
	    	}*/

	    	if ( preLctAcc!= null && preLctAcc > 500.0f && lctAcc != null && lctAcc < 100.0f && slong > 20) {
	    		return true;
	    	}
	    	
	    	
			if ( slong > 20 && snums > 21 ) {	    	
				if (preLctAcc != null && lctAcc != null && slong >=55 && preLctAcc >= lctAcc   )
					return true;
			}
	    	
			if ( slong > 20 && snums > 21 && snums < 1000) {
				
				
				if ( lctAcc!= null && lctAcc < 100.0f  ) {
					if ( snums < 50 ) {
						if (dist < (double)slong && dist < (double)snums ) 
							return true;
					} else if ( snums < 100) {
						if (dist < (double)2 * slong ) 
							return true;						
					} else if (snums < 150) {
						if (dist < (double)3 * slong ) 
							return true;												
					} else if (snums < 200) {
						if (dist < (double)4 * slong ) 
							return true;												
					} else
						return true;
				} else if ( lctAcc!= null && lctAcc < 150.0f  ) {
					if ( slong >= 300 && preLctAcc!= null && preLctAcc >= 150.0f)
						return true;
				} else if ( lctAcc!= null && lctAcc < 350.0f  ) {
					if ( slong >= 600 && preLctAcc!= null && preLctAcc >= 350.0f)
						return true;
				} else if ( lctAcc!= null && lctAcc < 550.0f  ) {
					if ( slong >= 1200 && preLctAcc!= null && preLctAcc >= 550.0f)
						return true;
				} else if ( lctAcc!= null && lctAcc < 850.0f  ) {
					if ( slong >= 1800  && preLctAcc!= null && preLctAcc >= 850.0f)
						return true;
				} else if ( lctAcc!= null && lctAcc < 1250.0f  ) {
					if ( slong >= 3600  && preLctAcc!= null && preLctAcc >= 1250.0f)
						return true;
				} else if ( lctAcc!= null && lctAcc < 3550.0f  ) {
					if ( slong >= 3600l * 24l)
						return true;
				} else if (lctAcc != null && lctAcc < 5500.0f)  {
					if ( slong >= 3600l * 36l)
						return true;										
				}
 
			} else	if ( slong > 20 && snums >= 1000 && snums < 3000) {
				if ( slong >= 18000)
					return true;										
			} else	if ( slong > 20 && snums >= 3000) {
				if ( slong >= 9000)
					return true;										
			}
	    	
	    	if ( dist < (double)8 * slong ) {
				if ( slong >= 120 && snums > 500 &&  lctAcc < 600.0f )
					return true;
		    	
				if ( slong >= 120 && snums >= 300 && dist < (double)2 * slong && lctAcc < 51.0f )
					return true;					
				
				if (   dist < (double)slong && dist < (double)snums ) {
					return true;
				}
				
				if (preLctType != null && slong >= 300 && 
						!"2".equals(preLctType) )
					return true;
				
				if ( preLctAcc!= null  && slong >= 300 && 
						preLctAcc > 170.0f )
					return true;
	
				if ( preLctAcc!= null && lctAcc != null  && slong >= 300 && 
						lctAcc < 70.0f && preLctAcc > 80.0f )
					return true;
				
				if ( preLctAcc!= null && lctAcc != null  && slong >= 300 && 
						lctAcc < 50.0f && preLctAcc > 60.0f )
					return true;
				
				
				if (lctType != null && 
						preLctAcc != null && lctAcc != null && 
						("1".equals(lctType) || "4".equals(lctType)) && 
						( preLctAcc - lctAcc > 8.0f ) ) 
					return true;
				
				
				if (slong == 0)
					return false;
				
				if ( snums * 100 / slong > 40 && snums * 100 / slong < 150 && slong > 20)
					return true;

				if ( slong > 20 && snums > 50 && slong > 25 && lctAcc < 62.0f)
					return true;
				
				
				if ( slong > 20 && snums >= 30 && slong > 25 && fabs(preLctAcc,lctAcc ) < 15.0f )
					return true;
				
				if ( 	preLctAcc != null && 
						lctAcc != null && 
						preLctAcc > 170.0f && 
						lctAcc < 50.0f &&
						dist < (double)3 * slong  )
					return true;
		    	
				if ( preLctAcc != null && 
						lctAcc != null &&
						preLctAcc > lctAcc &&
						slong >= 900 )
					return true;
	    	}



	    	
			if ( slong >= 300 && lctAcc < 100.0f )
				return true;	    		
	    	
			if ( slong >= 3600 * 12 && lctAcc < 600.0f )
				return true;	    		
			if ( slong >= 3600 * 24 && lctAcc < 1200.0f )
				return true;	    		
			if ( slong >= 3600 * 36 && lctAcc < 1600.0f )
				return true;	    		
	    	
	    	
	    	return false;

	    }
	    
	    public Float fabs(Float f1, Float f2) {
	    	if (  f1 > f2)
	    		return f1 - f2;
	    	else
	    		return f2 - f1;
	    }

	    public synchronized static boolean testa(double dist, long slong, int snums, String preLctType, 
	    		String lctType, 
	    		Float preLctAcc, Float lctAcc   ) {
			
			if ( snums * 100 / slong > 60 && snums * 100 / slong < 150 && slong > 20)
				return true;
			
			return false;
	    }

	    public String getStringFullFromDate(Date d1) {		/* d1, d2 :例如 "2011-04-01 14:07:35" */
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    	
	    	String ret = dateFormat.format(d1);
	 
	        return ret;
	    }
	    

	    public String getMd5(String ptkn) {
	    	return MD5.MD5(ptkn);
	    }
	    
	    public String getDummySn() throws Exception {
			Properties pros = new Properties();
			pros.load(this.getClass().getClassLoader()
					.getResourceAsStream("server.properties"));
			String sn = pros.getProperty("dummyimei");
			return sn;
		}

	    public String getDbPwd() throws Exception {
			Properties pros = new Properties();
			pros.load(this.getClass().getClassLoader()
					.getResourceAsStream("jdbc.properties"));
			String sn = pros.getProperty("jdbc.password");
			return sn;
		}
	    
	    
		public String getDummyVer() throws Exception {
			Properties pros = new Properties();
			pros.load(this.getClass().getClassLoader()
					.getResourceAsStream("server.properties"));
			String ver = pros.getProperty("dummyver");
			return ver;
		}
		public String getDummyVerEu() throws Exception {
			Properties pros = new Properties();
			pros.load(this.getClass().getClassLoader()
					.getResourceAsStream("server.properties"));
			String ver = pros.getProperty("dummyverEu");
			return ver;
		}

		public String getDreamemail() throws Exception {
			Properties pros = new Properties();
			pros.load(this.getClass().getClassLoader()
					.getResourceAsStream("server.properties"));
			String res = pros.getProperty("dreamemail");
			return res;
		}
		
		
		
		public static String getDevPort() throws Exception {
			Properties pros = new Properties();
			Tools tls = new Tools();
			pros.load(tls.getClass().getClassLoader()
					.getResourceAsStream("server.properties"));
			String pt = pros.getProperty("devPort");
			tls = null;
			return pt;
		}

		public static String getHttpPort() throws Exception {
			Properties pros = new Properties();
			Tools tls = new Tools();
			pros.load(tls.getClass().getClassLoader()
					.getResourceAsStream("server.properties"));
			String pt = pros.getProperty("httpPort");
			tls = null;
			return pt;
		}
		
		
}

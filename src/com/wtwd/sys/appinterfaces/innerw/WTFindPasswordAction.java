//yonghu create 20160625 label
package com.wtwd.sys.appinterfaces.innerw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;
import com.godoing.rose.log.LogFactory;
import com.wtwd.common.config.ServiceBean;
import com.wtwd.common.http.BaseAction;
import com.wtwd.common.lang.Constant;
import com.wtwd.common.lang.Tools;
import com.wtwd.sys.innerw.wappusers.domain.WappUsers;
import com.wtwd.sys.innerw.wappusers.domain.logic.WappUsersFacade;
import com.wtwd.sys.innerw.wappuserverify.domain.WappuserVerify;
import com.wtwd.sys.innerw.wappuserverify.domain.logic.WappuserVerifyFacade;

public class WTFindPasswordAction extends BaseAction{
	
	Log logger = LogFactory.getLog(WTFindPasswordAction.class);
	String loginout = "{\"request\":\"SERVER_UPDATEAPPUSERS_RE\"}";
	Integer dfg = 0;
	
	public Integer getDfg() {
		return dfg;
	}

	public void setDfg(Integer dfg) {
		this.dfg = dfg;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Tools tls = new Tools();
		
		request.setCharacterEncoding("UTF-8");
		String href= request.getServletPath();
		//Date start = new Date();
		JSONObject json = new JSONObject();
		try{
			ServletInputStream input = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuffer sb = new StringBuffer();
			String online = "";
			while((online = reader.readLine()) != null){
				sb.append(online);
			}			
			
			JSONObject object = JSONObject.fromObject(sb.toString());
			String user_name = object.getString("user_name");

			//super.logAction(user_name, object.optInt("device_id"), "WTFindPasswordAction");			
						
			Integer type = tls.getSafeIntFromJson(object, "type"); 
			String belongProject = ( tls.getSafeStringFromJson(object, "belong_project").equals("") ) ? "1" : tls.getSafeStringFromJson(object, "belong_project") ;
			dfg = object.optInt("at");

			WappUsers vo = new WappUsers();
			WappUsersFacade facade = ServiceBean.getInstance().getWappUsersFacade();
			vo.setCondition("email ='"+user_name+"' and belong_project ='"+belongProject+"'");
			
			List<DataMap> list = facade.getWappUsers(vo);
			
			String verify_code = null;
			
			if(list.size() > 0){				
		    	switch (type) {
					case 0:	// 0表示忘记密码申请
						ProForgetPassword(user_name, belongProject );
						//String rand = ProForgetPassword(user_name, belongProject );
						//json.put(Constant.VERIFY_CODE, rand);						
						result = Constant.SUCCESS_CODE;									
						break;
					case 1:	//1表示验证，用前一步骤中通过邮箱得到的验证码来实际与后台验证
						//ProVerifyForget();
						verify_code =  tls.getSafeStringFromJson(object, "verify_code"); 	
						result  = ProVerifyForget(user_name, belongProject, verify_code, 1);
						
						//String rand1 = getVerifyCode();
						//updateProForgetPwdCheckInfoLevel2(user_name, belongProject, rand1);
						//json.put(Constant.VERIFY_CODE, rand1);
						
						//result = Constant.SUCCESS_CODE;
						if ( result == Constant.SUCCESS_CODE) {
				        	String new_password = tls.getSafeStringFromJson(object, "new_password");
				        	
				    		if ("".equals(new_password)) {
				    			result = Constant.FAIL_CODE;
				    		}
							int ts = object.optInt("ts");
				    		
				    		if (ts > 0)
								vo.setPassmd(new_password);
				    		else
				    			vo.setUser_password(new_password);
				    		
				    		if ( facade.updateWappUsers(vo) > 0 )
					        	result = Constant.SUCCESS_CODE;				
				    		else						
				    			result = Constant.FAIL_CODE;										
						}
						break;
			        case 2:	//2 表示重设密码
			        	//ProResetPwd();
			        	verify_code =  tls.getSafeStringFromJson(object, "verify_code"); 	
						result  = ProVerifyForgetLevel2(user_name, belongProject, verify_code, 1);
						if ( result == Constant.FAIL_CODE )
							break;

			        	String new_password = tls.getSafeStringFromJson(object, "new_password");
			    		if ("".equals(new_password)) {
			    			result = Constant.FAIL_CODE;
			    			break;
			    		}
						int ts = object.optInt("ts");
			    		
			    		if (ts > 0)
							vo.setPassmd(new_password);
			    		else
			    			vo.setUser_password(new_password);

			    		if ( facade.updateWappUsers(vo) > 0 )
				        	result = Constant.SUCCESS_CODE;				
			    		else						
			    			result = Constant.FAIL_CODE;				
			        	break;
					default:
						result = Constant.FAIL_CODE;				
						break;
				}				
				
			}else{				
				result = Constant.ERR_APPUSER_NOT_EXIST;				
			}
		}catch(Exception e){
			e.printStackTrace();	
			StringBuffer sb = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			Throwable cause = e.getCause();		
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			String resultSb = writer.toString();
			sb.append(resultSb);
			
			logger.error(e);
			result = Constant.EXCEPTION_CODE;
			json.put(Constant.EXCEPTION, sb.toString());
		}

		json.put("request", href);
		json.put(Constant.RESULTCODE, result);
		response.setCharacterEncoding("UTF-8");	
		response.getWriter().write(json.toString());
		
		return null;
	}	
		
	public String ProForgetPassword(String emailaddress, String belongProject) throws MessagingException, Exception
	{
		Random random = new Random();
		StringBuffer randNumber = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			randNumber.append(rand);
		}
		
		CreateMessage(getServerName(), randNumber.toString(), emailaddress);
		updateProForgetPwdCheckInfo(emailaddress, belongProject, randNumber.toString());
		tmrResetCheckInfo(emailaddress, randNumber.toString(), belongProject);
		return randNumber.toString();
		
	}	
	
	// 发送验证码
	public void CreateMessage(String server, String rand, String emailaddress)
			throws MessagingException, IOException {
		Tools tls = new Tools();
		
		// String[] fromEmail = server.split(",");
		String fromEmail = "pioneeriot.com@gmail.com";
		String body = "Your verify code is " + rand; 
        String subject = "Your verify code";
		logger.info("email sender： " + fromEmail);
		
		// ****************************创建会话***************************************
		final Properties props = new Properties();		
		//String emlDestUp = emailaddress.toUpperCase();
		gmailssl(props);
		/*if (emlDestUp.contains("@QQ.COM") || emlDestUp.contains("@PIONEER.NET.AU")  ) {			
			props.put("mail.smtp.host", "smtp.paby.com");// 发件人使用发邮件的电子信箱服务器
			props.put("mail.smtp.auth", "true");
			props.put("mail.user", "service@paby.com");
			props.put("mail.password", "Pb654321Waterworld");
			fromEmail[0] = "service@paby.com";
						
			props.put("mail.smtp.host", "smtp.qq.com");// 发件人使用发邮件的电子信箱服务器
			props.put("mail.smtp.auth", "true");
			props.put("mail.user", "461261532@qq.com");
			props.put("mail.password", "zqafvhelhvxrbggb");
			props.put("mail.smtp.port", "587");			
			fromEmail[0] = "461261532@qq.com";
			
			props.put("mail.smtp.host", "smtp.mxhichina.com");// 发件人使用发邮件的电子信箱服务器
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");						
			props.put("mail.user", "pioneeriot.com@gmail.com");
			props.put("mail.password", "gmapio88");
			props.put("mail.user", "service@paby.com");
			props.put("mail.password", "Paby123456");
		} else {
			gmailssl(props);
			props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");// 发件人使用发邮件的电子信箱服务器
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");
			props.put("mail.user", "AKIAJPEOFRGFATV42SDQ");
			props.put("mail.password", "AuhmFfe1lMRSVxPpo44ZfQjjPGqyiZsymzJOtfebUEKc");
		}*/
				
		/*if (dfg > 0) {
			Properties pros = new Properties();
			pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
			fromEmail[0] = pros.getProperty("dreamemail");
		}*/				
		/*
		 * http://blog.csdn.net/wangxinqn/article/details/1708705
		 * 
		近日使用javamail 为公司的软件添加了邮件收发功能。遇到了Unsupported record version Unknown-50.49异常。
		该异常只会在发送邮件的时候产生，而且是应为所有邮箱使用了SSL加密功能。应该是javamail包的问题
		初步的解决方案是在你的发送类里 加上props.put("mail.smtp.quitwait", "false");将该异常屏蔽调		
		*/
		props.put("mail.smtp.quitwait", "false");
		
		Authenticator atctr = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String userName = props.getProperty("mail.user");
				String passWord = props.getProperty("mail.password");
				return new PasswordAuthentication(userName, passWord);				
			}
		};
		
		Session mailsession = Session.getInstance(props, atctr); // 获得默认的session对象
		mailsession.setDebug(true);

		// *****************************构造消息**************************************
		MimeMessage msg = new MimeMessage(mailsession);
		
		msg.setFrom(new InternetAddress(fromEmail)); 
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailaddress)); // 设置收件人地址并规定其类型
		//msg.setSentDate(new Date()); // 设置发信时间
		if (Constant.timeUtcFlag)		
			msg.setSentDate(tls.getUtcDateStrNowDate() );
		else
			msg.setSentDate(new Date()); // 设置发信时间
		
		msg.setSubject(subject); // 设置主题
		msg.setText(body);
		msg.setContent(body, "text/html;charset=UTF-8"); // 设置 正文

		//Transport transport = mailsession.getTransport("smtp");
		//transport.connect("mail.waterworld.com.cn", fromEmail[0].split("@")[0],
		//		fromEmail[1]);// 发邮件人帐户密码,此外是我的帐户密码，使用时请修改�?
		//transport.connect("smtp.126.com", fromEmail[0].split("@")[0],
		//		fromEmail[1]);// 发邮件人帐户密码,此外是我的帐户密码，使用时请修改�?

		//		transport.sendMessage(msg, msg.getAllRecipients());
		//transport.close();
		// *******************************发送消�?*******************************
		// msg.writeTo(System.out);//保存消息 并在控制�?显示消息对象中属性信�?
		// System.out.println("邮件已成功发送到 " + username);
		Transport.send(msg);
		logger.info(emailaddress + " email has sended!");
		//tmrResetCheckInfo();
	}
	
	//private String lserver;
	private String ltitle;
	private String lcontent;
	private String laddonName;
	private String ldest;
	private String lfrom;
		
	public void initPara(String from, String dest, String title, String content, String addonName) {
		lfrom = from;
		ldest = dest;
		ltitle = title;
		lcontent = content;
		laddonName = addonName;
	}
	
	/*
     * gmail邮箱SSL方式
     */
    private static void gmailssl(Properties props) {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        //props.put("mail.debug", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.user", "pioneeriot.com@gmail.com");
		props.put("mail.password", "gmapio88");
    }
	
	// 意见反馈
	public void CreateMessageIn() throws MessagingException, IOException {		
		Tools tls = new Tools();		
		// Get a Properties object
		final Properties props = new Properties();
		gmailssl(props);
		Authenticator atctr = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String userName = props.getProperty("mail.user");
				String passWord = props.getProperty("mail.password");
				return new PasswordAuthentication(userName, passWord);				
			}
		};
		
		Session mailsession = Session.getInstance(props, atctr); // 获得默认的session对象
		mailsession.setDebug(true);

		// -- Create a new message --
		MimeMessage msg = new MimeMessage(mailsession);
		
		InternetAddress from = null;
		if (lfrom == null ) {
			/*Properties pros = new Properties();
			String from_email = "pioneeriot.com@gmail.com";
			if (dfg > 0) {
				pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));	
				from_email = pros.getProperty("dreamemail");
			}*/						
			from = new InternetAddress("pioneeriot.com@gmail.com");			
		} else{
			from = new InternetAddress(lfrom);			
		}
		
		msg.setFrom(from);   // 发送者email帐号
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(ldest)); // 设置收件人地址并规定其类型
		if ( Constant.timeUtcFlag )	 // true	
			msg.setSentDate(tls.getUtcDateStrNowDate());
		else
			msg.setSentDate(new Date()); // 设置发信时间		
		msg.setSubject(ltitle); // 设置主题
		msg.setText(lcontent);
		msg.setContent(lcontent, "text/html;charset=UTF-8"); // 设置 正文

		if (laddonName != null) {
			/*// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件  
            Multipart multipart = new MimeMultipart();  
            // 设置邮件的文本内容  
            BodyPart contentPart = new MimeBodyPart();   
            contentPart.setText(lcontent);
            multipart.addBodyPart(contentPart);
            // 添加附件  
            BodyPart messageBodyPart = new MimeBodyPart();  
            DataSource source = new FileDataSource(laddonName);            
            // 添加附件的内容  
            messageBodyPart.setDataHandler(new DataHandler(source));                        
            // 添加附件的标题  
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码  
            messageBodyPart.setFileName(MimeUtility.encodeText("Image"));  
            multipart.addBodyPart(messageBodyPart);  
            msg.setContent(multipart); */ 
			
			// 创建邮件正文  
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(lcontent + "<br/><img src='cid:image_id'/>", "text/html;charset=UTF-8");
			// 创建图片  
            MimeBodyPart img = new MimeBodyPart();   
            DataHandler dh = new DataHandler(new FileDataSource(laddonName));//图片路径  
            img.setDataHandler(dh);
            img.setContentID("image_id"); // 创建图片的一个表示用于显示在邮件中显示    

            MimeMultipart mm = new MimeMultipart();  
            mm.addBodyPart(text);  
            mm.addBodyPart(img);  
            mm.setSubType("related");// 设置正文与图片之间的关系  
            // 图片与正文的 body  
            MimeBodyPart all = new MimeBodyPart();  
            all.setContent(mm);
            msg.setContent(mm);
		}
		// 保存邮件  
        msg.saveChanges(); 
        // 发送邮件
		Transport.send(msg);
		logger.info("email has sended to:" + ldest + ", from:" + from);
		//tmrResetCheckInfo();
	}
	
	public String getServerName() throws Exception {
		String mailName = "", mailpass = "";
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
		mailName = pros.getProperty("mailname");
		mailpass = pros.getProperty("mailpass");
		return mailName + "," + mailpass;
	}
	
	private void tmrResetCheckInfo(final String emailaddress, final String verify_code, final String belongProject)  throws SystemException {
		Timer timer = new Timer();
		timer.schedule( new TimerTask() {
				public void run() {
					try {						
						resetProForgetPwdCheckInfo(emailaddress, verify_code, belongProject);
					}
					catch (Exception e){
					}
					
				}
			},1000	* 60 * 4		//3 minutes
	    );					
	}

	private void resetProForgetPwdCheckInfo(String emailaddress, String verify_code, String belongProject) throws SystemException {
		WappuserVerify vo = new WappuserVerify();
		WappuserVerifyFacade facade = ServiceBean.getInstance().getWappuserVerifyFacade();
		vo.setCondition("email = '" + emailaddress.trim() + "' and verify_code='" + verify_code.trim() + "' and belong_project = " + belongProject);
		facade.delData(vo);	
	}
		
	private void updateProForgetPwdCheckInfo(String emailaddress, String belongProject, String rand) throws SystemException	{
		WappuserVerify vo = new WappuserVerify();
		WappuserVerifyFacade facade = ServiceBean.getInstance().getWappuserVerifyFacade();
		Tools tls = new Tools();	
		
		vo.setEmail(emailaddress);
		vo.setBelong_project(belongProject);
		vo.setVerify_code(rand);
		//vo.setCreate_time((Tools.getStringFromDate(new Date())));
		if ( Constant.timeUtcFlag )		
			vo.setCreate_time(tls.getUtcDateStrNow());
		else
			vo.setCreate_time((tls.getStringFromDate(new Date())));
		
		facade.insertData(vo);
		
	}
		
	public int ProVerifyForget(String emailaddress, String belongProject, String verifyCode,int resetOption) throws SystemException	{
		if ("".equals(verifyCode))
			return Constant.FAIL_CODE;			
		WappuserVerify vo = new WappuserVerify();
		WappuserVerifyFacade facade = ServiceBean.getInstance().getWappuserVerifyFacade();
		vo.setCondition("email = '" + emailaddress.trim() + "' and belong_project = " + belongProject + " and verify_code='" + verifyCode.trim() + "'");
		List<DataMap> list = facade.getData(vo);
		if (list.size() > 0) {
			if( resetOption > 0)
				facade.delData(vo);
			return Constant.SUCCESS_CODE;
		}
		else
			return Constant.FAIL_CODE;
	}

	public int ProVerifyForgetLevel2(String emailaddress, String belongProject, String verifyCode,int resetOption) throws SystemException	{
		if ("".equals(verifyCode))
			return Constant.FAIL_CODE;			
		WappuserVerify vo = new WappuserVerify();
		WappuserVerifyFacade facade = ServiceBean.getInstance().getWappuserVerifyFacade();
		vo.setCondition("email = '" + emailaddress + "' and belong_project = " + belongProject + " and verify_code='" + verifyCode + "'");
		List<DataMap> list = facade.getDataLevel2(vo);
		if (list.size() > 0) {
			if( resetOption > 0)
				facade.delDataLevel2(vo);
			return Constant.SUCCESS_CODE;
		}
		else
			return Constant.FAIL_CODE;
	}
	
	public void CreateMessageMqStop(String server, String rand, String emailaddress)
			throws MessagingException, IOException {
		Tools tls = new Tools();
		
		String[] fromEmail = server.split(",");
		String body = "Your verify code is " + rand /* + "!" */; 
        String subject = body;
		logger.info(fromEmail[0] + fromEmail[1]);
		
		// ****************************创建会话***************************************
		final Properties props = new Properties();
		//props.put("mail.smtp.host", "mail.waterworld.com.cn");// 发件人使用发邮件的电子信箱服务器
		//props.put("mail.smtp.auth", "true");
//		
//		props.put("mail.smtp.host", "smtp.126.com");// 发件人使用发邮件的电子信箱服务器
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.user", "twosides999@126.com");
//		props.put("mail.password", "3061226ABCabc");		

		String emlDestUp = emailaddress.toUpperCase();
		if (emlDestUp.contains("@QQ.COM")  ) {		
			props.put("mail.smtp.host", "smtp.paby.com");// 发件人使用发邮件的电子信箱服务器
			props.put("mail.smtp.auth", "true");
			props.put("mail.user", "service@paby.com");
			props.put("mail.password", "Pb654321Waterworld");
			//fromEmail[0] = "service@paby.com";
		} else {
			props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");// 发件人使用发邮件的电子信箱服务器
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");
			props.put("mail.user", "AKIAJPEOFRGFATV42SDQ");
			props.put("mail.password", "AuhmFfe1lMRSVxPpo44ZfQjjPGqyiZsymzJOtfebUEKc");
			//fromEmail[0] = "service@paby.com";
		}

		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader()
				.getResourceAsStream("server.properties"));		
		String fel = "service@paby.com";
		if (dfg > 0)
			fel = pros.getProperty("dreamemail");
		
		//fromEmail[0] = "service@paby.com";
		fromEmail[0] = fel;
				
		/*
		 * http://blog.csdn.net/wangxinqn/article/details/1708705
		 * 
		近日使用javamail 为公司的软件添加了邮件收发功能。遇到了Unsupported record version Unknown-50.49异常。

		该异常只会在发送邮件的时候产生，而且是应为所有邮箱使用了SSL加密功能。应该是javamail包的问题

		初步的解决方案是在你的发送类里 加上props.put("mail.smtp.quitwait", "false");将该异常屏蔽调		
		*/
		props.put("mail.smtp.quitwait", "false");
		
		Authenticator atctr = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String userName = props.getProperty("mail.user");
				String passWord = props.getProperty("mail.password");
				return new PasswordAuthentication(userName, passWord);
				
			}
		};
		
		Session mailsession = Session.getInstance(props, atctr); // 获得默认的session对象
		mailsession.setDebug(true);

		// *****************************构造消�?**************************************
		MimeMessage msg = new MimeMessage(mailsession);

		InternetAddress from = new InternetAddress(fromEmail[0]);
		msg.setFrom(from);
		InternetAddress to = new InternetAddress(emailaddress); // 设置收件人地址并规定其类型
		msg.setRecipient(Message.RecipientType.TO, to);
		//msg.setSentDate(new Date()); // 设置发信时间
		if ( Constant.timeUtcFlag )		
			msg.setSentDate(tls.getUtcDateStrNowDate() );
		else
			msg.setSentDate(new Date()); // 设置发信时间
		
		msg.setSubject(subject); // 设置主题
		msg.setText(body);
		msg.setContent(body, "text/html;charset=UTF-8"); // 设置 正文

		//Transport transport = mailsession.getTransport("smtp");
		//transport.connect("mail.waterworld.com.cn", fromEmail[0].split("@")[0],
		//		fromEmail[1]);// 发邮件人帐户密码,此外是我的帐户密码，使用时请修改�?
		//transport.connect("smtp.126.com", fromEmail[0].split("@")[0],
		//		fromEmail[1]);// 发邮件人帐户密码,此外是我的帐户密码，使用时请修改�?

		//		transport.sendMessage(msg, msg.getAllRecipients());
		//transport.close();
		// *******************************发送消�?*******************************
		// msg.writeTo(System.out);//保存消息 并在控制�?显示消息对象中属性信�?
		// System.out.println("邮件已成功发送到 " + username);
		Transport.send(msg);
		
		//tmrResetCheckInfo();
	}
	
	public static void main(String[] args) throws Exception {
		/*WTFindPasswordAction wfpa = new WTFindPasswordAction();
		wfpa.CreateMessage(wfpa.getServerName(), "testtest", "Atic.au@QQ.com");*/
	}
	
}

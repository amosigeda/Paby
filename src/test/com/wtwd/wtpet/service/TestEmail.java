package test.com.wtwd.wtpet.service;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.wtwd.common.lang.Tools;

public class TestEmail {
	public static void main(String[] args) throws MessagingException, IOException {
		new TestEmail().createMessageIn();
	}
	public void createMessageIn() throws MessagingException, IOException {
		Tools tls = new Tools();	
		final Properties props = new Properties();

		String dest_email = "wenti1484H6@163.com";
		String content = "this is content";
		String laddonName = null;

		props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");// 发件人使用发邮件的电子信箱服务器
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "25");
		props.put("mail.user", "AKIAJPEOFRGFATV42SDQ");
		props.put("mail.password", "AuhmFfe1lMRSVxPpo44ZfQjjPGqyiZsymzJOtfebUEKc");

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

		MimeMessage msg = new MimeMessage(mailsession);		
		
		msg.setFrom(new InternetAddress(dest_email));   // 发送者email帐号
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(dest_email)); // 设置收件人地址并规定其类型
		msg.setSentDate(tls.getUtcDateStrNowDate());		
		msg.setSubject("***test***"); // 设置主题
		msg.setText(content);
		msg.setContent(content, "text/html;charset=UTF-8"); // 设置 正文

		if ( laddonName != null ) {
			/*// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件  
            Multipart multipart = new MimeMultipart();  
            // 设置邮件的文本内容  
            BodyPart contentPart = new MimeBodyPart();   
            contentPart.setText(content);
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
            msg.setContent(multipart);*/ 
			
			// 创建邮件的正文  
            MimeBodyPart text = new MimeBodyPart();   
            text.setContent(content, "text/html;charset=UTF-8");
			String lString= "F:/pabySrv/WebRoot/images/app/msg/msg1538091213000.png";
			 // 创建图片  
            MimeBodyPart img = new MimeBodyPart();   
            DataHandler dh = new DataHandler(new FileDataSource(lString));//图片路径  
            img.setDataHandler(dh);  
            // 创建图片的一个表示用于显示在邮件中显示  
            img.setContentID("a");  

            MimeMultipart mm = new MimeMultipart();  
            mm.addBodyPart(text);  
            mm.addBodyPart(img);  
            mm.setSubType("related");// 设置正文与图片之间的关系  
            // 图班与正文的 body  
            MimeBodyPart all = new MimeBodyPart();  
            all.setContent(mm);  
            msg.setContent(mm);
		}
		// 保存邮件  
        msg.saveChanges(); 
        // 发送邮件
		Transport.send(msg);
		System.out.println("email has sended to " + dest_email);
	}

}

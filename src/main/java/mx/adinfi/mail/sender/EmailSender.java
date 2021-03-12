package mx.adinfi.mail.sender;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailSender {
	
	private static Log log = LogFactory.getLog(EmailSender.class);
	private JavaMailSender mailSender;
	private SimpleMailMessage simpleMailMessage;
	
	public EmailSender() {

	}
	
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}
	 
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
//	public void sendMail(String to, String content, String subject) throws MailException, Exception {
////		subject = "Subject";
//		log.info("Init send async mail");
//		String[] toList =  to.split(",");
//		final SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
//		
//		message.setTo(toList);
//		message.setSubject(subject);
////		message.setSubject(simpleMailMessage.getSubject());
//		message.setText(content);
//
//		MimeMessagePreparator preparator = new MimeMessagePreparator() {
//	        
//            public void prepare(MimeMessage mimeMessage) throws Exception {
//        
//                mimeMessage.setRecipient(Message.RecipientType.TO, 
//                        new InternetAddress(message.getTo()[0]));
//                mimeMessage.setFrom(new InternetAddress(message.getFrom()));
//                mimeMessage.setSubject(message.getSubject());
//                mimeMessage.setContent(message.getText(), "text/html");
//            }
//        };
//		mailSender.send(preparator);
//		log.info("Finish send mail");
//	}

	public void sendMail(String to, String content, String subject) throws MailException, Exception {
		log.info("Init send async mail");
		
		final SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
 
		message.setTo(to);
		message.setSubject(simpleMailMessage.getSubject());
		message.setText(content);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
	        
            public void prepare(MimeMessage mimeMessage) throws Exception {
        
                mimeMessage.setRecipient(Message.RecipientType.TO, 
                        new InternetAddress(message.getTo()[0]));
                mimeMessage.setFrom(new InternetAddress(message.getFrom()));
                mimeMessage.setSubject(subject != null ? subject.replace(".&#8203;", ".") : message.getSubject(), "UTF-8");
                mimeMessage.setContent(content.replace("&amp;#8203;", "&#8203;"), "text/html; charset=utf-8");
            }
        };
		mailSender.send(preparator);
		
		log.info("Finish send mail");
	}
}
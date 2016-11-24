package org.hurryapp.quickstart.service.mail;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hurryapp.quickstart.Constants;
import org.hurryapp.quickstart.service.config.ConfigParameterService;


public class MailService {
	static private String host;// = "smtp.gmail.com";
	static private String port;// = "587";
	static private String username;// = "user@gmail.com";
	static private String password;// = "pwd";
	static private String auth;// = "true";
	static private String starttls;// = "true";
	
	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;
	
	/**
	 * Initialisation des paramètres
	 */
	private void init() {
		if (host == null) {
			host = configParameterService.findParameter(Constants.CONFIG_DOMAIN_MAIL, Constants.CONFIG_PARAMETER_MAIL_HOST).toString();
			port = configParameterService.findParameter(Constants.CONFIG_DOMAIN_MAIL, Constants.CONFIG_PARAMETER_MAIL_PORT).toString();
			username = configParameterService.findParameter(Constants.CONFIG_DOMAIN_MAIL, Constants.CONFIG_PARAMETER_MAIL_USER).toString();
			password = configParameterService.findParameter(Constants.CONFIG_DOMAIN_MAIL, Constants.CONFIG_PARAMETER_MAIL_PASSWORD).toString();
			auth = configParameterService.findParameter(Constants.CONFIG_DOMAIN_MAIL, Constants.CONFIG_PARAMETER_MAIL_AUTH).toString();
			starttls = configParameterService.findParameter(Constants.CONFIG_DOMAIN_MAIL, Constants.CONFIG_PARAMETER_MAIL_STARTTLS).toString();
		}		
	}

	/**
	 * Envoi d'un mail
	 * @param to
	 * @param from
	 * @param subject
	 * @param content
	 * @throws Exception
	 */
	public void sendMail(String to, String from, String subject, String content) throws Exception {
		init();
		Properties props = new Properties();
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttls);

		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		message.setSubject(subject);
		message.setContent(content, "text/html; charset=ISO-8859-1");
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		Transport transport = session.getTransport("smtp");
		transport.connect(host, Integer.valueOf(port), username, password);
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}
	
//	public static void main(String[] args) {
//		try {
//			sendMail("lipfi88@gmail.com", "philippe.bujeau@gmail.com", "Test", "Hey hey hey");
//		}
//		catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

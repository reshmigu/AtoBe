package com.test;

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

public class mail {

	public void mailm() {
		String host = "smtp.office365.com";
		String to = "reshmi.g@thinkpalm.com";
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");

		{

			Session session = Session.getDefaultInstance(props,

					new javax.mail.Authenticator() {

						protected PasswordAuthentication getPasswordAuthentication() {

							return new PasswordAuthentication("reshmi.g@thinkpalm.com", "Niranjana#26");

						}

					});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("reshmi.g@thinkpalm.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("reshmi.g@thinkpalm.com"));
				message.setSubject("AtoBe Sample Report");
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setText(
						"Please find attached Execution Sample Report.\nThanks\nReshmi");
				MimeBodyPart messageBodyPart2 = new MimeBodyPart();

				String filename = "C:\\Users\\reshmi.g\\Desktop\\restassured1\\restassured\\RestAssuredServicesTestProject\\test-output\\emailable-report.html";
				DataSource source = new FileDataSource(filename);
				messageBodyPart2.setDataHandler(new DataHandler(source));
				messageBodyPart2.setFileName(filename);
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				multipart.addBodyPart(messageBodyPart2);
				message.setContent(multipart);
				Transport.send(message);
				System.out.println("Mail Sent Successfully");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		}
	}
}
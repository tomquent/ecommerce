package fr.adaming.util;

import java.io.Serializable;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import fr.adaming.model.Commande;

@ManagedBean (name="mailMB")
@SessionScoped
public class MailSender implements Serializable {

	private static final long serialVersionUID = 1L;

	public void sendMail(String toMail, String subject, String msg, Commande commande) {

		final String username = "michalbebert@gmail.com";
		final String password = "playre258!";		//gerantproxybanque00

		Properties props = new Properties();

		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
//			Configuration
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
			MimeBodyPart textPart = new MimeBodyPart();
			
			Multipart multipart = new MimeMultipart();
			
			
			DataSource source= new FileDataSource("C:\\Users\\formi\\Desktop\\PDF\\Commande N°"+commande.getIdCom()+".pdf");
			textPart.setDataHandler(new DataHandler(source));
			textPart.setFileName("Récapitulatif de la commande.pdf");
			message.setSubject(subject);	
			multipart.addBodyPart(textPart);
			
			textPart = new MimeBodyPart();
			String final_Text = msg;
			textPart.setText(final_Text);
			multipart.addBodyPart(textPart);
			
			
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

package com.odix.fr.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class JavaMailSenderService {
	
	@Autowired
    private JavaMailSender javaMailSender;

	public void sendSimpleMessage(String to, String subject, String text) {
    	
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        javaMailSender.send(message);
    }
	
	public void sendSimpleHtmlMessage(String to, String subject, String text) throws MessagingException, IOException {
    	
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setTo(to); 
		helper.setSubject(subject); 
		// ,true = text/html
		helper.setText(text, true);
		
        javaMailSender.send(message);
    }

	public void sendEmailWithAttachment(String to, String subject) throws MessagingException, IOException {

        MimeMessage message = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
        helper.setTo(to);
        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>Check attachment for image!</h1>", true);

		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        // il faut bien trouver le fichier dans le path
        helper.addAttachment("anis.png", new ClassPathResource("anis.png"));

        javaMailSender.send(message);

    }
	
	/*@Scheduled(fixedRate = 3000)
	public void cron() {

		String newLine = System.getProperty("line.separator");
		System.out.println(newLine);
		System.out.print("Anis Zaheni : Odix - le sourcing rendu facile : ");
		
	}*/
	
	/**
	 * How to use it :
	 * STEP 1 :
	 * 
	 * @Autowired
	 * JavaMailSenderService mailService;
	 * 
	 * STEP 2 :
	 * 
	 * mailService.sendSimpleMessage("azaheni@smartgraphe.com", "Test Subject", "Test Contenu");
	 * 
	 * OR
	 * 
	   mailService.sendEmailWithAttachment("azaheni@smartgraphe.com", "Test Subject");
	 */

}

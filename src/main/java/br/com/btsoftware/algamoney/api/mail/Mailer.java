package br.com.btsoftware.algamoney.api.mail;

import java.util.HashMap;
// import java.util.Arrays;
// import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.model.User;

// import br.com.btsoftware.algamoney.api.model.Posting;
// import br.com.btsoftware.algamoney.api.repository.PostingRepository;

@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine thymeleaf;

	//
	// // Para enviar email
	// @Autowired
	// private PostingRepository repository;

	// @EventListener
	// private void teste(ApplicationReadyEvent event) {
	// String template = "mail/postings-warning-expired";

	// List<Posting> list = repository.findAll();

	// Map<String, Object> variables = new HashMap<>();

	// variables.put("postings", list);

	// this.sendMail("teste.sistema1010@gmail.com",
	// Arrays.asList("bebenjamimthiago@gmail.com"), "Testando", template,
	// variables);
	// System.out.println("Terminado o envio de e-mail...");
	// }

	public void notifiesExpiredPosting(List<Posting> postingsExpired, List<User> recipients) {
		Map<String, Object> variables = new HashMap<>();
		variables.put("postings", postingsExpired);

		List<String> emails = recipients.stream().map(u -> u.getEmail()).collect(Collectors.toList());

		this.sendMail("teste.sistema1010@gmail.com", emails, "Lançamentos vencidos", "mail/postings-warning-expired",
				variables);
	}

	public void sendMail(String sender, List<String> recipients, String subject, String template,
			Map<String, Object> varibles) {

		Context context = new Context(new Locale("pt", "BR"));

		varibles.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));

		String message = thymeleaf.process(template, context);

		this.sendMail(sender, recipients, subject, message);
	}

	public void sendMail(String sender, List<String> recipients, String subject, String message) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(sender);
			helper.setTo(recipients.toArray(new String[recipients.size()]));
			helper.setSubject(subject);
			helper.setText(message, true);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problemas com o envio de e-mail!", e);
		}
	}
}

package br.com.btsoftware.algamoney.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.btsoftware.algamoney.api.dto.PostingStatisticPersonDTO;
import br.com.btsoftware.algamoney.api.mail.Mailer;
import br.com.btsoftware.algamoney.api.model.Person;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.model.User;
import br.com.btsoftware.algamoney.api.repository.PersonRepository;
import br.com.btsoftware.algamoney.api.repository.PostingRepository;
import br.com.btsoftware.algamoney.api.repository.UserRepository;
import br.com.btsoftware.algamoney.api.service.exception.PersonInexistOrInactiveException;
import br.com.btsoftware.algamoney.api.storage.S3;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PostingService {

	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";

	private static final Logger logger = LoggerFactory.getLogger(PostingService.class);

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PostingRepository postingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Mailer mailer;

	@Autowired
	private S3 s3;

	@Scheduled(cron = "0 41 09 * * *")
	public void notifiesExpiredPosting() {

		if (logger.isDebugEnabled()) {
			logger.debug("Preparando envio de " + "e-mails de aviso de lançamentos vencidos.");
		}

		List<Posting> expired = postingRepository
				.findByExpirationDateLessThanEqualAndPaymentDateIsNull(LocalDate.now());

		if (expired.isEmpty()) {
			logger.info("Sem lançamentos vencidos para aviso.");

			return;
		}

		logger.info("Exitem {} lançamentos vencidos.", expired.size());

		List<User> recipients = userRepository.findByPermissionsDescription(DESTINATARIOS);

		if (recipients.isEmpty()) {
			logger.warn("Existem lançamentos vencidos, mas o " + "sistema não encontrou destinatários.");

			return;
		}

		mailer.notifiesExpiredPosting(expired, recipients);

		logger.info("Envio de e-mail de aviso concluído.");
	}

	public byte[] reportPerPerson(LocalDate firstDate, LocalDate lastDate) throws JRException {
		List<PostingStatisticPersonDTO> data = postingRepository.perPerson(firstDate, lastDate);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("FIRST_DATE", Date.valueOf(firstDate));
		parameters.put("LAST_DATE", Date.valueOf(lastDate));
		parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

		InputStream inputStream = this.getClass().getResourceAsStream("/reports/posting-per-person.jasper");

		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters,
				new JRBeanCollectionDataSource(data));

		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public Posting save(Posting posting) {
		personValidate(posting);

		if (StringUtils.hasText(posting.getAnexo())) {
			s3.save(posting.getAnexo());
		}

		return postingRepository.save(posting);
	}

	public Posting update(Long id, Posting posting) {
		Posting postingSave = findExistPosting(id);

		if (!posting.getPerson().equals(postingSave.getPerson())) {
			personValidate(posting);
		}

		if (StringUtils.isEmpty(posting.getAnexo()) && StringUtils.hasText(postingSave.getAnexo())) {
			s3.remove(postingSave.getAnexo());
		} else if (StringUtils.hasText(posting.getAnexo()) && !posting.getAnexo().equals(postingSave.getAnexo())) {
			s3.replace(postingSave.getAnexo(), posting.getAnexo());
		}

		BeanUtils.copyProperties(posting, postingSave, "id");

		return postingRepository.save(posting);
	}

	private void personValidate(Posting posting) {
		Person person = null;

		if (posting.getPerson().getId() != null) {
			person = personRepository.getOne(posting.getPerson().getId());
		}

		if (person == null || person.isInactive()) {
			throw new PersonInexistOrInactiveException();
		}
	}

	private Posting findExistPosting(Long id) {
		Optional<Posting> posting = postingRepository.findById(id);

		if (!posting.isPresent()) {
			throw new IllegalArgumentException();
		}
		return posting.get();
	}

}

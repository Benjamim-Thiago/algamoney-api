package br.com.btsoftware.algamoney.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algamoney.api.dto.PostingStatisticPersonDTO;
import br.com.btsoftware.algamoney.api.mail.Mailer;
import br.com.btsoftware.algamoney.api.model.Person;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.model.User;
import br.com.btsoftware.algamoney.api.repository.PersonRepository;
import br.com.btsoftware.algamoney.api.repository.PostingRepository;
import br.com.btsoftware.algamoney.api.repository.UserRepository;
import br.com.btsoftware.algamoney.api.service.exception.PersonInexistOrInactiveException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PostingService {
	
	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PostingRepository postingRepository;
	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private Mailer mailer;

	@Scheduled(cron = "0 51 12 * * *")
	public void notifiesExpiredPosting() {
		List<Posting> expired = postingRepository.findByExpirationDateLessThanEqualAndPaymentDateIsNull(LocalDate.now());
		
		List<User> recipients = userRepository.findByPermissionsDescription(DESTINATARIOS);
		
		mailer.notifiesExpiredPosting(expired, recipients);
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
		Person person = personRepository.findOne(posting.getPerson().getId());

		if (person == null || person.isInactive()) {
			throw new PersonInexistOrInactiveException();
		}

		return postingRepository.save(posting);
	}

	public Posting update(Long id, Posting posting) {
		Posting postingSave = findExistPosting(id);

		if (!posting.getPerson().equals(postingSave.getPerson())) {
			personValidate(posting);
		}
		
		BeanUtils.copyProperties(posting, postingSave, "id");
		
		return postingRepository.save(posting);
	}

	private void personValidate(Posting posting) {
		Person person = null;
		
		if(posting.getPerson().getId() != null) {
			person = personRepository.findOne(posting.getPerson().getId());
		}
		
		if(person == null || person.isInactive()) {
			throw new PersonInexistOrInactiveException();
		}
	}

	private Posting findExistPosting(Long id) {
		Posting posting = postingRepository.findOne(id);

		if (posting == null) {
			throw new IllegalArgumentException();
		}
		return posting;
	}

}

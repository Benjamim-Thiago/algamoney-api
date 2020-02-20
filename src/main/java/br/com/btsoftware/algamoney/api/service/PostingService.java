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
import org.springframework.stereotype.Service;

import br.com.btsoftware.algamoney.api.dto.PostingStatisticPersonDTO;
import br.com.btsoftware.algamoney.api.model.Person;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.PersonRepository;
import br.com.btsoftware.algamoney.api.repository.PostingRepository;
import br.com.btsoftware.algamoney.api.service.exception.PersonInexistOrInactiveException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PostingService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PostingRepository postingRepository;

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

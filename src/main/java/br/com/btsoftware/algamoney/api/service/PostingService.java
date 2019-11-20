package br.com.btsoftware.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algamoney.api.model.Person;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.PersonRepository;
import br.com.btsoftware.algamoney.api.repository.PostingRepository;
import br.com.btsoftware.algamoney.api.service.exception.PersonInexistOrInactiveException;

@Service
public class PostingService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PostingRepository postingRepository;

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

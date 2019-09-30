package br.com.btsoftware.algamoney.api.service;

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

}

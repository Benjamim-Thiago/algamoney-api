package br.com.btsoftware.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.btsoftware.algamoney.api.model.Person;
import br.com.btsoftware.algamoney.api.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Person update(Long id, Person person) {
		Person personFind = findPersonById(id);

		BeanUtils.copyProperties(person, personFind, "id");
		return personRepository.save(personFind);

	}

	public Person findPersonById(Long id) {
		Person personFind = personRepository.findOne(id);

		if (personFind == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return personFind;
	}

	public void updateStatusPropertie(Long id, Boolean status) {
		Person personSalved = findPersonById(id);
		personSalved.setStatus(status);
		
		personRepository.save(personSalved);
	}
}

package br.com.btsoftware.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algamoney.api.dto.PersonUpdateStatusDTO;
import br.com.btsoftware.algamoney.api.event.EventCreatedResource;
import br.com.btsoftware.algamoney.api.model.Person;
import br.com.btsoftware.algamoney.api.repository.PersonRepository;
import br.com.btsoftware.algamoney.api.service.PersonService;

@RestController
@RequestMapping("/people")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private PersonService personService;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public List<Person> list() {
		List<Person> people = personRepository.findAll();

		return people;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person personSave = personRepository.save(person);

		publisher.publishEvent(new EventCreatedResource(this, response, personSave.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(personSave);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Person> update(@PathVariable Long id, @Valid @RequestBody Person person) {
		Person personSaved = personService.update(id, person);
		return ResponseEntity.ok(personSaved);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Person> findById(@PathVariable Long id) {
		Person person = personRepository.findOne(id);
		return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		this.personRepository.delete(id);
	}
	
	@PutMapping("/{id}/status")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public void updateStatusPropertie(@PathVariable Long id, @RequestBody PersonUpdateStatusDTO personUpdateStatusDTO) {
		personService.updateStatusPropertie(id, personUpdateStatusDTO.getStatus());
	}

}

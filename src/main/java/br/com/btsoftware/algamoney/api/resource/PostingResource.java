package br.com.btsoftware.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algamoney.api.event.EventCreatedResource;
import br.com.btsoftware.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Error;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.PostingRepository;
import br.com.btsoftware.algamoney.api.repository.filter.PostingFilter;
import br.com.btsoftware.algamoney.api.service.PostingService;
import br.com.btsoftware.algamoney.api.service.exception.PersonInexistOrInactiveException;

@RestController
@RequestMapping("/postings")
public class PostingResource {
	@Autowired
	private PostingRepository postingRepository;

	@Autowired
	private PostingService postingService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Posting> list(PostingFilter postongFilter, Pageable pageable) {
		Page<Posting> postings = postingRepository.filter(postongFilter, pageable);

		return postings;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Posting> findById(@PathVariable Long id) {
		Posting posting = postingRepository.findOne(id);

		return posting != null ? ResponseEntity.ok(posting) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Posting> create(@Valid @RequestBody Posting posting, HttpServletResponse response) {
		Posting postingSave = postingService.save(posting);

		publisher.publishEvent(new EventCreatedResource(this, response, postingSave.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(postingSave);
	}

	@ExceptionHandler({ PersonInexistOrInactiveException.class })
	public ResponseEntity<Object> handlePersonInexistOrInactiveException(PersonInexistOrInactiveException ex) {
		String userMessage = messageSource.getMessage("person.inexist-or-inactive", null,
				LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();

		List<Error> errors = Arrays.asList(new Error(userMessage, developerMessage));

		return ResponseEntity.badRequest().body(errors);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long id) {
		this.postingRepository.delete(id);
	}
}

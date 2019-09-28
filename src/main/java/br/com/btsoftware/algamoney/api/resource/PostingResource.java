package br.com.btsoftware.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algamoney.api.event.EventCreatedResource;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.PostingRepository;

@RestController
@RequestMapping("/postings")
public class PostingResource {
	@Autowired
	private PostingRepository postingRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Posting> list() {
		List<Posting> postings = postingRepository.findAll();

		return postings;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Posting> findById(@PathVariable Long id) {
		Posting posting = postingRepository.findOne(id);

		return posting != null ? ResponseEntity.ok(posting) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Posting> create(@Valid @RequestBody Posting posting, HttpServletResponse response) {
		Posting postingSave = postingRepository.save(posting);

		publisher.publishEvent(new EventCreatedResource(this, response, postingSave.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(postingSave);
	}
}

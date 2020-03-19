package br.com.btsoftware.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algamoney.api.model.State;
import br.com.btsoftware.algamoney.api.repository.StateRepository;

@RestController
@RequestMapping("/states")
public class StateResource {

	@Autowired
	private StateRepository stateRepository;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<State> list() {
		return stateRepository.findAllByOrderByNameAsc();
	}
}

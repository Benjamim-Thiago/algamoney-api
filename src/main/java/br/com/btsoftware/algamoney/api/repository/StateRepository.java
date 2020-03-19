package br.com.btsoftware.algamoney.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algamoney.api.model.State;

public interface StateRepository extends JpaRepository<State, Long> {
	List<State> findAllByOrderByNameAsc();
	
}

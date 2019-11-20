package br.com.btsoftware.algamoney.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algamoney.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	public Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);

}

package br.com.btsoftware.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algamoney.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}

package br.com.btsoftware.algamoney.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algamoney.api.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
	List<City> findByStateIdOrderByNameAsc(Long stateId);
}

package br.com.btsoftware.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algamoney.api.model.City;
import br.com.btsoftware.algamoney.api.repository.CityRepository;

@RestController
@RequestMapping("/cities")
public class CityResource {

	@Autowired
	private CityRepository cityRepository;
	
	@GetMapping
	public List<City> find(@RequestParam Long state) {
		return cityRepository.findByStateIdOrderByNameAsc(state);
	}
}

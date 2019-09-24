package br.com.btsoftware.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btsoftware.algamoney.api.model.Category;
import br.com.btsoftware.algamoney.api.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public List<Category> list() {
		List<Category> categories = categoryRepository.findAll();

		return categories;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Category category) {
		categoryRepository.save(category);
	}

}

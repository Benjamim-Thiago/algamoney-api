package br.com.btsoftware.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algamoney.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}

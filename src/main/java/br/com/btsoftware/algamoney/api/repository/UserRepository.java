package br.com.btsoftware.algamoney.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algamoney.api.model.User;

public interface UserRepository  extends JpaRepository<User, Long>{
	public Optional<User>findByEmail(String email);
}

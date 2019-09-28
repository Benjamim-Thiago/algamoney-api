package br.com.btsoftware.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algamoney.api.model.Posting;

public interface PostingRepository  extends JpaRepository<Posting, Long>{

}

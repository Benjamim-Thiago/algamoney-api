package br.com.btsoftware.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.posting.PostingRepositoryQuery;

public interface PostingRepository  extends JpaRepository<Posting, Long> , PostingRepositoryQuery{

}

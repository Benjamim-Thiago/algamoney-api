package br.com.btsoftware.algamoney.api.repository.posting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.filter.PostingFilter;
import br.com.btsoftware.algamoney.api.repository.projection.PostingSummary;

public interface PostingRepositoryQuery {
	public Page<Posting> filter(PostingFilter postingFilter, Pageable pageable);
	
	public Page<PostingSummary> summarize(PostingFilter postingFilter, Pageable pageable);
}

package br.com.btsoftware.algamoney.api.repository.posting;

import java.util.List;

import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.filter.PostingFilter;

public interface PostingRepositoryQuery {
	public List<Posting> filter(PostingFilter postingFilter);
}

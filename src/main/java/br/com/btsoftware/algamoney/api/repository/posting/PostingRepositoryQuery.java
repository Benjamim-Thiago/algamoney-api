package br.com.btsoftware.algamoney.api.repository.posting;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.btsoftware.algamoney.api.dto.PostingStatisticCategoryDTO;
import br.com.btsoftware.algamoney.api.dto.PostingStatisticDayDTO;
import br.com.btsoftware.algamoney.api.dto.PostingStatisticPersonDTO;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.filter.PostingFilter;
import br.com.btsoftware.algamoney.api.repository.projection.PostingSummary;

public interface PostingRepositoryQuery {

	public List<PostingStatisticPersonDTO> perPerson(LocalDate firstDate, LocalDate lastDate);

	public List<PostingStatisticCategoryDTO> perCategory(LocalDate monthReference);

	public List<PostingStatisticDayDTO> perDay(LocalDate monthReference);

	public Page<Posting> filter(PostingFilter postingFilter, Pageable pageable);

	public Page<PostingSummary> summarize(PostingFilter postingFilter, Pageable pageable);
}

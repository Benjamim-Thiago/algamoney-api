package br.com.btsoftware.algamoney.api.repository.posting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.btsoftware.algamoney.api.dto.PostingStatisticCategoryDTO;
import br.com.btsoftware.algamoney.api.dto.PostingStatisticDayDTO;
import br.com.btsoftware.algamoney.api.model.Category_;
import br.com.btsoftware.algamoney.api.model.Person_;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.model.Posting_;
import br.com.btsoftware.algamoney.api.repository.filter.PostingFilter;
import br.com.btsoftware.algamoney.api.repository.projection.PostingSummary;

public class PostingRepositoryImpl implements PostingRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<PostingStatisticDayDTO> perDay(LocalDate monthReference) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<PostingStatisticDayDTO> criteriaQuery = criteriaBuilder.
				createQuery(PostingStatisticDayDTO.class);
		
		Root<Posting> root =  criteriaQuery.from(Posting.class);
		
		criteriaQuery.select(criteriaBuilder.construct(PostingStatisticDayDTO.class, 
				root.get(Posting_.type),
				root.get(Posting_.expirationDate),
				criteriaBuilder.sum(root.get(Posting_.value))));
				
		LocalDate firstDay = monthReference.withDayOfMonth(1);
		LocalDate lastDay =  monthReference.withDayOfMonth(monthReference.lengthOfMonth());
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Posting_.expirationDate), 
						firstDay),
				criteriaBuilder.lessThanOrEqualTo(root.get(Posting_.expirationDate), 
						lastDay));
		
		criteriaQuery.groupBy(root.get(Posting_.type), root.get(Posting_.expirationDate));
		
		TypedQuery<PostingStatisticDayDTO> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<PostingStatisticCategoryDTO> perCategory(LocalDate monthReference) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<PostingStatisticCategoryDTO> criteriaQuery = criteriaBuilder.
				createQuery(PostingStatisticCategoryDTO.class);
		
		Root<Posting> root =  criteriaQuery.from(Posting.class);
		
		criteriaQuery.select(criteriaBuilder.construct(PostingStatisticCategoryDTO.class, 
				root.get(Posting_.category),
				criteriaBuilder.sum(root.get(Posting_.value))));
				
		LocalDate firstDay = monthReference.withDayOfMonth(1);
		LocalDate lastDay =  monthReference.withDayOfMonth(monthReference.lengthOfMonth());
		
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Posting_.expirationDate), 
						firstDay),
				criteriaBuilder.lessThanOrEqualTo(root.get(Posting_.expirationDate), 
						lastDay));
		
		criteriaQuery.groupBy(root.get(Posting_.category));
		
		TypedQuery<PostingStatisticCategoryDTO> typedQuery = manager
				.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public Page<Posting> filter(PostingFilter postingFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Posting> criteria = builder.createQuery(Posting.class);
		Root<Posting> root = criteria.from(Posting.class);
		

		// criar as restrições
		Predicate[] predicates = createConstraints(postingFilter, builder, root);
		criteria.where(predicates);
		//Ordena os itens
		criteria.orderBy(builder.desc(root.get(Posting_.id)));

		TypedQuery<Posting> query = manager.createQuery(criteria);
		addPaginateConstraint(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(postingFilter));
	}

	@Override
	public Page<PostingSummary> summarize(PostingFilter postingFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PostingSummary> criteria = builder.createQuery(PostingSummary.class);
		Root<Posting> root = criteria.from(Posting.class);
		
		criteria.select(builder.construct(PostingSummary.class
				, root.get(Posting_.id)
				, root.get(Posting_.description)
				, root.get(Posting_.expirationDate)
				, root.get(Posting_.paymentDate)
				, root.get(Posting_.value)
				, root.get(Posting_.type)
				, root.get(Posting_.category).get(Category_.name)
				, root.get(Posting_.person).get(Person_.name)));


		Predicate[] predicates = createConstraints(postingFilter, builder, root);
		criteria.where(predicates);
		
		//Ordena os itens
		criteria.orderBy(builder.desc(root.get(Posting_.id)));
		
		TypedQuery<PostingSummary> query = manager.createQuery(criteria);
		addPaginateConstraint(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(postingFilter));
	}

	private Predicate[] createConstraints(PostingFilter postingFilter, CriteriaBuilder builder, Root<Posting> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(postingFilter.getDescription())) {
			predicates.add(builder.like(builder.lower(root.get(Posting_.description)),
					"%" + postingFilter.getDescription().toLowerCase() + "%"));
		}
		if (postingFilter.getFirstDate() != null) {
			predicates
					.add(builder.greaterThanOrEqualTo(root.get(Posting_.expirationDate), postingFilter.getFirstDate()));
		}
		if (postingFilter.getLastDate() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Posting_.expirationDate), postingFilter.getLastDate()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addPaginateConstraint(TypedQuery<?> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRecordsPerPage = pageable.getPageSize();
		int firstRecordOfPage = currentPage * totalRecordsPerPage;

		query.setFirstResult(firstRecordOfPage);
		query.setMaxResults(totalRecordsPerPage);
	}

	private Long total(PostingFilter postingFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Posting> root = criteria.from(Posting.class);
				
		Predicate[] predicates = createConstraints(postingFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();

	}
}

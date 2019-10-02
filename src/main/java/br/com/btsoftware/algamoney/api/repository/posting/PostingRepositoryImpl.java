package br.com.btsoftware.algamoney.api.repository.posting;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;

import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.repository.filter.PostingFilter;

public class PostingRepositoryImpl implements PostingRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Posting> filter(PostingFilter postingFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Posting> criteria = builder.createQuery(Posting.class);
		Root<Posting> root = criteria.from(Posting.class);

		// criar as restrições
		Predicate[] predicates = createConstraints(postingFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Posting> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] createConstraints(PostingFilter postingFilter, CriteriaBuilder builder, Root<Posting> root) {
		List<Predicate> predicates = new ArrayList<>();

		/*
		 * OBS Certificar se em Propriedades do projeto está criado Java Compiler > Annotation Processing
		 * Em Generate source directory: src/main/java
		 * Depois em Java Compiler > Annotation Processing factory path => adicionar o jar do jpamodelgen
		 * => No pom.xml
		 * <dependency>
		 *	<groupId>org.hibernate</groupId>
		 *	<artifactId>hibernate-jpamodelgen</artifactId>
		 *	</dependency>
		 */
		
		if (!StringUtils.isEmpty(postingFilter.getDescription())) {
			predicates.add(builder.like(
					builder.lower(root.get("description")), "%" + postingFilter.getDescription().toLowerCase() + "%"));
		}
		if (postingFilter.getFirstDate() != null) {

		}
		if (postingFilter.getLastDate() != null) {

		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}

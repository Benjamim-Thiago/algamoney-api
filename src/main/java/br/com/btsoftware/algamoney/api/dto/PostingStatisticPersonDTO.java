package br.com.btsoftware.algamoney.api.dto;

import java.math.BigDecimal;

import br.com.btsoftware.algamoney.api.model.Person;
import br.com.btsoftware.algamoney.api.model.TypePosting;

public class PostingStatisticPersonDTO {
	private TypePosting type;
	private Person person;
	private BigDecimal total;

	public PostingStatisticPersonDTO(TypePosting type, Person person, BigDecimal total) {
		this.type = type;
		this.person = person;
		this.total = total;
	}

	public TypePosting getType() {
		return type;
	}

	public void setType(TypePosting type) {
		this.type = type;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}

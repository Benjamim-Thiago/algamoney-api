package br.com.btsoftware.algamoney.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.btsoftware.algamoney.api.model.TypePosting;

public class PostingStatisticDayDTO {
	private TypePosting type;
	private LocalDate day;
	private BigDecimal total;

	public PostingStatisticDayDTO() {
	}

	public PostingStatisticDayDTO(TypePosting type, LocalDate day, BigDecimal total) {
		this.type = type;
		this.day = day;
		this.total = total;
	}

	public TypePosting getType() {
		return type;
	}

	public void setType(TypePosting type) {
		this.type = type;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}

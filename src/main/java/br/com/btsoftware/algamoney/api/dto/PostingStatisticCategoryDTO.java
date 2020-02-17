package br.com.btsoftware.algamoney.api.dto;

import java.math.BigDecimal;

import br.com.btsoftware.algamoney.api.model.Category;

public class PostingStatisticCategoryDTO {
	private Category category;
	private BigDecimal total;

	
	public PostingStatisticCategoryDTO(Category category, BigDecimal total) {
		this.category = category;
		this.total = total;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}

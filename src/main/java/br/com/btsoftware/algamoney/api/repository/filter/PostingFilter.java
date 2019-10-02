package br.com.btsoftware.algamoney.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class PostingFilter {
	private String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate firstDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate lastDate;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(LocalDate firstDate) {
		this.firstDate = firstDate;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}

}

package br.com.btsoftware.algamoney.api.model;

public enum TypePosting {
	RECEITA("Receita"), DESPESA("Despesa");

	private final String description;

	private TypePosting(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}

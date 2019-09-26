package br.com.btsoftware.algamoney.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class EventCreatedResource extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
	private Long id;

	public EventCreatedResource(Object source, HttpServletResponse response, Long id) {
		super(source);
		this.response = response;
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
}

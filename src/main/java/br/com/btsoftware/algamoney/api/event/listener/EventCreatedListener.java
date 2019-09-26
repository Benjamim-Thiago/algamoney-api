package br.com.btsoftware.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.btsoftware.algamoney.api.event.EventCreatedResource;

@Component
public class EventCreatedListener implements ApplicationListener<EventCreatedResource> {

	@Override
	public void onApplicationEvent(EventCreatedResource eventCreatedResource) {
		HttpServletResponse response = eventCreatedResource.getResponse();
		Long id = eventCreatedResource.getId();

		addHeaderLocation(response, id);
	}

	private void addHeaderLocation(HttpServletResponse response, Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}

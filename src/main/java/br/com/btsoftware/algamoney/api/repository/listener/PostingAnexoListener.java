package br.com.btsoftware.algamoney.api.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.util.StringUtils;

import br.com.btsoftware.algamoney.api.AlgamoneyApiApplication;
import br.com.btsoftware.algamoney.api.model.Posting;
import br.com.btsoftware.algamoney.api.storage.S3;

public class PostingAnexoListener {

	@PostLoad
	public void postLoad(Posting posting) {
		if (StringUtils.hasText(posting.getAnexo())) {
			S3 s3 = AlgamoneyApiApplication.getBean(S3.class);
			posting.setUrlAnexo(s3.generateUrl(posting.getAnexo()));
		}
	}
}

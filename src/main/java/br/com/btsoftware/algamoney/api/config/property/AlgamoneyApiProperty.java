package br.com.btsoftware.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

	private String AllowOrigin = "http://localhost:8000";

	public String getAllowOrigin() {
		return AllowOrigin;
	}

	public void setAllowOrigin(String allowOrigin) {
		AllowOrigin = allowOrigin;
	}

	private final Security security = new Security();

	public Security getSecurity() {
		return security;
	}

	public static class Security {
		private boolean enabledHttps;

		public boolean isEnabledHttps() {
			return enabledHttps;
		}

		public void setEnabledHttps(boolean enabledHttps) {
			this.enabledHttps = enabledHttps;
		}

	}
}

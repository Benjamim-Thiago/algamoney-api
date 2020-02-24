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

	private final Mail mail = new Mail();

	public Mail getMail() {
		return mail;
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

	public static class Mail {

		private String host;
		private Integer port;
		private String username;
		private String password;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}

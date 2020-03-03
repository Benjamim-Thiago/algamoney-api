package br.com.btsoftware.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

	private String AllowOrigin = "http://localhost:8000";
	private final Security security = new Security();
	private final Mail mail = new Mail();
	private final S3 s3 =  new S3();

	public String getAllowOrigin() {
		return AllowOrigin;
	}

	public void setAllowOrigin(String allowOrigin) {
		AllowOrigin = allowOrigin;
	}

	public Security getSecurity() {
		return security;
	}

	public Mail getMail() {
		return mail;
	}

	public S3 getS3() {
		return s3;
	}
	
	public static class S3 {
		private String accessKeyId;
		private String secretAccessKey;
	    private String bucket = "aw-algamoney-achivos";
	    
	    public String getBucket() {
			return bucket;
		}		
		public String getAccessKeyId() {
			return accessKeyId;
		}
		public void setAccessKeyId(String accessKeyId) {
			this.accessKeyId = accessKeyId;
		}
		public String getSecretAccessKey() {
			return secretAccessKey;
		}
		public void setSecretAccessKey(String secretAccessKey) {
			this.secretAccessKey = secretAccessKey;
		}
		
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

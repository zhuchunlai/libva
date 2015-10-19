package org.codefamily.libva.http.core;

/**
 * http配置
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-09
 */
public class HttpConfig {

    public static final HttpConfig DEFAULT = builder().build();

    private long connectTimeout;
    private long readTimeout;

    public static HttpConfig.Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private HttpConfig config;

        private Builder() {
            config = new HttpConfig();
            config.connectTimeout = 5000;
            config.readTimeout = 5000;
        }

        public HttpConfig build() {
            return config;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            config.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(long readTimeout) {
            config.readTimeout = readTimeout;
            return this;
        }

    }

}

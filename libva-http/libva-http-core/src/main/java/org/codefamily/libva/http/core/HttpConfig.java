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

    private int connectTimeout;
    private int socketTimeout;

    public static HttpConfig.Builder builder() {
        return new Builder();
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public static final class Builder {

        private HttpConfig config;

        private Builder() {
            config = new HttpConfig();
            config.connectTimeout = 5000;
            config.socketTimeout = 5000;
        }

        public HttpConfig build() {
            return config;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            config.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setSocketTimeout(int socketTimeout) {
            config.socketTimeout = socketTimeout;
            return this;
        }

    }

}

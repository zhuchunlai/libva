package org.codefamily.libva.http.core;

import java.util.HashMap;
import java.util.Map;

/**
 * http 请求抽象
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-09
 */
public class HttpRequest<R> {
    // 请求地址
    private String endpoint;
    // path路径
    private String path;
    // Content-Type
    private String contentType = ClientConstants.CONTENT_TYPE_JSON;
    // 请求方法
    private HttpMethod method = HttpMethod.GET;
    // 配置
    private HttpConfig config = HttpConfig.DEFAULT;
    // 请求头
    private Map<String, String> headers;
    // 请求参数，最终会转换成URL参数
    private Map<String, Object> parameters;
    // 请求体
    private Object entity;
    // 响应的实体类型
    private Class<R> returnType;

    private HttpRequest() {
        // nothing to do.
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpConfig getConfig() {
        return config;
    }

    public String getContentType() {
        return contentType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Object getEntity() {
        return entity;
    }

    public Class<R> getReturnType() {
        return returnType;
    }

    public static <R> RequestBuilder<R> builder(Class<R> returnType) {
        return new RequestBuilder<R>(returnType);
    }

    public static final class RequestBuilder<R> {

        private HttpRequest<R> request;

        private RequestBuilder(Class<R> returnType) {
            request = new HttpRequest<R>();
            request.returnType = returnType;
        }

        public RequestBuilder<R> endpoint(String endpoint) {
            request.endpoint = endpoint;
            return this;
        }

        public RequestBuilder<R> path(String path) {
            request.path = path;
            return this;
        }

        public RequestBuilder<R> get() {
            return method(HttpMethod.GET);
        }

        public RequestBuilder<R> post() {
            return method(HttpMethod.POST);
        }

        public RequestBuilder<R> method(HttpMethod method) {
            request.method = method;
            return this;
        }

        public RequestBuilder<R> config(HttpConfig config) {
            request.config = config;
            return this;
        }

        public RequestBuilder<R> contentType(String contentType) {
            request.contentType = contentType;
            return this;
        }

        public RequestBuilder<R> addHeader(String name, String value) {
            if (request.headers == null) {
                request.headers = new HashMap<String, String>();
            }
            request.headers.put(name, value);
            return this;
        }

        public RequestBuilder<R> addParameter(String name, String value) {
            if (request.parameters == null) {
                request.parameters = new HashMap<String, Object>();
            }
            request.parameters.put(name, value);
            return this;
        }

        public RequestBuilder<R> entity(Object entity) {
            request.entity = entity;
            return this;
        }

        public HttpRequest<R> build() {
            return request;
        }

    }

}

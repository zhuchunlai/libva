package org.codefamily.libva.http.core;

import org.codefamily.libva.http.core.exception.ClientException;
import org.codefamily.libva.util.Strings;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 请求抽象
 *
 * @param <R> HTTP响应所对应的实体类型
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-09
 */
public class HttpRequest<R> {
    // 请求地址
    private String endpoint;
    // path路径
    private String path = Constants.EMPTY_STRING;
    // Content-Type
    private ContentType contentType = ContentType.APPLICATION_JSON;
    // 请求方法
    private HttpMethod method = HttpMethod.GET;
    // 配置
    private HttpConfig config = HttpConfig.DEFAULT;
    // 请求头
    private Map<String, String> headers;
    // 请求参数，最终会转换成URL参数
    private Map<String, String> parameters;
    // 请求体
    private Object entity;
    // 响应的实体类型
    private Class<R> returnType;
    // 响应解析器名称
    private String entityHandlerName;
    // 响应编码
    private Charset responseCharset;

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

    public ContentType getContentType() {
        return contentType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Charset getResponseCharset() {
        return responseCharset;
    }

    public Object getEntity() {
        return entity;
    }

    public Class<R> getReturnType() {
        return returnType;
    }

    public String getEntityHandlerName() {
        return entityHandlerName;
    }

    public boolean hasParameters() {
        return parameters != null && !parameters.isEmpty();
    }

    public boolean hasHeaders() {
        return headers != null && !headers.isEmpty();
    }

    public boolean hasEntity() {
        return entity != null;
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

        public RequestBuilder<R> method(HttpMethod method) {
            request.method = method;
            return this;
        }

        public RequestBuilder<R> config(HttpConfig config) {
            request.config = config;
            return this;
        }

        public RequestBuilder<R> contentType(ContentType contentType) {
            request.contentType = contentType;
            return this;
        }

        public RequestBuilder<R> addHeader(String name, String value) {
            if (request.headers == null) {
                request.headers = new HashMap<String, String>();
            }
            if (!Strings.isNullOrEmptyAfterTrim(name) && !Strings.isNull(value)) {
                request.headers.put(name, value);
            }
            return this;
        }

        public RequestBuilder<R> addParameter(String name, String value) {
            if (request.parameters == null) {
                request.parameters = new HashMap<String, String>();
            }
            if (!Strings.isNullOrEmptyAfterTrim(name) && !Strings.isNull(value)) {
                request.parameters.put(name, value);
            }
            return this;
        }

        public RequestBuilder<R> entity(Object entity) {
            request.entity = entity;
            return this;
        }

        public RequestBuilder<R> entityHandler(String entityHandlerName) {
            request.entityHandlerName = entityHandlerName;
            return this;
        }

        public RequestBuilder<R> responseCharset(Charset responseCharset) {
            request.responseCharset = responseCharset;
            return this;
        }

        public HttpRequest<R> build() {
            if (Strings.isNullOrEmptyAfterTrim(request.endpoint)) {
                throw new ClientException("no endpoint");
            }
            return request;
        }

    }

}

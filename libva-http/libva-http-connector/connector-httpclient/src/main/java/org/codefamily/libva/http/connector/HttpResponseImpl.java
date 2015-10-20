package org.codefamily.libva.http.connector;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.codefamily.libva.http.core.HttpEntityHandler;
import org.codefamily.libva.http.core.HttpResponse;
import org.codefamily.libva.http.core.exception.EntityHandlerNotFoundException;
import org.codefamily.libva.http.core.exception.LibvaHttpException;
import org.codefamily.libva.spi.PluginManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于Apache-HttpClient的响应实现
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
public final class HttpResponseImpl<R> implements HttpResponse<R> {

    private static final String JSON_ENTITY_HANDLER = "handler:json://";

    private CloseableHttpResponse response;

    HttpResponseImpl(CloseableHttpResponse response) {
        this.response = response;
    }

    @Override
    public int getStatus() {
        return response.getStatusLine().getStatusCode();
    }

    @Override
    public String getStatusMessage() {
        return response.getStatusLine().getReasonPhrase();
    }

    @Override
    public R readEntity(Class<R> returnType) {
        @SuppressWarnings("unchecked")
        HttpEntityHandler<R> defaultEntityHandler = PluginManager.getPlugin(JSON_ENTITY_HANDLER, HttpEntityHandler.class);
        if (defaultEntityHandler == null) {
            throw new EntityHandlerNotFoundException(JSON_ENTITY_HANDLER);
        }
        return readEntity(returnType, defaultEntityHandler);
    }

    @Override
    public R readEntity(Class<R> returnType, HttpEntityHandler<R> handler) {
        try {
            return readEntity(returnType, response.getEntity().getContent(), handler);
        } catch (IOException e) {
            throw new LibvaHttpException("read response content error.", e);
        }
    }

    @Override
    public String header(String name) {
        Header header = response.getFirstHeader(name);
        return header == null ? null : header.getValue();
    }

    @Override
    public Map<String, String> headers() {
        Header[] headers = response.getAllHeaders();
        if (headers == null || headers.length == 0) {
            return null;
        }
        Map<String, String> ret = new HashMap<String, String>(((int) (headers.length / 0.75F)) + 1);
        for (Header header : headers) {
            ret.put(header.getName(), header.getValue());
        }
        return ret;
    }

    protected R readEntity(Class<R> returnType, InputStream ins, HttpEntityHandler<R> handler) {
        return handler.handle(returnType, ins);
    }

}

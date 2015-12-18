package org.codefamily.libva.http.connector;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.codefamily.libva.http.core.*;
import org.codefamily.libva.http.core.exception.EntityHandlerNotFoundException;
import org.codefamily.libva.http.core.exception.LibvaHttpException;
import org.codefamily.libva.spi.PluginManager;
import org.codefamily.libva.util.Closeables;
import org.codefamily.libva.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(HttpResponseImpl.class);
    // 考虑实际使用场景，JSON格式的协议比较常见，因此，默认的解析器采用JSON解析器
    private static final String JSON_ENTITY_HANDLER = "JSON-Entity-Handler";

    private final HttpRequest<R> request;
    private final CloseableHttpResponse response;

    HttpResponseImpl(HttpRequest<R> request,
                     CloseableHttpResponse response) {
        this.request = request;
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
    public R readEntity() {
        String entityHandlerName = request.getEntityHandlerName();
        entityHandlerName = Strings.isNullOrEmpty(entityHandlerName) ? JSON_ENTITY_HANDLER : entityHandlerName;

        @SuppressWarnings("unchecked")
        HttpEntityHandler<R> entityHandler = PluginManager.getPlugin(entityHandlerName, HttpEntityHandler.class);
        if (entityHandler == null) {
            throw new EntityHandlerNotFoundException(entityHandlerName);
        }
        LOG.info(String.format("Load entity handler %s success.", entityHandler.getName()));

        return readEntity(entityHandler);
    }

    @Override
    public R readEntity(HttpEntityHandler<R> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Argument[handler] is null.");
        }
        try {
            InputStream ins = response.getEntity().getContent();
            try {
                return readEntity(ins, handler);
            } finally {
                Closeables.close(ins);
            }
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

    protected R readEntity(InputStream ins, HttpEntityHandler<R> handler) {
        return handler.handle(request.getReturnType(), ins, request.getResponseCharset());
    }

}

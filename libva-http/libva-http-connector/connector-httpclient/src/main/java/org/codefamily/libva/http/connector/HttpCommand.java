package org.codefamily.libva.http.connector;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codefamily.libva.http.core.*;
import org.codefamily.libva.http.core.ContentType;
import org.codefamily.libva.http.core.exception.ClientException;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 给定请求，并发起请求和接收服务端响应
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
class HttpCommand<R> {

    private final HttpRequest<R> request;
    private CloseableHttpClient client;
    private HttpUriRequest internalRequest;

    HttpCommand(HttpRequest<R> request) {
        this.request = request;
        this.init();
    }

    public HttpResponse<R> execute() throws Exception {
        CloseableHttpResponse response = client.execute(internalRequest);
        return new HttpResponseImpl<R>(request, response);
    }

    private void init() {
        this.client = createHttpClient();
        this.internalRequest = createHttpRequest();
    }

    private CloseableHttpClient createHttpClient() {
        HttpConfig config = request.getConfig();

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (config.getConnectTimeout() > 0) {
            requestConfigBuilder.setConnectTimeout(config.getConnectTimeout());
        }
        if (config.getSocketTimeout() > 0) {
            requestConfigBuilder.setSocketTimeout(config.getSocketTimeout());
        }

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setUserAgent(Constants.USER_AGENT);
        httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
        // TODO 暂时不考虑HTTPS
        return httpClientBuilder.build();
    }

    private HttpUriRequest createHttpRequest() {
        final String url = createURL();
        HttpUriRequest internalRequest;
        switch (request.getMethod()) {
            case GET:
                URI uri;
                try {
                    URIBuilder builder = new URIBuilder(url);
                    if (request.hasParameters()) {
                        for (Map.Entry<String, String> parameter : request.getParameters().entrySet()) {
                            builder.addParameter(parameter.getKey(), parameter.getValue());
                        }
                    }
                    uri = builder.build();
                } catch (URISyntaxException e) {
                    throw new ClientException("uri syntax error", e);
                }
                internalRequest = new HttpGet(uri);
                break;
            case POST:
                HttpPost post = new HttpPost(url);
                ContentType contentType = request.getContentType();
                HttpEntity entity;
                switch (contentType) {
                    case TEXT_XML:
                    case APPLICATION_JSON:
                        entity = EntityBuilder.create()
                                .setText(request.getEntity().toString())
                                .setContentEncoding(contentType.getCharset().name())
                                .setContentType(convertToInternalType(contentType))
                                .build();
                        break;
                    case APPLICATION_FORM_URLENCODED:
                        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                        if (request.hasParameters()) {
                            for (Map.Entry<String, String> parameter : request.getParameters().entrySet()) {
                                parameters.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
                            }
                        }
                        entity = EntityBuilder.create()
                                .setParameters(parameters)
                                .setContentEncoding(contentType.getCharset().name())
                                .setContentType(convertToInternalType(contentType))
                                .build();
                        break;
                    case MULTIPART_FORM_DATA:
                        if (!request.hasEntity() || !(request.getEntity().getClass().isAssignableFrom(InputStream.class))) {
                            throw new ClientException(contentType + " must has input stream entity");
                        }
                        entity = new InputStreamEntity((InputStream) request.getEntity(), convertToInternalType(contentType));
                        break;
                    default:
                        throw new ClientException("unsupported content type " + contentType);
                }
                post.setEntity(entity);
                internalRequest = post;
                break;
            default:
                throw new ClientException("unsupported http method" + request.getMethod());
        }

        if (request.hasHeaders()) {
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                internalRequest.addHeader(header.getKey(), header.getValue());
            }
        }
        return internalRequest;
    }


    private String createURL() {
        String url;
        if (request.getEndpoint().endsWith(Constants.URI_SEP)
                || request.getPath().startsWith(Constants.URI_SEP)) {
            url = request.getEndpoint() + request.getPath();
        } else {
            url = request.getEndpoint() + Constants.URI_SEP + request.getPath();
        }
        return url;
    }

    private static org.apache.http.entity.ContentType convertToInternalType(ContentType contentType) {
        return org.apache.http.entity.ContentType.create(contentType.getMimeType(), contentType.getCharset());
    }

}

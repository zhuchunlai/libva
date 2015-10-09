package org.codefamily.libva.http.core;

import java.util.HashMap;
import java.util.Map;

/**
 * // TODO doc
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-09
 */
public class HttpRequest<R> {
    private String endpoint;
    private String path;
    private HttpMethod method = HttpMethod.GET;
    private HttpConfig config;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, Object> parameters;
    private Object entity;
}

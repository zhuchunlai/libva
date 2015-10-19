package org.codefamily.libva.http.core;

import org.codefamily.libva.spi.Pluggable;

/**
 * 封装了HTTP请求和响应解析的细节
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-09
 */
public interface HttpExecutorService extends Pluggable {

    <R> HttpResponse<R> execute(HttpRequest<R> request);

}

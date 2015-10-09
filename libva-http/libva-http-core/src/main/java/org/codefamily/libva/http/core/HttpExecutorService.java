package org.codefamily.libva.http.core;

/**
 * // TODO doc
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-09
 */
public interface HttpExecutorService {

    <R> HttpResponse execute(HttpRequest<R> request);

}

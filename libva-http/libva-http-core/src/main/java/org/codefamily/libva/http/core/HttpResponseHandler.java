package org.codefamily.libva.http.core;

/**
 * // TODO docs
 *
 * @author zhuchunlai
 * @version $Id: HttpResponseHandler.java, v1.0 2015/10/19 21:52 $
 */
public interface HttpResponseHandler<T> {

    T handler(HttpResponse<T> response);

}

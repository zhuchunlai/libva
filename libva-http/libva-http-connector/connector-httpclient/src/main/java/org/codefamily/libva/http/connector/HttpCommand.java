package org.codefamily.libva.http.connector;

import org.codefamily.libva.http.core.HttpRequest;
import org.codefamily.libva.http.core.HttpResponse;

/**
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
class HttpCommand<R> {

    HttpCommand(HttpRequest<R> request) {
    }

    public HttpResponse<R> execute() {
        throw new UnsupportedOperationException();
    }


}

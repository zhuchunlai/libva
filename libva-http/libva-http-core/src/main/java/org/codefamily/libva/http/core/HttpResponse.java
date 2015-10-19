package org.codefamily.libva.http.core;

import java.util.Map;

/**
 * http响应的抽象
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-09
 */
public interface HttpResponse<R> {

    int getStatus();

    String getStatusMessage();

    R readEntity(Class<R> returnType);

    String header(String name);

    Map<String, String> headers();

}

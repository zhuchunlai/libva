package org.codefamily.libva.http;

import org.codefamily.libva.http.core.HttpExecutorService;
import org.codefamily.libva.http.core.HttpRequest;
import org.codefamily.libva.http.core.HttpResponse;
import org.codefamily.libva.http.core.exception.ConnectorNotFoundException;
import org.codefamily.libva.spi.PluginManager;
import org.codefamily.libva.util.ReadonlyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP请求执行器，对HttpExecutorService进行了进一步的封装
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @see HttpExecutorService
 * @since 2015-10-09
 */
public class HttpExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(HttpExecutor.class);

    private static final HttpExecutor INSTANCE = new HttpExecutor();

    private final HttpExecutorService executor;

    private HttpExecutor() {
        ReadonlyList<HttpExecutorService> executors = PluginManager.getPlugin(HttpExecutorService.class);
        if (executors == null || executors.isEmpty()) {
            throw new ConnectorNotFoundException();
        }
        HttpExecutorService executor = executors.get(0);
        LOG.info(String.format("load %s successfully.", executor.getName()));
        this.executor = executor;
    }

    public static HttpExecutor create() {
        return INSTANCE;
    }

    public <R> HttpResponse<R> execute(HttpRequest<R> request) {
        return executor.execute(request);
    }

}

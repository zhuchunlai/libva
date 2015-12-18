package org.codefamily.libva.http.connector;

import org.codefamily.libva.http.core.HttpExecutorService;
import org.codefamily.libva.http.core.HttpRequest;
import org.codefamily.libva.http.core.HttpResponse;
import org.codefamily.libva.http.core.exception.ConnectionException;

/**
 * 基于Apache HttpClient的执行器实现
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
public class HttpExecutorServiceImpl implements HttpExecutorService {

    private static final String EXECUTOR_NAME = "ApacheHttpClient-Executor";

    @Override
    public <R> HttpResponse<R> execute(HttpRequest<R> request) {
        HttpCommand<R> command = new HttpCommand<R>(request);
        try {
            return command.execute();
        } catch (Exception e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public String getName() {
        return EXECUTOR_NAME;
    }

    @Override
    public boolean acceptURL(String url) {
        return true;
    }

}

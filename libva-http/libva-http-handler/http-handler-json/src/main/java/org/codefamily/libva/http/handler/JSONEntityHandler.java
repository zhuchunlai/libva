package org.codefamily.libva.http.handler;

import com.alibaba.fastjson.JSON;
import org.codefamily.libva.annotation.Singleton;
import org.codefamily.libva.http.core.HttpEntityHandler;
import org.codefamily.libva.http.core.exception.LibvaHttpException;
import org.codefamily.libva.util.Closeables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * JSON解析http响应内容
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
@Singleton
public class JSONEntityHandler<R> implements HttpEntityHandler<R> {

    private static final String PROTOCOL = "handler:json://";

    @Override
    public R handle(Class<R> returnType, InputStream ins) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));

        String line;
        StringBuilder builder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            throw new LibvaHttpException("read response content error.", e);
        } finally {
            Closeables.close(reader);
        }
        return JSON.parseObject(builder.toString(), returnType);
    }

    @Override
    public boolean acceptURL(String url) {
        return url.startsWith(PROTOCOL);
    }

}

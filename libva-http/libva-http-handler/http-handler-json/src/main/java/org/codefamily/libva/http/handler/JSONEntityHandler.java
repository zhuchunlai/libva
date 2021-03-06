package org.codefamily.libva.http.handler;

import com.alibaba.fastjson.JSON;
import org.codefamily.libva.annotation.Singleton;
import org.codefamily.libva.http.core.Constants;
import org.codefamily.libva.http.core.HttpEntityHandler;
import org.codefamily.libva.http.core.exception.LibvaHttpException;
import org.codefamily.libva.util.Closeables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * JSON解析http响应内容
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
@Singleton
public class JSONEntityHandler<R> implements HttpEntityHandler<R> {

    @Override
    public R handle(Class<R> returnType, InputStream ins, Charset charset) {
        Charset internalCharset = charset == null ? Constants.UTF_8 : charset;
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins, internalCharset));

        String line;
        StringBuilder builder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return JSON.parseObject(builder.toString(), returnType);
        } catch (IOException e) {
            throw new LibvaHttpException("read response content error.", e);
        } finally {
            Closeables.close(reader);
        }
    }

    @Override
    public String getName() {
        return Constants.ENTITY_HANDLER_JSON;
    }

    @Override
    public boolean acceptURL(String url) {
        return url.startsWith(Constants.ENTITY_HANDLER_JSON);
    }

}

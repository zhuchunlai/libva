package org.codefamily.libva.http.handler;

import org.codefamily.libva.http.core.Constants;
import org.codefamily.libva.http.core.HttpEntityHandler;
import org.codefamily.libva.http.core.exception.ClientException;
import org.codefamily.libva.http.core.exception.ResponseException;
import org.codefamily.libva.util.Closeables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 文本实体解析器
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-12-18
 */
public class TextEntityHandler<R> implements HttpEntityHandler<R> {

    @Override
    @SuppressWarnings("unchecked")
    public R handle(Class<R> returnType, InputStream ins, Charset charset) {
        if (!returnType.isAssignableFrom(String.class)) {
            throw new ClientException("return type must be java.lang.String");
        }
        Charset internalCharset = charset == null ? Constants.UTF_8 : charset;
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins, internalCharset));
        try {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return (R) builder.toString();
        } catch (IOException e) {
            throw new ResponseException("read response error", e);
        } finally {
            Closeables.close(reader);
        }

    }

    @Override
    public String getName() {
        return Constants.ENTITY_HANDLER_TEXT;
    }

    @Override
    public boolean acceptURL(String url) {
        return url.startsWith(Constants.ENTITY_HANDLER_TEXT);
    }
}

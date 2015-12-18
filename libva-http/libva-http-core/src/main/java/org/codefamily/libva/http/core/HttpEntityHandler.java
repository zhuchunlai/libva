package org.codefamily.libva.http.core;

import org.codefamily.libva.spi.Pluggable;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * // TODO doc
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
public interface HttpEntityHandler<R> extends Pluggable {

    R handle(Class<R> returnType, InputStream ins, Charset charset);

    String getName();

}

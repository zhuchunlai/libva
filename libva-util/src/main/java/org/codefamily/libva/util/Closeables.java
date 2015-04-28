package org.codefamily.libva.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭IO流的工具类
 *
 * @author zhuchunlai
 * @version $Id: Closeables.java, v1.0 2015/04/27 21:34 $
 */
public final class Closeables {

    private Closeables() {
        // nothing to do.
    }

    /**
     * 关闭流，忽略关闭过程中的异常
     *
     * @param closeable 需要关闭的IO流
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }

}

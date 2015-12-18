package org.codefamily.libva.http.core.exception;

/**
 * HTTP异常基础抽象
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
public class LibvaHttpException extends RuntimeException {

    public LibvaHttpException(String message) {
        super(message);
    }

    public LibvaHttpException(String message, Throwable cause) {
        super(message, cause);
    }

}

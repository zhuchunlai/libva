package org.codefamily.libva.http.core.exception;

/**
 * // TODO doc
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-12-18
 */
public class ResponseException extends LibvaHttpException {

    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}

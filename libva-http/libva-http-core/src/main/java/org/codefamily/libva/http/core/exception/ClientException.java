package org.codefamily.libva.http.core.exception;

/**
 * 客户端错误，如：URI解析不正确
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-12-18
 */
public class ClientException extends LibvaHttpException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

}

package org.codefamily.libva.http.core.exception;

/**
 * 运行时环境中没有找到HttpExecutorService的实现
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
public class ConnectorNotFoundException extends LibvaHttpException {

    public ConnectorNotFoundException() {
        super("no connector found in runtime.");
    }

}

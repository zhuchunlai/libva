package org.codefamily.libva.http.core.exception;

/**
 * 运行时环境没有实体处理器或者没有指定类型的实体处理器
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-20
 */
public class EntityHandlerNotFoundException extends LibvaHttpException {

    public EntityHandlerNotFoundException() {
        super("no entity handler found in runtime.");
    }

    public EntityHandlerNotFoundException(String handlerName) {
        super(String.format("%s is not found in runtime.", handlerName));
    }

}

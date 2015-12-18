package org.codefamily.libva.http.core;

import org.codefamily.libva.http.core.exception.EntityHandlerNotFoundException;

import java.util.Map;

/**
 * http响应的抽象
 *
 * @author zhuchunlai
 * @version 1.0.0
 * @since 2015-10-09
 */
public interface HttpResponse<R> {

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    int getStatus();

    /**
     * 获取状态行信息
     *
     * @return 状态行信息
     */
    String getStatusMessage();

    /**
     * 采用默认实体解析器解析响应信息，默认实体解析器的加载规则：
     * 1、根据请求中指定的解析器类型查找
     * 2、根据Content-Type中的类型查找
     * 3、采用默认的解析器，JSON-Entity-Handler
     * <p/>
     * 上述三个规则顺序执行，找到即终止；全都找不到，则抛出异常 {@link EntityHandlerNotFoundException}
     *
     * @return 响应实体
     */
    R readEntity();

    /**
     * 根据给定的响应实体解析器解析响应内容并返回实体信息
     *
     * @param handler 实体解析器
     * @return 响应实体
     */
    R readEntity(HttpEntityHandler<R> handler);

    /**
     * 获取指定名称的响应头信息
     *
     * @param name 响应头名称
     * @return 响应头内容
     */
    String header(String name);

    /**
     * 获取所有的响应头信息
     *
     * @return 所有的响应头
     */
    Map<String, String> headers();

}

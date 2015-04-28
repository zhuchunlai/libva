package org.codefamily.libva.spi;

/**
 * 插件，标识组件是可插拔的。
 * 实现了此接口的组件，将会被自动加载，并接收PluginManager的管理。
 *
 * @author zhuchunlai
 * @version $Id: Pluggable.java, v1.0 2015/04/27 21:19 $
 */
public interface Pluggable {

    /**
     * 表示插件是否支持给定URL的处理
     *
     * @param url 需要支持的URL
     * @return <code>true</code>，插件支持给定URL的处理；反之则不支持
     */
    boolean acceptURL(String url);

}

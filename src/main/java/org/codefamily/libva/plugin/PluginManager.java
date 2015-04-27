package org.codefamily.libva.plugin;

import org.codefamily.libva.annotation.Singleton;
import org.codefamily.libva.util.Closeables;
import org.codefamily.libva.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 插件管理器，加载并管理所有实现了Pluggable接口的组件。
 * <p/>
 * 要想被PluginManager管理，则需要满足如下条件：
 * <ul>
 * <li>实现了Pluggable接口</li>
 * <li>无参构造方法</li>
 * <li>实现类需要配置到 META-INF/services/ 目录下</li>
 * </ul>
 *
 * @author zhuchunlai
 * @version $Id: PluginManager.java, v1.0 2015/04/27 21:23 $
 */
public final class PluginManager {

    // 插件的配置目录
    private static final String PLUGIN_FOLDER = "/META-INF/services/";

    private PluginManager() {
        // nothing to do.
    }

    /**
     * 获取能够处理指定URL的插件，如果找不到，则返回<code>null</code>
     *
     * @param url         需要支持的URL
     * @param pluginClass 插件基类
     * @param <T>         插件基类
     * @return 支持给定URL的插件实现
     */
    public static <T extends Pluggable> T getPlugin(final String url, final Class<T> pluginClass) {
        if (Strings.isNullOrEmpty(url)) {
            throw new IllegalArgumentException("url is null or empty.");
        }
        if (pluginClass == null) {
            throw new IllegalArgumentException("plugin class is null.");
        }
        if (!Pluggable.class.isAssignableFrom(pluginClass)) {
            throw new IllegalArgumentException("plugin class must be a sub-class of pluggable.");
        }

        for (T service : Service.providers(pluginClass)) {
            if (service.acceptURL(url)) {
                return service;
            }
        }
        return null;
    }

    /**
     * 服务
     */
    private static class Service {

        // 服务基类
        private final Class pluginClass;

        // 服务提供者列表
        private List<ServiceProvider> providers = new ArrayList<ServiceProvider>(10);

        // 服务是否加载完毕
        private boolean loaded = false;

        private static ConcurrentHashMap<Class, Service> PLUGINS = new ConcurrentHashMap<Class, Service>(10);

        private Service(final Class pluginClass) {
            this.pluginClass = pluginClass;
        }

        /**
         * 获取服务的提供者
         *
         * @param pluginClass 服务类
         * @param <T>         服务类
         * @return 当前JVM中，服务的所有提供者
         */
        public static <T extends Pluggable> Iterable<T> providers(Class<T> pluginClass) {
            String pluginName = pluginClass.getName();
            if (!Pluggable.class.isAssignableFrom(pluginClass)) {
                throw new RuntimeException(String.format("plugin(%s) must be a sub-class of %s",
                        pluginName, Pluggable.class.getName()));
            }

            String configFileName = PLUGIN_FOLDER + pluginName;

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> configs;
            try {
                configs = classLoader.getResources(configFileName);
            } catch (IOException e) {
                throw new RuntimeException(String.format("load plugin(%s) error.", pluginName), e);
            }

            URL providerURL;
            BufferedReader reader;
            String providerName;
            try {
                while (configs.hasMoreElements()) {
                    providerURL = configs.nextElement();
                    reader = new BufferedReader(new InputStreamReader(providerURL.openStream()));
                    try {
                        while ((providerName = reader.readLine()) != null) {
                            // todo
                        }
                    } finally {
                        Closeables.close(reader);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(String.format("load plugin(%s) error.", pluginName), e);
            }

            throw new UnsupportedOperationException();
        }

        private void addProvider(ServiceProvider provider) {
            providers.add(provider);
        }

    }

    /**
     * 服务提供者
     */
    private static class ServiceProvider<T> {

        private static final Class[] EMPTY_PARAMETERS = new Class[0];

        // 服务提供者的完整类名
        private final String className;

        // 服务提供者
        private final Class providerClass;

        // 标识服务是否是单例的
        private final boolean singleton;

        // 服务类
        private final Class<T> pluginClass;

        private T cachedTarget;

        ServiceProvider(final String className, final Class<T> pluginClass) {
            Class providerClass;
            boolean singleton = false;
            try {
                providerClass = Class.forName(className);
                if (providerClass.isAnnotationPresent(Singleton.class)) {
                    Singleton annotation = (Singleton) providerClass.getAnnotation(Singleton.class);
                    singleton = annotation.value();
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(String.format("plugin(%s) is not found.", className), e);
            }

            this.singleton = singleton;
            this.providerClass = providerClass;
            this.className = className;
            this.pluginClass = pluginClass;
        }

        /**
         * 获取插件实现
         *
         * @return 插件的特定实现
         */
        public T getProvider() {
            T provider;
            if (singleton) {
                if (cachedTarget == null) {
                    synchronized (this) {
                        if (cachedTarget == null) {
                            cachedTarget = getProvider0();
                        }
                    }
                }
                provider = cachedTarget;
            } else {
                provider = getProvider0();
            }
            return provider;
        }

        private T getProvider0() {
            try {
                if (!pluginClass.isAssignableFrom(providerClass)) {
                    throw new RuntimeException(String.format("plugin(%s) is not a sub-class of %s",
                            className, pluginClass.getName()));
                }
                int modifier = providerClass.getModifiers();
                if (!Modifier.isPublic(modifier)
                        || Modifier.isAbstract(modifier) || Modifier.isInterface(modifier)) {
                    throw new RuntimeException(String.format(
                            "plugin(%s) must be a public and specified implementation class.", className));
                }

                @SuppressWarnings("unchecked")
                Constructor constructor = providerClass.getConstructor(EMPTY_PARAMETERS);
                return pluginClass.cast(constructor.newInstance((Object[]) null));

            } catch (NoSuchMethodException e) {
                throw new RuntimeException(String.format("plugin(%s) must has non-parametric constructor.", className), e);
            } catch (Exception e) {
                throw new RuntimeException(String.format("load plugin(%s) error.", className), e);
            }
        }

    }


}

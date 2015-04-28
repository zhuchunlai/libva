package org.codefamily.libva.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识类的单例属性
 *
 * @author zhuchunlai
 * @version $Id: Singleton.java, v1.0 2015/04/27 21:53 $
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Singleton {
}

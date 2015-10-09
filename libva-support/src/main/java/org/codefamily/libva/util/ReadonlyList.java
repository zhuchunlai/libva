package org.codefamily.libva.util;

/**
 * 只读型List
 *
 * @author zhuchunlai
 * @version $Id: ReadonlyList.java, v1.0 2015/10/09 21:19 $
 */
public interface ReadonlyList<E> extends Iterable<E> {

    /**
     * 集合大小，即包含的元素数量
     *
     * @return 集合大小
     */
    int size();

    /**
     * 是否是空集合，即不含有任何元素
     *
     * @return <code>true</code>，空集合，反之则含有元素
     */
    boolean isEmpty();

    /**
     * 集合中是否存在给定的元素
     *
     * @param o 需要查找的元素
     * @return <code>true</code>，存在；反之则不存在
     */
    boolean contains(Object o);

    /**
     * 将集合转换成数组
     *
     * @return 数组，其元素依次为集合中对应索引位置的元素
     */
    Object[] toArray();

    /**
     * 将集合转换成指定类型的数组
     *
     * @param a   目标类型的数组
     * @param <T> 元素类型
     * @return 数组
     */
    <T> T[] toArray(T[] a);

    /**
     * 获取给定索引位置的元素
     *
     * @param index 位置索引
     * @return 对应位置的元素
     */
    E get(int index);

}

package org.codefamily.libva.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 基于数组结构的只读型List实现
 *
 * @author zhuchunlai
 * @version $Id: ReadonlyArrayList.java, v1.0 2015/10/09 21:23 $
 */
public class ReadonlyArrayList<E> implements ReadonlyList<E> {

    private final List<E> elements = new ArrayList<E>();

    public ReadonlyArrayList() {
        // nothing to do.
    }

    public ReadonlyArrayList(Collection<E> elements) {
        if (elements != null && !elements.isEmpty()) {
            for (E element : elements) {
                this.elements.add(element);
            }
        }
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elements.contains(o);
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return elements.toArray(a);
    }

    @Override
    public E get(int index) {
        return elements.get(index);
    }

    @Override
    public Iterator<E> iterator() {
        return new DefaultIterator();
    }

    private class DefaultIterator implements Iterator<E> {

        private final Iterator<E> iterator;

        DefaultIterator() {
            this.iterator = elements.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}

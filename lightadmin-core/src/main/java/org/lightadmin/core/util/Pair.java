package org.lightadmin.core.util;

import com.google.common.base.Function;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Pair<T, V> {

    public final T first;
    public final V second;

    public Pair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public static <T> Function<Pair<T, ?>, T> extractFirstTransformer() {
        return new Function<Pair<T, ?>, T>() {
            @Override
            public T apply(final Pair<T, ?> pair) {
                return pair.first;
            }
        };
    }

    public static <T> Function<Pair<?, T>, T> extractSecondTransformer() {
        return new Function<Pair<?, T>, T>() {
            @Override
            public T apply(final Pair<?, T> pair) {
                return pair.second;
            }
        };
    }

    public T getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public static <T, V> Pair<T, V> create(T first, V second) {
        return new Pair<T, V>(first, second);
    }

    public static Pair<String, String> stringPair(String first, String second) {
        return create(first, second);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Pair pair = (Pair) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) {
            return false;
        }
        if (second != null ? !second.equals(pair.second) : pair.second != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("first", first).append("second", second).toString();
    }
}
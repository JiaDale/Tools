package com.jdy.util;

import com.jdy.log.Log;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;

/**
 * Description: 数组工具
 * Created by Administrator on 2019/9/19 16:03
 */
public class ArrayUtil {

    /**
     * 将新元素添加到已有数组中<br>
     * 添加新元素会生成一个新的数组，不影响原数组
     *
     * @param <T>         数组元素类型
     * @param buffer      已有数组
     * @param newElements 新元素
     * @return 新数组
     */
    @SafeVarargs
    public static <T> T[] append(T[] buffer, T... newElements) {
        if (isEmpty(buffer)) {
            return newElements;
        }

        if (isEmpty(newElements)) {
            return buffer;
        }

        int len = buffer.length + newElements.length;
        T[] result = Arrays.copyOf(buffer, len);
        System.arraycopy(newElements, 0, result, buffer.length, newElements.length);
        return result;
    }

    /**
     * 数组是否为空
     * <p>
     * 备注：空包括两部分，
     * <pre>
     *      1.数组对象 == null；
     *      2.数组对象中没有元素;
     *  <pre>
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    @SafeVarargs
    public static <T> boolean isEmpty(final T... array) {
        return array == null || array.length == 0;
    }

    /**
     * 校验数组是否不为空
     *
     * @param array 被校验的数组
     * @param <T>   数组元素类型
     * @return true : 数组不为空
     * @see #isEmpty(Object[])
     */
    public static <T> boolean isNotEmpty(final T... array) {
        return !isEmpty(array);
    }

    /**
     * 判断 {@code Object}对象是否为数组对象
     *
     * @param object 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object object) {
        return !isNotArray(object);
    }

    public static boolean isNotArray(Object object) {
        return null == object || !object.getClass().isArray();
    }


    /**
     * 过滤<br>
     * 过滤过程通过传入的Filter实现来过滤返回需要的元素内容，这个Filter实现可以实现以下功能：
     *
     * <pre>
     * 1、过滤出需要的对象，{@link Predicate#test(Object)} 方法返回true的对象将被加入结果集合中
     * </pre>
     *
     * @param <T>       数组元素类型
     * @param array     数组
     * @param predicate 过滤器接口，用于定义过滤规则，null表示不过滤，返回原数组
     * @return 过滤后的数组
     */
    public static <T> T[] filter(T[] array, Predicate<T> predicate) {
        if (null == predicate) {
            return array;
        }

        final ArrayList<T> list = new ArrayList<>(array.length);
        for (T t : array) {
            if (predicate.test(t)) {
                list.add(t);
            }
        }
        final T[] result = newArray(array.getClass().getComponentType(), list.size());
        return list.toArray(result);
    }

    /**
     * 新建一个空数组
     *
     * @param <T>           数组元素类型
     * @param componentType 元素类型
     * @param newSize       大小
     * @return 空数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }


    /**
     * 检查数组中是否含有指定元素
     *
     * @param array   被校验的数组
     * @param element 指定元素
     * @param <T>     数组元素类型
     * @return true：数组含有指定元素；false：数组不含有指定元素
     */
    public static <T> boolean hasElement(T[] array, final T element) {
        return hasElement(array, item -> {
            if (Objects.isNull(item))//如果考虑数组中含有空元素，而指定的元素也是空元素，则可直接返回true
                return Objects.isNull(element);
            return item.equals(element);
        });
    }

    /**
     * 检查数组中是否含有指定元素
     *
     * @param array     被校验的数组
     * @param predicate 校验规则
     * @param <T>       数组元素类型
     * @return true：数组含有指定元素；false：数组不含有指定元素
     */
    public static <T> boolean hasElement(T[] array, Predicate<T> predicate) {
        if (isEmpty(array)) {
            return false;
        }
        Objects.requireNonNull(predicate, "请定义含有指定元素的规则！");
        for (T item : array)
            if (predicate.test(item))
                return true;
        return false;
    }

    /**
     * 判断数组中是否含有相同的元素，默认忽略空元素
     *
     * @param elements 数组元素集
     * @param <T>      数组元素类型
     * @return true ： 含有相同元素； false ： 不含相同元素
     */
    public static <T> boolean hasRepeatElement(T... elements) {
        return hasRepeatElement(elements, true);
    }

    /**
     * 判断数组中是否含有相同的元素，默认忽略空元素
     *
     * @param elements   数组元素集
     * @param ignoreNull 是否忽略空元素(即元素值为空的元素)
     * @param <T>        数组元素类型
     * @return true ： 含有相同元素； false ： 不含相同元素
     */
    public static <T> boolean hasRepeatElement(T[] elements, boolean ignoreNull) {
        Set<T> set = new TreeSet<>();
        for (T element : elements) {
            if (set.contains(element)) {//如果含有相同元素，直接返回true
                return true;
            }
            //如果忽略空元素，则直接跳过空元素即可
            if (Objects.isNull(element) && ignoreNull) {
                continue;
            }
            //加入set集合
            set.add(element);
        }
        return false;
    }

    /**
     * 用指定元素替换数组中的第{@code index}个元素
     *
     * @param array   元素数组
     * @param element 指定元素
     * @param index   数组所在位置
     * @param <T>     数组元素类型
     * @return 替换后的元素数组
     */
    public static <T> T[] replaceElements(T[] array, T element, int index) {
        if (isEmpty(array)) {
            return array;
        }

        if (index >= array.length) {
            Log.error("超过数组的最大长度，无法替换！");
            return array;
        }

        array[index] = element;
        return array;
    }

    /**
     * 用指定元素替换数组中的所有元素
     *
     * @param array   元素数组
     * @param element 指定元素
     * @param <T>     数组元素类型
     * @return 替换后的元素数组
     */
    public static <T> T[] replaceAllElements(T[] array, T element) {
        if (isEmpty(array)) {
            return array;
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = element;
        }

        return array;
    }
}

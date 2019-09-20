package com.jdy.check;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 检查接口
 * <p>
 * 此接口主要是对数据进行校验，如果校验成功则返回对校验成功值转换过后的值，否则返回对默认值转换过后的值
 *
 * @param <T> 校验的参数类型
 */
public interface Checkable<T> {

    /**
     * 字段值校验
     *
     * @param value        校验对象
     * @param defaultValue 失败时默认返回值
     * @return 如果{@code value}为空， 则返回{@code defaultValue}值]
     */
    default <R> R check(T value, R defaultValue) {
        return check(value, Objects.isNull(value), defaultValue);
    }

    /**
     * 字段值校验
     * 考虑到校验方式的变化，因此提供一个校验接口
     *
     * @param value        校验对象
     * @param predicate    接口对象
     * @param defaultValue 失败时默认返回值
     * @return 如果{@code value}为空， 则返回{@code defaultValue}值]
     */
    default <R> R check(T value, Predicate<T> predicate, R defaultValue) {
        return check(value, predicate.test(value), defaultValue);
    }

    /**
     * 字段值校验 考虑到校验方式的变化，因此提供一个校验接口
     *
     * @param value        校验对象
     * @param retDefault   是否返回默认值
     * @param defaultValue 失败时默认返回值
     * @return 如果{@code value}为空， 则返回{@code defaultValue}值]
     */
    <R> R check(T value, boolean retDefault, R defaultValue);

    /**
     * 字段值校验 在校验后可能还需要对返回结果进行转换，添加一个转换函数接口，
     * 由于需要的类型可能不确定，因此这里使用泛型，具体返回值数据类型由转换函数确定
     *
     * @param value        校验对象
     * @param defaultValue 失败时默认返回值
     * @param function     转换函数
     * @return 返回校验结果转换值
     */
    default <R> R check(T value, R defaultValue, Function<T, R> function) {
        return check(value, Objects.isNull(value), defaultValue, function);
    }

    /**
     * 数值校验
     * <p>
     * 参数中添加了一个{@link Function}对象，主要是用来对结果进行转换的一个函数， 这个结果包括校验成功的结果和校验失败的结果，
     * 但有些时候，在校验失败时，是不需要对校验失败的结果进行转换的，因此这里需要调用者根据需要在{@link Function}中对此做下处理
     *
     * @param value        校验对象
     * @param predicate    校验接口
     * @param defaultValue 失败时默认返回值
     * @param function     转换函数
     * @return 返回校验结果转换值
     */
    default <R> R check(T value, Predicate<T> predicate, R defaultValue, Function<T, R> function) {
        return check(value, rePredicate(predicate).test(value), defaultValue, function);
    }

    /**
     * 数值校验
     * <p>
     * 参数中添加了一个{@link Function}对象，主要是用来对结果进行转换的一个函数， 这个结果包括校验成功的结果和校验失败的结果，
     * 但有些时候，在校验失败时，是不需要对校验失败的结果进行转换的，因此这里需要调用者根据需要在{@link Function}中对此做下处理
     *
     * @param value        校验对象
     * @param retDefault   失败时默认返回值
     * @param defaultValue 失败时默认返回值
     * @param function     转换函数
     * @return 返回校验结果转换值
     */
    default <R> R check(T value, boolean retDefault, R defaultValue, Function<T, R> function){
        if (retDefault){
            return  defaultValue;
        }
        return function.apply(value);
    }

    /**
     * 构造一个校验接口对项
     *
     * 设计目的是为了达到， 在校验过程中的判断先后顺序的效果，
     * TODO 此效果暂时未达到
     *
     * 另外如果校验接口对象实例为空，默认会先进行判空校验
     * @param predicate 校验接口对象
     * @param <T> 校验的数据类型
     * @return 一个新的校验接口对象
     */
    static <T> Predicate<T> rePredicate(Predicate<T> predicate) {
        if (null == predicate) {
            return Objects::isNull;
        }
        Predicate<T> temp = Objects::isNull;
        return temp.or(predicate);
    }
}

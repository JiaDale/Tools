package com.jdy.functions;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 13:22
 */
public interface PredicateFunction<T, R> extends Predicate<T>, Function<T, R> {

}

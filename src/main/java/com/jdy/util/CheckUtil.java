package com.jdy.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class CheckUtil {

    /**
     * 用于检测某个方法是否能正常运行，此功能尚未完善
     *
     * @param value      指定类的对象实例
     * @param methodName 方法名称
     * @param args       方法参数
     * @param <T>        泛型类
     * @return 空或者方法的返回值
     */
    public static <T> Object check(T value, String methodName, Object... args) {
        if (Objects.isNull(value)) {
            return null;
        }

        try {
            Method method = value.getClass().getMethod(methodName, ClassUtil.getClass(args));
            return method.invoke(value, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
    public static <T, R> R checkValue(T value, boolean retDefault, R defaultValue, Function<T, R> function) {
        if (null == function) {
            throw new NullPointerException("Convert function can't be null!");
        }

        if (retDefault) {
            return defaultValue;
        }

        return function.apply(value);
    }

    /**
     * 字段值校验 考虑到校验方式的变化，因此提供一个校验接口
     *
     * @param value        校验对象
     * @param retDefault   是否返回默认值
     * @param defaultValue 失败时默认返回值
     * @return 如果{@code value}为空， 则返回{@code defaultValue}值]
     */
    public static <T> T checkValue(T value, boolean retDefault, T defaultValue) {
        if (retDefault) {
            return defaultValue;
        }
        return value;
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
    public static <T, R> R checkValue(T value, Predicate<T> predicate, R defaultValue, Function<T, R> function) {
        return checkValue(value, rePredicate(predicate).test(value), defaultValue, function);
    }

    /**
     * 字段值校验 考虑到校验方式的变化，因此提供一个校验接口
     *
     * @param value        校验对象
     * @param predicate    接口对象
     * @param defaultValue 失败时默认返回值
     * @return 如果{@code value}为空， 则返回{@code defaultValue}值
     */
    public static <T> T checkValue(T value, Predicate<T> predicate, T defaultValue) {
        return checkValue(value, rePredicate(predicate).test(value), defaultValue);
    }


    /**
     * 字段值校验 在校验后可能还需要对返回结果进行转换，添加一个转换函数接口，
     * 由于需要的类型可能不确定，因此这里使用泛型，具体返回值数据类型由转换函数确定
     *
     * @param value        校验对象
     * @param defaultValue 失败时默认返回值
     * @param function     转换函数
     * @return 返回校验结果转换值
     */
    public static <T, R> R checkValue(T value, R defaultValue, Function<T, R> function) {
        return checkValue(value, Objects.isNull(value), defaultValue, function);
    }

    /**
     * 字段值校验， 校验成功返回 {@code value}, 校验失败返回{@code defaultValue}
     *
     * @param value        校验的字段
     * @param defaultValue 校验失败时默认返回值
     * @param <T>          校验字段的数据类型
     * @return value ： 校验成功； defaultValue ：校验失败
     */
    public static <T> T checkValue(T value, T defaultValue) {
        return checkValue(value, Objects.isNull(value), defaultValue);
    }

    /**
     * 检测{@code format}是否不是日期格式的字符串
     *
     * @param format 日期格式的字符串
     * @return true ：{@code format} 不是日期格式的字符串； false : {@code format} 是日期格式的字符串;
     */
    public static boolean isNotDateFormat(String format) {
        if (TextUtils.isEmpty(format))
            return true;
        return TextUtils.isEmpty(DateUtil.analyze(format));
    }

    /**
     * 检测{@code format}是否是日期格式的字符串
     *
     * @param format 日期格式的字符串
     * @return true ：{@code format} 是日期格式的字符串； false : {@code format} 不是日期格式的字符串;
     */
    public static boolean isDateFormat(String format) {
        return !isNotDateFormat(format);
    }

    /**
     * 检测{@code tClass}是否是基本数据类型，这里基本数据类型主要包括以下数据类型：
     * <p>
     * <li>
     * {@link String}
     * {@link Integer}
     * {@link Double}
     * {@link Float}
     * {@link Long}
     * {@link Byte}
     * {@link Short}
     * {@link Boolean}
     * {@link Date}
     * </li>
     *
     * @param tClass 所检测的数据对象的{@code Class}对象
     * @return true ：所检测的数据是基本数据类型; false : 所检测的数据不是是基本数据类型
     */
    public static <T> boolean isSimpleDataType(Class<T> tClass) {
        return tClass.isAssignableFrom(String.class) || tClass.isAssignableFrom(Integer.class) || tClass.isAssignableFrom(Double.class) || tClass.isAssignableFrom(Float.class) ||
                tClass.isAssignableFrom(Long.class) || tClass.isAssignableFrom(Byte.class) || tClass.isAssignableFrom(Short.class) || tClass.isAssignableFrom(Boolean.class) || tClass.isAssignableFrom(Date.class);
    }

    /**
     * 构造一个校验接口对项
     * <p>
     * 设计目的是为了达到， 在校验过程中的判断先后顺序的效果，
     * TODO 此效果暂时未达到
     * <p>
     * 另外如果校验接口对象实例为空，默认会先进行判空校验
     *
     * @param predicate 校验接口对象
     * @param <T>       校验的数据类型
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



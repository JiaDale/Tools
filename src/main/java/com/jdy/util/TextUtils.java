package com.jdy.util;

import com.jdy.functions.SQLFunction;
import com.jdy.functions.StringFunction;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 字符串工具类
 */
public class TextUtils {

    public static final String EMPTY = "";
    public static final String NULL = "null";

    /**
     * 数值校验
     * <p>
     * 参数中添加了一个{@link Function}对象，主要是用来对结果进行转换的一个函数， 这个结果包括校验成功的结果和校验失败的结果，
     * 但有些时候，在校验失败时，是不需要对校验失败的结果进行转换的，因此这里需要调用者根据需要在{@link Function}中对此做下处理
     *
     * @param value        校验对象
     * @param retDefault   校验接口
     * @param defaultValue 失败时默认返回值
     * @param function     转换函数
     * @return 返回校验结果转换值
     */
    public static <T> T checkValue(String value, boolean retDefault, T defaultValue, Function<String, T> function) {
        return CheckUtil.checkValue(value, retDefault, defaultValue, function);
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
    public static String checkValue(String value, Predicate<String> predicate, String defaultValue) {
        return CheckUtil.checkValue(value, predicate, defaultValue);
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
    public static <T> T checkValue(String value, Predicate<String> predicate, T defaultValue, Function<String, T> function) {
        return CheckUtil.checkValue(value, predicate, defaultValue, function);
    }

    /**
     * 字段值校验
     *
     * @param value        校验对象
     * @param defaultValue 失败时默认返回值
     * @return 如果{@code value}为空， 则返回{@code defaultValue}值]
     */
    public static String checkValue(String value, String defaultValue) {
        return CheckUtil.checkValue(value, isEmpty(value), defaultValue);
    }

    /**
     * 字段值校验
     * 在校验后可能还需要对返回结果进行转换，添加一个转换函数接口，
     * 由于需要的类型可能不确定，因此这里使用泛型，具体返回值数据类型由转换函数确定
     *
     * @param value        校验对象
     * @param defaultValue 失败时默认返回值
     * @param function     转换函数
     * @return 返回校验结果转换值
     */
    public static <T> T checkValue(String value, T defaultValue, Function<String, T> function) {
        return checkValue(value, TextUtils::isEmpty, defaultValue, function);
    }

    /**
     * 如果字符串为空，返回空字符串，否则返回被检查的字符串
     *
     * @param str 被检查的字符串
     * @return 被检查的字符串或者空字符串
     */
    public static String emptyIfNull(String str) {
        return checkValue(str, EMPTY);
    }

    /**
     * 比较两个字符串是否相等。
     *
     * @param current    要比较的字符串
     * @param target     要比较的字符串
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(CharSequence current, CharSequence target, boolean ignoreCase) {
        if (null == current) {
            // 只有两个都为null才判断相等
            return target == null;
        }

        if (null == target) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return current.toString().equalsIgnoreCase(target.toString());
        } else {
            return current.equals(target);
        }
    }

    /**
     * 判断目标字符串中是否含有指定元素
     *
     * <p>
     * 字符串： 15,55  中是否包含 5 这个元素
     * <p>
     * 主要思路是先将字符串按照元素分隔符分开，然后逐一对比是否包含指定元素
     * </p>
     *
     * @param target 目标字符串
     * @param regex  元素分隔符
     * @param text   指定元素
     * @return true：目标字符串中含有指定元素；false：目标字符串中不含有指定元素
     */
    public static boolean hasElement(String target, String regex, String text) {
        if (isEmpty(target)) {
            return false;
        }
        return ArrayUtil.hasElement(target.split(regex), text);
    }

    /**
     * 检查字符串是否为空,其作用与{@link #isEmpty(CharSequence)}方法一样，添加了去空客后校验和"null"字符校验
     *
     * @param str 被检查的字符串
     * @return true ： 字符串为空； false： 字符串不为空
     */
    public static boolean isBlack(CharSequence str) {
        return isEmpty(str) || str.toString().trim().isEmpty() || "null".equals(str.toString());
    }

    /**
     * 检查字符串是否不为空，与方法{@link #isBlack(CharSequence)}结果相反
     *
     * @param str 被检查的字符串
     * @return true ： 字符串不为空； false： 字符串为空
     */
    public static boolean isNotBlack(CharSequence str) {
        return !isBlack(str);
    }


    /**
     * 检查字符串是否为空
     * 但字符串为 “ ”返回也是true，如果检查的字符串中 “ ”字符也要检测请调用{@link #isBlack(CharSequence)}方法
     *
     * @param str 被检查的字符串
     * @return true ： 字符串为空； false： 字符串不为空
     */
    public static boolean isEmpty(CharSequence str) {
        return null == str || str.toString().isEmpty();
    }


    /**
     * 检查字符串是否不为空，与方法{@link #isEmpty(CharSequence)}结果相反
     *
     * @param str 被检查的字符串
     * @return true ： 字符串不为空； false： 字符串为空
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }


    /**
     * 将字符串添加到另一个字符串的后面，默认不可重复(即，如果字符串中已经包含即将要添加的元素，则不会进行拼接)
     *
     * @param target    目标字符串
     * @param separator 分隔符
     * @param text      需拼接的字符串
     * @return 拼接后的字符串
     */
    public static String join(String target, String separator, String text) {
        return join(target, separator, text, false);
    }

    /**
     * 将字符串添加到另一个字符串的后面
     *
     * @param target       目标字符串
     * @param separator    分隔符
     * @param text         需拼接的字符串
     * @param ignoreRepeat 是否忽略重复
     * @return 拼接后的字符串
     */
    public static String join(String target, String separator, String text, boolean ignoreRepeat) {
        if (ignoreRepeat) { //如果忽略重复，则直接拼接即可
            return union(target, separator, text);
        }

        //如果不忽略重复，则序号判断在 target中是否存在，存在直接放回target
        if (hasElement(target, separator, text)) {
            return target;
        }

        //不存在拼接后返回
        return union(target, separator, text);
    }


    /**
     * 辅助{@link #join(String, String, String, boolean)}方法而新增的拼接方法，主要负责拼接工作
     *
     * @param target    目标字符串
     * @param separator 分隔符
     * @param text      需拼接的字符串
     * @return 拼接后的字符串
     */
    private static String union(String target, String separator, String text) {
        if (isEmpty(target)) {
            return text;
        }

        if (target.endsWith(separator)) {
            return target.concat(text);
        }

        return target + separator + text;
    }


    /**
     * 将字符串{@code target}首字母小写
     *
     * @param target 需转换的字符串
     * @return 首字母小写的字符串
     */
    public static String lowerFristCase(String target) {
        if (isEmpty(target))//如果需转换的字符串为空，直接返回
            return target;

        //转换成字符串
        char[] chars = target.toCharArray();

        //如果首字符不是大写英文大写字符，
        if (CharacterUtil.isNotUpperLetter(chars[0])) {
            //这里就不抛异常了，直接返回原来的字符串
            return target;
        }

        chars[0] += 32;
        return String.copyValueOf(chars);
    }


    /**
     * 如果字符串为空，返回null，否则返回被检查的字符串
     *
     * @param str 被检查的字符串
     * @return 被检查的字符串或者 null
     */
    public static String nullIfEmpty(String str) {
        return checkValue(str, null);
    }

    /**
     * 将字符串{@code target}首字母大写
     *
     * @param target 需转换的字符串
     * @return 首字母小写的字符串
     */
    public static String upperFristCase(String target) {
        if (isEmpty(target))//如果需转换的字符串为空，直接返回
            return target;
        //转换成字符串
        char[] chars = target.toCharArray();
        //如果首字符不是大写英文小写字符，
        if (CharacterUtil.isNotLowerLetter(chars[0])) {
            //这里就不抛异常了，直接返回原来的字符串
            return target;
        }

        chars[0] -= 32;
        return String.copyValueOf(chars);
    }


    /**
     * 检测字符串{@code value}是否含有中文字符
     *
     * @param value 需检测的字符串
     * @return true： 字符串含有中文字符； false：字符串没有中文字符
     */
    public static boolean hasChinese(CharSequence value) {
        if (isEmpty(value))
            return false;
        //只要有一个字符是中文，直接返回true
        for (char c : value.toString().toCharArray()) {
            if (CharacterUtil.isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查字符串中是否没有数字
     *
     * @param value 被检查的字符串
     * @return true：字符串不含有数字； false：字符串含有数字
     */
    public static boolean hasNoNumber(CharSequence value) {
        return !hasNumber(value);
    }

    /**
     * 检测字符串是否含有数字
     *
     * @param value 被检测的字符串
     * @return true： 字符串含有数字； false：字符串没有数字
     */
    public static boolean hasNumber(CharSequence value) {
        if (isEmpty(value))
            return false;

        for (char chr : value.toString().toCharArray()) {
            if (CharacterUtil.isNumber(chr))
                return true;
        }
        return false;
    }

    /**
     * 检测字符串是否全是英文字符
     *
     * @param value 需检测的字符串
     * @return true ： 是英文字符串； false ： 字符串中含有非英文的字符
     */
    public static boolean isLetter(CharSequence value) {
        if (isEmpty(value))
            return false;

        for (char c : value.toString().toCharArray())
            if (CharacterUtil.isNotLetter(c))
                return false;
        return true;
    }


    /**
     * 检测字符串{@code value}是否全是中文字符，
     *
     * @param value 需检测的字符串
     * @return true： 字符串全是中文字符； false：字符串含有非中文字符
     */
    public static boolean isChinese(CharSequence value) {
        if (isEmpty(value))
            return false;

        //只要有一个字符不是中文，直接返回false
        for (char c : value.toString().toCharArray()) {
            if (CharacterUtil.isNotChinese(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检测字符串是否全是大写的英文字符
     *
     * @param value 需检测的字符串
     * @return true ：字符串全是大写英文字符； false ：含有非大写的英文字符或者是非英文字符
     */
    public static boolean isUpperCase(String value) {
        if (isEmpty(value))
            return false;

        for (char chr : value.toCharArray())
            if (CharacterUtil.isNotUpperLetter(chr))
                return false;

        return true;
    }


    /**
     * 检测字符串是否全是小写英文字符
     *
     * @param value 需检测的字符串
     * @return true ：字符串全是小写英文字符； false ： 含有非小写的英文字符或者是非英文字符
     */
    public static boolean isLowerLetter(CharSequence value) {
        if (isEmpty(value))
            return false;

        for (char c : value.toString().toCharArray())
            if (CharacterUtil.isNotLowerLetter(c))
                return false;

        return true;
    }

    /**
     * 检测字符串是否全是数字(包括小数点， 即如果含有小数点，也会返回false)
     *
     * @param value 需检测的字符串
     * @return true ： 全是数字； false ： 含有非数字
     */
    public static boolean isNumber(Object value) {
        if (null == value)
            return false;

        //如果value本事就是一个Number对象，则认为value就是一个数字
        if (value instanceof Number)
            return true;

        //如果以小数点开头或结束，直接返回false
        String number = value.toString().trim();
        if (number.startsWith(".") || number.endsWith("."))
            return false;

        for (char chr : number.replaceFirst("\\.", "").toCharArray())
            if (CharacterUtil.isNotNumber(chr))
                return false;

        return true;
    }

    public static String completeSQL(String sql, Object[] values) {
        if (isEmpty(sql)) {
            return sql;
        }

        for (Object value : values) {
            sql = sql.replaceFirst("\\?", convert(value, SQLFunction.getInstance()));
        }

        return sql;
    }

    public static String convert(Object value){
        return convert(value,  StringFunction.getInstance());
    }

    public static String convert(Object value, Function<Object, String> function) {
        return Objects.requireNonNull(function).apply(value);
    }
}

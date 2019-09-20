package com.jdy.util;


import com.jdy.io.FileUtils;
import com.jdy.work.BaseWorkDay;
import com.jdy.work.IWorkDay;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

/**
 * 日期相关工具
 *
 * @author Dale at 2018年7月2日
 */
public class DateUtil {

    public static final String FORMAT = "yyyy-MM-dd";
    public static final String HIGH_PRECISION_FORMAT = "yyyy-MM-dd HH:mm:ss"; //高精度日期格式


    /**
     * 日期格式验证, 批量验证日期字符串是否是指定的日期格式
     *
     * @param format 日期格式
     * @param dates  日期字符串数组
     * @return true ： 日期字符串数组全部是指定的日期格式； false ：  日期字符串数组中至少含有一个不是指定的日期格式
     */
    public static boolean isValidDate(String format, String... dates) {
        if (dates == null || dates.length == 0) {
            return false;
        }

        for (String date : dates) {
            if (!isValidDate(format, date)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测日期字符串是否是指定的日期格式
     *
     * @param format 日期格式
     * @param date   日期字符串
     * @return true ：日期字符串是指定的 日期格式 ； false ： 日期字符串不是指定的日期格式
     */
    public static boolean isValidDate(String format, String date) {
        return analyze(format).equals(analyze(date));
    }

    /**
     * 将日期字符串转换成默认{@link #HIGH_PRECISION_FORMAT}日期{@link Date}对象
     *
     * @param target 日期字符串
     * @return 日期对象 高精度格式 'yyyy-MM-dd HH:mm:ss'
     */
    public static Date convert(String target) {
        //这里默认使用高精度日期格式进行转换
        return convert(target, HIGH_PRECISION_FORMAT);
    }

    public static Date convert(String target, String format) {
        DateFormat dateFormat = getDateFormat(format);
        if (null == dateFormat || CheckUtil.isNotDateFormat(target))
            return null;
        try {
            return dateFormat.parse(target);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期格式化， 将日期对象进行格式化，
     *
     * @param value 需格式话的 对象
     * @return 日期字符串 默认格式 ""yyyy-MM-dd
     */
    public static String dateFormat(Object value) {
        return dateFormat(value, FORMAT);
    }

    /**
     * 日期格式化， 将日期对象进行格式化，
     *
     * @param date 需格式话的 对象
     * @return 日期字符串 默认格式 ""yyyy-MM-dd
     */
    public static String dateFormat(Date date) {
        return dateFormat(date, FORMAT);
    }

    /**
     * 将日期字符串 格式化 成 'yyyy-MM-dd HH:mm:ss' 日期格式的字符串
     *
     * @param target 日期字符串
     * @return 'yyyy-MM-dd HH:mm:ss' 日期格式的字符串
     */
    public static String dateFormat(String target) {
        return dateFormat(target, FORMAT);
    }

    /**
     * 将日期字符串 格式化 成 指定的日期格式字符串
     * 在格式化的过程中，不希望系统报错，因此格式化的结果存在以下两种情况：
     * <pre>
     *     1.格式化成功，返回格式化后的字符串
     *     2.格式化失败，返回一个空字符串
     * </pre>
     *
     * @param target 日期字符串
     * @param format 指定的日期格式
     * @return 指定的日期格式字符串
     */
    public static String dateFormat(String target, String format) {
        //此方法由于两种参数类型相同，考虑到使用时可能传入的参数顺序不对，因此对这种情况进行兼容
        if (TextUtils.hasNoNumber(target)) {
            String temp = target;
            target = format;
            format = temp;
        }

        //如果需要格式化的字符串为空或者格式化的日期格式为空，直接返回一个空字符串
        if (TextUtils.isEmpty(target) || TextUtils.isEmpty(format))
            return "";

        //格式化思路是：先将日期字符串转换成日期，然后再对此日期进行格式化
        Date date = convert(target);

        //为了提高系统效率，这里对转换后的值进行校验，如果为空，直接返回一个空字符串
        if (null == date)
            return "";

        //不为空，对转换后的日期进行格式化
        return dateFormat(date, format);
    }

    /**
     * 将{@link Object}对象格式化成指定的{@code format}日期格式字符串，
     * <p>
     * 在格式化的过程中，不希望系统报错，因此格式化的结果存在以下两种情况：
     * <pre>
     *     1.格式化成功，返回格式化后的字符串
     *     2.格式化失败，返回一个空字符串
     * </pre>
     *
     * @param date   日期对象
     * @param format 指定的日期格式
     * @return 指定的日期格式字符串
     */
    public static String dateFormat(Object date, String format) {
        if (!(date instanceof Date)) {
            return "";
        }

        DateFormat dateFormat = getDateFormat(format);
        if (null == dateFormat) {
            return "";
        }

        return dateFormat.format(date);
    }

    /**
     * 比较两个日期的大小， 将两个日期都转换成'yyyy-MM-dd HH:mm:ss''格式后，再进行日期大小的比较
     * <p>
     * 在做项目中，发现比较两个日期大小过程中，这两个日期可能为空的情况，比如以下参数中{@code current}值可能为空，
     * 这样在比较过程中就会报空指针异常，这常常不是想要的结果, 因此在比较函数中对此做了以下处理：
     * <pre>
     *    如果所比较的前后两个对象都为空，则认为比较结果为相等;
     *    如果前项不为空而后项为空，则比较结果 > 1;
     *    如果前项为空而后项不为空，则比较结果 < 1;
     *    如果前后两项都不为空，则查看{@link Date#compareTo(Date)}函数
     * </pre>
     *
     * @param current 比较的前项
     * @param target  比较的后项
     * @return 0 ： 前项等于后项； > 0 :  前项大于后项； < 0 : 前项小于后项
     */
    public static int compare(String current, String target) {
        return compare(current, target, HIGH_PRECISION_FORMAT);
    }

    /**
     * 比较两个日期的大小， 将两个日期都转换成指定格式后，再进行日期大小的比较
     * <p>
     * 在做项目中，发现比较两个日期大小过程中，这两个日期有可能为空的情况，比如以下参数中{@code current}值可能为空，
     * 这样在比较过程中就会报空指针异常，这常常不是想要的结果, 因此在比较函数中对此做了以下处理：
     * <pre>
     *    如果所比较的前后两个对象都为空，则认为比较结果为相等;
     *    如果前项不为空而后项为空，则比较结果 > 1;
     *    如果前项为空而后项不为空，则比较结果 < 1;
     *    如果前后两项都不为空，则查看{@link Date#compareTo(Date)}函数
     * </pre>
     *
     * @param current 比较的前项
     * @param target  比较的后项
     * @param format  日期格式
     * @return 0 ： 前项等于后项； > 0 :  前项大于后项； < 0 : 前项小于后项
     */
    public static int compare(String current, String target, String format) {
        if (analyze(current).equals(analyze(target))) {
            return current.compareTo(target);
        }
        return compare(convert(current), convert(target), format);
    }

    /**
     * 比较两个日期的大小
     *
     * <p>
     * 在做项目中，发现比较两个日期大小过程中，这两个日期可能为空的情况，比如以下参数中{@code current}值可能为空，
     * 这样在比较过程中就会报空指针异常，这常常不是想要的结果, 因此在比较函数中对此做了以下处理：
     * <pre>
     *    如果所比较的前后两个对象都为空，则认为比较结果为相等;
     *    如果前项不为空而后项为空，则比较结果 > 1;
     *    如果前项为空而后项不为空，则比较结果 < 1;
     *    如果前后两项都不为空，则查看{@link Date#compareTo(Date)}函数
     * </pre>
     *
     * @param current 日期
     * @param target  日期
     * @return -1： current < target; 0 : current = target; 1 : current > target;
     * 建议使用 Date中的 after、equals和before来比较日期大小 author Dale at 2018年7月2日
     */
    public static int compare(Date current, Date target) {
        return Comparator.nullsFirst(Date::compareTo).compare(current, target);
    }

    /**
     * 比较两个日期的大小
     * <p>
     * 在做项目中，发现比较两个日期大小过程中，这两个日期可能为空的情况，比如以下参数中{@code current}值可能为空，
     * 这样在比较过程中就会报空指针异常，这常常不是想要的结果, 因此在比较函数中对此做了以下处理：
     * <pre>
     *    如果所比较的前后两个对象都为空，则认为比较结果为相等;
     *    如果前项不为空而后项为空，则比较结果 > 1;
     *    如果前项为空而后项不为空，则比较结果 < 1;
     *    如果前后两项都不为空，则查看{@link Date#compareTo(Date)}函数
     * </pre>
     *
     * @param current 日期
     * @param target  日期
     * @return -1： current < target; 0 : current = target; 1 : current > target;
     * 建议使用 Date中的 after、equals和before来比较日期大小 author Dale at 2018年7月2日
     */
    public static int compare(Date current, Date target, String format) {
        if (null == current) {
            return null == target ? 0 : -1;
        }

        if (null == target) {
            return 1;
        }

        DateFormat dateFormat = getDateFormat(format);
        if (null == dateFormat)
            throw new IllegalArgumentException("The parameter format[" + format + "] could not cast to bu An standard date format string!");

        return convert(dateFormat.format(current)).compareTo(convert(dateFormat.format(target)));
    }

    /**
     * 根据指点的日期格式获取一个{@link DateFormat}对象
     *
     * @param format 指定日期格式
     * @return DateFormat 对象
     */
    private static DateFormat getDateFormat(String format) {
        if (TextUtils.isBlack(format)) {
            return null;
        }

        if (TextUtils.hasNumber(format)) {
            format = analyze(format);
            if (TextUtils.isBlack(format)) {
                throw new NullPointerException("解析字符串【" + format + "】失败！");
            }
        }

        return new SimpleDateFormat(format, Locale.CHINA);
    }

    /**
     * 解析指定的日期格式并转换转化成规范的日期格式，比如{@link #HIGH_PRECISION_FORMAT} 和 {@link #FORMAT}
     * <p>
     * 日期格式种类众多，这里尽可能的转换成以下几种日期格式：
     * <pre>
     *      1.年月日：
     *          yyyy-MM-dd、yyyy/MM/dd、yyyy年MM月dd日
     *      2.时分秒：
     *          HH:mm:ss、 HH时mm分ss秒
     *      排列组合后可以得到6种，但实际上可能还不止以上这6种情况，比如： '2月' 可以格式化成 '02月',也可以格式化成'2月'，
     *      另外时、分、秒也存在这种情况，这时格式化时可能需要根据实际情况来确定，
     *  </pre>
     *
     * @param format 指定的日期格式
     * @return 比较规范的日期格式
     */
    static String analyze(String format) {
        //如果给定的日期格式为空，则直接返回一个空字符串
        if (TextUtils.isEmpty(format))
            return "";

        StringBuilder separatorBuilder = new StringBuilder(), formatBuilder = new StringBuilder();
        for (char chr : format.toCharArray()) {
            if (CharacterUtil.isNumber(chr) || CharacterUtil.isLetter(chr)) {
                separatorBuilder.append('_');
                formatBuilder.append(chr);
            } else if (32 == chr) {
                separatorBuilder.append(' ');
                formatBuilder.append(' ');
            } else {
                separatorBuilder.append(chr);
                formatBuilder.append('_');
            }
        }
        /**
         * 不管是实际情况的日期格式(比如: '2019-3-21 12:3:25')，还是标准的日期格式(比如：yyyy-MM-dd HH:mm:ss)
         * '年月日'和'时分秒'之间存在一个空格， 可以同个此空格符将'年月日'和'时分秒'进行分割成两部分
         */
        String separator = separatorBuilder.toString();

        //在解析'指点的日期格式'之前，可以对'指点的日期格式'进行校验，如果满足日期格式，则允许进行下面的解析，否则直接返回一个空的字符串
        if (isNotDateFormat(separator, formatBuilder.toString())) {
            return "";
        }

        char[] array = {'y', 'M', 'd', ' ', 'H', 'm', 's'};
        StringBuilder buffer = new StringBuilder();
        for (int i = 0, index = 0; i < separator.length(); i++) {
            char c = separator.charAt(i);
            if (c == '_') {
                buffer.append(array[index]);
            } else {
                buffer.append(c);
                index++;
            }
        }
        return buffer.toString();
    }

    private static boolean isNotDateFormat(String separator, String format) {
        return !isDateFormat(separator, format);
    }

    private static boolean isDateFormat(String separator, String format) {
        String[] separators = separator.split(" ");
        String[] formats = format.split(" ");

        String tempSeparators = separators[0].trim();
        String dateFormat = formats[0].trim();
        String timeFormat = null;
        if (separators.length > 1) {
            timeFormat = formats[1].trim();
        }

        boolean isChinese = "\u5e74\u6708\u65e5".equals(tempSeparators.replace("_", ""));
        boolean isStandard = tempSeparators.replace("_", "").matches("^-{2}|/{2}");

        String numberRegex = "^\\d{4}_\\d{1,2}_\\d{1,2}";
        String formatRegex = "y{4}_M{1,2}_d{1,2}";
        if (TextUtils.hasChinese(separator)) {
            numberRegex += "_";
            formatRegex += "_";
        }
        boolean isNumber = dateFormat.matches(numberRegex);
        boolean isFormat = dateFormat.matches(formatRegex);

        if (TextUtils.isNotEmpty(timeFormat)) {
            tempSeparators = separators[1].trim();
            isChinese &= tempSeparators.replace("_", "").matches("^:{0,2}|\u65f6|\u65f6\u5206|\u65f6\u5206\u79d2");
            numberRegex = "^(\\d{1,2}|\\d{1,2}_\\d{1,2}|\\d{1,2}_\\d{1,2}_\\d{1,2})";
            formatRegex = "(H{1,2}|H{1,2}_m{0,2}|H{1,2}_m{0,2}_s{0,2})";
            if (TextUtils.hasChinese(tempSeparators)) {
                numberRegex += "_";
                formatRegex += "_";
            }
            isNumber &= timeFormat.matches(numberRegex);
            isFormat &= timeFormat.matches(formatRegex);
        }

        return (isChinese || isStandard) && (isNumber || isFormat);
    }

    public static Date getWorkingDay(Date date) {
        return getWorkingDay(date, 0);
    }

    public static Date getWorkingDay(Date date, int nextTo) {
        Function<String, Object> propertyFunction = FileUtils.getPropertyFunction("");
        Object service = propertyFunction.apply("WorkDayService");
        if (service == null) {
            return getWorkingDay(new BaseWorkDay(), date, nextTo);
        }
        return getWorkingDay(new BaseWorkDay(), date, nextTo);
    }

    public static Date getWorkingDay(IWorkDay workDay, Date date, int nextTo) {
        if (workDay.isWorkingDay(date)) {
            if (nextTo == 0) {
                return date;
            }
            nextTo--;
        }
        return getWorkingDay(workDay, nextDate(date), nextTo);
    }

    public static Date nextDate(Date date) {
        return nextDate(date, 1);
    }

    public static Date nextDate(Date date, int nextTo) {
        if (null == date) {
            throw new NullPointerException("Target Date is Null");
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, nextTo);
        return calendar.getTime();
    }
}

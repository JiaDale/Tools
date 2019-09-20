package com.jdy.util;

/**
 * Description: 字符工具类
 * Created by Administrator on 2019/9/16 23:18
 */
public class CharacterUtil {
    public static final char SLASH = '/';
    public static final char BACKSLASH = '\\';

    /**
     * 是否为ASCII字符，ASCII字符位于0~127之间
     *
     * <pre>
     *   CharUtil.isAscii('a')  = true
     *   CharUtil.isAscii('A')  = true
     *   CharUtil.isAscii('3')  = true
     *   CharUtil.isAscii('-')  = true
     *   CharUtil.isAscii('\n') = true
     *   CharUtil.isAscii('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符处
     * @return true表示为ASCII字符，ASCII字符位于0~127之间
     */
    public static boolean isAscii(final char ch) {
        return ch < 128;
    }

    /**
     * 是否为可见ASCII字符，可见字符位于32~126之间
     *
     * <pre>
     *   CharUtil.isAsciiPrintable('a')  = true
     *   CharUtil.isAsciiPrintable('A')  = true
     *   CharUtil.isAsciiPrintable('3')  = true
     *   CharUtil.isAsciiPrintable('-')  = true
     *   CharUtil.isAsciiPrintable('\n') = false
     *   CharUtil.isAsciiPrintable('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符处
     * @return true表示为ASCII可见字符，可见字符位于32~126之间
     */
    public static boolean isAsciiPrintable(final char ch) {
        return ch > 31 && ch < 127;
    }

    /**
     * 是否为ASCII控制符（不可见字符），控制符位于0~31和127
     *
     * <pre>
     *   CharUtil.isAsciiControl('a')  = false
     *   CharUtil.isAsciiControl('A')  = false
     *   CharUtil.isAsciiControl('3')  = false
     *   CharUtil.isAsciiControl('-')  = false
     *   CharUtil.isAsciiControl('\n') = true
     *   CharUtil.isAsciiControl('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为控制符，控制符位于0~31和127
     */
    public static boolean isAsciiControl(final char ch) {
        return ch < 32 || ch == 127;
    }

    /**
     * 检测字符是否是英文字符 即(A-Z或 a-z)
     *
     * @param chr 需检测的字符
     * @return true： 是英文字符； false ：不是英文字符
     */
    public static boolean isLetter(final char chr) {
        return isLowerLetter(chr) || isUpperLetter(chr);
    }

    public static boolean isNotLetter(final char chr) {
        return !isLetter(chr);
    }

    /**
     * 检测字符{@code chr}是否是中文
     *
     * @param chr 需检测的字符
     * @return true：chr字符是中文；false chr字符不是中文
     */
    public static boolean isChinese(final char chr) {
        return !isNotChinese(chr);
    }

    /**
     * 检测字符{@code chr}是否不是中文
     *
     * @param chr 需检测的字符
     * @return true：chr字符不是中文；false chr字符是中文
     */
    public static boolean isNotChinese(final char chr) {
        return chr < 0x4E00 || chr > 0x9FA5;// 根据字节码判断
    }

    /**
     * 检测字符是否是小写英文字母 即(a-z)
     *
     * @param chr 需检测的字符
     * @return true ： 是小写英文字母； false ： 不是小写英文字母
     */
    public static boolean isLowerLetter(final char chr) {
        return !isNotLowerLetter(chr);
    }

    public static boolean isNotLowerLetter(final char chr) {
        return chr < 97 || chr > 122;
    }

    /**
     * 检测字符是否是大写英文字符 即(A-Z)
     *
     * @param chr 需检测的字符
     * @return true ： 是大写的英文字符； false ： 不是大写的英文字符
     */
    private static boolean isUpperLetter(final char chr) {
        return !isNotUpperLetter(chr);
    }

    public static boolean isNotUpperLetter(final char chr) {
        return chr < 65 || chr > 90;
    }

    /**
     * 检查字符是否是数字
     *
     * @param chr 需要检测的字符
     * @return true: cha 是数字； false： chr不是数字
     */
    public static boolean isNumber(final char chr) {
        return !isNotNumber(chr);
    }

    public static boolean isNotNumber(final char chr) {
        return chr < 48 || chr > 57;
    }

    /**
     * 判断是否为emoji表情符<br>
     *
     * @param c 字符
     * @return 是否为emoji
     */
    public static boolean isEmoji(final char c) {
        return !(c == 0x0 || c == 0x9 || c == 0xA || c == 0xD || c >= 0x20 && c <= 0xD7FF || c >= 0xE000 && c <= 0xFFFD);
    }

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param c 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    public static boolean isFileSeparator(final char c) {
        return SLASH == c || BACKSLASH == c;
    }


    /**
     * 是否为16进制规范的字符，判断是否为如下字符
     * <pre>
     * 1. 0~9
     * 2. a~f
     * 4. A~F
     * </pre>
     *
     * @param c 字符
     * @return 是否为16进制规范的字符
     * @since 4.1.5
     */
    public static boolean isHexChar(char c) {
        return isNumber(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    /**
     * 是否为字符或数字，包括A~Z、a~z、0~9
     *
     * <pre>
     *   CharUtil.isLetterOrNumber('a')  = true
     *   CharUtil.isLetterOrNumber('A')  = true
     *   CharUtil.isLetterOrNumber('3')  = true
     *   CharUtil.isLetterOrNumber('-')  = false
     *   CharUtil.isLetterOrNumber('\n') = false
     *   CharUtil.isLetterOrNumber('&copy;') = false
     * </pre>
     *
     * @param ch 被检查的字符
     * @return true表示为字符或数字，包括A~Z、a~z、0~9
     */
    public static boolean isLetterOrNumber(final char ch) {
        return isLetter(ch) || isNumber(ch);
    }

    /**
     * 给定类名是否为字符类，字符类包括：
     *
     * <pre>
     * Character.class
     * char.class
     * </pre>
     *
     * @param clazz 被检查的类
     * @return true表示为字符类
     */
    public static boolean isCharClass(Class<?> clazz) {
        return clazz == Character.class || clazz == char.class;
    }

    /**
     * 给定对象对应的类是否为字符类，字符类包括：
     *
     * <pre>
     * Character.class
     * char.class
     * </pre>
     *
     * @param value 被检查的对象
     * @return true表示为字符类
     */
    public static boolean isChar(Object value) {
        return value instanceof Character || value.getClass() == char.class;
    }


    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == '\ufeff' || c == '\u202a';
    }
}

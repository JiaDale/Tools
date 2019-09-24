package com.jdy.spring;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 23:18
 */
public class View {

    public final String DEFAULT_CONTENT_TYPE = "text/html;charset=UTF-8";

    private final File viewFile;

    public View(File viewFile) {
        this.viewFile = viewFile;
    }


    public void render(Map<String, Object> model, HttpServletResponse response) throws Exception {
        Appendable appendable = new StringBuilder();
        RandomAccessFile accessFile = new RandomAccessFile(viewFile, "r");
        String line;
        while (null != (line = accessFile.readLine())) {
            line = new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile("\uffe5\\{[^\\}]+\\}",Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String paramName = matcher.group();
                paramName = paramName.replaceAll("￥\\{|\\}", "");
                Object paramValue = model.get(paramName);
                if (Objects.isNull(paramValue)) {
                    continue;
                }
                line = matcher.replaceFirst(makeStringForRegExp(paramValue.toString()));
                matcher = pattern.matcher(line);
            }
            appendable.append(line);
        }

        //让浏览器用utf-8来解析返回的数据
        response.setHeader("Content-type", DEFAULT_CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(appendable.toString());
    }


    //处理特殊字符
    public static String makeStringForRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }
}

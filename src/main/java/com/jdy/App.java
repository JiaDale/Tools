package com.jdy;

import java.util.regex.Pattern;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 16:55
 */
public class App {
    public static void main(String[] args) throws Exception {

        String[] FILE_TYPE = {".html", ".xhtml"};

        String html = "/web/query.json";

        String regex = "/web/add.*.json";

        Pattern compile = Pattern.compile(regex);




        System.out.println(compile.matcher(html).matches());


    }

}

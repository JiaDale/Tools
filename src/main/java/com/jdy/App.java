package com.jdy;

import com.jdy.account.AccountEntity;
import com.jdy.entity.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/21 16:55
 */
public class App {
    public static void main(String[] args) throws Exception {

        Map<String, Object> map = new HashMap<>();

        BaseEntity entity = new BaseEntity();

        AccountEntity accountEntity = new AccountEntity();


        System.out.println(accountEntity.getClass().isAssignableFrom(entity.getClass()));

//
//
//
//        Method[] methods = accountEntity.getClass().getMethods();
//
//
//        for (Method method : methods) {
//            System.out.println(method.getName());
//        }



//



//        String[] FILE_TYPE = {".html", ".xhtml"};
//
//        String html = "/web/query.json";
//
//        String regex = "/web/add.*.json";
//
//        Pattern compile = Pattern.compile(regex);




//        System.out.println(accountEntity);


    }

}

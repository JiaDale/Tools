package com.jdy;


import com.jdy.database.BaseExecuteQuery;
import com.jdy.entity.BaseEntity;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {

//        Team team = new Team();
//
//        team.dance();
//
//        team.sing();
//
//        team.display();

//
//        BaseEntity entity = new AccountEntity();
//        entity.setRowGuid(UUIDUtils.randomUUID());
//        entity.set("UserAge", 22);
//        entity.set("UserName", "Dale");
//        entity.set("CreatedTime", new Date());
//        entity.set("CreateAddress", "中国江苏省");
//        entity.set("Password", "123456");
//
//        System.out.println(entity.hasChanged());
//        System.out.println(entity.getChangeData());



        BaseExecuteQuery service = new BaseExecuteQuery();
//        service.insert(entity);


//        service.query()


        String sql = "select * from Frame_User";

        List<BaseEntity> list = service.select(sql);

        System.out.println(list);


//
//        Attribute attribute = new DeleteEntity(entity);


//        Entity record = new Entity();
//        record.set("AbcD", 18);
//        record.set("CC", null);
//        Entity record1 = new Entity();
//        record1.set("abcd", 18);
//        record1.set("DD", null);
//
//        Entity record2 = record;
//
//
//        System.out.println(record.equals(record1));


//        int execute = JDBCUtil.execute(JDBCUtil.getDataSource(), attribute.generateSQL(), attribute.getColumnValues());
//
//        System.out.println(execute);


//        Map<String, Object> map = AnnotateUtils.getAnnotationValueMap(entity.getClass(), Table.class);
//
//        System.out.println(map);

//        Set<Object> values =

//        for (String value: values) {
//            System.out.println(value);
//        }

//        System.out.println(values);

//        Log.error("11111111111111111");

//        String  text = "A=2, B=3, C=4 ";
//
//        String[] strings = ArrayUtil.replaceElements(text.split(","), "E", 3);
//        System.out.println(String.join(",", strings));

    }
}

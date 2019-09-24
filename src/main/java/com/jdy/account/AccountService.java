package com.jdy.account;

import com.jdy.entity.BaseEntity;
import com.jdy.net.Response;


class AccountService {

    public Response login(String userName, String password) {
      /*  String sql = "select * from Frame_User where UserName = ? ";
        List<BaseEntity> list = service.select(sql, BaseEntity.class, userName);
        if (null == list || list.isEmpty()) {
            return new Response(false, "此账号系统中不存在， 请先注册！");
        }

        for (BaseEntity entity : list) {
            if (password.equals(entity.getStr("Password"))) {
                return new Response(true, entity.getDataMap());
            }
        }*/

        return Response.create(false,  501, "密码错误！",  null);
    }

    public Response register(BaseEntity entity){
//        com.jdy.database.BaseExecuteQuery
//
//        String sql = DatabaseUtil.generateInsertSQL(entity);
//        if (TextUtils.isEmpty(sql)){
//            /*记录下日志*/
//            return new Response(false, "系统错误！");
//        }
//
//        if (service.execute(sql, entity)){
//            return new Response(true, "注册成功！");
//        }

        return Response.create(false,  501, "密码错误！",  null);
    }

    public Response update(BaseEntity entity){
//        String sql = DatabaseUtil.generateUpdateSQL(entity);
//        if (TextUtils.isEmpty(sql)){
//            /*记录下日志*/
//            return new Response(false, "系统错误！");
//        }
//
//        if (service.execute(sql, entity)){
//            return new Response(true, "修改成功！");
//        }

        return Response.create(false,  501, "密码错误！",  null);
    }

    public Response delete(BaseEntity entity){
//        String sql = DatabaseUtil.generateDeleteSQL(entity);
//        if (TextUtils.isEmpty(sql)){
//            /*记录下日志*/
//            return new Response(false, "系统错误！");
//        }
//
//        if (service.execute(sql, entity)){
//            return new Response(true, "修改成功！");
//        }

        return Response.create(false,  501, "密码错误！",  null);
    }
}

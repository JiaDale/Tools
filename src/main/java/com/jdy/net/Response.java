package com.jdy.net;

import com.jdy.entity.Entity;
import com.jdy.log.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface Response extends Entity {

    public final static String STATUS = "status";
    public final static String CODE = "code";
    public final static String MSG = "msg";
    public final static String DATA = "Data";

    boolean isSucceed();

    int getCode();

    String getMessage();


    static Response create(boolean status, int code, String msg, Map<String, Object> dataMap) {
        if (status) {
            return new SucceedResponse(status, code, dataMap);
        }
        return new FailureResponse(code, msg);
    }

    static Response create(Object data) {
        if (Objects.isNull(data)) {
            return create(false, 400, "");
        }

        if (data instanceof String) {
            String msg = data.toString();
            int code = valueOf(msg);
            boolean status = code < 200 || code > 300;
            return create(!status, code, msg);
        }

        if (data instanceof Response){
            return (Response) data;
        }

        if (data instanceof Entity){
//            Entity entity = (Entity) data;
        }

        Log.error("数据%s无法转换成Response对象", data);
        return create(false, 400, "发生数据转换错误!");
    }

    static Response create(boolean b, int i, String s) {
        return create(b, i, s, new HashMap<>());
    }

    static int valueOf(String message) {
        switch (message) {
            case "404":
                return 404;
            default:
                return 500;
        }
    }

}

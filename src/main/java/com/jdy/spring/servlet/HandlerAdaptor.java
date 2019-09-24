package com.jdy.spring.servlet;

import com.jdy.net.Response;
import com.jdy.spring.annotation.RequestParam;
import com.jdy.util.ArrayUtil;
import com.jdy.util.DateUtil;
import com.jdy.util.TextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 17:12
 */
public class HandlerAdaptor {

    public boolean support(Object object) {
        return object instanceof HandlerMapping;
    }

    public Response handle(HttpServletRequest request, HttpServletResponse response, Object object) {
        if (!support(object)) {
            return null;
        }
        HandlerMapping handlerMapping = (HandlerMapping) object;

        Method method = handlerMapping.getMethod();

        //把方法的形参列表和request的参数列表所在顺序进行一一对应
        Map<String, Integer> paramIndexMapping = new HashMap<String, Integer>();

        //提取方法中加了注解的参数
        //把方法上的注解拿到，得到的是一个二维数组
        //因为一个参数可以有多个注解，而一个方法又有多个参数
//        AnnotateUtils.getAnnotationValueFromMethods()
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (ArrayUtil.isEmpty(parameterAnnotations)) {
            return null;
        }

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (!(annotation instanceof RequestParam)) continue;
                String value = ((RequestParam) annotation).value();
                if (TextUtils.isNotBlack(value)) {
                    paramIndexMapping.put(value, i);
                }
            }
        }

        //提取方法中的request和response参数
        Class<?>[] paramsTypes = method.getParameterTypes();
        for (int i = 0; i < paramsTypes.length; i++) {

            Class<?> type = paramsTypes[i];
            if (type == request.getClass() || type == response.getClass()) {
                paramIndexMapping.put(type.getName(), i);
            }
        }

        //获得方法的形参列表
        Map<String, String[]> params = request.getParameterMap();

        //实参列表
        Object[] paramValues = new Object[paramsTypes.length];

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "")
                    .replaceAll("\\s", ",");

            if (!paramIndexMapping.containsKey(param.getKey())) {
                continue;
            }

            int index = paramIndexMapping.get(param.getKey());

            paramValues[index] = caseStringValue(value, paramsTypes[index]);
        }

        if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }

        if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }


        Object result = null;
        try {
            result = method.invoke(handlerMapping.getController(), paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.create(result);
    }

    private Object caseStringValue(String value, Class<?> paramsType) {
        if (String.class == paramsType) return value;

        if (byte.class == paramsType || Byte.class == paramsType) return Byte.valueOf(value);

        if (short.class == paramsType || Short.class == paramsType) return Short.valueOf(value);

        if (int.class == paramsType || Integer.class == paramsType) return Integer.valueOf(value);

        if (long.class == paramsType || Long.class == paramsType) return Long.valueOf(value);

        if (float.class == paramsType || Float.class == paramsType) return Float.valueOf(value);

        if (double.class == paramsType || Double.class == paramsType) return Byte.valueOf(value);

        if (boolean.class == paramsType || Boolean.class == paramsType) return Boolean.valueOf(value);

        if (Date.class == paramsType) return DateUtil.convert(value);

        return null;
    }
}

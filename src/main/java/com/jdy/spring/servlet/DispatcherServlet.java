package com.jdy.spring.servlet;

import com.jdy.annotation.AnnotateUtils;
import com.jdy.io.FileUtils;
import com.jdy.io.PropertiesUtil;
import com.jdy.log.Log;
import com.jdy.net.Response;
import com.jdy.spring.View;
import com.jdy.spring.ViewResolver;
import com.jdy.spring.annotation.Controller;
import com.jdy.spring.annotation.RequestMapping;
import com.jdy.spring.context.ApplicationContext;
import com.jdy.spring.context.Context;
import com.jdy.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 常用工具包
 * <p>
 * [Description]
 * <p>
 * 创建人 Dale 时间 2019/9/22 16:37
 */
public class DispatcherServlet extends HttpServlet {

    private Collection<HandlerMapping> handlerMappings = new ArrayList<>();

    private Map<HandlerMapping, HandlerAdaptor> handlerAdaptorMap = new ConcurrentHashMap<>();

    private Collection<ViewResolver> viewResolvers = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        PropertiesUtil.get(Util.PROJECT_CONFIG, config.getServletContext().getContextPath());
        String LOCATION = "ContextLocationConfig";
        Context context = new ApplicationContext(config.getInitParameter(LOCATION));


//        config.getServletContext().get



        //多文件上传的组件
//        initMultipartResolver(context);
        //初始化本地语言环境
//        initLocaleResolver(context);
        //初始化模板处理器
//        initThemeResolver(context);

        //handlerMapping，必须实现
//        initHandlerMappings(context);
        //初始化参数适配器，必须实现
//        initHandlerAdapters(context);

        String[] definitionNames = context.getBeanDefinitionNames();
        if (ArrayUtil.isEmpty(definitionNames)) {
            throw new NullPointerException("没有可调用的接口！");
        }
        String regex, methodPath;
        try {
            for (String definitionName : definitionNames) {
                Object bean = context.getBean(definitionName);
                if (Objects.isNull(bean) || !bean.getClass().isAnnotationPresent(Controller.class)) continue;
                regex = AnnotateUtils.getAnnotationValue(bean.getClass(), RequestMapping.class);

                for (Method method : bean.getClass().getMethods()) {
                    //没有加RequestMapping注解的直接忽略
                    if (!method.isAnnotationPresent(RequestMapping.class)) continue;
                    //映射URL
                    methodPath = AnnotateUtils.getAnnotationValue(method, RequestMapping.class);

                    regex = ("/" + CheckUtil.checkValue(regex, "") + "/" + methodPath.replaceAll("\\*", ".*")).replaceAll("/+", "/");

                    HandlerMapping handlerMapping = new HandlerMapping(Pattern.compile(regex), bean, method);

                    handlerMappings.add(handlerMapping);

//                    handlerAdaptorMap.put(handler, new HandlerAdaptor());
                    Log.info("Mapped:  %s  --->  %s", regex, method.getName());

                    //初始化参数适配器
                    handlerAdaptorMap.put(handlerMapping, new HandlerAdaptor());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化异常拦截器
//        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
//        initRequestToViewNameTranslator(context);

        //初始化视图转换器，必须实现
        initViewResolvers(context);
        //参数缓存器
//        initFlashMapManager(context);
    }

    private void initViewResolvers(Context context) {
        Collection<File> templateFiles = FileUtils.scanFiles(File.class, context.getProperties().getProperty("templateRoot"));
        for (File templateFile : templateFiles) {
            viewResolvers.add(new ViewResolver(templateFile));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            processDispatchResult(response, Response.create("404"));
        }
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1、通过从request中拿到URL，去匹配一个HandlerMapping
        if (CollectionUtils.isEmpty(handlerMappings)) {
            throw new Exception("没有可用的网络接口连接！");
        }
        final String url = request.getRequestURI().replace(request.getContextPath(), "").replaceAll("/+", "/");
        HandlerMapping handlerMapping = handlerMappings.stream().filter(v -> v.match(url)).findAny().orElse(null);
        if (Objects.isNull(handlerMapping)) {
            processDispatchResult(response, Response.create("404"));
            return;
        }

        //2、准备调用前的参数
        HandlerAdaptor handlerAdaptor = handlerAdaptorMap.get(handlerMapping);
        if (Objects.isNull(handlerAdaptor) || !handlerAdaptor.support(handlerMapping)) {
            processDispatchResult(response, Response.create("404"));
            return;
        }

        //3、真正的调用方法,返回ModelAndView存储了要穿页面上值，和页面模板的名称,  这一步才是真正的输出
        processDispatchResult(response, handlerAdaptor.handle(request, response, handlerMapping));
    }


    private void processDispatchResult(HttpServletResponse response, Response res) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            if (Objects.isNull(res) || CollectionUtils.isEmpty(viewResolvers)) {
                writer.write(" Error 500! ");
            }

            //如果需要从模板中返回
            for (ViewResolver resolver : viewResolvers) {
                View view = resolver.resolveView(res.getMessage(), null);
                if (Objects.isNull(view)) continue;
                view.render(res.getDataMap(), response);
                return;
            }
            //否则直接返回
            writer.write(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(writer);
        }
    }
}

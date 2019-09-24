package com.jdy.io;

import java.io.File;

/**
 * Description: Tools
 * Created by Dale on 2019/9/21 14:12
 */
public class FileNameScanner extends FileScanner<String>{

//    private boolean isWeb = false;
//
//    private FileNameScanner(boolean isWeb) {
//        this.isWeb = isWeb;
//    }
//
//    public static Scanner<File, String> create(boolean isWeb) {
//        return new FileNameScanner(isWeb);
//    }

    @Override
    protected String convert(String packageName, File file) {
        return packageName + "." + file.getName();
    }

//    @Override
//    protected URL createURL(String classPath) {
//        if (TextUtils.isBlack(classPath)) {
//            return null;
//        }
//
//        URL url = ClassUtil.getClassLoader().getResource(classPath);
//
//        if (Objects.isNull(url)){
//            url =  this.getClass().getResource(classPath);
//        }
//
//        if (Objects.isNull(url)){
//            try {
//                url = new File(classPath).toURI().toURL();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }
////
////        if (isWeb) {
////            return ClassUtil.getClassLoader().getResource(classPath);
////        }
//        return url;
//    }
}

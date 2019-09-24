package com.jdy.io;


import com.jdy.functions.PredicateFunction;
import com.jdy.log.Log;
import com.jdy.util.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

/**
 * 文件工具类
 * <p>
 * Description: Tools
 * Created by Administrator on 2019/9/13 14:44
 */
public class FileUtils {

    /**
     * 类Unix路径分隔符
     */
    private static final char UNIX_SEPARATOR = CharacterUtil.SLASH;

    /**
     * Windows路径分隔符
     */
    private static final char WINDOWS_SEPARATOR = CharacterUtil.BACKSLASH;

    /**
     * Class文件扩展名
     */
    public static final String CLASS_EXT = ".class";

    /**
     * Jar文件扩展名
     */
    public static final String JAR_FILE_EXT = ".jar";

    /**
     * 在Jar中的路径jar的扩展名形式
     */
    public static final String JAR_PATH_EXT = ".jar!";

    /**
     * 收索文件最多可回滚到父目录的次数
     */
    private static final int FILE_SEARCH_LIMIT = 10;


    /**
     * 是否为Windows环境
     *
     * @return 是否为Windows环境
     * @since 3.0.9
     */
    public static boolean isWindows() {
        return WINDOWS_SEPARATOR == File.separatorChar;
    }


    /**
     * 列出目录文件<br>
     * 给定的绝对路径不能是压缩包中的路径
     *
     * @param path 目录绝对路径或者相对路径
     * @return 文件列表（包含目录）
     */
    public static File[] ls(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        File file = file(path);
        if (file.isDirectory()) {
            return file.listFiles();
        }

        throw new NullPointerException(String.format("Path [{}] is not directory!", path));
    }


    /**
     * 创建File对象，自动识别相对或绝对路径，相对路径将自动从ClassPath下寻找
     *
     * @param path 文件路径
     * @return File
     */
    public static File file(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        return new File(getAbsolutePath(path));
    }

    /**
     * 获取绝对路径，相对于ClassPath的目录<br>
     * 如果给定就是绝对路径，则返回原路径，原路径把所有\替换为/<br>
     * 兼容Spring风格的路径表示，例如：classpath:config/example.setting也会被识别后转换
     *
     * @param path 相对路径
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path) {
        return getAbsolutePath(path, null);
    }

    /**
     * 获取绝对路径<br>
     * 此方法不会判定给定路径是否有效（文件或目录存在）
     *
     * @param path      相对路径
     * @param baseClass 相对路径所相对的类
     * @return 绝对路径
     */
    public static String getAbsolutePath(String path, Class<?> baseClass) {
//        String normalPath;
//        if (TextUtils.isEmpty(path)) {
//            normalPath = TextUtils.EMPTY;
//        } else {
//            normalPath = normalize(path);
//            if (isAbsolutePath(normalPath)) {
//                // 给定的路径已经是绝对路径了
//                return normalPath;
//            }
//        }
//
//        // 相对于ClassPath路径
//        final URL url = ResourceUtil.getResource(normalPath, baseClass);
//        if (null != url) {
//            // 对于jar中文件包含file:前缀，需要去掉此类前缀，在此做标准化，since 3.0.8 解决中文或空格路径被编码的问题
//            return FileUtil.normalize(URLUtil.getDecodedPath(url));
//        }
//
//        // 如果资源不存在，则返回一个拼接的资源绝对路径
//        final String classPath = ClassUtil.getClassPath();
//        if (null == classPath) {
//            // throw new NullPointerException("ClassPath is null !");
//            // 在jar运行模式中，ClassPath有可能获取不到，此时返回原始相对路径（此时获取的文件为相对工作目录）
//            return path;
//        }

        // 资源不存在的情况下使用标准化路径有问题，使用原始路径拼接后标准化路径
//        return normalize(classPath.concat(path));

        return null;
    }

    /**
     * 修复路径<br>
     * 如果原路径尾部有分隔符，则保留为标准分隔符（/），否则不保留
     * <ol>
     * <li>1. 统一用 /</li>
     * <li>2. 多个 / 转换为一个 /</li>
     * <li>3. 去除两边空格</li>
     * <li>4. .. 和 . 转换为绝对路径，当..多于已有路径时，直接返回根路径</li>
     * </ol>
     * <p>
     * 栗子：
     *
     * <pre>
     * "/foo//" =》 "/foo/"
     * "/foo/./" =》 "/foo/"
     * "/foo/../bar" =》 "/bar"
     * "/foo/../bar/" =》 "/bar/"
     * "/foo/../bar/../baz" =》 "/baz"
     * "/../" =》 "/"
     * "foo/bar/.." =》 "foo"
     * "foo/../bar" =》 "bar"
     * "foo/../../bar" =》 "bar"
     * "//server/foo/../bar" =》 "/server/bar"
     * "//server/../bar" =》 "/bar"
     * "C:\\foo\\..\\bar" =》 "C:/bar"
     * "C:\\..\\bar" =》 "C:/bar"
     * "~/foo/../bar/" =》 "~/bar/"
     * "~/../bar" =》 "bar"
     * </pre>
     *
     * @param path 原路径
     * @return 修复后的路径
     */
    public static String normalize(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }


        // 兼容Spring风格的ClassPath路径，去除前缀，不区分大小写
        String pathToUse = TextUtils.removePrefixIgnoreCase(path, URLUtil.CLASSPATH_URL_PREFIX);

        // 去除file:前缀
        pathToUse = TextUtils.removePrefixIgnoreCase(pathToUse, URLUtil.FILE_URL_PREFIX);

        // 统一使用斜杠
        pathToUse = pathToUse.replaceAll("[/\\\\]{1,}", TextUtils.SLASH).trim();

        int prefixIndex = pathToUse.indexOf(TextUtils.COLON);

        String prefix = "";
        if (prefixIndex > -1) {
            // 可能Windows风格路径
            prefix = pathToUse.substring(0, prefixIndex + 1);
            if (prefix.startsWith(TextUtils.SLASH)) {
                // 去除类似于/C:这类路径开头的斜杠
                prefix = prefix.substring(1);
            }

            if (!prefix.contains(TextUtils.SLASH)) {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            } else {
                // 如果前缀中包含/,说明非Windows风格path
                prefix = TextUtils.EMPTY;
            }
        }

        if (pathToUse.startsWith(TextUtils.SLASH)) {
            prefix += TextUtils.SLASH;
            pathToUse = pathToUse.substring(1);
        }

//        Stream.of(pathToUse.split(TextUtils.SLASH)).filter(p -> {
//
//        });

//        StrUtil.split(pathToUse, StrUtil.C_SLASH);
        List<String> pathElements = new LinkedList<String>();
//        int tops = 0;
//
//        String element;
//        for (int i = pathList.size() - 1; i >= 0; i--) {
//            element = pathList.get(i);
//            if (TextUtils.DOT.equals(element)) {
//                // 当前目录，丢弃
//            } else if (TextUtils.DOUBLE_DOT.equals(element)) {
//                tops++;
//            } else {
//                if (tops > 0) {
//                    // 有上级目录标记时按照个数依次跳过
//                    tops--;
//                } else {
//                    // Normal path element found.
//                    pathElements.add(0, element);
//                }
//            }
//        }

//        return prefix + CollUtil.join(pathElements, StrUtil.SLASH);
        return null;
    }


//    public static URL searchFileURL(String filePath){
//        return searchFilePath(filePath).toUri().toURL();
//    }

    public static Path searchFilePath(String rootPath) {
        return searchFilePath(rootPath, null);
    }


    public static Path searchFilePath(String rootPath, Path path) {
        File rootFile = new File(rootPath);

        if (!rootFile.isAbsolute()) {//如果根路径不是绝对路径
            //将更路径转换成系统路径
            rootFile = new File(Objects.requireNonNull(ClassUtil.getClassLoader().getResource("")).getPath());
        }

        Path targetPath = CheckUtil.checkValue(path, Paths.get(rootPath));

        //这里需要注意,如果提供了path对象,请将path对象关联起来,否则将会直接寻找path中的文件
        path = searchFilePath(rootFile, targetPath, null);
        if (Objects.isNull(path)) {
            return searchParentFilePath(rootFile.getParentFile(), targetPath, rootFile, 0);
        }
        return path;
    }

    /**
     * 可能造成无限循环, 添加次数做一个限制
     *
     * @param parentFile 父目录
     * @param targetPath 寻找目标
     * @param rootFile   子目录
     * @return 目标文件地址
     */
    private static Path searchParentFilePath(File parentFile, Path targetPath, File rootFile, int limit) {
        Path path = searchFilePath(parentFile, targetPath, rootFile);
        if (Objects.isNull(path)) {
            if (limit > FILE_SEARCH_LIMIT){
                return null;
            }
            return searchParentFilePath(parentFile.getParentFile(), targetPath, parentFile, ++limit);
        }
        return path;
    }

    private static Path searchFilePath(File rootFile, Path targetPath, File hasSearch) {
        Path currentPath = rootFile.toPath();
        if (currentPath.endsWith(targetPath)) {//如果符合所要查找的目标文件,直接返回
            return currentPath;
        }

        if (!rootFile.isDirectory()) {//如果不是一个文件夹,直接返回
            return null;
        }

        File[] files = rootFile.listFiles();
        if (ArrayUtil.isEmpty(files)) {//如果是一个空的文件夹,直接返回
            return null;
        }

        for (File file : files) { //遍历文件夹
            if (file.equals(hasSearch)) continue;//已经查询过了,直接跳过
            currentPath = searchFilePath(file, targetPath, hasSearch); //以当前文件为根路径文件,查找目标文件
            if (Objects.nonNull(currentPath)) return currentPath; //如果找到了文件,直接返回
        }

        //如果这里目录下没有目标文件,
//        Log.warn("Can't find file named %s in %s package! ", targetPath, rootFile);
        return null;
    }


    /**
     * 获取.properties文件的函数对象
     *
     * @param fileName 文件名称
     * @return 函数对象
     */
    public static Function<String, Object> getPropertyFunction(String fileName) {
        Properties properties = loadFile(fileName, ClassLoader.getSystemClassLoader());
        if (properties == null) {
            return s -> null;
        }
        return properties::get;
    }

    public static Properties loadFile(String fileName, ClassLoader loader) {
        return loadFile(fileName, loader, false);
    }

    public static Properties loadFile(String fileName, ClassLoader loader, boolean isEnd) {
        try {
            Properties properties = new Properties();
            properties.load(Objects.requireNonNull(loader.getResourceAsStream(fileName)));
            return properties;
        } catch (Exception ex) {
            if (isEnd) {
                return null;
            }
            return loadFile(fileName, ClassUtil.getClassLoader(), true);
        }
    }


    public static Collection<String> scanFiles(final String... packageNames) {
        return scanFiles(String.class, packageNames);
    }


    public static <T> Collection<T> scanFiles(Class<T> type, final String... packageNames) {
        return scanFiles(type, null, packageNames);
    }

    /**
     * 获取指定文件夹下的所有文件名称
     *
     * @return 返回扫描的文件夹下的所有文件
     */
    public static <T> Collection<T> scanFiles(Class<T> type, PredicateFunction<File, T> predicate, final String... packageNames) {
        if (ArrayUtil.isEmpty(packageNames)) {
            Log.error("未指定扫描目录");
            return null;
        }

        Collection<T> files = null, temp;
        Scanner<File, T> scanner = FileScanner.create(type);
        try {
            for (String packageName : packageNames) {
                temp = scanner.scanFiles(packageName.replaceAll("\\.", "/"), predicate).getFiles();
                if (CollectionUtils.isEmpty(files))
                    files = temp;
                else files.addAll(temp);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return files;
    }

    public static File getFile(String fileName) {
        if (TextUtils.isEmpty(fileName)) return null;

        URL url = ClassUtil.getClassLoader().getResource(fileName);
        if (Objects.isNull(url)) {
            Log.warn("无法确认文件[%s]的地址", fileName);
            return null;
        }

        return new File(url.getFile());
    }

    public static File[] listFiles(String fileName) {
        File file = getFile(fileName);
        if (Objects.isNull(file)) return null;
        return file.listFiles();

    }
}


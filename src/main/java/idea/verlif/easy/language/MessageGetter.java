package idea.verlif.easy.language;

import idea.verlif.easy.language.config.GetterConfig;
import idea.verlif.easy.language.handler.MessageHandler;
import idea.verlif.easy.language.handler.impl.InitMessageHandler;
import idea.verlif.easy.language.resource.MessageResource;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/28 14:04
 */
public class MessageGetter {

    private static final String SPLIT = "_";
    private static final String END = ".properties";

    private final MessageHandler defaultHandler;
    private final Locale defaultLocale;

    private final GetterConfig config;

    /**
     * 使用系统推断的语言类型
     */
    public MessageGetter() {
        this.config = new GetterConfig();
        this.defaultLocale = Locale.getDefault();
        this.defaultHandler = new InitMessageHandler(config);
    }

    /**
     * 默认语言标识
     *
     * @param tag 语言标识
     */
    public MessageGetter(String tag) {
        this.config = new GetterConfig();
        this.defaultLocale = parserLocale(tag);
        this.defaultHandler = new InitMessageHandler(config);
    }

    /**
     * 默认语言地区
     *
     * @param defaultLocale 默认的语言
     */
    public MessageGetter(Locale defaultLocale) {
        this.config = new GetterConfig();
        this.defaultLocale = defaultLocale;
        this.defaultHandler = new InitMessageHandler(config);
    }

    /**
     * 获取配置
     */
    public GetterConfig getConfig() {
        return config;
    }

    /**
     * 添加资源文件
     *
     * @param path 资源文件或文件夹路径
     * @throws IOException 读取错误
     */
    public void addResource(String path) throws IOException {
        addResource(new File(path));
    }

    /**
     * 添加资源文件
     *
     * @param dir 资源文件或文件夹对象
     * @throws IOException 读取错误
     */
    public void addResource(File dir) throws IOException {
        File[] files = dir.listFiles(pathname -> pathname.isFile() && pathname.getName().endsWith(END));
        if (files != null) {
            for (File file : files) {
                handleResource(file);
            }
        } else {
            handleResource(dir);
        }
    }

    /**
     * 添加资源文件。当设定语言已存在时，进行替换与补充。
     *
     * @param path   资源文件或文件夹路径
     * @param locale 资源文件设定的语言。置为null则添加或替换到默认文本资源。
     * @throws IOException 读取错误
     */
    public void addResource(String path, Locale locale) throws IOException {
        addResource(new File(path), locale);
    }

    /**
     * 添加资源文件。当设定语言已存在时，进行替换与补充。
     *
     * @param file   资源文件对象
     * @param locale 资源文件设定的语言。置为null则添加或替换到默认文本资源。
     * @throws IOException 读取错误
     */
    public void addResource(File file, Locale locale) throws IOException {
        if (file.isDirectory()) {
            throw new IOException(file.getAbsolutePath() + " is not a file");
        }
        MessageResource resource = defaultHandler.getResource(locale);
        MessageResource mr = new MessageResource(locale);
        mr.loadFromFile(file);
        if (resource == null) {
            defaultHandler.add(mr);
        } else {
            resource.getProperties().putAll(mr.getProperties());
        }
    }

    /**
     * 添加资源文件
     *
     * @param path   资源文件或文件夹路径
     * @param filter 资源文件过滤器
     * @throws IOException 读取错误
     */
    public void addResource(String path, FileFilter filter) throws IOException {
        addResource(new File(path), filter);
    }

    /**
     * 添加资源文件
     *
     * @param dir    资源文件或文件夹对象
     * @param filter 资源文件过滤器
     * @throws IOException 读取错误
     */
    public void addResource(File dir, FileFilter filter) throws IOException {
        File[] files = dir.listFiles(filter);
        if (files != null) {
            for (File file : files) {
                handleResource(file);
            }
        } else {
            handleResource(dir);
        }
    }

    private void handleResource(File resource) throws IOException {
        if (resource.isFile()) {
            String filename = resource.getName();
            if (filename.length() > END.length()) {
                String[] parts = filename.substring(0, filename.length() - END.length()).split(SPLIT, 4);
                Locale locale;
                if (parts.length == 1) {
                    locale = null;
                } else if (parts.length == 2) {
                    locale = new Locale(parts[1]);
                } else {
                    locale = new Locale(parts[1], parts[2]);
                }
                MessageResource mr = new MessageResource(locale);
                mr.loadFromFile(resource);
                defaultHandler.add(mr);
            }
        }
    }

    /**
     * 获取默认语言的文本内容
     *
     * @param code 文本代码
     * @return 文本代码对应的默认语言内容
     */
    public String get(String code) {
        return defaultHandler.get(code, defaultLocale);
    }

    /**
     * 获取对应语言的文本内容。当对应语言中不存在文本代码时获取默认语言的文本，否则返回文本代码。
     *
     * @param code   文本代码
     * @param locale 语言
     * @return 文本代码对应的内容
     */
    public String get(String code, Locale locale) {
        return defaultHandler.get(code, locale);
    }

    /**
     * 获取对应语言的文本内容。当对应语言中不存在文本代码时获取默认语言的文本，否则返回文本代码。
     *
     * @param code 文本代码
     * @param tag  语言tag
     * @return 文本代码对应的内容
     */
    public String get(String code, String tag) {
        Locale locale = parserLocale(tag);
        return defaultHandler.get(code, locale);
    }

    /**
     * 获取当前可用的文本代码列表
     *
     * @return 文本代码列表
     */
    public Set<String> codeSet() {
        return defaultHandler.keys();
    }

    private Locale parserLocale(String tag) {
        String[] ss = tag.split(SPLIT, 3);
        if (ss.length == 1) {
            return new Locale(ss[0]);
        } else {
            return new Locale(ss[0], ss[1]);
        }
    }

}

package idea.verlif.easy.language.handler;

import idea.verlif.easy.language.resource.MessageResource;

import java.util.Locale;
import java.util.Set;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/28 14:15
 */
public interface MessageHandler {

    /**
     * 添加文本资源
     *
     * @param resource 文本资源对象
     */
    void add(MessageResource resource);

    /**
     * 获取code对应的文本信息
     *
     * @param code   文本代号
     * @param locale 语言地区
     * @return 文本信息
     */
    String get(String code, Locale locale);

    /**
     * 获取所有的文本代号
     *
     * @return 文本代号
     */
    Set<String> keys();

    /**
     * 移除文本
     *
     * @param code   文本代号
     * @param locale 语言地区
     */
    void remove(String code, Locale locale);

    /**
     * 替换文本
     *
     * @param code    文本代号
     * @param message 需要替换的内容
     * @param locale  语言地区
     */
    void replace(String code, String message, Locale locale);
}

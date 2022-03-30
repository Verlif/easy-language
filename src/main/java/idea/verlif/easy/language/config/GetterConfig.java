package idea.verlif.easy.language.config;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/30 10:23
 */
public class GetterConfig {

    private ResultType resultType;

    public GetterConfig() {
        resultType = ResultType.EASY;
    }

    public ResultType getResultType() {
        return resultType;
    }

    /**
     * 当获取文本内容时，若目标语言资源中没有此文本代码，控制返回值。
     *
     * @param resultType 取值模式
     */
    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    /**
     * 取值模式
     */
    public enum ResultType {

        /**
         * 严格模式，只在设定的语言资源中查询。
         * 设定的语言资源中不存在时，返回NULL。
         */
        HARD,

        /**
         * 兼容模式，设定的语言资源中不存在时，在默认语言资源中查询。
         * 当默认语言资源中也不存在时，返回文本代码。
         */
        EASY,

        /**
         * 空值模式，设定的语言资源中不存在时，在默认语言资源中查询。
         * 当默认语言资源中也不存在时，返回NULL。
         */
        WITH_NULL
    }
}

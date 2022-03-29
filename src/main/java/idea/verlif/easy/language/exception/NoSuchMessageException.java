package idea.verlif.easy.language.exception;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/28 10:57
 */
public class NoSuchMessageException extends RuntimeException {

    public NoSuchMessageException(String code) {
        super("No such message with code - " + code);
    }
}

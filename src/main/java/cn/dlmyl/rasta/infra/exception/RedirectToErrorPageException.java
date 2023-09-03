package cn.dlmyl.rasta.infra.exception;

/**
 * 重定向到错误页面异常
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public class RedirectToErrorPageException extends RuntimeException {

    public RedirectToErrorPageException(String message) {
        super(message);
    }

    public RedirectToErrorPageException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedirectToErrorPageException(Throwable cause) {
        super(cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}

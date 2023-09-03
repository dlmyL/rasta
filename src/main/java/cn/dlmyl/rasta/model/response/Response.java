package cn.dlmyl.rasta.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 公共响应
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -217225296893780836L;

    public static final Long SUCCESS = 200L;
    public static final Long ERROR = 500L;
    public static final Long BAD_REQUEST = 400L;

    private Long code;
    private String message;
    private T payload;

    public static <T> Response<T> succeed(T payload) {
        Response<T> response = new Response<>();
        response.setCode(SUCCESS);
        response.setPayload(payload);
        return response;
    }

}

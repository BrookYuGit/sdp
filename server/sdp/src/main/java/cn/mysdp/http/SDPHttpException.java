package cn.mysdp.http;

import lombok.Getter;

/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-10-30
 * Table:
 * Comment:
 */
@Getter
public class SDPHttpException extends RuntimeException{
    private final SDPHttpResult error;

    public SDPHttpException(SDPHttpResult error, String msg) {
        super(msg);
        this.error = error;
    }
}

package cn.sdp.http;

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
public enum SDPHttpResult {

    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),
    E2004(2004,"数据库插入异常");

    private int code;
    private String msg;

    SDPHttpResult(int c, String m) {
        code = c;
        msg = m;
    }

}

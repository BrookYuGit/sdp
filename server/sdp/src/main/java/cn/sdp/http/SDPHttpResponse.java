package cn.sdp.http;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-10-30
 * Table:
 * Comment:
 */
@Data
@ToString
public class SDPHttpResponse<T> implements Serializable {
    int code;
    String msg;
    T body;

    public SDPHttpResponse<T> code(int code) {
        this.code = code;
        return this;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder<T>{

        int code;
        String msg;
        T body;

        public Builder code(int code){
            this.code = code;
            return this;
        }

        public Builder msg(String msg){
            this.msg = msg;
            return this;
        }

        public Builder body(T body){
            this.body = body;
            return this;
        }

        public SDPHttpResponse build(){
            SDPHttpResponse SDPHttpResponse = new SDPHttpResponse();
            SDPHttpResponse.setCode(code);
            SDPHttpResponse.setMsg(msg);
            SDPHttpResponse.setBody(body);
            return SDPHttpResponse;
        }

    }
}

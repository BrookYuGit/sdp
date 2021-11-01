package cn.sdp.biz.dto.request;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-06-01
 * @Version:1.0
 * Table:
 * Comment:
 * 
 */
@Getter
@Setter
public class BaseRequest {
    @JsonProperty("query_options")
    @JSONField(name = "query_options")
    protected JSONObject queryOptions;

    @JsonProperty("page_size")
    @JSONField(name = "page_size")
    protected Integer pageSize;

    @JsonProperty("page_no")
    @JSONField(name = "page_no")
    protected Integer pageNo;

    @JsonProperty("sessionid")
    @JSONField(name = "sessionid")
    private String sessionid;

    @JsonProperty("client_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", name = "client_timestamp")
    private Date clientTimestamp;

    public void checkRequest() throws Exception {

    }

    @JSONField(serialize = false)
    public void checkNonNull(Object object, String msg) throws Exception {
        if (null == object) {
            throw new Exception("miss param:" + msg);
        }
    }
}
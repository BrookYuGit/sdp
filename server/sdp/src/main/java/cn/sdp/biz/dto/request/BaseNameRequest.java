package cn.sdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
public class BaseNameRequest extends BaseRequest {
    private static final long serialVersionUID = 33009742059561800L;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(name,"名称");
    }
}
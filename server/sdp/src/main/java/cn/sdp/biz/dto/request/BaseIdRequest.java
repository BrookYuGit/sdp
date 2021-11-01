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
public class BaseIdRequest extends BaseRequest {
    private static final long serialVersionUID = 33009742059561800L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private Integer id;

    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(id,"编号");
    }
}
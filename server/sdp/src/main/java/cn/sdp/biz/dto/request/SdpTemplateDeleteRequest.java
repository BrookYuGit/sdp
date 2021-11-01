package cn.sdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpTemplateDeleteRequest
 * @Description: DeleteRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpTemplateDeleteRequest extends BaseRequest {
    private static final long serialVersionUID = 87161731499495723L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private List<Integer> id;


    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(id, "id");
    }
}

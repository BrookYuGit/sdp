package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpProjectDeleteRequest
 * @Description: DeleteRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpProjectDeleteRequest extends BaseRequest {
    private static final long serialVersionUID = 21474270853935754L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private List<Integer> id;


    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(id, "id");
    }
}

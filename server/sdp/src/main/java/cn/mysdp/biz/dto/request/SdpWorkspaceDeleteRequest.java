package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceDeleteRequest
 * @Description: DeleteRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@Getter
@Setter
public class SdpWorkspaceDeleteRequest extends BaseRequest {
    private static final long serialVersionUID = 214742701563949774L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private List<Integer> id;


    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(id, "id");
    }
}

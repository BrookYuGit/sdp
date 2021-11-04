package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceConfigAddRequest
 * @Description: AddRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpWorkspaceConfigAddRequest extends BaseRequest {
    private static final long serialVersionUID = 8716173827780378L;

    @JsonProperty("workspace_name")
    @JSONField(name = "workspace_name")
    private String workspaceName;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @JsonProperty("value")
    @JSONField(name = "value")
    private String value;

    @JsonProperty("remark")
    @JSONField(name = "remark")
    private String remark;

    @JsonProperty("new_value")
    @JSONField(name = "new_value")
    private String newValue;

    @Override
    public void checkRequest() throws Exception {
    }
}

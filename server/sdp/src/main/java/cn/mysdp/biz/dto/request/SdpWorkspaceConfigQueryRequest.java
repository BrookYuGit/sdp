package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceConfigQueryRequest
 * @Description: QueryRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpWorkspaceConfigQueryRequest extends BaseRequest {
    private static final long serialVersionUID = 87161731055136479L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private Integer id;

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

    @Override
    public void checkRequest() throws Exception {
    }
}

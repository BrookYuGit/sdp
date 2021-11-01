package cn.sdp.biz.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceConfigQueryResponse
 * @Description: QueryResponse
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpWorkspaceConfigQueryResponse implements Serializable {
    private static final long serialVersionUID = 130326931597573647L;

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

}

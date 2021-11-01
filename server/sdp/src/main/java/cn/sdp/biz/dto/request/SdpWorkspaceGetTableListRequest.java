package cn.sdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceGetTableListRequest
 * @Description: SQLRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@Getter
@Setter
public class SdpWorkspaceGetTableListRequest extends BaseRequest {
    private static final long serialVersionUID = 8716173763906109L;

    public SdpWorkspaceGetTableListRequest() {
    }


    @JsonProperty("workspace_name")
    @JSONField(name = "workspace_name")
    private String workspaceName;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @JsonProperty("id_list")
    @JSONField(name = "id_list")
    private List<Integer> idList;

    @Override
    public void checkRequest() throws Exception {

    }
}

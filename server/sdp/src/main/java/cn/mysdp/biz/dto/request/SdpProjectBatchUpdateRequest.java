package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpProjectBatchUpdateRequest
 * @Description: UpdateRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpProjectBatchUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 87161732015065094L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private Integer id;

    @JsonProperty("workspace_name")
    @JSONField(name = "workspace_name")
    private String workspaceName;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @JsonProperty("root_path")
    @JSONField(name = "root_path")
    private String rootPath;

    @JsonProperty("tables")
    @JSONField(name = "tables")
    private String tables;

    @JsonProperty("remark")
    @JSONField(name = "remark")
    private String remark;

    @JsonProperty("ids")
    @JSONField(name = "ids")
    private List<Integer> ids;

    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(ids, "id");
    }
}

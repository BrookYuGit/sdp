package cn.mysdp.biz.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpProjectQueryResponse
 * @Description: QueryResponse
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpProjectQueryResponse implements Serializable {
    private static final long serialVersionUID = 209630341420489039L;

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

    @JsonProperty("extra_info")
    @JSONField(name = "extra_info")
    private String extraInfo;

    @JsonProperty("property_map")
    @JSONField(name = "property_map")
    private Map<String, String> propertyMap;

}

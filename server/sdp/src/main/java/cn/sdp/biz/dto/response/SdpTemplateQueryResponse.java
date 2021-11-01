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
 * @ClassName: SdpTemplateQueryResponse
 * @Description: QueryResponse
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpTemplateQueryResponse implements Serializable {
    private static final long serialVersionUID = 130326932066049008L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private Integer id;

    @JsonProperty("workspace_name")
    @JSONField(name = "workspace_name")
    private String workspaceName;

    @JsonProperty("project_name")
    @JSONField(name = "project_name")
    private String projectName;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @JsonProperty("file_type")
    @JSONField(name = "file_type")
    private String fileType;

    @JsonProperty("project")
    @JSONField(name = "project")
    private String project;

    @JsonProperty("package_name")
    @JSONField(name = "package_name")
    private String packageName;

    @JsonProperty("no_overwrite")
    @JSONField(name = "no_overwrite")
    private Integer noOverwrite;

    @JsonProperty("remark")
    @JSONField(name = "remark")
    private String remark;

    @JsonProperty("file_template")
    @JSONField(name = "file_template")
    private String fileTemplate;

    @JsonProperty("extra_info")
    @JSONField(name = "extra_info")
    private String extraInfo;

    @JsonProperty("alias_table_name")
    @JSONField(name = "alias_table_name")
    private String aliasTableName;

    @JsonProperty("dyn_project")
    @JSONField(name = "dyn_project")
    private SdpProjectQueryResponse dynProject;

    @JsonProperty("extra_info_map")
    @JSONField(name = "extra_info_map")
    private Map<String, Object> extraInfoMap;

}

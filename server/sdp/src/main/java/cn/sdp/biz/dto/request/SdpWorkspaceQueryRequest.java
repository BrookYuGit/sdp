package cn.sdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceQueryRequest
 * @Description: QueryRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@Getter
@Setter
public class SdpWorkspaceQueryRequest extends BaseRequest {
    private static final long serialVersionUID = 8716173205565693L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private Integer id;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @JsonProperty("root_path")
    @JSONField(name = "root_path")
    private String rootPath;

    @JsonProperty("db_host")
    @JSONField(name = "db_host")
    private String dbHost;

    @JsonProperty("db_port")
    @JSONField(name = "db_port")
    private Integer dbPort;

    @JsonProperty("db_database")
    @JSONField(name = "db_database")
    private String dbDatabase;

    @JsonProperty("db_username")
    @JSONField(name = "db_username")
    private String dbUsername;

    @JsonProperty("db_password")
    @JSONField(name = "db_password")
    private String dbPassword;

    @JsonProperty("db_classname")
    @JSONField(name = "db_classname")
    private String dbClassname;

    @JsonProperty("remark")
    @JSONField(name = "remark")
    private String remark;

    @Override
    public void checkRequest() throws Exception {
    }
}

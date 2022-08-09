package cn.mysdp.biz.dto.request;

import cn.mysdp.biz.dto.request.BaseRequest;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceBatchUpdateRequest
 * @Description: UpdateRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@Getter
@Setter
public class SdpWorkspaceBatchUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 21474270353662398L;

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

    @JsonProperty("extra_info")
    @JSONField(name = "extra_info")
    private String extraInfo;

    @JsonProperty("ids")
    @JSONField(name = "ids")
    private List<Integer> ids;

    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(ids, "id");
    }
}

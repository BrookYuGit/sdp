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
 * @ClassName: SdpWorkspaceGetDbConfigResponse
 * @Description: SQLResponse
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@Getter
@Setter
public class SdpWorkspaceGetDbConfigResponse implements Serializable {
    private static final long serialVersionUID = 20963034791169569L;

    @JsonProperty("host")
    @JSONField(name = "host")
    private String host;

    @JsonProperty("port")
    @JSONField(name = "port")
    private Integer port;

    @JsonProperty("db")
    @JSONField(name = "db")
    private String db;

    @JsonProperty("user")
    @JSONField(name = "user")
    private String user;

}

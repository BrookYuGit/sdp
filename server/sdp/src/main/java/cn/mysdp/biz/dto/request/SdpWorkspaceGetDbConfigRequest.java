package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceGetDbConfigRequest
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
public class SdpWorkspaceGetDbConfigRequest extends BaseRequest {
    private static final long serialVersionUID = 8716173300908719L;

    public SdpWorkspaceGetDbConfigRequest() {
    }


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

    @JsonProperty("id_list")
    @JSONField(name = "id_list")
    private List<Integer> idList;

    @Override
    public void checkRequest() throws Exception {

    }
}

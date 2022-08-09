package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpTableUpdateRequest
 * @Description: UpdateRequest
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpTableUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 21474270306280151L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private Integer id;

    @JsonProperty("workspace_name")
    @JSONField(name = "workspace_name")
    private String workspaceName;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @JsonProperty("alias_name")
    @JSONField(name = "alias_name")
    private String aliasName;

    @JsonProperty("remark")
    @JSONField(name = "remark")
    private String remark;

    @JsonProperty("extra_info")
    @JSONField(name = "extra_info")
    private String extraInfo;

    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(id, "id");
    }
}

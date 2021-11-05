package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpHistoryAddRequest
 * @Description: AddRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpHistoryAddRequest extends BaseRequest {
    private static final long serialVersionUID = 214742701134149629L;

    @JsonProperty("workspace_name")
    @JSONField(name = "workspace_name")
    private String workspaceName;

    @JsonProperty("table_name")
    @JSONField(name = "table_name")
    private String tableName;

    @JsonProperty("remark")
    @JSONField(name = "remark")
    private String remark;

    @JsonProperty("content")
    @JSONField(name = "content")
    private String content;

    @Override
    public void checkRequest() throws Exception {
    }
}

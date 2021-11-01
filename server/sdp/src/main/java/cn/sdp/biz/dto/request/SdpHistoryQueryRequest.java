package cn.sdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpHistoryQueryRequest
 * @Description: QueryRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpHistoryQueryRequest extends BaseRequest {
    private static final long serialVersionUID = 87161731307937604L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private Integer id;

    @JsonProperty("workspace_name")
    @JSONField(name = "workspace_name")
    private String workspaceName;

    @JsonProperty("table_name")
    @JSONField(name = "table_name")
    private String tableName;

    @JsonProperty("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", name = "update_time")
    private Date updateTime;

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

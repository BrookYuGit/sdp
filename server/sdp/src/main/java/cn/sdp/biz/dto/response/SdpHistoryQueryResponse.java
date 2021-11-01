package cn.sdp.biz.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpHistoryQueryResponse
 * @Description: QueryResponse
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpHistoryQueryResponse implements Serializable {
    private static final long serialVersionUID = 130326931838426188L;

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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", name = "update_time")
    private Date updateTime;

    @JsonProperty("remark")
    @JSONField(name = "remark")
    private String remark;

    @JsonProperty("content")
    @JSONField(name = "content")
    private String content;

}

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
 * @ClassName: SdpProjectGetTableListResponse
 * @Description: SQLResponse
 * @Author: SDP
 * @Date: 2021-08-05
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 项目
 */
@Getter
@Setter
public class SdpProjectGetTableListResponse implements Serializable {
    private static final long serialVersionUID = 124007102138866095L;

    @JsonProperty("project_name")
    @JSONField(name = "project_name")
    private String projectName;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

}

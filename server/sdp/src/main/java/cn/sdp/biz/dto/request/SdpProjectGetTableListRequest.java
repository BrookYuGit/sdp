package cn.sdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpProjectGetTableListRequest
 * @Description: SQLRequest
 * @Author: SDP
 * @Date: 2021-08-05
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 项目
 */
@Getter
@Setter
public class SdpProjectGetTableListRequest extends BaseRequest{
    private static final long serialVersionUID = 6510268347797889L;

    @JsonProperty("project_name")
    @JSONField(name = "project_name")
    private String projectName;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @JsonProperty("id_list")
    @JSONField(name = "id_list")
    private List<Integer> idList;

    @Override
    public void checkRequest() throws Exception {

    }
}

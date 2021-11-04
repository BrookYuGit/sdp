package cn.mysdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpSqlDeleteRequest
 * @Description: DeleteRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpSqlDeleteRequest extends BaseRequest {
    private static final long serialVersionUID = 87161731006279029L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private List<Integer> id;


    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(id, "id");
    }
}

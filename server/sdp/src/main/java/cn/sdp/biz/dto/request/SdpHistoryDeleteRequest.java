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
 * @ClassName: SdpHistoryDeleteRequest
 * @Description: DeleteRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpHistoryDeleteRequest extends BaseRequest {
    private static final long serialVersionUID = 87161731889987823L;

    @JsonProperty("id")
    @JSONField(name = "id")
    private List<Integer> id;


    @Override
    public void checkRequest() throws Exception {
        super.checkNonNull(id, "id");
    }
}

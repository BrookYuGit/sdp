package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpHistoryWithBLOBs
 * @Description: DomainObjectWithBLOBs
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpHistoryWithBLOBs extends SdpHistory implements Serializable {
    private static final long serialVersionUID = 90001741582510971L;

    private String content;

}

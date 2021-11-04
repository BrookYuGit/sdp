package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpProjectWithBLOBs
 * @Description: DomainObjectWithBLOBs
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpProjectWithBLOBs extends SdpProject implements Serializable {
    private static final long serialVersionUID = 2052953281193834L;

    private String tables;

    private String remark;

}

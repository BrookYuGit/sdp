package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpTableWithBLOBs
 * @Description: DomainObjectWithBLOBs
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpTableWithBLOBs extends SdpTable implements Serializable {
    private static final long serialVersionUID = 9000174535043775L;

    private String remark;

    private String extraInfo;

}

package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpTemplateWithBLOBs
 * @Description: DomainObjectWithBLOBs
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpTemplateWithBLOBs extends SdpTemplate implements Serializable {
    private static final long serialVersionUID = 2052953133579191L;

    private String fileTemplate;

    private String extraInfo;

}

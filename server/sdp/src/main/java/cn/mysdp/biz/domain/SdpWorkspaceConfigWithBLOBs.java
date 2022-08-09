package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceConfigWithBLOBs
 * @Description: DomainObjectWithBLOBs
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpWorkspaceConfigWithBLOBs extends SdpWorkspaceConfig implements Serializable {
    private static final long serialVersionUID = 9000174624363464L;

    private String extraInfo;

}

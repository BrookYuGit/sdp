package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceConfig
 * @Description: DomainObject
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpWorkspaceConfig implements Serializable {
    private static final long serialVersionUID = 20529531210493464L;

    private Integer id;

    private String workspaceName;

    private String name;

    private String value;

    private String remark;

}

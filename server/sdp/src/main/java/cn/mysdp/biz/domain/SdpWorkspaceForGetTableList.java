package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceForGetTableList
 * @Description: SQLDomainObject
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@Getter
@Setter
public class SdpWorkspaceForGetTableList implements Serializable {
    private static final long serialVersionUID = 9000174482762793L;

    private String workspaceName;

    private String name;

}

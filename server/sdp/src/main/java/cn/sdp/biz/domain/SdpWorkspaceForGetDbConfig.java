package cn.sdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspaceForGetDbConfig
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
public class SdpWorkspaceForGetDbConfig implements Serializable {
    private static final long serialVersionUID = 2052953572504285L;

    private String host;

    private Integer port;

    private String db;

    private String user;

}

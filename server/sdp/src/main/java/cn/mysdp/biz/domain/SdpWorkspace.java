package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpWorkspace
 * @Description: DomainObject
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@Getter
@Setter
public class SdpWorkspace implements Serializable {
    private static final long serialVersionUID = 90001741100610358L;

    private Integer id;

    private String name;

    private String rootPath;

    private String dbHost;

    private Integer dbPort;

    private String dbDatabase;

    private String dbUsername;

    private String dbPassword;

    private String dbClassname;

    private String remark;

}

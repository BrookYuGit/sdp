package cn.sdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpProject
 * @Description: DomainObject
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpProject implements Serializable {
    private static final long serialVersionUID = 2052953505967110L;

    private Integer id;

    private String workspaceName;

    private String name;

    private String rootPath;

}

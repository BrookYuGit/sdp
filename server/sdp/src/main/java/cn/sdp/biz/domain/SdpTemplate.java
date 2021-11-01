package cn.sdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpTemplate
 * @Description: DomainObject
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpTemplate implements Serializable {
    private static final long serialVersionUID = 20529531172039097L;

    private Integer id;

    private String workspaceName;

    private String projectName;

    private String name;

    private String fileType;

    private String project;

    private String packageName;

    private Integer noOverwrite;

    private String remark;

}

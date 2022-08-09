package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpTable
 * @Description: DomainObject
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpTable implements Serializable {
    private static final long serialVersionUID = 9000174467452655L;

    private Integer id;

    private String workspaceName;

    private String name;

    private String aliasName;

}

package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpSqlWithBLOBs
 * @Description: DomainObjectWithBLOBs
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpSqlWithBLOBs extends SdpSql implements Serializable {
    private static final long serialVersionUID = 20529531462889727L;

    private String parameterSql;

    private String javaBody;

    private String extraInfo;

}

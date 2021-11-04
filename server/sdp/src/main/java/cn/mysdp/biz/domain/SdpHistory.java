package cn.mysdp.biz.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpHistory
 * @Description: DomainObject
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpHistory implements Serializable {
    private static final long serialVersionUID = 2052953730277749L;

    private Integer id;

    private String workspaceName;

    private String tableName;

    private Date updateTime;

    private String remark;

}

package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpProjectForGetTableList
 * @Description: SQLDomainObject
 * @Author: SDP
 * @Date: 2021-08-05
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 项目
 */
@Getter
@Setter
public class SdpProjectForGetTableList implements Serializable {
    private static final long serialVersionUID = 15023841059089637L;

    private String projectName;

    private String name;

}

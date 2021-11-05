package cn.mysdp.biz.convert;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpWorkspaceGetDbConfigRequestConvert
 * @Description: SQLRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public class SdpWorkspaceGetDbConfigRequestConvert {
    public static SdpWorkspaceForGetDbConfig convert(SdpWorkspaceGetDbConfigRequest request) {
        SdpWorkspaceForGetDbConfig daoRequest = new SdpWorkspaceForGetDbConfig();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setHost(daoRequest.getHost() == null ? null : daoRequest.getHost().trim());
        daoRequest.setDb(daoRequest.getDb() == null ? null : daoRequest.getDb().trim());
        daoRequest.setUser(daoRequest.getUser() == null ? null : daoRequest.getUser().trim());

        return daoRequest;
    }
}

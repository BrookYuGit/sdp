package cn.mysdp.biz.convert;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpWorkspaceGetTableListRequestConvert
 * @Description: SQLRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public class SdpWorkspaceGetTableListRequestConvert {
    public static SdpWorkspaceForGetTableList convert(SdpWorkspaceGetTableListRequest request) {
        SdpWorkspaceForGetTableList daoRequest = new SdpWorkspaceForGetTableList();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setWorkspaceName(daoRequest.getWorkspaceName() == null ? null : daoRequest.getWorkspaceName().trim());
        daoRequest.setName(daoRequest.getName() == null ? null : daoRequest.getName().trim());

        return daoRequest;
    }
}

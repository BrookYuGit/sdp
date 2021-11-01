package cn.sdp.biz.convert;

import cn.sdp.biz.domain.*;
import cn.sdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpWorkspaceConfigUpdateRequestConvert
 * @Description: UpdateRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
public class SdpWorkspaceConfigUpdateRequestConvert {
    public static SdpWorkspaceConfigWithBLOBs convert(SdpWorkspaceConfigUpdateRequest request) {
        SdpWorkspaceConfigWithBLOBs daoRequest = new SdpWorkspaceConfigWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setWorkspaceName(daoRequest.getWorkspaceName() == null ? null : daoRequest.getWorkspaceName().trim());
        daoRequest.setName(daoRequest.getName() == null ? null : daoRequest.getName().trim());
        daoRequest.setValue(daoRequest.getValue() == null ? null : daoRequest.getValue().trim());
        daoRequest.setRemark(daoRequest.getRemark() == null ? null : daoRequest.getRemark().trim());

        return daoRequest;
    }
}

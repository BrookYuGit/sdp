package cn.mysdp.biz.convert;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpWorkspaceConfigAddRequestConvert
 * @Description: AddRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
public class SdpWorkspaceConfigAddRequestConvert {
    public static SdpWorkspaceConfigWithBLOBs convert(SdpWorkspaceConfigAddRequest request) {
        SdpWorkspaceConfigWithBLOBs daoRequest = new SdpWorkspaceConfigWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setWorkspaceName(daoRequest.getWorkspaceName() == null ? null : daoRequest.getWorkspaceName().trim());
        daoRequest.setName(daoRequest.getName() == null ? null : daoRequest.getName().trim());
        daoRequest.setValue(daoRequest.getValue() == null ? null : daoRequest.getValue().trim());
        daoRequest.setRemark(daoRequest.getRemark() == null ? null : daoRequest.getRemark().trim());

        return daoRequest;
    }
}

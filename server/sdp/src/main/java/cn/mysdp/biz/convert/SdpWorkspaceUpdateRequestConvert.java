package cn.mysdp.biz.convert;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpWorkspaceUpdateRequestConvert
 * @Description: UpdateRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public class SdpWorkspaceUpdateRequestConvert {
    public static SdpWorkspaceWithBLOBs convert(SdpWorkspaceUpdateRequest request) {
        SdpWorkspaceWithBLOBs daoRequest = new SdpWorkspaceWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setName(daoRequest.getName() == null ? null : daoRequest.getName().trim());
        daoRequest.setRootPath(daoRequest.getRootPath() == null ? null : daoRequest.getRootPath().trim());
        daoRequest.setDbHost(daoRequest.getDbHost() == null ? null : daoRequest.getDbHost().trim());
        daoRequest.setDbDatabase(daoRequest.getDbDatabase() == null ? null : daoRequest.getDbDatabase().trim());
        daoRequest.setDbUsername(daoRequest.getDbUsername() == null ? null : daoRequest.getDbUsername().trim());
        daoRequest.setDbPassword(daoRequest.getDbPassword() == null ? null : daoRequest.getDbPassword().trim());
        daoRequest.setDbClassname(daoRequest.getDbClassname() == null ? null : daoRequest.getDbClassname().trim());
        daoRequest.setRemark(daoRequest.getRemark() == null ? null : daoRequest.getRemark().trim());

        return daoRequest;
    }
}

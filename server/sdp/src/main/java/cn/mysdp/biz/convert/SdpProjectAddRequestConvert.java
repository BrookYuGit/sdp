package cn.mysdp.biz.convert;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpProjectAddRequestConvert
 * @Description: AddRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
public class SdpProjectAddRequestConvert {
    public static SdpProjectWithBLOBs convert(SdpProjectAddRequest request) {
        SdpProjectWithBLOBs daoRequest = new SdpProjectWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setWorkspaceName(daoRequest.getWorkspaceName() == null ? null : daoRequest.getWorkspaceName().trim());
        daoRequest.setName(daoRequest.getName() == null ? null : daoRequest.getName().trim());
        daoRequest.setRootPath(daoRequest.getRootPath() == null ? null : daoRequest.getRootPath().trim());
        daoRequest.setTables(daoRequest.getTables() == null ? null : daoRequest.getTables().trim());
        daoRequest.setRemark(daoRequest.getRemark() == null ? null : daoRequest.getRemark().trim());
        daoRequest.setExtraInfo(daoRequest.getExtraInfo() == null ? null : daoRequest.getExtraInfo().trim());

        return daoRequest;
    }
}

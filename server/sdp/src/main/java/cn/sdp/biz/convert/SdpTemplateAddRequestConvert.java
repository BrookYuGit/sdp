package cn.sdp.biz.convert;

import cn.sdp.biz.domain.*;
import cn.sdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpTemplateAddRequestConvert
 * @Description: AddRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
public class SdpTemplateAddRequestConvert {
    public static SdpTemplateWithBLOBs convert(SdpTemplateAddRequest request) {
        SdpTemplateWithBLOBs daoRequest = new SdpTemplateWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setWorkspaceName(daoRequest.getWorkspaceName() == null ? null : daoRequest.getWorkspaceName().trim());
        daoRequest.setProjectName(daoRequest.getProjectName() == null ? null : daoRequest.getProjectName().trim());
        daoRequest.setName(daoRequest.getName() == null ? null : daoRequest.getName().trim());
        daoRequest.setFileType(daoRequest.getFileType() == null ? null : daoRequest.getFileType().trim());
        daoRequest.setProject(daoRequest.getProject() == null ? null : daoRequest.getProject().trim());
        daoRequest.setPackageName(daoRequest.getPackageName() == null ? null : daoRequest.getPackageName().trim());
        daoRequest.setRemark(daoRequest.getRemark() == null ? null : daoRequest.getRemark().trim());
        daoRequest.setFileTemplate(daoRequest.getFileTemplate() == null ? null : daoRequest.getFileTemplate().trim());
        daoRequest.setExtraInfo(daoRequest.getExtraInfo() == null ? null : daoRequest.getExtraInfo().trim());

        return daoRequest;
    }
}

package cn.mysdp.biz.convert;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpHistoryUpdateRequestConvert
 * @Description: UpdateRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
public class SdpHistoryUpdateRequestConvert {
    public static SdpHistoryWithBLOBs convert(SdpHistoryUpdateRequest request) {
        SdpHistoryWithBLOBs daoRequest = new SdpHistoryWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setWorkspaceName(daoRequest.getWorkspaceName() == null ? null : daoRequest.getWorkspaceName().trim());
        daoRequest.setTableName(daoRequest.getTableName() == null ? null : daoRequest.getTableName().trim());
        daoRequest.setRemark(daoRequest.getRemark() == null ? null : daoRequest.getRemark().trim());
        daoRequest.setContent(daoRequest.getContent() == null ? null : daoRequest.getContent().trim());

        return daoRequest;
    }
}

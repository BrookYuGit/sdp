package cn.sdp.biz.convert;

import cn.sdp.biz.domain.*;
import cn.sdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpHistoryAddRequestConvert
 * @Description: AddRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
public class SdpHistoryAddRequestConvert {
    public static SdpHistoryWithBLOBs convert(SdpHistoryAddRequest request) {
        SdpHistoryWithBLOBs daoRequest = new SdpHistoryWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setWorkspaceName(daoRequest.getWorkspaceName() == null ? null : daoRequest.getWorkspaceName().trim());
        daoRequest.setTableName(daoRequest.getTableName() == null ? null : daoRequest.getTableName().trim());
        daoRequest.setRemark(daoRequest.getRemark() == null ? null : daoRequest.getRemark().trim());
        daoRequest.setContent(daoRequest.getContent() == null ? null : daoRequest.getContent().trim());

        return daoRequest;
    }
}

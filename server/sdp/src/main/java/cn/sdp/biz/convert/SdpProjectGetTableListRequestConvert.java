package cn.sdp.biz.convert;

import cn.sdp.biz.domain.SdpProjectForGetTableList;
import cn.sdp.biz.dto.request.SdpProjectGetTableListRequest;
import cn.sdp.biz.domain.*;
import cn.sdp.biz.dto.request.*;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpProjectGetTableListRequestConvert
 * @Description: SQLRequestConvert
 * @Author: SDP
 * @Date: 2021-08-05
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 项目
 */
public class SdpProjectGetTableListRequestConvert {
    public static SdpProjectForGetTableList convert(SdpProjectGetTableListRequest request) {
        SdpProjectForGetTableList daoRequest = new SdpProjectForGetTableList();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setProjectName(daoRequest.getProjectName() == null ? null : daoRequest.getProjectName().trim());
        daoRequest.setName(daoRequest.getName() == null ? null : daoRequest.getName().trim());

        return daoRequest;
    }
}

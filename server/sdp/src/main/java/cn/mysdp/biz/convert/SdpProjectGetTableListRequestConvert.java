package cn.mysdp.biz.convert;

import cn.mysdp.biz.domain.SdpProjectForGetTableList;
import cn.mysdp.biz.dto.request.SdpProjectGetTableListRequest;
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

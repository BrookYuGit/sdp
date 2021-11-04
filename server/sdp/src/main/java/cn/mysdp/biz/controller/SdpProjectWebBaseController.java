package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import cn.mysdp.biz.facade.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SdpProjectWebBaseController
 * @Description: WebBaseController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
public class SdpProjectWebBaseController extends BaseController {
    @Autowired
    SdpProjectFacade facade;

    public int addSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpProjectAddRequest request) throws Exception {
        return facade.addSdpProject(request);
    }

    public int updateSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpProjectUpdateRequest request) throws Exception {
        return facade.updateSdpProject(request);
    }

    public int batchUpdateSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpProjectBatchUpdateRequest request) throws Exception {
        return facade.batchUpdateSdpProject(request);
    }

    public int deleteSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpProjectDeleteRequest request) throws Exception {
        return facade.deleteSdpProject(request);
    }

    public SdpProjectQueryResponse querySdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpProjectQueryRequest request) throws Exception {
        return facade.querySdpProject(request);
    }

    public List<SdpProjectQueryResponse> listSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpProjectQueryRequest request) throws Exception {
        return facade.listSdpProjectByExampleWithBLOBs(request);
    }

    public int countSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpProjectQueryRequest request) throws Exception {
        return facade.countSdpProjectByExample(request);
    }

    public Integer clone(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BaseNameIdRequest request) throws Exception {
        return facade.clone(request);
    }

    public Integer execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BaseNameRequest request) throws Exception {
        return facade.execute(request);
    }

}

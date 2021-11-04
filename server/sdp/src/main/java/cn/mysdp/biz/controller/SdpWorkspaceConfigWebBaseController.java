package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import cn.mysdp.biz.facade.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SdpWorkspaceConfigWebBaseController
 * @Description: WebBaseController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
public class SdpWorkspaceConfigWebBaseController extends BaseController {
    @Autowired
    SdpWorkspaceConfigFacade facade;

    public int addSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceConfigAddRequest request) throws Exception {
        return facade.addSdpWorkspaceConfig(request);
    }

    public int updateSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceConfigUpdateRequest request) throws Exception {
        return facade.updateSdpWorkspaceConfig(request);
    }

    public int batchUpdateSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceConfigBatchUpdateRequest request) throws Exception {
        return facade.batchUpdateSdpWorkspaceConfig(request);
    }

    public int deleteSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceConfigDeleteRequest request) throws Exception {
        return facade.deleteSdpWorkspaceConfig(request);
    }

    public SdpWorkspaceConfigQueryResponse querySdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceConfigQueryRequest request) throws Exception {
        return facade.querySdpWorkspaceConfig(request);
    }

    public List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceConfigQueryRequest request) throws Exception {
        return facade.listSdpWorkspaceConfigByExampleWithBLOBs(request);
    }

    public int countSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceConfigQueryRequest request) throws Exception {
        return facade.countSdpWorkspaceConfigByExample(request);
    }

    public Integer batchUpdateValue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceConfigAddRequest request) throws Exception {
        return facade.batchUpdateValue(request);
    }

    public Integer clone(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BaseNameIdRequest request) throws Exception {
        return facade.clone(request);
    }

}

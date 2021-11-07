package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import cn.mysdp.biz.facade.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SdpWorkspaceWebBaseController
 * @Description: WebBaseController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public class SdpWorkspaceWebBaseController extends BaseController {
    @Autowired
    SdpWorkspaceFacade facade;

    public int addSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceAddRequest request) throws Exception {
        return facade.addSdpWorkspace(request);
    }

    public int updateSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceUpdateRequest request) throws Exception {
        return facade.updateSdpWorkspace(request);
    }

    public int batchUpdateSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceBatchUpdateRequest request) throws Exception {
        return facade.batchUpdateSdpWorkspace(request);
    }

    public int deleteSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceDeleteRequest request) throws Exception {
        return facade.deleteSdpWorkspace(request);
    }

    public SdpWorkspaceQueryResponse querySdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceQueryRequest request) throws Exception {
        return facade.querySdpWorkspace(request);
    }

    public List<SdpWorkspaceQueryResponse> listSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceQueryRequest request) throws Exception {
        return facade.listSdpWorkspaceByExampleWithBLOBs(request);
    }

    public int countSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceQueryRequest request) throws Exception {
        return facade.countSdpWorkspaceByExample(request);
    }

    public List<SdpWorkspaceGetDbConfigResponse> getDbConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceGetDbConfigRequest request) throws Exception {
        return facade.getDbConfigByExample(request);
    }

    public int countGetDbConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceGetDbConfigRequest request) throws Exception {
        return facade.countGetDbConfigByExample(request);
    }

    public List<SdpWorkspaceGetTableListResponse> getTableList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceGetTableListRequest request) throws Exception {
        return facade.getTableListByExample(request);
    }

    public int countGetTableList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceGetTableListRequest request) throws Exception {
        return facade.countGetTableListByExample(request);
    }

    public Integer clone(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BaseNameIdRequest request) throws Exception {
        return facade.clone(request);
    }

    public Integer cloneWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BaseNameIdRequest request) throws Exception {
        return facade.cloneWorkspace(request);
    }

    public String testConnect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpWorkspaceUpdateRequest request) throws Exception {
        return facade.testConnect(request);
    }

}

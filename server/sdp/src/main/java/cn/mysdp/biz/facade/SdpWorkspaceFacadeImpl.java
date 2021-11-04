package cn.mysdp.biz.facade;


import java.util.List;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: SdpWorkspaceFacadeImpl
 * @Description: FacadeImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@Service
public class SdpWorkspaceFacadeImpl extends SdpWorkspaceFacadeCustomImpl implements SdpWorkspaceFacade {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpWorkspace(SdpWorkspaceAddRequest request) throws Exception {
        return super.addSdpWorkspace(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpWorkspace(SdpWorkspaceUpdateRequest request) throws Exception {
        return super.updateSdpWorkspace(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpWorkspace(SdpWorkspaceDeleteRequest request) throws Exception {
        return super.deleteSdpWorkspace(request);
    }

    @Override
    public SdpWorkspaceQueryResponse querySdpWorkspace(SdpWorkspaceQueryRequest request) throws Exception {
        super.checkNonNull(request.getId(), "id");
        SdpWorkspaceQueryResponse result = super.querySdpWorkspace(request);
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public List<SdpWorkspaceQueryResponse> listSdpWorkspace(SdpWorkspaceQueryRequest request) throws Exception {
        return super.listSdpWorkspace(request);
    }

    @Override
    public List<SdpWorkspaceQueryResponse> listSdpWorkspaceWithBLOBs(SdpWorkspaceQueryRequest request) throws Exception {
        return super.listSdpWorkspaceWithBLOBs(request);
    }

    @Override
    public List<SdpWorkspaceGetDbConfigResponse> getDbConfig(SdpWorkspaceGetDbConfigRequest request) throws Exception {
        return super.getDbConfig(request);
    }

    @Override
    public int countGetDbConfig(SdpWorkspaceGetDbConfigRequest request) throws Exception {
        return super.countGetDbConfig(request);
    }

    @Override
    public List<SdpWorkspaceGetDbConfigResponse> getDbConfigByExample(SdpWorkspaceGetDbConfigRequest request) throws Exception {
        return super.getDbConfig(request);
    }

    @Override
    public int countGetDbConfigByExample(SdpWorkspaceGetDbConfigRequest request) throws Exception {
        return super.countGetDbConfig(request);
    }

    @Override
    public List<SdpWorkspaceGetTableListResponse> getTableList(SdpWorkspaceGetTableListRequest request) throws Exception {
        return super.getTableList(request);
    }

    @Override
    public int countGetTableList(SdpWorkspaceGetTableListRequest request) throws Exception {
        return super.countGetTableList(request);
    }

    @Override
    public List<SdpWorkspaceGetTableListResponse> getTableListByExample(SdpWorkspaceGetTableListRequest request) throws Exception {
        return super.getTableList(request);
    }

    @Override
    public int countGetTableListByExample(SdpWorkspaceGetTableListRequest request) throws Exception {
        return super.countGetTableList(request);
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        return super.clone(request);
    }

    @Override
    public Integer cloneWorkspace(BaseNameIdRequest request) throws Exception {
        return super.cloneWorkspace(request);
    }

}

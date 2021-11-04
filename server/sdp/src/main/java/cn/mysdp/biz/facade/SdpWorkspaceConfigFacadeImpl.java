package cn.mysdp.biz.facade;


import java.util.List;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: SdpWorkspaceConfigFacadeImpl
 * @Description: FacadeImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
@Service
public class SdpWorkspaceConfigFacadeImpl extends SdpWorkspaceConfigFacadeCustomImpl implements SdpWorkspaceConfigFacade {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpWorkspaceConfig(SdpWorkspaceConfigAddRequest request) throws Exception {
        return super.addSdpWorkspaceConfig(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpWorkspaceConfig(SdpWorkspaceConfigUpdateRequest request) throws Exception {
        return super.updateSdpWorkspaceConfig(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpWorkspaceConfig(SdpWorkspaceConfigDeleteRequest request) throws Exception {
        return super.deleteSdpWorkspaceConfig(request);
    }

    @Override
    public SdpWorkspaceConfigQueryResponse querySdpWorkspaceConfig(SdpWorkspaceConfigQueryRequest request) throws Exception {
        super.checkNonNull(request.getId(), "id");
        SdpWorkspaceConfigQueryResponse result = super.querySdpWorkspaceConfig(request);
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfig(SdpWorkspaceConfigQueryRequest request) throws Exception {
        return super.listSdpWorkspaceConfig(request);
    }

    @Override
    public List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfigWithBLOBs(SdpWorkspaceConfigQueryRequest request) throws Exception {
        return super.listSdpWorkspaceConfigWithBLOBs(request);
    }

    @Override
    public Integer batchUpdateValue(SdpWorkspaceConfigAddRequest request) throws Exception {
        return super.batchUpdateValue(request);
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        return super.clone(request);
    }

}

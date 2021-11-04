package cn.mysdp.biz.facade;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;

/**
 * InterfaceName: SdpWorkspaceConfigFacade
 * @Description: Facade
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
public interface SdpWorkspaceConfigFacade {
    int addSdpWorkspaceConfig(SdpWorkspaceConfigAddRequest request) throws Exception;

    int updateSdpWorkspaceConfig(SdpWorkspaceConfigUpdateRequest request) throws Exception;

    int batchUpdateSdpWorkspaceConfig(SdpWorkspaceConfigBatchUpdateRequest request) throws Exception;

    int deleteSdpWorkspaceConfig(SdpWorkspaceConfigDeleteRequest request) throws Exception;

    SdpWorkspaceConfigQueryResponse querySdpWorkspaceConfig(SdpWorkspaceConfigQueryRequest request) throws Exception;

    List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfig(SdpWorkspaceConfigQueryRequest request) throws Exception;

    List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfigWithBLOBs(SdpWorkspaceConfigQueryRequest request) throws Exception;

    List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfigByExample(SdpWorkspaceConfigQueryRequest request) throws Exception;

    List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfigByExampleWithBLOBs(SdpWorkspaceConfigQueryRequest request) throws Exception;

    int countSdpWorkspaceConfigByExample(SdpWorkspaceConfigQueryRequest request) throws Exception;

    Integer batchUpdateValue(SdpWorkspaceConfigAddRequest request) throws Exception;

    Integer clone(BaseNameIdRequest request) throws Exception;

}

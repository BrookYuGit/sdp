package cn.sdp.biz.facade;

import cn.sdp.biz.dto.request.*;
import cn.sdp.biz.dto.response.*;
import java.util.List;

/**
 * InterfaceName: SdpWorkspaceFacade
 * @Description: Facade
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public interface SdpWorkspaceFacade {
    int addSdpWorkspace(SdpWorkspaceAddRequest request) throws Exception;

    int updateSdpWorkspace(SdpWorkspaceUpdateRequest request) throws Exception;

    int batchUpdateSdpWorkspace(SdpWorkspaceBatchUpdateRequest request) throws Exception;

    int deleteSdpWorkspace(SdpWorkspaceDeleteRequest request) throws Exception;

    SdpWorkspaceQueryResponse querySdpWorkspace(SdpWorkspaceQueryRequest request) throws Exception;

    List<SdpWorkspaceQueryResponse> listSdpWorkspace(SdpWorkspaceQueryRequest request) throws Exception;

    List<SdpWorkspaceQueryResponse> listSdpWorkspaceWithBLOBs(SdpWorkspaceQueryRequest request) throws Exception;

    List<SdpWorkspaceQueryResponse> listSdpWorkspaceByExample(SdpWorkspaceQueryRequest request) throws Exception;

    List<SdpWorkspaceQueryResponse> listSdpWorkspaceByExampleWithBLOBs(SdpWorkspaceQueryRequest request) throws Exception;

    int countSdpWorkspaceByExample(SdpWorkspaceQueryRequest request) throws Exception;

    List<SdpWorkspaceGetDbConfigResponse> getDbConfig(SdpWorkspaceGetDbConfigRequest request) throws Exception;

    int countGetDbConfig(SdpWorkspaceGetDbConfigRequest request) throws Exception;

    List<SdpWorkspaceGetDbConfigResponse> getDbConfigByExample(SdpWorkspaceGetDbConfigRequest request) throws Exception;

    int countGetDbConfigByExample(SdpWorkspaceGetDbConfigRequest request) throws Exception;

    List<SdpWorkspaceGetTableListResponse> getTableList(SdpWorkspaceGetTableListRequest request) throws Exception;

    int countGetTableList(SdpWorkspaceGetTableListRequest request) throws Exception;

    List<SdpWorkspaceGetTableListResponse> getTableListByExample(SdpWorkspaceGetTableListRequest request) throws Exception;

    int countGetTableListByExample(SdpWorkspaceGetTableListRequest request) throws Exception;

    Integer clone(BaseNameIdRequest request) throws Exception;

    Integer cloneWorkspace(BaseNameIdRequest request) throws Exception;

}

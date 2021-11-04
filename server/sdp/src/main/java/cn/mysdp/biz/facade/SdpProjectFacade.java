package cn.mysdp.biz.facade;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;

/**
 * InterfaceName: SdpProjectFacade
 * @Description: Facade
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
public interface SdpProjectFacade {
    int addSdpProject(SdpProjectAddRequest request) throws Exception;

    int updateSdpProject(SdpProjectUpdateRequest request) throws Exception;

    int batchUpdateSdpProject(SdpProjectBatchUpdateRequest request) throws Exception;

    int deleteSdpProject(SdpProjectDeleteRequest request) throws Exception;

    SdpProjectQueryResponse querySdpProject(SdpProjectQueryRequest request) throws Exception;

    List<SdpProjectQueryResponse> listSdpProject(SdpProjectQueryRequest request) throws Exception;

    List<SdpProjectQueryResponse> listSdpProjectWithBLOBs(SdpProjectQueryRequest request) throws Exception;

    List<SdpProjectQueryResponse> listSdpProjectByExample(SdpProjectQueryRequest request) throws Exception;

    List<SdpProjectQueryResponse> listSdpProjectByExampleWithBLOBs(SdpProjectQueryRequest request) throws Exception;

    int countSdpProjectByExample(SdpProjectQueryRequest request) throws Exception;

    Integer clone(BaseNameIdRequest request) throws Exception;

    Integer execute(BaseNameRequest request) throws Exception;

}

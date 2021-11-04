package cn.mysdp.biz.facade;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;

/**
 * InterfaceName: SdpHistoryFacade
 * @Description: Facade
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
public interface SdpHistoryFacade {
    int addSdpHistory(SdpHistoryAddRequest request) throws Exception;

    int updateSdpHistory(SdpHistoryUpdateRequest request) throws Exception;

    int batchUpdateSdpHistory(SdpHistoryBatchUpdateRequest request) throws Exception;

    int deleteSdpHistory(SdpHistoryDeleteRequest request) throws Exception;

    SdpHistoryQueryResponse querySdpHistory(SdpHistoryQueryRequest request) throws Exception;

    List<SdpHistoryQueryResponse> listSdpHistory(SdpHistoryQueryRequest request) throws Exception;

    List<SdpHistoryQueryResponse> listSdpHistoryWithBLOBs(SdpHistoryQueryRequest request) throws Exception;

    List<SdpHistoryQueryResponse> listSdpHistoryByExample(SdpHistoryQueryRequest request) throws Exception;

    List<SdpHistoryQueryResponse> listSdpHistoryByExampleWithBLOBs(SdpHistoryQueryRequest request) throws Exception;

    int countSdpHistoryByExample(SdpHistoryQueryRequest request) throws Exception;

    int clear(BaseRequest request) throws Exception;

}

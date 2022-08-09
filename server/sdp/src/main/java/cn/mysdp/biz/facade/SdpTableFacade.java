package cn.mysdp.biz.facade;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;

/**
 * InterfaceName: SdpTableFacade
 * @Description: Facade
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
public interface SdpTableFacade {
    int addSdpTable(SdpTableAddRequest request) throws Exception;

    int updateSdpTable(SdpTableUpdateRequest request) throws Exception;

    int batchUpdateSdpTable(SdpTableBatchUpdateRequest request) throws Exception;

    int deleteSdpTable(SdpTableDeleteRequest request) throws Exception;

    SdpTableQueryResponse querySdpTable(SdpTableQueryRequest request) throws Exception;

    List<SdpTableQueryResponse> listSdpTable(SdpTableQueryRequest request) throws Exception;

    List<SdpTableQueryResponse> listSdpTableWithBLOBs(SdpTableQueryRequest request) throws Exception;

    List<SdpTableQueryResponse> listSdpTableByExample(SdpTableQueryRequest request) throws Exception;

    List<SdpTableQueryResponse> listSdpTableByExampleWithBLOBs(SdpTableQueryRequest request) throws Exception;

    int countSdpTableByExample(SdpTableQueryRequest request) throws Exception;

}

package cn.mysdp.biz.facade;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;

/**
 * InterfaceName: SdpSqlFacade
 * @Description: Facade
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
public interface SdpSqlFacade {
    int addSdpSql(SdpSqlAddRequest request) throws Exception;

    int updateSdpSql(SdpSqlUpdateRequest request) throws Exception;

    int batchUpdateSdpSql(SdpSqlBatchUpdateRequest request) throws Exception;

    int deleteSdpSql(SdpSqlDeleteRequest request) throws Exception;

    SdpSqlQueryResponse querySdpSql(SdpSqlQueryRequest request) throws Exception;

    List<SdpSqlQueryResponse> listSdpSql(SdpSqlQueryRequest request) throws Exception;

    List<SdpSqlQueryResponse> listSdpSqlWithBLOBs(SdpSqlQueryRequest request) throws Exception;

    List<SdpSqlQueryResponse> listSdpSqlByExample(SdpSqlQueryRequest request) throws Exception;

    List<SdpSqlQueryResponse> listSdpSqlByExampleWithBLOBs(SdpSqlQueryRequest request) throws Exception;

    int countSdpSqlByExample(SdpSqlQueryRequest request) throws Exception;

    Integer clone(BaseNameIdRequest request) throws Exception;

}

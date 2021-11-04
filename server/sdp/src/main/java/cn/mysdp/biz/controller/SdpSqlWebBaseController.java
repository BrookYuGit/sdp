package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import cn.mysdp.biz.facade.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SdpSqlWebBaseController
 * @Description: WebBaseController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
public class SdpSqlWebBaseController extends BaseController {
    @Autowired
    SdpSqlFacade facade;

    public int addSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpSqlAddRequest request) throws Exception {
        return facade.addSdpSql(request);
    }

    public int updateSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpSqlUpdateRequest request) throws Exception {
        return facade.updateSdpSql(request);
    }

    public int batchUpdateSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpSqlBatchUpdateRequest request) throws Exception {
        return facade.batchUpdateSdpSql(request);
    }

    public int deleteSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpSqlDeleteRequest request) throws Exception {
        return facade.deleteSdpSql(request);
    }

    public SdpSqlQueryResponse querySdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpSqlQueryRequest request) throws Exception {
        return facade.querySdpSql(request);
    }

    public List<SdpSqlQueryResponse> listSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpSqlQueryRequest request) throws Exception {
        return facade.listSdpSqlByExampleWithBLOBs(request);
    }

    public int countSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpSqlQueryRequest request) throws Exception {
        return facade.countSdpSqlByExample(request);
    }

    public Integer clone(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BaseNameIdRequest request) throws Exception {
        return facade.clone(request);
    }

}

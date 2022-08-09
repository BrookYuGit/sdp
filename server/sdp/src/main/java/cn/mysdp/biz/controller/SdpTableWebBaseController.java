package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import cn.mysdp.biz.facade.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SdpTableWebBaseController
 * @Description: WebBaseController
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
public class SdpTableWebBaseController extends BaseController {
    @Autowired
    SdpTableFacade facade;

    public int addSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTableAddRequest request) throws Exception {
        return facade.addSdpTable(request);
    }

    public int updateSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTableUpdateRequest request) throws Exception {
        return facade.updateSdpTable(request);
    }

    public int batchUpdateSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTableBatchUpdateRequest request) throws Exception {
        return facade.batchUpdateSdpTable(request);
    }

    public int deleteSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTableDeleteRequest request) throws Exception {
        return facade.deleteSdpTable(request);
    }

    public SdpTableQueryResponse querySdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTableQueryRequest request) throws Exception {
        return facade.querySdpTable(request);
    }

    public List<SdpTableQueryResponse> listSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTableQueryRequest request) throws Exception {
        return facade.listSdpTableByExampleWithBLOBs(request);
    }

    public int countSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTableQueryRequest request) throws Exception {
        return facade.countSdpTableByExample(request);
    }

}

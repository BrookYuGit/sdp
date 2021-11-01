package cn.sdp.biz.controller;


import cn.sdp.biz.dto.request.*;
import cn.sdp.biz.dto.response.*;
import cn.sdp.biz.facade.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SdpHistoryWebBaseController
 * @Description: WebBaseController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
public class SdpHistoryWebBaseController extends BaseController {
    @Autowired
    SdpHistoryFacade facade;

    public int addSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpHistoryAddRequest request) throws Exception {
        return facade.addSdpHistory(request);
    }

    public int updateSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpHistoryUpdateRequest request) throws Exception {
        return facade.updateSdpHistory(request);
    }

    public int batchUpdateSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpHistoryBatchUpdateRequest request) throws Exception {
        return facade.batchUpdateSdpHistory(request);
    }

    public int deleteSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpHistoryDeleteRequest request) throws Exception {
        return facade.deleteSdpHistory(request);
    }

    public SdpHistoryQueryResponse querySdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpHistoryQueryRequest request) throws Exception {
        return facade.querySdpHistory(request);
    }

    public List<SdpHistoryQueryResponse> listSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpHistoryQueryRequest request) throws Exception {
        return facade.listSdpHistoryByExampleWithBLOBs(request);
    }

    public int countSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpHistoryQueryRequest request) throws Exception {
        return facade.countSdpHistoryByExample(request);
    }

    public int clear(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BaseRequest request) throws Exception {
        return facade.clear(request);
    }

}

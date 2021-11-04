package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import cn.mysdp.biz.facade.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SdpTemplateWebBaseController
 * @Description: WebBaseController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
public class SdpTemplateWebBaseController extends BaseController {
    @Autowired
    SdpTemplateFacade facade;

    public int addSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTemplateAddRequest request) throws Exception {
        return facade.addSdpTemplate(request);
    }

    public int updateSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTemplateUpdateRequest request) throws Exception {
        return facade.updateSdpTemplate(request);
    }

    public int batchUpdateSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTemplateBatchUpdateRequest request) throws Exception {
        return facade.batchUpdateSdpTemplate(request);
    }

    public int deleteSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTemplateDeleteRequest request) throws Exception {
        return facade.deleteSdpTemplate(request);
    }

    public SdpTemplateQueryResponse querySdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTemplateQueryRequest request) throws Exception {
        return facade.querySdpTemplate(request);
    }

    public List<SdpTemplateQueryResponse> listSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTemplateQueryRequest request) throws Exception {
        return facade.listSdpTemplateByExampleWithBLOBs(request);
    }

    public int countSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SdpTemplateQueryRequest request) throws Exception {
        return facade.countSdpTemplateByExample(request);
    }

}

package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SdpTemplateWebController
 * @Description: WebController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
@RestController
@RequestMapping(value = "/sdp_template")
public class SdpTemplateWebController extends SdpTemplateWebCustomController {
    @PostMapping("/add")
    public int addSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTemplateAddRequest request) throws Exception {
        fixRequest(request);
        return super.addSdpTemplate(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/update")
    public int updateSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTemplateUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.updateSdpTemplate(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/batch_update")
    public int batchUpdateSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTemplateBatchUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.batchUpdateSdpTemplate(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/delete")
    public int deleteSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTemplateDeleteRequest request) throws Exception {
        fixRequest(request);
        return super.deleteSdpTemplate(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/query")
    public SdpTemplateQueryResponse querySdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTemplateQueryRequest request) throws Exception {
        fixRequest(request);
        return super.querySdpTemplate(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/list")
    public List<SdpTemplateQueryResponse> listSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTemplateQueryRequest request) throws Exception {
        fixRequest(request);
        return super.listSdpTemplate(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count")
    public int countSdpTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTemplateQueryRequest request) throws Exception {
        fixRequest(request);
        return super.countSdpTemplate(httpServletRequest, httpServletResponse, request);
    }

}

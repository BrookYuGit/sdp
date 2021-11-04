package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SdpProjectWebController
 * @Description: WebController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
@RestController
@RequestMapping(value = "/sdp_project")
public class SdpProjectWebController extends SdpProjectWebCustomController {
    @PostMapping("/add")
    public int addSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpProjectAddRequest request) throws Exception {
        fixRequest(request);
        return super.addSdpProject(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/update")
    public int updateSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpProjectUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.updateSdpProject(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/batch_update")
    public int batchUpdateSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpProjectBatchUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.batchUpdateSdpProject(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/delete")
    public int deleteSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpProjectDeleteRequest request) throws Exception {
        fixRequest(request);
        return super.deleteSdpProject(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/query")
    public SdpProjectQueryResponse querySdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpProjectQueryRequest request) throws Exception {
        fixRequest(request);
        return super.querySdpProject(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/list")
    public List<SdpProjectQueryResponse> listSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpProjectQueryRequest request) throws Exception {
        fixRequest(request);
        return super.listSdpProject(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count")
    public int countSdpProject(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpProjectQueryRequest request) throws Exception {
        fixRequest(request);
        return super.countSdpProject(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/clone")
    public Integer clone(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseNameIdRequest request) throws Exception {
        fixRequest(request);
        return super.clone(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/execute")
    public Integer execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseNameRequest request) throws Exception {
        fixRequest(request);
        return super.execute(httpServletRequest, httpServletResponse, request);
    }

}

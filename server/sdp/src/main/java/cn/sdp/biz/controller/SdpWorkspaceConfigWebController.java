package cn.sdp.biz.controller;


import cn.sdp.biz.dto.request.*;
import cn.sdp.biz.dto.response.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SdpWorkspaceConfigWebController
 * @Description: WebController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
@RestController
@RequestMapping(value = "/sdp_workspace_config")
public class SdpWorkspaceConfigWebController extends SdpWorkspaceConfigWebCustomController {
    @PostMapping("/add")
    public int addSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceConfigAddRequest request) throws Exception {
        fixRequest(request);
        return super.addSdpWorkspaceConfig(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/update")
    public int updateSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceConfigUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.updateSdpWorkspaceConfig(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/batch_update")
    public int batchUpdateSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceConfigBatchUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.batchUpdateSdpWorkspaceConfig(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/delete")
    public int deleteSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceConfigDeleteRequest request) throws Exception {
        fixRequest(request);
        return super.deleteSdpWorkspaceConfig(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/query")
    public SdpWorkspaceConfigQueryResponse querySdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceConfigQueryRequest request) throws Exception {
        fixRequest(request);
        return super.querySdpWorkspaceConfig(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/list")
    public List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceConfigQueryRequest request) throws Exception {
        fixRequest(request);
        return super.listSdpWorkspaceConfig(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count")
    public int countSdpWorkspaceConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceConfigQueryRequest request) throws Exception {
        fixRequest(request);
        return super.countSdpWorkspaceConfig(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/batch_update_value")
    public Integer batchUpdateValue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceConfigAddRequest request) throws Exception {
        fixRequest(request);
        return super.batchUpdateValue(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/clone")
    public Integer clone(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseNameIdRequest request) throws Exception {
        fixRequest(request);
        return super.clone(httpServletRequest, httpServletResponse, request);
    }

}

package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SdpWorkspaceWebController
 * @Description: WebController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
@RestController
@RequestMapping(value = "/sdp_workspace")
public class SdpWorkspaceWebController extends SdpWorkspaceWebCustomController {
    @PostMapping("/add")
    public int addSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceAddRequest request) throws Exception {
        fixRequest(request);
        return super.addSdpWorkspace(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/update")
    public int updateSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.updateSdpWorkspace(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/batch_update")
    public int batchUpdateSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceBatchUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.batchUpdateSdpWorkspace(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/delete")
    public int deleteSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceDeleteRequest request) throws Exception {
        fixRequest(request);
        return super.deleteSdpWorkspace(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/query")
    public SdpWorkspaceQueryResponse querySdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceQueryRequest request) throws Exception {
        fixRequest(request);
        return super.querySdpWorkspace(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/list")
    public List<SdpWorkspaceQueryResponse> listSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceQueryRequest request) throws Exception {
        fixRequest(request);
        return super.listSdpWorkspace(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count")
    public int countSdpWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceQueryRequest request) throws Exception {
        fixRequest(request);
        return super.countSdpWorkspace(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/get_db_config")
    public List<SdpWorkspaceGetDbConfigResponse> getDbConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceGetDbConfigRequest request) throws Exception {
        fixRequest(request);
        return super.getDbConfig(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count_get_db_config")
    public int countGetDbConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceGetDbConfigRequest request) throws Exception {
        fixRequest(request);
        return super.countGetDbConfig(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/get_table_list")
    public List<SdpWorkspaceGetTableListResponse> getTableList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceGetTableListRequest request) throws Exception {
        fixRequest(request);
        return super.getTableList(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count_get_table_list")
    public int countGetTableList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceGetTableListRequest request) throws Exception {
        fixRequest(request);
        return super.countGetTableList(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/clone")
    public Integer clone(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseNameIdRequest request) throws Exception {
        fixRequest(request);
        return super.clone(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/clone_workspace")
    public Integer cloneWorkspace(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseNameIdRequest request) throws Exception {
        fixRequest(request);
        return super.cloneWorkspace(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/test_connect")
    public String testConnect(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpWorkspaceUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.testConnect(httpServletRequest, httpServletResponse, request);
    }

}

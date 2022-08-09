package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SdpTableWebController
 * @Description: WebController
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
@RestController
@RequestMapping(value = "/sdp_table")
public class SdpTableWebController extends SdpTableWebCustomController {
    @PostMapping("/add")
    public int addSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTableAddRequest request) throws Exception {
        fixRequest(request);
        return super.addSdpTable(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/update")
    public int updateSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTableUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.updateSdpTable(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/batch_update")
    public int batchUpdateSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTableBatchUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.batchUpdateSdpTable(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/delete")
    public int deleteSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTableDeleteRequest request) throws Exception {
        fixRequest(request);
        return super.deleteSdpTable(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/query")
    public SdpTableQueryResponse querySdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTableQueryRequest request) throws Exception {
        fixRequest(request);
        return super.querySdpTable(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/list")
    public List<SdpTableQueryResponse> listSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTableQueryRequest request) throws Exception {
        fixRequest(request);
        return super.listSdpTable(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count")
    public int countSdpTable(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpTableQueryRequest request) throws Exception {
        fixRequest(request);
        return super.countSdpTable(httpServletRequest, httpServletResponse, request);
    }

}

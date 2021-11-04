package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SdpHistoryWebController
 * @Description: WebController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
@RestController
@RequestMapping(value = "/sdp_history")
public class SdpHistoryWebController extends SdpHistoryWebCustomController {
    @PostMapping("/add")
    public int addSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpHistoryAddRequest request) throws Exception {
        fixRequest(request);
        return super.addSdpHistory(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/update")
    public int updateSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpHistoryUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.updateSdpHistory(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/batch_update")
    public int batchUpdateSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpHistoryBatchUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.batchUpdateSdpHistory(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/delete")
    public int deleteSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpHistoryDeleteRequest request) throws Exception {
        fixRequest(request);
        return super.deleteSdpHistory(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/query")
    public SdpHistoryQueryResponse querySdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpHistoryQueryRequest request) throws Exception {
        fixRequest(request);
        return super.querySdpHistory(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/list")
    public List<SdpHistoryQueryResponse> listSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpHistoryQueryRequest request) throws Exception {
        fixRequest(request);
        return super.listSdpHistory(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count")
    public int countSdpHistory(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpHistoryQueryRequest request) throws Exception {
        fixRequest(request);
        return super.countSdpHistory(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/clear")
    public int clear(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseRequest request) throws Exception {
        fixRequest(request);
        return super.clear(httpServletRequest, httpServletResponse, request);
    }

}

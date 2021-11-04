package cn.mysdp.biz.controller;


import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SdpSqlWebController
 * @Description: WebController
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
@RestController
@RequestMapping(value = "/sdp_sql")
public class SdpSqlWebController extends SdpSqlWebCustomController {
    @PostMapping("/add")
    public int addSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpSqlAddRequest request) throws Exception {
        fixRequest(request);
        return super.addSdpSql(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/update")
    public int updateSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpSqlUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.updateSdpSql(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/batch_update")
    public int batchUpdateSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpSqlBatchUpdateRequest request) throws Exception {
        fixRequest(request);
        return super.batchUpdateSdpSql(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/delete")
    public int deleteSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpSqlDeleteRequest request) throws Exception {
        fixRequest(request);
        return super.deleteSdpSql(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/query")
    public SdpSqlQueryResponse querySdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpSqlQueryRequest request) throws Exception {
        fixRequest(request);
        return super.querySdpSql(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/list")
    public List<SdpSqlQueryResponse> listSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpSqlQueryRequest request) throws Exception {
        fixRequest(request);
        return super.listSdpSql(httpServletRequest, httpServletResponse, request);
    }

    @PostMapping("/count")
    public int countSdpSql(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody SdpSqlQueryRequest request) throws Exception {
        fixRequest(request);
        return super.countSdpSql(httpServletRequest, httpServletResponse, request);
    }

    @ResponseBody
    @PostMapping("/clone")
    public Integer clone(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestBody BaseNameIdRequest request) throws Exception {
        fixRequest(request);
        return super.clone(httpServletRequest, httpServletResponse, request);
    }

}

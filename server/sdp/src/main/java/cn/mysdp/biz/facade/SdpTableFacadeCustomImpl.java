package cn.mysdp.biz.facade;

import cn.mysdp.biz.domain.SdpTableExample;
import cn.mysdp.biz.domain.SdpTableWithBLOBs;
import cn.mysdp.biz.dto.request.SdpTableQueryRequest;
import cn.mysdp.biz.dto.request.SdpWorkspaceGetTableListRequest;
import cn.mysdp.biz.dto.response.SdpTableQueryResponse;
import cn.mysdp.biz.dto.response.SdpWorkspaceGetTableListResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName: SdpTableFacadeCustomImpl
 * @Description: FacadeCustomImpl
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
public class SdpTableFacadeCustomImpl extends SdpTableFacadeBaseImpl {
    @Autowired
    SdpWorkspaceFacade sdpWorkspaceFacade;

    public List<SdpTableQueryResponse> listSdpTableByExampleWithBLOBs(SdpTableQueryRequest request) throws Exception {
        if (!StringUtils.hasText(request.getWorkspaceName())) {
            throw new Exception("缺少参数：workspace");
        }
        List<SdpWorkspaceGetTableListResponse> tables;
        Map<String, SdpWorkspaceGetTableListResponse> map = new HashMap<>();
        {
            SdpWorkspaceGetTableListRequest newRequest = new SdpWorkspaceGetTableListRequest();
            newRequest.setCallType("only_tables");
            BeanUtils.copyProperties(request, newRequest);
            tables = sdpWorkspaceFacade.getTableList(newRequest);
            for(SdpWorkspaceGetTableListResponse item: tables) {
                map.put(item.getName(), item);
            }
        }
        List<SdpTableQueryResponse> list = super.listSdpTableByExampleWithBLOBs(request);
        Set<String> doneSet = new HashSet<>();
        int maxId = 0;
        for(SdpTableQueryResponse item: list) {
            if (map.containsKey(item.getName())) {
                doneSet.add(item.getName());
            }
            if (item.getId() > maxId) {
                maxId = item.getId();
            }
        }
        for(SdpWorkspaceGetTableListResponse item: tables) {
            if (doneSet.contains(item.getName())) {
                continue;
            }
            SdpTableQueryResponse newItem = new SdpTableQueryResponse();
            newItem.setId(++maxId);
            newItem.setName(item.getName());
            newItem.setWorkspaceName(request.getWorkspaceName());
            list.add(newItem);
        }

        return list;
    }

    @Override
    public int countSdpTableByExample(SdpTableQueryRequest request) throws Exception {
        clearPageInfo(request);
        return listSdpTableByExampleWithBLOBs(request).size();
    }
}

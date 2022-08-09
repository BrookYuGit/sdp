package cn.mysdp.biz.facade;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import cn.mysdp.biz.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @ClassName: SdpTableFacadeImpl
 * @Description: FacadeImpl
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
@Service
public class SdpTableFacadeImpl extends SdpTableFacadeCustomImpl implements SdpTableFacade {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpTable(SdpTableAddRequest request) throws Exception {
        return super.addSdpTable(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpTable(SdpTableUpdateRequest request) throws Exception {
        return super.updateSdpTable(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpTable(SdpTableDeleteRequest request) throws Exception {
        return super.deleteSdpTable(request);
    }

    @Override
    public SdpTableQueryResponse querySdpTable(SdpTableQueryRequest request) throws Exception {
        super.checkNonNull(request.getId(), "id");
        SdpTableQueryResponse result = super.querySdpTable(request);
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public List<SdpTableQueryResponse> listSdpTable(SdpTableQueryRequest request) throws Exception {
        return super.listSdpTable(request);
    }

    @Override
    public List<SdpTableQueryResponse> listSdpTableWithBLOBs(SdpTableQueryRequest request) throws Exception {
        return super.listSdpTableWithBLOBs(request);
    }

}

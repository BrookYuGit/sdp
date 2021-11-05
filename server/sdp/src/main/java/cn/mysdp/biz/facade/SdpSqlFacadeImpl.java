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
 * @ClassName: SdpSqlFacadeImpl
 * @Description: FacadeImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
@Service
public class SdpSqlFacadeImpl extends SdpSqlFacadeCustomImpl implements SdpSqlFacade {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpSql(SdpSqlAddRequest request) throws Exception {
        return super.addSdpSql(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpSql(SdpSqlUpdateRequest request) throws Exception {
        return super.updateSdpSql(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpSql(SdpSqlDeleteRequest request) throws Exception {
        return super.deleteSdpSql(request);
    }

    @Override
    public SdpSqlQueryResponse querySdpSql(SdpSqlQueryRequest request) throws Exception {
        super.checkNonNull(request.getId(), "id");
        SdpSqlQueryResponse result = super.querySdpSql(request);
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public List<SdpSqlQueryResponse> listSdpSql(SdpSqlQueryRequest request) throws Exception {
        return super.listSdpSql(request);
    }

    @Override
    public List<SdpSqlQueryResponse> listSdpSqlWithBLOBs(SdpSqlQueryRequest request) throws Exception {
        return super.listSdpSqlWithBLOBs(request);
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        return super.clone(request);
    }

}

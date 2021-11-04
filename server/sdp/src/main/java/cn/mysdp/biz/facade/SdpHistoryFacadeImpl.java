package cn.mysdp.biz.facade;


import java.util.List;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: SdpHistoryFacadeImpl
 * @Description: FacadeImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
@Service
public class SdpHistoryFacadeImpl extends SdpHistoryFacadeCustomImpl implements SdpHistoryFacade {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpHistory(SdpHistoryAddRequest request) throws Exception {
        return super.addSdpHistory(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpHistory(SdpHistoryUpdateRequest request) throws Exception {
        return super.updateSdpHistory(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpHistory(SdpHistoryDeleteRequest request) throws Exception {
        return super.deleteSdpHistory(request);
    }

    @Override
    public SdpHistoryQueryResponse querySdpHistory(SdpHistoryQueryRequest request) throws Exception {
        super.checkNonNull(request.getId(), "id");
        SdpHistoryQueryResponse result = super.querySdpHistory(request);
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public List<SdpHistoryQueryResponse> listSdpHistory(SdpHistoryQueryRequest request) throws Exception {
        return super.listSdpHistory(request);
    }

    @Override
    public List<SdpHistoryQueryResponse> listSdpHistoryWithBLOBs(SdpHistoryQueryRequest request) throws Exception {
        return super.listSdpHistoryWithBLOBs(request);
    }

    @Override
    public int clear(BaseRequest request) throws Exception {
        return super.clear(request);
    }

}

package cn.mysdp.biz.facade;


import java.util.List;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: SdpProjectFacadeImpl
 * @Description: FacadeImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
@Service
public class SdpProjectFacadeImpl extends SdpProjectFacadeCustomImpl implements SdpProjectFacade {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpProject(SdpProjectAddRequest request) throws Exception {
        return super.addSdpProject(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpProject(SdpProjectUpdateRequest request) throws Exception {
        return super.updateSdpProject(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpProject(SdpProjectDeleteRequest request) throws Exception {
        return super.deleteSdpProject(request);
    }

    @Override
    public SdpProjectQueryResponse querySdpProject(SdpProjectQueryRequest request) throws Exception {
        super.checkNonNull(request.getId(), "id");
        SdpProjectQueryResponse result = super.querySdpProject(request);
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public List<SdpProjectQueryResponse> listSdpProject(SdpProjectQueryRequest request) throws Exception {
        return super.listSdpProject(request);
    }

    @Override
    public List<SdpProjectQueryResponse> listSdpProjectWithBLOBs(SdpProjectQueryRequest request) throws Exception {
        return super.listSdpProjectWithBLOBs(request);
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        return super.clone(request);
    }

    @Override
    public Integer execute(BaseNameRequest request) throws Exception {
        return super.execute(request);
    }

}

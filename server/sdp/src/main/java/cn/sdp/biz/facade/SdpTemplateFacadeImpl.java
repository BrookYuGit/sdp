package cn.sdp.biz.facade;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.sdp.biz.dto.request.*;
import cn.sdp.biz.dto.response.*;
import cn.sdp.biz.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @ClassName: SdpTemplateFacadeImpl
 * @Description: FacadeImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
@Service
public class SdpTemplateFacadeImpl extends SdpTemplateFacadeCustomImpl implements SdpTemplateFacade {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpTemplate(SdpTemplateAddRequest request) throws Exception {
        return super.addSdpTemplate(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpTemplate(SdpTemplateUpdateRequest request) throws Exception {
        return super.updateSdpTemplate(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpTemplate(SdpTemplateDeleteRequest request) throws Exception {
        return super.deleteSdpTemplate(request);
    }

    @Override
    public SdpTemplateQueryResponse querySdpTemplate(SdpTemplateQueryRequest request) throws Exception {
        super.checkNonNull(request.getId(), "id");
        SdpTemplateQueryResponse result = super.querySdpTemplate(request);
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public List<SdpTemplateQueryResponse> listSdpTemplate(SdpTemplateQueryRequest request) throws Exception {
        return super.listSdpTemplate(request);
    }

    @Override
    public List<SdpTemplateQueryResponse> listSdpTemplateWithBLOBs(SdpTemplateQueryRequest request) throws Exception {
        return super.listSdpTemplateWithBLOBs(request);
    }

}

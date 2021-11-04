package cn.mysdp.biz.facade;


import java.util.List;

import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

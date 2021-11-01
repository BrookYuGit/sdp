package cn.sdp.biz.facade;

import cn.sdp.biz.dto.request.*;
import cn.sdp.biz.dto.response.*;
import java.util.List;

/**
 * InterfaceName: SdpTemplateFacade
 * @Description: Facade
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
public interface SdpTemplateFacade {
    int addSdpTemplate(SdpTemplateAddRequest request) throws Exception;

    int updateSdpTemplate(SdpTemplateUpdateRequest request) throws Exception;

    int batchUpdateSdpTemplate(SdpTemplateBatchUpdateRequest request) throws Exception;

    int deleteSdpTemplate(SdpTemplateDeleteRequest request) throws Exception;

    SdpTemplateQueryResponse querySdpTemplate(SdpTemplateQueryRequest request) throws Exception;

    List<SdpTemplateQueryResponse> listSdpTemplate(SdpTemplateQueryRequest request) throws Exception;

    List<SdpTemplateQueryResponse> listSdpTemplateWithBLOBs(SdpTemplateQueryRequest request) throws Exception;

    List<SdpTemplateQueryResponse> listSdpTemplateByExample(SdpTemplateQueryRequest request) throws Exception;

    List<SdpTemplateQueryResponse> listSdpTemplateByExampleWithBLOBs(SdpTemplateQueryRequest request) throws Exception;

    int countSdpTemplateByExample(SdpTemplateQueryRequest request) throws Exception;

}

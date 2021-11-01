package cn.sdp.biz.facade;

import cn.sdp.biz.dto.request.SdpTemplateAddRequest;
import cn.sdp.biz.dto.request.SdpTemplateBatchUpdateRequest;
import cn.sdp.biz.dto.request.SdpTemplateUpdateRequest;
import cn.sdp.biz.dto.request.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @ClassName:
 * @Description: FacadeCustomImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table:
 * Comment:
 *
 */

public class SdpTemplateFacadeCustomImpl extends SdpTemplateFacadeBaseImpl {
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpTemplate(SdpTemplateAddRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getProjectName());
        return super.addSdpTemplate(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpTemplate(SdpTemplateUpdateRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getProjectName());
        if (!StringUtils.isEmpty(request.getFileTemplate())) {
            request.setFileTemplate(request.getFileTemplate().replaceAll("\t", "    "));
        }
        return super.updateSdpTemplate(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpTemplate(SdpTemplateBatchUpdateRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getProjectName());
        if (!StringUtils.isEmpty(request.getFileTemplate())) {
            request.setFileTemplate(request.getFileTemplate().replaceAll("\t", "    "));
        }
        return super.batchUpdateSdpTemplate(request);
    }
}

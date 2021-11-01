package cn.sdp.biz.facade;

import cn.sdp.biz.domain.SdpWorkspaceConfigExample;
import cn.sdp.biz.domain.SdpWorkspaceConfigWithBLOBs;
import cn.sdp.biz.dto.request.SdpWorkspaceConfigBatchUpdateRequest;
import cn.sdp.biz.domain.*;
import cn.sdp.biz.dto.request.BaseNameIdRequest;
import cn.sdp.biz.dto.request.SdpWorkspaceConfigAddRequest;
import cn.sdp.biz.dto.request.SdpWorkspaceConfigUpdateRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class SdpWorkspaceConfigFacadeCustomImpl extends SdpWorkspaceConfigFacadeBaseImpl {
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpWorkspaceConfig(SdpWorkspaceConfigAddRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getName());
        return super.addSdpWorkspaceConfig(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpWorkspaceConfig(SdpWorkspaceConfigUpdateRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getName());
        return super.updateSdpWorkspaceConfig(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpWorkspaceConfig(SdpWorkspaceConfigBatchUpdateRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getName());
        return super.batchUpdateSdpWorkspaceConfig(request);
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        checkLowercaseName(request.getName());

        SdpWorkspaceConfigWithBLOBs sdpWorkspaceConfigWithBLOBs = sdpWorkspaceConfigMapper.selectByPrimaryKeyWithBLOBs(request.getId());
        if (request.getName().equals(sdpWorkspaceConfigWithBLOBs.getWorkspaceName())) {
            throw new Exception("dest name is it self");
        }
        SdpWorkspaceConfigExample example = new SdpWorkspaceConfigExample();
        example.createCriteria().andWorkspaceNameEqualTo(sdpWorkspaceConfigWithBLOBs.getWorkspaceName());
        List<SdpWorkspaceConfigWithBLOBs> oriList = sdpWorkspaceConfigMapper.selectByExampleWithBLOBs(example);
        SdpWorkspaceConfigExample destExample = new SdpWorkspaceConfigExample();
        destExample.createCriteria().andWorkspaceNameEqualTo(request.getName());
        List<SdpWorkspaceConfigWithBLOBs> destList = sdpWorkspaceConfigMapper.selectByExampleWithBLOBs(destExample);
        Map<String, SdpWorkspaceConfigWithBLOBs> oriMap = new HashMap<>();
        for(SdpWorkspaceConfigWithBLOBs item: oriList) {
            oriMap.put(item.getName(), item);
        }
        for(SdpWorkspaceConfigWithBLOBs item: destList) {
            String name = item.getName();
            SdpWorkspaceConfigWithBLOBs destItem = oriMap.get(name);
            if (destItem != null) {
                SdpWorkspaceConfigUpdateRequest destUpdateRequest = new SdpWorkspaceConfigUpdateRequest();
                BeanUtils.copyProperties(item, destUpdateRequest);
                destUpdateRequest.setId(destItem.getId());
                destUpdateRequest.setWorkspaceName(request.getName());
                super.updateSdpWorkspaceConfig(destUpdateRequest);

                oriMap.remove(name);
            }
        }
        for(SdpWorkspaceConfigWithBLOBs item: oriMap.values()) {
            item.setId(null);
            item.setWorkspaceName(request.getName());

            sdpWorkspaceConfigMapper.insertSelective(item);
        }
        return 1;
    }

    @Override
    public Integer batchUpdateValue(SdpWorkspaceConfigAddRequest request) throws Exception {
        if (StringUtils.isEmpty(request.getNewValue())) {
            throw new Exception("miss parameter：new_value");
        }
        if (StringUtils.isEmpty(request.getValue())) {
            throw new Exception("miss parameter：value");
        }
        if (request.getValue().equals(request.getNewValue())) {
            throw new Exception("it's the same value");
        }
        SdpWorkspaceConfigExample example = new SdpWorkspaceConfigExample();
        example.createCriteria().andWorkspaceNameEqualTo(request.getWorkspaceName())
        .andValueLike("%"+request.getValue()+"%");
        List<SdpWorkspaceConfigWithBLOBs> list = sdpWorkspaceConfigMapper.selectByExampleWithBLOBs(example);
        for(SdpWorkspaceConfigWithBLOBs item: list) {
            SdpWorkspaceConfigUpdateRequest newRequest = new SdpWorkspaceConfigUpdateRequest();
            newRequest.setId(item.getId());
            newRequest.setValue(item.getValue().replace(request.getValue(), request.getNewValue()));
            updateSdpWorkspaceConfig(newRequest);
        }
        return list.size();
    }
}

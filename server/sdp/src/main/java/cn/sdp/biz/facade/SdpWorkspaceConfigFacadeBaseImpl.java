package cn.sdp.biz.facade;


import cn.sdp.biz.convert.*;
import cn.sdp.biz.domain.*;
import cn.sdp.biz.dto.request.*;
import cn.sdp.biz.dto.response.*;
import cn.sdp.biz.repository.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: SdpWorkspaceConfigFacadeBaseImpl
 * @Description: FacadeBaseImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
public class SdpWorkspaceConfigFacadeBaseImpl extends BaseFacadeImpl implements SdpWorkspaceConfigFacade {
    @Autowired
    SdpWorkspaceConfigMapper sdpWorkspaceConfigMapper;

    public static Map<String, String> uniqueFieldMap = initUniqueFieldMap();

    public static Map initUniqueFieldMap() {
        HashMap map = new HashMap<>();
        map.put("unique_workspace_config_index_a", "workspace_name-name");
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpWorkspaceConfig(SdpWorkspaceConfigAddRequest request) throws Exception {
        SdpWorkspaceConfigWithBLOBs daoRequest = new SdpWorkspaceConfigAddRequestConvert().convert(request);
        int result = 0;
        try {
            result = sdpWorkspaceConfigMapper.insertSelective(daoRequest);
        }catch(Exception ex) {
            throw checkDBDupException(ex, uniqueFieldMap);
        }
        if (result > 0) {
            return daoRequest.getId();
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpWorkspaceConfig(SdpWorkspaceConfigUpdateRequest request) throws Exception {
        SdpWorkspaceConfigWithBLOBs daoRequest = new SdpWorkspaceConfigUpdateRequestConvert().convert(request);
        SdpWorkspaceConfigWithBLOBs record = sdpWorkspaceConfigMapper.selectByPrimaryKeyWithBLOBs(
                request.getId()
        );
        if (record == null) {
            throw new Exception("SdpWorkspaceConfig记录不存在:"+
                request.getId()
            );
        }
        int result = 0;
        try {
            result = sdpWorkspaceConfigMapper.updateByPrimaryKeySelective(daoRequest);
        }catch(Exception ex) {
            throw checkDBDupException(ex, uniqueFieldMap);
        }
        if (result > 0) {
            addHistory(record, "sdp_workspace_config");
            return result;
        }
        throw createUpdateFailException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpWorkspaceConfig(SdpWorkspaceConfigBatchUpdateRequest request) throws Exception {
        int result = 0;
        SdpWorkspaceConfigUpdateRequest destRequest = new SdpWorkspaceConfigUpdateRequest();
        BeanUtils.copyProperties(request, destRequest);
        for(int id : request.getIds()) {
            destRequest.setId(id);
            result += updateSdpWorkspaceConfig(destRequest);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpWorkspaceConfig(SdpWorkspaceConfigDeleteRequest request) throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        BeanUtils.copyProperties(request, baseRequest);

        for(int i = 0; i < request.getId().size(); i++) {
            SdpWorkspaceConfigWithBLOBs record = sdpWorkspaceConfigMapper.selectByPrimaryKeyWithBLOBs(
                request.getId().get(i)
            );
            if (record != null) {
                addHistory(record, "sdp_workspace_config");
                sdpWorkspaceConfigMapper.deleteByPrimaryKey(
                            request.getId().get(i)
                );
            }
        }
        return request.getId().size();
    }

    @Override
    public SdpWorkspaceConfigQueryResponse querySdpWorkspaceConfig(SdpWorkspaceConfigQueryRequest request) throws Exception {
        SdpWorkspaceConfigWithBLOBs daoResponse = sdpWorkspaceConfigMapper.selectByPrimaryKeyWithBLOBs(
            request.getId()
        );
        SdpWorkspaceConfigQueryResponse response = new SdpWorkspaceConfigQueryResponse();
        BeanUtils.copyProperties(daoResponse, response);
        return response;
    }

    @Override
    public List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfig(SdpWorkspaceConfigQueryRequest request) throws Exception {
        SdpWorkspaceConfig daoRequest = new SdpWorkspaceConfig();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpWorkspaceConfig> list;
        try {
            list = sdpWorkspaceConfigMapper.listWithoutBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpWorkspaceConfigQueryResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceConfig item : list) {
            SdpWorkspaceConfigQueryResponse newItem = new SdpWorkspaceConfigQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfigWithBLOBs(SdpWorkspaceConfigQueryRequest request) throws Exception {
        SdpWorkspaceConfigWithBLOBs daoRequest = new SdpWorkspaceConfigWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpWorkspaceConfigWithBLOBs> list;
        try {
            list = sdpWorkspaceConfigMapper.listWithBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpWorkspaceConfigQueryResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceConfigWithBLOBs item : list) {
            SdpWorkspaceConfigQueryResponse newItem = new SdpWorkspaceConfigQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfigByExample(SdpWorkspaceConfigQueryRequest request) throws Exception {
        SdpWorkspaceConfigWithBLOBs daoRequest = new SdpWorkspaceConfigWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpWorkspaceConfigExample example = SdpWorkspaceConfigExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpWorkspaceConfig> list;
        try {
            list = sdpWorkspaceConfigMapper.selectByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpWorkspaceConfigQueryResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceConfig item : list) {
            SdpWorkspaceConfigQueryResponse newItem = new SdpWorkspaceConfigQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    public List<SdpWorkspaceConfigQueryResponse> listSdpWorkspaceConfigByExampleWithBLOBs(SdpWorkspaceConfigQueryRequest request) throws Exception {
        SdpWorkspaceConfigWithBLOBs daoRequest = new SdpWorkspaceConfigWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpWorkspaceConfigExample example = SdpWorkspaceConfigExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpWorkspaceConfigWithBLOBs> list;
        try {
            list = sdpWorkspaceConfigMapper.selectByExampleWithBLOBs(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpWorkspaceConfigQueryResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceConfigWithBLOBs item : list) {
            SdpWorkspaceConfigQueryResponse newItem = new SdpWorkspaceConfigQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public int countSdpWorkspaceConfigByExample(SdpWorkspaceConfigQueryRequest request) throws Exception {
        SdpWorkspaceConfigWithBLOBs daoRequest = new SdpWorkspaceConfigWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpWorkspaceConfigExample example = SdpWorkspaceConfigExample.createExample(daoRequest, request.getQueryOptions());

        Integer count;
        try {
            count = sdpWorkspaceConfigMapper.countByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        return count;
    }

    @Override
    public Integer batchUpdateValue(SdpWorkspaceConfigAddRequest request) throws Exception {
        throw createFailException("not implement");
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        throw createFailException("not implement");
    }

}

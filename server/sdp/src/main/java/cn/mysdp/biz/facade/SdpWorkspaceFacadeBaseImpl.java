package cn.mysdp.biz.facade;


import cn.mysdp.biz.convert.*;
import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.*;
import cn.mysdp.biz.repository.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: SdpWorkspaceFacadeBaseImpl
 * @Description: FacadeBaseImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public class SdpWorkspaceFacadeBaseImpl extends BaseFacadeImpl implements SdpWorkspaceFacade {
    @Autowired
    SdpWorkspaceMapper sdpWorkspaceMapper;

    public static Map<String, String> uniqueFieldMap = initUniqueFieldMap();

    public static Map initUniqueFieldMap() {
        HashMap map = new HashMap<>();
        map.put("unique_workspace_index_3", "name");
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpWorkspace(SdpWorkspaceAddRequest request) throws Exception {
        SdpWorkspaceWithBLOBs daoRequest = new SdpWorkspaceAddRequestConvert().convert(request);
        int result = 0;
        try {
            result = sdpWorkspaceMapper.insertSelective(daoRequest);
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
    public int updateSdpWorkspace(SdpWorkspaceUpdateRequest request) throws Exception {
        SdpWorkspaceWithBLOBs daoRequest = new SdpWorkspaceUpdateRequestConvert().convert(request);
        SdpWorkspaceWithBLOBs record = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(
                request.getId()
        );
        if (record == null) {
            throw new Exception("SdpWorkspace记录不存在:"+
                request.getId()
            );
        }
        int result = 0;
        try {
            result = sdpWorkspaceMapper.updateByPrimaryKeySelective(daoRequest);
        }catch(Exception ex) {
            throw checkDBDupException(ex, uniqueFieldMap);
        }
        if (result > 0) {
            addHistory(record, "sdp_workspace");
            return result;
        }
        throw createUpdateFailException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpWorkspace(SdpWorkspaceBatchUpdateRequest request) throws Exception {
        int result = 0;
        SdpWorkspaceUpdateRequest destRequest = new SdpWorkspaceUpdateRequest();
        BeanUtils.copyProperties(request, destRequest);
        for(int id : request.getIds()) {
            destRequest.setId(id);
            result += updateSdpWorkspace(destRequest);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpWorkspace(SdpWorkspaceDeleteRequest request) throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        BeanUtils.copyProperties(request, baseRequest);

        for(int i = 0; i < request.getId().size(); i++) {
            SdpWorkspaceWithBLOBs record = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(
                request.getId().get(i)
            );
            if (record != null) {
                addHistory(record, "sdp_workspace");
                sdpWorkspaceMapper.deleteByPrimaryKey(
                            request.getId().get(i)
                );
            }
        }
        return request.getId().size();
    }

    @Override
    public SdpWorkspaceQueryResponse querySdpWorkspace(SdpWorkspaceQueryRequest request) throws Exception {
        SdpWorkspaceWithBLOBs daoResponse = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(
            request.getId()
        );
        SdpWorkspaceQueryResponse response = new SdpWorkspaceQueryResponse();
        BeanUtils.copyProperties(daoResponse, response);
        return response;
    }

    @Override
    public List<SdpWorkspaceQueryResponse> listSdpWorkspace(SdpWorkspaceQueryRequest request) throws Exception {
        SdpWorkspace daoRequest = new SdpWorkspace();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpWorkspace> list;
        try {
            list = sdpWorkspaceMapper.listWithoutBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpWorkspaceQueryResponse> responseList = new ArrayList<>();
        for(SdpWorkspace item : list) {
            SdpWorkspaceQueryResponse newItem = new SdpWorkspaceQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpWorkspaceQueryResponse> listSdpWorkspaceWithBLOBs(SdpWorkspaceQueryRequest request) throws Exception {
        SdpWorkspaceWithBLOBs daoRequest = new SdpWorkspaceWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpWorkspaceWithBLOBs> list;
        try {
            list = sdpWorkspaceMapper.listWithBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpWorkspaceQueryResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceWithBLOBs item : list) {
            SdpWorkspaceQueryResponse newItem = new SdpWorkspaceQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpWorkspaceQueryResponse> listSdpWorkspaceByExample(SdpWorkspaceQueryRequest request) throws Exception {
        SdpWorkspaceWithBLOBs daoRequest = new SdpWorkspaceWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpWorkspaceExample example = SdpWorkspaceExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpWorkspace> list;
        try {
            list = sdpWorkspaceMapper.selectByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpWorkspaceQueryResponse> responseList = new ArrayList<>();
        for(SdpWorkspace item : list) {
            SdpWorkspaceQueryResponse newItem = new SdpWorkspaceQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    public List<SdpWorkspaceQueryResponse> listSdpWorkspaceByExampleWithBLOBs(SdpWorkspaceQueryRequest request) throws Exception {
        SdpWorkspaceWithBLOBs daoRequest = new SdpWorkspaceWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpWorkspaceExample example = SdpWorkspaceExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpWorkspaceWithBLOBs> list;
        try {
            list = sdpWorkspaceMapper.selectByExampleWithBLOBs(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpWorkspaceQueryResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceWithBLOBs item : list) {
            SdpWorkspaceQueryResponse newItem = new SdpWorkspaceQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public int countSdpWorkspaceByExample(SdpWorkspaceQueryRequest request) throws Exception {
        SdpWorkspaceWithBLOBs daoRequest = new SdpWorkspaceWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpWorkspaceExample example = SdpWorkspaceExample.createExample(daoRequest, request.getQueryOptions());

        Integer count;
        try {
            count = sdpWorkspaceMapper.countByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        return count;
    }

    @Override
    public List<SdpWorkspaceGetDbConfigResponse> getDbConfig(SdpWorkspaceGetDbConfigRequest request) throws Exception {
        SdpWorkspaceForGetDbConfig daoRequest = new SdpWorkspaceForGetDbConfig();
        BeanUtils.copyProperties(request, daoRequest);
        List<SdpWorkspaceForGetDbConfig> list;
        try {
            list = sdpWorkspaceMapper.getDbConfig(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }
        List<SdpWorkspaceGetDbConfigResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceForGetDbConfig item : list) {
            SdpWorkspaceGetDbConfigResponse newItem = new SdpWorkspaceGetDbConfigResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }
        return responseList;
    }

    @Override
    public int countGetDbConfig(SdpWorkspaceGetDbConfigRequest request) throws Exception {
        SdpWorkspaceForGetDbConfig daoRequest = new SdpWorkspaceForGetDbConfig();
        BeanUtils.copyProperties(request, daoRequest);
        Integer count;
        try {
            count = sdpWorkspaceMapper.countGetDbConfig(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }
        return count;
    }

    @Override
    public List<SdpWorkspaceGetDbConfigResponse> getDbConfigByExample(SdpWorkspaceGetDbConfigRequest request) throws Exception {
        SdpWorkspaceForGetDbConfig daoRequest = new SdpWorkspaceForGetDbConfig();
        BeanUtils.copyProperties(request, daoRequest);
        List<SdpWorkspaceForGetDbConfig> list;
        SdpWorkspaceExampleForGetDbConfig example = SdpWorkspaceExampleForGetDbConfig.createExample(daoRequest, request.getQueryOptions());
        try {
            list = sdpWorkspaceMapper.getDbConfigByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }
        List<SdpWorkspaceGetDbConfigResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceForGetDbConfig item : list) {
            SdpWorkspaceGetDbConfigResponse newItem = new SdpWorkspaceGetDbConfigResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }
        return responseList;
    }

    @Override
    public int countGetDbConfigByExample(SdpWorkspaceGetDbConfigRequest request) throws Exception {
        SdpWorkspaceForGetDbConfig daoRequest = new SdpWorkspaceForGetDbConfig();
        BeanUtils.copyProperties(request, daoRequest);
        Integer count;
        SdpWorkspaceExampleForGetDbConfig example = SdpWorkspaceExampleForGetDbConfig.createExample(daoRequest, request.getQueryOptions());
        try {
            count = sdpWorkspaceMapper.countGetDbConfigByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }
        return count;
    }

    @Override
    public List<SdpWorkspaceGetTableListResponse> getTableList(SdpWorkspaceGetTableListRequest request) throws Exception {
        SdpWorkspaceForGetTableList daoRequest = new SdpWorkspaceForGetTableList();
        BeanUtils.copyProperties(request, daoRequest);
        List<SdpWorkspaceForGetTableList> list;
        try {
            list = sdpWorkspaceMapper.getTableList(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }
        List<SdpWorkspaceGetTableListResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceForGetTableList item : list) {
            SdpWorkspaceGetTableListResponse newItem = new SdpWorkspaceGetTableListResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }
        return responseList;
    }

    @Override
    public int countGetTableList(SdpWorkspaceGetTableListRequest request) throws Exception {
        SdpWorkspaceForGetTableList daoRequest = new SdpWorkspaceForGetTableList();
        BeanUtils.copyProperties(request, daoRequest);
        Integer count;
        try {
            count = sdpWorkspaceMapper.countGetTableList(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }
        return count;
    }

    @Override
    public List<SdpWorkspaceGetTableListResponse> getTableListByExample(SdpWorkspaceGetTableListRequest request) throws Exception {
        SdpWorkspaceForGetTableList daoRequest = new SdpWorkspaceForGetTableList();
        BeanUtils.copyProperties(request, daoRequest);
        List<SdpWorkspaceForGetTableList> list;
        SdpWorkspaceExampleForGetTableList example = SdpWorkspaceExampleForGetTableList.createExample(daoRequest, request.getQueryOptions());
        try {
            list = sdpWorkspaceMapper.getTableListByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }
        List<SdpWorkspaceGetTableListResponse> responseList = new ArrayList<>();
        for(SdpWorkspaceForGetTableList item : list) {
            SdpWorkspaceGetTableListResponse newItem = new SdpWorkspaceGetTableListResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }
        return responseList;
    }

    @Override
    public int countGetTableListByExample(SdpWorkspaceGetTableListRequest request) throws Exception {
        SdpWorkspaceForGetTableList daoRequest = new SdpWorkspaceForGetTableList();
        BeanUtils.copyProperties(request, daoRequest);
        Integer count;
        SdpWorkspaceExampleForGetTableList example = SdpWorkspaceExampleForGetTableList.createExample(daoRequest, request.getQueryOptions());
        try {
            count = sdpWorkspaceMapper.countGetTableListByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }
        return count;
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        throw createFailException("not implement");
    }

    @Override
    public Integer cloneWorkspace(BaseNameIdRequest request) throws Exception {
        throw createFailException("not implement");
    }

    @Override
    public String testConnect(SdpWorkspaceUpdateRequest request) throws Exception {
        throw createFailException("not implement");
    }

    @Override
    public String fixJsonObject(BaseNameRequest request) throws Exception {
        throw createFailException("not implement");
    }

}

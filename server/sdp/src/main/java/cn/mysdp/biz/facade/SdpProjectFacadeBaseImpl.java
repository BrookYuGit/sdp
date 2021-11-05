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
 * @ClassName: SdpProjectFacadeBaseImpl
 * @Description: FacadeBaseImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
public class SdpProjectFacadeBaseImpl extends BaseFacadeImpl implements SdpProjectFacade {
    @Autowired
    SdpProjectMapper sdpProjectMapper;

    public static Map<String, String> uniqueFieldMap = initUniqueFieldMap();

    public static Map initUniqueFieldMap() {
        HashMap map = new HashMap<>();
        map.put("unique_project_index_a", "workspace_name-name");
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpProject(SdpProjectAddRequest request) throws Exception {
        SdpProjectWithBLOBs daoRequest = new SdpProjectAddRequestConvert().convert(request);
        int result = 0;
        try {
            result = sdpProjectMapper.insertSelective(daoRequest);
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
    public int updateSdpProject(SdpProjectUpdateRequest request) throws Exception {
        SdpProjectWithBLOBs daoRequest = new SdpProjectUpdateRequestConvert().convert(request);
        SdpProjectWithBLOBs record = sdpProjectMapper.selectByPrimaryKeyWithBLOBs(
                request.getId()
        );
        if (record == null) {
            throw new Exception("SdpProject记录不存在:"+
                request.getId()
            );
        }
        int result = 0;
        try {
            result = sdpProjectMapper.updateByPrimaryKeySelective(daoRequest);
        }catch(Exception ex) {
            throw checkDBDupException(ex, uniqueFieldMap);
        }
        if (result > 0) {
            addHistory(record, "sdp_project");
            return result;
        }
        throw createUpdateFailException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpProject(SdpProjectBatchUpdateRequest request) throws Exception {
        int result = 0;
        SdpProjectUpdateRequest destRequest = new SdpProjectUpdateRequest();
        BeanUtils.copyProperties(request, destRequest);
        for(int id : request.getIds()) {
            destRequest.setId(id);
            result += updateSdpProject(destRequest);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpProject(SdpProjectDeleteRequest request) throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        BeanUtils.copyProperties(request, baseRequest);

        for(int i = 0; i < request.getId().size(); i++) {
            SdpProjectWithBLOBs record = sdpProjectMapper.selectByPrimaryKeyWithBLOBs(
                request.getId().get(i)
            );
            if (record != null) {
                addHistory(record, "sdp_project");
                sdpProjectMapper.deleteByPrimaryKey(
                            request.getId().get(i)
                );
            }
        }
        return request.getId().size();
    }

    @Override
    public SdpProjectQueryResponse querySdpProject(SdpProjectQueryRequest request) throws Exception {
        SdpProjectWithBLOBs daoResponse = sdpProjectMapper.selectByPrimaryKeyWithBLOBs(
            request.getId()
        );
        SdpProjectQueryResponse response = new SdpProjectQueryResponse();
        BeanUtils.copyProperties(daoResponse, response);
        return response;
    }

    @Override
    public List<SdpProjectQueryResponse> listSdpProject(SdpProjectQueryRequest request) throws Exception {
        SdpProject daoRequest = new SdpProject();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpProject> list;
        try {
            list = sdpProjectMapper.listWithoutBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpProjectQueryResponse> responseList = new ArrayList<>();
        for(SdpProject item : list) {
            SdpProjectQueryResponse newItem = new SdpProjectQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpProjectQueryResponse> listSdpProjectWithBLOBs(SdpProjectQueryRequest request) throws Exception {
        SdpProjectWithBLOBs daoRequest = new SdpProjectWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpProjectWithBLOBs> list;
        try {
            list = sdpProjectMapper.listWithBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpProjectQueryResponse> responseList = new ArrayList<>();
        for(SdpProjectWithBLOBs item : list) {
            SdpProjectQueryResponse newItem = new SdpProjectQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpProjectQueryResponse> listSdpProjectByExample(SdpProjectQueryRequest request) throws Exception {
        SdpProjectWithBLOBs daoRequest = new SdpProjectWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpProjectExample example = SdpProjectExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpProject> list;
        try {
            list = sdpProjectMapper.selectByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpProjectQueryResponse> responseList = new ArrayList<>();
        for(SdpProject item : list) {
            SdpProjectQueryResponse newItem = new SdpProjectQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    public List<SdpProjectQueryResponse> listSdpProjectByExampleWithBLOBs(SdpProjectQueryRequest request) throws Exception {
        SdpProjectWithBLOBs daoRequest = new SdpProjectWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpProjectExample example = SdpProjectExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpProjectWithBLOBs> list;
        try {
            list = sdpProjectMapper.selectByExampleWithBLOBs(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpProjectQueryResponse> responseList = new ArrayList<>();
        for(SdpProjectWithBLOBs item : list) {
            SdpProjectQueryResponse newItem = new SdpProjectQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public int countSdpProjectByExample(SdpProjectQueryRequest request) throws Exception {
        SdpProjectWithBLOBs daoRequest = new SdpProjectWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpProjectExample example = SdpProjectExample.createExample(daoRequest, request.getQueryOptions());

        Integer count;
        try {
            count = sdpProjectMapper.countByExample(example);
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
    public Integer execute(BaseNameRequest request) throws Exception {
        throw createFailException("not implement");
    }

}

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
 * @ClassName: SdpTemplateFacadeBaseImpl
 * @Description: FacadeBaseImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
public class SdpTemplateFacadeBaseImpl extends BaseFacadeImpl implements SdpTemplateFacade {
    @Autowired
    SdpTemplateMapper sdpTemplateMapper;

    public static Map<String, String> uniqueFieldMap = initUniqueFieldMap();

    public static Map initUniqueFieldMap() {
        HashMap map = new HashMap<>();
        map.put("unique_template_index_4", "workspace_name-project_name-name-file_type-project-package_name");
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpTemplate(SdpTemplateAddRequest request) throws Exception {
        SdpTemplateWithBLOBs daoRequest = new SdpTemplateAddRequestConvert().convert(request);
        int result = 0;
        try {
            result = sdpTemplateMapper.insertSelective(daoRequest);
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
    public int updateSdpTemplate(SdpTemplateUpdateRequest request) throws Exception {
        SdpTemplateWithBLOBs daoRequest = new SdpTemplateUpdateRequestConvert().convert(request);
        SdpTemplateWithBLOBs record = sdpTemplateMapper.selectByPrimaryKeyWithBLOBs(
                request.getId()
        );
        if (record == null) {
            throw new Exception("SdpTemplate记录不存在:"+
                request.getId()
            );
        }
        int result = 0;
        try {
            result = sdpTemplateMapper.updateByPrimaryKeySelective(daoRequest);
        }catch(Exception ex) {
            throw checkDBDupException(ex, uniqueFieldMap);
        }
        if (result > 0) {
            addHistory(record, "sdp_template");
            return result;
        }
        throw createUpdateFailException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpTemplate(SdpTemplateBatchUpdateRequest request) throws Exception {
        int result = 0;
        SdpTemplateUpdateRequest destRequest = new SdpTemplateUpdateRequest();
        BeanUtils.copyProperties(request, destRequest);
        for(int id : request.getIds()) {
            destRequest.setId(id);
            result += updateSdpTemplate(destRequest);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpTemplate(SdpTemplateDeleteRequest request) throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        BeanUtils.copyProperties(request, baseRequest);

        for(int i = 0; i < request.getId().size(); i++) {
            SdpTemplateWithBLOBs record = sdpTemplateMapper.selectByPrimaryKeyWithBLOBs(
                request.getId().get(i)
            );
            if (record != null) {
                addHistory(record, "sdp_template");
                sdpTemplateMapper.deleteByPrimaryKey(
                            request.getId().get(i)
                );
            }
        }
        return request.getId().size();
    }

    @Override
    public SdpTemplateQueryResponse querySdpTemplate(SdpTemplateQueryRequest request) throws Exception {
        SdpTemplateWithBLOBs daoResponse = sdpTemplateMapper.selectByPrimaryKeyWithBLOBs(
            request.getId()
        );
        SdpTemplateQueryResponse response = new SdpTemplateQueryResponse();
        BeanUtils.copyProperties(daoResponse, response);
        return response;
    }

    @Override
    public List<SdpTemplateQueryResponse> listSdpTemplate(SdpTemplateQueryRequest request) throws Exception {
        SdpTemplate daoRequest = new SdpTemplate();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpTemplate> list;
        try {
            list = sdpTemplateMapper.listWithoutBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpTemplateQueryResponse> responseList = new ArrayList<>();
        for(SdpTemplate item : list) {
            SdpTemplateQueryResponse newItem = new SdpTemplateQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpTemplateQueryResponse> listSdpTemplateWithBLOBs(SdpTemplateQueryRequest request) throws Exception {
        SdpTemplateWithBLOBs daoRequest = new SdpTemplateWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpTemplateWithBLOBs> list;
        try {
            list = sdpTemplateMapper.listWithBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpTemplateQueryResponse> responseList = new ArrayList<>();
        for(SdpTemplateWithBLOBs item : list) {
            SdpTemplateQueryResponse newItem = new SdpTemplateQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpTemplateQueryResponse> listSdpTemplateByExample(SdpTemplateQueryRequest request) throws Exception {
        SdpTemplateWithBLOBs daoRequest = new SdpTemplateWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpTemplateExample example = SdpTemplateExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpTemplate> list;
        try {
            list = sdpTemplateMapper.selectByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpTemplateQueryResponse> responseList = new ArrayList<>();
        for(SdpTemplate item : list) {
            SdpTemplateQueryResponse newItem = new SdpTemplateQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    public List<SdpTemplateQueryResponse> listSdpTemplateByExampleWithBLOBs(SdpTemplateQueryRequest request) throws Exception {
        SdpTemplateWithBLOBs daoRequest = new SdpTemplateWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpTemplateExample example = SdpTemplateExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpTemplateWithBLOBs> list;
        try {
            list = sdpTemplateMapper.selectByExampleWithBLOBs(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpTemplateQueryResponse> responseList = new ArrayList<>();
        for(SdpTemplateWithBLOBs item : list) {
            SdpTemplateQueryResponse newItem = new SdpTemplateQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public int countSdpTemplateByExample(SdpTemplateQueryRequest request) throws Exception {
        SdpTemplateWithBLOBs daoRequest = new SdpTemplateWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpTemplateExample example = SdpTemplateExample.createExample(daoRequest, request.getQueryOptions());

        Integer count;
        try {
            count = sdpTemplateMapper.countByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        return count;
    }

}

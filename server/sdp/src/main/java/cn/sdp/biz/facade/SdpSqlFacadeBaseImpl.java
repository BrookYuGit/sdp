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
 * @ClassName: SdpSqlFacadeBaseImpl
 * @Description: FacadeBaseImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
public class SdpSqlFacadeBaseImpl extends BaseFacadeImpl implements SdpSqlFacade {
    @Autowired
    SdpSqlMapper sdpSqlMapper;

    public static Map<String, String> uniqueFieldMap = initUniqueFieldMap();

    public static Map initUniqueFieldMap() {
        HashMap map = new HashMap<>();
        map.put("unique_sql_index_9", "workspace_name-table_name-parameter_catalog-parameter_catalog_type-name");
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpSql(SdpSqlAddRequest request) throws Exception {
        SdpSqlWithBLOBs daoRequest = new SdpSqlAddRequestConvert().convert(request);
        int result = 0;
        try {
            result = sdpSqlMapper.insertSelective(daoRequest);
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
    public int updateSdpSql(SdpSqlUpdateRequest request) throws Exception {
        SdpSqlWithBLOBs daoRequest = new SdpSqlUpdateRequestConvert().convert(request);
        SdpSqlWithBLOBs record = sdpSqlMapper.selectByPrimaryKeyWithBLOBs(
                request.getId()
        );
        if (record == null) {
            throw new Exception("SdpSql记录不存在:"+
                request.getId()
            );
        }
        int result = 0;
        try {
            result = sdpSqlMapper.updateByPrimaryKeySelective(daoRequest);
        }catch(Exception ex) {
            throw checkDBDupException(ex, uniqueFieldMap);
        }
        if (result > 0) {
            addHistory(record, "sdp_sql");
            return result;
        }
        throw createUpdateFailException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpSql(SdpSqlBatchUpdateRequest request) throws Exception {
        int result = 0;
        SdpSqlUpdateRequest destRequest = new SdpSqlUpdateRequest();
        BeanUtils.copyProperties(request, destRequest);
        for(int id : request.getIds()) {
            destRequest.setId(id);
            result += updateSdpSql(destRequest);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpSql(SdpSqlDeleteRequest request) throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        BeanUtils.copyProperties(request, baseRequest);

        for(int i = 0; i < request.getId().size(); i++) {
            SdpSqlWithBLOBs record = sdpSqlMapper.selectByPrimaryKeyWithBLOBs(
                request.getId().get(i)
            );
            if (record != null) {
                addHistory(record, "sdp_sql");
                sdpSqlMapper.deleteByPrimaryKey(
                            request.getId().get(i)
                );
            }
        }
        return request.getId().size();
    }

    @Override
    public SdpSqlQueryResponse querySdpSql(SdpSqlQueryRequest request) throws Exception {
        SdpSqlWithBLOBs daoResponse = sdpSqlMapper.selectByPrimaryKeyWithBLOBs(
            request.getId()
        );
        SdpSqlQueryResponse response = new SdpSqlQueryResponse();
        BeanUtils.copyProperties(daoResponse, response);
        return response;
    }

    @Override
    public List<SdpSqlQueryResponse> listSdpSql(SdpSqlQueryRequest request) throws Exception {
        SdpSql daoRequest = new SdpSql();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpSql> list;
        try {
            list = sdpSqlMapper.listWithoutBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpSqlQueryResponse> responseList = new ArrayList<>();
        for(SdpSql item : list) {
            SdpSqlQueryResponse newItem = new SdpSqlQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpSqlQueryResponse> listSdpSqlWithBLOBs(SdpSqlQueryRequest request) throws Exception {
        SdpSqlWithBLOBs daoRequest = new SdpSqlWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpSqlWithBLOBs> list;
        try {
            list = sdpSqlMapper.listWithBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpSqlQueryResponse> responseList = new ArrayList<>();
        for(SdpSqlWithBLOBs item : list) {
            SdpSqlQueryResponse newItem = new SdpSqlQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpSqlQueryResponse> listSdpSqlByExample(SdpSqlQueryRequest request) throws Exception {
        SdpSqlWithBLOBs daoRequest = new SdpSqlWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpSqlExample example = SdpSqlExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpSql> list;
        try {
            list = sdpSqlMapper.selectByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpSqlQueryResponse> responseList = new ArrayList<>();
        for(SdpSql item : list) {
            SdpSqlQueryResponse newItem = new SdpSqlQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    public List<SdpSqlQueryResponse> listSdpSqlByExampleWithBLOBs(SdpSqlQueryRequest request) throws Exception {
        SdpSqlWithBLOBs daoRequest = new SdpSqlWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpSqlExample example = SdpSqlExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpSqlWithBLOBs> list;
        try {
            list = sdpSqlMapper.selectByExampleWithBLOBs(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpSqlQueryResponse> responseList = new ArrayList<>();
        for(SdpSqlWithBLOBs item : list) {
            SdpSqlQueryResponse newItem = new SdpSqlQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public int countSdpSqlByExample(SdpSqlQueryRequest request) throws Exception {
        SdpSqlWithBLOBs daoRequest = new SdpSqlWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpSqlExample example = SdpSqlExample.createExample(daoRequest, request.getQueryOptions());

        Integer count;
        try {
            count = sdpSqlMapper.countByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        return count;
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        throw createFailException("not implement");
    }

}

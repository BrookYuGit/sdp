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
 * @ClassName: SdpTableFacadeBaseImpl
 * @Description: FacadeBaseImpl
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
public class SdpTableFacadeBaseImpl extends BaseFacadeImpl implements SdpTableFacade {
    @Autowired
    SdpTableMapper sdpTableMapper;

    public static Map<String, String> uniqueFieldMap = initUniqueFieldMap();

    public static Map initUniqueFieldMap() {
        HashMap map = new HashMap<>();
        map.put("unique_table_index_f", "workspace_name-name");
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpTable(SdpTableAddRequest request) throws Exception {
        SdpTableWithBLOBs daoRequest = new SdpTableAddRequestConvert().convert(request);
        int result = 0;
        try {
            result = sdpTableMapper.insertSelective(daoRequest);
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
    public int updateSdpTable(SdpTableUpdateRequest request) throws Exception {
        SdpTableWithBLOBs daoRequest = new SdpTableUpdateRequestConvert().convert(request);
        SdpTableWithBLOBs record = sdpTableMapper.selectByPrimaryKeyWithBLOBs(
                request.getId()
        );
        if (record == null) {
            throw new Exception("SdpTable记录不存在:"+
                request.getId()
            );
        }
        int result = 0;
        try {
            result = sdpTableMapper.updateByPrimaryKeySelective(daoRequest);
        }catch(Exception ex) {
            throw checkDBDupException(ex, uniqueFieldMap);
        }
        if (result > 0) {
            addHistory(record, "sdp_table");
            return result;
        }
        throw createUpdateFailException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpTable(SdpTableBatchUpdateRequest request) throws Exception {
        int result = 0;
        SdpTableUpdateRequest destRequest = new SdpTableUpdateRequest();
        BeanUtils.copyProperties(request, destRequest);
        for(int id : request.getIds()) {
            destRequest.setId(id);
            result += updateSdpTable(destRequest);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpTable(SdpTableDeleteRequest request) throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        BeanUtils.copyProperties(request, baseRequest);

        for(int i = 0; i < request.getId().size(); i++) {
            SdpTableWithBLOBs record = sdpTableMapper.selectByPrimaryKeyWithBLOBs(
                request.getId().get(i)
            );
            if (record != null) {
                addHistory(record, "sdp_table");
                sdpTableMapper.deleteByPrimaryKey(
                            request.getId().get(i)
                );
            }
        }
        return request.getId().size();
    }

    @Override
    public SdpTableQueryResponse querySdpTable(SdpTableQueryRequest request) throws Exception {
        SdpTableWithBLOBs daoResponse = sdpTableMapper.selectByPrimaryKeyWithBLOBs(
            request.getId()
        );
        SdpTableQueryResponse response = new SdpTableQueryResponse();
        BeanUtils.copyProperties(daoResponse, response);
        return response;
    }

    @Override
    public List<SdpTableQueryResponse> listSdpTable(SdpTableQueryRequest request) throws Exception {
        SdpTable daoRequest = new SdpTable();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpTable> list;
        try {
            list = sdpTableMapper.listWithoutBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpTableQueryResponse> responseList = new ArrayList<>();
        for(SdpTable item : list) {
            SdpTableQueryResponse newItem = new SdpTableQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpTableQueryResponse> listSdpTableWithBLOBs(SdpTableQueryRequest request) throws Exception {
        SdpTableWithBLOBs daoRequest = new SdpTableWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpTableWithBLOBs> list;
        try {
            list = sdpTableMapper.listWithBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpTableQueryResponse> responseList = new ArrayList<>();
        for(SdpTableWithBLOBs item : list) {
            SdpTableQueryResponse newItem = new SdpTableQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpTableQueryResponse> listSdpTableByExample(SdpTableQueryRequest request) throws Exception {
        SdpTableWithBLOBs daoRequest = new SdpTableWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpTableExample example = SdpTableExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpTable> list;
        try {
            list = sdpTableMapper.selectByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpTableQueryResponse> responseList = new ArrayList<>();
        for(SdpTable item : list) {
            SdpTableQueryResponse newItem = new SdpTableQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    public List<SdpTableQueryResponse> listSdpTableByExampleWithBLOBs(SdpTableQueryRequest request) throws Exception {
        SdpTableWithBLOBs daoRequest = new SdpTableWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpTableExample example = SdpTableExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpTableWithBLOBs> list;
        try {
            list = sdpTableMapper.selectByExampleWithBLOBs(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpTableQueryResponse> responseList = new ArrayList<>();
        for(SdpTableWithBLOBs item : list) {
            SdpTableQueryResponse newItem = new SdpTableQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public int countSdpTableByExample(SdpTableQueryRequest request) throws Exception {
        SdpTableWithBLOBs daoRequest = new SdpTableWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpTableExample example = SdpTableExample.createExample(daoRequest, request.getQueryOptions());

        Integer count;
        try {
            count = sdpTableMapper.countByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        return count;
    }

}

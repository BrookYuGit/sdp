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
 * @ClassName: SdpHistoryFacadeBaseImpl
 * @Description: FacadeBaseImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
public class SdpHistoryFacadeBaseImpl extends BaseFacadeImpl implements SdpHistoryFacade {
    @Autowired
    SdpHistoryMapper sdpHistoryMapper;

    public static Map<String, String> uniqueFieldMap = initUniqueFieldMap();

    public static Map initUniqueFieldMap() {
        HashMap map = new HashMap<>();
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpHistory(SdpHistoryAddRequest request) throws Exception {
        SdpHistoryWithBLOBs daoRequest = new SdpHistoryAddRequestConvert().convert(request);
        int result = 0;
        try {
            result = sdpHistoryMapper.insertSelective(daoRequest);
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
    public int updateSdpHistory(SdpHistoryUpdateRequest request) throws Exception {
        SdpHistoryWithBLOBs daoRequest = new SdpHistoryUpdateRequestConvert().convert(request);
        SdpHistoryWithBLOBs record = sdpHistoryMapper.selectByPrimaryKeyWithBLOBs(
                request.getId()
        );
        if (record == null) {
            throw new Exception("SdpHistory记录不存在:"+
                request.getId()
            );
        }
        int result = 0;
        try {
            result = sdpHistoryMapper.updateByPrimaryKeySelective(daoRequest);
        }catch(Exception ex) {
            throw checkDBDupException(ex, uniqueFieldMap);
        }
        if (result > 0) {
            addHistory(record, "sdp_history");
            return result;
        }
        throw createUpdateFailException();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpHistory(SdpHistoryBatchUpdateRequest request) throws Exception {
        int result = 0;
        SdpHistoryUpdateRequest destRequest = new SdpHistoryUpdateRequest();
        BeanUtils.copyProperties(request, destRequest);
        for(int id : request.getIds()) {
            destRequest.setId(id);
            result += updateSdpHistory(destRequest);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpHistory(SdpHistoryDeleteRequest request) throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        BeanUtils.copyProperties(request, baseRequest);

        for(int i = 0; i < request.getId().size(); i++) {
            SdpHistoryWithBLOBs record = sdpHistoryMapper.selectByPrimaryKeyWithBLOBs(
                request.getId().get(i)
            );
            if (record != null) {
                addHistory(record, "sdp_history");
                sdpHistoryMapper.deleteByPrimaryKey(
                            request.getId().get(i)
                );
            }
        }
        return request.getId().size();
    }

    @Override
    public SdpHistoryQueryResponse querySdpHistory(SdpHistoryQueryRequest request) throws Exception {
        SdpHistoryWithBLOBs daoResponse = sdpHistoryMapper.selectByPrimaryKeyWithBLOBs(
            request.getId()
        );
        SdpHistoryQueryResponse response = new SdpHistoryQueryResponse();
        BeanUtils.copyProperties(daoResponse, response);
        return response;
    }

    @Override
    public List<SdpHistoryQueryResponse> listSdpHistory(SdpHistoryQueryRequest request) throws Exception {
        SdpHistory daoRequest = new SdpHistory();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpHistory> list;
        try {
            list = sdpHistoryMapper.listWithoutBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpHistoryQueryResponse> responseList = new ArrayList<>();
        for(SdpHistory item : list) {
            SdpHistoryQueryResponse newItem = new SdpHistoryQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpHistoryQueryResponse> listSdpHistoryWithBLOBs(SdpHistoryQueryRequest request) throws Exception {
        SdpHistoryWithBLOBs daoRequest = new SdpHistoryWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        List<SdpHistoryWithBLOBs> list;
        try {
            list = sdpHistoryMapper.listWithBLOBs(daoRequest);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpHistoryQueryResponse> responseList = new ArrayList<>();
        for(SdpHistoryWithBLOBs item : list) {
            SdpHistoryQueryResponse newItem = new SdpHistoryQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public List<SdpHistoryQueryResponse> listSdpHistoryByExample(SdpHistoryQueryRequest request) throws Exception {
        SdpHistoryWithBLOBs daoRequest = new SdpHistoryWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpHistoryExample example = SdpHistoryExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpHistory> list;
        try {
            list = sdpHistoryMapper.selectByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpHistoryQueryResponse> responseList = new ArrayList<>();
        for(SdpHistory item : list) {
            SdpHistoryQueryResponse newItem = new SdpHistoryQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    public List<SdpHistoryQueryResponse> listSdpHistoryByExampleWithBLOBs(SdpHistoryQueryRequest request) throws Exception {
        SdpHistoryWithBLOBs daoRequest = new SdpHistoryWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpHistoryExample example = SdpHistoryExample.createExample(daoRequest, request.getQueryOptions());

        List<SdpHistoryWithBLOBs> list;
        try {
            list = sdpHistoryMapper.selectByExampleWithBLOBs(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        List<SdpHistoryQueryResponse> responseList = new ArrayList<>();
        for(SdpHistoryWithBLOBs item : list) {
            SdpHistoryQueryResponse newItem = new SdpHistoryQueryResponse();
            BeanUtils.copyProperties(item, newItem);
            responseList.add(newItem);
        }

        return responseList;
    }

    @Override
    public int countSdpHistoryByExample(SdpHistoryQueryRequest request) throws Exception {
        SdpHistoryWithBLOBs daoRequest = new SdpHistoryWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);
        SdpHistoryExample example = SdpHistoryExample.createExample(daoRequest, request.getQueryOptions());

        Integer count;
        try {
            count = sdpHistoryMapper.countByExample(example);
        } catch (Exception ex) {
            throw createDatabaseFailException(ex);
        }

        return count;
    }

    @Override
    public int clear(BaseRequest request) throws Exception {
        throw createFailException("not implement");
    }

}

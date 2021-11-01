package cn.sdp.biz.facade;

import cn.sdp.biz.domain.SdpProjectExample;
import cn.sdp.biz.domain.SdpProjectWithBLOBs;
import cn.sdp.biz.domain.SdpTemplateExample;
import cn.sdp.biz.domain.SdpTemplateWithBLOBs;
import cn.sdp.biz.dto.request.*;
import cn.sdp.biz.repository.SdpProjectMapper;
import cn.sdp.biz.repository.SdpTemplateMapper;
import cn.sdp.biz.repository.SdpWorkspaceConfigMapper;
import cn.sdp.utils.ByteWithPos;
import cn.sdp.biz.domain.*;
import cn.sdp.biz.dto.request.*;
import cn.sdp.biz.dto.response.SdpTemplateQueryResponse;
import cn.sdp.biz.repository.*;
import cn.sdp.utils.DynProcessTokenResult;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

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
public class SdpProjectFacadeCustomImpl extends SdpProjectFacadeBaseImpl {

    @Autowired
    SdpProjectMapper sdpProjectMapper;

    @Autowired
    SdpWorkspaceConfigMapper sdpWorkspaceConfigMapper;

    @Autowired
    SdpTemplateMapper sdpTemplateMapper;

    @Autowired
    SdpTemplateFacade sdpTemplateFacade;

    @Autowired
    ProcessSQLFacade processSQLFacade;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpProject(SdpProjectAddRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getName());
        checkName(request.getTables(), "abcdefghijklmnopqrstuvwxyz1234567890_, ", "必须为小写字母、数字、下滑线、逗号、空格");
        return super.addSdpProject(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpProject(SdpProjectUpdateRequest request) throws Exception {
        SdpProjectWithBLOBs record = sdpProjectMapper.selectByPrimaryKeyWithBLOBs(request.getId());

        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getName());
        checkName(request.getTables(), "abcdefghijklmnopqrstuvwxyz1234567890_, ", "必须为小写字母、数字、下滑线、逗号、空格");

        if (!StringUtils.isEmpty(request.getName())) {

            if (!request.getName().equals(record.getName())) {
                SdpTemplateExample destExample = new SdpTemplateExample();
                String workspaceName = request.getWorkspaceName();
                if (workspaceName == null) {
                    workspaceName = record.getWorkspaceName();
                }
                destExample.createCriteria().andWorkspaceNameEqualTo(workspaceName).andProjectNameEqualTo(record.getName());
                List<SdpTemplateWithBLOBs> destList = sdpTemplateMapper.selectByExampleWithBLOBs(destExample);
                SdpTemplateBatchUpdateRequest destBatchUpdateRequest = new SdpTemplateBatchUpdateRequest();
                destBatchUpdateRequest.setProjectName(request.getName());
                destBatchUpdateRequest.setIds(new ArrayList<>());
                for(SdpTemplateWithBLOBs destItem: destList) {
                    destBatchUpdateRequest.getIds().add(destItem.getId());
                }
                sdpTemplateFacade.batchUpdateSdpTemplate(destBatchUpdateRequest);
            }
        }
        return super.updateSdpProject(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpProject(SdpProjectBatchUpdateRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getName());
        checkName(request.getTables(), "abcdefghijklmnopqrstuvwxyz1234567890_, ", "必须为小写字母、数字、下滑线、逗号、空格");
        return super.batchUpdateSdpProject(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpProject(SdpProjectDeleteRequest request) throws Exception {

        for(int id : request.getId()) {
            SdpProjectWithBLOBs record = sdpProjectMapper.selectByPrimaryKeyWithBLOBs(id);
            if (record == null) {
                continue;
            }

            SdpTemplateExample destExample = new SdpTemplateExample();
            destExample.createCriteria().andWorkspaceNameEqualTo(record.getWorkspaceName()).andProjectNameEqualTo(record.getName());
            if (!CollectionUtils.isEmpty(sdpTemplateMapper.selectByExample(destExample))) {
                throw new Exception("exist template for this project："+record.getWorkspaceName()+"/"+record.getName());
            }
        }
        return super.deleteSdpProject(request);
    }

    @Override
    public Integer execute(BaseNameRequest request) throws Exception {
        return processSQLFacade.execute(request, this, getMethod(this.getClass(), "processBodyToken"), getMethod(this.getClass(), "processToken"));
    }

    private DynProcessTokenResult processTokenProperties(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<IntrospectedColumn> columns, Integer columnIndex, String token, Map<String, String> properties) throws Exception {
        DynProcessTokenResult result = new DynProcessTokenResult();
        return result;
    }

    private DynProcessTokenResult processToken(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<IntrospectedColumn> columns, Integer columnIndex, String token, Map<String, String> properties) throws Exception {
        DynProcessTokenResult result = new DynProcessTokenResult();
        String v = null;

        IntrospectedColumn column = null;
        if (columns != null && columnIndex > 0 && columnIndex <= columns.size()) {
            column = columns.get(columnIndex - 1);
        }

        Map<String, String> propertiesIsAjax = new HashMap<>();
        propertiesIsAjax.put("is_ajax", "1");

        do {
            DynProcessTokenResult result1 = processTokenProperties(introspectedTable, dynTemplate, fileName, sqlMethodName, columns, columnIndex, token, properties);
            if (result1.getProcessed()) {
                result.setProcessed(true);
                result.setResult(result1.getResult());
                continue;
            }
            result1 = processSQLFacade.processTokenProperties(introspectedTable, dynTemplate, fileName, sqlMethodName, columns, columnIndex, token, properties);
            if (result1.getProcessed()) {
                result.setProcessed(true);
                result.setResult(result1.getResult());
                continue;
            }

            if (processSQLFacade.isToken(token, "column_jsonformat_for_date")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                if ("DATE".equals(column.getJdbcTypeName())) {
                    v = "@JsonFormat(pattern = \"yyyy-MM-dd\", timezone = \"GMT+8\")";
                } else if ("TIMESTAMP".equals(column.getJdbcTypeName())) {
                    v = "@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")";
                } else if ("DATETIME".equals(column.getJdbcTypeName())) {
                    v = "@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")";
                } else if ("Date".equals(column.getFullyQualifiedJavaType().getShortName())) {
                    v = "@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")";
                } else if ("Timestamp".equals(column.getFullyQualifiedJavaType().getShortName())) {
                    v = "@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")";
                }
            } else if (processSQLFacade.isToken(token, "column_jsonfield_for_date")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                if ("DATE".equals(column.getJdbcTypeName())) {
                    v = "format = \"yyyy-MM-dd\", ";
                } else if ("TIMESTAMP".equals(column.getJdbcTypeName())) {
                    v = "format = \"yyyy-MM-dd HH:mm:ss\", ";
                } else if ("DATETIME".equals(column.getJdbcTypeName())) {
                    v = "format = \"yyyy-MM-dd HH:mm:ss\", ";
                } else if ("Date".equals(column.getFullyQualifiedJavaType().getShortName())) {
                    v = "format = \"yyyy-MM-dd HH:mm:ss\", ";
                } else if ("Timestamp".equals(column.getFullyQualifiedJavaType().getShortName())) {
                    v = "format = \"yyyy-MM-dd HH:mm:ss\", ";
                }
            }
        }while(false);
        result.setResult(v);
        if (!StringUtils.isEmpty(v)) {
            result.setProcessed(true);
        }
        return result;
    }

    private DynProcessTokenResult processBodyTokenProperties(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<ByteWithPos> lineBytesList, List<String> lineStringList, List<String> lineTrimStringList, List<Integer> deepList, int deep, ByteWithPos destBytes, String vTrim, Map<String, String> properties, List<IntrospectedColumn> inColumns) throws Exception {
        DynProcessTokenResult result = new DynProcessTokenResult();
        return result;
    }

    private DynProcessTokenResult processBodyToken(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<ByteWithPos> lineBytesList, List<String> lineStringList, List<String> lineTrimStringList, List<Integer> deepList, int deep, ByteWithPos destBytes, String vTrim, Map<String, String> properties, List<IntrospectedColumn> inColumns) throws Exception {
        DynProcessTokenResult result = new DynProcessTokenResult();

        List<IntrospectedColumn> introspectedColumns = null;

        List<IntrospectedColumn> extraColumns = new ArrayList<>();

        {
            DynProcessTokenResult result1 = processBodyTokenProperties(introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytesList, lineStringList, lineTrimStringList, deepList, deep, destBytes, vTrim, properties, inColumns);
            if (result1.getProcessed()) {
                result.setProcessed(true);
                result.setResult(result1.getResult());
                return result;
            }

            result1 = processSQLFacade.processBodyTokenProperties(introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytesList, lineStringList, lineTrimStringList, deepList, deep, destBytes, vTrim, properties, inColumns);
            if (result1.getProcessed()) {
                result.setProcessed(true);
                result.setResult(result1.getResult());
                return result;
            }
        }

        if (processSQLFacade.isBlockToken(vTrim,"primary_key")) {
            result.setProcessed(true);
            introspectedColumns = new ArrayList<>();
            IntrospectedColumn newColumn = new IntrospectedColumn();
            introspectedColumns.add(newColumn);
            newColumn.setActualColumnName("primary_key");
            newColumn.setFullyQualifiedJavaType(FullyQualifiedJavaType.getStringInstance());
            List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
            if (columns == null || columns.size() == 0) {
                result.setProcessed(true);
                return result;
            }

            StringBuilder sb = new StringBuilder();
            StringBuilder sbParam = new StringBuilder();
            StringBuilder sbName = new StringBuilder();
            StringBuilder sbParamForMapper = new StringBuilder();
            boolean isFirstColumn = true;
            for(IntrospectedColumn column: columns) {
                if (!isFirstColumn) {
                    sb.append("-");
                }
                sb.append(column.getFirstRemarks());
                isFirstColumn = false;

                if (sbParam.length() > 0) {
                    sbParam.append(", ");
                }
                if (columns.size() > 1) {
                    sbParam.append("@Param(\""+ JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false)+"\") ");
                }
                sbParam.append(column.getFullyQualifiedJavaType().getShortName());
                sbParam.append(" ");
                sbParam.append(JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false));

                sbName.append(JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), true));
            }
            if (columns.size() > 1) {
                sbParamForMapper.append("map");
            } else {
                sbParamForMapper.append(columns.get(0).getFullyQualifiedJavaType().getFullyQualifiedName());
            }
            newColumn.setUniqueKeynameForRepository(sbName.toString());
            newColumn.setUniqueKeyparameterForRepository(sbParam.toString());
            newColumn.setUniqueKeytypeForMapper(sbParamForMapper.toString());
            newColumn.setRemarks(sb.toString());
        } else if (processSQLFacade.isBlockToken(vTrim,"unique_keys")) {
            result.setProcessed(true);
            introspectedColumns = new ArrayList<>();
            for(String name: introspectedTable.getUniqueKeys()) {
                List<IntrospectedColumn> columns = introspectedTable.getUniqueColumns(name);
                IntrospectedColumn newColumn = new IntrospectedColumn();
                newColumn.setActualColumnName(name);

                StringBuilder sb = new StringBuilder();
                StringBuilder sbParam = new StringBuilder();
                StringBuilder sbName = new StringBuilder();
                StringBuilder sbParamForMapper = new StringBuilder();
                boolean isFirstColumn = true;
                for (IntrospectedColumn column : columns) {
                    if (!isFirstColumn) {
                        sb.append("-");
                    }
                    sb.append(column.getFirstRemarks());
                    isFirstColumn = false;

                    if (sbParam.length() > 0) {
                        sbParam.append(", ");
                    }
                    if (columns.size() > 1) {
                        sbParam.append("@Param(\""+JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false)+"\") ");
                    }
                    sbParam.append(column.getFullyQualifiedJavaType().getShortName());
                    sbParam.append(" ");
                    sbParam.append(JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false));

                    sbName.append(JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), true));
                }
                if (columns.size() > 1) {
                    sbParamForMapper.append("map");
                } else {
                    sbParamForMapper.append(columns.get(0).getFullyQualifiedJavaType().getFullyQualifiedName());
                }
                newColumn.setUniqueKeyparameterForRepository(sbParam.toString());
                newColumn.setUniqueKeynameForRepository(sbName.toString());
                newColumn.setUniqueKeytypeForMapper(sbParamForMapper.toString());
                introspectedColumns.add(newColumn);
                newColumn.setRemarks(sb.toString());
            }
        }

        if (CollectionUtils.isEmpty(introspectedColumns)) {
            return result;
        }
        processSQLFacade.processBodyTokenWithColumns(introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytesList, lineStringList, lineTrimStringList, deepList, deep, destBytes, vTrim, properties, inColumns, introspectedColumns, extraColumns, this, getMethod(this.getClass(), "processBodyToken"), getMethod(this.getClass(), "processToken"));
        return result;
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        checkLowercaseName(request.getName());

        SdpProjectWithBLOBs sdpProjectWithBLOBs = sdpProjectMapper.selectByPrimaryKeyWithBLOBs(request.getId());
        if (request.getName().equals(sdpProjectWithBLOBs.getWorkspaceName())) {
            throw new Exception("dest name is it self");
        }
        SdpProjectExample example = new SdpProjectExample();
        example.createCriteria().andWorkspaceNameEqualTo(sdpProjectWithBLOBs.getWorkspaceName());
        List<SdpProjectWithBLOBs> oriList = sdpProjectMapper.selectByExampleWithBLOBs(example);
        SdpProjectExample destExample = new SdpProjectExample();
        destExample.createCriteria().andWorkspaceNameEqualTo(request.getName());
        List<SdpProjectWithBLOBs> destList = sdpProjectMapper.selectByExampleWithBLOBs(destExample);
        Map<String, SdpProjectWithBLOBs> oriMap = new HashMap<>();
        for(SdpProjectWithBLOBs item: oriList) {
            oriMap.put(item.getName(), item);
        }
        for(SdpProjectWithBLOBs item: destList) {
            SdpProjectWithBLOBs oriItem = oriMap.get(item.getName());
            if (oriItem != null) {
                SdpProjectUpdateRequest destUpdateRequest = new SdpProjectUpdateRequest();
                BeanUtils.copyProperties(oriItem, destUpdateRequest);
                destUpdateRequest.setId(item.getId());
                destUpdateRequest.setWorkspaceName(request.getName());
                super.updateSdpProject(destUpdateRequest);

                oriMap.remove(item.getName());
            }
        }
        for(SdpProjectWithBLOBs item: oriMap.values()) {
            item.setWorkspaceName(request.getName());
            item.setId(null);
            sdpProjectMapper.insertSelective(item);
        }
        return 1;
    }
}

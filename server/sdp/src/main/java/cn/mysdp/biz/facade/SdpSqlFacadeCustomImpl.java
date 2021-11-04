package cn.mysdp.biz.facade;

import cn.mysdp.biz.domain.SdpSql;
import cn.mysdp.biz.domain.SdpSqlExample;
import cn.mysdp.biz.domain.SdpSqlWithBLOBs;
import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.repository.SdpSqlMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

import static cn.mysdp.biz.facade.ProcessSQLFacadeImpl.splitLines;

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
public class SdpSqlFacadeCustomImpl extends SdpSqlFacadeBaseImpl {

    @Autowired
    SdpSqlMapper sdpSqlMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpSql(SdpSqlAddRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getTableName());
        checkLowercaseNameWithDot(request.getParameterCatalog());
        checkLowercaseName(request.getParameterCatalogType());
        checkLowercaseName(request.getName());

        if (StringUtils.isEmpty(request.getWorkspaceName())) {
            throw new Exception("miss parameter：workspace_name");
        }
        if (StringUtils.isEmpty(request.getTableName())) {
            throw new Exception("miss parameter：table_name");
        }
        if (StringUtils.isEmpty(request.getParameterCatalog())) {
            throw new Exception("miss parameter：catalog");
        }
        if (StringUtils.isEmpty(request.getParameterCatalogType())) {
            throw new Exception("miss parameter：catalog_type");
        }

        if (!"sql".equals(request.getParameterCatalog())) {
            return super.addSdpSql(request);
        }
        if (StringUtils.isEmpty(request.getParameterSql())) {
            throw new Exception("miss parameter：sql");
        }

        if (!Integer.valueOf(1).equals(request.getDisableAutoParam())) {
            SdpSqlExample example = new SdpSqlExample();
            example.createCriteria()
                    .andWorkspaceNameEqualTo(request.getWorkspaceName())
                    .andTableNameEqualTo(request.getTableName())
                    .andParameterCatalogEqualTo(request.getParameterCatalog())
                    .andParameterCatalogTypeEqualTo(request.getParameterCatalogType());
            List<SdpSqlWithBLOBs> list = sdpSqlMapper.selectByExampleWithBLOBs(example);
            if (!CollectionUtils.isEmpty(list)) {
                return super.addSdpSql(request);
            }
            String lowerSql = request.getParameterSql().toLowerCase();
            int wherePos = lowerSql.indexOf("where ");
            if (wherePos < 0) {
                return super.addSdpSql(request);
            }
            String where = request.getParameterSql().substring(wherePos);
            lowerSql = where.toLowerCase();
            for(String endString: new String[]{"order by ", "group by ", "limit "}) {
                int endWherePos = lowerSql.indexOf(endString);
                if (endWherePos > 0) {
                    where = where.substring(0, endWherePos);
                    lowerSql = where.toLowerCase();
                }
            }
            List<String> lines = splitLines(where);
            String[] ops = new String[] { " like ,_like",
                    " not like ,_not_like",
                    ">=,_ge",
                    "<=,_le",
                    "<>,_not_eq",
                    "!=,_not_eq",
                    " regexp ,_regexp",
                    ">,_gt",
                    "<,_lt",
                    "=,"};
            Map<String, Integer> doneName = new HashMap<>();
            String firstTable = "";
            for(String line :lines) {
                line = line.trim();
                if (!line.startsWith("and ") && !line.startsWith("and(")) {
                    continue;
                }
                line = line.substring(4).trim();
                while (line.startsWith("(")) {
                    line = line.substring(1).trim();
                }
                while (line.endsWith(")")) {
                    line = line.substring(0, line.length()-1).trim();
                }

                for(String opInfo:ops) {
                    String op;
                    String opEnd = "";
                    String[] opInfos = opInfo.split(",");
                    try {
                        op = opInfos[0];
                        if (opInfos.length > 1) {
                            opEnd = opInfos[1];
                        }
                    }catch(Exception ex) {
                        ex.printStackTrace();
                        throw ex;
                    }
                    int opPos = line.toLowerCase().indexOf(op);
                    if (opPos <= 0) {
                        continue;
                    }
                    String left = line.substring(0, opPos).toLowerCase().trim();
                    String right = line.substring(opPos + op.length()).trim();
                    if (StringUtils.isEmpty(left) || StringUtils.isEmpty(right)) {
                        continue;
                    }

                    String validChars = "abcdefghijklmnopqrstuvwxyz1234567890_";
                    String newLeft = "";
                    if ("tl.id".equals(left)) {
                        newLeft = newLeft;
                    }
                    char lastChar = ' ';
                    for (char c : left.toCharArray()) {
                        if (c == '\'') {
                            newLeft = "";
                            break;
                        }
                        if (c == '.') {
                            if ("".equals(firstTable)) {
                                firstTable = newLeft;
                                newLeft = "";
                                lastChar = ' ';
                                continue;
                            }
                            if (firstTable.equals(newLeft)) {
                                newLeft = "";
                                lastChar = ' ';
                                continue;
                            }
                            if(lastChar != '_') {
                                newLeft += "_";
                                lastChar = '_';
                            }
                            continue;
                        }
                        if (validChars.indexOf(c) < 0) {
                            if(lastChar != '_') {
                                newLeft += "_";
                                lastChar = '_';
                            }
                            continue;
                        }
                        newLeft += c;
                        lastChar = c;
                    }
                    if (StringUtils.isEmpty(newLeft)) {
                        continue;
                    }
                    if (newLeft.toCharArray()[0] >= '0' && newLeft.toCharArray()[0] <= '9') {
                        continue;
                    }
                    left = newLeft;

                    SdpSqlWithBLOBs paramItem = new SdpSqlWithBLOBs();
                    paramItem.setWorkspaceName(request.getWorkspaceName());
                    paramItem.setTableName(request.getTableName());
                    paramItem.setParameterCatalog("sql.param");
                    paramItem.setParameterCatalogType(request.getParameterCatalogType());
                    paramItem.setParameterSqlValue(right);
                    paramItem.setParameterNullable(1);

                    if ("mdate_ge".equals(left + opEnd)) {
                        left = left;
                    }

                    boolean done = false;
                    if (right.startsWith("'") && right.endsWith("'")) {
                        if (right.length() >= 10 && right.length() <= 12 && right.split("-").length == 3) {
                            paramItem.setJavaType("Date");
                        } else if (right.length() >= 19 && right.length() <= 21 && right.split("-").length == 3 && right.split(":").length == 3) {
                            paramItem.setJavaType("Date");
                        } else {
                            if (" like ".equals(op)) {
                                paramItem.setParameterIsLike(1);
                            }
                            paramItem.setJavaType("String");
                        }
                        done = true;
                    }
                    if (StringUtils.isEmpty(paramItem.getJavaType())) {
                        if (right.startsWith("'")) {
                            paramItem.setJavaType("String");
                        } else {
                            paramItem.setJavaType("Integer");
                        }
                    }
                    left += opEnd;
                    if (left.split("\\.").length > 1) {
                        left = left.split("\\.")[left.split("\\.").length - 1];
                    }
                    left = left.replaceAll(" ", "_");
                    if (doneName.containsKey(left)) {
                        doneName.put(left, doneName.get(left)+1);
                        left += doneName.get(left)+1;
                    } else {
                        doneName.put(left, 1);
                    }
                    paramItem.setName(left);
                    sdpSqlMapper.insertSelective(paramItem);
                    break;
                }
            }
        }
        int result = super.addSdpSql(request);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpSql(SdpSqlUpdateRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getTableName());
        checkLowercaseNameWithDot(request.getParameterCatalog());
        checkLowercaseName(request.getParameterCatalogType());
        checkLowercaseName(request.getName());

        SdpSqlWithBLOBs record = sdpSqlMapper.selectByPrimaryKeyWithBLOBs(request.getId());

        if (!StringUtils.isEmpty(request.getParameterCatalogType())) {
            if ("sql".equals(record.getParameterCatalog()) && !StringUtils.isEmpty(record.getParameterCatalogType())) {
                SdpSqlExample example1 = new SdpSqlExample();
                example1.createCriteria().andWorkspaceNameEqualTo(record.getWorkspaceName())
                        .andTableNameEqualTo(record.getTableName())
                        .andParameterCatalogNotEqualTo("sql")
                        .andParameterCatalogTypeEqualTo(record.getParameterCatalogType());

                SdpSqlBatchUpdateRequest destBatchUpdateRequest = new SdpSqlBatchUpdateRequest();
                destBatchUpdateRequest.setWorkspaceName(!StringUtils.isEmpty(request.getWorkspaceName()) ? request.getWorkspaceName() : record.getWorkspaceName());
                destBatchUpdateRequest.setTableName(!StringUtils.isEmpty(request.getTableName()) ? request.getTableName() : record.getTableName());
                destBatchUpdateRequest.setParameterCatalogType(request.getParameterCatalogType());
                destBatchUpdateRequest.setIds(new ArrayList<>());

                List<SdpSqlWithBLOBs> destList = sdpSqlMapper.selectByExampleWithBLOBs(example1);
                for(SdpSqlWithBLOBs destItem: destList) {
                    destBatchUpdateRequest.getIds().add(destItem.getId());
                }
                if (!CollectionUtils.isEmpty(destList)) {
                    super.batchUpdateSdpSql(destBatchUpdateRequest);
                }
                Thread.sleep(1000);
            }
        }

        return super.updateSdpSql(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpSql(SdpSqlBatchUpdateRequest request) throws Exception {
        checkLowercaseName(request.getWorkspaceName());
        checkLowercaseName(request.getTableName());
        checkLowercaseNameWithDot(request.getParameterCatalog());
        checkLowercaseName(request.getParameterCatalogType());
        checkLowercaseName(request.getName());

        return super.batchUpdateSdpSql(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpSql(SdpSqlDeleteRequest request) throws Exception {
        SdpSqlExample example = new SdpSqlExample();
        example.createCriteria().andIdIn(request.getId());
        List<SdpSqlWithBLOBs> list = sdpSqlMapper.selectByExampleWithBLOBs(example);
        boolean hasDeleteSubItem = false;
        for(SdpSql item: list) {
            SdpSqlWithBLOBs record = sdpSqlMapper.selectByPrimaryKeyWithBLOBs(item.getId());

            if ("sql".equals(item.getParameterCatalog())) {
                SdpSqlExample example1 = new SdpSqlExample();
                example1.createCriteria().andWorkspaceNameEqualTo(item.getWorkspaceName())
                        .andTableNameEqualTo(item.getTableName())
                        .andParameterCatalogEqualTo("sql.param")
                        .andParameterCatalogTypeEqualTo(item.getParameterCatalogType());
                List<SdpSqlWithBLOBs> list1 = sdpSqlMapper.selectByExampleWithBLOBs(example1);
                SdpSqlDeleteRequest destDeleteRequest = new SdpSqlDeleteRequest();
                destDeleteRequest.setId(new ArrayList<>());
                for(SdpSqlWithBLOBs item1: list1) {
                    hasDeleteSubItem = true;
                    destDeleteRequest.getId().add(item1.getId());
                }
                super.deleteSdpSql(destDeleteRequest);
            }
        }

        //fix update time for history
        if(hasDeleteSubItem) {
            Thread.sleep(1000);
        }

        return super.deleteSdpSql(request);
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        checkLowercaseName(request.getName());

        SdpSqlWithBLOBs sdpSqlWithBLOBs = sdpSqlMapper.selectByPrimaryKeyWithBLOBs(request.getId());
        if (request.getName().equals(sdpSqlWithBLOBs.getWorkspaceName())) {
            throw new Exception("dest name is it self");
        }
        SdpSqlExample example = new SdpSqlExample();
        example.createCriteria().andWorkspaceNameEqualTo(sdpSqlWithBLOBs.getWorkspaceName());
        List<SdpSqlWithBLOBs> oriList = sdpSqlMapper.selectByExampleWithBLOBs(example);
        SdpSqlExample destExample = new SdpSqlExample();
        destExample.createCriteria().andWorkspaceNameEqualTo(request.getName());
        List<SdpSqlWithBLOBs> destList = sdpSqlMapper.selectByExampleWithBLOBs(destExample);
        Map<String, SdpSqlWithBLOBs> oriMap = new HashMap<>();
        for(SdpSqlWithBLOBs item: oriList) {
            oriMap.put(item.getTableName()+";"+item.getParameterCatalog()+";"+item.getParameterCatalogType()+";"+item.getName(), item);
        }

        for(SdpSqlWithBLOBs item: destList) {
            SdpSqlWithBLOBs oriItem = oriMap.get(item.getTableName()+";"+item.getParameterCatalog()+";"+item.getParameterCatalogType()+";"+item.getName());
            if (oriItem != null) {
                SdpSqlUpdateRequest destUpdateRequest = new SdpSqlUpdateRequest();
                BeanUtils.copyProperties(oriItem, destUpdateRequest);
                destUpdateRequest.setId(item.getId());
                destUpdateRequest.setWorkspaceName(request.getName());
                updateSdpSql(destUpdateRequest);

                oriMap.remove(item.getTableName()+";"+item.getParameterCatalog()+";"+item.getParameterCatalogType()+";"+item.getName());
            }
        }
        for(SdpSqlWithBLOBs item: oriMap.values()) {
            item.setId(null);
            item.setWorkspaceName(request.getName());
            sdpSqlMapper.insertSelective(item);
        }
        return 1;
    }

}

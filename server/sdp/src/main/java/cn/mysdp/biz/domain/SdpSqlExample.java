package cn.mysdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpSqlExample
 * @Description: Example
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
public class SdpSqlExample {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpSqlExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setLimitClause(Integer limitClause) {
        this.limitClause = limitClause;
    }

    public Integer getLimitClause() {
        return limitClause;
    }

    public void setCountClause(Integer countClause) {
        this.countClause = countClause;
    }

    public Integer getCountClause() {
        return countClause;
    }

    public void setTopClause(Integer topClause) {
        this.topClause = topClause;
    }

    public Integer getTopClause() {
        return topClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public static SdpSqlExample createExample(SdpSqlWithBLOBs request) throws Exception {
        return createExample(request, null);
    }

    public static SdpSqlExample createExample(SdpSqlWithBLOBs request, JSONObject options) throws Exception {
        SdpSqlExample example = new SdpSqlExample();
        SdpSqlExample.Criteria criteria = example.createCriteria();
        int count = 0;

        count += setCriteriaEqualTo(request, criteria);

        if (options != null) {
            String orderBy = "";
            if (options.containsKey("order_by")) {
                JSONArray jsonArray = options.getJSONArray("order_by");
                orderBy = setCriteriaOrderBy(jsonArray.toJavaList(CriteriaString.class), example);
                if (orderBy.length() > 0) {
                    example.setOrderByClause(orderBy);
                }
            }
            if (options.containsKey("limit")) {
                example.setLimitClause(+options.getInteger("limit"));
            }
            if (options.containsKey("count")) {
                example.setCountClause(+options.getInteger("count"));
            }
            if (options.containsKey("top")) {
                example.setTopClause(+options.getInteger("top"));
            }

            if(options.containsKey("=")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpSqlWithBLOBs.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpSqlWithBLOBs.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpSqlWithBLOBs.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpSqlWithBLOBs.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpSqlWithBLOBs.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpSqlWithBLOBs.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpSqlWithBLOBs.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpSqlWithBLOBs.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpSqlWithBLOBs.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpSqlWithBLOBs.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpSqlWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpSqlWithBLOBs.class);
                count += setCriteriaRegexp(obj, criteria);
            }

        }

        return example;
    }

    private static int appendCriteriaOrderByItem(StringBuilder sb, String name, String value) throws Exception {
        if (value == null) {
            return 0;
        }
        if (sb.length() > 0) {
            sb.append(",");
        }
        value = value.trim();
        if ("desc/gbk".equals(value)) {
            sb.append("convert(");
            sb.append(name);
            sb.append(" using gbk) desc");
        } else if ("asc/gbk".equals(value)) {
            sb.append("convert(");
            sb.append(name);
            sb.append(" using gbk) asc");
        } else if ("desc".equals(value)) {
            sb.append(name);
            sb.append(" desc");
        } else if ("asc".equals(value) || "".equals(value)) {
            sb.append(name);
            sb.append(" asc");
        } else {
            throw new Exception("非法的order by类型:"+value);
        }
        return 1;
    }

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpSqlExample example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "id", criteriaOrderBy.getId());
            subCount += appendCriteriaOrderByItem(sb, "is_disable", criteriaOrderBy.getIsDisable());
            subCount += appendCriteriaOrderByItem(sb, "workspace_name", criteriaOrderBy.getWorkspaceName());
            subCount += appendCriteriaOrderByItem(sb, "`table_name`", criteriaOrderBy.getTableName());
            subCount += appendCriteriaOrderByItem(sb, "parameter_catalog", criteriaOrderBy.getParameterCatalog());
            subCount += appendCriteriaOrderByItem(sb, "parameter_catalog_type", criteriaOrderBy.getParameterCatalogType());
            subCount += appendCriteriaOrderByItem(sb, "`name`", criteriaOrderBy.getName());
            subCount += appendCriteriaOrderByItem(sb, "java_type", criteriaOrderBy.getJavaType());
            subCount += appendCriteriaOrderByItem(sb, "remarks", criteriaOrderBy.getRemarks());
            subCount += appendCriteriaOrderByItem(sb, "`parameter_mode`", criteriaOrderBy.getParameterMode());
            subCount += appendCriteriaOrderByItem(sb, "parameter_nullable", criteriaOrderBy.getParameterNullable());
            subCount += appendCriteriaOrderByItem(sb, "parameter_is_like", criteriaOrderBy.getParameterIsLike());
            subCount += appendCriteriaOrderByItem(sb, "parameter_is_import_excel", criteriaOrderBy.getParameterIsImportExcel());
            subCount += appendCriteriaOrderByItem(sb, "parameter_is_export_excel", criteriaOrderBy.getParameterIsExportExcel());
            subCount += appendCriteriaOrderByItem(sb, "parameter_sql_value_item", criteriaOrderBy.getParameterSqlValueItem());
            subCount += appendCriteriaOrderByItem(sb, "parameter_sql_value", criteriaOrderBy.getParameterSqlValue());
            subCount += appendCriteriaOrderByItem(sb, "parameter_sql_value_ignore", criteriaOrderBy.getParameterSqlValueIgnore());
            subCount += appendCriteriaOrderByItem(sb, "parameter_without_test", criteriaOrderBy.getParameterWithoutTest());
            subCount += appendCriteriaOrderByItem(sb, "parameter_sql_issimple", criteriaOrderBy.getParameterSqlIssimple());
            subCount += appendCriteriaOrderByItem(sb, "parameter_sql_return_nolist", criteriaOrderBy.getParameterSqlReturnNolist());
            subCount += appendCriteriaOrderByItem(sb, "java_imports", criteriaOrderBy.getJavaImports());
            subCount += appendCriteriaOrderByItem(sb, "parameter_overwrite_default_sql", criteriaOrderBy.getParameterOverwriteDefaultSql());
            subCount += appendCriteriaOrderByItem(sb, "java_return_type", criteriaOrderBy.getJavaReturnType());
            subCount += appendCriteriaOrderByItem(sb, "sort_no", criteriaOrderBy.getSortNo());
            subCount += appendCriteriaOrderByItem(sb, "remark", criteriaOrderBy.getRemark());
            subCount += appendCriteriaOrderByItem(sb, "is_interface", criteriaOrderBy.getIsInterface());
            subCount += appendCriteriaOrderByItem(sb, "parameter_sql", criteriaOrderBy.getParameterSql());
            subCount += appendCriteriaOrderByItem(sb, "java_body", criteriaOrderBy.getJavaBody());
            subCount += appendCriteriaOrderByItem(sb, "extra_info", criteriaOrderBy.getExtraInfo());
            if (subCount > 1) {
                throw new Exception("order by数组元素对象属性多于1个");
            }
        }
        if (sb.length() == 0) {
            appendCriteriaOrderByItem(sb, "1", "");
        }

        return sb.toString();
    }

    private static int setCriteriaEqualTo(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdEqualTo(request.getId());
        }

        if (request.getIsDisable() != null) {
            count++;
            criteria.andIsDisableEqualTo(request.getIsDisable());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameEqualTo(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameEqualTo(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogEqualTo(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeEqualTo(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameEqualTo(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeEqualTo(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksEqualTo(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeEqualTo(request.getParameterMode());
        }

        if (request.getParameterNullable() != null) {
            count++;
            criteria.andParameterNullableEqualTo(request.getParameterNullable());
        }

        if (request.getParameterIsLike() != null) {
            count++;
            criteria.andParameterIsLikeEqualTo(request.getParameterIsLike());
        }

        if (request.getParameterIsImportExcel() != null) {
            count++;
            criteria.andParameterIsImportExcelEqualTo(request.getParameterIsImportExcel());
        }

        if (request.getParameterIsExportExcel() != null) {
            count++;
            criteria.andParameterIsExportExcelEqualTo(request.getParameterIsExportExcel());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemEqualTo(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueEqualTo(request.getParameterSqlValue());
        }

        if (request.getParameterSqlValueIgnore() != null) {
            count++;
            criteria.andParameterSqlValueIgnoreEqualTo(request.getParameterSqlValueIgnore());
        }

        if (request.getParameterWithoutTest() != null) {
            count++;
            criteria.andParameterWithoutTestEqualTo(request.getParameterWithoutTest());
        }

        if (request.getParameterSqlIssimple() != null) {
            count++;
            criteria.andParameterSqlIssimpleEqualTo(request.getParameterSqlIssimple());
        }

        if (request.getParameterSqlReturnNolist() != null) {
            count++;
            criteria.andParameterSqlReturnNolistEqualTo(request.getParameterSqlReturnNolist());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsEqualTo(request.getJavaImports());
        }

        if (request.getParameterOverwriteDefaultSql() != null) {
            count++;
            criteria.andParameterOverwriteDefaultSqlEqualTo(request.getParameterOverwriteDefaultSql());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeEqualTo(request.getJavaReturnType());
        }

        if (request.getSortNo() != null) {
            count++;
            criteria.andSortNoEqualTo(request.getSortNo());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkEqualTo(request.getRemark());
        }

        if (request.getIsInterface() != null) {
            count++;
            criteria.andIsInterfaceEqualTo(request.getIsInterface());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlEqualTo(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyEqualTo(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThan(request.getId());
        }

        if (request.getIsDisable() != null) {
            count++;
            criteria.andIsDisableGreaterThan(request.getIsDisable());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThan(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameGreaterThan(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogGreaterThan(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeGreaterThan(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThan(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeGreaterThan(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksGreaterThan(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeGreaterThan(request.getParameterMode());
        }

        if (request.getParameterNullable() != null) {
            count++;
            criteria.andParameterNullableGreaterThan(request.getParameterNullable());
        }

        if (request.getParameterIsLike() != null) {
            count++;
            criteria.andParameterIsLikeGreaterThan(request.getParameterIsLike());
        }

        if (request.getParameterIsImportExcel() != null) {
            count++;
            criteria.andParameterIsImportExcelGreaterThan(request.getParameterIsImportExcel());
        }

        if (request.getParameterIsExportExcel() != null) {
            count++;
            criteria.andParameterIsExportExcelGreaterThan(request.getParameterIsExportExcel());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemGreaterThan(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueGreaterThan(request.getParameterSqlValue());
        }

        if (request.getParameterSqlValueIgnore() != null) {
            count++;
            criteria.andParameterSqlValueIgnoreGreaterThan(request.getParameterSqlValueIgnore());
        }

        if (request.getParameterWithoutTest() != null) {
            count++;
            criteria.andParameterWithoutTestGreaterThan(request.getParameterWithoutTest());
        }

        if (request.getParameterSqlIssimple() != null) {
            count++;
            criteria.andParameterSqlIssimpleGreaterThan(request.getParameterSqlIssimple());
        }

        if (request.getParameterSqlReturnNolist() != null) {
            count++;
            criteria.andParameterSqlReturnNolistGreaterThan(request.getParameterSqlReturnNolist());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsGreaterThan(request.getJavaImports());
        }

        if (request.getParameterOverwriteDefaultSql() != null) {
            count++;
            criteria.andParameterOverwriteDefaultSqlGreaterThan(request.getParameterOverwriteDefaultSql());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeGreaterThan(request.getJavaReturnType());
        }

        if (request.getSortNo() != null) {
            count++;
            criteria.andSortNoGreaterThan(request.getSortNo());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThan(request.getRemark());
        }

        if (request.getIsInterface() != null) {
            count++;
            criteria.andIsInterfaceGreaterThan(request.getIsInterface());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlGreaterThan(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyGreaterThan(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoGreaterThan(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThanOrEqualTo(request.getId());
        }

        if (request.getIsDisable() != null) {
            count++;
            criteria.andIsDisableGreaterThanOrEqualTo(request.getIsDisable());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameGreaterThanOrEqualTo(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogGreaterThanOrEqualTo(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeGreaterThanOrEqualTo(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThanOrEqualTo(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeGreaterThanOrEqualTo(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksGreaterThanOrEqualTo(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeGreaterThanOrEqualTo(request.getParameterMode());
        }

        if (request.getParameterNullable() != null) {
            count++;
            criteria.andParameterNullableGreaterThanOrEqualTo(request.getParameterNullable());
        }

        if (request.getParameterIsLike() != null) {
            count++;
            criteria.andParameterIsLikeGreaterThanOrEqualTo(request.getParameterIsLike());
        }

        if (request.getParameterIsImportExcel() != null) {
            count++;
            criteria.andParameterIsImportExcelGreaterThanOrEqualTo(request.getParameterIsImportExcel());
        }

        if (request.getParameterIsExportExcel() != null) {
            count++;
            criteria.andParameterIsExportExcelGreaterThanOrEqualTo(request.getParameterIsExportExcel());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemGreaterThanOrEqualTo(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueGreaterThanOrEqualTo(request.getParameterSqlValue());
        }

        if (request.getParameterSqlValueIgnore() != null) {
            count++;
            criteria.andParameterSqlValueIgnoreGreaterThanOrEqualTo(request.getParameterSqlValueIgnore());
        }

        if (request.getParameterWithoutTest() != null) {
            count++;
            criteria.andParameterWithoutTestGreaterThanOrEqualTo(request.getParameterWithoutTest());
        }

        if (request.getParameterSqlIssimple() != null) {
            count++;
            criteria.andParameterSqlIssimpleGreaterThanOrEqualTo(request.getParameterSqlIssimple());
        }

        if (request.getParameterSqlReturnNolist() != null) {
            count++;
            criteria.andParameterSqlReturnNolistGreaterThanOrEqualTo(request.getParameterSqlReturnNolist());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsGreaterThanOrEqualTo(request.getJavaImports());
        }

        if (request.getParameterOverwriteDefaultSql() != null) {
            count++;
            criteria.andParameterOverwriteDefaultSqlGreaterThanOrEqualTo(request.getParameterOverwriteDefaultSql());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeGreaterThanOrEqualTo(request.getJavaReturnType());
        }

        if (request.getSortNo() != null) {
            count++;
            criteria.andSortNoGreaterThanOrEqualTo(request.getSortNo());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThanOrEqualTo(request.getRemark());
        }

        if (request.getIsInterface() != null) {
            count++;
            criteria.andIsInterfaceGreaterThanOrEqualTo(request.getIsInterface());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlGreaterThanOrEqualTo(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyGreaterThanOrEqualTo(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoGreaterThanOrEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNotNull();
        }

        if (request.getIsDisable() != null) {
            count++;
            criteria.andIsDisableIsNotNull();
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNotNull();
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameIsNotNull();
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogIsNotNull();
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeIsNotNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNotNull();
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeIsNotNull();
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksIsNotNull();
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeIsNotNull();
        }

        if (request.getParameterNullable() != null) {
            count++;
            criteria.andParameterNullableIsNotNull();
        }

        if (request.getParameterIsLike() != null) {
            count++;
            criteria.andParameterIsLikeIsNotNull();
        }

        if (request.getParameterIsImportExcel() != null) {
            count++;
            criteria.andParameterIsImportExcelIsNotNull();
        }

        if (request.getParameterIsExportExcel() != null) {
            count++;
            criteria.andParameterIsExportExcelIsNotNull();
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemIsNotNull();
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueIsNotNull();
        }

        if (request.getParameterSqlValueIgnore() != null) {
            count++;
            criteria.andParameterSqlValueIgnoreIsNotNull();
        }

        if (request.getParameterWithoutTest() != null) {
            count++;
            criteria.andParameterWithoutTestIsNotNull();
        }

        if (request.getParameterSqlIssimple() != null) {
            count++;
            criteria.andParameterSqlIssimpleIsNotNull();
        }

        if (request.getParameterSqlReturnNolist() != null) {
            count++;
            criteria.andParameterSqlReturnNolistIsNotNull();
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsIsNotNull();
        }

        if (request.getParameterOverwriteDefaultSql() != null) {
            count++;
            criteria.andParameterOverwriteDefaultSqlIsNotNull();
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeIsNotNull();
        }

        if (request.getSortNo() != null) {
            count++;
            criteria.andSortNoIsNotNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNotNull();
        }

        if (request.getIsInterface() != null) {
            count++;
            criteria.andIsInterfaceIsNotNull();
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlIsNotNull();
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyIsNotNull();
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNull();
        }

        if (request.getIsDisable() != null) {
            count++;
            criteria.andIsDisableIsNull();
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNull();
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameIsNull();
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogIsNull();
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeIsNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNull();
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeIsNull();
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksIsNull();
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeIsNull();
        }

        if (request.getParameterNullable() != null) {
            count++;
            criteria.andParameterNullableIsNull();
        }

        if (request.getParameterIsLike() != null) {
            count++;
            criteria.andParameterIsLikeIsNull();
        }

        if (request.getParameterIsImportExcel() != null) {
            count++;
            criteria.andParameterIsImportExcelIsNull();
        }

        if (request.getParameterIsExportExcel() != null) {
            count++;
            criteria.andParameterIsExportExcelIsNull();
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemIsNull();
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueIsNull();
        }

        if (request.getParameterSqlValueIgnore() != null) {
            count++;
            criteria.andParameterSqlValueIgnoreIsNull();
        }

        if (request.getParameterWithoutTest() != null) {
            count++;
            criteria.andParameterWithoutTestIsNull();
        }

        if (request.getParameterSqlIssimple() != null) {
            count++;
            criteria.andParameterSqlIssimpleIsNull();
        }

        if (request.getParameterSqlReturnNolist() != null) {
            count++;
            criteria.andParameterSqlReturnNolistIsNull();
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsIsNull();
        }

        if (request.getParameterOverwriteDefaultSql() != null) {
            count++;
            criteria.andParameterOverwriteDefaultSqlIsNull();
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeIsNull();
        }

        if (request.getSortNo() != null) {
            count++;
            criteria.andSortNoIsNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNull();
        }

        if (request.getIsInterface() != null) {
            count++;
            criteria.andIsInterfaceIsNull();
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlIsNull();
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyIsNull();
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThan(request.getId());
        }

        if (request.getIsDisable() != null) {
            count++;
            criteria.andIsDisableLessThan(request.getIsDisable());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThan(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameLessThan(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogLessThan(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeLessThan(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThan(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeLessThan(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksLessThan(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeLessThan(request.getParameterMode());
        }

        if (request.getParameterNullable() != null) {
            count++;
            criteria.andParameterNullableLessThan(request.getParameterNullable());
        }

        if (request.getParameterIsLike() != null) {
            count++;
            criteria.andParameterIsLikeLessThan(request.getParameterIsLike());
        }

        if (request.getParameterIsImportExcel() != null) {
            count++;
            criteria.andParameterIsImportExcelLessThan(request.getParameterIsImportExcel());
        }

        if (request.getParameterIsExportExcel() != null) {
            count++;
            criteria.andParameterIsExportExcelLessThan(request.getParameterIsExportExcel());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemLessThan(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueLessThan(request.getParameterSqlValue());
        }

        if (request.getParameterSqlValueIgnore() != null) {
            count++;
            criteria.andParameterSqlValueIgnoreLessThan(request.getParameterSqlValueIgnore());
        }

        if (request.getParameterWithoutTest() != null) {
            count++;
            criteria.andParameterWithoutTestLessThan(request.getParameterWithoutTest());
        }

        if (request.getParameterSqlIssimple() != null) {
            count++;
            criteria.andParameterSqlIssimpleLessThan(request.getParameterSqlIssimple());
        }

        if (request.getParameterSqlReturnNolist() != null) {
            count++;
            criteria.andParameterSqlReturnNolistLessThan(request.getParameterSqlReturnNolist());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsLessThan(request.getJavaImports());
        }

        if (request.getParameterOverwriteDefaultSql() != null) {
            count++;
            criteria.andParameterOverwriteDefaultSqlLessThan(request.getParameterOverwriteDefaultSql());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeLessThan(request.getJavaReturnType());
        }

        if (request.getSortNo() != null) {
            count++;
            criteria.andSortNoLessThan(request.getSortNo());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThan(request.getRemark());
        }

        if (request.getIsInterface() != null) {
            count++;
            criteria.andIsInterfaceLessThan(request.getIsInterface());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlLessThan(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyLessThan(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLessThan(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThanOrEqualTo(request.getId());
        }

        if (request.getIsDisable() != null) {
            count++;
            criteria.andIsDisableLessThanOrEqualTo(request.getIsDisable());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameLessThanOrEqualTo(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogLessThanOrEqualTo(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeLessThanOrEqualTo(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThanOrEqualTo(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeLessThanOrEqualTo(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksLessThanOrEqualTo(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeLessThanOrEqualTo(request.getParameterMode());
        }

        if (request.getParameterNullable() != null) {
            count++;
            criteria.andParameterNullableLessThanOrEqualTo(request.getParameterNullable());
        }

        if (request.getParameterIsLike() != null) {
            count++;
            criteria.andParameterIsLikeLessThanOrEqualTo(request.getParameterIsLike());
        }

        if (request.getParameterIsImportExcel() != null) {
            count++;
            criteria.andParameterIsImportExcelLessThanOrEqualTo(request.getParameterIsImportExcel());
        }

        if (request.getParameterIsExportExcel() != null) {
            count++;
            criteria.andParameterIsExportExcelLessThanOrEqualTo(request.getParameterIsExportExcel());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemLessThanOrEqualTo(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueLessThanOrEqualTo(request.getParameterSqlValue());
        }

        if (request.getParameterSqlValueIgnore() != null) {
            count++;
            criteria.andParameterSqlValueIgnoreLessThanOrEqualTo(request.getParameterSqlValueIgnore());
        }

        if (request.getParameterWithoutTest() != null) {
            count++;
            criteria.andParameterWithoutTestLessThanOrEqualTo(request.getParameterWithoutTest());
        }

        if (request.getParameterSqlIssimple() != null) {
            count++;
            criteria.andParameterSqlIssimpleLessThanOrEqualTo(request.getParameterSqlIssimple());
        }

        if (request.getParameterSqlReturnNolist() != null) {
            count++;
            criteria.andParameterSqlReturnNolistLessThanOrEqualTo(request.getParameterSqlReturnNolist());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsLessThanOrEqualTo(request.getJavaImports());
        }

        if (request.getParameterOverwriteDefaultSql() != null) {
            count++;
            criteria.andParameterOverwriteDefaultSqlLessThanOrEqualTo(request.getParameterOverwriteDefaultSql());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeLessThanOrEqualTo(request.getJavaReturnType());
        }

        if (request.getSortNo() != null) {
            count++;
            criteria.andSortNoLessThanOrEqualTo(request.getSortNo());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThanOrEqualTo(request.getRemark());
        }

        if (request.getIsInterface() != null) {
            count++;
            criteria.andIsInterfaceLessThanOrEqualTo(request.getIsInterface());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlLessThanOrEqualTo(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyLessThanOrEqualTo(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLessThanOrEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaLike(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLike(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameLike(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogLike(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeLike(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLike(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeLike(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksLike(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeLike(request.getParameterMode());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemLike(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueLike(request.getParameterSqlValue());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsLike(request.getJavaImports());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeLike(request.getJavaReturnType());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLike(request.getRemark());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlLike(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyLike(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLike(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdNotEqualTo(request.getId());
        }

        if (request.getIsDisable() != null) {
            count++;
            criteria.andIsDisableNotEqualTo(request.getIsDisable());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotEqualTo(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameNotEqualTo(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogNotEqualTo(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeNotEqualTo(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotEqualTo(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeNotEqualTo(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksNotEqualTo(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeNotEqualTo(request.getParameterMode());
        }

        if (request.getParameterNullable() != null) {
            count++;
            criteria.andParameterNullableNotEqualTo(request.getParameterNullable());
        }

        if (request.getParameterIsLike() != null) {
            count++;
            criteria.andParameterIsLikeNotEqualTo(request.getParameterIsLike());
        }

        if (request.getParameterIsImportExcel() != null) {
            count++;
            criteria.andParameterIsImportExcelNotEqualTo(request.getParameterIsImportExcel());
        }

        if (request.getParameterIsExportExcel() != null) {
            count++;
            criteria.andParameterIsExportExcelNotEqualTo(request.getParameterIsExportExcel());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemNotEqualTo(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueNotEqualTo(request.getParameterSqlValue());
        }

        if (request.getParameterSqlValueIgnore() != null) {
            count++;
            criteria.andParameterSqlValueIgnoreNotEqualTo(request.getParameterSqlValueIgnore());
        }

        if (request.getParameterWithoutTest() != null) {
            count++;
            criteria.andParameterWithoutTestNotEqualTo(request.getParameterWithoutTest());
        }

        if (request.getParameterSqlIssimple() != null) {
            count++;
            criteria.andParameterSqlIssimpleNotEqualTo(request.getParameterSqlIssimple());
        }

        if (request.getParameterSqlReturnNolist() != null) {
            count++;
            criteria.andParameterSqlReturnNolistNotEqualTo(request.getParameterSqlReturnNolist());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsNotEqualTo(request.getJavaImports());
        }

        if (request.getParameterOverwriteDefaultSql() != null) {
            count++;
            criteria.andParameterOverwriteDefaultSqlNotEqualTo(request.getParameterOverwriteDefaultSql());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeNotEqualTo(request.getJavaReturnType());
        }

        if (request.getSortNo() != null) {
            count++;
            criteria.andSortNoNotEqualTo(request.getSortNo());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotEqualTo(request.getRemark());
        }

        if (request.getIsInterface() != null) {
            count++;
            criteria.andIsInterfaceNotEqualTo(request.getIsInterface());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlNotEqualTo(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyNotEqualTo(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoNotEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotLike(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameNotLike(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogNotLike(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeNotLike(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotLike(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeNotLike(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksNotLike(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeNotLike(request.getParameterMode());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemNotLike(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueNotLike(request.getParameterSqlValue());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsNotLike(request.getJavaImports());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeNotLike(request.getJavaReturnType());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotLike(request.getRemark());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlNotLike(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyNotLike(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoNotLike(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpSqlWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameRegexp(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameRegexp(request.getTableName());
        }

        if (request.getParameterCatalog() != null) {
            count++;
            criteria.andParameterCatalogRegexp(request.getParameterCatalog());
        }

        if (request.getParameterCatalogType() != null) {
            count++;
            criteria.andParameterCatalogTypeRegexp(request.getParameterCatalogType());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameRegexp(request.getName());
        }

        if (request.getJavaType() != null) {
            count++;
            criteria.andJavaTypeRegexp(request.getJavaType());
        }

        if (request.getRemarks() != null) {
            count++;
            criteria.andRemarksRegexp(request.getRemarks());
        }

        if (request.getParameterMode() != null) {
            count++;
            criteria.andParameterModeRegexp(request.getParameterMode());
        }

        if (request.getParameterSqlValueItem() != null) {
            count++;
            criteria.andParameterSqlValueItemRegexp(request.getParameterSqlValueItem());
        }

        if (request.getParameterSqlValue() != null) {
            count++;
            criteria.andParameterSqlValueRegexp(request.getParameterSqlValue());
        }

        if (request.getJavaImports() != null) {
            count++;
            criteria.andJavaImportsRegexp(request.getJavaImports());
        }

        if (request.getJavaReturnType() != null) {
            count++;
            criteria.andJavaReturnTypeRegexp(request.getJavaReturnType());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkRegexp(request.getRemark());
        }

        if (request.getParameterSql() != null) {
            count++;
            criteria.andParameterSqlRegexp(request.getParameterSql());
        }

        if (request.getJavaBody() != null) {
            count++;
            criteria.andJavaBodyRegexp(request.getJavaBody());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoRegexp(request.getExtraInfo());
        }

        return count;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIsDisableIsNull() {
            addCriterion("is_disable is null");
            return (Criteria) this;
        }

        public Criteria andIsDisableIsNotNull() {
            addCriterion("is_disable is not null");
            return (Criteria) this;
        }

        public Criteria andIsDisableEqualTo(Integer value) {
            addCriterion("is_disable =", value, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableNotEqualTo(Integer value) {
            addCriterion("is_disable <>", value, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableGreaterThan(Integer value) {
            addCriterion("is_disable >", value, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_disable >=", value, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableLessThan(Integer value) {
            addCriterion("is_disable <", value, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableLessThanOrEqualTo(Integer value) {
            addCriterion("is_disable <=", value, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableIn(List<Integer> values) {
            addCriterion("is_disable in", values, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableNotIn(List<Integer> values) {
            addCriterion("is_disable not in", values, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableBetween(Integer value1, Integer value2) {
            addCriterion("is_disable between", value1, value2, "isDisable");
            return (Criteria) this;
        }

        public Criteria andIsDisableNotBetween(Integer value1, Integer value2) {
            addCriterion("is_disable not between", value1, value2, "isDisable");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameIsNull() {
            addCriterion("workspace_name is null");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameIsNotNull() {
            addCriterion("workspace_name is not null");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameEqualTo(String value) {
            addCriterion("workspace_name =", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameNotEqualTo(String value) {
            addCriterion("workspace_name <>", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameGreaterThan(String value) {
            addCriterion("workspace_name >", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameGreaterThanOrEqualTo(String value) {
            addCriterion("workspace_name >=", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameLessThan(String value) {
            addCriterion("workspace_name <", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameLessThanOrEqualTo(String value) {
            addCriterion("workspace_name <=", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameRegexp(String value) {
            addCriterion("workspace_name regexp", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameLike(String value) {
            addCriterion("workspace_name like", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameNotLike(String value) {
            addCriterion("workspace_name not like", value, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameIn(List<String> values) {
            addCriterion("workspace_name in", values, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameNotIn(List<String> values) {
            addCriterion("workspace_name not in", values, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameBetween(String value1, String value2) {
            addCriterion("workspace_name between", value1, value2, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andWorkspaceNameNotBetween(String value1, String value2) {
            addCriterion("workspace_name not between", value1, value2, "workspaceName");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNull() {
            addCriterion("`table_name` is null");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNotNull() {
            addCriterion("`table_name` is not null");
            return (Criteria) this;
        }

        public Criteria andTableNameEqualTo(String value) {
            addCriterion("`table_name` =", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotEqualTo(String value) {
            addCriterion("`table_name` <>", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThan(String value) {
            addCriterion("`table_name` >", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("`table_name` >=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThan(String value) {
            addCriterion("`table_name` <", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("`table_name` <=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameRegexp(String value) {
            addCriterion("`table_name` regexp", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLike(String value) {
            addCriterion("`table_name` like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotLike(String value) {
            addCriterion("`table_name` not like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameIn(List<String> values) {
            addCriterion("`table_name` in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotIn(List<String> values) {
            addCriterion("`table_name` not in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameBetween(String value1, String value2) {
            addCriterion("`table_name` between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("`table_name` not between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogIsNull() {
            addCriterion("parameter_catalog is null");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogIsNotNull() {
            addCriterion("parameter_catalog is not null");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogEqualTo(String value) {
            addCriterion("parameter_catalog =", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogNotEqualTo(String value) {
            addCriterion("parameter_catalog <>", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogGreaterThan(String value) {
            addCriterion("parameter_catalog >", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogGreaterThanOrEqualTo(String value) {
            addCriterion("parameter_catalog >=", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogLessThan(String value) {
            addCriterion("parameter_catalog <", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogLessThanOrEqualTo(String value) {
            addCriterion("parameter_catalog <=", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogRegexp(String value) {
            addCriterion("parameter_catalog regexp", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogLike(String value) {
            addCriterion("parameter_catalog like", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogNotLike(String value) {
            addCriterion("parameter_catalog not like", value, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogIn(List<String> values) {
            addCriterion("parameter_catalog in", values, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogNotIn(List<String> values) {
            addCriterion("parameter_catalog not in", values, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogBetween(String value1, String value2) {
            addCriterion("parameter_catalog between", value1, value2, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogNotBetween(String value1, String value2) {
            addCriterion("parameter_catalog not between", value1, value2, "parameterCatalog");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeIsNull() {
            addCriterion("parameter_catalog_type is null");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeIsNotNull() {
            addCriterion("parameter_catalog_type is not null");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeEqualTo(String value) {
            addCriterion("parameter_catalog_type =", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeNotEqualTo(String value) {
            addCriterion("parameter_catalog_type <>", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeGreaterThan(String value) {
            addCriterion("parameter_catalog_type >", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeGreaterThanOrEqualTo(String value) {
            addCriterion("parameter_catalog_type >=", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeLessThan(String value) {
            addCriterion("parameter_catalog_type <", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeLessThanOrEqualTo(String value) {
            addCriterion("parameter_catalog_type <=", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeRegexp(String value) {
            addCriterion("parameter_catalog_type regexp", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeLike(String value) {
            addCriterion("parameter_catalog_type like", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeNotLike(String value) {
            addCriterion("parameter_catalog_type not like", value, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeIn(List<String> values) {
            addCriterion("parameter_catalog_type in", values, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeNotIn(List<String> values) {
            addCriterion("parameter_catalog_type not in", values, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeBetween(String value1, String value2) {
            addCriterion("parameter_catalog_type between", value1, value2, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andParameterCatalogTypeNotBetween(String value1, String value2) {
            addCriterion("parameter_catalog_type not between", value1, value2, "parameterCatalogType");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameRegexp(String value) {
            addCriterion("`name` regexp", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andJavaTypeIsNull() {
            addCriterion("java_type is null");
            return (Criteria) this;
        }

        public Criteria andJavaTypeIsNotNull() {
            addCriterion("java_type is not null");
            return (Criteria) this;
        }

        public Criteria andJavaTypeEqualTo(String value) {
            addCriterion("java_type =", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeNotEqualTo(String value) {
            addCriterion("java_type <>", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeGreaterThan(String value) {
            addCriterion("java_type >", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeGreaterThanOrEqualTo(String value) {
            addCriterion("java_type >=", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeLessThan(String value) {
            addCriterion("java_type <", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeLessThanOrEqualTo(String value) {
            addCriterion("java_type <=", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeRegexp(String value) {
            addCriterion("java_type regexp", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeLike(String value) {
            addCriterion("java_type like", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeNotLike(String value) {
            addCriterion("java_type not like", value, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeIn(List<String> values) {
            addCriterion("java_type in", values, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeNotIn(List<String> values) {
            addCriterion("java_type not in", values, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeBetween(String value1, String value2) {
            addCriterion("java_type between", value1, value2, "javaType");
            return (Criteria) this;
        }

        public Criteria andJavaTypeNotBetween(String value1, String value2) {
            addCriterion("java_type not between", value1, value2, "javaType");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksRegexp(String value) {
            addCriterion("remarks regexp", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andParameterModeIsNull() {
            addCriterion("`parameter_mode` is null");
            return (Criteria) this;
        }

        public Criteria andParameterModeIsNotNull() {
            addCriterion("`parameter_mode` is not null");
            return (Criteria) this;
        }

        public Criteria andParameterModeEqualTo(String value) {
            addCriterion("`parameter_mode` =", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeNotEqualTo(String value) {
            addCriterion("`parameter_mode` <>", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeGreaterThan(String value) {
            addCriterion("`parameter_mode` >", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeGreaterThanOrEqualTo(String value) {
            addCriterion("`parameter_mode` >=", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeLessThan(String value) {
            addCriterion("`parameter_mode` <", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeLessThanOrEqualTo(String value) {
            addCriterion("`parameter_mode` <=", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeRegexp(String value) {
            addCriterion("`parameter_mode` regexp", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeLike(String value) {
            addCriterion("`parameter_mode` like", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeNotLike(String value) {
            addCriterion("`parameter_mode` not like", value, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeIn(List<String> values) {
            addCriterion("`parameter_mode` in", values, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeNotIn(List<String> values) {
            addCriterion("`parameter_mode` not in", values, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeBetween(String value1, String value2) {
            addCriterion("`parameter_mode` between", value1, value2, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterModeNotBetween(String value1, String value2) {
            addCriterion("`parameter_mode` not between", value1, value2, "parameterMode");
            return (Criteria) this;
        }

        public Criteria andParameterNullableIsNull() {
            addCriterion("parameter_nullable is null");
            return (Criteria) this;
        }

        public Criteria andParameterNullableIsNotNull() {
            addCriterion("parameter_nullable is not null");
            return (Criteria) this;
        }

        public Criteria andParameterNullableEqualTo(Integer value) {
            addCriterion("parameter_nullable =", value, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableNotEqualTo(Integer value) {
            addCriterion("parameter_nullable <>", value, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableGreaterThan(Integer value) {
            addCriterion("parameter_nullable >", value, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_nullable >=", value, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableLessThan(Integer value) {
            addCriterion("parameter_nullable <", value, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_nullable <=", value, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableIn(List<Integer> values) {
            addCriterion("parameter_nullable in", values, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableNotIn(List<Integer> values) {
            addCriterion("parameter_nullable not in", values, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableBetween(Integer value1, Integer value2) {
            addCriterion("parameter_nullable between", value1, value2, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterNullableNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_nullable not between", value1, value2, "parameterNullable");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeIsNull() {
            addCriterion("parameter_is_like is null");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeIsNotNull() {
            addCriterion("parameter_is_like is not null");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeEqualTo(Integer value) {
            addCriterion("parameter_is_like =", value, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeNotEqualTo(Integer value) {
            addCriterion("parameter_is_like <>", value, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeGreaterThan(Integer value) {
            addCriterion("parameter_is_like >", value, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_is_like >=", value, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeLessThan(Integer value) {
            addCriterion("parameter_is_like <", value, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_is_like <=", value, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeIn(List<Integer> values) {
            addCriterion("parameter_is_like in", values, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeNotIn(List<Integer> values) {
            addCriterion("parameter_is_like not in", values, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeBetween(Integer value1, Integer value2) {
            addCriterion("parameter_is_like between", value1, value2, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsLikeNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_is_like not between", value1, value2, "parameterIsLike");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelIsNull() {
            addCriterion("parameter_is_import_excel is null");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelIsNotNull() {
            addCriterion("parameter_is_import_excel is not null");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelEqualTo(Integer value) {
            addCriterion("parameter_is_import_excel =", value, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelNotEqualTo(Integer value) {
            addCriterion("parameter_is_import_excel <>", value, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelGreaterThan(Integer value) {
            addCriterion("parameter_is_import_excel >", value, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_is_import_excel >=", value, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelLessThan(Integer value) {
            addCriterion("parameter_is_import_excel <", value, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_is_import_excel <=", value, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelIn(List<Integer> values) {
            addCriterion("parameter_is_import_excel in", values, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelNotIn(List<Integer> values) {
            addCriterion("parameter_is_import_excel not in", values, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelBetween(Integer value1, Integer value2) {
            addCriterion("parameter_is_import_excel between", value1, value2, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsImportExcelNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_is_import_excel not between", value1, value2, "parameterIsImportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelIsNull() {
            addCriterion("parameter_is_export_excel is null");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelIsNotNull() {
            addCriterion("parameter_is_export_excel is not null");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelEqualTo(Integer value) {
            addCriterion("parameter_is_export_excel =", value, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelNotEqualTo(Integer value) {
            addCriterion("parameter_is_export_excel <>", value, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelGreaterThan(Integer value) {
            addCriterion("parameter_is_export_excel >", value, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_is_export_excel >=", value, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelLessThan(Integer value) {
            addCriterion("parameter_is_export_excel <", value, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_is_export_excel <=", value, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelIn(List<Integer> values) {
            addCriterion("parameter_is_export_excel in", values, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelNotIn(List<Integer> values) {
            addCriterion("parameter_is_export_excel not in", values, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelBetween(Integer value1, Integer value2) {
            addCriterion("parameter_is_export_excel between", value1, value2, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterIsExportExcelNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_is_export_excel not between", value1, value2, "parameterIsExportExcel");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemIsNull() {
            addCriterion("parameter_sql_value_item is null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemIsNotNull() {
            addCriterion("parameter_sql_value_item is not null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemEqualTo(String value) {
            addCriterion("parameter_sql_value_item =", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemNotEqualTo(String value) {
            addCriterion("parameter_sql_value_item <>", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemGreaterThan(String value) {
            addCriterion("parameter_sql_value_item >", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemGreaterThanOrEqualTo(String value) {
            addCriterion("parameter_sql_value_item >=", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemLessThan(String value) {
            addCriterion("parameter_sql_value_item <", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemLessThanOrEqualTo(String value) {
            addCriterion("parameter_sql_value_item <=", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemRegexp(String value) {
            addCriterion("parameter_sql_value_item regexp", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemLike(String value) {
            addCriterion("parameter_sql_value_item like", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemNotLike(String value) {
            addCriterion("parameter_sql_value_item not like", value, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemIn(List<String> values) {
            addCriterion("parameter_sql_value_item in", values, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemNotIn(List<String> values) {
            addCriterion("parameter_sql_value_item not in", values, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemBetween(String value1, String value2) {
            addCriterion("parameter_sql_value_item between", value1, value2, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueItemNotBetween(String value1, String value2) {
            addCriterion("parameter_sql_value_item not between", value1, value2, "parameterSqlValueItem");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIsNull() {
            addCriterion("parameter_sql_value is null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIsNotNull() {
            addCriterion("parameter_sql_value is not null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueEqualTo(String value) {
            addCriterion("parameter_sql_value =", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueNotEqualTo(String value) {
            addCriterion("parameter_sql_value <>", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueGreaterThan(String value) {
            addCriterion("parameter_sql_value >", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueGreaterThanOrEqualTo(String value) {
            addCriterion("parameter_sql_value >=", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueLessThan(String value) {
            addCriterion("parameter_sql_value <", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueLessThanOrEqualTo(String value) {
            addCriterion("parameter_sql_value <=", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueRegexp(String value) {
            addCriterion("parameter_sql_value regexp", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueLike(String value) {
            addCriterion("parameter_sql_value like", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueNotLike(String value) {
            addCriterion("parameter_sql_value not like", value, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIn(List<String> values) {
            addCriterion("parameter_sql_value in", values, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueNotIn(List<String> values) {
            addCriterion("parameter_sql_value not in", values, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueBetween(String value1, String value2) {
            addCriterion("parameter_sql_value between", value1, value2, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueNotBetween(String value1, String value2) {
            addCriterion("parameter_sql_value not between", value1, value2, "parameterSqlValue");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreIsNull() {
            addCriterion("parameter_sql_value_ignore is null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreIsNotNull() {
            addCriterion("parameter_sql_value_ignore is not null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreEqualTo(Integer value) {
            addCriterion("parameter_sql_value_ignore =", value, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreNotEqualTo(Integer value) {
            addCriterion("parameter_sql_value_ignore <>", value, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreGreaterThan(Integer value) {
            addCriterion("parameter_sql_value_ignore >", value, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_sql_value_ignore >=", value, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreLessThan(Integer value) {
            addCriterion("parameter_sql_value_ignore <", value, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_sql_value_ignore <=", value, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreIn(List<Integer> values) {
            addCriterion("parameter_sql_value_ignore in", values, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreNotIn(List<Integer> values) {
            addCriterion("parameter_sql_value_ignore not in", values, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreBetween(Integer value1, Integer value2) {
            addCriterion("parameter_sql_value_ignore between", value1, value2, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterSqlValueIgnoreNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_sql_value_ignore not between", value1, value2, "parameterSqlValueIgnore");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestIsNull() {
            addCriterion("parameter_without_test is null");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestIsNotNull() {
            addCriterion("parameter_without_test is not null");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestEqualTo(Integer value) {
            addCriterion("parameter_without_test =", value, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestNotEqualTo(Integer value) {
            addCriterion("parameter_without_test <>", value, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestGreaterThan(Integer value) {
            addCriterion("parameter_without_test >", value, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_without_test >=", value, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestLessThan(Integer value) {
            addCriterion("parameter_without_test <", value, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_without_test <=", value, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestIn(List<Integer> values) {
            addCriterion("parameter_without_test in", values, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestNotIn(List<Integer> values) {
            addCriterion("parameter_without_test not in", values, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestBetween(Integer value1, Integer value2) {
            addCriterion("parameter_without_test between", value1, value2, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterWithoutTestNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_without_test not between", value1, value2, "parameterWithoutTest");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleIsNull() {
            addCriterion("parameter_sql_issimple is null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleIsNotNull() {
            addCriterion("parameter_sql_issimple is not null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleEqualTo(Integer value) {
            addCriterion("parameter_sql_issimple =", value, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleNotEqualTo(Integer value) {
            addCriterion("parameter_sql_issimple <>", value, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleGreaterThan(Integer value) {
            addCriterion("parameter_sql_issimple >", value, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_sql_issimple >=", value, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleLessThan(Integer value) {
            addCriterion("parameter_sql_issimple <", value, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_sql_issimple <=", value, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleIn(List<Integer> values) {
            addCriterion("parameter_sql_issimple in", values, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleNotIn(List<Integer> values) {
            addCriterion("parameter_sql_issimple not in", values, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleBetween(Integer value1, Integer value2) {
            addCriterion("parameter_sql_issimple between", value1, value2, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIssimpleNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_sql_issimple not between", value1, value2, "parameterSqlIssimple");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistIsNull() {
            addCriterion("parameter_sql_return_nolist is null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistIsNotNull() {
            addCriterion("parameter_sql_return_nolist is not null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistEqualTo(Integer value) {
            addCriterion("parameter_sql_return_nolist =", value, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistNotEqualTo(Integer value) {
            addCriterion("parameter_sql_return_nolist <>", value, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistGreaterThan(Integer value) {
            addCriterion("parameter_sql_return_nolist >", value, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_sql_return_nolist >=", value, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistLessThan(Integer value) {
            addCriterion("parameter_sql_return_nolist <", value, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_sql_return_nolist <=", value, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistIn(List<Integer> values) {
            addCriterion("parameter_sql_return_nolist in", values, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistNotIn(List<Integer> values) {
            addCriterion("parameter_sql_return_nolist not in", values, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistBetween(Integer value1, Integer value2) {
            addCriterion("parameter_sql_return_nolist between", value1, value2, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andParameterSqlReturnNolistNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_sql_return_nolist not between", value1, value2, "parameterSqlReturnNolist");
            return (Criteria) this;
        }

        public Criteria andJavaImportsIsNull() {
            addCriterion("java_imports is null");
            return (Criteria) this;
        }

        public Criteria andJavaImportsIsNotNull() {
            addCriterion("java_imports is not null");
            return (Criteria) this;
        }

        public Criteria andJavaImportsEqualTo(String value) {
            addCriterion("java_imports =", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsNotEqualTo(String value) {
            addCriterion("java_imports <>", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsGreaterThan(String value) {
            addCriterion("java_imports >", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsGreaterThanOrEqualTo(String value) {
            addCriterion("java_imports >=", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsLessThan(String value) {
            addCriterion("java_imports <", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsLessThanOrEqualTo(String value) {
            addCriterion("java_imports <=", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsRegexp(String value) {
            addCriterion("java_imports regexp", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsLike(String value) {
            addCriterion("java_imports like", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsNotLike(String value) {
            addCriterion("java_imports not like", value, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsIn(List<String> values) {
            addCriterion("java_imports in", values, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsNotIn(List<String> values) {
            addCriterion("java_imports not in", values, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsBetween(String value1, String value2) {
            addCriterion("java_imports between", value1, value2, "javaImports");
            return (Criteria) this;
        }

        public Criteria andJavaImportsNotBetween(String value1, String value2) {
            addCriterion("java_imports not between", value1, value2, "javaImports");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlIsNull() {
            addCriterion("parameter_overwrite_default_sql is null");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlIsNotNull() {
            addCriterion("parameter_overwrite_default_sql is not null");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlEqualTo(Integer value) {
            addCriterion("parameter_overwrite_default_sql =", value, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlNotEqualTo(Integer value) {
            addCriterion("parameter_overwrite_default_sql <>", value, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlGreaterThan(Integer value) {
            addCriterion("parameter_overwrite_default_sql >", value, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlGreaterThanOrEqualTo(Integer value) {
            addCriterion("parameter_overwrite_default_sql >=", value, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlLessThan(Integer value) {
            addCriterion("parameter_overwrite_default_sql <", value, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlLessThanOrEqualTo(Integer value) {
            addCriterion("parameter_overwrite_default_sql <=", value, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlIn(List<Integer> values) {
            addCriterion("parameter_overwrite_default_sql in", values, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlNotIn(List<Integer> values) {
            addCriterion("parameter_overwrite_default_sql not in", values, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlBetween(Integer value1, Integer value2) {
            addCriterion("parameter_overwrite_default_sql between", value1, value2, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andParameterOverwriteDefaultSqlNotBetween(Integer value1, Integer value2) {
            addCriterion("parameter_overwrite_default_sql not between", value1, value2, "parameterOverwriteDefaultSql");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeIsNull() {
            addCriterion("java_return_type is null");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeIsNotNull() {
            addCriterion("java_return_type is not null");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeEqualTo(String value) {
            addCriterion("java_return_type =", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeNotEqualTo(String value) {
            addCriterion("java_return_type <>", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeGreaterThan(String value) {
            addCriterion("java_return_type >", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeGreaterThanOrEqualTo(String value) {
            addCriterion("java_return_type >=", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeLessThan(String value) {
            addCriterion("java_return_type <", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeLessThanOrEqualTo(String value) {
            addCriterion("java_return_type <=", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeRegexp(String value) {
            addCriterion("java_return_type regexp", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeLike(String value) {
            addCriterion("java_return_type like", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeNotLike(String value) {
            addCriterion("java_return_type not like", value, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeIn(List<String> values) {
            addCriterion("java_return_type in", values, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeNotIn(List<String> values) {
            addCriterion("java_return_type not in", values, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeBetween(String value1, String value2) {
            addCriterion("java_return_type between", value1, value2, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andJavaReturnTypeNotBetween(String value1, String value2) {
            addCriterion("java_return_type not between", value1, value2, "javaReturnType");
            return (Criteria) this;
        }

        public Criteria andSortNoIsNull() {
            addCriterion("sort_no is null");
            return (Criteria) this;
        }

        public Criteria andSortNoIsNotNull() {
            addCriterion("sort_no is not null");
            return (Criteria) this;
        }

        public Criteria andSortNoEqualTo(Integer value) {
            addCriterion("sort_no =", value, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoNotEqualTo(Integer value) {
            addCriterion("sort_no <>", value, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoGreaterThan(Integer value) {
            addCriterion("sort_no >", value, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_no >=", value, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoLessThan(Integer value) {
            addCriterion("sort_no <", value, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoLessThanOrEqualTo(Integer value) {
            addCriterion("sort_no <=", value, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoIn(List<Integer> values) {
            addCriterion("sort_no in", values, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoNotIn(List<Integer> values) {
            addCriterion("sort_no not in", values, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoBetween(Integer value1, Integer value2) {
            addCriterion("sort_no between", value1, value2, "sortNo");
            return (Criteria) this;
        }

        public Criteria andSortNoNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_no not between", value1, value2, "sortNo");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkRegexp(String value) {
            addCriterion("remark regexp", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceIsNull() {
            addCriterion("is_interface is null");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceIsNotNull() {
            addCriterion("is_interface is not null");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceEqualTo(Integer value) {
            addCriterion("is_interface =", value, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceNotEqualTo(Integer value) {
            addCriterion("is_interface <>", value, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceGreaterThan(Integer value) {
            addCriterion("is_interface >", value, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_interface >=", value, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceLessThan(Integer value) {
            addCriterion("is_interface <", value, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceLessThanOrEqualTo(Integer value) {
            addCriterion("is_interface <=", value, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceIn(List<Integer> values) {
            addCriterion("is_interface in", values, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceNotIn(List<Integer> values) {
            addCriterion("is_interface not in", values, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceBetween(Integer value1, Integer value2) {
            addCriterion("is_interface between", value1, value2, "isInterface");
            return (Criteria) this;
        }

        public Criteria andIsInterfaceNotBetween(Integer value1, Integer value2) {
            addCriterion("is_interface not between", value1, value2, "isInterface");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIsNull() {
            addCriterion("parameter_sql is null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIsNotNull() {
            addCriterion("parameter_sql is not null");
            return (Criteria) this;
        }

        public Criteria andParameterSqlEqualTo(String value) {
            addCriterion("parameter_sql =", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlNotEqualTo(String value) {
            addCriterion("parameter_sql <>", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlGreaterThan(String value) {
            addCriterion("parameter_sql >", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlGreaterThanOrEqualTo(String value) {
            addCriterion("parameter_sql >=", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlLessThan(String value) {
            addCriterion("parameter_sql <", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlLessThanOrEqualTo(String value) {
            addCriterion("parameter_sql <=", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlRegexp(String value) {
            addCriterion("parameter_sql regexp", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlLike(String value) {
            addCriterion("parameter_sql like", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlNotLike(String value) {
            addCriterion("parameter_sql not like", value, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlIn(List<String> values) {
            addCriterion("parameter_sql in", values, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlNotIn(List<String> values) {
            addCriterion("parameter_sql not in", values, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlBetween(String value1, String value2) {
            addCriterion("parameter_sql between", value1, value2, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andParameterSqlNotBetween(String value1, String value2) {
            addCriterion("parameter_sql not between", value1, value2, "parameterSql");
            return (Criteria) this;
        }

        public Criteria andJavaBodyIsNull() {
            addCriterion("java_body is null");
            return (Criteria) this;
        }

        public Criteria andJavaBodyIsNotNull() {
            addCriterion("java_body is not null");
            return (Criteria) this;
        }

        public Criteria andJavaBodyEqualTo(String value) {
            addCriterion("java_body =", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyNotEqualTo(String value) {
            addCriterion("java_body <>", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyGreaterThan(String value) {
            addCriterion("java_body >", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyGreaterThanOrEqualTo(String value) {
            addCriterion("java_body >=", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyLessThan(String value) {
            addCriterion("java_body <", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyLessThanOrEqualTo(String value) {
            addCriterion("java_body <=", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyRegexp(String value) {
            addCriterion("java_body regexp", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyLike(String value) {
            addCriterion("java_body like", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyNotLike(String value) {
            addCriterion("java_body not like", value, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyIn(List<String> values) {
            addCriterion("java_body in", values, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyNotIn(List<String> values) {
            addCriterion("java_body not in", values, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyBetween(String value1, String value2) {
            addCriterion("java_body between", value1, value2, "javaBody");
            return (Criteria) this;
        }

        public Criteria andJavaBodyNotBetween(String value1, String value2) {
            addCriterion("java_body not between", value1, value2, "javaBody");
            return (Criteria) this;
        }

        public Criteria andExtraInfoIsNull() {
            addCriterion("extra_info is null");
            return (Criteria) this;
        }

        public Criteria andExtraInfoIsNotNull() {
            addCriterion("extra_info is not null");
            return (Criteria) this;
        }

        public Criteria andExtraInfoEqualTo(String value) {
            addCriterion("extra_info =", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoNotEqualTo(String value) {
            addCriterion("extra_info <>", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoGreaterThan(String value) {
            addCriterion("extra_info >", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoGreaterThanOrEqualTo(String value) {
            addCriterion("extra_info >=", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoLessThan(String value) {
            addCriterion("extra_info <", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoLessThanOrEqualTo(String value) {
            addCriterion("extra_info <=", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoRegexp(String value) {
            addCriterion("extra_info regexp", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoLike(String value) {
            addCriterion("extra_info like", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoNotLike(String value) {
            addCriterion("extra_info not like", value, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoIn(List<String> values) {
            addCriterion("extra_info in", values, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoNotIn(List<String> values) {
            addCriterion("extra_info not in", values, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoBetween(String value1, String value2) {
            addCriterion("extra_info between", value1, value2, "extraInfo");
            return (Criteria) this;
        }

        public Criteria andExtraInfoNotBetween(String value1, String value2) {
            addCriterion("extra_info not between", value1, value2, "extraInfo");
            return (Criteria) this;
        }

    }

    @Getter
    @Setter
    public static class CriteriaString {
        private String id;

        private String isDisable;

        private String workspaceName;

        private String tableName;

        private String parameterCatalog;

        private String parameterCatalogType;

        private String name;

        private String javaType;

        private String remarks;

        private String parameterMode;

        private String parameterNullable;

        private String parameterIsLike;

        private String parameterIsImportExcel;

        private String parameterIsExportExcel;

        private String parameterSqlValueItem;

        private String parameterSqlValue;

        private String parameterSqlValueIgnore;

        private String parameterWithoutTest;

        private String parameterSqlIssimple;

        private String parameterSqlReturnNolist;

        private String javaImports;

        private String parameterOverwriteDefaultSql;

        private String javaReturnType;

        private String sortNo;

        private String remark;

        private String isInterface;

        private String parameterSql;

        private String javaBody;

        private String extraInfo;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

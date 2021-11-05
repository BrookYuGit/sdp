package cn.mysdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpTemplateExample
 * @Description: Example
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
public class SdpTemplateExample {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpTemplateExample() {
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

    public static SdpTemplateExample createExample(SdpTemplateWithBLOBs request) throws Exception {
        return createExample(request, null);
    }

    public static SdpTemplateExample createExample(SdpTemplateWithBLOBs request, JSONObject options) throws Exception {
        SdpTemplateExample example = new SdpTemplateExample();
        SdpTemplateExample.Criteria criteria = example.createCriteria();
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
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpTemplateWithBLOBs.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpTemplateWithBLOBs.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpTemplateWithBLOBs.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpTemplateWithBLOBs.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpTemplateWithBLOBs.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpTemplateWithBLOBs.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpTemplateWithBLOBs.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpTemplateWithBLOBs.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpTemplateWithBLOBs.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpTemplateWithBLOBs.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpTemplateWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpTemplateWithBLOBs.class);
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

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpTemplateExample example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "id", criteriaOrderBy.getId());
            subCount += appendCriteriaOrderByItem(sb, "workspace_name", criteriaOrderBy.getWorkspaceName());
            subCount += appendCriteriaOrderByItem(sb, "project_name", criteriaOrderBy.getProjectName());
            subCount += appendCriteriaOrderByItem(sb, "`name`", criteriaOrderBy.getName());
            subCount += appendCriteriaOrderByItem(sb, "file_type", criteriaOrderBy.getFileType());
            subCount += appendCriteriaOrderByItem(sb, "project", criteriaOrderBy.getProject());
            subCount += appendCriteriaOrderByItem(sb, "package_name", criteriaOrderBy.getPackageName());
            subCount += appendCriteriaOrderByItem(sb, "no_overwrite", criteriaOrderBy.getNoOverwrite());
            subCount += appendCriteriaOrderByItem(sb, "remark", criteriaOrderBy.getRemark());
            subCount += appendCriteriaOrderByItem(sb, "file_template", criteriaOrderBy.getFileTemplate());
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

    private static int setCriteriaEqualTo(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameEqualTo(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameEqualTo(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameEqualTo(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeEqualTo(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectEqualTo(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameEqualTo(request.getPackageName());
        }

        if (request.getNoOverwrite() != null) {
            count++;
            criteria.andNoOverwriteEqualTo(request.getNoOverwrite());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkEqualTo(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateEqualTo(request.getFileTemplate());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThan(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThan(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameGreaterThan(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThan(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeGreaterThan(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectGreaterThan(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameGreaterThan(request.getPackageName());
        }

        if (request.getNoOverwrite() != null) {
            count++;
            criteria.andNoOverwriteGreaterThan(request.getNoOverwrite());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThan(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateGreaterThan(request.getFileTemplate());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoGreaterThan(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThanOrEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameGreaterThanOrEqualTo(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThanOrEqualTo(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeGreaterThanOrEqualTo(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectGreaterThanOrEqualTo(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameGreaterThanOrEqualTo(request.getPackageName());
        }

        if (request.getNoOverwrite() != null) {
            count++;
            criteria.andNoOverwriteGreaterThanOrEqualTo(request.getNoOverwrite());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThanOrEqualTo(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateGreaterThanOrEqualTo(request.getFileTemplate());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoGreaterThanOrEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNotNull();
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNotNull();
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameIsNotNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNotNull();
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeIsNotNull();
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectIsNotNull();
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameIsNotNull();
        }

        if (request.getNoOverwrite() != null) {
            count++;
            criteria.andNoOverwriteIsNotNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNotNull();
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateIsNotNull();
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNull();
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNull();
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameIsNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNull();
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeIsNull();
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectIsNull();
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameIsNull();
        }

        if (request.getNoOverwrite() != null) {
            count++;
            criteria.andNoOverwriteIsNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNull();
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateIsNull();
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThan(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThan(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameLessThan(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThan(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeLessThan(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectLessThan(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameLessThan(request.getPackageName());
        }

        if (request.getNoOverwrite() != null) {
            count++;
            criteria.andNoOverwriteLessThan(request.getNoOverwrite());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThan(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateLessThan(request.getFileTemplate());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLessThan(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThanOrEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameLessThanOrEqualTo(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThanOrEqualTo(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeLessThanOrEqualTo(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectLessThanOrEqualTo(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameLessThanOrEqualTo(request.getPackageName());
        }

        if (request.getNoOverwrite() != null) {
            count++;
            criteria.andNoOverwriteLessThanOrEqualTo(request.getNoOverwrite());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThanOrEqualTo(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateLessThanOrEqualTo(request.getFileTemplate());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLessThanOrEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaLike(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLike(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameLike(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLike(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeLike(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectLike(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameLike(request.getPackageName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLike(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateLike(request.getFileTemplate());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLike(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdNotEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotEqualTo(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameNotEqualTo(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotEqualTo(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeNotEqualTo(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectNotEqualTo(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameNotEqualTo(request.getPackageName());
        }

        if (request.getNoOverwrite() != null) {
            count++;
            criteria.andNoOverwriteNotEqualTo(request.getNoOverwrite());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotEqualTo(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateNotEqualTo(request.getFileTemplate());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoNotEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotLike(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameNotLike(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotLike(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeNotLike(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectNotLike(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameNotLike(request.getPackageName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotLike(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateNotLike(request.getFileTemplate());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoNotLike(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpTemplateWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameRegexp(request.getWorkspaceName());
        }

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameRegexp(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameRegexp(request.getName());
        }

        if (request.getFileType() != null) {
            count++;
            criteria.andFileTypeRegexp(request.getFileType());
        }

        if (request.getProject() != null) {
            count++;
            criteria.andProjectRegexp(request.getProject());
        }

        if (request.getPackageName() != null) {
            count++;
            criteria.andPackageNameRegexp(request.getPackageName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkRegexp(request.getRemark());
        }

        if (request.getFileTemplate() != null) {
            count++;
            criteria.andFileTemplateRegexp(request.getFileTemplate());
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

        public Criteria andProjectNameIsNull() {
            addCriterion("project_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNotNull() {
            addCriterion("project_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNameEqualTo(String value) {
            addCriterion("project_name =", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotEqualTo(String value) {
            addCriterion("project_name <>", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThan(String value) {
            addCriterion("project_name >", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_name >=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThan(String value) {
            addCriterion("project_name <", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThanOrEqualTo(String value) {
            addCriterion("project_name <=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameRegexp(String value) {
            addCriterion("project_name regexp", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLike(String value) {
            addCriterion("project_name like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotLike(String value) {
            addCriterion("project_name not like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameIn(List<String> values) {
            addCriterion("project_name in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotIn(List<String> values) {
            addCriterion("project_name not in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameBetween(String value1, String value2) {
            addCriterion("project_name between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotBetween(String value1, String value2) {
            addCriterion("project_name not between", value1, value2, "projectName");
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

        public Criteria andFileTypeIsNull() {
            addCriterion("file_type is null");
            return (Criteria) this;
        }

        public Criteria andFileTypeIsNotNull() {
            addCriterion("file_type is not null");
            return (Criteria) this;
        }

        public Criteria andFileTypeEqualTo(String value) {
            addCriterion("file_type =", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotEqualTo(String value) {
            addCriterion("file_type <>", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeGreaterThan(String value) {
            addCriterion("file_type >", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeGreaterThanOrEqualTo(String value) {
            addCriterion("file_type >=", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLessThan(String value) {
            addCriterion("file_type <", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLessThanOrEqualTo(String value) {
            addCriterion("file_type <=", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeRegexp(String value) {
            addCriterion("file_type regexp", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeLike(String value) {
            addCriterion("file_type like", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotLike(String value) {
            addCriterion("file_type not like", value, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeIn(List<String> values) {
            addCriterion("file_type in", values, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotIn(List<String> values) {
            addCriterion("file_type not in", values, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeBetween(String value1, String value2) {
            addCriterion("file_type between", value1, value2, "fileType");
            return (Criteria) this;
        }

        public Criteria andFileTypeNotBetween(String value1, String value2) {
            addCriterion("file_type not between", value1, value2, "fileType");
            return (Criteria) this;
        }

        public Criteria andProjectIsNull() {
            addCriterion("project is null");
            return (Criteria) this;
        }

        public Criteria andProjectIsNotNull() {
            addCriterion("project is not null");
            return (Criteria) this;
        }

        public Criteria andProjectEqualTo(String value) {
            addCriterion("project =", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectNotEqualTo(String value) {
            addCriterion("project <>", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectGreaterThan(String value) {
            addCriterion("project >", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectGreaterThanOrEqualTo(String value) {
            addCriterion("project >=", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectLessThan(String value) {
            addCriterion("project <", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectLessThanOrEqualTo(String value) {
            addCriterion("project <=", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectRegexp(String value) {
            addCriterion("project regexp", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectLike(String value) {
            addCriterion("project like", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectNotLike(String value) {
            addCriterion("project not like", value, "project");
            return (Criteria) this;
        }

        public Criteria andProjectIn(List<String> values) {
            addCriterion("project in", values, "project");
            return (Criteria) this;
        }

        public Criteria andProjectNotIn(List<String> values) {
            addCriterion("project not in", values, "project");
            return (Criteria) this;
        }

        public Criteria andProjectBetween(String value1, String value2) {
            addCriterion("project between", value1, value2, "project");
            return (Criteria) this;
        }

        public Criteria andProjectNotBetween(String value1, String value2) {
            addCriterion("project not between", value1, value2, "project");
            return (Criteria) this;
        }

        public Criteria andPackageNameIsNull() {
            addCriterion("package_name is null");
            return (Criteria) this;
        }

        public Criteria andPackageNameIsNotNull() {
            addCriterion("package_name is not null");
            return (Criteria) this;
        }

        public Criteria andPackageNameEqualTo(String value) {
            addCriterion("package_name =", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameNotEqualTo(String value) {
            addCriterion("package_name <>", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameGreaterThan(String value) {
            addCriterion("package_name >", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameGreaterThanOrEqualTo(String value) {
            addCriterion("package_name >=", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameLessThan(String value) {
            addCriterion("package_name <", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameLessThanOrEqualTo(String value) {
            addCriterion("package_name <=", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameRegexp(String value) {
            addCriterion("package_name regexp", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameLike(String value) {
            addCriterion("package_name like", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameNotLike(String value) {
            addCriterion("package_name not like", value, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameIn(List<String> values) {
            addCriterion("package_name in", values, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameNotIn(List<String> values) {
            addCriterion("package_name not in", values, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameBetween(String value1, String value2) {
            addCriterion("package_name between", value1, value2, "packageName");
            return (Criteria) this;
        }

        public Criteria andPackageNameNotBetween(String value1, String value2) {
            addCriterion("package_name not between", value1, value2, "packageName");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteIsNull() {
            addCriterion("no_overwrite is null");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteIsNotNull() {
            addCriterion("no_overwrite is not null");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteEqualTo(Integer value) {
            addCriterion("no_overwrite =", value, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteNotEqualTo(Integer value) {
            addCriterion("no_overwrite <>", value, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteGreaterThan(Integer value) {
            addCriterion("no_overwrite >", value, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteGreaterThanOrEqualTo(Integer value) {
            addCriterion("no_overwrite >=", value, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteLessThan(Integer value) {
            addCriterion("no_overwrite <", value, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteLessThanOrEqualTo(Integer value) {
            addCriterion("no_overwrite <=", value, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteIn(List<Integer> values) {
            addCriterion("no_overwrite in", values, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteNotIn(List<Integer> values) {
            addCriterion("no_overwrite not in", values, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteBetween(Integer value1, Integer value2) {
            addCriterion("no_overwrite between", value1, value2, "noOverwrite");
            return (Criteria) this;
        }

        public Criteria andNoOverwriteNotBetween(Integer value1, Integer value2) {
            addCriterion("no_overwrite not between", value1, value2, "noOverwrite");
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

        public Criteria andFileTemplateIsNull() {
            addCriterion("file_template is null");
            return (Criteria) this;
        }

        public Criteria andFileTemplateIsNotNull() {
            addCriterion("file_template is not null");
            return (Criteria) this;
        }

        public Criteria andFileTemplateEqualTo(String value) {
            addCriterion("file_template =", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateNotEqualTo(String value) {
            addCriterion("file_template <>", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateGreaterThan(String value) {
            addCriterion("file_template >", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateGreaterThanOrEqualTo(String value) {
            addCriterion("file_template >=", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateLessThan(String value) {
            addCriterion("file_template <", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateLessThanOrEqualTo(String value) {
            addCriterion("file_template <=", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateRegexp(String value) {
            addCriterion("file_template regexp", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateLike(String value) {
            addCriterion("file_template like", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateNotLike(String value) {
            addCriterion("file_template not like", value, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateIn(List<String> values) {
            addCriterion("file_template in", values, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateNotIn(List<String> values) {
            addCriterion("file_template not in", values, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateBetween(String value1, String value2) {
            addCriterion("file_template between", value1, value2, "fileTemplate");
            return (Criteria) this;
        }

        public Criteria andFileTemplateNotBetween(String value1, String value2) {
            addCriterion("file_template not between", value1, value2, "fileTemplate");
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

        private String workspaceName;

        private String projectName;

        private String name;

        private String fileType;

        private String project;

        private String packageName;

        private String noOverwrite;

        private String remark;

        private String fileTemplate;

        private String extraInfo;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

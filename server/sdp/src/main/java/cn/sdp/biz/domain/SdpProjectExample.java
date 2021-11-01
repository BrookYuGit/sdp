package cn.sdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpProjectExample
 * @Description: Example
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
public class SdpProjectExample {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpProjectExample() {
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

    public static SdpProjectExample createExample(SdpProjectWithBLOBs request) throws Exception {
        return createExample(request, null);
    }

    public static SdpProjectExample createExample(SdpProjectWithBLOBs request, JSONObject options) throws Exception {
        SdpProjectExample example = new SdpProjectExample();
        SdpProjectExample.Criteria criteria = example.createCriteria();
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
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpProjectWithBLOBs.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpProjectWithBLOBs.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpProjectWithBLOBs.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpProjectWithBLOBs.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpProjectWithBLOBs.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpProjectWithBLOBs.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpProjectWithBLOBs.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpProjectWithBLOBs.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpProjectWithBLOBs.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpProjectWithBLOBs.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpProjectWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpProjectWithBLOBs.class);
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

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpProjectExample example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "id", criteriaOrderBy.getId());
            subCount += appendCriteriaOrderByItem(sb, "workspace_name", criteriaOrderBy.getWorkspaceName());
            subCount += appendCriteriaOrderByItem(sb, "`name`", criteriaOrderBy.getName());
            subCount += appendCriteriaOrderByItem(sb, "root_path", criteriaOrderBy.getRootPath());
            subCount += appendCriteriaOrderByItem(sb, "`tables`", criteriaOrderBy.getTables());
            subCount += appendCriteriaOrderByItem(sb, "remark", criteriaOrderBy.getRemark());
            if (subCount > 1) {
                throw new Exception("order by数组元素对象属性多于1个");
            }
        }
        if (sb.length() == 0) {
            appendCriteriaOrderByItem(sb, "1", "");
        }

        return sb.toString();
    }

    private static int setCriteriaEqualTo(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameEqualTo(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameEqualTo(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathEqualTo(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesEqualTo(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkEqualTo(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThan(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThan(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThan(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathGreaterThan(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesGreaterThan(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThan(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThanOrEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThanOrEqualTo(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathGreaterThanOrEqualTo(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesGreaterThanOrEqualTo(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThanOrEqualTo(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNotNull();
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNotNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNotNull();
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathIsNotNull();
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesIsNotNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNull();
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNull();
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathIsNull();
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesIsNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThan(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThan(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThan(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathLessThan(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesLessThan(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThan(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThanOrEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThanOrEqualTo(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathLessThanOrEqualTo(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesLessThanOrEqualTo(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThanOrEqualTo(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaLike(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLike(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLike(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathLike(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesLike(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLike(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdNotEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotEqualTo(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotEqualTo(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathNotEqualTo(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesNotEqualTo(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotEqualTo(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotLike(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotLike(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathNotLike(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesNotLike(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotLike(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpProjectWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameRegexp(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameRegexp(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathRegexp(request.getRootPath());
        }

        if (request.getTables() != null) {
            count++;
            criteria.andTablesRegexp(request.getTables());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkRegexp(request.getRemark());
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

        public Criteria andRootPathIsNull() {
            addCriterion("root_path is null");
            return (Criteria) this;
        }

        public Criteria andRootPathIsNotNull() {
            addCriterion("root_path is not null");
            return (Criteria) this;
        }

        public Criteria andRootPathEqualTo(String value) {
            addCriterion("root_path =", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathNotEqualTo(String value) {
            addCriterion("root_path <>", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathGreaterThan(String value) {
            addCriterion("root_path >", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathGreaterThanOrEqualTo(String value) {
            addCriterion("root_path >=", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathLessThan(String value) {
            addCriterion("root_path <", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathLessThanOrEqualTo(String value) {
            addCriterion("root_path <=", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathRegexp(String value) {
            addCriterion("root_path regexp", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathLike(String value) {
            addCriterion("root_path like", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathNotLike(String value) {
            addCriterion("root_path not like", value, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathIn(List<String> values) {
            addCriterion("root_path in", values, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathNotIn(List<String> values) {
            addCriterion("root_path not in", values, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathBetween(String value1, String value2) {
            addCriterion("root_path between", value1, value2, "rootPath");
            return (Criteria) this;
        }

        public Criteria andRootPathNotBetween(String value1, String value2) {
            addCriterion("root_path not between", value1, value2, "rootPath");
            return (Criteria) this;
        }

        public Criteria andTablesIsNull() {
            addCriterion("`tables` is null");
            return (Criteria) this;
        }

        public Criteria andTablesIsNotNull() {
            addCriterion("`tables` is not null");
            return (Criteria) this;
        }

        public Criteria andTablesEqualTo(String value) {
            addCriterion("`tables` =", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesNotEqualTo(String value) {
            addCriterion("`tables` <>", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesGreaterThan(String value) {
            addCriterion("`tables` >", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesGreaterThanOrEqualTo(String value) {
            addCriterion("`tables` >=", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesLessThan(String value) {
            addCriterion("`tables` <", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesLessThanOrEqualTo(String value) {
            addCriterion("`tables` <=", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesRegexp(String value) {
            addCriterion("`tables` regexp", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesLike(String value) {
            addCriterion("`tables` like", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesNotLike(String value) {
            addCriterion("`tables` not like", value, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesIn(List<String> values) {
            addCriterion("`tables` in", values, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesNotIn(List<String> values) {
            addCriterion("`tables` not in", values, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesBetween(String value1, String value2) {
            addCriterion("`tables` between", value1, value2, "tables");
            return (Criteria) this;
        }

        public Criteria andTablesNotBetween(String value1, String value2) {
            addCriterion("`tables` not between", value1, value2, "tables");
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

    }

    @Getter
    @Setter
    public static class CriteriaString {
        private String id;

        private String workspaceName;

        private String name;

        private String rootPath;

        private String tables;

        private String remark;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

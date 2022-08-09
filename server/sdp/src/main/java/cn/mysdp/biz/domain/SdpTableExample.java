package cn.mysdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpTableExample
 * @Description: Example
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
public class SdpTableExample {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpTableExample() {
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

    public static SdpTableExample createExample(SdpTableWithBLOBs request) throws Exception {
        return createExample(request, null);
    }

    public static SdpTableExample createExample(SdpTableWithBLOBs request, JSONObject options) throws Exception {
        SdpTableExample example = new SdpTableExample();
        SdpTableExample.Criteria criteria = example.createCriteria();
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
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpTableWithBLOBs.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpTableWithBLOBs.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpTableWithBLOBs.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpTableWithBLOBs.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpTableWithBLOBs.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpTableWithBLOBs.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpTableWithBLOBs.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpTableWithBLOBs.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpTableWithBLOBs.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpTableWithBLOBs.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpTableWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpTableWithBLOBs.class);
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

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpTableExample example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "id", criteriaOrderBy.getId());
            subCount += appendCriteriaOrderByItem(sb, "workspace_name", criteriaOrderBy.getWorkspaceName());
            subCount += appendCriteriaOrderByItem(sb, "`name`", criteriaOrderBy.getName());
            subCount += appendCriteriaOrderByItem(sb, "alias_name", criteriaOrderBy.getAliasName());
            subCount += appendCriteriaOrderByItem(sb, "remark", criteriaOrderBy.getRemark());
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

    private static int setCriteriaEqualTo(SdpTableWithBLOBs request, Criteria criteria) {
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

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameEqualTo(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkEqualTo(request.getRemark());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpTableWithBLOBs request, Criteria criteria) {
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

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameGreaterThan(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThan(request.getRemark());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoGreaterThan(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpTableWithBLOBs request, Criteria criteria) {
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

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameGreaterThanOrEqualTo(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThanOrEqualTo(request.getRemark());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoGreaterThanOrEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpTableWithBLOBs request, Criteria criteria) {
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

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameIsNotNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNotNull();
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpTableWithBLOBs request, Criteria criteria) {
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

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameIsNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNull();
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpTableWithBLOBs request, Criteria criteria) {
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

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameLessThan(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThan(request.getRemark());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLessThan(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpTableWithBLOBs request, Criteria criteria) {
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

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameLessThanOrEqualTo(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThanOrEqualTo(request.getRemark());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLessThanOrEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaLike(SdpTableWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLike(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLike(request.getName());
        }

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameLike(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLike(request.getRemark());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoLike(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpTableWithBLOBs request, Criteria criteria) {
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

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameNotEqualTo(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotEqualTo(request.getRemark());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoNotEqualTo(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpTableWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotLike(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotLike(request.getName());
        }

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameNotLike(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotLike(request.getRemark());
        }

        if (request.getExtraInfo() != null) {
            count++;
            criteria.andExtraInfoNotLike(request.getExtraInfo());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpTableWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameRegexp(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameRegexp(request.getName());
        }

        if (request.getAliasName() != null) {
            count++;
            criteria.andAliasNameRegexp(request.getAliasName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkRegexp(request.getRemark());
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

        public Criteria andAliasNameIsNull() {
            addCriterion("alias_name is null");
            return (Criteria) this;
        }

        public Criteria andAliasNameIsNotNull() {
            addCriterion("alias_name is not null");
            return (Criteria) this;
        }

        public Criteria andAliasNameEqualTo(String value) {
            addCriterion("alias_name =", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameNotEqualTo(String value) {
            addCriterion("alias_name <>", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameGreaterThan(String value) {
            addCriterion("alias_name >", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameGreaterThanOrEqualTo(String value) {
            addCriterion("alias_name >=", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameLessThan(String value) {
            addCriterion("alias_name <", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameLessThanOrEqualTo(String value) {
            addCriterion("alias_name <=", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameRegexp(String value) {
            addCriterion("alias_name regexp", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameLike(String value) {
            addCriterion("alias_name like", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameNotLike(String value) {
            addCriterion("alias_name not like", value, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameIn(List<String> values) {
            addCriterion("alias_name in", values, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameNotIn(List<String> values) {
            addCriterion("alias_name not in", values, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameBetween(String value1, String value2) {
            addCriterion("alias_name between", value1, value2, "aliasName");
            return (Criteria) this;
        }

        public Criteria andAliasNameNotBetween(String value1, String value2) {
            addCriterion("alias_name not between", value1, value2, "aliasName");
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

        private String name;

        private String aliasName;

        private String remark;

        private String extraInfo;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

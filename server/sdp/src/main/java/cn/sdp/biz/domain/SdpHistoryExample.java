package cn.sdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpHistoryExample
 * @Description: Example
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
public class SdpHistoryExample {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpHistoryExample() {
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

    public static SdpHistoryExample createExample(SdpHistoryWithBLOBs request) throws Exception {
        return createExample(request, null);
    }

    public static SdpHistoryExample createExample(SdpHistoryWithBLOBs request, JSONObject options) throws Exception {
        SdpHistoryExample example = new SdpHistoryExample();
        SdpHistoryExample.Criteria criteria = example.createCriteria();
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
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpHistoryWithBLOBs.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpHistoryWithBLOBs.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpHistoryWithBLOBs.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpHistoryWithBLOBs.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpHistoryWithBLOBs.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpHistoryWithBLOBs.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpHistoryWithBLOBs.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpHistoryWithBLOBs.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpHistoryWithBLOBs.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpHistoryWithBLOBs.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpHistoryWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpHistoryWithBLOBs.class);
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

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpHistoryExample example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "id", criteriaOrderBy.getId());
            subCount += appendCriteriaOrderByItem(sb, "workspace_name", criteriaOrderBy.getWorkspaceName());
            subCount += appendCriteriaOrderByItem(sb, "`table_name`", criteriaOrderBy.getTableName());
            subCount += appendCriteriaOrderByItem(sb, "update_time", criteriaOrderBy.getUpdateTime());
            subCount += appendCriteriaOrderByItem(sb, "remark", criteriaOrderBy.getRemark());
            subCount += appendCriteriaOrderByItem(sb, "content", criteriaOrderBy.getContent());
            if (subCount > 1) {
                throw new Exception("order by数组元素对象属性多于1个");
            }
        }
        if (sb.length() == 0) {
            appendCriteriaOrderByItem(sb, "1", "");
        }

        return sb.toString();
    }

    private static int setCriteriaEqualTo(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameEqualTo(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameEqualTo(request.getTableName());
        }

        if (request.getUpdateTime() != null) {
            count++;
            criteria.andUpdateTimeEqualTo(request.getUpdateTime());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkEqualTo(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentEqualTo(request.getContent());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThan(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThan(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameGreaterThan(request.getTableName());
        }

        if (request.getUpdateTime() != null) {
            count++;
            criteria.andUpdateTimeGreaterThan(request.getUpdateTime());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThan(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentGreaterThan(request.getContent());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThanOrEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameGreaterThanOrEqualTo(request.getTableName());
        }

        if (request.getUpdateTime() != null) {
            count++;
            criteria.andUpdateTimeGreaterThanOrEqualTo(request.getUpdateTime());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThanOrEqualTo(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentGreaterThanOrEqualTo(request.getContent());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNotNull();
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNotNull();
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameIsNotNull();
        }

        if (request.getUpdateTime() != null) {
            count++;
            criteria.andUpdateTimeIsNotNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNotNull();
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNull();
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNull();
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameIsNull();
        }

        if (request.getUpdateTime() != null) {
            count++;
            criteria.andUpdateTimeIsNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNull();
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThan(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThan(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameLessThan(request.getTableName());
        }

        if (request.getUpdateTime() != null) {
            count++;
            criteria.andUpdateTimeLessThan(request.getUpdateTime());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThan(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentLessThan(request.getContent());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThanOrEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameLessThanOrEqualTo(request.getTableName());
        }

        if (request.getUpdateTime() != null) {
            count++;
            criteria.andUpdateTimeLessThanOrEqualTo(request.getUpdateTime());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThanOrEqualTo(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentLessThanOrEqualTo(request.getContent());
        }

        return count;
    }

    private static int setCriteriaLike(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLike(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameLike(request.getTableName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLike(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentLike(request.getContent());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdNotEqualTo(request.getId());
        }

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotEqualTo(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameNotEqualTo(request.getTableName());
        }

        if (request.getUpdateTime() != null) {
            count++;
            criteria.andUpdateTimeNotEqualTo(request.getUpdateTime());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotEqualTo(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentNotEqualTo(request.getContent());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotLike(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameNotLike(request.getTableName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotLike(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentNotLike(request.getContent());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpHistoryWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameRegexp(request.getWorkspaceName());
        }

        if (request.getTableName() != null) {
            count++;
            criteria.andTableNameRegexp(request.getTableName());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkRegexp(request.getRemark());
        }

        if (request.getContent() != null) {
            count++;
            criteria.andContentRegexp(request.getContent());
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentRegexp(String value) {
            addCriterion("content regexp", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

    }

    @Getter
    @Setter
    public static class CriteriaString {
        private String id;

        private String workspaceName;

        private String tableName;

        private String updateTime;

        private String remark;

        private String content;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

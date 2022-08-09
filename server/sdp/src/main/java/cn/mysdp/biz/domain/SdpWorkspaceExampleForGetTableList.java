package cn.mysdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpWorkspaceExampleForGetTableList
 * @Description: SQLExample
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public class SdpWorkspaceExampleForGetTableList {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected SdpWorkspaceForGetTableList simple;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpWorkspaceExampleForGetTableList() {
        oredCriteria = new ArrayList<>();
        simple = new SdpWorkspaceForGetTableList();
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

    public void setSimple(SdpWorkspaceForGetTableList simple) {
        this.simple = simple;
    }

    public SdpWorkspaceForGetTableList getSimple() {
        return simple;
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

    public static SdpWorkspaceExampleForGetTableList createExample(SdpWorkspaceForGetTableList request) throws Exception {
        return createExample(request, null);
    }

    public static SdpWorkspaceExampleForGetTableList createExample(SdpWorkspaceForGetTableList request, JSONObject options) throws Exception {
        SdpWorkspaceExampleForGetTableList example = new SdpWorkspaceExampleForGetTableList();
        example.setSimple(request);
        SdpWorkspaceExampleForGetTableList.Criteria criteria = example.createCriteria();
        int count = 0;

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
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpWorkspaceForGetTableList.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpWorkspaceForGetTableList.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpWorkspaceForGetTableList.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpWorkspaceForGetTableList.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpWorkspaceForGetTableList.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpWorkspaceForGetTableList.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpWorkspaceForGetTableList.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpWorkspaceForGetTableList.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpWorkspaceForGetTableList.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpWorkspaceForGetTableList.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpWorkspaceForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpWorkspaceForGetTableList.class);
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

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpWorkspaceExampleForGetTableList example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "workspace_name", criteriaOrderBy.getWorkspaceName());
            subCount += appendCriteriaOrderByItem(sb, "`name`", criteriaOrderBy.getName());
            if (subCount > 1) {
                throw new Exception("order by数组元素对象属性多于1个");
            }
        }
        if (sb.length() == 0) {
            appendCriteriaOrderByItem(sb, "1", "");
        }

        return sb.toString();
    }

    private static int setCriteriaEqualTo(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameEqualTo(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameEqualTo(request.getName());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThan(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThan(request.getName());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameGreaterThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThanOrEqualTo(request.getName());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNotNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameIsNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThan(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThan(request.getName());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLessThanOrEqualTo(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThanOrEqualTo(request.getName());
        }

        return count;
    }

    private static int setCriteriaLike(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameLike(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLike(request.getName());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotEqualTo(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotEqualTo(request.getName());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameNotLike(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotLike(request.getName());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpWorkspaceForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getWorkspaceName() != null) {
            count++;
            criteria.andWorkspaceNameRegexp(request.getWorkspaceName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameRegexp(request.getName());
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

    }

    @Getter
    @Setter
    public static class CriteriaString {
        private String workspaceName;

        private String name;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

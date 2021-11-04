package cn.mysdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpProjectExampleForGetTableList
 * @Description: SQLExample
 * @Author: SDP
 * @Date: 2021-08-05
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 项目
 */
public class SdpProjectExampleForGetTableList {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected SdpProjectForGetTableList simple;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpProjectExampleForGetTableList() {
        oredCriteria = new ArrayList<>();
		simple = new SdpProjectForGetTableList();
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

    public void setSimple(SdpProjectForGetTableList simple) {
        this.simple = simple;
    }

    public SdpProjectForGetTableList getSimple() {
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

    public static SdpProjectExampleForGetTableList createExample(SdpProjectForGetTableList request) throws Exception {
        return createExample(request, null);
    }

    public static SdpProjectExampleForGetTableList createExample(SdpProjectForGetTableList request, JSONObject options) throws Exception {
        SdpProjectExampleForGetTableList example = new SdpProjectExampleForGetTableList();
        example.setSimple(request);
        SdpProjectExampleForGetTableList.Criteria criteria = example.createCriteria();
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
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpProjectForGetTableList.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpProjectForGetTableList.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpProjectForGetTableList.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpProjectForGetTableList.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpProjectForGetTableList.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpProjectForGetTableList.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpProjectForGetTableList.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpProjectForGetTableList.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpProjectForGetTableList.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpProjectForGetTableList.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpProjectForGetTableList obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpProjectForGetTableList.class);
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

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpProjectExampleForGetTableList example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "project_name", criteriaOrderBy.getProjectName());
            subCount += appendCriteriaOrderByItem(sb, "name", criteriaOrderBy.getName());
            if (subCount > 1) {
                throw new Exception("order by数组元素对象属性多于1个");
            }
        }
        if (sb.length() == 0) {
            appendCriteriaOrderByItem(sb, "1", "");
        }

        return sb.toString();
    }

    private static int setCriteriaEqualTo(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameEqualTo(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameEqualTo(request.getName());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameGreaterThan(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThan(request.getName());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameGreaterThanOrEqualTo(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThanOrEqualTo(request.getName());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameIsNotNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameIsNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameLessThan(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThan(request.getName());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameLessThanOrEqualTo(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThanOrEqualTo(request.getName());
        }

        return count;
    }

    private static int setCriteriaLike(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameLike(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLike(request.getName());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameNotEqualTo(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotEqualTo(request.getName());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameNotLike(request.getProjectName());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotLike(request.getName());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpProjectForGetTableList request, Criteria criteria) {
        int count = 0;

        if (request.getProjectName() != null) {
            count++;
            criteria.andProjectNameRegexp(request.getProjectName());
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
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameRegexp(String value) {
            addCriterion("name regexp", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

    }

    @Getter
    @Setter
    public static class CriteriaString {
        private String projectName;

        private String name;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

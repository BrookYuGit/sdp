package cn.mysdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpWorkspaceExample
 * @Description: Example
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public class SdpWorkspaceExample {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpWorkspaceExample() {
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

    public static SdpWorkspaceExample createExample(SdpWorkspaceWithBLOBs request) throws Exception {
        return createExample(request, null);
    }

    public static SdpWorkspaceExample createExample(SdpWorkspaceWithBLOBs request, JSONObject options) throws Exception {
        SdpWorkspaceExample example = new SdpWorkspaceExample();
        SdpWorkspaceExample.Criteria criteria = example.createCriteria();
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
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpWorkspaceWithBLOBs.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpWorkspaceWithBLOBs obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpWorkspaceWithBLOBs.class);
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

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpWorkspaceExample example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "id", criteriaOrderBy.getId());
            subCount += appendCriteriaOrderByItem(sb, "`name`", criteriaOrderBy.getName());
            subCount += appendCriteriaOrderByItem(sb, "root_path", criteriaOrderBy.getRootPath());
            subCount += appendCriteriaOrderByItem(sb, "db_host", criteriaOrderBy.getDbHost());
            subCount += appendCriteriaOrderByItem(sb, "db_port", criteriaOrderBy.getDbPort());
            subCount += appendCriteriaOrderByItem(sb, "db_database", criteriaOrderBy.getDbDatabase());
            subCount += appendCriteriaOrderByItem(sb, "db_username", criteriaOrderBy.getDbUsername());
            subCount += appendCriteriaOrderByItem(sb, "db_password", criteriaOrderBy.getDbPassword());
            subCount += appendCriteriaOrderByItem(sb, "db_classname", criteriaOrderBy.getDbClassname());
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

    private static int setCriteriaEqualTo(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdEqualTo(request.getId());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameEqualTo(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathEqualTo(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostEqualTo(request.getDbHost());
        }

        if (request.getDbPort() != null) {
            count++;
            criteria.andDbPortEqualTo(request.getDbPort());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseEqualTo(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameEqualTo(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordEqualTo(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameEqualTo(request.getDbClassname());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkEqualTo(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThan(request.getId());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThan(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathGreaterThan(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostGreaterThan(request.getDbHost());
        }

        if (request.getDbPort() != null) {
            count++;
            criteria.andDbPortGreaterThan(request.getDbPort());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseGreaterThan(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameGreaterThan(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordGreaterThan(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameGreaterThan(request.getDbClassname());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThan(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdGreaterThanOrEqualTo(request.getId());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameGreaterThanOrEqualTo(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathGreaterThanOrEqualTo(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostGreaterThanOrEqualTo(request.getDbHost());
        }

        if (request.getDbPort() != null) {
            count++;
            criteria.andDbPortGreaterThanOrEqualTo(request.getDbPort());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseGreaterThanOrEqualTo(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameGreaterThanOrEqualTo(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordGreaterThanOrEqualTo(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameGreaterThanOrEqualTo(request.getDbClassname());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkGreaterThanOrEqualTo(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNotNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNotNull();
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathIsNotNull();
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostIsNotNull();
        }

        if (request.getDbPort() != null) {
            count++;
            criteria.andDbPortIsNotNull();
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseIsNotNull();
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameIsNotNull();
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordIsNotNull();
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameIsNotNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdIsNull();
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameIsNull();
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathIsNull();
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostIsNull();
        }

        if (request.getDbPort() != null) {
            count++;
            criteria.andDbPortIsNull();
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseIsNull();
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameIsNull();
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordIsNull();
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameIsNull();
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThan(request.getId());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThan(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathLessThan(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostLessThan(request.getDbHost());
        }

        if (request.getDbPort() != null) {
            count++;
            criteria.andDbPortLessThan(request.getDbPort());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseLessThan(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameLessThan(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordLessThan(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameLessThan(request.getDbClassname());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThan(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdLessThanOrEqualTo(request.getId());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameLessThanOrEqualTo(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathLessThanOrEqualTo(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostLessThanOrEqualTo(request.getDbHost());
        }

        if (request.getDbPort() != null) {
            count++;
            criteria.andDbPortLessThanOrEqualTo(request.getDbPort());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseLessThanOrEqualTo(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameLessThanOrEqualTo(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordLessThanOrEqualTo(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameLessThanOrEqualTo(request.getDbClassname());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLessThanOrEqualTo(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaLike(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getName() != null) {
            count++;
            criteria.andNameLike(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathLike(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostLike(request.getDbHost());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseLike(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameLike(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordLike(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameLike(request.getDbClassname());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkLike(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getId() != null) {
            count++;
            criteria.andIdNotEqualTo(request.getId());
        }

        if (request.getName() != null) {
            count++;
            criteria.andNameNotEqualTo(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathNotEqualTo(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostNotEqualTo(request.getDbHost());
        }

        if (request.getDbPort() != null) {
            count++;
            criteria.andDbPortNotEqualTo(request.getDbPort());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseNotEqualTo(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameNotEqualTo(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordNotEqualTo(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameNotEqualTo(request.getDbClassname());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotEqualTo(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getName() != null) {
            count++;
            criteria.andNameNotLike(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathNotLike(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostNotLike(request.getDbHost());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseNotLike(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameNotLike(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordNotLike(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameNotLike(request.getDbClassname());
        }

        if (request.getRemark() != null) {
            count++;
            criteria.andRemarkNotLike(request.getRemark());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpWorkspaceWithBLOBs request, Criteria criteria) {
        int count = 0;

        if (request.getName() != null) {
            count++;
            criteria.andNameRegexp(request.getName());
        }

        if (request.getRootPath() != null) {
            count++;
            criteria.andRootPathRegexp(request.getRootPath());
        }

        if (request.getDbHost() != null) {
            count++;
            criteria.andDbHostRegexp(request.getDbHost());
        }

        if (request.getDbDatabase() != null) {
            count++;
            criteria.andDbDatabaseRegexp(request.getDbDatabase());
        }

        if (request.getDbUsername() != null) {
            count++;
            criteria.andDbUsernameRegexp(request.getDbUsername());
        }

        if (request.getDbPassword() != null) {
            count++;
            criteria.andDbPasswordRegexp(request.getDbPassword());
        }

        if (request.getDbClassname() != null) {
            count++;
            criteria.andDbClassnameRegexp(request.getDbClassname());
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

        public Criteria andDbHostIsNull() {
            addCriterion("db_host is null");
            return (Criteria) this;
        }

        public Criteria andDbHostIsNotNull() {
            addCriterion("db_host is not null");
            return (Criteria) this;
        }

        public Criteria andDbHostEqualTo(String value) {
            addCriterion("db_host =", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostNotEqualTo(String value) {
            addCriterion("db_host <>", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostGreaterThan(String value) {
            addCriterion("db_host >", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostGreaterThanOrEqualTo(String value) {
            addCriterion("db_host >=", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostLessThan(String value) {
            addCriterion("db_host <", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostLessThanOrEqualTo(String value) {
            addCriterion("db_host <=", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostRegexp(String value) {
            addCriterion("db_host regexp", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostLike(String value) {
            addCriterion("db_host like", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostNotLike(String value) {
            addCriterion("db_host not like", value, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostIn(List<String> values) {
            addCriterion("db_host in", values, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostNotIn(List<String> values) {
            addCriterion("db_host not in", values, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostBetween(String value1, String value2) {
            addCriterion("db_host between", value1, value2, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbHostNotBetween(String value1, String value2) {
            addCriterion("db_host not between", value1, value2, "dbHost");
            return (Criteria) this;
        }

        public Criteria andDbPortIsNull() {
            addCriterion("db_port is null");
            return (Criteria) this;
        }

        public Criteria andDbPortIsNotNull() {
            addCriterion("db_port is not null");
            return (Criteria) this;
        }

        public Criteria andDbPortEqualTo(Integer value) {
            addCriterion("db_port =", value, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortNotEqualTo(Integer value) {
            addCriterion("db_port <>", value, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortGreaterThan(Integer value) {
            addCriterion("db_port >", value, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("db_port >=", value, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortLessThan(Integer value) {
            addCriterion("db_port <", value, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortLessThanOrEqualTo(Integer value) {
            addCriterion("db_port <=", value, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortIn(List<Integer> values) {
            addCriterion("db_port in", values, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortNotIn(List<Integer> values) {
            addCriterion("db_port not in", values, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortBetween(Integer value1, Integer value2) {
            addCriterion("db_port between", value1, value2, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbPortNotBetween(Integer value1, Integer value2) {
            addCriterion("db_port not between", value1, value2, "dbPort");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseIsNull() {
            addCriterion("db_database is null");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseIsNotNull() {
            addCriterion("db_database is not null");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseEqualTo(String value) {
            addCriterion("db_database =", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseNotEqualTo(String value) {
            addCriterion("db_database <>", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseGreaterThan(String value) {
            addCriterion("db_database >", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseGreaterThanOrEqualTo(String value) {
            addCriterion("db_database >=", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseLessThan(String value) {
            addCriterion("db_database <", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseLessThanOrEqualTo(String value) {
            addCriterion("db_database <=", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseRegexp(String value) {
            addCriterion("db_database regexp", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseLike(String value) {
            addCriterion("db_database like", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseNotLike(String value) {
            addCriterion("db_database not like", value, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseIn(List<String> values) {
            addCriterion("db_database in", values, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseNotIn(List<String> values) {
            addCriterion("db_database not in", values, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseBetween(String value1, String value2) {
            addCriterion("db_database between", value1, value2, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbDatabaseNotBetween(String value1, String value2) {
            addCriterion("db_database not between", value1, value2, "dbDatabase");
            return (Criteria) this;
        }

        public Criteria andDbUsernameIsNull() {
            addCriterion("db_username is null");
            return (Criteria) this;
        }

        public Criteria andDbUsernameIsNotNull() {
            addCriterion("db_username is not null");
            return (Criteria) this;
        }

        public Criteria andDbUsernameEqualTo(String value) {
            addCriterion("db_username =", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameNotEqualTo(String value) {
            addCriterion("db_username <>", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameGreaterThan(String value) {
            addCriterion("db_username >", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("db_username >=", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameLessThan(String value) {
            addCriterion("db_username <", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameLessThanOrEqualTo(String value) {
            addCriterion("db_username <=", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameRegexp(String value) {
            addCriterion("db_username regexp", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameLike(String value) {
            addCriterion("db_username like", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameNotLike(String value) {
            addCriterion("db_username not like", value, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameIn(List<String> values) {
            addCriterion("db_username in", values, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameNotIn(List<String> values) {
            addCriterion("db_username not in", values, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameBetween(String value1, String value2) {
            addCriterion("db_username between", value1, value2, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbUsernameNotBetween(String value1, String value2) {
            addCriterion("db_username not between", value1, value2, "dbUsername");
            return (Criteria) this;
        }

        public Criteria andDbPasswordIsNull() {
            addCriterion("db_password is null");
            return (Criteria) this;
        }

        public Criteria andDbPasswordIsNotNull() {
            addCriterion("db_password is not null");
            return (Criteria) this;
        }

        public Criteria andDbPasswordEqualTo(String value) {
            addCriterion("db_password =", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordNotEqualTo(String value) {
            addCriterion("db_password <>", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordGreaterThan(String value) {
            addCriterion("db_password >", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("db_password >=", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordLessThan(String value) {
            addCriterion("db_password <", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordLessThanOrEqualTo(String value) {
            addCriterion("db_password <=", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordRegexp(String value) {
            addCriterion("db_password regexp", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordLike(String value) {
            addCriterion("db_password like", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordNotLike(String value) {
            addCriterion("db_password not like", value, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordIn(List<String> values) {
            addCriterion("db_password in", values, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordNotIn(List<String> values) {
            addCriterion("db_password not in", values, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordBetween(String value1, String value2) {
            addCriterion("db_password between", value1, value2, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbPasswordNotBetween(String value1, String value2) {
            addCriterion("db_password not between", value1, value2, "dbPassword");
            return (Criteria) this;
        }

        public Criteria andDbClassnameIsNull() {
            addCriterion("db_classname is null");
            return (Criteria) this;
        }

        public Criteria andDbClassnameIsNotNull() {
            addCriterion("db_classname is not null");
            return (Criteria) this;
        }

        public Criteria andDbClassnameEqualTo(String value) {
            addCriterion("db_classname =", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameNotEqualTo(String value) {
            addCriterion("db_classname <>", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameGreaterThan(String value) {
            addCriterion("db_classname >", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameGreaterThanOrEqualTo(String value) {
            addCriterion("db_classname >=", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameLessThan(String value) {
            addCriterion("db_classname <", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameLessThanOrEqualTo(String value) {
            addCriterion("db_classname <=", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameRegexp(String value) {
            addCriterion("db_classname regexp", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameLike(String value) {
            addCriterion("db_classname like", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameNotLike(String value) {
            addCriterion("db_classname not like", value, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameIn(List<String> values) {
            addCriterion("db_classname in", values, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameNotIn(List<String> values) {
            addCriterion("db_classname not in", values, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameBetween(String value1, String value2) {
            addCriterion("db_classname between", value1, value2, "dbClassname");
            return (Criteria) this;
        }

        public Criteria andDbClassnameNotBetween(String value1, String value2) {
            addCriterion("db_classname not between", value1, value2, "dbClassname");
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

        private String name;

        private String rootPath;

        private String dbHost;

        private String dbPort;

        private String dbDatabase;

        private String dbUsername;

        private String dbPassword;

        private String dbClassname;

        private String remark;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

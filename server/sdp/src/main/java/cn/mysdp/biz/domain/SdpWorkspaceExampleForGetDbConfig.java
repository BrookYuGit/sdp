package cn.mysdp.biz.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * InterfaceName: SdpWorkspaceExampleForGetDbConfig
 * @Description: SQLExample
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public class SdpWorkspaceExampleForGetDbConfig {
    protected Integer limitClause;

    protected Integer countClause;

    protected Integer topClause;

    protected SdpWorkspaceForGetDbConfig simple;

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdpWorkspaceExampleForGetDbConfig() {
        oredCriteria = new ArrayList<>();
        simple = new SdpWorkspaceForGetDbConfig();
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

    public void setSimple(SdpWorkspaceForGetDbConfig simple) {
        this.simple = simple;
    }

    public SdpWorkspaceForGetDbConfig getSimple() {
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

    public static SdpWorkspaceExampleForGetDbConfig createExample(SdpWorkspaceForGetDbConfig request) throws Exception {
        return createExample(request, null);
    }

    public static SdpWorkspaceExampleForGetDbConfig createExample(SdpWorkspaceForGetDbConfig request, JSONObject options) throws Exception {
        SdpWorkspaceExampleForGetDbConfig example = new SdpWorkspaceExampleForGetDbConfig();
        example.setSimple(request);
        SdpWorkspaceExampleForGetDbConfig.Criteria criteria = example.createCriteria();
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
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("="), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaEqualTo(obj, criteria);
            }
            if(options.containsKey(">")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject(">"), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaGreaterThan(obj, criteria);
            }
            if(options.containsKey(">=")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject(">="), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaGreaterThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("!null")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("!null"), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaIsNotNull(obj, criteria);
            }
            if(options.containsKey("null")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("null"), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaIsNull(obj, criteria);
            }
            if(options.containsKey("<")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("<"), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaLessThan(obj, criteria);
            }
            if(options.containsKey("<=")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("<="), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaLessThanOrEqualTo(obj, criteria);
            }
            if(options.containsKey("like")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("like"), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaLike(obj, criteria);
            }
            if(options.containsKey("!=")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("!="), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaNotEqualTo(obj, criteria);
            }
            if(options.containsKey("!like")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("!like"), SdpWorkspaceForGetDbConfig.class);
                count += setCriteriaNotLike(obj, criteria);
            }
            if(options.containsKey("regexp")) {
                SdpWorkspaceForGetDbConfig obj = JSONObject.toJavaObject(options.getJSONObject("regexp"), SdpWorkspaceForGetDbConfig.class);
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

    private static String setCriteriaOrderBy(List<CriteriaString> request, SdpWorkspaceExampleForGetDbConfig example) throws Exception {
        StringBuilder sb = new StringBuilder();
        for(CriteriaString criteriaOrderBy: request) {
            int subCount = 0;

            subCount += appendCriteriaOrderByItem(sb, "`host`", criteriaOrderBy.getHost());
            subCount += appendCriteriaOrderByItem(sb, "port", criteriaOrderBy.getPort());
            subCount += appendCriteriaOrderByItem(sb, "db", criteriaOrderBy.getDb());
            subCount += appendCriteriaOrderByItem(sb, "`user`", criteriaOrderBy.getUser());
            if (subCount > 1) {
                throw new Exception("order by数组元素对象属性多于1个");
            }
        }
        if (sb.length() == 0) {
            appendCriteriaOrderByItem(sb, "1", "");
        }

        return sb.toString();
    }

    private static int setCriteriaEqualTo(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostEqualTo(request.getHost());
        }

        if (request.getPort() != null) {
            count++;
            criteria.andPortEqualTo(request.getPort());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbEqualTo(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserEqualTo(request.getUser());
        }

        return count;
    }

    private static int setCriteriaGreaterThan(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostGreaterThan(request.getHost());
        }

        if (request.getPort() != null) {
            count++;
            criteria.andPortGreaterThan(request.getPort());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbGreaterThan(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserGreaterThan(request.getUser());
        }

        return count;
    }

    private static int setCriteriaGreaterThanOrEqualTo(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostGreaterThanOrEqualTo(request.getHost());
        }

        if (request.getPort() != null) {
            count++;
            criteria.andPortGreaterThanOrEqualTo(request.getPort());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbGreaterThanOrEqualTo(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserGreaterThanOrEqualTo(request.getUser());
        }

        return count;
    }

    private static int setCriteriaIsNotNull(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostIsNotNull();
        }

        if (request.getPort() != null) {
            count++;
            criteria.andPortIsNotNull();
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbIsNotNull();
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserIsNotNull();
        }

        return count;
    }

    private static int setCriteriaIsNull(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostIsNull();
        }

        if (request.getPort() != null) {
            count++;
            criteria.andPortIsNull();
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbIsNull();
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserIsNull();
        }

        return count;
    }

    private static int setCriteriaLessThan(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostLessThan(request.getHost());
        }

        if (request.getPort() != null) {
            count++;
            criteria.andPortLessThan(request.getPort());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbLessThan(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserLessThan(request.getUser());
        }

        return count;
    }

    private static int setCriteriaLessThanOrEqualTo(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostLessThanOrEqualTo(request.getHost());
        }

        if (request.getPort() != null) {
            count++;
            criteria.andPortLessThanOrEqualTo(request.getPort());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbLessThanOrEqualTo(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserLessThanOrEqualTo(request.getUser());
        }

        return count;
    }

    private static int setCriteriaLike(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostLike(request.getHost());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbLike(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserLike(request.getUser());
        }

        return count;
    }

    private static int setCriteriaNotEqualTo(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostNotEqualTo(request.getHost());
        }

        if (request.getPort() != null) {
            count++;
            criteria.andPortNotEqualTo(request.getPort());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbNotEqualTo(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserNotEqualTo(request.getUser());
        }

        return count;
    }

    private static int setCriteriaNotLike(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostNotLike(request.getHost());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbNotLike(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserNotLike(request.getUser());
        }

        return count;
    }

    private static int setCriteriaRegexp(SdpWorkspaceForGetDbConfig request, Criteria criteria) {
        int count = 0;

        if (request.getHost() != null) {
            count++;
            criteria.andHostRegexp(request.getHost());
        }

        if (request.getDb() != null) {
            count++;
            criteria.andDbRegexp(request.getDb());
        }

        if (request.getUser() != null) {
            count++;
            criteria.andUserRegexp(request.getUser());
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

        public Criteria andHostIsNull() {
            addCriterion("`host` is null");
            return (Criteria) this;
        }

        public Criteria andHostIsNotNull() {
            addCriterion("`host` is not null");
            return (Criteria) this;
        }

        public Criteria andHostEqualTo(String value) {
            addCriterion("`host` =", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostNotEqualTo(String value) {
            addCriterion("`host` <>", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostGreaterThan(String value) {
            addCriterion("`host` >", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostGreaterThanOrEqualTo(String value) {
            addCriterion("`host` >=", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostLessThan(String value) {
            addCriterion("`host` <", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostLessThanOrEqualTo(String value) {
            addCriterion("`host` <=", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostRegexp(String value) {
            addCriterion("`host` regexp", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostLike(String value) {
            addCriterion("`host` like", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostNotLike(String value) {
            addCriterion("`host` not like", value, "host");
            return (Criteria) this;
        }

        public Criteria andHostIn(List<String> values) {
            addCriterion("`host` in", values, "host");
            return (Criteria) this;
        }

        public Criteria andHostNotIn(List<String> values) {
            addCriterion("`host` not in", values, "host");
            return (Criteria) this;
        }

        public Criteria andHostBetween(String value1, String value2) {
            addCriterion("`host` between", value1, value2, "host");
            return (Criteria) this;
        }

        public Criteria andHostNotBetween(String value1, String value2) {
            addCriterion("`host` not between", value1, value2, "host");
            return (Criteria) this;
        }

        public Criteria andPortIsNull() {
            addCriterion("port is null");
            return (Criteria) this;
        }

        public Criteria andPortIsNotNull() {
            addCriterion("port is not null");
            return (Criteria) this;
        }

        public Criteria andPortEqualTo(Integer value) {
            addCriterion("port =", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotEqualTo(Integer value) {
            addCriterion("port <>", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThan(Integer value) {
            addCriterion("port >", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("port >=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThan(Integer value) {
            addCriterion("port <", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThanOrEqualTo(Integer value) {
            addCriterion("port <=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortIn(List<Integer> values) {
            addCriterion("port in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotIn(List<Integer> values) {
            addCriterion("port not in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortBetween(Integer value1, Integer value2) {
            addCriterion("port between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotBetween(Integer value1, Integer value2) {
            addCriterion("port not between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andDbIsNull() {
            addCriterion("db is null");
            return (Criteria) this;
        }

        public Criteria andDbIsNotNull() {
            addCriterion("db is not null");
            return (Criteria) this;
        }

        public Criteria andDbEqualTo(String value) {
            addCriterion("db =", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbNotEqualTo(String value) {
            addCriterion("db <>", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbGreaterThan(String value) {
            addCriterion("db >", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbGreaterThanOrEqualTo(String value) {
            addCriterion("db >=", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbLessThan(String value) {
            addCriterion("db <", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbLessThanOrEqualTo(String value) {
            addCriterion("db <=", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbRegexp(String value) {
            addCriterion("db regexp", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbLike(String value) {
            addCriterion("db like", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbNotLike(String value) {
            addCriterion("db not like", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbIn(List<String> values) {
            addCriterion("db in", values, "db");
            return (Criteria) this;
        }

        public Criteria andDbNotIn(List<String> values) {
            addCriterion("db not in", values, "db");
            return (Criteria) this;
        }

        public Criteria andDbBetween(String value1, String value2) {
            addCriterion("db between", value1, value2, "db");
            return (Criteria) this;
        }

        public Criteria andDbNotBetween(String value1, String value2) {
            addCriterion("db not between", value1, value2, "db");
            return (Criteria) this;
        }

        public Criteria andUserIsNull() {
            addCriterion("`user` is null");
            return (Criteria) this;
        }

        public Criteria andUserIsNotNull() {
            addCriterion("`user` is not null");
            return (Criteria) this;
        }

        public Criteria andUserEqualTo(String value) {
            addCriterion("`user` =", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotEqualTo(String value) {
            addCriterion("`user` <>", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThan(String value) {
            addCriterion("`user` >", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserGreaterThanOrEqualTo(String value) {
            addCriterion("`user` >=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThan(String value) {
            addCriterion("`user` <", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLessThanOrEqualTo(String value) {
            addCriterion("`user` <=", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserRegexp(String value) {
            addCriterion("`user` regexp", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserLike(String value) {
            addCriterion("`user` like", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotLike(String value) {
            addCriterion("`user` not like", value, "user");
            return (Criteria) this;
        }

        public Criteria andUserIn(List<String> values) {
            addCriterion("`user` in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotIn(List<String> values) {
            addCriterion("`user` not in", values, "user");
            return (Criteria) this;
        }

        public Criteria andUserBetween(String value1, String value2) {
            addCriterion("`user` between", value1, value2, "user");
            return (Criteria) this;
        }

        public Criteria andUserNotBetween(String value1, String value2) {
            addCriterion("`user` not between", value1, value2, "user");
            return (Criteria) this;
        }

    }

    @Getter
    @Setter
    public static class CriteriaString {
        private String host;

        private String port;

        private String db;

        private String user;

    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }
}

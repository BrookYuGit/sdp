# SDP
SQL-driven programming

The SDP is a tool for generate source code, which base on SpringBoot and Mybatis.

The backend of SDP based on Mybatis generator plugin https://github.com/mybatis/generator.
The front end of SDP based on https://github.com/chuzhixin/vue-admin-beautiful-pro.

# SDP template language
SDP use template to generate source file.
The tempalate is very simple: without IF, without for. Only support two style:block and element.
The block is just like {*xxx}, and the element just like {yyy}.
Block is asa 'FOR' in other source code generate tool.

# A sample for automatic convert real select to Mybatis
```sql
select t.* from sdp_workspace t
where 88=88
and name = 'sdp'
and db_host like '%localhost%'
```
This select can be convert to xml for Mybatis via add a config in the tool by a easy-use UI. Then, you can get the xml such as :
```xml
<select id="getWorkspaceList" parameterType="cn.mysdp.biz.domain.SdpWorkspaceForGetWorkspaceList" resultType="cn.mysdp.biz.domain.SdpWorkspaceForGetWorkspaceList">
<![CDATA[
    select t.id as id
,t.`name` as `name`
,t.root_path as root_path
,t.db_host as db_host
,t.db_port as db_port
,t.db_database as db_database
,t.db_username as db_username
,t.db_password as db_password
,t.remark as remark
 from sdp_workspace t
where 88=88
]]>
<if test="name != null">
and name = #{name}
</if>
<if test="dbHostLike != null">
and db_host like #{dbHostLike}
</if>
<![CDATA[

]]>
  </select>
```

package cn.mysdp.biz.facade;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import cn.mysdp.biz.dto.response.SdpWorkspaceGetTableListResponse;
import cn.mysdp.biz.dto.response.SdpWorkspaceQueryResponse;
import cn.mysdp.biz.repository.*;
import cn.mysdp.utils.ConnectUtil;
import cn.mysdp.utils.SplitUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.ConnectionFactory;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.db.ActualTableName;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLNonTransientConnectionException;
import java.util.*;

import static org.springframework.jdbc.support.JdbcUtils.closeResultSet;

/**
 * @ClassName:
 * @Description: FacadeCustomImpl
 * @Author: SDP
 * @Date: 2021-08-30
 * @Version: 1.0
 * Table:
 * Comment:
 *
 */
@Slf4j
public class SdpWorkspaceFacadeCustomImpl extends SdpWorkspaceFacadeBaseImpl {

    @Value("${spring.datasource.url}")
    private String springDatasourceUrl;

    @Value("${spring.datasource.username}")
    private String springDatasourceUsername;

    @Value("${spring.datasource.password}")
    private String springDatasourcePassword;

    @Autowired
    DataSource dataSource;

    @Autowired
    SdpWorkspaceMapper sdpWorkspaceMapper;

    @Autowired
    SdpProjectMapper sdpProjectMapper;

    @Autowired
    SdpWorkspaceConfigMapper sdpWorkspaceConfigMapper;

    @Autowired
    SdpTemplateMapper sdpTemplateMapper;

    @Autowired
    SdpSqlMapper sdpSqlMapper;

    @Autowired
    SdpHistoryMapper sdpHistoryMapper;

    @Autowired
    ProcessSQLFacade processSQLFacade;

    @Autowired
    SdpWorkspaceConfigFacade sdpWorkspaceConfigFacade;

    @Autowired
    SdpProjectFacade sdpProjectFacade;

    @Autowired
    SdpTemplateFacade sdpTemplateFacade;

    @Autowired
    SdpSqlFacade sdpSqlFacade;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addSdpWorkspace(SdpWorkspaceAddRequest request) throws Exception {
        checkLowercaseName(request.getName());

        if (StringUtils.isEmpty(request.getName())) {
            throw new Exception("缺少名称："+request.getName());
        }
        if (request.getName().indexOf('.') >= 0) {
            throw new Exception("名称不可包含小数点");
        }
        request.setDbPassword(processSQLFacade.encryptDBPassword(request));
        return super.addSdpWorkspace(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateSdpWorkspace(SdpWorkspaceUpdateRequest request) throws Exception {
        checkLowercaseName(request.getName());

        request.setDbPassword(processSQLFacade.encryptDBPassword(request));

        SdpWorkspaceWithBLOBs record = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(request.getId());
        if (record == null) {
            throw new Exception("not exist："+request.getId());
        }

        if (Integer.valueOf(0).equals(request.getDbPort())) {
            record.setDbPort(null);
            sdpWorkspaceMapper.updateByPrimaryKeyWithBLOBs(record);
            request.setDbPort(null);
        }
        if (!StringUtils.isEmpty(request.getName())) {
            if (request.getName().indexOf('.') >= 0) {
                throw new Exception("name invalid:include invalid character:'.'");
            }
            if (!request.getName().equals(record.getName())) {
                SdpWorkspaceConfigExample destExample = new SdpWorkspaceConfigExample();
                destExample.createCriteria().andWorkspaceNameEqualTo(record.getName());
                List<SdpWorkspaceConfigWithBLOBs> destList = sdpWorkspaceConfigMapper.selectByExampleWithBLOBs(destExample);

                SdpWorkspaceConfigBatchUpdateRequest destBatchUpdateRequest = new SdpWorkspaceConfigBatchUpdateRequest();
                destBatchUpdateRequest.setWorkspaceName(request.getName());
                destBatchUpdateRequest.setIds(new ArrayList<>());
                for(SdpWorkspaceConfigWithBLOBs destItem: destList) {
                    destBatchUpdateRequest.getIds().add(destItem.getId());
                }
                sdpWorkspaceConfigFacade.batchUpdateSdpWorkspaceConfig(destBatchUpdateRequest);
            }

            if (!request.getName().equals(record.getName())) {
                SdpProjectExample destExample = new SdpProjectExample();
                destExample.createCriteria().andWorkspaceNameEqualTo(record.getName());
                List<SdpProjectWithBLOBs> destList = sdpProjectMapper.selectByExampleWithBLOBs(destExample);

                SdpProjectBatchUpdateRequest destBatchUpdateRequest = new SdpProjectBatchUpdateRequest();
                destBatchUpdateRequest.setWorkspaceName(request.getName());
                destBatchUpdateRequest.setIds(new ArrayList<>());
                for(SdpProjectWithBLOBs destItem: destList) {
                    destBatchUpdateRequest.getIds().add(destItem.getId());
                }
                sdpProjectFacade.batchUpdateSdpProject(destBatchUpdateRequest);
            }

            if (!request.getName().equals(record.getName())) {
                SdpTemplateExample destExample = new SdpTemplateExample();
                destExample.createCriteria().andWorkspaceNameEqualTo(record.getName());
                List<SdpTemplateWithBLOBs> destList = sdpTemplateMapper.selectByExampleWithBLOBs(destExample);

                SdpTemplateBatchUpdateRequest destBatchUpdateRequest = new SdpTemplateBatchUpdateRequest();
                destBatchUpdateRequest.setWorkspaceName(request.getName());
                destBatchUpdateRequest.setIds(new ArrayList<>());
                for(SdpTemplateWithBLOBs destItem: destList) {
                    destBatchUpdateRequest.getIds().add(destItem.getId());
                }
                sdpTemplateFacade.batchUpdateSdpTemplate(destBatchUpdateRequest);
            }

            if (!request.getName().equals(record.getName())) {
                SdpSqlExample destExample = new SdpSqlExample();
                destExample.createCriteria().andWorkspaceNameEqualTo(record.getName());
                List<SdpSqlWithBLOBs> destList = sdpSqlMapper.selectByExampleWithBLOBs(destExample);

                SdpSqlBatchUpdateRequest destBatchUpdateRequest = new SdpSqlBatchUpdateRequest();
                destBatchUpdateRequest.setWorkspaceName(request.getName());
                destBatchUpdateRequest.setIds(new ArrayList<>());
                for(SdpSqlWithBLOBs destItem: destList) {
                    destBatchUpdateRequest.getIds().add(destItem.getId());
                }
                sdpSqlFacade.batchUpdateSdpSql(destBatchUpdateRequest);
            }
        }

        if (request.getName() == null
                && request.getDbHost() == null
                && request.getDbPort() == null
                && request.getDbDatabase() == null
                && request.getDbUsername() == null
                && request.getDbPassword() == null
                && request.getDbClassname() == null
                && request.getRemark() == null
        ) {
            return 1;
        }
        return super.updateSdpWorkspace(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateSdpWorkspace(SdpWorkspaceBatchUpdateRequest request) throws Exception {
        checkLowercaseName(request.getName());
        return super.batchUpdateSdpWorkspace(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteSdpWorkspace(SdpWorkspaceDeleteRequest request) throws Exception {
        BaseRequest baseRequest = new BaseRequest();
        BeanUtils.copyProperties(request, baseRequest);

        for(int id : request.getId()) {
            SdpWorkspaceWithBLOBs record = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(id);

            if (record == null || StringUtils.isEmpty(record.getName())) {
                continue;
            }
            {
                SdpSqlExample example = new SdpSqlExample();
                example.createCriteria().andWorkspaceNameEqualTo(record.getName());
                List<SdpSqlWithBLOBs> list1 = sdpSqlMapper.selectByExampleWithBLOBs(example);
                SdpSqlDeleteRequest destDeleteRequest = new SdpSqlDeleteRequest();
                destDeleteRequest.setId(new ArrayList<>());
                for(SdpSqlWithBLOBs record1: list1) {
                    if ("sql".equals(record1.getParameterCatalog())) {
                        continue;
                    }
                    destDeleteRequest.getId().add(record1.getId());
                }
                if (destDeleteRequest.getId().size() > 0) {
                    sdpSqlFacade.deleteSdpSql(destDeleteRequest);
                    Thread.sleep(1000);
                    destDeleteRequest.getId().clear();
                }
                for(SdpSqlWithBLOBs record1: list1) {
                    if (!"sql".equals(record1.getParameterCatalog())) {
                        continue;
                    }
                    destDeleteRequest.getId().add(record1.getId());
                }
                if (destDeleteRequest.getId().size() > 0) {
                    sdpSqlFacade.deleteSdpSql(destDeleteRequest);
                    Thread.sleep(1000);
                }
            }
            {
                SdpTemplateExample example = new SdpTemplateExample();
                example.createCriteria().andWorkspaceNameEqualTo(record.getName());

                SdpTemplateDeleteRequest destDeleteRequest = new SdpTemplateDeleteRequest();
                destDeleteRequest.setId(new ArrayList<>());
                List<SdpTemplateWithBLOBs> list1 = sdpTemplateMapper.selectByExampleWithBLOBs(example);
                for(SdpTemplateWithBLOBs record1: list1) {
                    destDeleteRequest.getId().add(record1.getId());
                }

                if (destDeleteRequest.getId().size() > 0) {
                    sdpTemplateFacade.deleteSdpTemplate(destDeleteRequest);
                    Thread.sleep(1000);
                }
            }
            {
                SdpProjectExample example = new SdpProjectExample();
                example.createCriteria().andWorkspaceNameEqualTo(record.getName());
                SdpProjectDeleteRequest destDeleteRequest = new SdpProjectDeleteRequest();
                destDeleteRequest.setId(new ArrayList<>());
                List<SdpProjectWithBLOBs> list1 = sdpProjectMapper.selectByExampleWithBLOBs(example);
                for(SdpProjectWithBLOBs record1: list1) {
                    destDeleteRequest.getId().add(record1.getId());
                }
                if (destDeleteRequest.getId().size() > 0) {
                    sdpProjectFacade.deleteSdpProject(destDeleteRequest);
                    Thread.sleep(1000);
                }
            }
            {
                boolean hasDelete = false;
                SdpWorkspaceConfigExample example = new SdpWorkspaceConfigExample();
                example.createCriteria().andWorkspaceNameEqualTo(record.getName());
                SdpWorkspaceConfigDeleteRequest destDeleteRequest = new SdpWorkspaceConfigDeleteRequest();
                destDeleteRequest.setId(new ArrayList<>());
                List<SdpWorkspaceConfigWithBLOBs> list1 = sdpWorkspaceConfigMapper.selectByExampleWithBLOBs(example);
                for(SdpWorkspaceConfigWithBLOBs record1: list1) {
                    destDeleteRequest.getId().add(record1.getId());
                }
                if (destDeleteRequest.getId().size() > 0) {
                    sdpWorkspaceConfigFacade.deleteSdpWorkspaceConfig(destDeleteRequest);
                    Thread.sleep(1000);
                }
            }
        }
        return super.deleteSdpWorkspace(request);
    }

    @Override
    public List<SdpWorkspaceQueryResponse> listSdpWorkspace(SdpWorkspaceQueryRequest request) throws Exception {
        List<SdpWorkspaceQueryResponse> list = super.listSdpWorkspace(request);
        for(SdpWorkspaceQueryResponse item: list) {
            if (!StringUtils.isEmpty(item.getDbPassword())) {
                item.setDbPassword("******");
            }
        }
        return list;
    }

    @Override
    public List<SdpWorkspaceQueryResponse> listSdpWorkspaceWithBLOBs(SdpWorkspaceQueryRequest request) throws Exception {
        List<SdpWorkspaceQueryResponse> list = super.listSdpWorkspaceWithBLOBs(request);
        for(SdpWorkspaceQueryResponse item: list) {
            if (!StringUtils.isEmpty(item.getDbPassword())) {
                item.setDbPassword("******");
            }
        }
        return list;
    }

    public List<SdpWorkspaceQueryResponse> listSdpWorkspaceByExampleWithBLOBs(SdpWorkspaceQueryRequest request) throws Exception {
        List<SdpWorkspaceQueryResponse> list = super.listSdpWorkspaceByExampleWithBLOBs(request);
        for(SdpWorkspaceQueryResponse item: list) {
            if (!StringUtils.isEmpty(item.getDbPassword())) {
                item.setDbPassword("******");
            }
        }
        return list;
    }

    @Override
    public Integer cloneWorkspace(BaseNameIdRequest request) throws Exception {
        checkLowercaseName(request.getName());

        SdpWorkspaceWithBLOBs sdpWorkspace = sdpWorkspaceMapper.selectByNameWithBLOBs(request.getName());

        if (sdpWorkspace != null) {
            throw new Exception("dest name exist:"+request.getName());
        }

        sdpWorkspace = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(request.getId());
        if (request.getName().equals(sdpWorkspace.getName())) {
            throw new Exception("dest name is it self");
        }

        String oriName = sdpWorkspace.getName();

        sdpWorkspace.setName(request.getName());
        sdpWorkspace.setId(null);
        sdpWorkspaceMapper.insertSelective(sdpWorkspace);

        {
            SdpProjectExample example = new SdpProjectExample();
            example.createCriteria().andWorkspaceNameEqualTo(oriName);
            List<SdpProjectWithBLOBs> list = sdpProjectMapper.selectByExampleWithBLOBs(example);
            for(SdpProjectWithBLOBs item: list) {
                item.setWorkspaceName(request.getName());
                item.setId(null);
                sdpProjectMapper.insertSelective(item);
            }

        }

        {
            SdpWorkspaceConfigExample example = new SdpWorkspaceConfigExample();
            example.createCriteria().andWorkspaceNameEqualTo(oriName);
            List<SdpWorkspaceConfigWithBLOBs> list = sdpWorkspaceConfigMapper.selectByExampleWithBLOBs(example);
            for(SdpWorkspaceConfigWithBLOBs item: list) {
                item.setWorkspaceName(request.getName());
                item.setId(null);
                sdpWorkspaceConfigMapper.insertSelective(item);
            }

        }

        {
            SdpTemplateExample example = new SdpTemplateExample();
            example.createCriteria().andWorkspaceNameEqualTo(oriName);
            List<SdpTemplateWithBLOBs> list = sdpTemplateMapper.selectByExampleWithBLOBs(example);
            for(SdpTemplateWithBLOBs item: list) {
                item.setWorkspaceName(request.getName());
                item.setId(null);
                sdpTemplateMapper.insertSelective(item);
            }

        }

        {
            SdpSqlExample example = new SdpSqlExample();
            example.createCriteria().andWorkspaceNameEqualTo(oriName);
            List<SdpSqlWithBLOBs> list = sdpSqlMapper.selectByExampleWithBLOBs(example);
            for(SdpSqlWithBLOBs item: list) {
                item.setWorkspaceName(request.getName());
                item.setId(null);
                sdpSqlMapper.insertSelective(item);
            }

        }

        return 1;
    }

    @Override
    public Integer clone(BaseNameIdRequest request) throws Exception {
        checkLowercaseName(request.getName());

        SdpWorkspaceWithBLOBs sdpWorkspace = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(request.getId());

        SdpWorkspaceWithBLOBs destSdpWorkspace = sdpWorkspaceMapper.selectByNameWithBLOBs(request.getName());

        if (destSdpWorkspace == null) {
            SdpWorkspaceAddRequest addRequest = new SdpWorkspaceAddRequest();
            BeanUtils.copyProperties(sdpWorkspace, addRequest);
            addRequest.setName(request.getName());
            return addSdpWorkspace(addRequest);
        }
        SdpWorkspaceUpdateRequest updateRequest = new SdpWorkspaceUpdateRequest();
        BeanUtils.copyProperties(sdpWorkspace, updateRequest);
        updateRequest.setId(destSdpWorkspace.getId());
        updateRequest.setName(request.getName());
        updateSdpWorkspace(updateRequest);
        SdpWorkspaceWithBLOBs newRecord = new SdpWorkspaceWithBLOBs();
        newRecord.setId(destSdpWorkspace.getId());
        newRecord.setDbPassword(sdpWorkspace.getDbPassword());
        return sdpWorkspaceMapper.updateByPrimaryKeySelective(newRecord);
    }

    @Override
    public List<SdpWorkspaceGetTableListResponse> getTableList(SdpWorkspaceGetTableListRequest request) throws Exception {
        if (StringUtils.isEmpty(request.getWorkspaceName())) {
            throw new Exception("缺少参数:workspace_name");
        }

        SdpWorkspaceWithBLOBs sdpWorkspace = sdpWorkspaceMapper.selectByNameWithBLOBs(request.getWorkspaceName());
        if (sdpWorkspace == null) {
            throw new Exception("无对应项目："+request.getWorkspaceName());
        }

        List<SdpWorkspaceGetTableListResponse> responseList = new ArrayList<>();

        SdpProjectExample sdpProjectExample = new SdpProjectExample();
        sdpProjectExample.createCriteria().andWorkspaceNameEqualTo(request.getWorkspaceName());
        List<SdpProjectWithBLOBs> sdpProjectWithBLOBs = sdpProjectMapper.selectByExampleWithBLOBs(sdpProjectExample);
        Set<String> doneNameSet = new HashSet<>();
        for(SdpProjectWithBLOBs project: sdpProjectWithBLOBs) {
            if (!StringUtils.isEmpty(project.getTables())) {
                String[] tables = SplitUtil.split(project.getTables(), ",");
                for(String table: tables) {
                    table = SplitUtil.split(table, " as ")[0].trim().toLowerCase();
                    if (!doneNameSet.contains(table)) {
                        SdpWorkspaceGetTableListResponse newItem = new SdpWorkspaceGetTableListResponse();
                        newItem.setName(table);
                        responseList.add(newItem);
                        doneNameSet.add(table);
                    }
                }
            }
        }

        SdpWorkspaceQueryResponse workspace = new SdpWorkspaceQueryResponse();
        BeanUtils.copyProperties(sdpWorkspace, workspace);

        String url = ConnectUtil.getUrl(workspace.getDbClassname(), workspace.getDbHost(), workspace.getDbPort(), workspace.getDbDatabase());
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass(workspace.getDbClassname());
        jdbcConnectionConfiguration.setConnectionURL(url);
        jdbcConnectionConfiguration.setUserId(workspace.getDbUsername());
        String dbPassword = processSQLFacade.decryptDbPassword(workspace);
        jdbcConnectionConfiguration.setPassword(dbPassword);

        Context context = new Context(null);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_AUTO_DELIMIT_KEYWORDS, "true");

        JavaTypeResolver javaTypeResolver = ObjectFactory
                .createJavaTypeResolver(context, new ArrayList<>());

        ConnectionFactory connectionFactory;

        jdbcConnectionConfiguration.addProperty("springDatasourceUrl", springDatasourceUrl);
        jdbcConnectionConfiguration.addProperty("springDatasourceUsername", springDatasourceUsername);
        jdbcConnectionConfiguration.addProperty("springDatasourcePassword", springDatasourcePassword);

        connectionFactory = new JDBCConnectionFactory(jdbcConnectionConfiguration);

        Connection connection = null;

        try {
            connection = connectionFactory.getConnection();
        }catch(Exception ex) {
            ex.printStackTrace();
            if (ex instanceof SQLNonTransientConnectionException) {
                Throwable ex1 = ((SQLNonTransientConnectionException)ex).getCause();

                throw new Exception("cannot connect to database："+workspace.getName()+",db："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+":"+workspace.getDbPassword()+"("+ex1.getMessage()+")");

            }
            throw new Exception("cannot connect to database："+workspace.getName()+",db："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+":"+workspace.getDbPassword()+"("+ex.getMessage()+")");
        }

        DatabaseMetaData databaseMetaData = connection.getMetaData();

        Map<ActualTableName, List<IntrospectedColumn>> answer = new HashMap<>();

        ResultSet rs = databaseMetaData.getTables(workspace.getDbDatabase(), null,"%", null);

        if (doneNameSet.size() > 0) {
            SdpWorkspaceGetTableListResponse newItem = new SdpWorkspaceGetTableListResponse();
            newItem.setName("---- tables not include in project ----");
            responseList.add(newItem);
        }
        while (rs.next()) {

            String table = rs.getString("TABLE_NAME").trim().toLowerCase();
            if (!doneNameSet.contains(table)) {
                SdpWorkspaceGetTableListResponse newItem = new SdpWorkspaceGetTableListResponse();
                newItem.setName(table);
                responseList.add(newItem);
                doneNameSet.add(table);
            }

        }

        closeResultSet(rs);


        return responseList;
    }

    @Override
    public String testConnect(SdpWorkspaceUpdateRequest request) throws Exception {
        SdpWorkspaceQueryResponse workspace = new SdpWorkspaceQueryResponse();
        if (request.getId() != null && !Integer.valueOf(0).equals(request.getId())) {
            SdpWorkspaceWithBLOBs oriWorkspace = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(request.getId());
            if (oriWorkspace == null) {
                throw new Exception("Not found workspace:"+request.getId());
            }
            BeanUtils.copyProperties(oriWorkspace, workspace);
            if (!StringUtils.isEmpty(oriWorkspace.getDbPassword())) {
                workspace.setDbPassword(processSQLFacade.decryptDbPassword(workspace));
            }
            if (request.getDbClassname() != null) {
                workspace.setDbClassname(request.getDbClassname());
            }
            if (request.getDbHost() != null) {
                workspace.setDbHost(request.getDbHost());
            }
            if (request.getDbPort() != null) {
                workspace.setDbPort(request.getDbPort());
            }
            if (request.getDbDatabase() != null) {
                workspace.setDbDatabase(request.getDbDatabase());
            }
            if (request.getDbUsername() != null) {
                workspace.setDbUsername(request.getDbUsername());
            }
            if (request.getDbPassword() != null) {
                workspace.setDbPassword(request.getDbPassword());
            }
        } else {
            BeanUtils.copyProperties(request, workspace);
        }
        if (Integer.valueOf(0).equals(request.getDbPort())) {
            workspace.setDbPort(null);
        }

        String url = ConnectUtil.getUrl(workspace.getDbClassname(), workspace.getDbHost(), workspace.getDbPort(), workspace.getDbDatabase());
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass(workspace.getDbClassname());
        jdbcConnectionConfiguration.setConnectionURL(url);
        jdbcConnectionConfiguration.setUserId(workspace.getDbUsername());
        String dbPassword = workspace.getDbPassword();
        jdbcConnectionConfiguration.setPassword(dbPassword);

        Context context = new Context(null);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_AUTO_DELIMIT_KEYWORDS, "true");

        JavaTypeResolver javaTypeResolver = ObjectFactory
                .createJavaTypeResolver(context, new ArrayList<>());

        Connection connection = null;

        try {
            jdbcConnectionConfiguration.addProperty("springDatasourceUrl", springDatasourceUrl);
            jdbcConnectionConfiguration.addProperty("springDatasourceUsername", springDatasourceUsername);
            jdbcConnectionConfiguration.addProperty("springDatasourcePassword", springDatasourcePassword);

            if ("org.h2.Driver".equals(workspace.getDbClassname()) && StringUtils.isEmpty(workspace.getDbDatabase())) {
                connection = dataSource.getConnection();
                context.setConnection(connection);
            } else {
                ConnectionFactory connectionFactory;
                connectionFactory = new JDBCConnectionFactory(jdbcConnectionConfiguration);

                connection = connectionFactory.getConnection();
                connection.close();
            }
        }catch(Exception ex) {
            ex.printStackTrace();
            if (ex instanceof SQLNonTransientConnectionException) {
                Throwable ex1 = ((SQLNonTransientConnectionException)ex).getCause();
                if (ex1 == null) {
                    throw new Exception("无法连接到数据库：数据库："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+"("+ex.getMessage()+")");
                }
                if (ex1.getMessage().indexOf("Access denied for user") >= 0) {
                    throw new Exception("数据库无权限或密码错误：数据库："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+"("+ex1.getMessage()+")"+","+ex.getMessage());
                }

                throw new Exception("无法连接到数据库：数据库："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+"("+ex1.getMessage()+")"+","+ex.getMessage());

            }
            throw new Exception("无法连接到数据库：数据库："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+":"+workspace.getDbPassword()+"("+ex.getMessage()+")");
        }
        return "";
    }

    @Override
    public String fixJsonObject(BaseNameRequest request) throws Exception {
        JSONObject obj = JSONObject.parseObject(request.getName(), Feature.AllowUnQuotedFieldNames, Feature.AllowSingleQuotes);
        return JSONObject.toJSONString(obj);
    }
}

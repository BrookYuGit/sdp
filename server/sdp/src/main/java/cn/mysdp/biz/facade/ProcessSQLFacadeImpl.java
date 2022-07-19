package cn.mysdp.biz.facade;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.BaseNameRequest;
import cn.mysdp.biz.dto.request.SdpWorkspaceAddRequest;
import cn.mysdp.biz.dto.request.SdpWorkspaceUpdateRequest;
import cn.mysdp.biz.dto.response.SdpProjectQueryResponse;
import cn.mysdp.biz.dto.response.SdpTemplateQueryResponse;
import cn.mysdp.biz.dto.response.SdpWorkspaceQueryResponse;
import cn.mysdp.biz.repository.*;
import cn.mysdp.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.MysqlType;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.mybatis.generator.api.ConnectionFactory;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaReservedWords;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.db.SqlReservedWords;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Key;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.escapeStringForJava;

/**
 * @ClassName:
 * @Description: FacadeImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table:
 * Comment:
 *
 */
@Service
public class ProcessSQLFacadeImpl extends BaseFacadeImpl implements ProcessSQLFacade {

    @Autowired
    DataSource dataSource;

    @Autowired
    SdpProjectMapper sdpProjectMapper;

    @Autowired
    SdpWorkspaceConfigMapper sdpWorkspaceConfigMapper;

    @Autowired
    SdpTemplateMapper sdpTemplateMapper;

    @Autowired
    SdpWorkspaceMapper sdpWorkspaceMapper;

    @Autowired
    SdpSqlMapper sdpSqlMapper;

    @Value("${spring.datasource.url}")
    private String springDatasourceUrl;

    @Value("${spring.datasource.username}")
    private String springDatasourceUsername;

    @Value("${spring.datasource.password}")
    private String springDatasourcePassword;

    private static final String DB_PASSWORD_SEED = "567382";

    public static List<String> splitLines(String str) {
        String[] lines = SplitUtil.split(str, "\n\r");
        List<String> destLines = new ArrayList<>();
        for(String line: lines) {
            String[] subLinesByLn = SplitUtil.split(line,"\n");
            for(String subLineByLn: subLinesByLn) {
                String[] subLinesByLr = SplitUtil.split(subLineByLn, "\r");
                for(String subLineByLr: subLinesByLr) {
                    destLines.add(subLineByLr);
                }
            }
        }
        return destLines;
    }

    @Override
    public String decryptDbPassword(SdpWorkspaceQueryResponse record) throws Exception {
        String dbHost = record.getDbHost();
        String dbUsername = record.getDbUsername();
        String dbPassword = record.getDbPassword();
        if (StringUtils.isEmpty(dbPassword)) {
            return dbPassword;
        }

        String data = dbPassword;

        //config
        boolean useCrypto = true;

        Cipher cipher = null;
        if (useCrypto) {
            DESKeySpec dks = new DESKeySpec((DB_PASSWORD_SEED+dbHost+dbUsername).getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);

            cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }
        byte[] src = Base64.decodeBase64(data);

        byte[] bytes = cipher.doFinal(src);

        String dest = new String(bytes, "UTF-8");
        return dest.trim();
    }

    @Override
    public String encryptDBPassword(SdpWorkspaceUpdateRequest request) throws Exception {
        if (StringUtils.isEmpty(request.getDbPassword())
                && StringUtils.isEmpty(request.getDbHost())
                && StringUtils.isEmpty(request.getDbUsername())
        ) {
            if ("".equals(request.getDbPassword())) {
                return "";
            }
            return null;
        }

        SdpWorkspaceWithBLOBs sdpWorkspace = sdpWorkspaceMapper.selectByPrimaryKeyWithBLOBs(request.getId());
        SdpWorkspaceQueryResponse record = new SdpWorkspaceQueryResponse();
        BeanUtils.copyProperties(sdpWorkspace, record);

        String dbPassword = request.getDbPassword();

        if (StringUtils.isEmpty(dbPassword)) {
            dbPassword = decryptDbPassword(record);
        } else {
            dbPassword = dbPassword.trim();
        }

        String dbHost = request.getDbHost();
        if (StringUtils.isEmpty(dbHost)) {
            dbHost = record.getDbHost();
        }
        String dbUsername = request.getDbUsername();
        if (StringUtils.isEmpty(dbUsername)) {
            dbUsername = record.getDbUsername();
        }

        return encryptDBPassword(dbHost, dbUsername, dbPassword);
    }

    @Override
    public String encryptDBPassword(SdpWorkspaceAddRequest request) throws Exception {
        if (StringUtils.isEmpty(request.getDbPassword())) {
            return "";
        }
        if (StringUtils.isEmpty(request.getDbUsername())) {
            throw new Exception("缺少db_username");
        }

        String dbPassword = request.getDbPassword();
        String dbHost = StringUtils.isEmpty(request.getDbHost()) ? "":request.getDbHost();
        String dbUsername = request.getDbUsername();

        return encryptDBPassword(dbHost, dbUsername, dbPassword);
    }

    public String encryptDBPassword(String dbHost, String dbUsername, String dbPassword) throws Exception {
        if (StringUtils.isEmpty(dbHost)) {
            dbHost = "";
        }
        if (StringUtils.isEmpty(dbPassword)) {
            return "";
        }
        if ("******".equals(dbPassword)) {
            return dbPassword;
        }
        if ("********".equals(dbPassword)) {
            return dbPassword;
        }
        String data = dbPassword;

        //config
        boolean useCrypto = true;

        Cipher cipher = null;
        if (useCrypto) {
            DESKeySpec dks = new DESKeySpec((DB_PASSWORD_SEED+dbHost+dbUsername).getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);

            cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }
        byte[] src = data.getBytes("UTF-8");

        int len = src.length;
        int diff = len % 8;
        byte[] bytes;
        byte[]newSrc = new byte[len + 32+(8 - diff)];
        for(int i = 0; i < 8-diff; i++) {
            newSrc[len + 32+i] = ' ';
        }
        for(int i = 0; i < 32; i++) {
            newSrc[i] = ' ';
        }
        System.arraycopy(src, 0, newSrc, 32, len);

        bytes = cipher.doFinal(newSrc);

        return Base64.encodeBase64String(bytes);

    }

    private void pringNotFoundInfo(Set<String> infoSet, String msg) {
        if (!infoSet.contains(msg)) {
            infoSet.add(msg);
            System.out.println(msg);
        }
    }

    @Override
    public Integer execute(BaseNameRequest request, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception {
        FileUtil.clear();

        try {
//            String lineSeparator = System.lineSeparator();
//            lineSeparator = "\r\n";
//            Field lineSeparatorField = System.class.getDeclaredField("lineSeparator");
//            lineSeparatorField.setAccessible(true);
//            lineSeparatorField.set(System.class, lineSeparator);

        }catch (Exception ex) {
            ex.printStackTrace();
        }

        JSONObject jsonObject = JSON.parseObject(request.getName());

        Set<String> fountFoundInfoSet = new HashSet<>();

        Map<String, SdpWorkspaceQueryResponse> sdpWorkspaceMap = new HashMap<>();
        {
            SdpWorkspaceExample example = new SdpWorkspaceExample();
            if (jsonObject.containsKey("workspace_name") && !StringUtils.isEmpty(jsonObject.getString("workspace_name"))) {
                example.createCriteria().andNameEqualTo(jsonObject.getString("workspace_name"));
            }
            //这里不能调用facade的list方法，因为facade中的list已经将密码隐藏了
            List<SdpWorkspaceWithBLOBs> list = sdpWorkspaceMapper.selectByExampleWithBLOBs(example);
            for(SdpWorkspaceWithBLOBs item: list) {
                SdpWorkspaceQueryResponse newItem = new SdpWorkspaceQueryResponse();
                BeanUtils.copyProperties(item, newItem);
                sdpWorkspaceMap.put(item.getName(), newItem);
            }
        }

        Map<String, Map<String, String>> sdpConfigMap = new HashMap<>();
        {
            SdpWorkspaceConfigExample example = new SdpWorkspaceConfigExample();
            if (jsonObject.containsKey("workspace_name")) {
                example.createCriteria().andWorkspaceNameEqualTo(jsonObject.getString("workspace_name"));
            }
            for(SdpWorkspaceConfigWithBLOBs item: sdpWorkspaceConfigMapper.selectByExampleWithBLOBs(example)) {
                String workspaceName = item.getWorkspaceName();
                if (!sdpWorkspaceMap.containsKey(workspaceName)) {
                    pringNotFoundInfo(fountFoundInfoSet, "not found config workspace:"+workspaceName);
                    continue;
                }
                if (!sdpConfigMap.containsKey(workspaceName)) {
                    sdpConfigMap.put(workspaceName, new HashMap<>());
                }
                sdpConfigMap.get(workspaceName).put(item.getName(), item.getValue());
            }
        }

        Map<String, Set<String>> tableMap = new HashMap<>();

        Map<String, SdpProjectQueryResponse> sdpProjectMap = new HashMap<>();
        {
            SdpProjectExample example = new SdpProjectExample();
            if (jsonObject.containsKey("workspace_name")) {
                example.createCriteria().andWorkspaceNameEqualTo(jsonObject.getString("workspace_name"));
            }
            for(SdpProjectWithBLOBs item: sdpProjectMapper.selectByExampleWithBLOBs(example)) {
                String workspaceName = item.getWorkspaceName();
                SdpWorkspaceQueryResponse workspace = sdpWorkspaceMap.get(workspaceName);

                SdpWorkspaceQueryResponse sdpWorkspace = sdpWorkspaceMap.get(workspaceName);
                if (sdpWorkspace == null) {
                    pringNotFoundInfo(fountFoundInfoSet, "not found project workspace:"+workspaceName);
                    continue;
                }

                if (!sdpConfigMap.containsKey(workspaceName)) {
                    pringNotFoundInfo(fountFoundInfoSet, "not found config for project workspace:"+workspaceName);
                    continue;
                }

                SdpProjectQueryResponse newItem = new SdpProjectQueryResponse();
                BeanUtils.copyProperties(item, newItem);

                newItem.setPropertyMap(sdpConfigMap.get(workspaceName));

                sdpProjectMap.put(workspaceName+";" + item.getName(), newItem);

                if (StringUtils.isEmpty(item.getTables())) {
                    continue;
                }

                if (!tableMap.containsKey(workspaceName)) {
                    tableMap.put(workspaceName, new HashSet<>());
                }
                Set<String> tableSet = tableMap.get(workspaceName);

                String schema = sdpWorkspace.getDbDatabase();
                if ("org.h2.Driver".equals(workspace.getDbClassname())) {
                    schema = "public";
                }

                String[] tables = item.getTables().split(",");
                for(String name: tables) {
                    name = name.trim();
                    if (StringUtils.isEmpty(name)) {
                        continue;
                    }
                    String[] names = name.split(" as ");
                    if (names.length > 1) {
                        name = names[0].trim();
                    }
                    tableSet.add(workspaceName + "." + schema + "." + name);
                }
            }
        }


        Map<String, Map<String, List<IntrospectedColumn>>> parameterColumnsMap = new HashMap<>();
        Map<String, List<String>> parameterColumnKeysMap = new HashMap<>();

        Map<String, List<String>> tableMapForList = new HashMap<>();

        for(String workspaceName: tableMap.keySet()) {
            System.out.println("table for workspace:"+workspaceName);
            List<String> tableList = new ArrayList<>();
            tableMapForList.put(workspaceName, tableList);

            for(String name: tableMap.get(workspaceName)) {
                System.out.println("table:"+name);
                name = name.trim();
                String names[] = name.split( " as ");
                if (names.length > 1) {
                    name = names[0].trim();
                }

                tableList.add(name);
            }
        }

        Map<String, Connection> connectionMap = new HashMap<>();
        Map<String, Context> contextMap = new HashMap<>();
        Map<String, JavaTypeResolver> javaTypeResolverMap = new HashMap<>();

        for(String workspaceName: sdpWorkspaceMap.keySet()) {

            SdpWorkspaceQueryResponse workspace = sdpWorkspaceMap.get(workspaceName);

            if (!tableMap.containsKey(workspaceName)) {
//                pringNotFoundInfo(fountFoundInfoSet, "not found table workspace:"+workspaceName);
                continue;
            }

            String url = ConnectUtil.getUrl(workspace.getDbClassname(), workspace.getDbHost(), workspace.getDbPort(), workspace.getDbDatabase());
            JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
            jdbcConnectionConfiguration.setDriverClass(workspace.getDbClassname());
            jdbcConnectionConfiguration.setConnectionURL(url);
            jdbcConnectionConfiguration.setUserId(workspace.getDbUsername());
            String dbPassword = null;
            if (jsonObject.containsKey("db_password")) {
                dbPassword = jsonObject.getString("db_password");
            }
            if (StringUtils.isEmpty(dbPassword)) {
                dbPassword = decryptDbPassword(workspace);
            }
            jdbcConnectionConfiguration.setPassword(dbPassword);

            Context context = new Context(null);
            context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
            context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
            context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
            context.addProperty(PropertyRegistry.CONTEXT_AUTO_DELIMIT_KEYWORDS, "true");

            contextMap.put(workspaceName, context);

            JavaTypeResolver javaTypeResolver = ObjectFactory
                    .createJavaTypeResolver(context, new ArrayList<>());

            javaTypeResolverMap.put(workspaceName, javaTypeResolver);

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
                }
            }catch(Exception ex) {
                ex.printStackTrace();
                if (ex instanceof SQLNonTransientConnectionException) {
                    Throwable ex1 = ((SQLNonTransientConnectionException)ex).getCause();
                    if (ex1 == null) {
                        throw new Exception("无法连接到数据库："+workspaceName+",数据库："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+"("+ex.getMessage()+")");
                    }
                    if (ex1.getMessage().indexOf("Access denied for user") >= 0) {
                        throw new Exception("数据库无权限或密码错误："+workspaceName+",数据库："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+"("+ex1.getMessage()+")"+","+ex.getMessage());
                    }

                    throw new Exception("无法连接到数据库："+workspaceName+",数据库："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+"("+ex1.getMessage()+")"+","+ex.getMessage());

                }
                throw new Exception("无法连接到数据库："+workspaceName+",数据库："+workspace.getDbHost()+":"+workspace.getDbPort()+"@"+workspace.getDbUsername()+":"+workspace.getDbPassword()+"("+ex.getMessage()+")");
            }
            connectionMap.put(workspaceName, connection);
            System.out.println("connection for:"+workspaceName);
        }

        {
            SdpSqlExample example = new SdpSqlExample();
            if (jsonObject.containsKey("workspace_name")) {
                example.createCriteria().andWorkspaceNameEqualTo(jsonObject.getString("workspace_name"));
            }
            example.setOrderByClause("workspace_name, table_name, sort_no, id");
            List<SdpSqlWithBLOBs> sdpSqls = sdpSqlMapper.selectByExampleWithBLOBs(example);
            for (SdpSqlWithBLOBs item : sdpSqls) {
                String workspaceName = item.getWorkspaceName();
                SdpWorkspaceQueryResponse workspace = sdpWorkspaceMap.get(workspaceName);

                if (!tableMap.containsKey(workspaceName)) {
//                pringNotFoundInfo(fountFoundInfoSet, "not found table workspace:"+workspaceName);
                    continue;
                }

                if (workspace == null) {
                    pringNotFoundInfo(fountFoundInfoSet, "not found workspace for sql:" + workspaceName);
                    continue;
                }
                if (!sdpConfigMap.containsKey(workspaceName)) {
                    pringNotFoundInfo(fountFoundInfoSet, "not found config workspace for sql:" + workspaceName);
                    continue;
                }

                Connection connection = connectionMap.get(workspaceName);
                if (connection == null) {
                    pringNotFoundInfo(fountFoundInfoSet, "not found connection for workspace:" + workspaceName);
                    continue;
                }
                Context context = contextMap.get(workspaceName);
                String schema = workspace.getDbDatabase();
                if ("org.h2.Driver".equals(workspace.getDbClassname())) {
                    schema = "public";
                }
                Set<String> tableSet = tableMap.get(workspace.getName());
                if (tableSet == null) {
                    pringNotFoundInfo(fountFoundInfoSet, "not found tableSet for workspace:" + workspaceName);
                    continue;
                }

                String tableName = item.getTableName();
                String parameterCatalogName = item.getParameterCatalog();
                String parameterCatalogType = item.getParameterCatalogType();
                String parameterJavaReturnType = item.getJavaReturnType();
                String parameterJavaTypeName = item.getJavaType().replaceAll(" ", "");
                String parameterName = item.getName();
                String parameterMode = item.getParameterMode();
                String parameterSql = item.getParameterSql();
                String parameterSqlValue = item.getParameterSqlValue();
                String parameterExtraInfo = item.getExtraInfo();

                Integer parameterSqlValueIngore = 0;
                if (Integer.valueOf(1).equals(item.getParameterSqlValueIgnore())) {
                    parameterSqlValueIngore = 1;
                }
                String parameterJavaBody = item.getJavaBody();
                Integer parameterSqlIsSimple = item.getParameterSqlIssimple();
                Integer parameterSqlReutrnNoList = item.getParameterSqlReturnNolist();
                Integer parameterNullable = item.getParameterNullable();
                Integer parameterIsLike = item.getParameterIsLike();
                Integer parameterIsImportExcel = item.getParameterIsImportExcel();
                Integer parameterIsExportExcel = item.getParameterIsExportExcel();
                Integer parameterWithoutTest = item.getParameterWithoutTest();

                if (StringUtils.isEmpty(parameterMode)) {
                    throw new Exception("!!! === parameter_mode不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }

                if (StringUtils.isEmpty(parameterCatalogName)) {
                    throw new Exception("!!! === parameter_catalog不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }

                if (StringUtils.isEmpty(parameterCatalogType)) {
                    throw new Exception("!!! === parameter_catalog_type不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }
                if (parameterName != null && Pattern.matches("_[0-9]", parameterName)) {
                    throw new Exception("!!! === parameter_name不可以在下划线后跟随数字! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }
                if (parameterCatalogName != null && Pattern.matches("_[0-9]", parameterCatalogName)) {
                    throw new Exception("!!! === parameter_catalog不可以在下划线后跟随数字! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }
                if (parameterCatalogType != null && Pattern.matches("_[0-9]", parameterCatalogType)) {
                    throw new Exception("!!! === parameter_catalog_type不可以在下划线后跟随数字! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }

                if (!parameterName.equals(JavaBeansUtil.getAjaxString(JavaBeansUtil.getCamelCaseString(JavaBeansUtil.getAjaxString(parameterName), false)))) {
                    throw new Exception("name：_后必须为小写英文字母:" + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }
                if (!parameterCatalogType.equals(JavaBeansUtil.getAjaxString(JavaBeansUtil.getCamelCaseString(JavaBeansUtil.getAjaxString(parameterCatalogType), false)))) {
                    throw new Exception("catalog_type非法：_后必须为小写英文字母:" + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }
                parameterMode = JavaBeansUtil.getAjaxString(JavaBeansUtil.getCamelCaseString(JavaBeansUtil.getAjaxString(parameterMode), false));
                parameterCatalogName = JavaBeansUtil.getAjaxString(JavaBeansUtil.getCamelCaseString(JavaBeansUtil.getAjaxString(parameterCatalogName), false));
                parameterCatalogType = JavaBeansUtil.getAjaxString(JavaBeansUtil.getCamelCaseString(JavaBeansUtil.getAjaxString(parameterCatalogType), false));
                parameterName = JavaBeansUtil.getAjaxString(JavaBeansUtil.getCamelCaseString(JavaBeansUtil.getAjaxString(parameterName), false));


                if ("sql".equals(parameterCatalogName)) {
                    if (StringUtils.isEmpty(parameterSql)) {
                        throw new Exception("!!! === parameter_sql不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                    }
                } else if ("api.request".equals(parameterCatalogName) ||
                        "api.response".equals(parameterCatalogName)) {
                    if (!"remove".equals(parameterMode)) {
                        if (StringUtils.isEmpty(parameterName)) {
                            throw new Exception("!!! === parameter_name不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                        }
                        if (StringUtils.isEmpty(parameterJavaTypeName)) {
                            throw new Exception("!!! === parameter_java_type不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                        }
                    }
                } else if ("api.facade".equals(parameterCatalogName)) {
                    if (!"remove".equals(parameterMode)) {
                        if (StringUtils.isEmpty(parameterJavaTypeName)) {
                            throw new Exception("!!! === parameter_java_type不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                        }
                        if (StringUtils.isEmpty(parameterJavaReturnType)) {
                            throw new Exception("!!! === parameter_java_return_type不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                        }
                    }
                } else if ("sql.param".equals(parameterCatalogName)) {
                    if (StringUtils.isEmpty(parameterName)) {
                        throw new Exception("!!! === parameter_name不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                    }
                    if (!Integer.valueOf(1).equals(parameterSqlValueIngore) && StringUtils.isEmpty(parameterSqlValue)) {
                        throw new Exception("!!! === parameter_sql_value不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                    }
                } else if ("sql.response".equals(parameterCatalogName)) {
                    if (StringUtils.isEmpty(parameterName)) {
                        throw new Exception("!!! === parameter_name不能为空! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                    }
                } else {
                    throw new Exception("!!! === parameter_catalog类型非法! " + tableName + "," + parameterCatalogName + "." + parameterCatalogType + ", " + parameterName);
                }

                FullyQualifiedJavaType parameterType;

                if (StringUtils.isEmpty(parameterCatalogType)) {
                    parameterCatalogType = "";
                }
                if (parameterJavaTypeName.startsWith("List<")) {
                    String typeName = parameterJavaTypeName.replace("List<", "");
                    typeName = typeName.replace(">", "");
                    parameterType = FullyQualifiedJavaType.getNewListInstance();
                    parameterType.addTypeArgument(new FullyQualifiedJavaType(typeName));
                } else {
                    if ("Date".equals(parameterJavaTypeName)) {
                        parameterType = new FullyQualifiedJavaType("java.util.Date");
                    } else {
                        parameterType = new FullyQualifiedJavaType(parameterJavaTypeName);
                    }
                }

                String jdbcTypeName = "VARCHAR";
                if ("Integer".equals(parameterJavaTypeName)) {
                    jdbcTypeName = "INTEGER";
                } else if ("Long".equals(parameterJavaTypeName)) {
                    jdbcTypeName = "BIGINT";
                } else if ("Double".equals(parameterJavaTypeName)) {
                    jdbcTypeName = "DOUBLE";
                } else if ("Date".equals(parameterJavaTypeName)) {
                    jdbcTypeName = "TIMESTAMP";
                }

                IntrospectedColumn introspectedColumn = new IntrospectedColumn();
                introspectedColumn.setJdbcTypeName(jdbcTypeName);
                if ("Date".equals(parameterJavaTypeName)) {
                    introspectedColumn.setFullyQualifiedJavaType(new FullyQualifiedJavaType("java.util.Date"));
                } else {
                    introspectedColumn.setFullyQualifiedJavaType(new FullyQualifiedJavaType(parameterJavaTypeName));
                }
                introspectedColumn.setJavaProperty(JavaBeansUtil.getCamelCaseString(parameterName, false));
                introspectedColumn.setActualColumnName(parameterName);
                introspectedColumn.setRemarks(item.getRemarks());

                introspectedColumn.setParameterImports(item.getJavaImports());
                introspectedColumn.setParameterJavaReturnType(parameterJavaReturnType);
                introspectedColumn.setParameterJavaType(parameterJavaTypeName);
                introspectedColumn.setFullyQualifiedJavaType(new FullyQualifiedJavaType(parameterJavaTypeName));

                introspectedColumn.setParameterRemark(item.getRemarks());
                introspectedColumn.setParameterMode(parameterMode);
                introspectedColumn.setParameterCatalog(parameterCatalogName);
                introspectedColumn.setParameterCatalogType(parameterCatalogType);
                introspectedColumn.setParameterName(parameterName);
                introspectedColumn.setParameterSql(parameterSql);
                introspectedColumn.setParameterSqlValue(parameterSqlValue);
                introspectedColumn.setParameterExtraInfo(parameterExtraInfo);
                introspectedColumn.setParameterJavaBody(parameterJavaBody);

                introspectedColumn.setParameterSqlIsSimple(parameterSqlIsSimple);
                introspectedColumn.setParameterSqlReturnNoList(parameterSqlReutrnNoList);
                introspectedColumn.setParameterNullable(parameterNullable);
                introspectedColumn.setSqlIsInterface(item.getIsInterface());
                introspectedColumn.setParameterIsLike(parameterIsLike);
                introspectedColumn.setParameterWithoutTest(parameterWithoutTest);
                introspectedColumn.setParameterIsImportExcel(parameterIsImportExcel);
                introspectedColumn.setParameterIsExportExcel(parameterIsExportExcel);
                introspectedColumn.setParameterIgnoreSqlValue(parameterSqlValueIngore);

                if ("api.facade".equals(introspectedColumn.getParameterCatalog())) {
                    if (StringUtils.isEmpty(introspectedColumn.getParameterName())) {
                        introspectedColumn.setParameterName("request");
                    }
                    introspectedColumn.setActualColumnName(introspectedColumn.getParameterCatalogType());
                }

                if ("sql".equals(introspectedColumn.getParameterCatalog())) {
                    introspectedColumn.setActualColumnName(introspectedColumn.getParameterCatalogType());
                }

                if (!parameterColumnsMap.containsKey(workspaceName + "." + schema + "." + tableName)) {
                    parameterColumnsMap.put(workspaceName + "." + schema + "." + tableName, new HashMap<>());
                    parameterColumnKeysMap.put(workspaceName + "." + schema + "." + tableName, new ArrayList<>());
                }
                String key;
                if ("rename".equals(parameterMode)) {
                    key = parameterCatalogName;
                } else {
                    key = parameterCatalogName + "." + parameterCatalogType;
                }
                if (!parameterColumnsMap.get(workspaceName + "." + schema + "." + tableName).containsKey(key)) {
                    parameterColumnsMap.get(workspaceName + "." + schema + "." + tableName).put(key, new ArrayList<>());
                    parameterColumnKeysMap.get(workspaceName + "." + schema + "." + tableName).add(key);
                }
                parameterColumnsMap.get(workspaceName + "." + schema + "." + tableName).get(key).add(introspectedColumn);

                tableSet.add(workspaceName + "." + schema + "." + tableName);

                if ("sql".equals(introspectedColumn.getParameterCatalog())) {
                    PreparedStatement mysqlStatement = null;
                    try {
                        String sql = parameterSql;
                        try {
                            mysqlStatement = connection.prepareStatement(sql);
                        } catch (Exception ex) {
                            System.out.println("=== prepareStatement error, sql ===");
                            System.out.println(sql);
                            throw ex;
                        }
                        ResultSetMetaData resultSetMetaData;
                        try {
                            resultSetMetaData = mysqlStatement.getMetaData();
                        } catch (Exception ex) {
                            System.out.println("=== getMetaData error, sql ===");
                            System.out.println(sql);
                            throw new Exception(schema+"."+tableName+introspectedColumn.getParameterCatalog()+","+introspectedColumn.getParameterCatalogType()+","+introspectedColumn.getParameterName()+":"+ex.getMessage());
                        }


                        Object[] jdbcFields = getMetaFields(resultSetMetaData);

                        Map<String, Method> methodMap = new HashMap<>();
                        methodMap.put("getName", null);
                        methodMap.put("getTableName", null);
                        methodMap.put("getOriginalTableName", null);
                        methodMap.put("getMysqlType", null);

                        Class clz = jdbcFields[0].getClass();
                        while (clz != null) {
                            int count = 0;
                            Method[] methods = jdbcFields[0].getClass().getDeclaredMethods();
                            for (Method method : methods) {
                                count = 0;
                                for (String fieldKey : methodMap.keySet()) {
                                    if (methodMap.get(fieldKey) != null) {
                                        count++;
                                        continue;
                                    }
                                    if (fieldKey.equals(method.getName())) {
                                        method.setAccessible(true);
                                        methodMap.put(fieldKey, method);
                                        count++;
                                    }
                                }
                                if (count == methodMap.size()) {
                                    break;
                                }
                            }
                            if (count == methodMap.size()) {
                                break;
                            }
                            clz = clz.getSuperclass();
                        }

                        for (Object field : jdbcFields) {
                            String fieldOriginalTableName = (String) methodMap.get("getOriginalTableName").invoke(field);

                            if (!StringUtils.isEmpty(fieldOriginalTableName)) {
                                tableSet.add(workspaceName + "." + schema + "." + fieldOriginalTableName);
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw ex;
                    } finally {
                        if (mysqlStatement != null) {
                            mysqlStatement.close();
                        }
                    }
                }
            }
        }

        Map<String, IntrospectedTable> introspectedTableMap = new HashMap<>();

        for(String workspaceName: tableMap.keySet()) {
            Set<String> tableSet = tableMap.get(workspaceName);
            SdpWorkspaceQueryResponse workspace = sdpWorkspaceMap.get(workspaceName);
            Context context = contextMap.get(workspaceName);
            for(String name: tableSet) {
                TableConfiguration tc = new TableConfiguration(context);
                String[] names = name.split("\\.");
                if ("org.h2.Driver".equals(workspace.getDbClassname())) {
                    tc.setSchema("public");
                    tc.setCatalog("public");
                } else {
                    tc.setCatalog(names[1]);
                }
                tc.setTableName(names[2]);
                context.addTableConfiguration(tc);
            }

            List<String> warnings = new ArrayList<>();
            context.introspectTables(null,
                    warnings,
                    null);
            if (warnings.size() > 0) {
                for(String line: warnings) {
                    if (!line.startsWith("Warning")) {
                        throw new Exception("错误："+warnings.get(0));
                    }
                }
            }

            Field introspectedTablesField = getField(context.getClass(), "introspectedTables");
            introspectedTablesField.setAccessible(true);
            List<IntrospectedTable> introspectedTables = (List<IntrospectedTable>) introspectedTablesField.get(context);
            for(IntrospectedTable introspectedTable: introspectedTables) {
                introspectedTableMap.put(workspaceName + "." + introspectedTable.getTableConfiguration().getCatalog() + "." + introspectedTable.getTableConfiguration().getTableName(), introspectedTable);
            }

        }

        try {
            //process select
            for(String fullName: parameterColumnKeysMap.keySet()) {
                String[] names = fullName.split("\\.");
                String workspaceName = names[0];
                String schema = names[1];
                String tableName = names[2];
                Connection connection = connectionMap.get(workspaceName);
                Context context = contextMap.get(workspaceName);
                JavaTypeResolver javaTypeResolver = javaTypeResolverMap.get(workspaceName);

                Map<String, List<IntrospectedColumn>> parameterColumns = parameterColumnsMap.get(workspaceName + "." + schema + "." + tableName);

                if (!parameterColumnKeysMap.containsKey(workspaceName + "." + schema+"."+tableName)) {
                    pringNotFoundInfo(fountFoundInfoSet, "not found columns for fullName:"+fullName);
                    continue;
                }
                List<IntrospectedColumn> columnForSql = new ArrayList<>();
                for(String key: parameterColumnKeysMap.get(workspaceName + "." + schema + "." + tableName)) {
                    IntrospectedColumn oriColumn = parameterColumns.get(key).get(0);
                    if (!"sql".equals(oriColumn.getParameterCatalog())) {
                        continue;
                    }
                    columnForSql.add(oriColumn);
                }

                List<String> tableList = tableMapForList.get(workspaceName);
                if (CollectionUtils.isEmpty(tableList)) {
                    continue;
                }
                for(String table: tableList) {
                    if (table.equals(fullName)) {
                        table = table+"";
                    }
                }

                for(IntrospectedColumn column: columnForSql) {
                    //fix sql
                    String firstOriginalTableName = "";
                    String firstStarTableName = "";
                    Map<String, String> starTableMap = new HashMap<>();
                    List<String> starTableKeyList = new ArrayList<>();

                    String parameterSql = column.getParameterSql();
                    column.setParameterSimpleSql(new String(parameterSql));
                    if (StringUtils.isEmpty(parameterSql)) {
                        throw new Exception("!!! === 当parameter_catalog为sql时，parameter_sql 不能为空! "+tableName+","+column.getParameterCatalog()+"."+column.getParameterCatalogType()+", "+column.getParameterName());
                    }

                    String oriParameterSql = parameterSql;

                    String extraErrorInfo = "";

                    String sql = "";

                    try {
                        List<IntrospectedColumn> introspectedColumns = new ArrayList<>();

                        PreparedStatement mysqlStatement;
                        sql = parameterSql;

                        try {
                            mysqlStatement = connection.prepareStatement(sql);
                        }catch(Exception ex) {
                            System.out.println("=== prepareStatement error, sql ===");
                            System.out.println(sql);
                            throw ex;
                        }
                        ResultSetMetaData resultSetMetaData;
                        try {
                            resultSetMetaData = mysqlStatement.getMetaData();
                        }catch(Exception ex) {
                            System.out.println("=== getMetaData error, sql ===");
                            System.out.println(sql);
                            throw new Exception(schema+"."+tableName+column.getParameterCatalog()+","+column.getParameterCatalogType()+","+column.getParameterName()+":"+ex.getMessage());
                        }

                        Object[] jdbcFields = getMetaFields(resultSetMetaData);

                        Map<String, Method> methodMap = new HashMap<>();
                        methodMap.put("getName", null);
                        methodMap.put("getTableName", null);
                        methodMap.put("getOriginalTableName", null);
                        methodMap.put("getMysqlType", null);

                        Class clz = jdbcFields[0].getClass();
                        while(clz != null) {
                            int count = 0;
                            Method[] methods = jdbcFields[0].getClass().getDeclaredMethods();
                            for(Method method: methods) {
                                count = 0;
                                for(String key: methodMap.keySet()) {
                                    if (methodMap.get(key) != null) {
                                        count++;
                                        continue;
                                    }
                                    String methodName = method.getName();
                                    if (key.equals(methodName)) {
                                        method.setAccessible(true);
                                        methodMap.put(key, method);
                                        count++;
                                    }
                                }
                                if (count == methodMap.size()) {
                                    break;
                                }
                            }
                            if (count == methodMap.size()) {
                                break;
                            }
                            clz = clz.getSuperclass();
                        }

                        List<IntrospectedColumn> oriColumns = new ArrayList<>();

                        for(Object field: jdbcFields) {
                            String fieldOriginalTableName = (String) methodMap.get("getOriginalTableName").invoke(field);
                            String fieldTableName = (String) methodMap.get("getTableName").invoke(field);
                            String fieldName = (String) methodMap.get("getName").invoke(field);
                            String columnName = fieldName;
                            String columnComment = null;
                            if (fieldName.split(";").length > 1) {
                                columnName = fieldName.split(";")[0];
                                columnComment = fieldName.split(";")[1];
                            }
                            Object fieldMysqlTypeIdObject = (Object) methodMap.get("getMysqlType").invoke(field);
                            Integer fieldMysqlTypeId;
                            if (fieldMysqlTypeIdObject instanceof Integer) {
                                fieldMysqlTypeId = (Integer) fieldMysqlTypeIdObject;
                            } else {
                                fieldMysqlTypeId = ((MysqlType)fieldMysqlTypeIdObject).getJdbcType();
                            }
                            IntrospectedColumn oriColumn = new IntrospectedColumn();
                            oriColumn.setActualColumnName(columnName);
                            oriColumn.setOriginalTableName(fieldOriginalTableName);
                            oriColumn.setTableName(fieldTableName);
                            oriColumn.setTypeId(fieldMysqlTypeId);
                            if (!StringUtils.isEmpty(columnComment)) {
                                oriColumn.setRemarks(columnComment);
                            }
                            oriColumns.add(oriColumn);

                            if (!StringUtils.isEmpty(fieldOriginalTableName)) {
                                if ("".equals(firstOriginalTableName)) {
                                    firstOriginalTableName = fieldOriginalTableName;
                                    firstStarTableName = fieldTableName;
                                }
                                if (!starTableMap.containsKey(fieldTableName) && (
                                        parameterSql.contains(fieldTableName+".*") || parameterSql.contains("`"+fieldTableName+"`.*"))) {
                                    starTableMap.put(fieldTableName, fieldOriginalTableName);
                                }
                            }
                        }
                        for(String key: starTableMap.keySet()) {
                            starTableKeyList.add(key);
                        }
                        Collections.sort(starTableKeyList,new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                return o2.length() - o1.length();
                            }
                        });

                        mysqlStatement.close();
                        sql = "";

                        Map<String, IntrospectedColumn> introspectedColumnMapForSql = new HashMap<>();
                        try {

                            Map<String, Integer> doneColumnNameMap = new HashMap<>();

                            //获取除xxx.*外其他的字段名称，用于处理可能存在的字段名重复问题
                            String discardStarTableSQL = column.getParameterSql();
                            int starTableIndex = 0;
                            for (String startTableName : starTableKeyList) {
                                discardStarTableSQL = discardStarTableSQL.replace(startTableName + ".*", "1 as discard_star_table_" + (++starTableIndex));
                                discardStarTableSQL = discardStarTableSQL.replace("`"+startTableName + "`.*", "1 as discard_star_table_" + (++starTableIndex));
                            }
                            sql = discardStarTableSQL;
                            try {
                                mysqlStatement = connection.prepareStatement(sql);
                            } catch (Exception ex) {
                                System.out.println("=== prepareStatement error, sql ===");
                                System.out.println(sql);
                                throw ex;
                            }
                            try {
                                resultSetMetaData = mysqlStatement.getMetaData();
                            } catch (Exception ex) {
                                System.out.println("=== getMetaData error, sql ===");
                                System.out.println(sql);
                                throw new Exception(schema+"."+tableName+column.getParameterCatalog()+","+column.getParameterCatalogType()+","+column.getParameterName()+":"+ex.getMessage());
                            }

                            jdbcFields = getMetaFields(resultSetMetaData);

                            column.setParameterOriSql(new String(column.getParameterSql()));
                            column.setParameterSimpleWithStarSql(new String(column.getParameterSql()));

                            String validChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890$_";
                            String validFirstChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$_";
                            for (Object field : jdbcFields) {
                                String fieldName = (String) methodMap.get("getName").invoke(field);
                                String columnName = fieldName;
                                String columnComment = null;
                                if (SplitUtil.split(fieldName, ";").length > 1) {
                                    columnName = SplitUtil.split(fieldName, ";")[0];
                                    columnComment = SplitUtil.split(fieldName, ";")[1];
                                }
                                if (JavaReservedWords.containsWord(columnName)) {
                                    extraErrorInfo = "非法字段名（Java保留字）:" + column.getParameterCatalog() + "." + column.getParameterCatalogType() + columnName;
                                    throw new Exception(extraErrorInfo);
                                }
                                int cIndex = 0;
                                for (char c : columnName.toCharArray()) {
                                    if ((cIndex == 0 && validFirstChars.indexOf(c) < 0) || (cIndex > 0 && validChars.indexOf(c) < 0)) {
                                        extraErrorInfo = "非法字段名:" + column.getParameterCatalog() + "." + column.getParameterCatalogType() + columnName;
                                        throw new Exception(extraErrorInfo);
                                    }
                                    cIndex++;
                                }
                                if (doneColumnNameMap.containsKey(columnName)) {
                                    extraErrorInfo = "重复的字段:" + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + columnName;
                                    throw new Exception(extraErrorInfo);
                                }
                                doneColumnNameMap.put(columnName, 1);
                            }

                            mysqlStatement.close();
                            sql = "";

                            if (starTableMap.size() == 0 && !StringUtils.isEmpty(firstOriginalTableName)) {
                                IntrospectedTable introspectedTablesForSql = introspectedTableMap.get(workspaceName + "." + schema + "." + firstOriginalTableName);
                                if (introspectedTablesForSql != null) {
                                    List<IntrospectedColumn> introspectedColumnsForSql = introspectedTablesForSql.getAllColumns();

                                    for (IntrospectedColumn oriIntrospectedColumn : introspectedColumnsForSql) {
                                        IntrospectedColumn introspectedColumn = new IntrospectedColumn();
                                        BeanUtils.copyProperties(oriIntrospectedColumn, introspectedColumn);

                                        String columnName;
                                        columnName = introspectedColumn.getActualColumnName();
                                        introspectedColumnMapForSql.put(columnName, introspectedColumn);
                                    }
                                }
                            }

                            for (String startTableName : starTableKeyList) {
                                boolean needAddPrefix = false;
                                String originalTableName = starTableMap.get(startTableName);
                                if (!firstStarTableName.equals(startTableName)) {
                                    needAddPrefix = true;
                                }

                                IntrospectedTable introspectedTablesForSql = introspectedTableMap.get(workspaceName + "." + schema + "." + originalTableName);
                                if (introspectedColumnMapForSql == null) {
                                    continue;
                                }
                                if (introspectedTablesForSql == null) {
                                    System.out.println("=== not found:" + workspaceName + "." + schema + "." + originalTableName);
                                    continue;
                                }
                                List<IntrospectedColumn> oriIntrospectedColumnsForSql = introspectedTablesForSql.getAllColumns();
                                List<IntrospectedColumn> introspectedColumnsForSql = new ArrayList<>();

                                for (IntrospectedColumn oriIntrospectedColumn : oriIntrospectedColumnsForSql) {
                                    IntrospectedColumn introspectedColumn = new IntrospectedColumn();
                                    BeanUtils.copyProperties(oriIntrospectedColumn, introspectedColumn);
                                    introspectedColumnsForSql.add(introspectedColumn);
                                }

                                StringBuilder sb = new StringBuilder();
                                StringBuilder sbSub = new StringBuilder();
                                int lengthCount = 0;

                                for (IntrospectedColumn introspectedColumn : introspectedColumnsForSql) {

                                    if (sb.length() > 0) {
                                        sbSub.append(",");
                                    }
                                    String columnName = introspectedColumn.getActualColumnName().toLowerCase();

                                    if (context.autoDelimitKeywords()
                                            && SqlReservedWords.containsWord(startTableName)) {
                                        sbSub.append(escapeStringForJava(context.getBeginningDelimiter()) +
                                                startTableName + escapeStringForJava(context.getEndingDelimiter()));
                                    } else {
                                        sbSub.append(startTableName);
                                    }
                                    sbSub.append(".");

                                    if (context.autoDelimitKeywords()
                                            && SqlReservedWords.containsWord(columnName)) {
                                        sbSub.append(escapeStringForJava(context.getBeginningDelimiter()) +
                                                columnName + escapeStringForJava(context.getEndingDelimiter()));
                                    } else {
                                        sbSub.append(columnName);
                                    }

                                    sbSub.append(" as ");
                                    if (needAddPrefix) {
                                        if (!StringUtils.isEmpty(introspectedColumn.getRemarks())) {
                                            introspectedColumn.setRemarks(startTableName + " - " + introspectedColumn.getRemarks());
                                        } else {
                                            introspectedColumn.setRemarks(startTableName + " - " + columnName);
                                        }
                                        columnName = startTableName + "_" + columnName;
                                    }
                                    columnName = columnName.toLowerCase();
                                    if (doneColumnNameMap.containsKey(columnName)) {
                                        doneColumnNameMap.put(columnName, doneColumnNameMap.get(columnName) + 1);
                                        columnName += doneColumnNameMap.get(columnName);
                                        introspectedColumn.setDup(true);
                                    } else {
                                        doneColumnNameMap.put(columnName, 1);
                                    }
                                    introspectedColumn.setActualColumnName(columnName);

                                    if (context.autoDelimitKeywords()
                                            && SqlReservedWords.containsWord(columnName)) {
                                        sbSub.append(escapeStringForJava(context.getBeginningDelimiter()) +
                                                columnName + escapeStringForJava(context.getEndingDelimiter()));
                                    } else {
                                        sbSub.append(columnName);
                                    }

                                    introspectedColumnMapForSql.put(columnName, introspectedColumn);
                                    introspectedColumn.setJavaProperty(JavaBeansUtil.getCamelCaseString(introspectedColumn.getActualColumnName(), false));
                                    sb.append(sbSub);
                                    sb.append("\n");
                                    sbSub.setLength(0);
                                }
                                if (sbSub.length() > 0) {
                                    sb.append(sbSub);
                                    sbSub.setLength(0);
                                }

                                column.setParameterSql(column.getParameterSql().replace(startTableName + ".*", sb.toString()));
                                column.setParameterSql(column.getParameterSql().replace("`"+startTableName + "`.*", sb.toString()));

                                column.setParameterSimpleSql(column.getParameterSimpleSql().replace(startTableName + ".*", sb.toString()));
                                column.setParameterSimpleSql(column.getParameterSimpleSql().replace("`"+startTableName + "`.*", sb.toString()));

                                mysqlStatement.close();
                                sql = "";
                            }

                            //check if sql syntax valid
                            sql = "select * from (" + column.getParameterSql() + ") t";
                            try {
                                mysqlStatement = connection.prepareStatement(sql);
                            } catch (Exception ex) {
                                System.out.println("=== prepareStatement error, sql ===");
                                System.out.println(sql);
                                throw ex;
                            }

                            try {
                                resultSetMetaData = mysqlStatement.getMetaData();
                            } catch (Exception ex) {
                                System.out.println("=== getMetaData error, sql ===");
                                System.out.println(sql);
                                throw new Exception(schema+"."+tableName+column.getParameterCatalog()+","+column.getParameterCatalogType()+","+column.getParameterName()+":"+ex.getMessage());
                            }

                            mysqlStatement.close();
                            sql = "";

                        }catch(Exception ex) {
                            ex.printStackTrace();
                            System.out.println("!!! invalid sql");
                            throw ex;
                        }

                        sql = column.getParameterSql();
                        try {
                            mysqlStatement = connection.prepareStatement(sql);
                        } catch (Exception ex) {
                            System.out.println("=== prepareStatement error, sql ===");
                            System.out.println(sql);
                            throw ex;
                        }

                        try {
                            resultSetMetaData = mysqlStatement.getMetaData();
                        } catch (Exception ex) {
                            System.out.println("=== getMetaData error, sql ===");
                            System.out.println(sql);
                            throw new Exception(schema+"."+tableName+column.getParameterCatalog()+","+column.getParameterCatalogType()+","+column.getParameterName()+":"+ex.getMessage());
                        }

                        jdbcFields = getMetaFields(resultSetMetaData);

                        for (int fieldIndex = 0; fieldIndex < jdbcFields.length; fieldIndex++) {
                            Object field = jdbcFields[fieldIndex];
                            String fieldName = (String) methodMap.get("getName").invoke(field);
                            String columnName = fieldName;
                            String columnComment = null;
                            if (fieldName.split(";").length > 1) {
                                columnName = fieldName.split(";")[0];
                                columnComment = fieldName.split(";")[1];
                            }
                            Object fieldMysqlTypeIdObject = (Object) methodMap.get("getMysqlType").invoke(field);
                            Integer fieldMysqlTypeId;
                            if (fieldMysqlTypeIdObject instanceof Integer) {
                                fieldMysqlTypeId = (Integer) fieldMysqlTypeIdObject;
                            } else {
                                fieldMysqlTypeId = ((MysqlType) fieldMysqlTypeIdObject).getJdbcType();
                            }

                            IntrospectedColumn introspectedColumn;
                            if (introspectedColumnMapForSql.containsKey(columnName)) {
                                IntrospectedColumn oriIntrospectedColumn = introspectedColumnMapForSql.get(columnName);
                                introspectedColumn = new IntrospectedColumn();
                                BeanUtils.copyProperties(oriIntrospectedColumn, introspectedColumn);

                                introspectedColumn.setParameterImports("");
                                introspectedColumn.setParameterJavaReturnType("");
                                introspectedColumn.setParameterJavaType(introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName());

                                introspectedColumn.setParameterRemark(introspectedColumn.getRemarks());
                                introspectedColumn.setParameterMode("append");
                                introspectedColumn.setParameterCatalog("api.request_for_sql");
                                introspectedColumn.setParameterCatalogType(column.getParameterCatalogType());
                                introspectedColumn.setParameterName(columnName);
                                introspectedColumn.setParameterSql("");
                                introspectedColumn.setParameterSqlValue("");

                            } else {
                                introspectedColumn = new IntrospectedColumn();
                                introspectedColumn.setJdbcType(fieldMysqlTypeId);
                                String jdbcTypeName = javaTypeResolver
                                        .calculateJdbcTypeName(introspectedColumn);
                                String javaTypeName = resultSetMetaData.getColumnClassName(fieldIndex + 1);
                                if ("java.sql.Date".equals(javaTypeName)) {
                                    javaTypeName = "java.util.Date";
                                }
                                introspectedColumn.setJdbcTypeName(jdbcTypeName);
                                introspectedColumn.setFullyQualifiedJavaType(new FullyQualifiedJavaType(javaTypeName));
                                introspectedColumn.setJavaProperty(JavaBeansUtil.getCamelCaseString(columnName, false));
                                introspectedColumn.setActualColumnName(columnName.toLowerCase());
                                introspectedColumn.setRemarks("");

                                introspectedColumn.setParameterImports("");
                                introspectedColumn.setParameterJavaReturnType("");
                                introspectedColumn.setParameterJavaType(javaTypeName);

                                introspectedColumn.setParameterRemark("");
                                introspectedColumn.setParameterMode("append");
                                introspectedColumn.setParameterCatalog("api.request_for_sql");
                                introspectedColumn.setParameterCatalogType(column.getParameterCatalogType());
                                introspectedColumn.setParameterName(columnName);
                                introspectedColumn.setParameterSql("");
                                introspectedColumn.setParameterSqlValue("");
                            }
                            if ("java.sql.Timestamp".equals(introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName())) {
                                introspectedColumn.setFullyQualifiedJavaType(new FullyQualifiedJavaType("java.util.Date"));
                            }
                            if (!StringUtils.isEmpty(columnComment)) {
                                introspectedColumn.setRemarks(columnComment);
                            }
                            introspectedColumns.add(introspectedColumn);
                        }

                        mysqlStatement.close();
                        sql = "";

                        if (!parameterColumns.containsKey("api.request_for_sql."+column.getParameterCatalogType())) {
                            parameterColumns.put("api.request_for_sql."+column.getParameterCatalogType(), introspectedColumns);
                            parameterColumnKeysMap.get(workspaceName + "." + schema + "." + tableName).add("api.request_for_sql."+column.getParameterCatalogType());
                        }
                        if (!parameterColumns.containsKey("api.response_for_sql."+column.getParameterCatalogType())) {
                            parameterColumns.put("api.response_for_sql."+column.getParameterCatalogType(), introspectedColumns);
                            parameterColumnKeysMap.get(workspaceName + "." + schema + "." + tableName).add("api.response_for_sql."+column.getParameterCatalogType());
                        }

                        List<IntrospectedColumn> extraColumns = parameterColumns.get("sql.param."+column.getParameterCatalogType());
                        if (extraColumns != null) {
                            for(IntrospectedColumn introspectedColumn: extraColumns) {
                                if (Integer.valueOf(1).equals(introspectedColumn.getParameterIgnoreSqlValue())) {
                                    continue;
                                }
                                if (StringUtils.isEmpty(introspectedColumn.getParameterJavaType())) {
                                    extraErrorInfo = "sql.param." + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + introspectedColumn.getParameterName() + " 缺少java_type属性";
                                    throw new Exception(extraErrorInfo);
                                }
                                if (StringUtils.isEmpty(introspectedColumn.getParameterSqlValue())) {
                                    extraErrorInfo = "sql.param." + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + introspectedColumn.getParameterName() + " 缺少sql_value属性";
                                    throw new Exception(extraErrorInfo);
                                }
                                String oldSql = column.getParameterSql();
                                int pos = oldSql.indexOf(introspectedColumn.getParameterSqlValue());
                                if (pos < 0) {
                                    extraErrorInfo = "sql.param." + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + introspectedColumn.getParameterName() + " 未找到参数:" + introspectedColumn.getParameterSqlValue();
                                    throw new Exception(extraErrorInfo);
                                }
                                Integer intValue = null;
                                try {
                                    intValue = Integer.valueOf(introspectedColumn.getParameterSqlValue());
                                    if (!introspectedColumn.getParameterSqlValue().equals(intValue+"")) {
                                        intValue = null;
                                    }
                                }catch(Exception ex) {
                                }
                                if (introspectedColumn.getParameterSqlValue().equals(intValue+"")) {
                                    if (pos == 0) {
                                        extraErrorInfo = "sql.param." + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + introspectedColumn.getParameterName() + " 数字不可以在语句起始处:" + introspectedColumn.getParameterSqlValue();
                                        throw new Exception(extraErrorInfo);
                                    }
                                    while(pos > 0) {
                                        String preChar = oldSql.substring(pos - 1, pos).toLowerCase();
                                        if(preChar.getBytes("UTF-8").length > 1) {
                                            extraErrorInfo = "sql.param." + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + introspectedColumn.getParameterName() + " 数字不可以出现在中文标识符后:" + introspectedColumn.getParameterSqlValue();
                                            throw new Exception(extraErrorInfo);
                                        }
                                        String validChars = "0123456789abcdefghijklmnopqrstuvwxyz_#";
                                        if (validChars.indexOf(preChar) >= 0) {
                                            extraErrorInfo = "sql.param." + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + introspectedColumn.getParameterName() + " 数字不可以出现在其他数字或标识符后:" + introspectedColumn.getParameterSqlValue();
                                            throw new Exception(extraErrorInfo);
                                        }
                                        pos += introspectedColumn.getParameterSqlValue().length();
                                        if (pos < oldSql.length() - 1) {
                                            String nextChar = oldSql.substring(pos, pos+1).toLowerCase();
                                            if(nextChar.getBytes("UTF-8").length > 1) {
                                                extraErrorInfo = "sql.param." + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + introspectedColumn.getParameterName() + " 数字不可以出现在中文标识符前:" + introspectedColumn.getParameterSqlValue();
                                                throw new Exception(extraErrorInfo);
                                            }
                                            if (validChars.indexOf(nextChar) >= 0) {
                                                extraErrorInfo = "sql.param." + column.getParameterCatalog() + "." + column.getParameterCatalogType() + "." + introspectedColumn.getParameterName() + " 数字不可以出现在其他数字或标识符前:" + introspectedColumn.getParameterSqlValue();
                                                throw new Exception(extraErrorInfo);
                                            }
                                        }
                                        pos = oldSql.indexOf(introspectedColumn.getParameterSqlValue(), pos);
                                    }
                                }
                                if (oldSql.indexOf("'" + introspectedColumn.getParameterSqlValue() + "'") >= 0) {
                                    extraErrorInfo = "sql.param." + introspectedColumn.getParameterName() + " 外加单引号后引起歧义";
                                    throw new Exception(extraErrorInfo);
                                }
                            }

                            int sqlIndex = 0;
                            for(String sqlStr:new String[]{column.getParameterSql(), column.getParameterSimpleSql(), column.getParameterSimpleWithStarSql()}) {
                                sqlIndex++;
                                List<String> lines = splitLines(sqlStr);
                                StringBuilder sbSql = new StringBuilder();
                                for(String line: lines) {
                                    line = line.replaceAll("\r", "").replace("\n", "");
                                    IntrospectedColumn introspectedColumnFound = null;
                                    int pos = -1;
                                    for(IntrospectedColumn introspectedColumn: extraColumns) {
                                        if (Integer.valueOf(1).equals(introspectedColumn.getParameterIgnoreSqlValue())) {
                                            continue;
                                        }
                                        pos = line.indexOf(introspectedColumn.getParameterSqlValue());
                                        if (pos >= 0) {
                                            introspectedColumnFound = introspectedColumn;
                                            break;
                                        }
                                    }
                                    if (pos < 0) {
                                        sbSql.append(line);
                                        sbSql.append(System.lineSeparator());
                                        continue;
                                    }
                                    for(IntrospectedColumn introspectedColumn: extraColumns) {
                                        if (Integer.valueOf(1).equals(introspectedColumn.getParameterIgnoreSqlValue())) {
                                            continue;
                                        }
                                        pos = line.indexOf(introspectedColumn.getParameterSqlValue());
                                        if (pos < 0) {
                                            continue;
                                        }
                                        while(pos >= 0) {
                                            String newLineStr = line.substring(0, pos) +
                                                    "#{" +
                                                    (sqlIndex == 1 ? "simple." : "") +
                                                    JavaBeansUtil.getCamelCaseString(introspectedColumn.getParameterName(), false) +
                                                    "}" +
                                                    line.substring(pos +introspectedColumn.getParameterSqlValue().length());
                                            line = newLineStr;
                                            pos = line.indexOf(introspectedColumn.getParameterSqlValue(), pos + 20);
                                        }
                                    }

                                    if (Integer.valueOf(1).equals(introspectedColumnFound.getParameterWithoutTest())) {
                                        sbSql.append(line);
                                        sbSql.append(System.lineSeparator());
                                    } else {
                                        if (line.indexOf(">") >= 0 ||
                                                line.indexOf("<") >= 0 ||
                                                line.indexOf("&") >= 0 ||
                                                line.indexOf("'") >= 0 ||
                                                line.indexOf("\"") >= 0) {
                                            line = "<if test=\"" +
                                                    (sqlIndex == 1 ? "simple." : "") +
                                                    JavaBeansUtil.getCamelCaseString(introspectedColumnFound.getParameterName(), false) +
                                                    " != null\">" +
                                                    System.lineSeparator() +
                                                    "<![CDATA[ " +
                                                    line +
                                                    " ]]>" +
                                                    System.lineSeparator() +
                                                    "</if>";
                                        } else {
                                            line = "<if test=\"" +
                                                    (sqlIndex == 1 ? "simple." : "") +
                                                    JavaBeansUtil.getCamelCaseString(introspectedColumnFound.getParameterName(), false) +
                                                    " != null\">" +
                                                    System.lineSeparator() +
                                                    line +
                                                    System.lineSeparator() +
                                                    "</if>";
                                        }
                                        sbSql.append("]]>");
                                        sbSql.append(System.lineSeparator());
                                        sbSql.append(line);
                                        sbSql.append(System.lineSeparator());
                                        sbSql.append("<![CDATA[");
                                        sbSql.append(System.lineSeparator());
                                    }

                                }
                                if (sqlIndex == 1) {
                                    column.setParameterSql(sbSql.toString());
                                } else if (sqlIndex == 2){
                                    column.setParameterSimpleSql(sbSql.toString());
                                } else if (sqlIndex == 3) {
                                    column.setParameterSimpleWithStarSql(sbSql.toString());
                                }

                            }
                            column.setParameterSql(fixCData(column.getParameterSql()));
                            column.setParameterSimpleSql(fixCData(column.getParameterSimpleSql()));
                            column.setParameterSimpleWithStarSql(fixCData(column.getParameterSimpleWithStarSql()));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("==== 当前语句 ======================");
                        System.out.println(sql);
                        System.out.println("==== 原始语句 ======================");
                        System.out.println(oriParameterSql);
                        System.out.println("==== 附加错误 ======================");
                        System.out.println(extraErrorInfo);
                        System.out.println("==========================");
                        throw new Exception("!!! === sql错误! "+tableName+","+column.getParameterCatalog()+"."+column.getParameterCatalogType()+", "+column.getParameterName()+","+extraErrorInfo+",ex:"+ex.getMessage());
                    }

                }
            }
        }catch (Exception ex ) {
            ex.printStackTrace();
            throw ex;
        }finally {
        }

        for(String workspaceName: sdpWorkspaceMap.keySet()) {
            if (!tableMap.containsKey(workspaceName)) {
//                pringNotFoundInfo(fountFoundInfoSet, "not found table workspace:"+workspaceName);
                continue;
            }

            List<String> tableList = tableMapForList.get(workspaceName);
            if (CollectionUtils.isEmpty(tableList)) {
                pringNotFoundInfo(fountFoundInfoSet, "not found tableList for workspace:"+workspaceName);
                continue;
            }

            for(String tableName : tableList) {
                String[] names = tableName.split("\\.");
                String schema = names[1];
                tableName = names[2];
                IntrospectedTable introspectedTable = introspectedTableMap.get(workspaceName + "." + schema + "." + tableName);
                if (introspectedTable == null) {
                    System.out.println("==== 未找到表："+tableName);
                    continue;
                }
                try {
                    if (parameterColumnsMap.containsKey(workspaceName + "." + introspectedTable.getTableConfiguration().getCatalog() + "." + introspectedTable.getTableConfiguration().getTableName())) {
                        introspectedTable.setParameterColumns(parameterColumnsMap.get(workspaceName + "." + introspectedTable.getTableConfiguration().getCatalog() + "." + introspectedTable.getTableConfiguration().getTableName()));
                        introspectedTable.setParameterColumnKeys(parameterColumnKeysMap.get(workspaceName + "." + introspectedTable.getTableConfiguration().getCatalog() + "." + introspectedTable.getTableConfiguration().getTableName()));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw ex;
                }
            }
        }

        for(String workspaceName: connectionMap.keySet()) {
            Connection connection = connectionMap.get(workspaceName);
            connection.close();
        }

        Map<String, List<SdpTemplateWithBLOBs>> sdpTemplateMapByConfigId = new HashMap<>();
        {
            SdpTemplateExample example = new SdpTemplateExample();
            if (jsonObject.containsKey("workspace_name")) {
                example.createCriteria().andWorkspaceNameEqualTo(jsonObject.getString("workspace_name"));
            }
            example.setOrderByClause("workspace_name, project_name, name");
            List<SdpTemplateWithBLOBs> sdpTemplates = sdpTemplateMapper.selectByExampleWithBLOBs(example);
            for(SdpTemplateWithBLOBs item: sdpTemplates) {
                String name = item.getWorkspaceName() +";"+item.getProjectName();
                if (!sdpTemplateMapByConfigId.containsKey(name)) {
                    sdpTemplateMapByConfigId.put(name, new ArrayList<>());
                }
                sdpTemplateMapByConfigId.get(name).add(item);
            }
        }

        for(String projectNamePlus: sdpProjectMap.keySet()) {

            String workspaceName = projectNamePlus.split(";")[0];
            String projectName = projectNamePlus.split(";")[1];
            SdpProjectQueryResponse project = sdpProjectMap.get(projectNamePlus);
            SdpWorkspaceQueryResponse workspace = sdpWorkspaceMap.get(workspaceName);

            List<SdpTemplateWithBLOBs> sdpTemplate1 = sdpTemplateMapByConfigId.get(workspaceName+";"+projectName);
            if (CollectionUtils.isEmpty(sdpTemplate1)) {
                pringNotFoundInfo(fountFoundInfoSet, "not found template for project:"+workspaceName+";"+projectName);
                continue;
            }

            String schema = workspace.getDbDatabase();
            if ("org.h2.Driver".equals(workspace.getDbClassname())) {
                schema = "public";
            }
            if (StringUtils.isEmpty(project.getTables())) {
                pringNotFoundInfo(fountFoundInfoSet, "no tables for project:"+workspaceName+";"+projectName);
                continue;
            }
            String [] tables = project.getTables().split(",");
            for(String tableName: tables) {
                tableName = tableName.trim();
                String aliasName = tableName;
                String[] tableNames = tableName.split(" as ");
                if (tableNames.length > 1) {
                    tableName = tableNames[0].trim();
                    aliasName = tableNames[1].trim();
                }

                IntrospectedTable introspectedTable = introspectedTableMap.get(workspaceName + "." + schema + "." + tableName);
                if (introspectedTable == null) {
                    pringNotFoundInfo(fountFoundInfoSet, "no introspectedTable for table:"+workspaceName+"."+schema+"."+tableName);
                    continue;
                }

                for(SdpTemplateWithBLOBs item: sdpTemplate1) {
                    SdpTemplateQueryResponse dynTemplate = new SdpTemplateQueryResponse();

                    BeanUtils.copyProperties(item, dynTemplate);

                    dynTemplate.setDynWorkspace(workspace);
                    dynTemplate.setDynProject(project);
                    dynTemplate.setAliasTableName(aliasName);

                    if (!StringUtils.isEmpty(item.getPackageName())) {
                        String content = getCompilationUnits(introspectedTable, item.getPackageName(), dynTemplate, "", null, _processInstance, _processBodyToken, _processToken);
                        content = content.replaceAll(System.lineSeparator(), "");
                        dynTemplate.setPackageName(content);
                    } else {
                        dynTemplate.setPackageName("");
                    }

                    dynTemplate.setExtraInfoMap(new HashMap<>());
                    if (!StringUtils.isEmpty(item.getExtraInfo())) {
                        JSONObject extraInfo;
                        try {
                            extraInfo = JSON.parseObject(item.getExtraInfo());
                        }catch(Exception ex) {
                            System.out.println(item.getExtraInfo());
                            ex.printStackTrace();
                            throw new Exception("template.extra_info非法："+item.getProjectName()+","+item.getName());
                        }
                        for(String jsonKey: extraInfo.keySet()) {
                            dynTemplate.getExtraInfoMap().put(jsonKey, extraInfo.get(jsonKey));
                        }
                    }

                    if (!StringUtils.isEmpty(item.getProject())) {
                        String content = getCompilationUnits(introspectedTable, item.getProject(), dynTemplate, "", null, _processInstance, _processBodyToken, _processToken);
                        content = content.replaceAll(System.lineSeparator(), "");
                        dynTemplate.setProject(content);
                    } else {
                        item.setProject("");
                    }

                    if (dynTemplate.getExtraInfoMap() != null) {
                        if ("1".equals(dynTemplate.getExtraInfoMap().get("has_blobs")+"")) {
                            if (!introspectedTable.hasBLOBColumns()) {
                                continue;
                            }
                        }
                        if ("0".equals(dynTemplate.getExtraInfoMap().get("has_blobs")+"")) {
                            if (introspectedTable.hasBLOBColumns()) {
                                continue;
                            }
                        }
                    }

                    File packageFile = new File(dynTemplate.getDynProject().getRootPath());
                    if (!packageFile.exists()) {
                        throw new Exception("目录不存在，请先创建目录："+dynTemplate.getDynProject().getRootPath());
                    }

                    String packageFileName = dynTemplate.getDynProject().getRootPath()+"/"+ dynTemplate.getProject()+"/";
                    packageFile = new File(packageFileName);
                    if (!packageFile.exists()) {
                        try {
                            packageFile.mkdirs();
                        }catch (Exception ex) {
                            throw new Exception("目录不存在，且无法创建："+packageFileName);
                        }
                    }
                    if (!packageFile.isDirectory()) {
                        throw new Exception("目标不是目录："+packageFileName);
                    }

                    if (dynTemplate.getName().indexOf("{sql_name") >= 0) {
                        for(String key: introspectedTable.getParameterColumnKeys()) {
                            if (key.startsWith("api.request_for_sql.")) {
                                String sqlMethodName = key.replace("api.request_for_sql.", "");

                                if (!CollectionUtils.isEmpty(dynTemplate.getExtraInfoMap())) {
                                    List<IntrospectedColumn> columns = introspectedTable.getParameterColumns().get("sql."+sqlMethodName);
                                    if (CollectionUtils.isEmpty(columns)) {
                                        System.err.println("miss sql."+key);
                                        continue;
                                    }
                                    IntrospectedColumn column = columns.get(0);
                                    if ("sql".equals(column.getParameterCatalog())) {
                                        if ("0".equals(dynTemplate.getExtraInfoMap().get("is_interface")+"")) {
                                            if (Integer.valueOf(1).equals(column.getSqlIsInterface())) {
                                                continue;
                                            }
                                        }
                                    }
                                }
                                String fileName = getCompilationUnits(introspectedTable, dynTemplate.getName(), dynTemplate, "", sqlMethodName, _processInstance, _processBodyToken, _processToken);
                                fileName = fileName.replaceAll(System.lineSeparator(), "");

                                System.out.println("process:"+ dynTemplate.getDynProject().getRootPath()+";"+ dynTemplate.getProject()+";"+ dynTemplate.getPackageName()+";"+fileName);

                                String fullFileName = dynTemplate.getDynProject().getRootPath()+"/"+ dynTemplate.getProject()+"/"+ dynTemplate.getPackageName().replaceAll("\\.", "/")+"/"+fileName+"."+ dynTemplate.getFileType();
                                File file = new File(fullFileName);

                                if (Integer.valueOf(1).equals(dynTemplate.getNoOverwrite())) {
                                    if (file.exists()) {
                                        continue;
                                    }
                                }

                                FileUtil.fetchFileOldProperty(file);

                                String content = getCompilationUnits(introspectedTable, dynTemplate.getFileTemplate(), dynTemplate, fileName, sqlMethodName, _processInstance, _processBodyToken, _processToken);

                                writeFile(file, content);
                            }
                        }
                    } else {
                        String fileName = getCompilationUnits(introspectedTable, dynTemplate.getName(), dynTemplate, "", null, _processInstance, _processBodyToken, _processToken);
                        fileName = fileName.replaceAll(System.lineSeparator(), "");
                        System.out.println("process:"+ dynTemplate.getDynProject().getRootPath()+";"+ dynTemplate.getProject()+";"+ dynTemplate.getPackageName()+";"+fileName);

                        String fullFileName = dynTemplate.getDynProject().getRootPath()+"/"+ dynTemplate.getProject()+"/"+ dynTemplate.getPackageName().replaceAll("\\.", "/")+"/"+fileName+"."+ dynTemplate.getFileType();
                        File file = new File(fullFileName);

                        if (Integer.valueOf(1).equals(dynTemplate.getNoOverwrite())) {
                            if (file.exists()) {
                                continue;
                            }
                        }

                        FileUtil.fetchFileOldProperty(file);

                        String content = getCompilationUnits(introspectedTable, dynTemplate.getFileTemplate(), dynTemplate, fileName, null, _processInstance, _processBodyToken, _processToken);

                        writeFile(file, content);
                    }
                }
            }
        }

        return 1;
    }

    private void writeFile(File file, String content) throws Exception {
        FileOutputStream fos = null;
        BufferedWriter writer = null;
        try {
            file.getParentFile().mkdirs();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        try {
            fos = new FileOutputStream(file);

            writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

            writer.write(content);
            writer.close();
            writer = null;
            fos.close();
            fos = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }finally {
            if (writer != null) {
                writer.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    public boolean isToken(String tokenIn, String v) {
        String token = tokenIn.trim();
        String[] tokens;
        if (!token.startsWith("{")) {
            return false;
        }
        token = SplitUtil.split(token, "\\{")[1];
        token = SplitUtil.split(token, "&")[0];
        token = SplitUtil.split(token, "}")[0];
        return token.equals(v);
    }

    public boolean isBlockToken(String token, String v) {
        token = token.trim();
        if (!token.startsWith("{*")) {
            return false;
        }
        token = token.substring(2);
        token = SplitUtil.split(token, "&")[0];
        token = SplitUtil.split(token, "}")[0];
        return token.equals(v);
    }

    public String getName(String token, String name, Map<String, String> properties, IntrospectedColumn column) throws Exception {
        if (properties == null) {
            properties = new HashMap<>();
        }
        if (StringUtils.isEmpty(name)) {
            throw new Exception("name is null");
        }
        String v;
        if ("1".equals(properties.get("is_ajax"))) {
            v = name;
        } else if ("1".equals(properties.get("is_first_lower"))) {
            v = JavaBeansUtil.getCamelCaseString(name, false);
        } else if ("1".equals(properties.get("is_java_name"))) {
            v = JavaBeansUtil.getCamelCaseString(name, false);
        } else {
            v = JavaBeansUtil.getCamelCaseString(name, true);
        }
        if (properties.containsKey("is_rename")) {
            if ("member_code".equals(column.getActualColumnName())) {
                System.out.println("debug");
            }
            if (!StringUtils.isEmpty(column.getParameterExtraInfo())) {
                try {
                    JSONObject extraInfo = JSON.parseObject(column.getParameterExtraInfo());
                    String name2 = extraInfo.getString(properties.get("is_rename"));
                    if (StringUtils.hasText(name2)) {
                        v = name2;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (properties.containsKey("is_replace")) {
            if (!StringUtils.isEmpty(column.getParameterExtraInfo())) {
                try {
                    JSONObject extraInfo = JSON.parseObject(column.getParameterExtraInfo());
                    String name2 = extraInfo.getString(properties.get("is_replace"));
                    if (StringUtils.hasText(name2)) {
                        v = name2;
                    } else {
                        v = "";
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                v = "";
            }
        }
        if ("1".equals(properties.get("is_lower"))) {
            v = v.toLowerCase();
        } else if ("1".equals(properties.get("is_upper"))) {
            v = v.toUpperCase();
        }

        if ("1".equals(properties.get("is_java_name"))) {
            if(KeywordUtil.isKeyword(v, "java")) {
                v = "_" + v;
            }
        }
        if ("1".equals(properties.get("with_delimited")) && column != null
                && column.getContext() != null
                && SqlReservedWords.containsWord(v)) {
            try {
                v = escapeStringForJava(column
                        .getContext().getBeginningDelimiter()) + v + escapeStringForJava(column
                        .getContext().getEndingDelimiter());
            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
        return v;
    }

    public DynProcessTokenResult processTokenProperties(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<IntrospectedColumn> columns, Integer columnIndex, String token, Map<String, String> properties) throws Exception {
        DynProcessTokenResult result = new DynProcessTokenResult();
        IntrospectedColumn column = null;
        if (columns != null && columnIndex > 0 && columnIndex <= columns.size()) {
            column = columns.get(columnIndex - 1);
        }

        String v = null;
        do {
            if (StringUtility.stringHasValue(properties.get("has_blobs"))) {
                if ("1".equals(properties.get("has_blobs"))) {
                    if (!introspectedTable.hasBLOBColumns()) {
                        result.setProcessed(true);
                        continue;
                    }
                } else if ("0".equals(properties.get("has_blobs"))) {
                    if (introspectedTable.hasBLOBColumns()) {
                        result.setProcessed(true);
                        continue;
                    }
                }
            }
            if (columnIndex != null && properties.containsKey("column_is_last")) {
                if (column == null) {
                    v = "<miss column>:"+token;
                    continue;
                }
                if ("1".equals(properties.get("column_is_last"))) {
                    if (columnIndex != columns.size()) {
                        result.setProcessed(true);
                        continue;
                    }
                } else if ("0".equals(properties.get("column_is_last"))) {
                    if (columnIndex == columns.size()) {
                        result.setProcessed(true);
                        continue;
                    }
                }

            }
            if (properties.containsKey("column_is_first")) {
                if (column == null) {
                    v = "<miss column>:"+token;
                    continue;
                }
                if ("1".equals(properties.get("column_is_first"))) {
                    if (columnIndex != 1) {
                        result.setProcessed(true);
                        continue;
                    }
                } else if ("0".equals(properties.get("column_is_first"))) {
                    if (columnIndex == 1) {
                        result.setProcessed(true);
                        continue;
                    }
                }
            }

        }while(false);
        result.setResult(v);
        return result;
    }

    private DynProcessTokenResult processToken(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<IntrospectedColumn> columns, Integer columnIndex, String token, Map<String, String> properties) throws Exception {
        DynProcessTokenResult result = new DynProcessTokenResult();
        String v = null;

        IntrospectedColumn column = null;
        if (columns != null && columnIndex > 0 && columnIndex <= columns.size()) {
            column = columns.get(columnIndex - 1);
        }

        Map<String, String> propertiesIsAjax = new HashMap<>();
        propertiesIsAjax.put("is_ajax", "1");

        do {
            DynProcessTokenResult result1 = processTokenProperties(introspectedTable, dynTemplate, fileName, sqlMethodName, columns, columnIndex, token, properties);
            if (result1.getProcessed()) {
                result.setProcessed(true);
                result.setResult(result1.getResult());
                continue;
            }

            if (isToken(token, "alias_table_name")) {
                result.setProcessed(true);
                v = getName(token, dynTemplate.getAliasTableName(), properties, column);
            } else if (isToken(token, "table_name")) {
                result.setProcessed(true);
                v = getName(token, introspectedTable.getTableConfiguration().getTableName(), properties, column);
            } else if (isToken(token, "sql_name")) {
                result.setProcessed(true);
                if (StringUtils.isEmpty(sqlMethodName)) {
                    v = token + "<miss sql name>"+token;
                    continue;
                }
                v = getName(token, sqlMethodName, properties, column);
            } else if (isToken(token, "sql_param_name")) {
                result.setProcessed(true);
                if (StringUtils.isEmpty(sqlMethodName)) {
                    v = token + "<miss sql name>"+token;
                    continue;
                }
                List<IntrospectedColumn> oriColumns = introspectedTable.getParameterColumns().get("sql.param."+sqlMethodName);
                StringBuilder sbName = new StringBuilder();
                for(IntrospectedColumn paramItem: oriColumns) {
                    if (Integer.valueOf(1).equals(paramItem.getParameterNullable())) {
                        continue;
                    }
                    if (sbName.length() > 0) {
                        sbName.append("_");
                    }
                    sbName.append(getName(null, paramItem.getActualColumnName(), propertiesIsAjax, null));
                }
                v = getName(token, sbName.toString(), properties, column);
            } else if (isToken(token, "column_method_name")) {
                result.setProcessed(true);
                if (column == null) {
                    v = token + "<miss column>"+token;
                    continue;
                }
                v = getName(token, column.getParameterCatalogType(), properties, column);
            } else if (isToken(token, "column_name")) {
                result.setProcessed(true);
                if (column == null) {
                    v = token + "<miss column>"+token;
                    continue;
                }
                v = getName(token, column.getActualColumnName(), properties, column);
            } else if (isToken(token, "package_name")) {
                result.setProcessed(true);
                v = getName(token, dynTemplate.getPackageName(), propertiesIsAjax, null);
            } else if (isToken(token, "file_name")) {
                result.setProcessed(true);
                v = getName(token, fileName, propertiesIsAjax, null);
            } else if (isToken(token, "file_date")) {
                result.setProcessed(true);
                String fullFileName = dynTemplate.getDynProject().getRootPath() + "/" + dynTemplate.getProject() + "/" + dynTemplate.getPackageName().replaceAll("\\.", "/") + "/" + fileName + "." + dynTemplate.getFileType();
                File file = new File(fullFileName);

                v = FileUtil.getOld(file.getAbsolutePath(), "* @Date:", null);
                result.setProcessed(true);
            } else if (isToken(token, "config")) {
                result.setProcessed(true);
                String name = properties.get("name");
                if ("workspace_db_host".equals(name)) {
                    v = dynTemplate.getDynWorkspace().getDbHost();
                } else if ("workspace_db_port".equals(name)) {
                    v = dynTemplate.getDynWorkspace().getDbPort()+"";
                } else if ("workspace_db_name".equals(name)) {
                    v = dynTemplate.getDynWorkspace().getDbDatabase();
                } else if ("workspace_db_username".equals(name)) {
                    v = dynTemplate.getDynWorkspace().getDbUsername();
                } else if ("workspace_db_password".equals(name)) {
                    v = dynTemplate.getDynWorkspace().getDbPassword();
                } else {
                    v = dynTemplate.getDynProject().getPropertyMap().get(name);
                }
                if (v == null) {
                    v = "<miss config>"+token+"/"+name;
                    continue;
                }
                if ("version".equals(name)) {
                    String fullFileName = dynTemplate.getDynProject().getRootPath() + "/" + dynTemplate.getProject() + "/" + dynTemplate.getPackageName().replaceAll("\\.", "/") + "/" + fileName + "." + dynTemplate.getFileType();
                    File file = new File(fullFileName);

                    v = FileUtil.getOld(file.getAbsolutePath(), "* @Version:", v);
                } else if ("author".equals(name)) {
                    String fullFileName = dynTemplate.getDynProject().getRootPath() + "/" + dynTemplate.getProject() + "/" + dynTemplate.getPackageName().replaceAll("\\.", "/") + "/" + fileName + "." + dynTemplate.getFileType();
                    File file = new File(fullFileName);

                    v = FileUtil.getOld(file.getAbsolutePath(), "* @Author:", v);
                }
            } else if (isToken(token, "serialVersionUID")) {
                result.setProcessed(true);
                v = Math.abs(dynTemplate.getPackageName().hashCode() / 100) + "" + Math.abs(fileName.hashCode());
            } else if (isToken(token, "table_comment")) {
                result.setProcessed(true);
                if (properties.get("is_first_line") == null || "1".equals(properties.get("is_first_line"))) {
                    v = introspectedTable.getFirstRemarks();
                    if (StringUtils.isEmpty(v)) {
                        v = getName(token, introspectedTable.getTableConfiguration().getTableName(), properties, column);
                    }
                } else {
                    v = introspectedTable.getRemarks();
                }
            } else if (isToken(token, "value")) {
                result.setProcessed(true);
                v = properties.get("value");
                if (v == null) {
                    v = "<miss value>"+token;
                    continue;
                }
            } else if (isToken(token, "column_java_type")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                if ("1".equals(properties.get("is_full_name"))) {
                    v = column.getFullyQualifiedJavaType().getFullyQualifiedName();
                    if ("Date".equals(v)) {
                        v = "java.util.Date";
                    } else if (v.startsWith("List<")) {
                        v = "java.util.List";
                    } else if (v.startsWith("Map<")) {
                        v = "java.util.Map";
                    } else if ("BigDecimal".equals(v)) {
                        v = "java.math.BigDecimal";
                    }
                    if (v.indexOf(".") < 0) {
                        v = null;
                        continue;
                    }
                } else {
                    v = column.getFullyQualifiedJavaType().getShortName();
                }
            } else if (isToken(token, "column_java_return_type")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                v = column.getParameterJavaReturnType();
            } else if (isToken(token, "column_java_imports")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                if (!StringUtility.stringHasValue(column.getParameterImports())) {
                    continue;
                }
                List<String> vsLines = splitLines(column.getParameterImports());
                v = "";
                for (String vLine : vsLines) {
                    vLine = vLine.replaceAll("\n", "").replaceAll("\r", "");
                    String[] vImports = vLine.split(";");
                    for (String vImport : vImports) {
                        vImport = vImport.trim();
                        if (!StringUtility.stringHasValue(vImport)) {
                            continue;
                        }
                        if (vImport.startsWith("import ")) {
                            vImport = vImport.replace("import ", "").trim();
                        }
                        if (!"".equals(v)) {
                            v += System.lineSeparator();
                        }
                        v += "import " + vImport + ";";
                    }
                }
            } else if (isToken(token, "column_sql")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                v = column.getParameterSql();
            } else if (isToken(token, "column_ori_sql")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                v = column.getParameterOriSql();
            } else if (isToken(token, "column_simple_sql")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                v = column.getParameterSimpleSql();
            } else if (isToken(token, "column_simple_sql_with_star")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                v = column.getParameterSimpleWithStarSql();
            } else if (isToken(token, "column_comment")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                if (properties.get("is_first_line") == null || "1".equals(properties.get("is_first_line"))) {
                    v = column.getFirstRemarks();
                } else {
                    v = column.getRemarks();
                }
            } else if (isToken(token, "column_param_for_repository")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                v = column.getUniqueKeyparameterForRepository();
            } else if (isToken(token, "column_name_for_repository")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                v = column.getUniqueKeynameForRepository();
            } else if (isToken(token, "column_parameter_type_for_mapper")) {
                result.setProcessed(true);
                if (column == null) {
                    v = "<miss column>"+token;
                    continue;
                }
                v = column.getUniqueKeytypeForMapper();
            }
        }while(false);
        result.setResult(v);
        if (!StringUtils.isEmpty(v)) {
            result.setProcessed(true);
        }
        return result;
    }

    public void processLine(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, ByteWithPos bodyBytes, ByteWithPos destBytes, List<IntrospectedColumn> columns, int columnIndex, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception {
        byte[] srcBytes = bodyBytes.bytes;
        int srcLength = bodyBytes.pos;

        ByteWithPos prefixBytes = new ByteWithPos(1024);
        ByteWithPos lineBytes = new ByteWithPos(1024);

        for(int pos = 0; pos < srcLength; pos++) {
            int srcPos = pos;
            while ( pos < srcLength && srcBytes[pos] != '{') {
                ByteWithPos.appendDestBytes(prefixBytes, srcBytes, pos, pos+1);
                pos++;
            }
            if (pos == srcLength) {
                if (prefixBytes.pos > 0) {
                    ByteWithPos.appendDestBytes(lineBytes, prefixBytes.bytes, 0, prefixBytes.pos);
                    prefixBytes = new ByteWithPos(1024);
                }
                break;
            }

            srcPos = pos++;
            ByteWithPos tokenBytes = new ByteWithPos(1024);
            while(pos < bodyBytes.pos) {
                if (srcBytes[pos] == '{') {
                    break;
                }
                if (srcBytes[pos] == '}') {
                    ByteWithPos.appendDestBytes(tokenBytes, srcBytes, srcPos, pos + 1);
                    break;
                }
                pos++;
            }
            if (tokenBytes.pos == 0) {
                if (prefixBytes.pos > 0) {
                    ByteWithPos.appendDestBytes(lineBytes, prefixBytes.bytes, 0, prefixBytes.pos);
                    prefixBytes = new ByteWithPos(1024);
                }
                ByteWithPos.appendDestBytes(lineBytes, srcBytes, srcPos, pos);
                pos--;
                continue;
            }

            String token = new String(tokenBytes.bytes, 0, tokenBytes.pos, "UTF-8");
            Map<String, String> properties = processStarProperties(token);

            String v = null;
            do {
                if (token.startsWith("{*")) {
                    lineBytes = new ByteWithPos(1024);
                    ByteWithPos.appendDestBytes(lineBytes, " ");
                    prefixBytes = new ByteWithPos(1024);
                    pos = srcLength;
                    break;
                }
                DynProcessTokenResult result = (DynProcessTokenResult) _processToken.invoke(_processInstance, introspectedTable, dynTemplate, fileName, sqlMethodName, columns, columnIndex, token, properties);
                if (result.getProcessed()) {
                    v = result.getResult();
                    break;
                }

                result = processToken(introspectedTable, dynTemplate, fileName, sqlMethodName, columns, columnIndex, token, properties);
                if (result.getProcessed()) {
                    v = result.getResult();
                    break;
                }
                v = new String(srcBytes, srcPos, pos - srcPos + 1, "UTF-8");
            }while(false);

            if (v != null) {
                if (prefixBytes.pos > 0) {
                    ByteWithPos.appendDestBytes(lineBytes, prefixBytes.bytes, 0, prefixBytes.pos);
                    prefixBytes = new ByteWithPos(1024);
                }
                ByteWithPos.appendDestBytes(lineBytes, v);
            }
        }
        if (prefixBytes.pos > 0) {
            ByteWithPos.appendDestBytes(lineBytes, prefixBytes.bytes, 0, prefixBytes.pos);
            prefixBytes = new ByteWithPos(1024);
        }
        if (lineBytes.pos == 0) {
            ByteWithPos.appendLineSeperator(destBytes);
        } else {
            String v = new String(lineBytes.bytes, 0, lineBytes.pos, "UTF-8");
            if (!"".equals(v.trim())) {
                ByteWithPos.appendDestBytes(destBytes, lineBytes.bytes, 0, lineBytes.pos);
                ByteWithPos.appendDestBytes(destBytes, System.lineSeparator());
            }
        }
    }

    private Map<String, String> processStarProperties(String vTrim) {
        Map<String, String> map = new HashMap<>();
        vTrim = vTrim.substring(0, vTrim.length() - 1);
        String[] properties = vTrim.split("&");
        if (properties.length > 1) {
            for(int i = 1; i < properties.length; i++) {
                int pos = properties[i].indexOf("=");
                if (pos > 0) {
                    if (pos < properties[i].length() - 1) {
                        map.put(properties[i].substring(0, pos).trim(), properties[i].substring(pos+1).trim());
                    } else {
                        map.put(properties[i].substring(0, pos).trim(), "");
                    }
                } else {
                    map.put(properties[i].trim(), "");
                }
            }
        }
        return map;
    }

    public List<IntrospectedColumn> fixIntrospectedColumns(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, List<IntrospectedColumn> introspectedColumns, Map<String, String> properties, Map<String, IntrospectedColumn> sqlParamNameMap) {
//        if (CollectionUtils.isEmpty(introspectedColumns)) {
//            return introspectedColumns;
//        }


        if (properties.get("has_columns_1") != null) {
            Map<String, IntrospectedColumn> doneSet = new HashMap<>();

            List<IntrospectedColumn> introspectedColumns1 = new ArrayList<>();

            for(IntrospectedColumn column: introspectedColumns) {
                doneSet.put(column.getActualColumnName(), column);
            }

            String[] hasColumnNames = properties.get("has_columns_1").split(",");
            for(String name: hasColumnNames) {
                name = name.trim();
                if (StringUtils.isEmpty(name)) {
                    continue;
                }
                if (doneSet.containsKey(name)) {
                    introspectedColumns1.add(doneSet.get(name));
                    return introspectedColumns1;
                }
            }
            return introspectedColumns1;
        }
        if (properties.get("has_columns_0") != null) {
            Set<String> doneSet = new HashSet<>();

            List<IntrospectedColumn> introspectedColumns1 = new ArrayList<>();

            for(IntrospectedColumn column: introspectedColumns) {
                doneSet.add(column.getActualColumnName());
            }

            String[] hasColumnNames = properties.get("has_columns_0").split(",");
            for(String name: hasColumnNames) {
                name = name.trim();
                if (StringUtils.isEmpty(name)) {
                    continue;
                }
                if (doneSet.contains(name)) {
                    return introspectedColumns1;
                }
            }
            introspectedColumns1.add(introspectedColumns.get(0));
            return introspectedColumns1;
        }

        Set<String> discardSet = new HashSet<>();
        if (properties.containsKey("discard")) {
            for(String name: properties.get("discard").split(",")) {
                discardSet.add(name.trim());
            }
        }
        Set<String> hasColumnsSet = new HashSet<>();
        if (properties.containsKey("has_columns")) {
            for(String name: properties.get("has_columns").split(",")) {
                hasColumnsSet.add(name.trim());
            }
        }

        Set<String> doneSet = new HashSet<>();

        List<IntrospectedColumn> introspectedColumns1 = new ArrayList<>();

        Set<String> hasIsSet = new HashSet<>();

        if ("1".equals(properties.get("is_dup"))) {
            hasIsSet.add("is_dup");
        }

        for(IntrospectedColumn column: introspectedColumns) {
            Set<String> localHasIsSet = new HashSet<>();

            if ("1".equals(properties.get("is_dup"))) {
                localHasIsSet.add("is_dup");
            }

            if (doneSet.contains(column.getActualColumnName())) {
                continue;
            }
            if (discardSet.contains(column.getActualColumnName())) {
                continue;
            }
            if (hasColumnsSet.size() > 0 && !hasColumnsSet.contains(column.getActualColumnName())) {
                continue;
            }
            doneSet.add(column.getActualColumnName());

            if (!StringUtils.isEmpty(properties.get("column_name"))) {
                if (!properties.get("column_name").equals(column.getActualColumnName())) {
                    continue;
                }
            }
            if ("1".equals(properties.get("is_simple"))) {
                if (Integer.valueOf(1).equals(column.getParameterSqlIsSimple())) {
                    hasIsSet.add("is_simple");
                    localHasIsSet.add("is_simple");
                } else {
                    continue;
                }
            }
            if ("0".equals(properties.get("is_simple"))) {
                if (Integer.valueOf(1).equals(column.getParameterSqlIsSimple())) {
                    continue;
                }
            }
            if ("1".equals(properties.get("is_interface"))) {
                if (Integer.valueOf(1).equals(column.getSqlIsInterface())) {
                    hasIsSet.add("is_interface");
                    localHasIsSet.add("is_interface");
                } else {
                    continue;
                }
            }
            if ("0".equals(properties.get("is_interface"))) {
                if (Integer.valueOf(1).equals(column.getSqlIsInterface())) {
                    continue;
                }
            }

            if ("1".equals(properties.get("sql_is_interface"))) {
                if (Integer.valueOf(1).equals(column.getSqlIsInterface())) {
                    hasIsSet.add("is_sql_interface");
                    localHasIsSet.add("is_sql_interface");
                } else {
                    continue;
                }
            }
            if ("0".equals(properties.get("sql_is_interface"))) {
                if (Integer.valueOf(1).equals(column.getSqlIsInterface())) {
                    continue;
                }
            }

            if ("1".equals(properties.get("is_string"))) {
                if ("String".equals(column.getFullyQualifiedJavaType().getShortName())
                        || column.isStringColumn()
                ) {
                    hasIsSet.add("is_string");
                    localHasIsSet.add("is_string");
                } else {
                    continue;
                }
            }
            if ("1".equals(properties.get("is_date"))) {
                if ("Date".equals(column.getFullyQualifiedJavaType().getShortName())
                        || column.isJDBCDateColumn()
                ) {
                    hasIsSet.add("is_date");
                    localHasIsSet.add("is_date");
                } else {
                    continue;
                }
            }
            if (!StringUtils.isEmpty(column.getParameterExtraInfo())) {
                try {
                    JSONObject extraInfo = JSON.parseObject(column.getParameterExtraInfo());
                    boolean needIgnore = false;
                    for(String p : extraInfo.keySet()) {
                        if ("1".equals(extraInfo.get(p) + "") && p.startsWith("is_")) {
                            hasIsSet.add(p);
                            localHasIsSet.add(p);
                        }
                        if (StringUtils.isEmpty(properties.get(p))) {
                            continue;
                        }
                        if (!properties.get(p).equals(extraInfo.get(p) + "")) {
                            needIgnore = true;
                            break;
                        }
                    }
                    if (needIgnore) {
                        continue;
                    }
                }catch(Exception ex) {
                    ex.printStackTrace();
                    System.out.println("invalid extra info:"+column.getParameterExtraInfo());
                }
            }
            if ("1".equals(properties.get("is_blob"))) {
                if (column.isBLOBColumn()) {
                    hasIsSet.add("is_blob");
                    localHasIsSet.add("is_blob");
                } else {
                    continue;
                }
            }
            if ("0".equals(properties.get("is_blob")) && column.isBLOBColumn()) {
                continue;
            }
            if ("1".equals(properties.get("is_primary_key"))) {
                if(introspectedTable.isPrimaryKey(column.getActualColumnName())) {
                    hasIsSet.add("is_primary_key");
                    localHasIsSet.add("is_primary_key");
                } else {
                    continue;
                }
            }
            if ("0".equals(properties.get("is_primary_key")) && introspectedTable.isPrimaryKey(column.getActualColumnName())) {
                continue;
            }
            if ("1".equals(properties.get("is_primary_key_multiple"))) {
                if (!CollectionUtils.isEmpty(introspectedTable.getPrimaryKeyColumns()) && introspectedTable.getPrimaryKeyColumns().size() > 1) {
                    hasIsSet.add("is_primary_key_multiple");
                    localHasIsSet.add("is_primary_key_multiple");
                } else {
                    continue;
                }
            }
            if ("0".equals(properties.get("is_primary_key_multiple"))) {
                if (!CollectionUtils.isEmpty(introspectedTable.getPrimaryKeyColumns()) && introspectedTable.getPrimaryKeyColumns().size() > 1) {
                    continue;
                }
            }
            if ("1".equals(properties.get("is_auto_increment"))) {
                if(column.isAutoIncrement()){
                    hasIsSet.add("is_auto_increment");
                    localHasIsSet.add("is_auto_increment");
                } else{
                    continue;
                }
            }
            if ("0".equals(properties.get("is_auto_increment")) && column.isAutoIncrement()) {
                continue;
            }
            if ("1".equals(properties.get("is_import_excel"))) {
                if (Integer.valueOf(1).equals(column.getParameterIsImportExcel())) {
                    hasIsSet.add("is_import_excel");
                    localHasIsSet.add("is_import_excel");
                } else {
                    continue;
                }
            }
            if ("0".equals(properties.get("is_import_excel"))) {
                if (Integer.valueOf(1).equals(column.getParameterIsImportExcel())) {
                    continue;
                }
            }

            if (Boolean.valueOf(true).equals(column.getDup())) {
                if ("1".equals(properties.get("is_dup"))) {
                } else {
                    continue;
                }
            }
            if ("0".equals(properties.get("param_is_nullable"))) {
                if (!sqlParamNameMap.containsKey(column.getActualColumnName())) {
                    continue;
                }
                IntrospectedColumn parameterColumn = sqlParamNameMap.get(column.getActualColumnName());
                if (Integer.valueOf(1).equals(parameterColumn.getParameterNullable())) {
                    continue;
                }
            }
            if ("1".equals(properties.get("param_is_nullable"))) {
                if (!sqlParamNameMap.containsKey(column.getActualColumnName())) {
                    continue;
                }
                IntrospectedColumn parameterColumn = sqlParamNameMap.get(column.getActualColumnName());
                if (!Integer.valueOf(1).equals(parameterColumn.getParameterNullable())) {
                    continue;
                }
                hasIsSet.add("is_nullable_param");
                localHasIsSet.add("is_nullable_param");
            }
            if ("1".equals(properties.get("param_is_like"))) {
                if (!sqlParamNameMap.containsKey(column.getActualColumnName())) {
                    continue;
                }
                IntrospectedColumn parameterColumn = sqlParamNameMap.get(column.getActualColumnName());
                if (!Integer.valueOf(1).equals(parameterColumn.getParameterIsLike())) {
                    continue;
                }
                hasIsSet.add("is_like_param");
                localHasIsSet.add("is_like_param");
            }

            boolean needIgnore = false;
            for(String p : properties.keySet()) {
                if (p.startsWith("is_")) {
                    if ("1".equals(properties.get(p))) {
                        if (!localHasIsSet.contains(p)) {
                            needIgnore = true;
                        }
                    } else {
                        if (localHasIsSet.contains(p)) {
                            needIgnore = true;
                        }
                    }
                }
            }
            if (needIgnore) {
                continue;
            }

            if ("1".equals(properties.get("has_column_java_imports"))) {
                if (!StringUtility.stringHasValue(column.getParameterImports())) {
                    continue;
                }
            }

            if ("1".equals(properties.get("has_java_field_type_with_package"))) {
                String v = column.getFullyQualifiedJavaType().getFullyQualifiedName();
                if ("Date".equals(v)) {
                    v = "java.util.Date";
                } else if (v.startsWith("List<")) {
                    v = "java.util.List";
                } else if (v.startsWith("Map<")) {
                    v = "java.util.Map";
                }

                if (v.indexOf(".") < 0) {
                    continue;
                }
                if (v.startsWith("java.lang.")) {
                    continue;
                }
            }

            introspectedColumns1.add(column);
        }

        for(String p : properties.keySet()) {
            if (p.startsWith("has_is_")) {
                String pp = p.substring("has_".length());
                if ("1".equals(properties.get(p))) {
                    if (!hasIsSet.contains(pp)) {
                        introspectedColumns1 = new ArrayList<>();
                        break;
                    }
                } else {
                    if (hasIsSet.contains(pp)) {
                        introspectedColumns1 = new ArrayList<>();
                        break;
                    }
                    if (CollectionUtils.isEmpty(introspectedColumns1)) {
                        IntrospectedColumn newColumn = new IntrospectedColumn();
                        introspectedColumns.add(newColumn);
                        newColumn.setActualColumnName(introspectedTable.getTableConfiguration().getTableName());
                        newColumn.setFullyQualifiedJavaType(FullyQualifiedJavaType.getStringInstance());
                        introspectedColumns1.add(newColumn);
                    }
                }
            }
        }

        if (introspectedColumns1.size() > 1) {
            if (properties.containsKey("column_is_first")) {
                List<IntrospectedColumn> introspectedColumns2 = new ArrayList<>();
                if ("1".equals(properties.get("column_is_first"))) {
                    introspectedColumns2.add(introspectedColumns1.get(0));
                } else {
                    for(int i = 1; i < introspectedColumns1.size(); i++) {
                        introspectedColumns2.add(introspectedColumns1.get(i));
                    }
                }
                introspectedColumns1 = introspectedColumns2;
            } else if(properties.containsKey("column_is_last")) {
                List<IntrospectedColumn> introspectedColumns2 = new ArrayList<>();
                if ("1".equals(properties.get("column_is_last"))) {
                    introspectedColumns2.add(introspectedColumns1.get(introspectedColumns1.size() - 1));
                } else {
                    for(int i = 0; i < introspectedColumns1.size() - 1; i++) {
                        introspectedColumns2.add(introspectedColumns1.get(i));
                    }
                }
                introspectedColumns1 = introspectedColumns2;
            }
        }
        return introspectedColumns1;
    }

    public DynProcessTokenResult processBodyTokenProperties(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<ByteWithPos> lineBytesList, List<String> lineStringList, List<String> lineTrimStringList, List<Integer> deepList, int deep, ByteWithPos destBytes, String vTrim, Map<String, String> properties, List<IntrospectedColumn> inColumns) throws Exception {
        DynProcessTokenResult result = new DynProcessTokenResult();
        if (properties.get("has_blobs") != null) {
            if ("1".equals(properties.get("has_blobs")) && !introspectedTable.hasBLOBColumns()) {
                result.setProcessed(true);
                return result;
            }
            if ("0".equals(properties.get("has_blobs")) && introspectedTable.hasBLOBColumns()) {
                result.setProcessed(true);
                return result;
            }
        }
        if (properties.get("has_primary_key") != null) {
            if ("1".equals(properties.get("has_primary_key")) && (introspectedTable.getPrimaryKeyColumns() == null || introspectedTable.getPrimaryKeyColumns().size() == 0)) {
                result.setProcessed(true);
                return result;
            }
            if ("0".equals(properties.get("has_primary_key")) && introspectedTable.getPrimaryKeyColumns() != null && introspectedTable.getPrimaryKeyColumns().size() > 0) {
                result.setProcessed(true);
                return result;
            }
        }
        if (properties.get("has_auto_increment") != null) {
            List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
            boolean hasAutoIncrement = false;
            for(IntrospectedColumn column: columns) {
                if (column.isAutoIncrement()) {
                    hasAutoIncrement = true;
                    break;
                }
            }
            if ("1".equals(properties.get("has_auto_increment")) && !hasAutoIncrement) {
                result.setProcessed(true);
                return result;
            }
            if ("0".equals(properties.get("has_auto_increment")) && hasAutoIncrement) {
                result.setProcessed(true);
                return result;
            }
        }
        return result;
    }

    public void processBodyTokenWithColumns(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<ByteWithPos> lineBytesList, List<String> lineStringList, List<String> lineTrimStringList, List<Integer> deepList, int deep, ByteWithPos destBytes, String vTrim, Map<String, String> properties, List<IntrospectedColumn> inColumns, List<IntrospectedColumn> introspectedColumns, List<IntrospectedColumn> extraColumns, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception {
        Map<String, IntrospectedColumn> sqlParamNameMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(extraColumns)) {
            for(IntrospectedColumn column: extraColumns) {
                sqlParamNameMap.put(column.getActualColumnName(), column);
            }
        }

        introspectedColumns = fixIntrospectedColumns(introspectedTable, dynTemplate, introspectedColumns, properties, sqlParamNameMap);
        if (CollectionUtils.isEmpty(introspectedColumns)) {
            return;
        }

        Set<String> doneSet = new HashSet<>();

        int columnIndex = 0;
        for(IntrospectedColumn column: introspectedColumns) {
            columnIndex++;
            ByteWithPos bodyBytes = new ByteWithPos(1024);

            boolean hasContent = false;
            for(int i = 0; i < deepList.size(); i++) {
                if (deepList.get(i) < deep) {
                    continue;
                }
                if (deepList.get(i) == deep) {
                    if (lineTrimStringList.get(i).startsWith("{*")) {
                        continue;
                    }
                    if (lineBytesList.get(i).pos == 0) {
                        ByteWithPos.appendLineSeperator(bodyBytes);
                        continue;
                    }
                    ByteWithPos tempBytes = new ByteWithPos(1024);
                    if ("".equals(lineTrimStringList.get(i))) {
                        if (hasContent) {
                            ByteWithPos.appendDestBytes(bodyBytes, lineStringList.get(i));
                            ByteWithPos.appendLineSeperator(bodyBytes);
                        }
                        continue;
                    }
                    processLine(introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytesList.get(i), tempBytes, introspectedColumns, columnIndex, _processInstance, _processBodyToken, _processToken);
                    if (tempBytes.pos > 0) {
                        hasContent = true;
                        ByteWithPos.appendDestBytes(bodyBytes, tempBytes.bytes, 0, tempBytes.pos);
                    } else {
                        hasContent = false;
                    }
                }
                if (deepList.get(i) > deep) {
                    String subVTrim = lineTrimStringList.get(i);
                    List<ByteWithPos> subLineBytesList = new ArrayList<>();
                    List<String> subLineStringList = new ArrayList<>();
                    List<String> subLineTrimStringList = new ArrayList<>();
                    List<Integer> subDeepList = new ArrayList<>();

                    int subDeep = deepList.get(i);
                    while(i < deepList.size() && deepList.get(i) > deep) {
                        subLineBytesList.add(lineBytesList.get(i));
                        subLineStringList.add(lineStringList.get(i));
                        subLineTrimStringList.add(lineTrimStringList.get(i));
                        subDeepList.add(deepList.get(i));
                        i++;
                    }
                    ByteWithPos subDestBytes = new ByteWithPos(1024);
                    List<IntrospectedColumn> subColumns = null;

                    if (isBlockToken(subVTrim,"unique_key_fields") && isBlockToken(vTrim, "unique_keys")) {
                        subColumns = introspectedTable.getUniqueColumns(column.getActualColumnName());
                    } else if (isBlockToken(subVTrim, "column")) {
                        subColumns = new ArrayList<>();
                        subColumns.add(column);
                    }

                    if (isBlockToken(vTrim, "sqls")) {
                        processBodyToken(introspectedTable, dynTemplate, fileName, column.getParameterCatalogType(), subLineBytesList, subLineStringList, subLineTrimStringList, subDeepList, subDeep, subDestBytes, subVTrim, subColumns, _processInstance, _processBodyToken, _processToken);
                    } else if (!StringUtils.isEmpty(sqlMethodName)){
                        processBodyToken(introspectedTable, dynTemplate, fileName, sqlMethodName, subLineBytesList, subLineStringList, subLineTrimStringList, subDeepList, subDeep, subDestBytes, subVTrim, subColumns, _processInstance, _processBodyToken, _processToken);
                    } else {
                        processBodyToken(introspectedTable, dynTemplate, fileName, null, subLineBytesList, subLineStringList, subLineTrimStringList, subDeepList, subDeep, subDestBytes, subVTrim, subColumns, _processInstance, _processBodyToken, _processToken);
                    }
                    ByteWithPos.appendDestBytes(bodyBytes, subDestBytes.bytes, 0, subDestBytes.pos);
                    i--;
                    continue;
                }
            }
            String v = new String(bodyBytes.bytes, 0, bodyBytes.pos, "UTF-8");
            if (doneSet.contains(v)) {
                continue;
            }
            doneSet.add(v);
            ByteWithPos.appendDestBytes(destBytes, bodyBytes.bytes, 0, bodyBytes.pos);
        }
    }

    public DynProcessTokenResult processBodyToken(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<ByteWithPos> lineBytesList, List<String> lineStringList, List<String> lineTrimStringList, List<Integer> deepList, int deep, ByteWithPos destBytes, String vTrim, List<IntrospectedColumn> inColumns, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception {
        Map<String, String> properties = processStarProperties(vTrim);

        DynProcessTokenResult result;
        try {
            result = (DynProcessTokenResult) _processBodyToken.invoke(_processInstance, introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytesList, lineStringList, lineTrimStringList, deepList, deep, destBytes, vTrim, properties, inColumns);
        }catch(Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        if (result.getProcessed()) {
            return result;
        }
        result = new DynProcessTokenResult();

        List<IntrospectedColumn> introspectedColumns = null;
        Map<String, IntrospectedColumn> sqlParamNameMap = new HashMap<>();

        Set<String> doneSet = new HashSet<>();
        List<IntrospectedColumn> extraColumns = new ArrayList<>();

        {
            DynProcessTokenResult result1 = processBodyTokenProperties(introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytesList, lineStringList, lineTrimStringList, deepList, deep, destBytes, vTrim, properties, inColumns);
            if (result1.getProcessed()) {
                result.setProcessed(true);
                result.setResult(result1.getResult());
                return result;
            }
        }

        if (inColumns != null) {
            result.setProcessed(true);
            introspectedColumns = inColumns;
        }else if (isBlockToken(vTrim,"table")) {
            introspectedColumns = new ArrayList<>();
            IntrospectedColumn newColumn = new IntrospectedColumn();
            introspectedColumns.add(newColumn);
            newColumn.setActualColumnName(introspectedTable.getTableConfiguration().getTableName());
            newColumn.setFullyQualifiedJavaType(FullyQualifiedJavaType.getStringInstance());
            sqlMethodName = null;
        }else if (isBlockToken(vTrim,"tables")) {
            introspectedColumns = new ArrayList<>();
            String tablesStr = dynTemplate.getDynProject().getTables();
            if (!StringUtils.isEmpty(tablesStr)) {
                String[] tables = tablesStr.split(",");
                for(String name: tables) {
                    name = name.trim();
                    if (StringUtils.isEmpty(name)) {
                        continue;
                    }
                    String[] names = name.split(" as ");
                    String alias = names[0].trim();
                    if (names.length > 1) {
                        name = names[0].trim();
                        alias = names[1].trim();
                    }
                    IntrospectedColumn newColumn = new IntrospectedColumn();
                    introspectedColumns.add(newColumn);
                    newColumn.setActualColumnName(alias);
                    newColumn.setFullyQualifiedJavaType(FullyQualifiedJavaType.getStringInstance());
                }
            }
            sqlMethodName = null;
        }else if (isBlockToken(vTrim,"sql")) {
            result.setProcessed(true);
            introspectedColumns = new ArrayList<>();
            if (!StringUtils.isEmpty(sqlMethodName)) {
                IntrospectedColumn newColumn = new IntrospectedColumn();
                introspectedColumns.add(newColumn);
                newColumn.setActualColumnName(sqlMethodName);
            }
        } else if (isBlockToken(vTrim,"columns")) {
            result.setProcessed(true);
            if ("1".equals(properties.get("only_primary_key"))) {
                List<IntrospectedColumn> columns = introspectedTable.getPrimaryKeyColumns();
                if (columns == null || columns.size() == 0) {
                    introspectedColumns = new ArrayList<>();
                } else {
                    introspectedColumns = columns;
                }
            } else {
                if (!StringUtils.isEmpty(sqlMethodName)) {
                    introspectedColumns = new ArrayList<>();
                    List<IntrospectedColumn> oriColumns = introspectedTable.getParameterColumns().get("api.request_for_sql."+sqlMethodName);
                    if (oriColumns == null) {
                        throw new Exception("逻辑错误："+fileName + ","+sqlMethodName);
                    }
                    Map<String, IntrospectedColumn> oriColumnMap = new HashMap<>();
                    for(IntrospectedColumn column1: oriColumns) {
                        oriColumnMap.put(column1.getActualColumnName(), column1);
                    }
                    if (oriColumns != null) {
                        introspectedColumns.addAll(oriColumns);
                    }
                    if ("1".equals(properties.get("is_only_param_columns"))) {
                        properties.remove("is_only_param_columns");
                        introspectedColumns = new ArrayList<>();
                        List<IntrospectedColumn> extraColumns2 = introspectedTable.getParameterColumns().get("sql.param."+sqlMethodName);
                        if (extraColumns2 != null) {
                            introspectedColumns.addAll(extraColumns2);
                            extraColumns.addAll(extraColumns2);
                        }
                    } else if ("1".equals(properties.get("is_only_response_columns"))) {
                        properties.remove("is_only_param_columns");
                        introspectedColumns = new ArrayList<>();
                        List<IntrospectedColumn> extraColumns2 = introspectedTable.getParameterColumns().get("sql.response." + sqlMethodName);
                        if (extraColumns2 != null) {
                            introspectedColumns.addAll(extraColumns2);
                            extraColumns.addAll(extraColumns2);
                        }
                    }else {
                        if ("1".equals(properties.get("include_param_columns"))) {
                            List<IntrospectedColumn> extraColumns2 = introspectedTable.getParameterColumns().get("sql.param." + sqlMethodName);
                            if (extraColumns2 != null) {
                                introspectedColumns.addAll(extraColumns2);
                                extraColumns.addAll(extraColumns2);
                            }
                        }

                    }
                    if ("1".equals(properties.get("include_response_columns"))
                            || "1".equals(properties.get("is_only_response_columns"))
                    ) {
                        List<IntrospectedColumn> extraColumns2 = introspectedTable.getParameterColumns().get("sql.response." + sqlMethodName);
                        if (extraColumns2 != null) {
                            for(IntrospectedColumn column1: extraColumns2) {
                                IntrospectedColumn oriColumn = oriColumnMap.get(column1.getActualColumnName());
                                if (oriColumn != null) {
                                    if (StringUtils.hasText(column1.getParameterExtraInfo())) {
                                        oriColumn.setParameterExtraInfo(column1.getParameterExtraInfo());
                                    }
                                    oriColumn.setTypeId(column1.getTypeId());
                                    oriColumn.setJdbcType(column1.getJdbcType());
                                    oriColumn.setJdbcTypeName(column1.getJdbcTypeName());
                                    oriColumn.setParameterJavaType(column1.getParameterJavaType());
                                    oriColumn.setFullyQualifiedJavaType(column1.getFullyQualifiedJavaType());
                                    oriColumn.setParameterJavaReturnType(column1.getParameterJavaReturnType());
                                    oriColumn.setTypeHandler(column1.getTypeHandler());
                                    oriColumn.setParameterCatalogType(column1.getParameterCatalogType());
                                    oriColumn.setParameterImports(column1.getParameterImports());
                                }
                            }
                            introspectedColumns.addAll(extraColumns2);
                            extraColumns.addAll(extraColumns2);
                        }
                    }
                    if ("1".equals(properties.get("include_param_columns"))
                            || "1".equals(properties.get("is_only_param_columns"))
                    ) {
                        List<IntrospectedColumn> extraColumns2 = introspectedTable.getParameterColumns().get("sql.param." + sqlMethodName);
                        if (extraColumns2 != null) {
                            for(IntrospectedColumn column1: extraColumns2) {
                                IntrospectedColumn oriColumn = oriColumnMap.get(column1.getActualColumnName());
                                if (oriColumn != null) {
                                    if (StringUtils.hasText(column1.getParameterExtraInfo())) {
                                        oriColumn.setParameterExtraInfo(column1.getParameterExtraInfo());
                                    }
                                    oriColumn.setTypeId(column1.getTypeId());
                                    oriColumn.setJdbcType(column1.getJdbcType());
                                    oriColumn.setJdbcTypeName(column1.getJdbcTypeName());
                                    oriColumn.setParameterJavaType(column1.getParameterJavaType());
                                    oriColumn.setFullyQualifiedJavaType(column1.getFullyQualifiedJavaType());
                                    oriColumn.setParameterJavaReturnType(column1.getParameterJavaReturnType());
                                    oriColumn.setTypeHandler(column1.getTypeHandler());
                                    oriColumn.setParameterCatalogType(column1.getParameterCatalogType());
                                    oriColumn.setParameterImports(column1.getParameterImports());
                                }
                            }
                            introspectedColumns.addAll(extraColumns2);
                            extraColumns.addAll(extraColumns2);
                        }
                    }
                    if (!CollectionUtils.isEmpty(extraColumns)) {
                        for(IntrospectedColumn column1: extraColumns) {
                            IntrospectedColumn oriColumn = oriColumnMap.get(column1.getActualColumnName());
                            if (oriColumn != null) {
                                if (StringUtils.isEmpty(column1.getRemarks())
                                        && !StringUtils.isEmpty(oriColumn.getRemarks())
                                ) {
                                    column1.setRemarks(oriColumn.getRemarks());
                                }
                            }
                        }
                    }
                } else {
                    if ("1".equals(properties.get("is_only_request_columns"))) {
                        properties.remove("is_only_request_columns");
                        introspectedColumns = new ArrayList<>();
                    } else if ("1".equals(properties.get("is_only_response_columns"))) {
                        properties.remove("is_only_param_columns");
                        introspectedColumns = new ArrayList<>();
                    } else {
                        introspectedColumns = introspectedTable.getAllColumns();
                    }
                    if (properties.containsKey("extra_request_columns")) {
                        String name = properties.get("extra_request_columns");
                        if (introspectedTable.getParameterColumns() != null && introspectedTable.getParameterColumns().containsKey("api.request." + name)) {
                            extraColumns = introspectedTable.getParameterColumns().get("api.request." + name);
                            introspectedColumns.addAll(extraColumns);
                        }
                    }
                    if (properties.containsKey("extra_response_columns")) {
                        String name = properties.get("extra_response_columns");
                        if (introspectedTable.getParameterColumns() != null && introspectedTable.getParameterColumns().containsKey("api.response." + name)) {
                            extraColumns = introspectedTable.getParameterColumns().get("api.response." + name);
                            introspectedColumns.addAll(extraColumns);
                        }
                    }
                }
            }
        } else if (isBlockToken(vTrim,"sqls")) {
            introspectedColumns = new ArrayList<>();
            for (String key : introspectedTable.getParameterColumnKeys()) {
                List<IntrospectedColumn> columns = introspectedTable.getParameterColumns().get(key);
                for(IntrospectedColumn column: columns) {
                    if ("sql".equals(column.getParameterCatalog())) {
                        introspectedColumns.add(column);
                    }
                }
            }
        } else if (isBlockToken(vTrim,"methods")) {
            result.setProcessed(true);
            introspectedColumns = new ArrayList<>();
            for (String key : introspectedTable.getParameterColumnKeys()) {
                List<IntrospectedColumn> columns = introspectedTable.getParameterColumns().get(key);
                IntrospectedColumn column = columns.get(0);
                if ("api.facade".equals(column.getParameterCatalog())) {
                    introspectedColumns.add(column);
                }
            }
        } else {
            ByteWithPos.appendDestBytes(destBytes, vTrim + System.lineSeparator());
        }
        if (!CollectionUtils.isEmpty(extraColumns)) {
            for(IntrospectedColumn column: extraColumns) {
                sqlParamNameMap.put(column.getActualColumnName(), column);
            }
        }
//        if (CollectionUtils.isEmpty(introspectedColumns)) {
//            return result;
//        }
        processBodyTokenWithColumns(introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytesList, lineStringList, lineTrimStringList, deepList, deep, destBytes, vTrim, properties, inColumns, introspectedColumns, extraColumns, _processInstance, _processBodyToken, _processToken);
        return result;
    }

    private String getCompilationUnits(IntrospectedTable introspectedTable, String template, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception {

        byte[] srcBytes = template.getBytes("UTF-8");
        int srcLength = srcBytes.length;

        ByteWithPos destBytes = new ByteWithPos(srcLength*2);

        for(int pos = 0; pos < srcLength; pos++) {
            ByteWithPos lineBytes = new ByteWithPos(1024);

            int srcPos = pos;
            while (pos < srcLength && srcBytes[pos] != '\n') {
                pos++;
            }
            if (srcPos == pos) {
                ByteWithPos.appendLineSeperator(destBytes);
                continue;
            }
            ByteWithPos.appendDestBytes(lineBytes, srcBytes, srcPos, pos);
            String v = new String(lineBytes.bytes, 0, lineBytes.pos, "UTF-8");
            String vTrim = v.trim();
            if ("".equals(vTrim)) {
                ByteWithPos.appendLineSeperator(destBytes);
                continue;
            }
            if (!vTrim.startsWith("{*")) {
                processLine(introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytes, destBytes, null, 0, _processInstance, _processBodyToken, _processToken);
                continue;
            }
            pos++;

            List<ByteWithPos> lineBytesList = new ArrayList<>();
            List<String> lineStringList = new ArrayList<>();
            List<String> lineTrimStringList = new ArrayList<>();
            List<Integer> deepList = new ArrayList<>();

            int deep = 1;

            lineBytesList.add(lineBytes);
            deepList.add(deep);
            lineStringList.add(v);
            lineTrimStringList.add(vTrim);

            while (deep > 0 && pos < srcLength) {
                lineBytes = new ByteWithPos(1024);
                srcPos = pos;
                while (pos < srcLength && srcBytes[pos] != '\n') {
                    pos++;
                }
                if (pos == srcLength) {
                    ByteWithPos.appendDestBytes(lineBytes, srcBytes, srcPos, pos);
                    lineBytesList.add(lineBytes);
                    String v2 = new String(srcBytes, srcPos, pos - srcPos, "UTF-8");
                    lineStringList.add(v2);
                    lineTrimStringList.add(v2.trim());
                    deepList.add(deep);
                    break;
                }
                if (srcPos == pos) {
                    ByteWithPos.appendDestBytes(lineBytes, "");
                    lineBytesList.add(lineBytes);
                    lineStringList.add("");
                    lineTrimStringList.add("");
                    deepList.add(deep);
                    pos++;
                    continue;
                }
                ByteWithPos.appendDestBytes(lineBytes, srcBytes, srcPos, pos);
                lineBytesList.add(lineBytes);
                String v2 = new String(lineBytes.bytes, 0, lineBytes.pos, "UTF-8");
                String vTrim2 = v2.trim();
                lineStringList.add(v2);
                lineTrimStringList.add(vTrim2);

                pos++;

                if (vTrim2.equals("{*}")) {
                    deepList.add(deep);
                    deep--;

                    lineBytesList.add(new ByteWithPos(1024));
                    lineStringList.add("{*}");
                    lineTrimStringList.add("{*}");
                    deepList.add(deep);
                } else {
                    if (vTrim2.startsWith("{*")) {
                        deep++;
                    }
                    deepList.add(deep);
                }
            }
            processBodyToken(introspectedTable, dynTemplate, fileName, sqlMethodName, lineBytesList, lineStringList, lineTrimStringList, deepList, 1, destBytes, vTrim, null, _processInstance, _processBodyToken, _processToken);
            if (deep == 0) {
                pos--;
            }
        }

        String content = new String(destBytes.bytes, 0, destBytes.pos, "UTF-8");

        return content;

    }

    String fixCData(String oldSql) {
        int pos = 0;
        String startStr = System.lineSeparator()+"<![CDATA[";
        String endStr = System.lineSeparator()+"]]>";
        do {
            pos = oldSql.indexOf(startStr, pos);
            if (pos < 0) {
                break;
            }
            int endPos = oldSql.indexOf(endStr, pos);
            if (endPos < 0) {
                break;
            }
            String midStr = oldSql.substring(pos, endPos+endStr.length());
            midStr = midStr.replaceAll(System.lineSeparator(), "");
            if ("<![CDATA[]]>".equals(midStr)) {
                oldSql = oldSql.substring(0, pos) + oldSql.substring(endPos + endStr.length());
            } else {
                pos += 10;
            }
        }while (pos >= 0);
        return oldSql;
    }

    @Getter
    @Setter
    public static class H2MetaField {
        public String name;
        public String tableName;
        public String originalTableName;
        public Integer mysqlType;

        void setMysqlType(int type) {
            mysqlType = type;
        }
    };

    private Object[] getMetaFields(ResultSetMetaData resultSetMetaData) throws Exception {
        Object[] jdbcFields;
        try {
            Field reflectField = resultSetMetaData.getClass().getDeclaredField("fields");
            reflectField.setAccessible(true);
            jdbcFields = (Object[]) reflectField.get(resultSetMetaData);
            return jdbcFields;

        }catch(Exception ex) {

        }

        int fieldCount = resultSetMetaData.getColumnCount();
        List<H2MetaField> fields = new ArrayList<>();
        for(int i = 0; i < fieldCount; i++) {
            H2MetaField field = new H2MetaField();
            field.setName(resultSetMetaData.getColumnName(i+1));
            field.setTableName(resultSetMetaData.getTableName(i+1));
            field.setOriginalTableName(resultSetMetaData.getTableName(i+1));
            field.setMysqlType(resultSetMetaData.getColumnType(i+1));
            fields.add(field);
        }
        jdbcFields = fields.toArray();
        return jdbcFields;
    }

}

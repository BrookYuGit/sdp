/**
 *    Copyright 2006-2020 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.api;

import java.sql.Types;
import java.util.Properties;

import cn.mysdp.utils.SplitUtil;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * This class holds information about an introspected column.
 * 
 * @author Jeff Butler
 */
public class IntrospectedColumn {
    protected String actualColumnName;

    protected int jdbcType;

    protected String jdbcTypeName;

    protected boolean nullable;

    protected int length;

    protected int scale;

    protected boolean identity;

    protected boolean isSequenceColumn;

    protected String javaProperty;

    protected FullyQualifiedJavaType fullyQualifiedJavaType;

    protected String tableAlias;

    protected String typeHandler;

    protected Context context;

    protected boolean isColumnNameDelimited;

    protected IntrospectedTable introspectedTable;

    protected Properties properties;

    // any database comment associated with this column. May be null
    protected String remarks;

    protected String defaultValue;

    /**
     * true if the JDBC driver reports that this column is auto-increment.
     */
    protected boolean isAutoIncrement;

    /**
     * true if the JDBC driver reports that this column is generated.
     */
    protected boolean isGeneratedColumn;

    /**
     * True if there is a column override that defines this column as GENERATED ALWAYS.
     */
    protected boolean isGeneratedAlways;

    private String parameterMode = "append";
    private String parameterImports;
    private String parameterCatalog = "";
    private String parameterCatalogType = "";
    private String parameterJavaReturnType = "";
    private String parameterJavaType = "";
    private String parameterName = "";
    private String parameterRemark = "";
    private String parameterOriSql = "";
    private String parameterSql = "";
    private String parameterSimpleSql = "";
    private String parameterSimpleWithStarSql = "";
    private String parameterSqlValue = "";
    private String parameterJavaBody = "";
    private String parameterExtraInfo = "";
    private Integer parameterSqlIsSimple = 0;
    private Integer parameterSqlReturnNoList = 0;
    private Integer parameterNullable = 0;
    private Integer parameterIsLike = 0;
    private Integer parameterIsImportExcel = 0;
    private Integer parameterIsExportExcel = 0;
    private Integer parameterIgnoreSqlValue = 0;
    private Integer parameterOverwriteDefaultSql = 0;
    private Integer parameterWithoutTest = 0;
    private Integer sqlIsInterface = 0;
    private Boolean isDup = false;

    private String uniqueKeyparameterForRepository = null;
    private String uniqueKeynameForRepository = null;
    private String uniqueKeytypeForMapper = null;

    private String originalTableName;
    private String tableName;
    private Integer typeId;

    public String getParameterMode() {
        return parameterMode;
    }

    public void setParameterMode(String parameterMode) {
        this.parameterMode = parameterMode;
    }

    public String getParameterCatalog() {
        return parameterCatalog;
    }

    public void setParameterCatalog(String parameterCatalog) {
        this.parameterCatalog = parameterCatalog;
    }

    public String getParameterImports() {
        return parameterImports;
    }

    public void setParameterImports(String parameterImports) {
        this.parameterImports = parameterImports;
    }

    /**
     * Constructs a Column definition. This object holds all the information
     * about a column that is required to generate Java objects and SQL maps;
     */
    public IntrospectedColumn() {
        super();
        properties = new Properties();
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    /*
     * This method is primarily used for debugging, so we don't externalize the
     * strings
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Actual Column Name: "); //$NON-NLS-1$
        sb.append(actualColumnName);
        sb.append(", JDBC Type: "); //$NON-NLS-1$
        sb.append(jdbcType);
        sb.append(", Nullable: "); //$NON-NLS-1$
        sb.append(nullable);
        sb.append(", Length: "); //$NON-NLS-1$
        sb.append(length);
        sb.append(", Scale: "); //$NON-NLS-1$
        sb.append(scale);
        sb.append(", Identity: "); //$NON-NLS-1$
        sb.append(identity);

        return sb.toString();
    }

    public void setActualColumnName(String actualColumnName) {
        this.actualColumnName = actualColumnName;
        isColumnNameDelimited = StringUtility
                .stringContainsSpace(actualColumnName);
    }

    public boolean isIdentity() {
        return identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    public boolean isBLOBColumn() {
        String typeName = getJdbcTypeName();

        return "BINARY".equals(typeName) || "BLOB".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
                || "CLOB".equals(typeName) || "LONGNVARCHAR".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$ 
                || "LONGVARBINARY".equals(typeName) || "LONGVARCHAR".equals(typeName) //$NON-NLS-1$ //$NON-NLS-2$
                || "NCLOB".equals(typeName) || "VARBINARY".equals(typeName); //$NON-NLS-1$ //$NON-NLS-2$ 
    }

    public boolean isStringColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
                .getStringInstance());
    }

    public boolean isJdbcCharacterColumn() {
        return jdbcType == Types.CHAR || jdbcType == Types.CLOB
                || jdbcType == Types.LONGVARCHAR || jdbcType == Types.VARCHAR
                || jdbcType == Types.LONGNVARCHAR || jdbcType == Types.NCHAR
                || jdbcType == Types.NCLOB || jdbcType == Types.NVARCHAR;
    }

    public String getJavaProperty() {
        return getJavaProperty(null);
    }

    public String getterProperty() {
        return getJavaProperty("get", true);
    }

    public String getJavaProperty(String prefix) {
        if (prefix == null) {
            return javaProperty;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(javaProperty);

        return sb.toString();
    }

    public String toLowerCaseFirstOne(String name){
        if(Character.isLowerCase(name.charAt(0))) {
            return name;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();
        }
    }

    public String toUpperCaseFirstOne(String name){
        if(Character.isLowerCase(name.charAt(0))) {
            return (new StringBuilder()).append(Character.toUpperCase(name.charAt(0))).append(name.substring(1)).toString();
        } else {
            return name;
        }
    }

    public String getJavaProperty(String prefix, boolean firstUpperCase) {
        if (prefix == null) {
            return getJavaProperty("", firstUpperCase);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(firstUpperCase ? toUpperCaseFirstOne(javaProperty) : toLowerCaseFirstOne(javaProperty));

        return sb.toString();
    }

    public void setJavaProperty(String javaProperty) {
        this.javaProperty = javaProperty;
    }

    public boolean isJDBCDateColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
                .getDateInstance())
                && "DATE".equalsIgnoreCase(jdbcTypeName); //$NON-NLS-1$
    }

    public boolean isJDBCTimeColumn() {
        return fullyQualifiedJavaType.equals(FullyQualifiedJavaType
                .getDateInstance())
                && "TIME".equalsIgnoreCase(jdbcTypeName); //$NON-NLS-1$
    }

    public String getTypeHandler() {
        return typeHandler;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }

    public String getActualColumnName() {
        return actualColumnName.toLowerCase();
    }

    public void setColumnNameDelimited(boolean isColumnNameDelimited) {
        this.isColumnNameDelimited = isColumnNameDelimited;
    }

    public boolean isColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public String getJdbcTypeName() {
        if (jdbcTypeName == null) {
            return "OTHER"; //$NON-NLS-1$
        }

        return jdbcTypeName;
    }

    public void setJdbcTypeName(String jdbcTypeName) {
        this.jdbcTypeName = jdbcTypeName;
    }

    public FullyQualifiedJavaType getFullyQualifiedJavaType() {
        return fullyQualifiedJavaType;
    }

    public void setFullyQualifiedJavaType(
            FullyQualifiedJavaType fullyQualifiedJavaType) {
        this.fullyQualifiedJavaType = fullyQualifiedJavaType;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public IntrospectedTable getIntrospectedTable() {
        return introspectedTable;
    }

    public void setIntrospectedTable(IntrospectedTable introspectedTable) {
        this.introspectedTable = introspectedTable;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    public String getRemarks() {
        return remarks;
    }

    public String getFirstRemarks() {
        String remarks = this.remarks;
        if (StringUtility.stringHasValue(remarks)) {
            remarks = SplitUtil.split(remarks, System.getProperty("line.separator"))[0];  //$NON-NLS-1$
            remarks = SplitUtil.split(remarks, "\\n")[0];
            return remarks;
        } else {
            return actualColumnName;
        }
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isSequenceColumn() {
        return isSequenceColumn;
    }

    public void setSequenceColumn(boolean isSequenceColumn) {
        this.isSequenceColumn = isSequenceColumn;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public boolean isGeneratedColumn() {
        return isGeneratedColumn;
    }

    public void setGeneratedColumn(boolean isGeneratedColumn) {
        this.isGeneratedColumn = isGeneratedColumn;
    }

    public boolean isGeneratedAlways() {
        return isGeneratedAlways;
    }

    public void setGeneratedAlways(boolean isGeneratedAlways) {
        this.isGeneratedAlways = isGeneratedAlways;
    }

    public String getParameterCatalogType() {
        return parameterCatalogType;
    }

    public void setParameterCatalogType(String parameterCatalogType) {
        this.parameterCatalogType = parameterCatalogType;
    }

    public String getParameterJavaReturnType() {
        return parameterJavaReturnType;
    }

    public void setParameterJavaReturnType(String parameterJavaReturnType) {
        this.parameterJavaReturnType = parameterJavaReturnType;
    }

    public String getParameterJavaType() {
        return parameterJavaType;
    }

    public void setParameterJavaType(String parameterJavaType) {
        this.parameterJavaType = parameterJavaType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterRemark() {
        return parameterRemark;
    }

    public void setParameterRemark(String parameterRemark) {
        this.parameterRemark = parameterRemark;
    }

    public String getParameterRemarkOrMethodName() {
        if (StringUtility.stringHasValue(getParameterRemark())) {
            return getParameterRemark();
        }
        return getParameterCatalogType();
    }

    public String getParameterSql() {
        return parameterSql;
    }

    public void setParameterSql(String parameterSql) {
        this.parameterSql = parameterSql;
    }

    public String getParameterSqlValue() {
        return parameterSqlValue;
    }

    public void setParameterSqlValue(String parameterSqlValue) {
        this.parameterSqlValue = parameterSqlValue;
    }

    public Integer getParameterSqlIsSimple() {
        return parameterSqlIsSimple;
    }

    public void setParameterSqlIsSimple(Integer parameterSqlIsSimple) {
        this.parameterSqlIsSimple = parameterSqlIsSimple;
    }

    public String getParameterSimpleSql() {
        return parameterSimpleSql;
    }

    public void setParameterSimpleSql(String parameterSimpleSql) {
        this.parameterSimpleSql = parameterSimpleSql;
    }

    public Integer getParameterSqlReturnNoList() {
        return parameterSqlReturnNoList;
    }

    public void setParameterSqlReturnNoList(Integer parameterSqlReturnNoList) {
        this.parameterSqlReturnNoList = parameterSqlReturnNoList;
    }

    public String getParameterJavaBody() {
        return parameterJavaBody;
    }

    public void setParameterJavaBody(String parameterJavaBody) {
        this.parameterJavaBody = parameterJavaBody;
    }

    public Integer getParameterNullable() {
        return parameterNullable;
    }

    public void setParameterNullable(Integer parameterNullable) {
        this.parameterNullable = parameterNullable;
    }

    public Integer getParameterIsLike() {
        return parameterIsLike;
    }

    public void setParameterIsLike(Integer parameterIsLike) {
        this.parameterIsLike = parameterIsLike;
    }

    public Integer getParameterIsImportExcel() {
        return parameterIsImportExcel;
    }

    public void setParameterIsImportExcel(Integer parameterIsImportExcel) {
        this.parameterIsImportExcel = parameterIsImportExcel;
    }

    public Integer getParameterIgnoreSqlValue() {
        return parameterIgnoreSqlValue;
    }

    public void setParameterIgnoreSqlValue(Integer parameterIgnoreSqlValue) {
        this.parameterIgnoreSqlValue = parameterIgnoreSqlValue;
    }

    public Integer getParameterIsExportExcel() {
        return parameterIsExportExcel;
    }

    public void setParameterIsExportExcel(Integer parameterIsExportExcel) {
        this.parameterIsExportExcel = parameterIsExportExcel;
    }

    public Integer getParameterOverwriteDefaultSql() {
        return parameterOverwriteDefaultSql;
    }

    public void setParameterOverwriteDefaultSql(Integer parameterOverwriteDefaultSql) {
        this.parameterOverwriteDefaultSql = parameterOverwriteDefaultSql;
    }


    public String getUniqueKeyparameterForRepository() {
        return uniqueKeyparameterForRepository;
    }

    public void setUniqueKeyparameterForRepository(String uniqueKeyparameterForRepository) {
        this.uniqueKeyparameterForRepository = uniqueKeyparameterForRepository;
    }

    public String getUniqueKeynameForRepository() {
        return uniqueKeynameForRepository;
    }

    public void setUniqueKeynameForRepository(String uniqueKeynameForRepository) {
        this.uniqueKeynameForRepository = uniqueKeynameForRepository;
    }

    public String getUniqueKeytypeForMapper() {
        return uniqueKeytypeForMapper;
    }

    public void setUniqueKeytypeForMapper(String uniqueKeytypeForMapper) {
        this.uniqueKeytypeForMapper = uniqueKeytypeForMapper;
    }


    public String getOriginalTableName() {
        return originalTableName;
    }

    public void setOriginalTableName(String originalTableName) {
        this.originalTableName = originalTableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }


    public Integer getParameterWithoutTest() {
        return parameterWithoutTest;
    }

    public void setParameterWithoutTest(Integer parameterWithoutTest) {
        this.parameterWithoutTest = parameterWithoutTest;
    }

    public String getParameterOriSql() {
        return parameterOriSql;
    }

    public void setParameterOriSql(String parameterOriSql) {
        this.parameterOriSql = parameterOriSql;
    }

    public Integer getSqlIsInterface() {
        return sqlIsInterface;
    }

    public void setSqlIsInterface(Integer sqlIsInterface) {
        this.sqlIsInterface = sqlIsInterface;
    }


    public String getParameterSimpleWithStarSql() {
        return parameterSimpleWithStarSql;
    }

    public void setParameterSimpleWithStarSql(String parameterSimpleWithStarSql) {
        this.parameterSimpleWithStarSql = parameterSimpleWithStarSql;
    }

    public String getParameterExtraInfo() {
        return parameterExtraInfo;
    }

    public void setParameterExtraInfo(String parameterExtraInfo) {
        this.parameterExtraInfo = parameterExtraInfo;
    }

    public Boolean getDup() {
        return isDup;
    }

    public void setDup(Boolean dup) {
        isDup = dup;
    }

}

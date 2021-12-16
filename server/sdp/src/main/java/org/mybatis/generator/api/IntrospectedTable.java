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

import static org.mybatis.generator.internal.util.JavaBeansUtil.getCamelCaseString;
import static org.mybatis.generator.internal.util.StringUtility.isTrue;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.*;

import cn.mysdp.utils.SplitUtil;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * Base class for all code generator implementations. This class provides many
 * of the housekeeping methods needed to implement a code generator, with only
 * the actual code generation methods left unimplemented.
 * 
 * @author Jeff Butler
 * 
 */
public abstract class IntrospectedTable {

    public enum TargetRuntime {
        MYBATIS3,
        MYBATIS3_DSQL
    }

    protected enum InternalAttribute {
        ATTR_PRIMARY_KEY_TYPE,
        ATTR_MYBATIS3_XML_MAPPER_PACKAGE,
        ATTR_MYBATIS3_XML_MAPPER_FILE_NAME,
        /** also used as XML Mapper namespace if a Java mapper is generated. */
        ATTR_MYBATIS3_JAVA_MAPPER_TYPE,

        /** used as XML Mapper namespace if no client is generated. */
        ATTR_MYBATIS3_FALLBACK_SQL_MAP_NAMESPACE,
        ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME,
        ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME,
        ATTR_MYBATIS3_SQL_PROVIDER_TYPE,
        ATTR_MYBATIS_DYNAMIC_SQL_SUPPORT_TYPE
    }

    protected TableConfiguration tableConfiguration;

    protected FullyQualifiedTable fullyQualifiedTable;

    protected Context context;

    protected Map<String, List<IntrospectedColumn>> uniqueColumns;

    protected List<IntrospectedColumn> primaryKeyColumns;
    protected Map<String, IntrospectedColumn> primaryKeyColumnMap = new HashMap<>();

    protected List<IntrospectedColumn> baseColumns;

    protected List<IntrospectedColumn> blobColumns;

    protected Map<String, List<IntrospectedColumn>> parameterColumns = new HashMap<>();

    protected List<String> parameterColumnKeys = new ArrayList<>();

    protected TargetRuntime targetRuntime;

    protected Set<String> basicFacadeName = new HashSet<>();

    /**
     * Attributes may be used by plugins to capture table related state between
     * the different plugin calls.
     */
    protected Map<String, Object> attributes;

    /** Internal attributes are used to store commonly accessed items by all code generators. */
    protected Map<IntrospectedTable.InternalAttribute, String> internalAttributes;

    /**
     * Table remarks retrieved from database metadata.
     */
    protected String remarks;

    /**
     * Table type retrieved from database metadata.
     */
    protected String tableType;

    public IntrospectedTable(TargetRuntime targetRuntime) {
        super();
        this.targetRuntime = targetRuntime;
        primaryKeyColumns = new ArrayList<>();
        primaryKeyColumnMap = new HashMap<>();
        uniqueColumns = new HashMap<>();
        baseColumns = new ArrayList<>();
        blobColumns = new ArrayList<>();
        attributes = new HashMap<>();
        internalAttributes = new HashMap<>();
    }

    public FullyQualifiedTable getFullyQualifiedTable() {
        return fullyQualifiedTable;
    }

    public String getSelectByExampleQueryId() {
        return tableConfiguration.getSelectByExampleQueryId();
    }

    public String getSelectByPrimaryKeyQueryId() {
        return tableConfiguration.getSelectByPrimaryKeyQueryId();
    }

    public GeneratedKey getGeneratedKey() {
        return tableConfiguration.getGeneratedKey();
    }

    public IntrospectedColumn getColumn(String columnName) {
        if (columnName == null) {
            return null;
        } else {
            columnName = columnName.toLowerCase();
            // search primary key columns
            for (IntrospectedColumn introspectedColumn : primaryKeyColumns) {
                if (introspectedColumn.isColumnNameDelimited()) {
                    if (introspectedColumn.getActualColumnName().equals(
                            columnName)) {
                        return introspectedColumn;
                    }
                } else {
                    if (introspectedColumn.getActualColumnName()
                            .equalsIgnoreCase(columnName)) {
                        return introspectedColumn;
                    }
                }
            }

            // search base columns
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isColumnNameDelimited()) {
                    if (introspectedColumn.getActualColumnName().equals(
                            columnName)) {
                        return introspectedColumn;
                    }
                } else {
                    if (introspectedColumn.getActualColumnName()
                            .equalsIgnoreCase(columnName)) {
                        return introspectedColumn;
                    }
                }
            }

            // search blob columns
            for (IntrospectedColumn introspectedColumn : blobColumns) {
                if (introspectedColumn.isColumnNameDelimited()) {
                    if (introspectedColumn.getActualColumnName().equals(
                            columnName)) {
                        return introspectedColumn;
                    }
                } else {
                    if (introspectedColumn.getActualColumnName()
                            .equalsIgnoreCase(columnName)) {
                        return introspectedColumn;
                    }
                }
            }

            return null;
        }
    }

    /**
     * Returns true if any of the columns in the table are JDBC Dates (as
     * opposed to timestamps).
     * 
     * @return true if the table contains DATE columns
     */
    public boolean hasJDBCDateColumns() {
        boolean rc = false;

        for (IntrospectedColumn introspectedColumn : primaryKeyColumns) {
            if (introspectedColumn.isJDBCDateColumn()) {
                rc = true;
                break;
            }
        }

        if (!rc) {
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isJDBCDateColumn()) {
                    rc = true;
                    break;
                }
            }
        }

        for(String key: parameterColumnKeys) {
            for(IntrospectedColumn column: parameterColumns.get(key)) {
                if (column.isJDBCDateColumn()) {
                    rc = true;
                    break;
                }
            }
        }

        return rc;
    }

    /**
     * Returns true if any of the columns in the table are JDBC Times (as
     * opposed to timestamps).
     * 
     * @return true if the table contains TIME columns
     */
    public boolean hasJDBCTimeColumns() {
        boolean rc = false;

        for (IntrospectedColumn introspectedColumn : primaryKeyColumns) {
            if (introspectedColumn.isJDBCTimeColumn()) {
                rc = true;
                break;
            }
        }

        if (!rc) {
            for (IntrospectedColumn introspectedColumn : baseColumns) {
                if (introspectedColumn.isJDBCTimeColumn()) {
                    rc = true;
                    break;
                }
            }
        }

        for(String key: parameterColumnKeys) {
            for(IntrospectedColumn column: parameterColumns.get(key)) {
                if (column.isJDBCTimeColumn()) {
                    rc = true;
                    break;
                }
            }
        }


        return rc;
    }

    /**
     * Returns the columns in the primary key. If the generatePrimaryKeyClass()
     * method returns false, then these columns will be iterated as the
     * parameters of the selectByPrimaryKay and deleteByPrimaryKey methods
     * 
     * @return a List of ColumnDefinition objects for columns in the primary key
     */
    public List<IntrospectedColumn> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public List<IntrospectedColumn> getNotNullCColumnsForWebRule() {
        List<IntrospectedColumn> result = new ArrayList<>();
        Map<String, IntrospectedColumn> map = new HashMap<>();
        for(IntrospectedColumn col: getPrimaryKeyColumns()) {
            map.put(col.getActualColumnName(), col);
        }
        for(String indexName: getUniqueKeys()) {
            for(IntrospectedColumn col: getUniqueColumns(indexName)) {
                map.put(col.getActualColumnName(), col);
            }
        }
        for(IntrospectedColumn col: map.values()) {
            result.add(col);
        }
        return result;
    }

    public List<IntrospectedColumn> getNullCColumnsForWebRule() {
        List<IntrospectedColumn> notNullCols = getNotNullCColumnsForWebRule();
        Set<String> set = new HashSet<>();
        for(IntrospectedColumn col: notNullCols) {
            set.add(col.getActualColumnName());
        }
        List<IntrospectedColumn> result = new ArrayList<>();
        for(IntrospectedColumn col: getAllColumns()) {
            if (!set.contains(col.getActualColumnName())) {
                result.add(col);
            }
        }
        return result;
    }

    public List<IntrospectedColumn> getPrimaryKeyColumns(String indexName) {
        if (indexName == null) {
            return getPrimaryKeyColumns();
        } else {
            return getUniqueColumns(indexName);
        }
    }

    public List<IntrospectedColumn> getUniqueColumns(String indexName) {
        return uniqueColumns.get(indexName);
    }

    public Set<String> getUniqueKeys() {
        return uniqueColumns.keySet();
    }

    public boolean hasPrimaryKeyColumns() {
        return primaryKeyColumns.size() > 0;
    }

    public boolean hasUniqueColumns() {
        return uniqueColumns.size() > 0;
    }

    public List<IntrospectedColumn> getBaseColumns() {
        return baseColumns;
    }

    /**
     * Returns all columns in the table (for use by the select by primary key and
     * select by example with BLOBs methods).
     *
     * @return a List of ColumnDefinition objects for all columns in the table
     */
    public List<IntrospectedColumn> getAllColumns() {
        List<IntrospectedColumn> answer = new ArrayList<>();
        answer.addAll(primaryKeyColumns);
        answer.addAll(baseColumns);
        answer.addAll(blobColumns);

        return answer;
    }

    /**
     * Returns all columns except BLOBs (for use by the select by example without BLOBs method).
     *
     * @return a List of ColumnDefinition objects for columns in the table that are non BLOBs
     */
    public List<IntrospectedColumn> getNonBLOBColumns() {
        List<IntrospectedColumn> answer = new ArrayList<>();
        answer.addAll(primaryKeyColumns);
        answer.addAll(baseColumns);

        return answer;
    }

    public int getNonBLOBColumnCount() {
        return primaryKeyColumns.size() + baseColumns.size();
    }

    public List<IntrospectedColumn> getNonPrimaryKeyColumns() {
        List<IntrospectedColumn> answer = new ArrayList<>();
        answer.addAll(baseColumns);
        answer.addAll(blobColumns);

        return answer;
    }

    public List<IntrospectedColumn> getNonPrimaryKeyColumns(String indexName) {
        if (indexName == null) {
            return getNonPrimaryKeyColumns();
        }
        List<IntrospectedColumn> answer = new ArrayList<>();
//        answer.addAll(primaryKeyColumns);
//        answer.addAll(baseColumns);
//        answer.addAll(blobColumns);

        List<IntrospectedColumn> uniqueCols = getUniqueColumns(indexName);
        Map<String, IntrospectedColumn> uniqueColsMap = new HashMap<>();
        for(IntrospectedColumn col: uniqueCols) {
            uniqueColsMap.put(col.getActualColumnName(), col);
        }
        for(IntrospectedColumn col: primaryKeyColumns) {
            if (!uniqueColsMap.containsKey(col.getActualColumnName())) {
                answer.add(col);
            }
        }
        for(IntrospectedColumn col: baseColumns) {
            if (!uniqueColsMap.containsKey(col.getActualColumnName())) {
                answer.add(col);
            }
        }
        for(IntrospectedColumn col: blobColumns) {
            if (!uniqueColsMap.containsKey(col.getActualColumnName())) {
                answer.add(col);
            }
        }

        return answer;
    }

    public List<IntrospectedColumn> getBLOBColumns() {
        return blobColumns;
    }

    public boolean hasBLOBColumns() {
        return blobColumns.size() > 0;
    }

    public boolean hasBaseColumns() {
        return baseColumns.size() > 0;
    }

    public String getTableConfigurationProperty(String property) {
        return tableConfiguration.getProperty(property);
    }

    public String getPrimaryKeyType() {
        return internalAttributes.get(InternalAttribute.ATTR_PRIMARY_KEY_TYPE);
    }

    public String getMyBatis3SqlMapNamespace() {
        String namespace = getMyBatis3JavaMapperType();
        if (namespace == null) {
            namespace = getMyBatis3FallbackSqlMapNamespace();
        }

        return namespace;
    }

    public String getMyBatis3FallbackSqlMapNamespace() {
        return internalAttributes
                .get(InternalAttribute.ATTR_MYBATIS3_FALLBACK_SQL_MAP_NAMESPACE);
    }

    public boolean hasAnyColumns() {
        return primaryKeyColumns.size() > 0 || baseColumns.size() > 0
                || blobColumns.size() > 0;
    }

    public void setTableConfiguration(TableConfiguration tableConfiguration) {
        this.tableConfiguration = tableConfiguration;
    }

    public void setFullyQualifiedTable(FullyQualifiedTable fullyQualifiedTable) {
        this.fullyQualifiedTable = fullyQualifiedTable;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void addColumn(IntrospectedColumn introspectedColumn) {
        if (introspectedColumn.isBLOBColumn()) {
            blobColumns.add(introspectedColumn);
        } else {
            baseColumns.add(introspectedColumn);
        }

        introspectedColumn.setIntrospectedTable(this);
    }

    public void addPrimaryKeyColumn(String columnName) throws Exception {
        boolean found = false;
        // first search base columns
        Iterator<IntrospectedColumn> iter = baseColumns.iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            if (introspectedColumn.getActualColumnName().equals(columnName)) {
                primaryKeyColumns.add(introspectedColumn);
                primaryKeyColumnMap.put(introspectedColumn.getActualColumnName(), introspectedColumn);
                iter.remove();
                found = true;
                break;
            }
        }

        // search blob columns in the weird event that a blob is the primary key
        if (!found) {
            iter = blobColumns.iterator();
            while (iter.hasNext()) {
                IntrospectedColumn introspectedColumn = iter.next();
                if (introspectedColumn.getActualColumnName().equals(columnName)) {
                    primaryKeyColumns.add(introspectedColumn);
                    primaryKeyColumnMap.put(introspectedColumn.getActualColumnName(), introspectedColumn);
                    iter.remove();
                    found = true;
                    break;
                }
            }
        }

        //Why getColumns not return primary key?
        if (!found) {
            primaryKeyColumns.iterator();
            while (iter.hasNext()) {
                IntrospectedColumn introspectedColumn = iter.next();
                if (introspectedColumn.getActualColumnName().equals(columnName)) {
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new Exception("未找到主键:"+columnName+","+this.getDomainObjectName());
        }
    }

    public void addUniqueColumn(String indexName, String columnName) {
        boolean found = false;
        // first search base columns
        Iterator<IntrospectedColumn> iter = getAllColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            if (introspectedColumn.getActualColumnName().equals(columnName)) {
                if (!uniqueColumns.containsKey(indexName)) {
                    uniqueColumns.put(indexName, new ArrayList<>());
                }
                uniqueColumns.get(indexName).add(introspectedColumn);
//                iter.remove();
                found = true;
                break;
            }
        }
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void initialize() {
    }

    public String getPrimaryKeyColumnNames() {
        List<IntrospectedColumn> list = getPrimaryKeyColumns();
        String name = "";
        for(IntrospectedColumn n: list) {
            if (name.length() >= 0) {
                name += "_";
            }
            name += n.getActualColumnName();
        }
        return getCamelCaseString(name, true);
    }

    public String getUniqueColumnNames(String indexName) {
        List<IntrospectedColumn> list = getUniqueColumns(indexName);
        String name = "";
        for(IntrospectedColumn n: list) {
            if (name.length() >= 0) {
                name += "_";
            }
            name += n.getActualColumnName();
        }
        return getCamelCaseString(name, true);
    }

    private boolean isSubPackagesEnabled(PropertyHolder propertyHolder) {
        return isTrue(propertyHolder.getProperty(PropertyRegistry.ANY_ENABLE_SUB_PACKAGES));
    }

    public String getAliasTableName() {

        String aliasName = getFullyQualifiedTableNameAtRuntime();
        for(String key: getParameterColumns().keySet()) {
            if (key.startsWith("%")) {
                IntrospectedColumn introspectedColumn = getParameterColumns().get(key).get(0);
                aliasName = introspectedColumn.getParameterCatalogType();
                return aliasName;
            }
        }
        return aliasName;
    }

    public String calculateJavaNameWithSuffixName(String packageName, String suffixName) {

        StringBuilder sb = new StringBuilder();
        sb.append(packageName);
        sb.append(".");
        sb.append(JavaBeansUtil.getCamelCaseString(getAliasTableName(), true));
        sb.append(suffixName);
        return sb.toString();
    }

    protected String calculateSqlMapFullyQualifiedRuntimeTableName() {
        return fullyQualifiedTable.getFullyQualifiedTableNameAtRuntime();
    }

    protected String calculateSqlMapAliasedFullyQualifiedRuntimeTableName() {
        return fullyQualifiedTable.getAliasedFullyQualifiedTableNameAtRuntime();
    }

    public String getFullyQualifiedTableNameAtRuntime() {
        return internalAttributes
                .get(InternalAttribute.ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME);
    }

    public String getAliasedFullyQualifiedTableNameAtRuntime() {
        return internalAttributes
                .get(InternalAttribute.ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME);
    }

    /**
     * This method can be used to initialize the generators before they will be called.
     * 
     * <p>This method is called after all the setX methods, but before getNumberOfSubtasks(), getGeneratedJavaFiles, and
     * getGeneratedXmlFiles.
     *
     * @param warnings
     *            the warnings
     * @param progressCallback
     *            the progress callback
     */
    public abstract void calculateGenerators(List<String> warnings,
            ProgressCallback progressCallback);

    /**
     * This method should return the number of progress messages that will be
     * send during the generation phase.
     * 
     * @return the number of progress messages
     */
    public abstract int getGenerationSteps();

    public TableConfiguration getTableConfiguration() {
        return tableConfiguration;
    }

    public void setPrimaryKeyType(String primaryKeyType) {
        internalAttributes.put(InternalAttribute.ATTR_PRIMARY_KEY_TYPE,
                primaryKeyType);
    }

    public void setMyBatis3FallbackSqlMapNamespace(String sqlMapNamespace) {
        internalAttributes.put(
                InternalAttribute.ATTR_MYBATIS3_FALLBACK_SQL_MAP_NAMESPACE,
                sqlMapNamespace);
    }

    public void setSqlMapFullyQualifiedRuntimeTableName(
            String fullyQualifiedRuntimeTableName) {
        internalAttributes.put(
                InternalAttribute.ATTR_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME,
                fullyQualifiedRuntimeTableName);
    }

    public void setSqlMapAliasedFullyQualifiedRuntimeTableName(
            String aliasedFullyQualifiedRuntimeTableName) {
        internalAttributes
                .put(
                        InternalAttribute.ATTR_ALIASED_FULLY_QUALIFIED_TABLE_NAME_AT_RUNTIME,
                        aliasedFullyQualifiedRuntimeTableName);
    }

    public String getMyBatis3XmlMapperPackage() {
        return internalAttributes
                .get(InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_PACKAGE);
    }

    public void setMyBatis3XmlMapperPackage(String mybatis3XmlMapperPackage) {
        internalAttributes.put(
                InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_PACKAGE,
                mybatis3XmlMapperPackage);
    }

    public String getMyBatis3XmlMapperFileName() {
        return internalAttributes
                .get(InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_FILE_NAME);
    }

    public void setMyBatis3XmlMapperFileName(String mybatis3XmlMapperFileName) {
        internalAttributes.put(
                InternalAttribute.ATTR_MYBATIS3_XML_MAPPER_FILE_NAME,
                mybatis3XmlMapperFileName);
    }

    public String getMyBatis3JavaMapperType() {
        return internalAttributes
                .get(InternalAttribute.ATTR_MYBATIS3_JAVA_MAPPER_TYPE);
    }

    public void setMyBatis3JavaMapperType(String mybatis3JavaMapperType) {
        internalAttributes.put(
                InternalAttribute.ATTR_MYBATIS3_JAVA_MAPPER_TYPE,
                mybatis3JavaMapperType);
    }

    public String getMyBatis3SqlProviderType() {
        return internalAttributes
                .get(InternalAttribute.ATTR_MYBATIS3_SQL_PROVIDER_TYPE);
    }

    public void setMyBatis3SqlProviderType(String mybatis3SqlProviderType) {
        internalAttributes.put(
                InternalAttribute.ATTR_MYBATIS3_SQL_PROVIDER_TYPE,
                mybatis3SqlProviderType);
    }
    
    public String getMyBatisDynamicSqlSupportType() {
        return internalAttributes.get(InternalAttribute.ATTR_MYBATIS_DYNAMIC_SQL_SUPPORT_TYPE);
    }
    
    public void setMyBatisDynamicSqlSupportType(String s) {
        internalAttributes.put(InternalAttribute.ATTR_MYBATIS_DYNAMIC_SQL_SUPPORT_TYPE, s);
    }
    
    public TargetRuntime getTargetRuntime() {
        return targetRuntime;
    }
    
    public boolean isImmutable() {
        Properties properties;

        if (tableConfiguration.getProperties().containsKey(PropertyRegistry.ANY_IMMUTABLE)) {
            properties = tableConfiguration.getProperties();
        } else {
            properties = context.getJavaModelGeneratorConfiguration().getProperties();
        }

        return isTrue(properties.getProperty(PropertyRegistry.ANY_IMMUTABLE));
    }

    public boolean isConstructorBased() {
        if (isImmutable()) {
            return true;
        }

        Properties properties;

        if (tableConfiguration.getProperties().containsKey(PropertyRegistry.ANY_CONSTRUCTOR_BASED)) {
            properties = tableConfiguration.getProperties();
        } else {
            properties = context.getJavaModelGeneratorConfiguration().getProperties();
        }

        return isTrue(properties.getProperty(PropertyRegistry.ANY_CONSTRUCTOR_BASED));
    }

    /**
     * Should return true if an XML generator is required for this table. This method will be called during validation
     * of the configuration, so it should not rely on database introspection. This method simply tells the validator if
     * an XML configuration is normally required for this implementation.
     *
     * @return true, if successful
     */
    public abstract boolean requiresXMLGenerator();

    public Context getContext() {
        return context;
    }

    public String getRemarks() {
        if (remarks == null) {
            return "";
        }
        return remarks.trim();
    }

    public String getFirstRemarks() {
        String remarks = this.remarks;
        if (StringUtility.stringHasValue(remarks)) {
            remarks = SplitUtil.split(remarks, System.getProperty("line.separator"))[0];  //$NON-NLS-1$
            remarks = SplitUtil.split(remarks, "\\n")[0];
        }
        return remarks.trim();
    }

    public String getFirstRemarksOrTableName() {
        String remarks = this.remarks;
        if (StringUtility.stringHasValue(remarks)) {
            remarks = SplitUtil.split(remarks, System.getProperty("line.separator"))[0];  //$NON-NLS-1$
            remarks = SplitUtil.split(remarks, "\\n")[0];
        }
        if (StringUtility.stringHasValue(remarks)) {
            return remarks;
        }
        return getAliasTableName();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }


    public String getDomainObjectName() {
        return fullyQualifiedTable.getDomainObjectName();
    }
    public String getDomainTypeName() {
        return JavaBeansUtil.getFirstCharacterUppercase(getDomainObjectName());
    }

    public Map<String, List<IntrospectedColumn>> getParameterColumns() {
        return parameterColumns;
    }

    public void setParameterColumns(Map<String, List<IntrospectedColumn>> parameterColumns) {
        this.parameterColumns = parameterColumns;
    }

    public boolean isPrimaryKey(String columnName) {
        return primaryKeyColumnMap.containsKey(columnName);
    }

    public List<String> getParameterColumnKeys() {
        return parameterColumnKeys;
    }

    public void setParameterColumnKeys(List<String> parameterColumnKeys) {
        this.parameterColumnKeys = parameterColumnKeys;
    }

}

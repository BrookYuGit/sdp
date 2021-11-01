/**
 *    Copyright 2006-2019 the original author or authors.
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
package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtility.composeFullyQualifiedTableName;
import static org.mybatis.generator.internal.util.StringUtility.isTrue;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import org.mybatis.generator.api.*;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.db.DatabaseIntrospector;

public class Context extends PropertyHolder {

    private String id;

    private JDBCConnectionConfiguration jdbcConnectionConfiguration;

    private ConnectionFactoryConfiguration connectionFactoryConfiguration;

    private SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration;

    private JavaTypeResolverConfiguration javaTypeResolverConfiguration;

    private JavaModelGeneratorConfiguration javaModelGeneratorConfiguration;

    private JavaClientGeneratorConfiguration javaClientGeneratorConfiguration;

    private ArrayList<TableConfiguration> tableConfigurations;

    private ModelType defaultModelType;

    private String beginningDelimiter = "\""; //$NON-NLS-1$

    private String endingDelimiter = "\""; //$NON-NLS-1$

    private String targetRuntime;

    private String introspectedColumnImpl;

    private Boolean autoDelimitKeywords;

    private boolean isJava8Targeted = true;

    public static Map<String, List<IntrospectedTable>> introspectTablesMap = new HashMap<>();
    public static Map<String, Map<String, List<IntrospectedColumn>>> parameterColumnsMap = new HashMap<>();
    public static Map<String, List<String>> parameterColumnKeysMap = new HashMap<>();

    private Configuration configuration;

    public Context(ModelType defaultModelType) {
        super();

        if (defaultModelType == null) {
            this.defaultModelType = ModelType.CONDITIONAL;
        } else {
            this.defaultModelType = defaultModelType;
        }

        tableConfigurations = new ArrayList<>();
    }

    public void addTableConfiguration(TableConfiguration tc) {
        tableConfigurations.add(tc);
    }

    public JDBCConnectionConfiguration getJdbcConnectionConfiguration() {
        return jdbcConnectionConfiguration;
    }

    public JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
        return javaClientGeneratorConfiguration;
    }

    public JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
        return javaModelGeneratorConfiguration;
    }

    public JavaTypeResolverConfiguration getJavaTypeResolverConfiguration() {
        return javaTypeResolverConfiguration;
    }

    public SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
        return sqlMapGeneratorConfiguration;
    }

    /**
     * This method does a simple validate, it makes sure that all required fields have been filled in. It does not do
     * any more complex operations such as validating that database tables exist or validating that named columns exist
     *
     * @param errors
     *            the errors
     */
    public void validate(List<String> errors) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJavaClientGeneratorConfiguration(
            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration) {
        this.javaClientGeneratorConfiguration = javaClientGeneratorConfiguration;
    }

    public void setJavaModelGeneratorConfiguration(
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration) {
        this.javaModelGeneratorConfiguration = javaModelGeneratorConfiguration;
    }

    public void setJavaTypeResolverConfiguration(
            JavaTypeResolverConfiguration javaTypeResolverConfiguration) {
        this.javaTypeResolverConfiguration = javaTypeResolverConfiguration;
    }

    public void setJdbcConnectionConfiguration(
            JDBCConnectionConfiguration jdbcConnectionConfiguration) {
        this.jdbcConnectionConfiguration = jdbcConnectionConfiguration;
    }

    public void setSqlMapGeneratorConfiguration(
            SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration) {
        this.sqlMapGeneratorConfiguration = sqlMapGeneratorConfiguration;
    }

    public ModelType getDefaultModelType() {
        return defaultModelType;
    }

    public List<TableConfiguration> getTableConfigurations() {
        return tableConfigurations;
    }

    public String getBeginningDelimiter() {
        return beginningDelimiter;
    }

    public String getEndingDelimiter() {
        return endingDelimiter;
    }

    @Override
    public void addProperty(String name, String value) {
        super.addProperty(name, value);

        if (PropertyRegistry.CONTEXT_BEGINNING_DELIMITER.equals(name)) {
            beginningDelimiter = value;
        } else if (PropertyRegistry.CONTEXT_ENDING_DELIMITER.equals(name)) {
            endingDelimiter = value;
        } else if (PropertyRegistry.CONTEXT_AUTO_DELIMIT_KEYWORDS.equals(name)
                && stringHasValue(value)) {
            autoDelimitKeywords = isTrue(value);
        } else if (PropertyRegistry.CONTEXT_TARGET_JAVA8.equals(name)
                && stringHasValue(value)) {
            isJava8Targeted = isTrue(value);
        }
    }

    public String getTargetRuntime() {
        return targetRuntime;
    }

    public void setTargetRuntime(String targetRuntime) {
        this.targetRuntime = targetRuntime;
    }

    public String getIntrospectedColumnImpl() {
        return introspectedColumnImpl;
    }

    public void setIntrospectedColumnImpl(String introspectedColumnImpl) {
        this.introspectedColumnImpl = introspectedColumnImpl;
    }

    // methods related to code generation.
    //
    // Methods should be called in this order:
    //
    // 1. getIntrospectionSteps()
    // 2. introspectTables()
    // 3. getGenerationSteps()
    // 4. generateFiles()
    //

    private List<IntrospectedTable> introspectedTables;

    public int getIntrospectionSteps() {
        int steps = 0;

        steps++; // connect to database

        // for each table:
        //
        // 1. Create introspected table implementation

        steps += tableConfigurations.size() * 1;

        return steps;
    }

    /**
     * Introspect tables based on the configuration specified in the
     * constructor. This method is long running.
     * 
     * @param callback
     *            a progress callback if progress information is desired, or
     *            <code>null</code>
     * @param warnings
     *            any warning generated from this method will be added to the
     *            List. Warnings are always Strings.
     * @param fullyQualifiedTableNames
     *            a set of table names to generate. The elements of the set must
     *            be Strings that exactly match what's specified in the
     *            configuration. For example, if table name = "foo" and schema =
     *            "bar", then the fully qualified table name is "foo.bar". If
     *            the Set is null or empty, then all tables in the configuration
     *            will be used for code generation.
     * 
     * @throws SQLException
     *             if some error arises while introspecting the specified
     *             database tables.
     * @throws InterruptedException
     *             if the progress callback reports a cancel
     */
    public void introspectTables(ProgressCallback callback,
            List<String> warnings, Set<String> fullyQualifiedTableNames)
            throws Exception {

        introspectedTables = new ArrayList<>();
        JavaTypeResolver javaTypeResolver = ObjectFactory
                .createJavaTypeResolver(this, warnings);

        Connection connection = null;

        try {
            if (callback != null) {
                callback.startTask(getString("Progress.0")); //$NON-NLS-1$
            }
            connection = getConnection();

            DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(
                    this, connection.getMetaData(), javaTypeResolver, warnings);

            for (TableConfiguration tc : tableConfigurations) {
                String tableName = composeFullyQualifiedTableName(tc.getCatalog(), tc
                                .getSchema(), tc.getTableName(), '.');

                if (fullyQualifiedTableNames != null
                        && fullyQualifiedTableNames.size() > 0
                        && !fullyQualifiedTableNames.contains(tableName)) {
                    continue;
                }

                if (!tc.areAnyStatementsEnabled()) {
                    warnings.add(getString("Warning.0", tableName)); //$NON-NLS-1$
                    continue;
                }

                if (callback != null) {
                    callback.startTask(getString("Progress.1", tableName)); //$NON-NLS-1$
                }
                List<IntrospectedTable> tables = databaseIntrospector
                        .introspectTables(tc);

                if (tables != null) {
                    introspectedTables.addAll(tables);
                }

                if (callback != null) {
                    callback.checkCancel();
                }
            }
        } finally {
            closeConnection(connection);
        }
    }

    public int getGenerationSteps() {
        int steps = 0;

        if (introspectedTables != null) {
            for (IntrospectedTable introspectedTable : introspectedTables) {
                steps += introspectedTable.getGenerationSteps();
            }
        }

        return steps;
    }

    private Connection getConnection() throws SQLException {
        ConnectionFactory connectionFactory;
        if (jdbcConnectionConfiguration != null) {
            connectionFactory = new JDBCConnectionFactory(jdbcConnectionConfiguration);
        } else {
            connectionFactory = ObjectFactory.createConnectionFactory(this);
        }

        return connectionFactory.getConnection();
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    public boolean autoDelimitKeywords() {
        return autoDelimitKeywords != null
                && autoDelimitKeywords.booleanValue();
    }

    public ConnectionFactoryConfiguration getConnectionFactoryConfiguration() {
        return connectionFactoryConfiguration;
    }

    public void setConnectionFactoryConfiguration(ConnectionFactoryConfiguration connectionFactoryConfiguration) {
        this.connectionFactoryConfiguration = connectionFactoryConfiguration;
    }

    public boolean isJava8Targeted() {
        return isJava8Targeted;
    }

    public void setJava8Targeted(boolean isJava8Targeted) {
        this.isJava8Targeted = isJava8Targeted;
    }

}

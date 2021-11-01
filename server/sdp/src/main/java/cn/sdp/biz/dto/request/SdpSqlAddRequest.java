package cn.sdp.biz.dto.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpSqlAddRequest
 * @Description: AddRequest
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpSqlAddRequest extends BaseRequest {
    private static final long serialVersionUID = 87161731052290755L;

    @JsonProperty("is_disable")
    @JSONField(name = "is_disable")
    private Integer isDisable;

    @JsonProperty("workspace_name")
    @JSONField(name = "workspace_name")
    private String workspaceName;

    @JsonProperty("table_name")
    @JSONField(name = "table_name")
    private String tableName;

    @JsonProperty("parameter_catalog")
    @JSONField(name = "parameter_catalog")
    private String parameterCatalog;

    @JsonProperty("parameter_catalog_type")
    @JSONField(name = "parameter_catalog_type")
    private String parameterCatalogType;

    @JsonProperty("name")
    @JSONField(name = "name")
    private String name;

    @JsonProperty("java_type")
    @JSONField(name = "java_type")
    private String javaType;

    @JsonProperty("remarks")
    @JSONField(name = "remarks")
    private String remarks;

    @JsonProperty("parameter_mode")
    @JSONField(name = "parameter_mode")
    private String parameterMode;

    @JsonProperty("parameter_nullable")
    @JSONField(name = "parameter_nullable")
    private Integer parameterNullable;

    @JsonProperty("parameter_is_like")
    @JSONField(name = "parameter_is_like")
    private Integer parameterIsLike;

    @JsonProperty("parameter_is_import_excel")
    @JSONField(name = "parameter_is_import_excel")
    private Integer parameterIsImportExcel;

    @JsonProperty("parameter_is_export_excel")
    @JSONField(name = "parameter_is_export_excel")
    private Integer parameterIsExportExcel;

    @JsonProperty("parameter_sql_value_item")
    @JSONField(name = "parameter_sql_value_item")
    private String parameterSqlValueItem;

    @JsonProperty("parameter_sql_value")
    @JSONField(name = "parameter_sql_value")
    private String parameterSqlValue;

    @JsonProperty("parameter_sql_value_ignore")
    @JSONField(name = "parameter_sql_value_ignore")
    private Integer parameterSqlValueIgnore;

    @JsonProperty("parameter_without_test")
    @JSONField(name = "parameter_without_test")
    private Integer parameterWithoutTest;

    @JsonProperty("parameter_sql_issimple")
    @JSONField(name = "parameter_sql_issimple")
    private Integer parameterSqlIssimple;

    @JsonProperty("parameter_sql_return_nolist")
    @JSONField(name = "parameter_sql_return_nolist")
    private Integer parameterSqlReturnNolist;

    @JsonProperty("java_imports")
    @JSONField(name = "java_imports")
    private String javaImports;

    @JsonProperty("parameter_overwrite_default_sql")
    @JSONField(name = "parameter_overwrite_default_sql")
    private Integer parameterOverwriteDefaultSql;

    @JsonProperty("java_return_type")
    @JSONField(name = "java_return_type")
    private String javaReturnType;

    @JsonProperty("sort_no")
    @JSONField(name = "sort_no")
    private Integer sortNo;

    @JsonProperty("remark")
    @JSONField(name = "remark")
    private String remark;

    @JsonProperty("is_interface")
    @JSONField(name = "is_interface")
    private Integer isInterface;

    @JsonProperty("parameter_sql")
    @JSONField(name = "parameter_sql")
    private String parameterSql;

    @JsonProperty("java_body")
    @JSONField(name = "java_body")
    private String javaBody;

    @JsonProperty("extra_info")
    @JSONField(name = "extra_info")
    private String extraInfo;

    @JsonProperty("disable_auto_param")
    @JSONField(name = "disable_auto_param")
    private Integer disableAutoParam;

    @Override
    public void checkRequest() throws Exception {
    }
}

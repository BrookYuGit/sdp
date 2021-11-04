package cn.mysdp.biz.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: SdpSql
 * @Description: DomainObject
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
@Getter
@Setter
public class SdpSql implements Serializable {
    private static final long serialVersionUID = 20529531822975697L;

    private Integer id;

    private Integer isDisable;

    private String workspaceName;

    private String tableName;

    private String parameterCatalog;

    private String parameterCatalogType;

    private String name;

    private String javaType;

    private String remarks;

    private String parameterMode;

    private Integer parameterNullable;

    private Integer parameterIsLike;

    private Integer parameterIsImportExcel;

    private Integer parameterIsExportExcel;

    private String parameterSqlValueItem;

    private String parameterSqlValue;

    private Integer parameterSqlValueIgnore;

    private Integer parameterWithoutTest;

    private Integer parameterSqlIssimple;

    private Integer parameterSqlReturnNolist;

    private String javaImports;

    private Integer parameterOverwriteDefaultSql;

    private String javaReturnType;

    private Integer sortNo;

    private String remark;

    private Integer isInterface;

}

package cn.mysdp.biz.repository;

import cn.mysdp.biz.domain.*;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * InterfaceName: SdpSqlMapper
 * @Description: Repository
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
public interface SdpSqlMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByWorkspaceNameTableNameParameterCatalogParameterCatalogTypeName(@Param("workspaceName") String workspaceName, @Param("tableName") String tableName, @Param("parameterCatalog") String parameterCatalog, @Param("parameterCatalogType") String parameterCatalogType, @Param("name") String name);

    int deleteByExample(SdpSqlExample example);

    int insert(SdpSql record);

    int insertWithBLOBs(SdpSqlWithBLOBs record);

    int insertSelective(SdpSqlWithBLOBs record);

    List<SdpSqlWithBLOBs> selectByExampleWithBLOBs(SdpSqlExample example);

    List<SdpSql> selectByExample(SdpSqlExample example);

    int countByExample(SdpSqlExample example);

    List<SdpSql> listWithoutBLOBs(SdpSql record);

    List<SdpSqlWithBLOBs> listWithBLOBs(SdpSql record);

    SdpSql selectByPrimaryKey(Integer id);

    SdpSqlWithBLOBs selectByPrimaryKeyWithBLOBs(Integer id);

    SdpSql selectByWorkspaceNameTableNameParameterCatalogParameterCatalogTypeName(@Param("workspaceName") String workspaceName, @Param("tableName") String tableName, @Param("parameterCatalog") String parameterCatalog, @Param("parameterCatalogType") String parameterCatalogType, @Param("name") String name);

    SdpSqlWithBLOBs selectByWorkspaceNameTableNameParameterCatalogParameterCatalogTypeNameWithBLOBs(@Param("workspaceName") String workspaceName, @Param("tableName") String tableName, @Param("parameterCatalog") String parameterCatalog, @Param("parameterCatalogType") String parameterCatalogType, @Param("name") String name);

    int updateByExampleSelective(@Param("record") SdpSqlWithBLOBs record, @Param("example") SdpSqlExample example);

    int updateByPrimaryKeySelective(SdpSqlWithBLOBs record);

    int updateByWorkspaceNameTableNameParameterCatalogParameterCatalogTypeNameSelective(SdpSqlWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SdpSqlWithBLOBs record);

    int updateByPrimaryKey(SdpSql record);

}

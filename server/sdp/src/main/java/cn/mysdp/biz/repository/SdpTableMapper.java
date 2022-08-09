package cn.mysdp.biz.repository;

import cn.mysdp.biz.domain.*;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * InterfaceName: SdpTableMapper
 * @Description: Repository
 * @Author: SDP
 * @Date: 2022-08-09
 * @Version: 1.0
 * Table: sdp_table
 * Comment:
 * 
 */
public interface SdpTableMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByWorkspaceNameName(@Param("workspaceName") String workspaceName, @Param("name") String name);

    int deleteByExample(SdpTableExample example);

    int insert(SdpTable record);

    int insertWithBLOBs(SdpTableWithBLOBs record);

    int insertSelective(SdpTableWithBLOBs record);

    List<SdpTableWithBLOBs> selectByExampleWithBLOBs(SdpTableExample example);

    List<SdpTable> selectByExample(SdpTableExample example);

    int countByExample(SdpTableExample example);

    List<SdpTable> listWithoutBLOBs(SdpTable record);

    List<SdpTableWithBLOBs> listWithBLOBs(SdpTable record);

    SdpTable selectByPrimaryKey(Integer id);

    SdpTableWithBLOBs selectByPrimaryKeyWithBLOBs(Integer id);

    SdpTable selectByWorkspaceNameName(@Param("workspaceName") String workspaceName, @Param("name") String name);

    SdpTableWithBLOBs selectByWorkspaceNameNameWithBLOBs(@Param("workspaceName") String workspaceName, @Param("name") String name);

    int updateByExampleSelective(@Param("record") SdpTableWithBLOBs record, @Param("example") SdpTableExample example);

    int updateByPrimaryKeySelective(SdpTableWithBLOBs record);

    int updateByWorkspaceNameNameSelective(SdpTableWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SdpTableWithBLOBs record);

    int updateByPrimaryKey(SdpTable record);

}

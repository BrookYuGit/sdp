package cn.mysdp.biz.repository;

import cn.mysdp.biz.domain.*;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * InterfaceName: SdpWorkspaceConfigMapper
 * @Description: Repository
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace_config
 * Comment:
 * 
 */
public interface SdpWorkspaceConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByWorkspaceNameName(@Param("workspaceName") String workspaceName, @Param("name") String name);

    int deleteByExample(SdpWorkspaceConfigExample example);

    int insert(SdpWorkspaceConfig record);

    int insertWithBLOBs(SdpWorkspaceConfigWithBLOBs record);

    int insertSelective(SdpWorkspaceConfigWithBLOBs record);

    List<SdpWorkspaceConfigWithBLOBs> selectByExampleWithBLOBs(SdpWorkspaceConfigExample example);

    List<SdpWorkspaceConfig> selectByExample(SdpWorkspaceConfigExample example);

    int countByExample(SdpWorkspaceConfigExample example);

    List<SdpWorkspaceConfig> listWithoutBLOBs(SdpWorkspaceConfig record);

    List<SdpWorkspaceConfigWithBLOBs> listWithBLOBs(SdpWorkspaceConfig record);

    SdpWorkspaceConfig selectByPrimaryKey(Integer id);

    SdpWorkspaceConfigWithBLOBs selectByPrimaryKeyWithBLOBs(Integer id);

    SdpWorkspaceConfig selectByWorkspaceNameName(@Param("workspaceName") String workspaceName, @Param("name") String name);

    SdpWorkspaceConfigWithBLOBs selectByWorkspaceNameNameWithBLOBs(@Param("workspaceName") String workspaceName, @Param("name") String name);

    int updateByExampleSelective(@Param("record") SdpWorkspaceConfigWithBLOBs record, @Param("example") SdpWorkspaceConfigExample example);

    int updateByPrimaryKeySelective(SdpWorkspaceConfigWithBLOBs record);

    int updateByWorkspaceNameNameSelective(SdpWorkspaceConfigWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SdpWorkspaceConfigWithBLOBs record);

    int updateByPrimaryKey(SdpWorkspaceConfig record);

}

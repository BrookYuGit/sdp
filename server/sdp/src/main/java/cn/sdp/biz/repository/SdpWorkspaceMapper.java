package cn.sdp.biz.repository;

import cn.sdp.biz.domain.*;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * InterfaceName: SdpWorkspaceMapper
 * @Description: Repository
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_workspace
 * Comment:
 * workspace
 */
public interface SdpWorkspaceMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByName(String name);

    int deleteByExample(SdpWorkspaceExample example);

    int insert(SdpWorkspace record);

    int insertWithBLOBs(SdpWorkspaceWithBLOBs record);

    int insertSelective(SdpWorkspaceWithBLOBs record);

    List<SdpWorkspaceWithBLOBs> selectByExampleWithBLOBs(SdpWorkspaceExample example);

    List<SdpWorkspace> selectByExample(SdpWorkspaceExample example);

    int countByExample(SdpWorkspaceExample example);

    List<SdpWorkspace> listWithoutBLOBs(SdpWorkspace record);

    List<SdpWorkspaceWithBLOBs> listWithBLOBs(SdpWorkspace record);

    SdpWorkspace selectByPrimaryKey(Integer id);

    SdpWorkspaceWithBLOBs selectByPrimaryKeyWithBLOBs(Integer id);

    SdpWorkspace selectByName(String name);

    SdpWorkspaceWithBLOBs selectByNameWithBLOBs(String name);

    int updateByExampleSelective(@Param("record") SdpWorkspaceWithBLOBs record, @Param("example") SdpWorkspaceExample example);

    int updateByPrimaryKeySelective(SdpWorkspaceWithBLOBs record);

    int updateByNameSelective(SdpWorkspaceWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SdpWorkspaceWithBLOBs record);

    int updateByPrimaryKey(SdpWorkspace record);

    List<SdpWorkspaceForGetDbConfig> getDbConfigBySimple(SdpWorkspaceForGetDbConfig record);

    int countGetDbConfigBySimple(SdpWorkspaceForGetDbConfig record);

    List<SdpWorkspaceForGetDbConfig> getDbConfig(SdpWorkspaceForGetDbConfig record);

    int countGetDbConfig(SdpWorkspaceForGetDbConfig record);

    List<SdpWorkspaceForGetDbConfig> getDbConfigByExample(SdpWorkspaceExampleForGetDbConfig example);

    int countGetDbConfigByExample(SdpWorkspaceExampleForGetDbConfig example);

    List<SdpWorkspaceForGetTableList> getTableListBySimple(SdpWorkspaceForGetTableList record);

    int countGetTableListBySimple(SdpWorkspaceForGetTableList record);

    List<SdpWorkspaceForGetTableList> getTableList(SdpWorkspaceForGetTableList record);

    int countGetTableList(SdpWorkspaceForGetTableList record);

    List<SdpWorkspaceForGetTableList> getTableListByExample(SdpWorkspaceExampleForGetTableList example);

    int countGetTableListByExample(SdpWorkspaceExampleForGetTableList example);

}

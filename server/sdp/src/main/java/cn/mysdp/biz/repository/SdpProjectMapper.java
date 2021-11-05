package cn.mysdp.biz.repository;

import cn.mysdp.biz.domain.*;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * InterfaceName: SdpProjectMapper
 * @Description: Repository
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_project
 * Comment:
 * 
 */
public interface SdpProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByWorkspaceNameName(@Param("workspaceName") String workspaceName, @Param("name") String name);

    int deleteByExample(SdpProjectExample example);

    int insert(SdpProject record);

    int insertWithBLOBs(SdpProjectWithBLOBs record);

    int insertSelective(SdpProjectWithBLOBs record);

    List<SdpProjectWithBLOBs> selectByExampleWithBLOBs(SdpProjectExample example);

    List<SdpProject> selectByExample(SdpProjectExample example);

    int countByExample(SdpProjectExample example);

    List<SdpProject> listWithoutBLOBs(SdpProject record);

    List<SdpProjectWithBLOBs> listWithBLOBs(SdpProject record);

    SdpProject selectByPrimaryKey(Integer id);

    SdpProjectWithBLOBs selectByPrimaryKeyWithBLOBs(Integer id);

    SdpProject selectByWorkspaceNameName(@Param("workspaceName") String workspaceName, @Param("name") String name);

    SdpProjectWithBLOBs selectByWorkspaceNameNameWithBLOBs(@Param("workspaceName") String workspaceName, @Param("name") String name);

    int updateByExampleSelective(@Param("record") SdpProjectWithBLOBs record, @Param("example") SdpProjectExample example);

    int updateByPrimaryKeySelective(SdpProjectWithBLOBs record);

    int updateByWorkspaceNameNameSelective(SdpProjectWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SdpProjectWithBLOBs record);

    int updateByPrimaryKey(SdpProject record);

}

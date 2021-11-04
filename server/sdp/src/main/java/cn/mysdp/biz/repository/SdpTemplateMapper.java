package cn.mysdp.biz.repository;

import cn.mysdp.biz.domain.*;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * InterfaceName: SdpTemplateMapper
 * @Description: Repository
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_template
 * Comment:
 * 
 */
public interface SdpTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByWorkspaceNameProjectNameNameFileTypeProjectPackageName(@Param("workspaceName") String workspaceName, @Param("projectName") String projectName, @Param("name") String name, @Param("fileType") String fileType, @Param("project") String project, @Param("packageName") String packageName);

    int deleteByExample(SdpTemplateExample example);

    int insert(SdpTemplate record);

    int insertWithBLOBs(SdpTemplateWithBLOBs record);

    int insertSelective(SdpTemplateWithBLOBs record);

    List<SdpTemplateWithBLOBs> selectByExampleWithBLOBs(SdpTemplateExample example);

    List<SdpTemplate> selectByExample(SdpTemplateExample example);

    int countByExample(SdpTemplateExample example);

    List<SdpTemplate> listWithoutBLOBs(SdpTemplate record);

    List<SdpTemplateWithBLOBs> listWithBLOBs(SdpTemplate record);

    SdpTemplate selectByPrimaryKey(Integer id);

    SdpTemplateWithBLOBs selectByPrimaryKeyWithBLOBs(Integer id);

    SdpTemplate selectByWorkspaceNameProjectNameNameFileTypeProjectPackageName(@Param("workspaceName") String workspaceName, @Param("projectName") String projectName, @Param("name") String name, @Param("fileType") String fileType, @Param("project") String project, @Param("packageName") String packageName);

    SdpTemplateWithBLOBs selectByWorkspaceNameProjectNameNameFileTypeProjectPackageNameWithBLOBs(@Param("workspaceName") String workspaceName, @Param("projectName") String projectName, @Param("name") String name, @Param("fileType") String fileType, @Param("project") String project, @Param("packageName") String packageName);

    int updateByExampleSelective(@Param("record") SdpTemplateWithBLOBs record, @Param("example") SdpTemplateExample example);

    int updateByPrimaryKeySelective(SdpTemplateWithBLOBs record);

    int updateByWorkspaceNameProjectNameNameFileTypeProjectPackageNameSelective(SdpTemplateWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SdpTemplateWithBLOBs record);

    int updateByPrimaryKey(SdpTemplate record);

}

package cn.mysdp.biz.repository;

import cn.mysdp.biz.domain.*;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * InterfaceName: SdpHistoryMapper
 * @Description: Repository
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_history
 * Comment:
 * 
 */
public interface SdpHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByExample(SdpHistoryExample example);

    int insert(SdpHistory record);

    int insertWithBLOBs(SdpHistoryWithBLOBs record);

    int insertSelective(SdpHistoryWithBLOBs record);

    List<SdpHistoryWithBLOBs> selectByExampleWithBLOBs(SdpHistoryExample example);

    List<SdpHistory> selectByExample(SdpHistoryExample example);

    int countByExample(SdpHistoryExample example);

    List<SdpHistory> listWithoutBLOBs(SdpHistory record);

    List<SdpHistoryWithBLOBs> listWithBLOBs(SdpHistory record);

    SdpHistory selectByPrimaryKey(Integer id);

    SdpHistoryWithBLOBs selectByPrimaryKeyWithBLOBs(Integer id);

    int updateByExampleSelective(@Param("record") SdpHistoryWithBLOBs record, @Param("example") SdpHistoryExample example);

    int updateByPrimaryKeySelective(SdpHistoryWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SdpHistoryWithBLOBs record);

    int updateByPrimaryKey(SdpHistory record);

}

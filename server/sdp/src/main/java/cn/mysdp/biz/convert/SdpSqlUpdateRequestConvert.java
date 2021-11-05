package cn.mysdp.biz.convert;

import cn.mysdp.biz.domain.*;
import cn.mysdp.biz.dto.request.*;
import java.util.Date;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName: SdpSqlUpdateRequestConvert
 * @Description: UpdateRequestConvert
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table: sdp_sql
 * Comment:
 * 
 */
public class SdpSqlUpdateRequestConvert {
    public static SdpSqlWithBLOBs convert(SdpSqlUpdateRequest request) {
        SdpSqlWithBLOBs daoRequest = new SdpSqlWithBLOBs();
        BeanUtils.copyProperties(request, daoRequest);

        daoRequest.setWorkspaceName(daoRequest.getWorkspaceName() == null ? null : daoRequest.getWorkspaceName().trim());
        daoRequest.setTableName(daoRequest.getTableName() == null ? null : daoRequest.getTableName().trim());
        daoRequest.setParameterCatalog(daoRequest.getParameterCatalog() == null ? null : daoRequest.getParameterCatalog().trim());
        daoRequest.setParameterCatalogType(daoRequest.getParameterCatalogType() == null ? null : daoRequest.getParameterCatalogType().trim());
        daoRequest.setName(daoRequest.getName() == null ? null : daoRequest.getName().trim());
        daoRequest.setJavaType(daoRequest.getJavaType() == null ? null : daoRequest.getJavaType().trim());
        daoRequest.setRemarks(daoRequest.getRemarks() == null ? null : daoRequest.getRemarks().trim());
        daoRequest.setParameterMode(daoRequest.getParameterMode() == null ? null : daoRequest.getParameterMode().trim());
        daoRequest.setParameterSqlValueItem(daoRequest.getParameterSqlValueItem() == null ? null : daoRequest.getParameterSqlValueItem().trim());
        daoRequest.setParameterSqlValue(daoRequest.getParameterSqlValue() == null ? null : daoRequest.getParameterSqlValue().trim());
        daoRequest.setJavaImports(daoRequest.getJavaImports() == null ? null : daoRequest.getJavaImports().trim());
        daoRequest.setJavaReturnType(daoRequest.getJavaReturnType() == null ? null : daoRequest.getJavaReturnType().trim());
        daoRequest.setRemark(daoRequest.getRemark() == null ? null : daoRequest.getRemark().trim());
        daoRequest.setParameterSql(daoRequest.getParameterSql() == null ? null : daoRequest.getParameterSql().trim());
        daoRequest.setJavaBody(daoRequest.getJavaBody() == null ? null : daoRequest.getJavaBody().trim());
        daoRequest.setExtraInfo(daoRequest.getExtraInfo() == null ? null : daoRequest.getExtraInfo().trim());

        return daoRequest;
    }
}

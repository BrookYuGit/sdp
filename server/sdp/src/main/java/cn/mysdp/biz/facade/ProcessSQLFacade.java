package cn.mysdp.biz.facade;

import cn.mysdp.biz.dto.request.BaseNameRequest;
import cn.mysdp.biz.dto.request.SdpWorkspaceAddRequest;
import cn.mysdp.biz.dto.request.SdpWorkspaceUpdateRequest;
import cn.mysdp.biz.dto.response.SdpTemplateQueryResponse;
import cn.mysdp.utils.ByteWithPos;
import cn.mysdp.utils.DynProcessTokenResult;
import cn.mysdp.biz.dto.response.SdpWorkspaceQueryResponse;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * InterfaceName:
 * @Description:
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table:
 * Comment:
 *
 */
public interface ProcessSQLFacade {
    Integer execute(BaseNameRequest request, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception;
    String encryptDBPassword(SdpWorkspaceAddRequest request) throws Exception;
    String encryptDBPassword(SdpWorkspaceUpdateRequest request) throws Exception;
    String decryptDbPassword(SdpWorkspaceQueryResponse record) throws Exception;
    boolean isToken(String token, String v);
    boolean isBlockToken(String token, String v);
    String getName(String token, String name, Map<String, String> properties, IntrospectedColumn column) throws Exception;
    DynProcessTokenResult processTokenProperties(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<IntrospectedColumn> columns, Integer columnIndex, String token, Map<String, String> properties) throws Exception;
    void processLine(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, ByteWithPos bodyBytes, ByteWithPos destBytes, List<IntrospectedColumn> columns, int columnIndex, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception;
    DynProcessTokenResult processBodyToken(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<ByteWithPos> lineBytesList, List<String> lineStringList, List<String> lineTrimStringList, List<Integer> deepList, int deep, ByteWithPos destBytes, String vTrim, List<IntrospectedColumn> inColumns, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception;
    DynProcessTokenResult processBodyTokenProperties(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<ByteWithPos> lineBytesList, List<String> lineStringList, List<String> lineTrimStringList, List<Integer> deepList, int deep, ByteWithPos destBytes, String vTrim, Map<String, String> properties, List<IntrospectedColumn> inColumns) throws Exception;
    void processBodyTokenWithColumns(IntrospectedTable introspectedTable, SdpTemplateQueryResponse dynTemplate, String fileName, String sqlMethodName, List<ByteWithPos> lineBytesList, List<String> lineStringList, List<String> lineTrimStringList, List<Integer> deepList, int deep, ByteWithPos destBytes, String vTrim, Map<String, String> properties, List<IntrospectedColumn> inColumns, List<IntrospectedColumn> introspectedColumns, List<IntrospectedColumn> extraColumns, Object _processInstance, Method _processBodyToken, Method _processToken) throws Exception;
}

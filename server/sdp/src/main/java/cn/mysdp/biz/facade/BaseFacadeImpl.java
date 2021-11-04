package cn.mysdp.biz.facade;

import cn.mysdp.biz.domain.SdpHistoryWithBLOBs;
import cn.mysdp.biz.dto.request.BaseRequest;
import cn.mysdp.biz.repository.SdpHistoryMapper;
import cn.mysdp.http.SDPHttpException;
import cn.mysdp.http.SDPHttpResult;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @ClassName:
 * @Description:
 * @Author:
 * @Date: 2020-10-30
 * Table:
 * Comment:
 *
 */
@Service
public abstract class BaseFacadeImpl {

    @Autowired
    SdpHistoryMapper sdpHistoryMapper;

    public void checkLowercaseName(String name) throws Exception {
        String validChars = "abcdefghijklmnopqrstuvwxyz1234567890_";
        checkName(name, validChars, "必须为小写字母、数字、下滑线");
    }

    public void checkLowercaseNameWithDot(String name) throws Exception {
        String validChars = "abcdefghijklmnopqrstuvwxyz1234567890_.";
        checkName(name, validChars, "必须为小写字母、数字、下滑线、小数点");
    }

    public void checkName(String name, String validChars, String tip) throws Exception {
        if (!StringUtils.isEmpty(name)) {
            for (char c : name.toCharArray()) {
                if (validChars.indexOf(c) < 0) {
                    throw new Exception("名称非法，"+tip+"："+name);
                }
            }
        }
    }

    public void addHistory(Object object, String tableName) {
        if ("sdp_history".equals(tableName)) {
            return;
        }
        String workspaceName = "";
        String fieldName = "workspaceName";
        if ("sdp_workspace".equals(tableName)) {
            fieldName = "name";
        }
        Field field = getField(object.getClass(), fieldName);
        if (field != null) {
            try {
                workspaceName = (String) field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        SdpHistoryWithBLOBs history = new SdpHistoryWithBLOBs();
        history.setWorkspaceName(workspaceName);
        history.setTableName(tableName);
        history.setContent(JSON.toJSONString(object));
        sdpHistoryMapper.insertSelective(history);
    }

    public void checkNonNull(Object object, String msg) {
        if (null == object) {
            throw new SDPHttpException(SDPHttpResult.FAIL,"miss param:" + msg);
        }
    }

    public SDPHttpException createFailException(String msg) {
        return new SDPHttpException(SDPHttpResult.FAIL, msg);
    }

    public SDPHttpException createUpdateFailException() {
        return new SDPHttpException(SDPHttpResult.E2004, SDPHttpResult.E2004.getMsg());
    }

    public SDPHttpException createDatabaseFailException(Exception ex) {
        ex.printStackTrace();
        String msg = ex.getMessage().split(" error")[0];
        if (msg.length() > 200) {
            msg = msg.substring(0, 200);
        }
        return new SDPHttpException(SDPHttpResult.FAIL, msg);
    }

    public SDPHttpException checkDBDupException(Exception ex, Map<String, String> map) throws SDPHttpException {
        String exMessage = ex.getMessage();
        Integer dupIndex = exMessage.indexOf("Duplicate entry '");
        Integer forKeyIndex = exMessage.indexOf("' for key ", dupIndex);
        if (dupIndex >= 0 && forKeyIndex >= 0) {
            String errInfo = exMessage.substring(dupIndex + "Duplicate entry '".length(), forKeyIndex);
            String keyName = exMessage.substring(forKeyIndex + 1).split("'")[1];
            if (map.containsKey(keyName)) {
                return new SDPHttpException(SDPHttpResult.FAIL, "已经存在：" + errInfo + "(" + map.get(keyName) + ")");
            }
        }
        return new SDPHttpException(SDPHttpResult.FAIL, ex.getMessage());
    }

    void clearPageInfo(BaseRequest request) {
        if(request.getQueryOptions() != null) {
            request.getQueryOptions().remove("count");
            request.getQueryOptions().remove("limit");
        }
        request.setPageNo(null);
        request.setPageSize(null);
    }

    Field getField(Class clz, String name) {
        int loop = 0;
        while (clz != null && loop++ < 10) {
            for(Field field: clz.getDeclaredFields()) {
                if (name.equals(field.getName())) {
                    field.setAccessible(true);
                    return field;
                }
            }
            clz = clz.getSuperclass();
        }
        return null;
    }

    Method getMethod(Class clz, String name) {
        int loop = 0;
        while (clz != null && loop++ < 10) {
            for(Method method: clz.getDeclaredMethods()) {
                if (name.equals(method.getName())) {
                    method.setAccessible(true);
                    return method;
                }
            }
            clz = clz.getSuperclass();
        }
        return null;
    }

}

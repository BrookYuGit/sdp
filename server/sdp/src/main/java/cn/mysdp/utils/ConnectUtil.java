package cn.mysdp.utils;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-10-30
 * Table:
 * Comment:
 */
@Getter
@Setter
public class ConnectUtil {
    public static String getUrl(String type, String host, Integer port, String database) throws Exception {
        String url = "";
        if ("com.mysql.jdbc.Driver".equals(type) || "com.mysql.cj.jdbc.Driver".equals(type)) {
            url = "jdbc:mysql://"+host+":"+port+"/"+database+"?useUnicode=true&characterEncoding=UTF8&serverTimezone=Asia/Shanghai&tcpKeepAlive=true&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
        } else if ("org.h2.Driver".equals(type)) {
            url = "jdbc:h2:"+database+";AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1";
        } else {
            throw new Exception("不支持的数据库驱动类："+type);
        }
        return url;
    }

}

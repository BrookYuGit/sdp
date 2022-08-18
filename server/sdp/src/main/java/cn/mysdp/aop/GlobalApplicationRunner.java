package cn.mysdp.aop;

import cn.mysdp.biz.dto.request.BaseRequest;
import cn.mysdp.http.SDPHttpResponse;
import cn.mysdp.http.SDPHttpResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.TreeSet;

/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-10-30
 * Table:
 * Comment:
 */
@Component
public class GlobalApplicationRunner  implements ApplicationRunner {
    @Value("${server.port}")
    String serverPort;

    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        String db = datasourceUrl.split(";")[0];
        db = db.split(":")[db.split(":").length - 1];
        System.err.println("------------------------------------------------------------------------");
        System.err.println("Welcome to SDP's world via the following URL:");
        System.err.println("");
        System.err.println("    http://localhost:"+serverPort);
        System.err.println("");
        System.err.println("Browse http://localhost:"+serverPort+"/db to view the database.");
        System.err.println("Access https://github.com/BrookYuGit/sdp to get the lastest source code.");
        System.err.println("");
        System.err.println("Datasource is "+datasourceUrl);
        System.err.println("Database file is "+db+"/sdp.mv.db");
        System.err.println("------------------------------------------------------------------------");
    }
}

package cn.sdp.aop;

import cn.sdp.http.SDPHttpResponse;
import cn.sdp.http.SDPHttpResult;
import cn.sdp.biz.dto.request.BaseRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-10-30
 * Table:
 * Comment:
 */
@Aspect
@Component
@Order(-1)
public class GlobalAop {

    @Pointcut("execution(public * cn.sdp.biz.controller..*.*(..))")
    public void accessPointcut() {
    }

    @Around("accessPointcut()")
    public Object doAccessPointcut(ProceedingJoinPoint pjd) throws Throwable {
        Object response = null;
        doBeforeProceed(pjd);
        response = pjd.proceed();
        return response;

    }

    @AfterReturning(pointcut = "accessPointcut()", returning = "returnValue")
    public void doReturnValue(Object returnValue) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = attributes.getResponse();

        if (!(returnValue instanceof SDPHttpResponse)) {

            String json = JSON.toJSONString(SDPHttpResponse.builder().
                        code(SDPHttpResult.SUCCESS.getCode()).
                        msg(SDPHttpResult.SUCCESS.getMsg()).
                        body(returnValue).
                        build());

            ServletOutputStream os = null;
            try {
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("text/json;charset=UTF-8");
                os = httpServletResponse.getOutputStream();
                os.write(json.getBytes());
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (os != null) {
                    os.close();
                }
            }

        }
    }

    public void doBeforeProceed(JoinPoint joinPoint) throws Exception {
        Object[] arguments = joinPoint.getArgs();

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof ServletRequest || arguments[i] instanceof ServletResponse
                    || arguments[i] instanceof MultipartFile || arguments[i] instanceof ExtendedServletRequestDataBinder) {
                continue;
            }

            if (arguments[i] instanceof BaseRequest) {

                BaseRequest baseRequest = (BaseRequest) arguments[i];

                baseRequest.checkRequest();

                if (baseRequest.getPageNo() != null && baseRequest.getPageSize() != null) {
                    if (baseRequest.getQueryOptions() == null) {
                        baseRequest.setQueryOptions(new JSONObject());
                    }
                    baseRequest.getQueryOptions().put("limit", (baseRequest.getPageNo() - 1) * baseRequest.getPageSize());
                    baseRequest.getQueryOptions().put("count", baseRequest.getPageSize());
                }
            }
        }
     }

}
